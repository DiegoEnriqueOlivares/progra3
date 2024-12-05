package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.rest.server;

import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;

public class TipoAsientoResource extends AbstractResources<TipoAsiento> implements Serializable {

    @Inject
    TipoAsientoBean taBean;


    @Override
    public AbstractDataPersistence<TipoAsiento> getBean() {return taBean;}

    @Override
    public Integer getId(TipoAsiento entity) {return entity.getIdTipoAsiento();}
}
