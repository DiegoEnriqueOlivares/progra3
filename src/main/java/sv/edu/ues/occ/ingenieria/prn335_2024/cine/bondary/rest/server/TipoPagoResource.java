package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.rest.server;

import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;

import java.io.Serializable;

public class TipoPagoResource extends AbstractResources<TipoPago> implements Serializable {

    @Inject
    TipoPagoBean tpaBean;


    @Override
    public AbstractDataPersistence<TipoPago> getBean() {return tpaBean;}

    @Override
    public Integer getId(TipoPago entity) {return entity.getIdTipoPago();}

}
