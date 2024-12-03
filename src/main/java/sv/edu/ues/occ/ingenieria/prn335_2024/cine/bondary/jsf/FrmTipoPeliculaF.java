package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmTipoPeliculaF extends FrmAbstractDataPersistF<TipoPelicula> implements Serializable {

    @Inject
    TipoPeliculaBean tpeBean;

    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
    }

    @Override
    protected String getRowKeyFromEntity(TipoPelicula entity) {
        if (entity != null && entity.getIdTipoPelicula() != null) {
            return entity.getIdTipoPelicula().toString();
        }
        return null;
    }

    @Override
    protected TipoPelicula getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return tpeBean.findById(Integer.parseInt(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return tpeBean.count().intValue();
    }

    @Override
    protected List<TipoPelicula> loadRegistros(int desde, int max) {
        return tpeBean.findRange(desde, max);
    }

    @Override
    protected void createRegistro(TipoPelicula registro) {
        tpeBean.create(registro);
    }

    @Override
    protected TipoPelicula updateRegistro(TipoPelicula registro) {
        return tpeBean.update(registro);
    }

    @Override
    protected void deleteRegistro(TipoPelicula registro) {
        tpeBean.delete(registro);
    }

    @Override
    public Object getTituloDePagina() {
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
    protected TipoPelicula createNewEntity() {
        TipoPelicula nuevaPelicula = new TipoPelicula();
        nuevaPelicula.setActivo(true);
        return nuevaPelicula;
    }

}