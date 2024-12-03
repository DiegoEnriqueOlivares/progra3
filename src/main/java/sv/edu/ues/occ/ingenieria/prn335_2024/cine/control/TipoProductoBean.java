package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;
import java.io.Serializable;

@Stateless
@LocalBean
public class TipoProductoBean extends AbstractDataPersistence<TipoProducto> implements Serializable {
   @PersistenceContext(unitName = "CinePU")
   EntityManager em;

    public TipoProductoBean() {
        super(TipoProducto.class);
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