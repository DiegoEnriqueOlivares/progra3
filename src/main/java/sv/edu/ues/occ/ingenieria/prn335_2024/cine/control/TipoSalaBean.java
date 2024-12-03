package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;
import java.io.Serializable;

@Stateless
@LocalBean
public class TipoSalaBean extends AbstractDataPersistence<TipoSala> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    public EntityManager em;

    public TipoSalaBean() {
        super(TipoSala.class);
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}