package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Reserva;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmReservaCliente extends FrmAbstractDataPersistF<Reserva> implements Serializable {

    @Inject
    ReservaBean rBean;
    @Inject
    TipoReservaBean trBean;
    @Inject
    FrmPeliculaF frmPelicula;
    @Inject
    ProgramacionBean programacionBean;


    Programacion programacionSeleccionada;
    List<TipoReserva> tipoReservaList;
    Long idReserva;
    List<Programacion> programacionList;


    @PostConstruct
    public void inicializar() {
        super.inicializar();
        try {
            this.programacionList = programacionBean.findAll();
            this.tipoReservaList = trBean.findRange(0, Integer.MAX_VALUE);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los tipos"));
        }
    }

    private int activeIndex = 0;

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public void siguientePaso() {
        activeIndex++;
    }

    public void anteriorPaso(){
        activeIndex--;
    }

    public String getProgramacionLabel(Programacion programacion) {
        if (programacion != null) {
            String nombrePelicula = programacion.getIdPelicula().getNombre();
            String sala = programacion.getIdSala().getNombre();
            String sucursal= programacion.getIdSala().getIdSucursal().getNombre();
            OffsetDateTime desde = programacion.getDesde();
            OffsetDateTime hasta = programacion.getHasta();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedDesde = desde.format(timeFormatter);
            String formattedHasta = hasta.format(timeFormatter);

            return nombrePelicula + "," + sala + "-" + sucursal+"("+formattedDesde+"-"+formattedHasta+")";
        }
        return "Programacion no disponible";
    }

    public Integer getIdTipoReservaSeleccionado() {
        if (this.registro != null && this.registro.getIdTipoReserva() != null) {
            return this.registro.getIdTipoReserva().getIdTipoReserva();
        }
        return null;
    }

    public void setIdTipoReservaSeleccionado(final Integer idTipoReserva) {
        if (this.registro != null && this.tipoReservaList != null && !this.tipoReservaList.isEmpty()) {
            this.registro.setIdTipoReserva(this.tipoReservaList.stream().filter(r -> r.getIdTipoReserva().equals(idTipoReserva)).findFirst().orElse(null));
        }
    }
    public void cambiarTab(TabChangeEvent tce) {
        if (tce.getTab().getTitle().equals("Fecha")) {
            this.registro.setIdReserva(this.registro.getIdReserva());
        }else if(tce.getTab().getTitle().equals("Funcion")){
            this.programacionList = programacionBean.findAll();
        }

    }
    public List<Programacion> completeProgramacion(String query) {
        List<Programacion> allProgramaciones = getProgramacionList();
        List<Programacion> filteredProgramaciones = new ArrayList<>();

        for (Programacion programacion : allProgramaciones) {
            if (programacion != null && programacion.getIdPelicula() != null &&
                    programacion.getIdPelicula().getNombre() != null &&
                    programacion.getIdPelicula().getNombre().toLowerCase().contains(query.toLowerCase())) {
                filteredProgramaciones.add(programacion);
            }
        }
        return filteredProgramaciones;
    }

    public Programacion getProgramacionSeleccionada() {
        return programacionSeleccionada;
    }

    public void setProgramacionSeleccionada(Programacion programacionSeleccionada) {
        this.programacionSeleccionada = programacionSeleccionada;
    }

    public ProgramacionBean getProgramacionBean() {
        return programacionBean;
    }

    public void setProgramacionBean(ProgramacionBean programacionBean) {
        this.programacionBean = programacionBean;
    }

    public List<Programacion> getProgramacionList() {
        return programacionList;
    }

    public void setProgramacionList(List<Programacion> programacionList) {
        this.programacionList = programacionList;
    }

    @Override
    protected String getRowKeyFromEntity(Reserva entity) {
        if (entity != null && entity.getIdReserva() != null) {
            return entity.getIdReserva().toString();
        }
        return null;
    }

    @Override
    protected Reserva getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return rBean.findById(Integer.parseInt(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return rBean.count().intValue();
    }

    @Override
    protected List<Reserva> loadRegistros(int desde, int max) {
        return rBean.findRange(desde,max);
    }

    @Override
    protected void createRegistro(Reserva registro) {
        rBean.create(registro);
    }

    @Override
    protected Reserva updateRegistro(Reserva registro) {
        return rBean.update(registro);
    }

    @Override
    protected void deleteRegistro(Reserva registro) {
        rBean.delete(registro);
    }

    @Override
    protected Reserva createNewEntity() {
        return new Reserva();
    }

    @Override
    public Object getTituloDePagina() {
        return "Reserva";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }

    public ReservaBean getrBean() {
        return rBean;
    }

    public void setrBean(ReservaBean rBean) {
        this.rBean = rBean;
    }

    public TipoReservaBean getTrBean() {
        return trBean;
    }

    public void setTrBean(TipoReservaBean trBean) {
        this.trBean = trBean;
    }

    public List<TipoReserva> getTipoReservaList() {
        return tipoReservaList;
    }

    public void setTipoReservaList(List<TipoReserva> tipoReservaList) {
        this.tipoReservaList = tipoReservaList;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public FrmPeliculaF getFrmPelicula() {
        return frmPelicula;
    }

    public void setFrmPelicula(FrmPeliculaF frmPelicula) {
        this.frmPelicula = frmPelicula;
    }

    // Método que se ejecuta cuando se selecciona un elemento en el autoComplete
    public void onPeliculaSelect(ValueChangeEvent event) {
        Programacion nuevaSeleccion = (Programacion) event.getNewValue();
        if (nuevaSeleccion != null) {
            // Actualizamos el registro con la programación seleccionada
            this.registro.setIdProgramacion(nuevaSeleccion);
            // Asegúrate de que 'idProgramacion' sea el campo correcto en la entidad 'Reserva'
            // Realiza cualquier otra lógica adicional si es necesario
            System.out.println("Película seleccionada: " + nuevaSeleccion.getIdPelicula().getNombre());
        }
    }
    // Método para manejar la selección
    public void onProgramacionSelect(SelectEvent<Programacion> event) {
        this.programacionSeleccionada = event.getObject();
        // Puedes agregar más lógica si es necesario
    }

}
