package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pago;

import java.io.Serializable;

@Stateless
@LocalBean
public class PagoBean extends AbstractDataPersistence<Pago> implements Serializable {
   @PersistenceContext(unitName = "CinePU")
   EntityManager em;

    public PagoBean() {
        super(Pago.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
