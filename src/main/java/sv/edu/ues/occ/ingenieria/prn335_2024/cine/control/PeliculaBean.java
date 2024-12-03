package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;

@Stateless
@LocalBean
public class PeliculaBean extends AbstractDataPersistence<Pelicula> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public PeliculaBean() {
        super(Pelicula.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
