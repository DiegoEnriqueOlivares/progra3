package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class FrmTipoReservaF extends FrmAbstractDataPersistF<TipoReserva> implements Serializable {

    @Inject
    TipoReservaBean trBean;

    @PostConstruct
    @Override
    public void inicializar() {
        super.inicializar();
    }

    @Override
    protected String getRowKeyFromEntity(TipoReserva entity) {
        if (entity != null && entity.getIdTipoReserva() != null) {
            return entity.getIdTipoReserva().toString();
        }
        return null;
    }

    @Override
    protected TipoReserva getRowDataFromKey(String rowKey) {
        if (rowKey != null) {
            return trBean.findById(Integer.parseInt(rowKey));
        }
        return null;
    }

    @Override
    protected int countRegistros() {
        return trBean.count().intValue();
    }

    @Override
    protected List<TipoReserva> loadRegistros(int desde, int max) {
        return trBean.findRange(desde, max);
    }

    @Override
    protected void createRegistro(TipoReserva registro) {
        trBean.create(registro);
    }

    @Override
    protected TipoReserva updateRegistro(TipoReserva registro) {
        return trBean.update(registro);
    }

    @Override
    protected void deleteRegistro(TipoReserva registro) {
        trBean.delete(registro);
    }

    @Override
    public Object getTituloDePagina() {
        return "Tipo Reserva";
    }

    @Override
    public FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void setFacesContext(FacesContext facesContext) {

    }

    @Override
    protected TipoReserva createNewEntity() {
        TipoReserva nuevaReserva = new TipoReserva();
        nuevaReserva.setActivo(true);
        return nuevaReserva;
    }
}