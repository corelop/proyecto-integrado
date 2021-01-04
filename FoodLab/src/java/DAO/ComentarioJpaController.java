/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Comentario;
import DTO.ComentarioPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Receta;
import DTO.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author CRIS
 */
public class ComentarioJpaController implements Serializable {

    public ComentarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comentario comentario) throws PreexistingEntityException, Exception {
        if (comentario.getComentarioPK() == null) {
            comentario.setComentarioPK(new ComentarioPK());
        }
        comentario.getComentarioPK().setUsuario(comentario.getUsuario1().getNick());
        comentario.getComentarioPK().setCodReceta(comentario.getReceta().getCodReceta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receta receta = comentario.getReceta();
            if (receta != null) {
                receta = em.getReference(receta.getClass(), receta.getCodReceta());
                comentario.setReceta(receta);
            }
            Usuario usuario1 = comentario.getUsuario1();
            if (usuario1 != null) {
                usuario1 = em.getReference(usuario1.getClass(), usuario1.getNick());
                comentario.setUsuario1(usuario1);
            }
            em.persist(comentario);
            if (receta != null) {
                receta.getComentarioList().add(comentario);
                receta = em.merge(receta);
            }
            if (usuario1 != null) {
                usuario1.getComentarioList().add(comentario);
                usuario1 = em.merge(usuario1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComentario(comentario.getComentarioPK()) != null) {
                throw new PreexistingEntityException("Comentario " + comentario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comentario comentario) throws NonexistentEntityException, Exception {
        comentario.getComentarioPK().setUsuario(comentario.getUsuario1().getNick());
        comentario.getComentarioPK().setCodReceta(comentario.getReceta().getCodReceta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentario persistentComentario = em.find(Comentario.class, comentario.getComentarioPK());
            Receta recetaOld = persistentComentario.getReceta();
            Receta recetaNew = comentario.getReceta();
            Usuario usuario1Old = persistentComentario.getUsuario1();
            Usuario usuario1New = comentario.getUsuario1();
            if (recetaNew != null) {
                recetaNew = em.getReference(recetaNew.getClass(), recetaNew.getCodReceta());
                comentario.setReceta(recetaNew);
            }
            if (usuario1New != null) {
                usuario1New = em.getReference(usuario1New.getClass(), usuario1New.getNick());
                comentario.setUsuario1(usuario1New);
            }
            comentario = em.merge(comentario);
            if (recetaOld != null && !recetaOld.equals(recetaNew)) {
                recetaOld.getComentarioList().remove(comentario);
                recetaOld = em.merge(recetaOld);
            }
            if (recetaNew != null && !recetaNew.equals(recetaOld)) {
                recetaNew.getComentarioList().add(comentario);
                recetaNew = em.merge(recetaNew);
            }
            if (usuario1Old != null && !usuario1Old.equals(usuario1New)) {
                usuario1Old.getComentarioList().remove(comentario);
                usuario1Old = em.merge(usuario1Old);
            }
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                usuario1New.getComentarioList().add(comentario);
                usuario1New = em.merge(usuario1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ComentarioPK id = comentario.getComentarioPK();
                if (findComentario(id) == null) {
                    throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ComentarioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentario comentario;
            try {
                comentario = em.getReference(Comentario.class, id);
                comentario.getComentarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.", enfe);
            }
            Receta receta = comentario.getReceta();
            if (receta != null) {
                receta.getComentarioList().remove(comentario);
                receta = em.merge(receta);
            }
            Usuario usuario1 = comentario.getUsuario1();
            if (usuario1 != null) {
                usuario1.getComentarioList().remove(comentario);
                usuario1 = em.merge(usuario1);
            }
            em.remove(comentario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentario> findComentarioEntities() {
        return findComentarioEntities(true, -1, -1);
    }

    public List<Comentario> findComentarioEntities(int maxResults, int firstResult) {
        return findComentarioEntities(false, maxResults, firstResult);
    }

    private List<Comentario> findComentarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comentario.class));
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

    public Comentario findComentario(ComentarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comentario.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comentario> rt = cq.from(Comentario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
