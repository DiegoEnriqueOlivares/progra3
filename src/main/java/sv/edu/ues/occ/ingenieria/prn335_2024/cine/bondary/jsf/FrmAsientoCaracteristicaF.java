package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.el.MethodExpression;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.*;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Dependent
public class FrmAsientoCaracteristicaF extends FrmAbstractDataPersistF<AsientoCaracteristica> implements Serializable {

    @Inject
    AsientoCaracteristicaBean acBean;
    @Inject
    TipoAsientoBean taBean;
    @Inject
    FacesContext facesContext;


    List<TipoAsiento> tipoAsientoslist;
    Long idAsiento;
    TipoAsiento selectedTipoAsiento;

    @PostConstruct
    @Override
    public void inicializar(){
        super.inicializar();
        try {
            this.tipoAsientoslist = taBean.findRange(0, Integer.MAX_VALUE);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los tipos"));
        }
    }


    @Override
    protected String getRowKeyFromEntity(AsientoCaracteristica entity) {
        if (entity != null && entity.getIdTipoAsiento()!= null) {
            return entity.getIdAsientoCaracteristica().toString();
        }
        return null;
    }

    @Override
    protected AsientoCaracteristica getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return acBean.findById(Long.parseLong(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        try {
            if (idAsiento != null && acBean != null) {
                return acBean.countAsiento(this.idAsiento);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al contar los registros"));

        }
        return 0;
    }

    @Override
    protected List<AsientoCaracteristica> loadRegistros(int desde, int max) {
        try {
            if (this.idAsiento != null && acBean != null) {
                return  acBean.findByIdAsiento(this.idAsiento, desde, max);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los registros"));

        }
        return List.of();
    }

    @Override
    protected void createRegistro(AsientoCaracteristica registro) {
        acBean.create(registro);
    }

    @Override
    protected AsientoCaracteristica updateRegistro(AsientoCaracteristica registro) {
        return acBean.update(registro);
    }

    @Override
    protected void deleteRegistro(AsientoCaracteristica registro) {
        acBean.delete(registro);
    }

    @Override
    protected AsientoCaracteristica createNewEntity() {
        AsientoCaracteristica ac = new AsientoCaracteristica();

        // Verificamos si idAsiento no es nulo y le asignamos un objeto Asiento con dicho ID
        if (idAsiento != null) {
            Asiento asiento = new Asiento();
            asiento.setIdAsiento(idAsiento);
            ac.setIdAsiento(asiento);
        }

        // Asignamos el primer elemento de tipoAsientoslist si está disponible
        if (tipoAsientoslist != null && !tipoAsientoslist.isEmpty()) {
            ac.setIdTipoAsiento(tipoAsientoslist.get(0));
        }

        return ac;
    }


    @Override
    public Object getTituloDePagina() {
        return "Tipo de Asiento";
    }

    public Integer getIdTipoAsientoSeleccionado() {
        if (this.registro != null && this.registro.getIdAsiento() != null) {
            return this.registro.getIdTipoAsiento().getIdTipoAsiento();
        }
        return null;
    }

    public void setIdTipoAsientoSeleccionado(final Integer idTipoAsiento) {
        if (this.registro != null && this.tipoAsientoslist != null && !this.tipoAsientoslist.isEmpty()) {
            this.registro.setIdTipoAsiento(this.tipoAsientoslist.stream().filter(r -> r.getIdTipoAsiento().equals(idTipoAsiento)).findFirst().orElse(null));
        }
    }



    public void validarValor(FacesContext facesContext, UIComponent componente, Object valor) {
        UIInput input = (UIInput) componente;

        // Verificar si el registro y el tipo de película son válidos
        if (registro != null && this.registro.getIdTipoAsiento() != null) {
            String nuevoValor = valor.toString();
            String expresionRegular = this.registro.getIdTipoAsiento().getExpresionRegular();
            Pattern patron = Pattern.compile(expresionRegular);
            Matcher validador = patron.matcher(nuevoValor);

            // Validar según la expresión regular definida
            if (validador.matches()) {
                input.setValid(true);
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Validación exitosa", "El valor ingresado es correcto"));
                return;
            } else {
                // Si no cumple con la expresión regular
                input.setValid(false);
                if (registro.getIdTipoAsiento() != null && registro.getIdTipoAsiento().getNombre().equals("CLIMA")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "Aire Acondicionado,Calefaccion,\nVentilacion,Climatizado");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                } else if (registro.getIdTipoAsiento() != null && registro.getIdTipoAsiento().getNombre().equals("ERGONOMIA")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado","Ergonomico,Comodo,Ajustable,\nRespaldo Lumbar,Reposabrazos");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                } else if (registro.getIdTipoAsiento() != null && registro.getIdTipoAsiento().getNombre().equals("ACCESIBILIDAD")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "Rampas,Ascensor,Espacio,\nAcesible,Silla de ruedas");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                }


            }
        }


    }
    public AsientoCaracteristicaBean getAcBean() {
        return acBean;
    }

    public void setAcBean(AsientoCaracteristicaBean acBean) {
        this.acBean = acBean;
    }

    public TipoAsientoBean getTaBean() {
        return taBean;
    }

    public void setTaBean(TipoAsientoBean taBean) {
        this.taBean = taBean;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }


    public List<TipoAsiento> getTipoAsientoslist() {
        return tipoAsientoslist;
    }

    public void setTipoAsientoslist(List<TipoAsiento> tipoAsientoslist) {
        this.tipoAsientoslist = tipoAsientoslist;
    }

    public void setIdAsiento(Long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Long getIdAsiento() {
        return idAsiento;
    }

    public TipoAsiento getSelectedTipoAsiento() {
        return selectedTipoAsiento;
    }

}
