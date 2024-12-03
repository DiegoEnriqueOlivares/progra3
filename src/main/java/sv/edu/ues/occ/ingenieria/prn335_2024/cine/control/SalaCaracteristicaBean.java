package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class SalaCaracteristicaBean extends AbstractDataPersistence<SalaCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public SalaCaracteristicaBean(){
        super(SalaCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<SalaCaracteristica> findByIdSala(final Integer idSala, int first, int max) {
        try {
            TypedQuery<SalaCaracteristica> q = em.createNamedQuery("SalaCaracteristica.finByIdSala", SalaCaracteristica.class);
            q.setParameter("idSala", idSala);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }

    public int countSala(final Integer idSala) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("SalaCaracteristica.countByIdSala", Long.class);
            q.setParameter("idSala", idSala);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
}
