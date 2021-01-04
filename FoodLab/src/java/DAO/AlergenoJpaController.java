/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Alergeno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.AlergenosIngrediente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author CRIS
 */
public class AlergenoJpaController implements Serializable {

    public AlergenoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alergeno alergeno) {
        if (alergeno.getAlergenosIngredienteList() == null) {
            alergeno.setAlergenosIngredienteList(new ArrayList<AlergenosIngrediente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AlergenosIngrediente> attachedAlergenosIngredienteList = new ArrayList<AlergenosIngrediente>();
            for (AlergenosIngrediente alergenosIngredienteListAlergenosIngredienteToAttach : alergeno.getAlergenosIngredienteList()) {
                alergenosIngredienteListAlergenosIngredienteToAttach = em.getReference(alergenosIngredienteListAlergenosIngredienteToAttach.getClass(), alergenosIngredienteListAlergenosIngredienteToAttach.getAlergenosIngredientePK());
                attachedAlergenosIngredienteList.add(alergenosIngredienteListAlergenosIngredienteToAttach);
            }
            alergeno.setAlergenosIngredienteList(attachedAlergenosIngredienteList);
            em.persist(alergeno);
            for (AlergenosIngrediente alergenosIngredienteListAlergenosIngrediente : alergeno.getAlergenosIngredienteList()) {
                Alergeno oldAlergeno1OfAlergenosIngredienteListAlergenosIngrediente = alergenosIngredienteListAlergenosIngrediente.getAlergeno1();
                alergenosIngredienteListAlergenosIngrediente.setAlergeno1(alergeno);
                alergenosIngredienteListAlergenosIngrediente = em.merge(alergenosIngredienteListAlergenosIngrediente);
                if (oldAlergeno1OfAlergenosIngredienteListAlergenosIngrediente != null) {
                    oldAlergeno1OfAlergenosIngredienteListAlergenosIngrediente.getAlergenosIngredienteList().remove(alergenosIngredienteListAlergenosIngrediente);
                    oldAlergeno1OfAlergenosIngredienteListAlergenosIngrediente = em.merge(oldAlergeno1OfAlergenosIngredienteListAlergenosIngrediente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alergeno alergeno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alergeno persistentAlergeno = em.find(Alergeno.class, alergeno.getCodAlergeno());
            List<AlergenosIngrediente> alergenosIngredienteListOld = persistentAlergeno.getAlergenosIngredienteList();
            List<AlergenosIngrediente> alergenosIngredienteListNew = alergeno.getAlergenosIngredienteList();
            List<String> illegalOrphanMessages = null;
            for (AlergenosIngrediente alergenosIngredienteListOldAlergenosIngrediente : alergenosIngredienteListOld) {
                if (!alergenosIngredienteListNew.contains(alergenosIngredienteListOldAlergenosIngrediente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AlergenosIngrediente " + alergenosIngredienteListOldAlergenosIngrediente + " since its alergeno1 field is not nullable.");
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
            alergeno.setAlergenosIngredienteList(alergenosIngredienteListNew);
            alergeno = em.merge(alergeno);
            for (AlergenosIngrediente alergenosIngredienteListNewAlergenosIngrediente : alergenosIngredienteListNew) {
                if (!alergenosIngredienteListOld.contains(alergenosIngredienteListNewAlergenosIngrediente)) {
                    Alergeno oldAlergeno1OfAlergenosIngredienteListNewAlergenosIngrediente = alergenosIngredienteListNewAlergenosIngrediente.getAlergeno1();
                    alergenosIngredienteListNewAlergenosIngrediente.setAlergeno1(alergeno);
                    alergenosIngredienteListNewAlergenosIngrediente = em.merge(alergenosIngredienteListNewAlergenosIngrediente);
                    if (oldAlergeno1OfAlergenosIngredienteListNewAlergenosIngrediente != null && !oldAlergeno1OfAlergenosIngredienteListNewAlergenosIngrediente.equals(alergeno)) {
                        oldAlergeno1OfAlergenosIngredienteListNewAlergenosIngrediente.getAlergenosIngredienteList().remove(alergenosIngredienteListNewAlergenosIngrediente);
                        oldAlergeno1OfAlergenosIngredienteListNewAlergenosIngrediente = em.merge(oldAlergeno1OfAlergenosIngredienteListNewAlergenosIngrediente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alergeno.getCodAlergeno();
                if (findAlergeno(id) == null) {
                    throw new NonexistentEntityException("The alergeno with id " + id + " no longer exists.");
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
            Alergeno alergeno;
            try {
                alergeno = em.getReference(Alergeno.class, id);
                alergeno.getCodAlergeno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alergeno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AlergenosIngrediente> alergenosIngredienteListOrphanCheck = alergeno.getAlergenosIngredienteList();
            for (AlergenosIngrediente alergenosIngredienteListOrphanCheckAlergenosIngrediente : alergenosIngredienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alergeno (" + alergeno + ") cannot be destroyed since the AlergenosIngrediente " + alergenosIngredienteListOrphanCheckAlergenosIngrediente + " in its alergenosIngredienteList field has a non-nullable alergeno1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(alergeno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alergeno> findAlergenoEntities() {
        return findAlergenoEntities(true, -1, -1);
    }

    public List<Alergeno> findAlergenoEntities(int maxResults, int firstResult) {
        return findAlergenoEntities(false, maxResults, firstResult);
    }

    private List<Alergeno> findAlergenoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alergeno.class));
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

    public Alergeno findAlergeno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alergeno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlergenoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alergeno> rt = cq.from(Alergeno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
