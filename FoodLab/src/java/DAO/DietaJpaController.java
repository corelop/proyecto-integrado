/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DTO.Dieta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Receta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author CRIS
 */
public class DietaJpaController implements Serializable {

    public DietaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dieta dieta) {
        if (dieta.getRecetaList() == null) {
            dieta.setRecetaList(new ArrayList<Receta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Receta> attachedRecetaList = new ArrayList<Receta>();
            for (Receta recetaListRecetaToAttach : dieta.getRecetaList()) {
                recetaListRecetaToAttach = em.getReference(recetaListRecetaToAttach.getClass(), recetaListRecetaToAttach.getCodReceta());
                attachedRecetaList.add(recetaListRecetaToAttach);
            }
            dieta.setRecetaList(attachedRecetaList);
            em.persist(dieta);
            for (Receta recetaListReceta : dieta.getRecetaList()) {
                recetaListReceta.getDietaList().add(dieta);
                recetaListReceta = em.merge(recetaListReceta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dieta dieta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dieta persistentDieta = em.find(Dieta.class, dieta.getCodDieta());
            List<Receta> recetaListOld = persistentDieta.getRecetaList();
            List<Receta> recetaListNew = dieta.getRecetaList();
            List<Receta> attachedRecetaListNew = new ArrayList<Receta>();
            for (Receta recetaListNewRecetaToAttach : recetaListNew) {
                recetaListNewRecetaToAttach = em.getReference(recetaListNewRecetaToAttach.getClass(), recetaListNewRecetaToAttach.getCodReceta());
                attachedRecetaListNew.add(recetaListNewRecetaToAttach);
            }
            recetaListNew = attachedRecetaListNew;
            dieta.setRecetaList(recetaListNew);
            dieta = em.merge(dieta);
            for (Receta recetaListOldReceta : recetaListOld) {
                if (!recetaListNew.contains(recetaListOldReceta)) {
                    recetaListOldReceta.getDietaList().remove(dieta);
                    recetaListOldReceta = em.merge(recetaListOldReceta);
                }
            }
            for (Receta recetaListNewReceta : recetaListNew) {
                if (!recetaListOld.contains(recetaListNewReceta)) {
                    recetaListNewReceta.getDietaList().add(dieta);
                    recetaListNewReceta = em.merge(recetaListNewReceta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dieta.getCodDieta();
                if (findDieta(id) == null) {
                    throw new NonexistentEntityException("The dieta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dieta dieta;
            try {
                dieta = em.getReference(Dieta.class, id);
                dieta.getCodDieta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dieta with id " + id + " no longer exists.", enfe);
            }
            List<Receta> recetaList = dieta.getRecetaList();
            for (Receta recetaListReceta : recetaList) {
                recetaListReceta.getDietaList().remove(dieta);
                recetaListReceta = em.merge(recetaListReceta);
            }
            em.remove(dieta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dieta> findDietaEntities() {
        return findDietaEntities(true, -1, -1);
    }

    public List<Dieta> findDietaEntities(int maxResults, int firstResult) {
        return findDietaEntities(false, maxResults, firstResult);
    }

    private List<Dieta> findDietaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dieta.class));
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

    public Dieta findDieta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dieta.class, id);
        } finally {
            em.close();
        }
    }

    public int getDietaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dieta> rt = cq.from(Dieta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    public List<Dieta> dietasPorTipo(String t) {

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Dieta> tq = em.createNamedQuery("Dieta.findByTipo", Dieta.class);
            tq.setParameter("tipo", t);
            return tq.getResultList();
        } finally {
            em.close();
        }

    }

    
}
