package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.rest.server;

import jakarta.inject.Inject;
import org.eclipse.persistence.jpa.rs.resources.common.AbstractResource;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;

public class TipoProductoResource extends AbstractResources<TipoProducto> implements Serializable {

    @Inject
    TipoProductoBean tpBean;


    @Override
    public AbstractDataPersistence<TipoProducto> getBean() {return tpBean;}

    @Override
    public Integer getId(TipoProducto entity) {return entity.getIdTipoProducto();}
}
