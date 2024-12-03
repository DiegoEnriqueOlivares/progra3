package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;
import java.io.Serializable;

@Stateless
@LocalBean
public class TipoAsientoBean extends AbstractDataPersistence<TipoAsiento> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public TipoAsientoBean() {
        super(TipoAsiento.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}