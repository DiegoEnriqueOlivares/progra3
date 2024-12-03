package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class FrmPeliculaF extends FrmAbstractDataPersistF<Pelicula> implements Serializable {
    @Inject
    PeliculaBean dataBean;

    @Inject
    FrmPeliculaCaracteristicaF frmPeliculaCaracteristica;

     String valor;
    List<Pelicula> peliculaList;


    @PostConstruct
    public void inicializar() {
        super.inicializar();
        try {
            this.peliculaList = dataBean.findRange(0,Integer.MAX_VALUE);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar", "Error al cargar los tipos"));
        }
    }

    public void setFrmPeliculaCaracteristica(FrmPeliculaCaracteristicaF frmPeliculaCaracteristica) {
        this.frmPeliculaCaracteristica = frmPeliculaCaracteristica;
    }

    public List<Pelicula> getPeliculaList() {
        return peliculaList;
    }

    public void setPeliculaList(List<Pelicula> peliculaList) {
        this.peliculaList = peliculaList;
    }

    public List<Pelicula> completePelicula(String query) {
        List<Pelicula> allPeliculas = getPeliculaList();
        List<Pelicula> filteredPeliculas = new ArrayList<>();

        for (Pelicula pelicula : allPeliculas) {
            if (pelicula != null && pelicula.getIdPelicula() != null &&
                    pelicula.getNombre() != null &&
                    pelicula.getNombre().toLowerCase().contains(query.toLowerCase())) {
                filteredPeliculas.add(pelicula);
            }
        }
        return filteredPeliculas;
    }

    public void cambiarTab(TabChangeEvent tce) {
        if (tce.getTab().getTitle().equals("Caracteristicas")) {
            if (this.registro!=null && this.frmPeliculaCaracteristica!=null) {
                this.frmPeliculaCaracteristica.setIdPelicula(this.registro.getIdPelicula());
            }
        }

    }
    public String getTituloDePagina() {
        return "Pelicula";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }

    @Override
    protected String getRowKeyFromEntity(Pelicula entity) {
        if (entity != null && entity.getIdPelicula() != null) {
            return entity.getIdPelicula().toString();
        }
        return null;
    }

    @Override
    protected Pelicula getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return dataBean.findById(Long.parseLong(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return dataBean.count().intValue();
    }

    @Override
    protected List<Pelicula> loadRegistros(int desde, int max) {
        return dataBean.findRange(desde, max);
    }

    @Override
    protected void createRegistro(Pelicula registro) {
        dataBean.create(registro);
    }

    @Override
    protected Pelicula updateRegistro(Pelicula registro) {
        return dataBean.update(registro);
    }

    @Override
    protected void deleteRegistro(Pelicula registro) {
        dataBean.delete(registro);
    }


    @Override
    protected Pelicula createNewEntity() {
        return new Pelicula();
    }

    public PeliculaBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(PeliculaBean dataBean) {
        this.dataBean = dataBean;
    }

    @Override
    public LazyDataModel<Pelicula> getModelo() {
        return super.getModelo();
    }

    @Override
    public void setModelo(LazyDataModel<Pelicula> modelo) {
        super.setModelo(modelo);
    }

    public FrmPeliculaCaracteristicaF getFrmPeliculaCaracteristica() {
        return frmPeliculaCaracteristica;
    }

}
