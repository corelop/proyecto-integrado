/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Alergeno;
import DTO.AlergenosIngrediente;
import DTO.AlergenosIngredientePK;
import DTO.Ingrediente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author CRIS
 */
public class AlergenosIngredienteJpaController implements Serializable {

    public AlergenosIngredienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AlergenosIngrediente alergenosIngrediente) throws PreexistingEntityException, Exception {
        if (alergenosIngrediente.getAlergenosIngredientePK() == null) {
            alergenosIngrediente.setAlergenosIngredientePK(new AlergenosIngredientePK());
        }
        alergenosIngrediente.getAlergenosIngredientePK().setIngrediente(alergenosIngrediente.getIngrediente1().getCodIngrediente());
        alergenosIngrediente.getAlergenosIngredientePK().setAlergeno(alergenosIngrediente.getAlergeno1().getCodAlergeno());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alergeno alergeno1 = alergenosIngrediente.getAlergeno1();
            if (alergeno1 != null) {
                alergeno1 = em.getReference(alergeno1.getClass(), alergeno1.getCodAlergeno());
                alergenosIngrediente.setAlergeno1(alergeno1);
            }
            Ingrediente ingrediente1 = alergenosIngrediente.getIngrediente1();
            if (ingrediente1 != null) {
                ingrediente1 = em.getReference(ingrediente1.getClass(), ingrediente1.getCodIngrediente());
                alergenosIngrediente.setIngrediente1(ingrediente1);
            }
            em.persist(alergenosIngrediente);
            if (alergeno1 != null) {
                alergeno1.getAlergenosIngredienteList().add(alergenosIngrediente);
                alergeno1 = em.merge(alergeno1);
            }
            if (ingrediente1 != null) {
                ingrediente1.getAlergenosIngredienteList().add(alergenosIngrediente);
                ingrediente1 = em.merge(ingrediente1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlergenosIngrediente(alergenosIngrediente.getAlergenosIngredientePK()) != null) {
                throw new PreexistingEntityException("AlergenosIngrediente " + alergenosIngrediente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AlergenosIngrediente alergenosIngrediente) throws NonexistentEntityException, Exception {
        alergenosIngrediente.getAlergenosIngredientePK().setIngrediente(alergenosIngrediente.getIngrediente1().getCodIngrediente());
        alergenosIngrediente.getAlergenosIngredientePK().setAlergeno(alergenosIngrediente.getAlergeno1().getCodAlergeno());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AlergenosIngrediente persistentAlergenosIngrediente = em.find(AlergenosIngrediente.class, alergenosIngrediente.getAlergenosIngredientePK());
            Alergeno alergeno1Old = persistentAlergenosIngrediente.getAlergeno1();
            Alergeno alergeno1New = alergenosIngrediente.getAlergeno1();
            Ingrediente ingrediente1Old = persistentAlergenosIngrediente.getIngrediente1();
            Ingrediente ingrediente1New = alergenosIngrediente.getIngrediente1();
            if (alergeno1New != null) {
                alergeno1New = em.getReference(alergeno1New.getClass(), alergeno1New.getCodAlergeno());
                alergenosIngrediente.setAlergeno1(alergeno1New);
            }
            if (ingrediente1New != null) {
                ingrediente1New = em.getReference(ingrediente1New.getClass(), ingrediente1New.getCodIngrediente());
                alergenosIngrediente.setIngrediente1(ingrediente1New);
            }
            alergenosIngrediente = em.merge(alergenosIngrediente);
            if (alergeno1Old != null && !alergeno1Old.equals(alergeno1New)) {
                alergeno1Old.getAlergenosIngredienteList().remove(alergenosIngrediente);
                alergeno1Old = em.merge(alergeno1Old);
            }
            if (alergeno1New != null && !alergeno1New.equals(alergeno1Old)) {
                alergeno1New.getAlergenosIngredienteList().add(alergenosIngrediente);
                alergeno1New = em.merge(alergeno1New);
            }
            if (ingrediente1Old != null && !ingrediente1Old.equals(ingrediente1New)) {
                ingrediente1Old.getAlergenosIngredienteList().remove(alergenosIngrediente);
                ingrediente1Old = em.merge(ingrediente1Old);
            }
            if (ingrediente1New != null && !ingrediente1New.equals(ingrediente1Old)) {
                ingrediente1New.getAlergenosIngredienteList().add(alergenosIngrediente);
                ingrediente1New = em.merge(ingrediente1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AlergenosIngredientePK id = alergenosIngrediente.getAlergenosIngredientePK();
                if (findAlergenosIngrediente(id) == null) {
                    throw new NonexistentEntityException("The alergenosIngrediente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AlergenosIngredientePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AlergenosIngrediente alergenosIngrediente;
            try {
                alergenosIngrediente = em.getReference(AlergenosIngrediente.class, id);
                alergenosIngrediente.getAlergenosIngredientePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alergenosIngrediente with id " + id + " no longer exists.", enfe);
            }
            Alergeno alergeno1 = alergenosIngrediente.getAlergeno1();
            if (alergeno1 != null) {
                alergeno1.getAlergenosIngredienteList().remove(alergenosIngrediente);
                alergeno1 = em.merge(alergeno1);
            }
            Ingrediente ingrediente1 = alergenosIngrediente.getIngrediente1();
            if (ingrediente1 != null) {
                ingrediente1.getAlergenosIngredienteList().remove(alergenosIngrediente);
                ingrediente1 = em.merge(ingrediente1);
            }
            em.remove(alergenosIngrediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AlergenosIngrediente> findAlergenosIngredienteEntities() {
        return findAlergenosIngredienteEntities(true, -1, -1);
    }

    public List<AlergenosIngrediente> findAlergenosIngredienteEntities(int maxResults, int firstResult) {
        return findAlergenosIngredienteEntities(false, maxResults, firstResult);
    }

    private List<AlergenosIngrediente> findAlergenosIngredienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AlergenosIngrediente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public AlergenosIngrediente findAlergenosIngrediente(AlergenosIngredientePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AlergenosIngrediente.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlergenosIngredienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AlergenosIngrediente> rt = cq.from(AlergenosIngrediente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
