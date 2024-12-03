package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class PeliculaCaracteristicaBean extends AbstractDataPersistence<PeliculaCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public PeliculaCaracteristicaBean() {
        super(PeliculaCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<PeliculaCaracteristica> findByIdPelicula(final Long idPelicula, int first, int max) {
        try {
            TypedQuery<PeliculaCaracteristica> q = em.createNamedQuery("PeliculaCaracteristica.finByIdPelicula", PeliculaCaracteristica.class);
            q.setParameter("idPelicula", idPelicula);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    public int countPelicula(final Long idPelicula) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("PeliculaCaracteristica.countByIdPelicula", Long.class);
            q.setParameter("idPelicula", idPelicula);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
}
