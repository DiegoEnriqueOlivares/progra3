package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Dependent
public class FrmSalaCaracteristicaF extends FrmAbstractDataPersistF<SalaCaracteristica> implements Serializable {

    @Inject
    SalaCaracteristicaBean scBean;
    @Inject
    FacesContext facesContext;
    @Inject
    TipoSalaBean tsBean;

    List<TipoSala> tipoSalaList;
    Integer idSala;

    @PostConstruct
    @Override
    public void inicializar(){
        super.inicializar();
        try {

            this.tipoSalaList = tsBean.findRange(0, Integer.MAX_VALUE);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los tipos"));
        }
    }

    @Override
    protected String getRowKeyFromEntity(SalaCaracteristica entity) {
        if (entity != null && entity.getIdTipoSala()!= null) {
            return entity.getIdSalaCaracteristica().toString();
        }
        return null;
    }

    @Override
    protected SalaCaracteristica getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return scBean.findById(Long.parseLong(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        try {
            if (idSala != null && scBean != null) {
                return scBean.countSala(this.idSala);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al contar los registros"));

        }
        return 0;
    }

    @Override
    protected List<SalaCaracteristica> loadRegistros(int desde, int max) {
        try {
            if (this.idSala != null && scBean != null) {
                return  scBean.findByIdSala(this.idSala, desde, max);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los registros"));

        }
        return List.of();
    }

    @Override
    protected void createRegistro(SalaCaracteristica registro) {
        scBean.create(registro);
    }

    @Override
    protected SalaCaracteristica updateRegistro(SalaCaracteristica registro) {
       return scBean.update(registro);
    }

    @Override
    protected void deleteRegistro(SalaCaracteristica registro) {
        scBean.delete(registro);
    }

    @Override
    protected SalaCaracteristica createNewEntity() {
        SalaCaracteristica sc = new SalaCaracteristica();
        if (idSala != null) {
            sc.setIdSala(new Sala(idSala));
        }
        if (tipoSalaList != null && !tipoSalaList.isEmpty()) {
            sc.setIdTipoSala(tipoSalaList.get(0));
        }
        return sc;
    }

    @Override
    public Object getTituloDePagina() {
        return "Tipo de Sala";
    }

    public SalaCaracteristicaBean getScBean() {
        return scBean;
    }

    public void setScBean(SalaCaracteristicaBean scBean) {
        this.scBean = scBean;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public TipoSalaBean getTsBean() {
        return tsBean;
    }

    public void setTsBean(TipoSalaBean tsBean) {
        this.tsBean = tsBean;
    }

    public List<TipoSala> getTipoSalaList() {
        return tipoSalaList;
    }

    public void setTipoSalaList(List<TipoSala> tipoSalaList) {
        this.tipoSalaList = tipoSalaList;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public Integer getIdTipoSalaSeleccionado() {
        if (this.registro != null && this.registro.getIdTipoSala() != null) {
            return this.registro.getIdTipoSala().getIdTipoSala();
        }
        return null;
    }

    public void setIdTipoSalaSeleccionado(final Integer idTipoSala) {
        if (this.registro != null && this.tipoSalaList != null && !this.tipoSalaList.isEmpty()) {
            this.registro.setIdTipoSala(this.tipoSalaList.stream().filter(r -> r.getIdTipoSala().equals(idTipoSala)).findFirst().orElse(null));
        }
    }

    public void validarValor(FacesContext facesContext, UIComponent componente, Object valor) {
        UIInput input = (UIInput) componente;

        // Verificar si el registro y el tipo de película son válidos
        if (registro != null && this.registro.getIdTipoSala() != null) {
            String nuevoValor = valor.toString();
            String expresionRegular = this.registro.getIdTipoSala().getExpresionRegular();
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
                // Mensaje para tipos de películas específicas (por ejemplo, idTipoPelicula == 1)
                if (registro.getIdTipoSala() != null && registro.getIdTipoSala().getNombre().equals("Clima")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "Aire Acondicionado,Climatizada,Fresco,\nTemplado,Frío,Calor");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                } else if (registro.getIdTipoSala() != null && registro.getIdTipoSala().getNombre().equals("Calidad")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "IMAX,4DX,3D,2D,\nVIP,Premium,Estándar,Lujo,\nDolby Atmos,ScreenX");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                }



            }
        }


    }
}