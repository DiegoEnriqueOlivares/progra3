package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class ProgramacionBean implements Serializable {

    @PersistenceContext(unitName = "CinePU")
    private EntityManager em;

    public Programacion create(Programacion programacion) {
        em.persist(programacion);
        return programacion;
    }

    public Programacion update(Programacion programacion) {
        return em.merge(programacion);
    }

    public void delete(Long idProgramacion) {
        Programacion prog = em.find(Programacion.class, idProgramacion);
        if (prog != null) {
            em.remove(prog);
        }
    }

    public List<Programacion> findByIdSala(final Integer idSala, int first, int max) {
        try {
            TypedQuery<Programacion> q = em.createNamedQuery("Programacion.findByIdSala", Programacion.class);
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
            TypedQuery<Long> q = em.createNamedQuery("Programacion.countByIdSala", Long.class);
            q.setParameter("idSala", idSala);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    public List<Programacion> findAll() {
        TypedQuery<Programacion> query = em.createQuery("SELECT p FROM Programacion p", Programacion.class);
        return query.getResultList();
    }

    public Programacion findById(Long id) {
        return em.find(Programacion.class, id);
    }
}