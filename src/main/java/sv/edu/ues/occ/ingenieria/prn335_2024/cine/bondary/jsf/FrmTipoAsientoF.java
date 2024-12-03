package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmTipoAsientoF extends FrmAbstractDataPersistF<TipoAsiento> implements Serializable {

    @Inject
    TipoAsientoBean taBean;

    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar(); // Llama al método inicializar del abstracto
    }

    @Override
    protected String getRowKeyFromEntity(TipoAsiento entity) {
        if (entity != null && entity.getIdTipoAsiento() != null) {
            return entity.getIdTipoAsiento().toString();
        }
        return null;
    }

    @Override
    protected TipoAsiento getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return taBean.findById(Integer.parseInt(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return taBean.count().intValue();
    }

    @Override
    protected List<TipoAsiento> loadRegistros(int desde, int max) {
        return taBean.findRange(desde, max);
    }

    @Override
    protected void createRegistro(TipoAsiento registro) {
        taBean.create(registro);
    }

    @Override
    protected TipoAsiento updateRegistro(TipoAsiento registro) {
        return taBean.update(registro);
    }

    @Override
    protected void deleteRegistro(TipoAsiento registro) {
        taBean.delete(registro);
    }

    @Override
    protected TipoAsiento createNewEntity() {
        TipoAsiento nuevoAsiento = new TipoAsiento();
        nuevoAsiento.setActivo(true);
        return nuevoAsiento;
    }

    public String getTituloDePagina() {
        return "Tipo de Asiento";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }
}