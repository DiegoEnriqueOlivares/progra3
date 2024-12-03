package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.ReservaDetalle;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ReservaDetalleBean extends AbstractDataPersistence<ReservaDetalle> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public ReservaDetalleBean() {
        super(ReservaDetalle.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<ReservaDetalle> findByIdReserva(final Long idReserva, int first, int max) {
        try {
            TypedQuery<ReservaDetalle> q = em.createNamedQuery("ReservaDetalle.finByIdReserva", ReservaDetalle.class);
            q.setParameter("idReserva", idReserva);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }
    public int countReserva(final Long idReserva) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("ReservaDetalle.countByIdReserva", Long.class);
            q.setParameter("idReserva", idReserva);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }
}
