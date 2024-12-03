package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmTipoPagoF extends FrmAbstractDataPersistF<TipoPago> implements Serializable {

    @Inject
    TipoPagoBean tpBean;

    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
    }

    @Override
    protected String getRowKeyFromEntity(TipoPago entity) {
        if (entity != null && entity.getIdTipoPago() != null) {
            return entity.getIdTipoPago().toString();
        }
        return null;
    }

    @Override
    protected TipoPago getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return tpBean.findById(Integer.parseInt(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return tpBean.count().intValue();
    }

    @Override
    protected List<TipoPago> loadRegistros(int desde, int max) {
        return tpBean.findRange(desde, max);
    }

    @Override
    protected void createRegistro(TipoPago registro) {
        tpBean.create(registro);
    }

    @Override
    protected TipoPago updateRegistro(TipoPago registro) {
        return tpBean.update(registro);
    }

    @Override
    protected void deleteRegistro(TipoPago registro) {
        tpBean.delete(registro);
    }

    @Override
    protected TipoPago createNewEntity() {
        TipoPago nuevoPago = new TipoPago();
        nuevoPago.setActivo(true);
        return nuevoPago;
    }

    @Override
    public Object getTituloDePagina() {
        return "Tipo de pago";
    }

    @Override
    public FacesContext getFacesContext() {
        return null;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }
}