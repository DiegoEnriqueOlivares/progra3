package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;

public abstract class FrmAbstractDataPersist<T> implements Serializable {
    @Inject
    FacesContext facesContext;
    protected ESTADO_CRUD estado;
    protected List<T> registros;
    protected T registro;

    @PostConstruct
    public void init(){
        estado = ESTADO_CRUD.NINGUNO;
        registros = readRegistros();
    }

    protected abstract void createRegistro(T registro);
    protected abstract List<T> readRegistros();
    protected abstract void updateRegistro(T registro);
    protected abstract void deleteRegistro(T registro);

    protected abstract Integer getId(T registro);
    protected abstract T createNewRegistro();

    public void btnSeleccionarRegistroHandler(final Integer id){
        if(id !=null){
            this.registro = registros.stream().filter(r -> id.equals(getId(r))).findFirst().orElse(null);
            this.estado =ESTADO_CRUD.MODIFICAR;
            return;
        }
        this.registro=null;
    }
    public void btnCancelarRegistroHandler(){
        this.registro=null;
        this.estado=ESTADO_CRUD.NINGUNO;
    }
    public void btnNuevoRegistroHandler(){
        this.registro=createNewRegistro();
        this.estado=ESTADO_CRUD.CREAR;
    }
    public void btnGuardarRegistroHandler(){
        if(estado == ESTADO_CRUD.CREAR){
            createRegistro(registro);
        }
        reset();
    }
    public void btnModificarRegistroHandler(){
        if(estado == ESTADO_CRUD.MODIFICAR){
            updateRegistro(registro);
        }
        reset();
    }
    public void btnEliminarRegistroHandler(){
        deleteRegistro(registro);
        reset();
    }
    protected void reset() {
        this.registro = null;
        this.estado = ESTADO_CRUD.NINGUNO;
        this.registros = readRegistros();
    }

    public List<T> getRegistros(){
        return registros;
    }
    public T getRegistro(){
        return registro;
    }
    public void setRegistro(T registro) {
        this.registro = registro;
    }
    public ESTADO_CRUD getEstado() {
        return estado;
    }
}
