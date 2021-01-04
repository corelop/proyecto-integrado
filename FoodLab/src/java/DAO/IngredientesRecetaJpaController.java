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
import DTO.Ingrediente;
import DTO.IngredientesReceta;
import DTO.IngredientesRecetaPK;
import DTO.Receta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author CRIS
 */
public class IngredientesRecetaJpaController implements Serializable {

    public IngredientesRecetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IngredientesReceta ingredientesReceta) throws PreexistingEntityException, Exception {
        if (ingredientesReceta.getIngredientesRecetaPK() == null) {
            ingredientesReceta.setIngredientesRecetaPK(new IngredientesRecetaPK());
        }
        ingredientesReceta.getIngredientesRecetaPK().setIngrediente(ingredientesReceta.getIngrediente1().getCodIngrediente());
        ingredientesReceta.getIngredientesRecetaPK().setReceta(ingredientesReceta.getReceta1().getCodReceta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente ingrediente1 = ingredientesReceta.getIngrediente1();
            if (ingrediente1 != null) {
                ingrediente1 = em.getReference(ingrediente1.getClass(), ingrediente1.getCodIngrediente());
                ingredientesReceta.setIngrediente1(ingrediente1);
            }
            Receta receta1 = ingredientesReceta.getReceta1();
            if (receta1 != null) {
                receta1 = em.getReference(receta1.getClass(), receta1.getCodReceta());
                ingredientesReceta.setReceta1(receta1);
            }
            em.persist(ingredientesReceta);
            if (ingrediente1 != null) {
                ingrediente1.getIngredientesRecetaList().add(ingredientesReceta);
                ingrediente1 = em.merge(ingrediente1);
            }
            if (receta1 != null) {
                receta1.getIngredientesRecetaList().add(ingredientesReceta);
                receta1 = em.merge(receta1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIngredientesReceta(ingredientesReceta.getIngredientesRecetaPK()) != null) {
                throw new PreexistingEntityException("IngredientesReceta " + ingredientesReceta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IngredientesReceta ingredientesReceta) throws NonexistentEntityException, Exception {
        ingredientesReceta.getIngredientesRecetaPK().setIngrediente(ingredientesReceta.getIngrediente1().getCodIngrediente());
        ingredientesReceta.getIngredientesRecetaPK().setReceta(ingredientesReceta.getReceta1().getCodReceta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IngredientesReceta persistentIngredientesReceta = em.find(IngredientesReceta.class, ingredientesReceta.getIngredientesRecetaPK());
            Ingrediente ingrediente1Old = persistentIngredientesReceta.getIngrediente1();
            Ingrediente ingrediente1New = ingredientesReceta.getIngrediente1();
            Receta receta1Old = persistentIngredientesReceta.getReceta1();
            Receta receta1New = ingredientesReceta.getReceta1();
            if (ingrediente1New != null) {
                ingrediente1New = em.getReference(ingrediente1New.getClass(), ingrediente1New.getCodIngrediente());
                ingredientesReceta.setIngrediente1(ingrediente1New);
            }
            if (receta1New != null) {
                receta1New = em.getReference(receta1New.getClass(), receta1New.getCodReceta());
                ingredientesReceta.setReceta1(receta1New);
            }
            ingredientesReceta = em.merge(ingredientesReceta);
            if (ingrediente1Old != null && !ingrediente1Old.equals(ingrediente1New)) {
                ingrediente1Old.getIngredientesRecetaList().remove(ingredientesReceta);
                ingrediente1Old = em.merge(ingrediente1Old);
            }
            if (ingrediente1New != null && !ingrediente1New.equals(ingrediente1Old)) {
                ingrediente1New.getIngredientesRecetaList().add(ingredientesReceta);
                ingrediente1New = em.merge(ingrediente1New);
            }
            if (receta1Old != null && !receta1Old.equals(receta1New)) {
                receta1Old.getIngredientesRecetaList().remove(ingredientesReceta);
                receta1Old = em.merge(receta1Old);
            }
            if (receta1New != null && !receta1New.equals(receta1Old)) {
                receta1New.getIngredientesRecetaList().add(ingredientesReceta);
                receta1New = em.merge(receta1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IngredientesRecetaPK id = ingredientesReceta.getIngredientesRecetaPK();
                if (findIngredientesReceta(id) == null) {
                    throw new NonexistentEntityException("The ingredientesReceta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IngredientesRecetaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IngredientesReceta ingredientesReceta;
            try {
                ingredientesReceta = em.getReference(IngredientesReceta.class, id);
                ingredientesReceta.getIngredientesRecetaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingredientesReceta with id " + id + " no longer exists.", enfe);
            }
            Ingrediente ingrediente1 = ingredientesReceta.getIngrediente1();
            if (ingrediente1 != null) {
                ingrediente1.getIngredientesRecetaList().remove(ingredientesReceta);
                ingrediente1 = em.merge(ingrediente1);
            }
            Receta receta1 = ingredientesReceta.getReceta1();
            if (receta1 != null) {
                receta1.getIngredientesRecetaList().remove(ingredientesReceta);
                receta1 = em.merge(receta1);
            }
            em.remove(ingredientesReceta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IngredientesReceta> findIngredientesRecetaEntities() {
        return findIngredientesRecetaEntities(true, -1, -1);
    }

    public List<IngredientesReceta> findIngredientesRecetaEntities(int maxResults, int firstResult) {
        return findIngredientesRecetaEntities(false, maxResults, firstResult);
    }

    private List<IngredientesReceta> findIngredientesRecetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IngredientesReceta.class));
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

    public IngredientesReceta findIngredientesReceta(IngredientesRecetaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IngredientesReceta.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredientesRecetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IngredientesReceta> rt = cq.from(IngredientesReceta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
