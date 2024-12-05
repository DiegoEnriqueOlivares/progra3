package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.rest.server;

import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;

public class TipoPeliculaResouces extends AbstractResources<TipoPelicula> implements Serializable {

    @Inject
    TipoPeliculaBean tpeBean;


    @Override
    public AbstractDataPersistence<TipoPelicula> getBean() {return tpeBean;}

    @Override
    public Integer getId(TipoPelicula entity) {return entity.getIdTipoPelicula();}

}
