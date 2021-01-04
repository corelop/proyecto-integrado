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
import DTO.Usuario;
import DTO.Ingrediente;
import DTO.ListaCompra;
import DTO.ListaCompraPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author CRIS
 */
public class ListaCompraJpaController implements Serializable {

    public ListaCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListaCompra listaCompra) throws PreexistingEntityException, Exception {
        if (listaCompra.getListaCompraPK() == null) {
            listaCompra.setListaCompraPK(new ListaCompraPK());
        }
        listaCompra.getListaCompraPK().setUsuario(listaCompra.getUsuario1().getNick());
        listaCompra.getListaCompraPK().setIngrediente(listaCompra.getIngrediente1().getCodIngrediente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario1 = listaCompra.getUsuario1();
            if (usuario1 != null) {
                usuario1 = em.getReference(usuario1.getClass(), usuario1.getNick());
                listaCompra.setUsuario1(usuario1);
            }
            Ingrediente ingrediente1 = listaCompra.getIngrediente1();
            if (ingrediente1 != null) {
                ingrediente1 = em.getReference(ingrediente1.getClass(), ingrediente1.getCodIngrediente());
                listaCompra.setIngrediente1(ingrediente1);
            }
            em.persist(listaCompra);
            if (usuario1 != null) {
                usuario1.getListaCompraList().add(listaCompra);
                usuario1 = em.merge(usuario1);
            }
            if (ingrediente1 != null) {
                ingrediente1.getListaCompraList().add(listaCompra);
                ingrediente1 = em.merge(ingrediente1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findListaCompra(listaCompra.getListaCompraPK()) != null) {
                throw new PreexistingEntityException("ListaCompra " + listaCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListaCompra listaCompra) throws NonexistentEntityException, Exception {
        listaCompra.getListaCompraPK().setUsuario(listaCompra.getUsuario1().getNick());
        listaCompra.getListaCompraPK().setIngrediente(listaCompra.getIngrediente1().getCodIngrediente());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaCompra persistentListaCompra = em.find(ListaCompra.class, listaCompra.getListaCompraPK());
            Usuario usuario1Old = persistentListaCompra.getUsuario1();
            Usuario usuario1New = listaCompra.getUsuario1();
            Ingrediente ingrediente1Old = persistentListaCompra.getIngrediente1();
            Ingrediente ingrediente1New = listaCompra.getIngrediente1();
            if (usuario1New != null) {
                usuario1New = em.getReference(usuario1New.getClass(), usuario1New.getNick());
                listaCompra.setUsuario1(usuario1New);
            }
            if (ingrediente1New != null) {
                ingrediente1New = em.getReference(ingrediente1New.getClass(), ingrediente1New.getCodIngrediente());
                listaCompra.setIngrediente1(ingrediente1New);
            }
            listaCompra = em.merge(listaCompra);
            if (usuario1Old != null && !usuario1Old.equals(usuario1New)) {
                usuario1Old.getListaCompraList().remove(listaCompra);
                usuario1Old = em.merge(usuario1Old);
            }
            if (usuario1New != null && !usuario1New.equals(usuario1Old)) {
                usuario1New.getListaCompraList().add(listaCompra);
                usuario1New = em.merge(usuario1New);
            }
            if (ingrediente1Old != null && !ingrediente1Old.equals(ingrediente1New)) {
                ingrediente1Old.getListaCompraList().remove(listaCompra);
                ingrediente1Old = em.merge(ingrediente1Old);
            }
            if (ingrediente1New != null && !ingrediente1New.equals(ingrediente1Old)) {
                ingrediente1New.getListaCompraList().add(listaCompra);
                ingrediente1New = em.merge(ingrediente1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ListaCompraPK id = listaCompra.getListaCompraPK();
                if (findListaCompra(id) == null) {
                    throw new NonexistentEntityException("The listaCompra with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ListaCompraPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaCompra listaCompra;
            try {
                listaCompra = em.getReference(ListaCompra.class, id);
                listaCompra.getListaCompraPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaCompra with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario1 = listaCompra.getUsuario1();
            if (usuario1 != null) {
                usuario1.getListaCompraList().remove(listaCompra);
                usuario1 = em.merge(usuario1);
            }
            Ingrediente ingrediente1 = listaCompra.getIngrediente1();
            if (ingrediente1 != null) {
                ingrediente1.getListaCompraList().remove(listaCompra);
                ingrediente1 = em.merge(ingrediente1);
            }
            em.remove(listaCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListaCompra> findListaCompraEntities() {
        return findListaCompraEntities(true, -1, -1);
    }

    public List<ListaCompra> findListaCompraEntities(int maxResults, int firstResult) {
        return findListaCompraEntities(false, maxResults, firstResult);
    }

    private List<ListaCompra> findListaCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaCompra.class));
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

    public ListaCompra findListaCompra(ListaCompraPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaCompra> rt = cq.from(ListaCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public List<Ingrediente> ingPorNombre(String n) {

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Ingrediente> tq = em.createNamedQuery("Ingrediente.findByNombre", Ingrediente.class);
            tq.setParameter("nombre", n);
            return tq.getResultList();
        } finally {
            em.close();
        }

    }
    
     public List<ListaCompra> listaPorIng(int n) {
         
          EntityManager em = getEntityManager();
        try {
            TypedQuery<ListaCompra> tq = em.createNamedQuery("ListaCompra.findByIngrediente", ListaCompra.class);
            tq.setParameter("ingrediente", n);
            return tq.getResultList();
        } finally {
            em.close();
        }
     }

    
}
