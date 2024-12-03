package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class FrmAbstractDataPersistF<T> implements Serializable {
    @Inject
    protected FacesContext facesContext;
    protected ESTADO_CRUD estado;
    protected LazyDataModel<T> modelo;
    protected T registro;

    @PostConstruct
    public void inicializar() {
        estado = ESTADO_CRUD.NINGUNO;
        modelo = new LazyDataModel<T>() {
            @Override
            public String getRowKey(T object) {
                try {
                    return getRowKeyFromEntity(object);
                } catch (Exception e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo obtener la clave de fila.");
                    facesContext.addMessage(null, message);
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public T getRowData(String rowKey) {
                try {
                    T entity = getRowDataFromKey(rowKey);
                    if (entity != null) {
                        registro = entity;
                        estado = ESTADO_CRUD.MODIFICAR;
                    }
                    return entity;
                } catch (Exception e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo obtener el registro de datos.");
                    facesContext.addMessage(null, message);
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    return countRegistros();
                } catch (Exception e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo contar los registros.");
                    facesContext.addMessage(null, message);
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            public List<T> load(int desde, int max, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
                try {
                    // Validar que los parámetros de paginación sean correctos
                    if (desde >= 0 && max > 0) {
                        return loadRegistros(desde, max);
                    } else {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Parámetros de paginación inválidos.");
                        facesContext.addMessage(null, message);
                    }
                } catch (Exception e) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudieron cargar los datos.");
                    facesContext.addMessage(null, message);
                    e.printStackTrace();
                }
                return List.of();
            }
        };
    }


    protected abstract String getRowKeyFromEntity(T entity);

    protected abstract T getRowDataFromKey(String rowKey);

    protected abstract int countRegistros();

    protected abstract List<T> loadRegistros(int desde, int max);

    protected abstract void createRegistro(T registro);

    protected abstract T updateRegistro(T registro);

    protected abstract void deleteRegistro(T registro);


    public void btnCrearHandler() {
        try {
            createRegistro(registro);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito",
                    "Registro de " + registro.getClass().getSimpleName() + " creado con éxito."));
            reset();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "Error al crear el registro de " + registro.getClass().getSimpleName() + "."));
            e.printStackTrace();
        }
    }

    public void btnModificarHandler() {
        try {
            T actualizado = updateRegistro(registro);
            if (actualizado != null) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro modificado con éxito."));
                reset();
            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo modificar el registro."));
            }
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al modificar el registro."));
            e.printStackTrace();
        }
    }

    public void btnEliminarHandler() {
        try {
            deleteRegistro(registro);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro eliminado con éxito."));
            reset();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el registro."));
            e.printStackTrace();
        }
    }

    public void btnCancelarHandler() {
        reset();
    }

    public void btnNuevoHandler() {
        registro = createNewEntity();
        estado = ESTADO_CRUD.CREAR;
    }

    protected void reset() {
        registro = null;
        estado = ESTADO_CRUD.NINGUNO;
    }

    public LazyDataModel<T> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<T> modelo) {
        this.modelo = modelo;
    }

    public T getRegistro() {
        return registro;
    }

    public void setRegistro(T registro) {
        this.registro = registro;
    }

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    protected abstract T createNewEntity();

    public abstract Object getTituloDePagina();

    public abstract FacesContext getFacesContext();

    public abstract void setFacesContext(FacesContext facesContext);
}
