package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmTipoSalaF extends FrmAbstractDataPersistF<TipoSala> implements Serializable {

    @Inject
    TipoSalaBean tsBean;

    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
    }

    @Override
    protected String getRowKeyFromEntity(TipoSala entity) {
        if (entity != null && entity.getIdTipoSala() != null) {
            return entity.getIdTipoSala().toString();
        }
        return null;
    }

    @Override
    protected TipoSala getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return tsBean.findById(Integer.parseInt(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return tsBean.count().intValue();
    }

    @Override
    protected List<TipoSala> loadRegistros(int desde, int max) {
        return tsBean.findRange(desde, max);
    }

    @Override
    protected void createRegistro(TipoSala registro) {
        tsBean.create(registro);
    }

    @Override
    protected TipoSala updateRegistro(TipoSala registro) {
        return tsBean.update(registro);
    }

    @Override
    protected void deleteRegistro(TipoSala registro) {
        tsBean.delete(registro);
    }

    @Override
    public Object getTituloDePagina() {
        return "Tipo de Sala";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }

    @Override
    protected TipoSala createNewEntity() {
        TipoSala nuevaSala = new TipoSala();
        nuevaSala.setActivo(true);
        return nuevaSala;
    }
}