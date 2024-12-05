package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.rest.server;

import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.io.Serializable;

public class TipoReservaResource extends AbstractResources<TipoReserva> implements Serializable {

    @Inject
    TipoReservaBean trBean;

    @Override
    public AbstractDataPersistence<TipoReserva> getBean() {return trBean;}

    @Override
    public Integer getId(TipoReserva entity) {return entity.getIdTipoReserva();}
}
