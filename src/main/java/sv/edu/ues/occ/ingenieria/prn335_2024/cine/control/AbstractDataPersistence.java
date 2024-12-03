package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public abstract class AbstractDataPersistence<T> {
    public abstract EntityManager getEntityManager();

    Class tipoDatos;

    public AbstractDataPersistence(Class tipoDatos) {
        this.tipoDatos = tipoDatos;
    }

    public void create(T entity) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        try {
            em = getEntityManager();

            if (em == null) {

                throw new IllegalStateException("Error al acceder al repositorio");
            }
            em.persist(entity);

        } catch (Exception e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
    }

    public T findById(final Object id) throws IllegalArgumentException, IllegalStateException {

        EntityManager em = null;
        if (id == null) {
            throw new IllegalArgumentException("Parametro no válido: id");
        }
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio de Tipo", ex);
        }

        return (T) em.find(tipoDatos, id);
    }

    public List<T> findRange(int first, int max) throws IllegalArgumentException, IllegalStateException {
        if (first < 0 || max < 0) {
            throw new IllegalArgumentException("Parámetro no válido: first o max no pueden ser negativos");
        }

        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("Error al acceder al repositorio");
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(tipoDatos);
        Root<T> root = cq.from(tipoDatos);
        cq.select(root);

        TypedQuery<T> query = em.createQuery(cq);
        query.setFirstResult(first);
        query.setMaxResults(max);

        return query.getResultList();
    }


    public T update(T entity) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;

        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }

        try {
            em = getEntityManager();

            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }


            T updatedEntity = em.merge(entity);

            return updatedEntity;
        } catch (Exception e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
    }
    public void delete(T entity) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        try {
            em = getEntityManager();

            if (em == null) {

                throw new IllegalStateException("Error al acceder al repositorio");
            }
            if(em.contains(entity)){
                em.remove(entity);
            }else{
                T managedEntity = em.merge(entity);
                em.remove(managedEntity);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
    }
    public Long count() throws IllegalStateException {
        EntityManager em = null;

        try {
            em = getEntityManager();

            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class); // Definir que queremos un resultado de tipo Long
            Root<T> raiz = cq.from(tipoDatos);
            cq.select(cb.count(raiz)); // Utilizar el método count

            TypedQuery<Long> query = em.createQuery(cq);
            return query.getSingleResult(); // Obtener el resultado único de la consulta

        } catch (Exception e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
    }

    public String imprimirCarnet(){
        return "LP23006";
    }
}