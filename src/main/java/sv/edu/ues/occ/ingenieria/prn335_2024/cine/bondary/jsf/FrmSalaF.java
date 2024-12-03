package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmSalaF extends FrmAbstractDataPersistF<Sala> implements Serializable {

    @Inject
    SalaBean salaBean;
    @Inject
    AsientoCaracteristicaBean acBean;
    @Inject
    FrmSalaCaracteristicaF frmSalaCaracteristicaF;
    @Inject
    FrmAsientoF frmAsientoF;
    @Inject
    FrmProgramacion frmProgramacion;
    @Inject
    FrmSucursal frmSucursal;
    @Inject
    SucursalBean sucursalBean;

    List<Sucursal> sucursalList;
    Long idPelicula;
    Integer idSucursal;
    Long idAsiento;
    @PostConstruct
    public void init() {
        super.inicializar();
        try{
            this.sucursalList = sucursalBean.findRange(0, Integer.MAX_VALUE);
        }catch (Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar."));
        }
    }

    @Override
    protected String getRowKeyFromEntity(Sala entity) {
        if (entity != null && entity.getIdSala() != null) {
            return entity.getIdSala().toString();
        }
        return null;
    }

    @Override
    protected Sala getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return salaBean.findById(Integer.parseInt(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return salaBean.count().intValue();
    }

    @Override
    protected List<Sala> loadRegistros(int desde, int max) {
        return salaBean.findRange(desde,max);
    }

    @Override
    protected void createRegistro(Sala registro) {
        salaBean.create(registro);
    }

    @Override
    protected Sala updateRegistro(Sala registro) {
        return salaBean.update(registro);
    }

    @Override
    protected void deleteRegistro(Sala registro) {
        salaBean.delete(registro);
    }

    @Override
    protected Sala createNewEntity() {
        Sala sala=new Sala();
        sala.setActivo(true);
        return sala;
    }

    @Override
    public Object getTituloDePagina() {
        return "Sala";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }
    public void onAsientoSelect(SelectEvent<?> event) {
        Asiento asientoSeleccionado = (Asiento) event.getObject();
        if (asientoSeleccionado != null) {
            this.idAsiento = asientoSeleccionado.getIdAsiento();
            System.out.println("ID del asiento seleccionado: " + this.idAsiento);

            this.frmAsientoF.frmAsientoCaracteristicaF.setIdAsiento(idAsiento);
        }
    }


    public void cambiarTab(TabChangeEvent tce) {
        if (tce.getTab().getTitle().equals("Caracteristica")) {
            if (this.registro != null && this.frmSalaCaracteristicaF != null) {
                this.frmSalaCaracteristicaF.setIdSala(this.registro.getIdSala());
            }
        } else if (tce.getTab().getTitle().equals("Asientos")) {
            if (this.registro != null && this.frmAsientoF != null) {
                this.frmAsientoF.setIdSala(this.registro.getIdSala());
            }
        }else if(tce.getTab().getTitle().equals("Programación")){
            if(this.registro != null && this.frmProgramacion != null){
                this.frmProgramacion.setIdSala(this.registro.getIdSala());
            }
        }
    }

    @Override
    public LazyDataModel<Sala> getModelo() {
        return super.getModelo();
    }

    @Override
    public void setModelo(LazyDataModel<Sala> modelo) {
        super.setModelo(modelo);
    }

    public SalaBean getSalaBean() {
        return salaBean;
    }

    public void setSalaBean(SalaBean salaBean) {
        this.salaBean = salaBean;
    }

    public FrmSalaCaracteristicaF getFrmSalaCaracteristicaF() {
        return frmSalaCaracteristicaF;
    }
    public FrmAsientoF getFrmAsientoF(){
        return frmAsientoF;
    }

    public FrmProgramacion getFrmProgramacion() {return frmProgramacion;}

    public FrmSucursal getFrmSucursal() {return frmSucursal;}

    public List<Sucursal> getSucursalList() {return sucursalList;}

    public void setSucursalList(List<Sucursal> sucursalList) {this.sucursalList = sucursalList;}

    public Integer getIdSucursalSeleccionado() {
        if (this.registro != null && this.registro.getIdSucursal() != null) {
            return this.registro.getIdSucursal().getIdSucursal();
        }
        return null;
    }

    public void setIdSucursalSeleccionado(final Integer idSucursal) {
        if (this.registro != null && this.sucursalList != null && !this.sucursalList.isEmpty()) {
            // Buscar la sucursal por ID en la lista
            this.registro.setIdSucursal(this.sucursalList.stream().filter(s -> s.getIdSucursal().equals(idSucursal)).findFirst().orElse(null));
        }
    }

    public void setFrmSalaCaracteristicaF(FrmSalaCaracteristicaF frmSalaCaracteristicaF) {
        this.frmSalaCaracteristicaF = frmSalaCaracteristicaF;
    }

    public void setFrmAsientoF(FrmAsientoF frmAsientoF) {
        this.frmAsientoF = frmAsientoF;
    }

    public void setFrmProgramacion(FrmProgramacion frmProgramacion) {
        this.frmProgramacion = frmProgramacion;
    }

    public void setFrmSucursal(FrmSucursal frmSucursal) {
        this.frmSucursal = frmSucursal;
    }

    public SucursalBean getSucursalBean() {
        return sucursalBean;
    }

    public void setSucursalBean(SucursalBean sucursalBean) {
        this.sucursalBean = sucursalBean;
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public AsientoCaracteristicaBean getAcBean() {
        return acBean;
    }

    public void setAcBean(AsientoCaracteristicaBean acBean) {
        this.acBean = acBean;
    }

}