package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;
import java.io.Serializable;

@Stateless
@LocalBean
public class TipoPagoBean extends AbstractDataPersistence<TipoPago> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public TipoPagoBean() {
        super(TipoPago.class);
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