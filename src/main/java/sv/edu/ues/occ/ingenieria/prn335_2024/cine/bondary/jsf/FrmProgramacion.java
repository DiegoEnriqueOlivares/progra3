package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class FrmProgramacion extends FrmAbstractDataPersistF<Programacion> implements Serializable {

    @Inject
    ProgramacionBean progBean;
    @Inject
    FacesContext facesContext;
    @Inject
    FrmPeliculaF frmPeliculaF;
    @Inject
    FrmSalaF frmSalaF;

    Pelicula selectedPelicula;
    boolean eventSelected = false;
    Integer idSala;
    Long idProgramacion;
    Long idPelicula;
    String nombrePelicula;
    private ScheduleModel eventModel;
    private ScheduleEvent<Programacion> selectedEvent;

    List<Programacion> programacionList;


    @PostConstruct
    public void init() {
        super.inicializar();
        try {
            this.programacionList = progBean.findByIdSala(idSala,0,Integer.MAX_VALUE);
            eventModel = new DefaultScheduleModel();
            cargarProgramaciones();

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los tipos"));
        }

        selectedEvent = new DefaultScheduleEvent<>();
    }

    public void obtenerFechaFin(){
        List<Pelicula> peliculas = frmPeliculaF.loadRegistros(0, 100000);
        for(Pelicula p: peliculas){
            if(p.getNombre().equals(nombrePelicula)){
                selectedPelicula=p;
                for(PeliculaCaracteristica pc: p.getPeliculaCaracteristicaList()) {
                    if (pc.getIdTipoPelicula().getNombre().toUpperCase().equals("DURACION")) {
                        selectedEvent.setEndDate( selectedEvent.getStartDate().plusMinutes(Long.parseLong(pc.getValor())));
                    }
                }
            }

        }
    }



    protected void cargarProgramaciones() {
        eventModel.clear();
        programacionList = progBean.findByIdSala(this.idSala, 0, Integer.MAX_VALUE);

        for (Programacion prog : programacionList) {
            DefaultScheduleEvent<Object> event = DefaultScheduleEvent.builder()
                    .title(prog.getIdPelicula() != null ? prog.getIdPelicula().getNombre() : "Sin título")
                    .startDate(prog.getDesde().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .endDate(prog.getHasta().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .data(prog)
                    .build();
            eventModel.addEvent(event);
        }
    }


    public void onEventSelect(SelectEvent<org.primefaces.model.ScheduleEvent<Programacion>> selectEvent) {
        selectedEvent = (DefaultScheduleEvent<Programacion>) selectEvent.getObject();
        this.eventSelected=true;
        this.nombrePelicula= selectEvent.getObject().getTitle();
        this.idProgramacion = selectEvent.getObject().getData().getIdProgramacion();

    }

    public void onDateSelect(SelectEvent<Date> selectEvent) {
        System.out.println(" id Peli ese" + selectedPelicula);
        // Convertir la fecha seleccionada de Date a LocalDateTime
        LocalDateTime startDate = selectEvent.getObject()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endDate = selectEvent.getObject()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();


        selectedEvent.setStartDate(startDate);
        selectedEvent.setEndDate(endDate);


        // Actualizar la vista parcial para mostrar el diálogo
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("eventDialog");
        PrimeFaces.current().ajax().update("eventDialog");
        PrimeFaces.current().executeScript("PF('eventDialog').show()");
    }

    public void btnCreateHandler() {
        try {
                Programacion prog = createNewEntity();
                Sala sala = new Sala();
                sala.setIdSala(idSala);
                prog.setIdSala(sala);
                prog.setIdPelicula(programacionList.get(0).getIdPelicula());
                prog.setDesde(selectedEvent.getStartDate().atZone(ZoneId.systemDefault()).toOffsetDateTime());
                prog.setHasta(selectedEvent.getEndDate().atZone(ZoneId.systemDefault()).toOffsetDateTime());
                if(prog.getDesde().isBefore(prog.getHasta())) {
                    progBean.create(prog);
                    cargarProgramaciones();
                }else{
                    String mensaje = "La fecha final no puede ser menor que la inicial";
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", mensaje);

                    PrimeFaces.current().dialog().showMessageDynamic(message);
                }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public void btnGuardarHandler() {
        Programacion prog = selectedEvent.getData() != null ? selectedEvent.getData() : new Programacion();

        Sala sala = new Sala();
        sala.setIdSala(idSala);
        prog.setIdSala(sala);

        prog.setIdPelicula(selectedEvent.getData().getIdPelicula());
        prog.setDesde(selectedEvent.getStartDate().atZone(ZoneId.systemDefault()).toOffsetDateTime());
        prog.setHasta(selectedEvent.getEndDate().atZone(ZoneId.systemDefault()).toOffsetDateTime());

        if(prog.getDesde().isBefore(prog.getHasta())) {
            progBean.update(prog);
            cargarProgramaciones();
        }else{
            String mensaje = "La fecha final no puede ser menor que la inicial";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia", mensaje);

            PrimeFaces.current().dialog().showMessageDynamic(message);
        }

    }

    public void btnEliminarHandler() {
        if (selectedEvent != null) {
            Programacion prog = selectedEvent.getData();
            progBean.delete(prog.getIdProgramacion());
            cargarProgramaciones(); // Recargar los eventos después de eliminar
        }
    }

    public void onIdPeliculaSelect(SelectEvent<Pelicula> event) {
        this.selectedPelicula = event.getObject();
        obtenerFechaFin();
        System.out.println("Película seleccionada: " + selectedPelicula.getIdPelicula());
    }



    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
        cargarProgramaciones();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }


    public ScheduleEvent<Programacion> getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(ScheduleEvent<Programacion> selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ProgramacionBean getProgBean() {
        return progBean;
    }

    public void setProgBean(ProgramacionBean progBean) {
        this.progBean = progBean;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public Long getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Long idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    @Override
    protected String getRowKeyFromEntity(Programacion entity) {
        if (entity != null && entity.getIdSala()!= null) {
            return entity.getIdProgramacion().toString();
        }
        return null;
    }

    @Override
    protected Programacion getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return progBean.findById(Long.parseLong(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        try {
            if (idSala != null && progBean != null) {
                return progBean.countSala(this.idSala);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al contar los registros"));

        }
        return 0;
    }

    @Override
    protected List<Programacion> loadRegistros(int desde, int max) {
        try {
            if (this.idSala != null && progBean != null) {
                return  progBean.findByIdSala(this.idSala, desde, max);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los registros"));

        }
        return List.of();
    }

    @Override
    protected void createRegistro(Programacion registro) {
        progBean.create(registro);
    }

    @Override
    protected Programacion updateRegistro(Programacion registro) {
        return progBean.update(registro);
    }

    @Override
    protected void deleteRegistro(Programacion registro) {
        if (selectedEvent != null) {
            Programacion prog = selectedEvent.getData();
            progBean.delete(prog.getIdProgramacion());
            cargarProgramaciones(); // Recargar los eventos después de eliminar
        }
    }

    @Override
    protected Programacion createNewEntity() {
        Programacion prog = new Programacion();
        if (idSala != null) {
            prog.setIdSala(new Sala(idSala));
        }
        if (programacionList != null && !programacionList.isEmpty()) {
            prog.setIdProgramacion(programacionList.get(0).getIdProgramacion());
        }
        return prog;
    }

    @Override
    public Object getTituloDePagina() {
        return "Programacion";
    }

    public List<Programacion> getProgramacionList() {
        return programacionList;
    }

    public void setProgramacionList(List<Programacion> programacionList) {
        this.programacionList = programacionList;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public boolean isEventSelected() {
        return eventSelected;
    }

    public void setEventSelected(boolean eventSelected) {
        this.eventSelected = eventSelected;
    }

    public String getNombrePelicula() {
        return nombrePelicula = selectedEvent.getTitle();
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public Pelicula getSelectedPelicula() {
        return selectedPelicula;
    }


}






