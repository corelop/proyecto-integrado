/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Usuario;
import DTO.Receta;
import DTO.SolicitudIngredientes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author CRIS
 */
public class SolicitudIngredientesJpaController implements Serializable {

    public SolicitudIngredientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolicitudIngredientes solicitudIngredientes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = solicitudIngredientes.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getNick());
                solicitudIngredientes.setUsuario(usuario);
            }
            Receta receta = solicitudIngredientes.getReceta();
            if (receta != null) {
                receta = em.getReference(receta.getClass(), receta.getCodReceta());
                solicitudIngredientes.setReceta(receta);
            }
            em.persist(solicitudIngredientes);
            if (usuario != null) {
                usuario.getSolicitudIngredientesList().add(solicitudIngredientes);
                usuario = em.merge(usuario);
            }
            if (receta != null) {
                receta.getSolicitudIngredientesList().add(solicitudIngredientes);
                receta = em.merge(receta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SolicitudIngredientes solicitudIngredientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SolicitudIngredientes persistentSolicitudIngredientes = em.find(SolicitudIngredientes.class, solicitudIngredientes.getCodSolicitud());
            Usuario usuarioOld = persistentSolicitudIngredientes.getUsuario();
            Usuario usuarioNew = solicitudIngredientes.getUsuario();
            Receta recetaOld = persistentSolicitudIngredientes.getReceta();
            Receta recetaNew = solicitudIngredientes.getReceta();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getNick());
                solicitudIngredientes.setUsuario(usuarioNew);
            }
            if (recetaNew != null) {
                recetaNew = em.getReference(recetaNew.getClass(), recetaNew.getCodReceta());
                solicitudIngredientes.setReceta(recetaNew);
            }
            solicitudIngredientes = em.merge(solicitudIngredientes);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getSolicitudIngredientesList().remove(solicitudIngredientes);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getSolicitudIngredientesList().add(solicitudIngredientes);
                usuarioNew = em.merge(usuarioNew);
            }
            if (recetaOld != null && !recetaOld.equals(recetaNew)) {
                recetaOld.getSolicitudIngredientesList().remove(solicitudIngredientes);
                recetaOld = em.merge(recetaOld);
            }
            if (recetaNew != null && !recetaNew.equals(recetaOld)) {
                recetaNew.getSolicitudIngredientesList().add(solicitudIngredientes);
                recetaNew = em.merge(recetaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitudIngredientes.getCodSolicitud();
                if (findSolicitudIngredientes(id) == null) {
                    throw new NonexistentEntityException("The solicitudIngredientes with id " + id + " no longer exists.");
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
            SolicitudIngredientes solicitudIngredientes;
            try {
                solicitudIngredientes = em.getReference(SolicitudIngredientes.class, id);
                solicitudIngredientes.getCodSolicitud();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudIngredientes with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario = solicitudIngredientes.getUsuario();
            if (usuario != null) {
                usuario.getSolicitudIngredientesList().remove(solicitudIngredientes);
                usuario = em.merge(usuario);
            }
            Receta receta = solicitudIngredientes.getReceta();
            if (receta != null) {
                receta.getSolicitudIngredientesList().remove(solicitudIngredientes);
                receta = em.merge(receta);
            }
            em.remove(solicitudIngredientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SolicitudIngredientes> findSolicitudIngredientesEntities() {
        return findSolicitudIngredientesEntities(true, -1, -1);
    }

    public List<SolicitudIngredientes> findSolicitudIngredientesEntities(int maxResults, int firstResult) {
        return findSolicitudIngredientesEntities(false, maxResults, firstResult);
    }

    private List<SolicitudIngredientes> findSolicitudIngredientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolicitudIngredientes.class));
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

    public SolicitudIngredientes findSolicitudIngredientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolicitudIngredientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudIngredientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolicitudIngredientes> rt = cq.from(SolicitudIngredientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
