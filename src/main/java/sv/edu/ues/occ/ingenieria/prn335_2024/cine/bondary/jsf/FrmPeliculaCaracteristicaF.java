package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Dependent
public class FrmPeliculaCaracteristicaF extends FrmAbstractDataPersistF<PeliculaCaracteristica> implements Serializable {

    @Inject
    PeliculaCaracteristicaBean pcBean;
    @Inject
    FacesContext facesContext;
    @Inject
    TipoPeliculaBean tpBean;


    List<TipoPelicula> tipoPeliculaList;
    Long idPelicula;

    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
        try {
            this.tipoPeliculaList = tpBean.findRange(0, Integer.MAX_VALUE);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los tipos"));
        }
    }

    public String getTituloDePagina() {
        return "Tipo de Pelicula";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }

    @Override
    protected String getRowKeyFromEntity(PeliculaCaracteristica entity) {
        if (entity != null && entity.getIdTipoPelicula()!= null) {
            return entity.getIdPeliculaCaracteristica().toString();
        }
        return null;
    }

    @Override
    protected PeliculaCaracteristica getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return pcBean.findById(Long.parseLong(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        try {
            if (idPelicula != null && pcBean != null) {
                return pcBean.countPelicula(this.idPelicula);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al contar los registros"));

        }
        return 0;
    }

    @Override
    protected List<PeliculaCaracteristica> loadRegistros(int desde, int max) {
        try {
            if (this.idPelicula != null && pcBean != null) {
                return  pcBean.findByIdPelicula(this.idPelicula, desde, max);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los registros"));

        }
        return List.of();
    }

    @Override
    protected void createRegistro(PeliculaCaracteristica registro) {
        pcBean.create(registro);
    }

    @Override
    protected PeliculaCaracteristica updateRegistro(PeliculaCaracteristica registro) {
        return pcBean.update(registro);
    }

    @Override
    protected void deleteRegistro(PeliculaCaracteristica registro) {
        pcBean.delete(registro);
    }

    @Override
    protected PeliculaCaracteristica createNewEntity() {
        PeliculaCaracteristica pc = new PeliculaCaracteristica();
        if (idPelicula != null) {
            pc.setIdPelicula(new Pelicula(idPelicula));
        }
        if (tipoPeliculaList != null && !tipoPeliculaList.isEmpty()) {
            pc.setIdTipoPelicula(tipoPeliculaList.getFirst());
        }
        return pc;
    }

    public PeliculaCaracteristicaBean getPcBean() {
        return pcBean;
    }

    public void setPcBean(PeliculaCaracteristicaBean pcBean) {
        this.pcBean = pcBean;
    }

    public TipoPeliculaBean getTpBean() {
        return tpBean;
    }

    public void setTpBean(TipoPeliculaBean tpBean) {
        this.tpBean = tpBean;
    }

    public List<TipoPelicula> getTipoPeliculaList() {
        return tipoPeliculaList;
    }

    public void setTipoPeliculaList(List<TipoPelicula> tipoPeliculaList) {
        this.tipoPeliculaList = tipoPeliculaList;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Integer getIdTipoPeliculaSeleccionado() {
        if (this.registro != null && this.registro.getIdTipoPelicula() != null) {
            return this.registro.getIdTipoPelicula().getIdTipoPelicula();
        }
        return null;
    }

    public void setIdTipoPeliculaSeleccionado(final Integer idTipoPelicula) {
        if (this.registro != null && this.tipoPeliculaList != null && !this.tipoPeliculaList.isEmpty()) {
            this.registro.setIdTipoPelicula(this.tipoPeliculaList.stream().filter(r -> r.getIdTipoPelicula().equals(idTipoPelicula)).findFirst().orElse(null));
        }
    }

    public void validarValor(FacesContext facesContext, UIComponent componente, Object valor) {
        UIInput input = (UIInput) componente;

        // Verificar si el registro y el tipo de película son válidos
        if (registro != null && this.registro.getIdTipoPelicula() != null) {
            String nuevoValor = valor.toString();
            String expresionRegular = this.registro.getIdTipoPelicula().getExpresionRegular();
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
                if (registro.getIdTipoPelicula() != null && registro.getIdTipoPelicula().getNombre().equals("CLASIFICACION")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "Use G, PG, PG-13, R, NC-17");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                } else if (registro.getIdTipoPelicula() != null && registro.getIdTipoPelicula().getNombre().equals("GENERO")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "Use Acción,Aventura,Animación\n,Comedia,Crimen,Drama,\nFamiliar,Fantasía,Historia\nTerror,Misterio,Romance\nCiencia ficción,Suspenso");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                } else if (registro.getIdTipoPelicula() != null && registro.getIdTipoPelicula().getNombre().equals("DURACION")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "Use [0-9]");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                }


            }
        }


    }
}
