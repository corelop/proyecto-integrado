/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.AlergenosIngrediente;
import DTO.Ingrediente;
import java.util.ArrayList;
import java.util.List;
import DTO.ListaCompra;
import DTO.IngredientesReceta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author CRIS
 */
public class IngredienteJpaController implements Serializable {

    public IngredienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingrediente ingrediente) {
        if (ingrediente.getAlergenosIngredienteList() == null) {
            ingrediente.setAlergenosIngredienteList(new ArrayList<AlergenosIngrediente>());
        }
        if (ingrediente.getListaCompraList() == null) {
            ingrediente.setListaCompraList(new ArrayList<ListaCompra>());
        }
        if (ingrediente.getIngredientesRecetaList() == null) {
            ingrediente.setIngredientesRecetaList(new ArrayList<IngredientesReceta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AlergenosIngrediente> attachedAlergenosIngredienteList = new ArrayList<AlergenosIngrediente>();
            for (AlergenosIngrediente alergenosIngredienteListAlergenosIngredienteToAttach : ingrediente.getAlergenosIngredienteList()) {
                alergenosIngredienteListAlergenosIngredienteToAttach = em.getReference(alergenosIngredienteListAlergenosIngredienteToAttach.getClass(), alergenosIngredienteListAlergenosIngredienteToAttach.getAlergenosIngredientePK());
                attachedAlergenosIngredienteList.add(alergenosIngredienteListAlergenosIngredienteToAttach);
            }
            ingrediente.setAlergenosIngredienteList(attachedAlergenosIngredienteList);
            List<ListaCompra> attachedListaCompraList = new ArrayList<ListaCompra>();
            for (ListaCompra listaCompraListListaCompraToAttach : ingrediente.getListaCompraList()) {
                listaCompraListListaCompraToAttach = em.getReference(listaCompraListListaCompraToAttach.getClass(), listaCompraListListaCompraToAttach.getListaCompraPK());
                attachedListaCompraList.add(listaCompraListListaCompraToAttach);
            }
            ingrediente.setListaCompraList(attachedListaCompraList);
            List<IngredientesReceta> attachedIngredientesRecetaList = new ArrayList<IngredientesReceta>();
            for (IngredientesReceta ingredientesRecetaListIngredientesRecetaToAttach : ingrediente.getIngredientesRecetaList()) {
                ingredientesRecetaListIngredientesRecetaToAttach = em.getReference(ingredientesRecetaListIngredientesRecetaToAttach.getClass(), ingredientesRecetaListIngredientesRecetaToAttach.getIngredientesRecetaPK());
                attachedIngredientesRecetaList.add(ingredientesRecetaListIngredientesRecetaToAttach);
            }
            ingrediente.setIngredientesRecetaList(attachedIngredientesRecetaList);
            em.persist(ingrediente);
            for (AlergenosIngrediente alergenosIngredienteListAlergenosIngrediente : ingrediente.getAlergenosIngredienteList()) {
                Ingrediente oldIngrediente1OfAlergenosIngredienteListAlergenosIngrediente = alergenosIngredienteListAlergenosIngrediente.getIngrediente1();
                alergenosIngredienteListAlergenosIngrediente.setIngrediente1(ingrediente);
                alergenosIngredienteListAlergenosIngrediente = em.merge(alergenosIngredienteListAlergenosIngrediente);
                if (oldIngrediente1OfAlergenosIngredienteListAlergenosIngrediente != null) {
                    oldIngrediente1OfAlergenosIngredienteListAlergenosIngrediente.getAlergenosIngredienteList().remove(alergenosIngredienteListAlergenosIngrediente);
                    oldIngrediente1OfAlergenosIngredienteListAlergenosIngrediente = em.merge(oldIngrediente1OfAlergenosIngredienteListAlergenosIngrediente);
                }
            }
            for (ListaCompra listaCompraListListaCompra : ingrediente.getListaCompraList()) {
                Ingrediente oldIngrediente1OfListaCompraListListaCompra = listaCompraListListaCompra.getIngrediente1();
                listaCompraListListaCompra.setIngrediente1(ingrediente);
                listaCompraListListaCompra = em.merge(listaCompraListListaCompra);
                if (oldIngrediente1OfListaCompraListListaCompra != null) {
                    oldIngrediente1OfListaCompraListListaCompra.getListaCompraList().remove(listaCompraListListaCompra);
                    oldIngrediente1OfListaCompraListListaCompra = em.merge(oldIngrediente1OfListaCompraListListaCompra);
                }
            }
            for (IngredientesReceta ingredientesRecetaListIngredientesReceta : ingrediente.getIngredientesRecetaList()) {
                Ingrediente oldIngrediente1OfIngredientesRecetaListIngredientesReceta = ingredientesRecetaListIngredientesReceta.getIngrediente1();
                ingredientesRecetaListIngredientesReceta.setIngrediente1(ingrediente);
                ingredientesRecetaListIngredientesReceta = em.merge(ingredientesRecetaListIngredientesReceta);
                if (oldIngrediente1OfIngredientesRecetaListIngredientesReceta != null) {
                    oldIngrediente1OfIngredientesRecetaListIngredientesReceta.getIngredientesRecetaList().remove(ingredientesRecetaListIngredientesReceta);
                    oldIngrediente1OfIngredientesRecetaListIngredientesReceta = em.merge(oldIngrediente1OfIngredientesRecetaListIngredientesReceta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingrediente ingrediente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente persistentIngrediente = em.find(Ingrediente.class, ingrediente.getCodIngrediente());
            List<AlergenosIngrediente> alergenosIngredienteListOld = persistentIngrediente.getAlergenosIngredienteList();
            List<AlergenosIngrediente> alergenosIngredienteListNew = ingrediente.getAlergenosIngredienteList();
            List<ListaCompra> listaCompraListOld = persistentIngrediente.getListaCompraList();
            List<ListaCompra> listaCompraListNew = ingrediente.getListaCompraList();
            List<IngredientesReceta> ingredientesRecetaListOld = persistentIngrediente.getIngredientesRecetaList();
            List<IngredientesReceta> ingredientesRecetaListNew = ingrediente.getIngredientesRecetaList();
            List<String> illegalOrphanMessages = null;
            for (AlergenosIngrediente alergenosIngredienteListOldAlergenosIngrediente : alergenosIngredienteListOld) {
                if (!alergenosIngredienteListNew.contains(alergenosIngredienteListOldAlergenosIngrediente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AlergenosIngrediente " + alergenosIngredienteListOldAlergenosIngrediente + " since its ingrediente1 field is not nullable.");
                }
            }
            for (ListaCompra listaCompraListOldListaCompra : listaCompraListOld) {
                if (!listaCompraListNew.contains(listaCompraListOldListaCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ListaCompra " + listaCompraListOldListaCompra + " since its ingrediente1 field is not nullable.");
                }
            }
            for (IngredientesReceta ingredientesRecetaListOldIngredientesReceta : ingredientesRecetaListOld) {
                if (!ingredientesRecetaListNew.contains(ingredientesRecetaListOldIngredientesReceta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IngredientesReceta " + ingredientesRecetaListOldIngredientesReceta + " since its ingrediente1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<AlergenosIngrediente> attachedAlergenosIngredienteListNew = new ArrayList<AlergenosIngrediente>();
            for (AlergenosIngrediente alergenosIngredienteListNewAlergenosIngredienteToAttach : alergenosIngredienteListNew) {
                alergenosIngredienteListNewAlergenosIngredienteToAttach = em.getReference(alergenosIngredienteListNewAlergenosIngredienteToAttach.getClass(), alergenosIngredienteListNewAlergenosIngredienteToAttach.getAlergenosIngredientePK());
                attachedAlergenosIngredienteListNew.add(alergenosIngredienteListNewAlergenosIngredienteToAttach);
            }
            alergenosIngredienteListNew = attachedAlergenosIngredienteListNew;
            ingrediente.setAlergenosIngredienteList(alergenosIngredienteListNew);
            List<ListaCompra> attachedListaCompraListNew = new ArrayList<ListaCompra>();
            for (ListaCompra listaCompraListNewListaCompraToAttach : listaCompraListNew) {
                listaCompraListNewListaCompraToAttach = em.getReference(listaCompraListNewListaCompraToAttach.getClass(), listaCompraListNewListaCompraToAttach.getListaCompraPK());
                attachedListaCompraListNew.add(listaCompraListNewListaCompraToAttach);
            }
            listaCompraListNew = attachedListaCompraListNew;
            ingrediente.setListaCompraList(listaCompraListNew);
            List<IngredientesReceta> attachedIngredientesRecetaListNew = new ArrayList<IngredientesReceta>();
            for (IngredientesReceta ingredientesRecetaListNewIngredientesRecetaToAttach : ingredientesRecetaListNew) {
                ingredientesRecetaListNewIngredientesRecetaToAttach = em.getReference(ingredientesRecetaListNewIngredientesRecetaToAttach.getClass(), ingredientesRecetaListNewIngredientesRecetaToAttach.getIngredientesRecetaPK());
                attachedIngredientesRecetaListNew.add(ingredientesRecetaListNewIngredientesRecetaToAttach);
            }
            ingredientesRecetaListNew = attachedIngredientesRecetaListNew;
            ingrediente.setIngredientesRecetaList(ingredientesRecetaListNew);
            ingrediente = em.merge(ingrediente);
            for (AlergenosIngrediente alergenosIngredienteListNewAlergenosIngrediente : alergenosIngredienteListNew) {
                if (!alergenosIngredienteListOld.contains(alergenosIngredienteListNewAlergenosIngrediente)) {
                    Ingrediente oldIngrediente1OfAlergenosIngredienteListNewAlergenosIngrediente = alergenosIngredienteListNewAlergenosIngrediente.getIngrediente1();
                    alergenosIngredienteListNewAlergenosIngrediente.setIngrediente1(ingrediente);
                    alergenosIngredienteListNewAlergenosIngrediente = em.merge(alergenosIngredienteListNewAlergenosIngrediente);
                    if (oldIngrediente1OfAlergenosIngredienteListNewAlergenosIngrediente != null && !oldIngrediente1OfAlergenosIngredienteListNewAlergenosIngrediente.equals(ingrediente)) {
                        oldIngrediente1OfAlergenosIngredienteListNewAlergenosIngrediente.getAlergenosIngredienteList().remove(alergenosIngredienteListNewAlergenosIngrediente);
                        oldIngrediente1OfAlergenosIngredienteListNewAlergenosIngrediente = em.merge(oldIngrediente1OfAlergenosIngredienteListNewAlergenosIngrediente);
                    }
                }
            }
            for (ListaCompra listaCompraListNewListaCompra : listaCompraListNew) {
                if (!listaCompraListOld.contains(listaCompraListNewListaCompra)) {
                    Ingrediente oldIngrediente1OfListaCompraListNewListaCompra = listaCompraListNewListaCompra.getIngrediente1();
                    listaCompraListNewListaCompra.setIngrediente1(ingrediente);
                    listaCompraListNewListaCompra = em.merge(listaCompraListNewListaCompra);
                    if (oldIngrediente1OfListaCompraListNewListaCompra != null && !oldIngrediente1OfListaCompraListNewListaCompra.equals(ingrediente)) {
                        oldIngrediente1OfListaCompraListNewListaCompra.getListaCompraList().remove(listaCompraListNewListaCompra);
                        oldIngrediente1OfListaCompraListNewListaCompra = em.merge(oldIngrediente1OfListaCompraListNewListaCompra);
                    }
                }
            }
            for (IngredientesReceta ingredientesRecetaListNewIngredientesReceta : ingredientesRecetaListNew) {
                if (!ingredientesRecetaListOld.contains(ingredientesRecetaListNewIngredientesReceta)) {
                    Ingrediente oldIngrediente1OfIngredientesRecetaListNewIngredientesReceta = ingredientesRecetaListNewIngredientesReceta.getIngrediente1();
                    ingredientesRecetaListNewIngredientesReceta.setIngrediente1(ingrediente);
                    ingredientesRecetaListNewIngredientesReceta = em.merge(ingredientesRecetaListNewIngredientesReceta);
                    if (oldIngrediente1OfIngredientesRecetaListNewIngredientesReceta != null && !oldIngrediente1OfIngredientesRecetaListNewIngredientesReceta.equals(ingrediente)) {
                        oldIngrediente1OfIngredientesRecetaListNewIngredientesReceta.getIngredientesRecetaList().remove(ingredientesRecetaListNewIngredientesReceta);
                        oldIngrediente1OfIngredientesRecetaListNewIngredientesReceta = em.merge(oldIngrediente1OfIngredientesRecetaListNewIngredientesReceta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingrediente.getCodIngrediente();
                if (findIngrediente(id) == null) {
                    throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente ingrediente;
            try {
                ingrediente = em.getReference(Ingrediente.class, id);
                ingrediente.getCodIngrediente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AlergenosIngrediente> alergenosIngredienteListOrphanCheck = ingrediente.getAlergenosIngredienteList();
            for (AlergenosIngrediente alergenosIngredienteListOrphanCheckAlergenosIngrediente : alergenosIngredienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingrediente (" + ingrediente + ") cannot be destroyed since the AlergenosIngrediente " + alergenosIngredienteListOrphanCheckAlergenosIngrediente + " in its alergenosIngredienteList field has a non-nullable ingrediente1 field.");
            }
            List<ListaCompra> listaCompraListOrphanCheck = ingrediente.getListaCompraList();
            for (ListaCompra listaCompraListOrphanCheckListaCompra : listaCompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingrediente (" + ingrediente + ") cannot be destroyed since the ListaCompra " + listaCompraListOrphanCheckListaCompra + " in its listaCompraList field has a non-nullable ingrediente1 field.");
            }
            List<IngredientesReceta> ingredientesRecetaListOrphanCheck = ingrediente.getIngredientesRecetaList();
            for (IngredientesReceta ingredientesRecetaListOrphanCheckIngredientesReceta : ingredientesRecetaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingrediente (" + ingrediente + ") cannot be destroyed since the IngredientesReceta " + ingredientesRecetaListOrphanCheckIngredientesReceta + " in its ingredientesRecetaList field has a non-nullable ingrediente1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ingrediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingrediente> findIngredienteEntities() {
        return findIngredienteEntities(true, -1, -1);
    }

    public List<Ingrediente> findIngredienteEntities(int maxResults, int firstResult) {
        return findIngredienteEntities(false, maxResults, firstResult);
    }

    private List<Ingrediente> findIngredienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingrediente.class));
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

    public Ingrediente findIngrediente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingrediente.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingrediente> rt = cq.from(Ingrediente.class);
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
    
}
