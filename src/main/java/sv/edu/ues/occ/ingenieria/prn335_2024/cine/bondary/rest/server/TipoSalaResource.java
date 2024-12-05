package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.rest.server;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Max;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("tiposala")
public class TipoSalaResource extends AbstractResources<TipoSala> implements Serializable {

    @Inject
    TipoSalaBean tsBean;

    @Override
    public AbstractDataPersistence<TipoSala> getBean() {
        return tsBean;
    }

    @Override
    public Integer getId(TipoSala entity) {
        return entity.getIdTipoSala();
    }
}