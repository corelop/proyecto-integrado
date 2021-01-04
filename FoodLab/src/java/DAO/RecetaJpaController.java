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
import DTO.Usuario;
import DTO.Dieta;
import java.util.ArrayList;
import java.util.List;
import DTO.SolicitudIngredientes;
import DTO.Comentario;
import DTO.IngredientesReceta;
import DTO.Receta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author CRIS
 */
public class RecetaJpaController implements Serializable {

    public RecetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Receta receta) {
        if (receta.getDietaList() == null) {
            receta.setDietaList(new ArrayList<Dieta>());
        }
        if (receta.getUsuarioList() == null) {
            receta.setUsuarioList(new ArrayList<Usuario>());
        }
        if (receta.getSolicitudIngredientesList() == null) {
            receta.setSolicitudIngredientesList(new ArrayList<SolicitudIngredientes>());
        }
        if (receta.getComentarioList() == null) {
            receta.setComentarioList(new ArrayList<Comentario>());
        }
        if (receta.getIngredientesRecetaList() == null) {
            receta.setIngredientesRecetaList(new ArrayList<IngredientesReceta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario autor = receta.getAutor();
            if (autor != null) {
                autor = em.getReference(autor.getClass(), autor.getNick());
                receta.setAutor(autor);
            }
            List<Dieta> attachedDietaList = new ArrayList<Dieta>();
            for (Dieta dietaListDietaToAttach : receta.getDietaList()) {
                dietaListDietaToAttach = em.getReference(dietaListDietaToAttach.getClass(), dietaListDietaToAttach.getCodDieta());
                attachedDietaList.add(dietaListDietaToAttach);
            }
            receta.setDietaList(attachedDietaList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : receta.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getNick());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            receta.setUsuarioList(attachedUsuarioList);
            List<SolicitudIngredientes> attachedSolicitudIngredientesList = new ArrayList<SolicitudIngredientes>();
            for (SolicitudIngredientes solicitudIngredientesListSolicitudIngredientesToAttach : receta.getSolicitudIngredientesList()) {
                solicitudIngredientesListSolicitudIngredientesToAttach = em.getReference(solicitudIngredientesListSolicitudIngredientesToAttach.getClass(), solicitudIngredientesListSolicitudIngredientesToAttach.getCodSolicitud());
                attachedSolicitudIngredientesList.add(solicitudIngredientesListSolicitudIngredientesToAttach);
            }
            receta.setSolicitudIngredientesList(attachedSolicitudIngredientesList);
            List<Comentario> attachedComentarioList = new ArrayList<Comentario>();
            for (Comentario comentarioListComentarioToAttach : receta.getComentarioList()) {
                comentarioListComentarioToAttach = em.getReference(comentarioListComentarioToAttach.getClass(), comentarioListComentarioToAttach.getComentarioPK());
                attachedComentarioList.add(comentarioListComentarioToAttach);
            }
            receta.setComentarioList(attachedComentarioList);
            List<IngredientesReceta> attachedIngredientesRecetaList = new ArrayList<IngredientesReceta>();
            for (IngredientesReceta ingredientesRecetaListIngredientesRecetaToAttach : receta.getIngredientesRecetaList()) {
                ingredientesRecetaListIngredientesRecetaToAttach = em.getReference(ingredientesRecetaListIngredientesRecetaToAttach.getClass(), ingredientesRecetaListIngredientesRecetaToAttach.getIngredientesRecetaPK());
                attachedIngredientesRecetaList.add(ingredientesRecetaListIngredientesRecetaToAttach);
            }
            receta.setIngredientesRecetaList(attachedIngredientesRecetaList);
            em.persist(receta);
            if (autor != null) {
                autor.getRecetaList().add(receta);
                autor = em.merge(autor);
            }
            for (Dieta dietaListDieta : receta.getDietaList()) {
                dietaListDieta.getRecetaList().add(receta);
                dietaListDieta = em.merge(dietaListDieta);
            }
            for (Usuario usuarioListUsuario : receta.getUsuarioList()) {
                usuarioListUsuario.getRecetaList().add(receta);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            for (SolicitudIngredientes solicitudIngredientesListSolicitudIngredientes : receta.getSolicitudIngredientesList()) {
                Receta oldRecetaOfSolicitudIngredientesListSolicitudIngredientes = solicitudIngredientesListSolicitudIngredientes.getReceta();
                solicitudIngredientesListSolicitudIngredientes.setReceta(receta);
                solicitudIngredientesListSolicitudIngredientes = em.merge(solicitudIngredientesListSolicitudIngredientes);
                if (oldRecetaOfSolicitudIngredientesListSolicitudIngredientes != null) {
                    oldRecetaOfSolicitudIngredientesListSolicitudIngredientes.getSolicitudIngredientesList().remove(solicitudIngredientesListSolicitudIngredientes);
                    oldRecetaOfSolicitudIngredientesListSolicitudIngredientes = em.merge(oldRecetaOfSolicitudIngredientesListSolicitudIngredientes);
                }
            }
            for (Comentario comentarioListComentario : receta.getComentarioList()) {
                Receta oldRecetaOfComentarioListComentario = comentarioListComentario.getReceta();
                comentarioListComentario.setReceta(receta);
                comentarioListComentario = em.merge(comentarioListComentario);
                if (oldRecetaOfComentarioListComentario != null) {
                    oldRecetaOfComentarioListComentario.getComentarioList().remove(comentarioListComentario);
                    oldRecetaOfComentarioListComentario = em.merge(oldRecetaOfComentarioListComentario);
                }
            }
            for (IngredientesReceta ingredientesRecetaListIngredientesReceta : receta.getIngredientesRecetaList()) {
                Receta oldReceta1OfIngredientesRecetaListIngredientesReceta = ingredientesRecetaListIngredientesReceta.getReceta1();
                ingredientesRecetaListIngredientesReceta.setReceta1(receta);
                ingredientesRecetaListIngredientesReceta = em.merge(ingredientesRecetaListIngredientesReceta);
                if (oldReceta1OfIngredientesRecetaListIngredientesReceta != null) {
                    oldReceta1OfIngredientesRecetaListIngredientesReceta.getIngredientesRecetaList().remove(ingredientesRecetaListIngredientesReceta);
                    oldReceta1OfIngredientesRecetaListIngredientesReceta = em.merge(oldReceta1OfIngredientesRecetaListIngredientesReceta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Receta receta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receta persistentReceta = em.find(Receta.class, receta.getCodReceta());
            Usuario autorOld = persistentReceta.getAutor();
            Usuario autorNew = receta.getAutor();
            List<Dieta> dietaListOld = persistentReceta.getDietaList();
            List<Dieta> dietaListNew = receta.getDietaList();
            List<Usuario> usuarioListOld = persistentReceta.getUsuarioList();
            List<Usuario> usuarioListNew = receta.getUsuarioList();
            List<SolicitudIngredientes> solicitudIngredientesListOld = persistentReceta.getSolicitudIngredientesList();
            List<SolicitudIngredientes> solicitudIngredientesListNew = receta.getSolicitudIngredientesList();
            List<Comentario> comentarioListOld = persistentReceta.getComentarioList();
            List<Comentario> comentarioListNew = receta.getComentarioList();
            List<IngredientesReceta> ingredientesRecetaListOld = persistentReceta.getIngredientesRecetaList();
            List<IngredientesReceta> ingredientesRecetaListNew = receta.getIngredientesRecetaList();
            List<String> illegalOrphanMessages = null;
            for (SolicitudIngredientes solicitudIngredientesListOldSolicitudIngredientes : solicitudIngredientesListOld) {
                if (!solicitudIngredientesListNew.contains(solicitudIngredientesListOldSolicitudIngredientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudIngredientes " + solicitudIngredientesListOldSolicitudIngredientes + " since its receta field is not nullable.");
                }
            }
            for (Comentario comentarioListOldComentario : comentarioListOld) {
                if (!comentarioListNew.contains(comentarioListOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioListOldComentario + " since its receta field is not nullable.");
                }
            }
            for (IngredientesReceta ingredientesRecetaListOldIngredientesReceta : ingredientesRecetaListOld) {
                if (ingredientesRecetaListNew != null && !ingredientesRecetaListNew.contains(ingredientesRecetaListOldIngredientesReceta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IngredientesReceta " + ingredientesRecetaListOldIngredientesReceta + " since its receta1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (autorNew != null) {
                autorNew = em.getReference(autorNew.getClass(), autorNew.getNick());
                receta.setAutor(autorNew);
            }
            List<Dieta> attachedDietaListNew = new ArrayList<Dieta>();
            for (Dieta dietaListNewDietaToAttach : dietaListNew) {
                dietaListNewDietaToAttach = em.getReference(dietaListNewDietaToAttach.getClass(), dietaListNewDietaToAttach.getCodDieta());
                attachedDietaListNew.add(dietaListNewDietaToAttach);
            }
            dietaListNew = attachedDietaListNew;
            receta.setDietaList(dietaListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            if (usuarioListNew != null) {
                for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                    usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getNick());
                    attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
                }
                usuarioListNew = attachedUsuarioListNew;
            } else {
                usuarioListNew = new ArrayList<>();
            }
            receta.setUsuarioList(usuarioListNew);
            List<SolicitudIngredientes> attachedSolicitudIngredientesListNew = new ArrayList<SolicitudIngredientes>();
            if (solicitudIngredientesListNew != null) {
                for (SolicitudIngredientes solicitudIngredientesListNewSolicitudIngredientesToAttach : solicitudIngredientesListNew) {
                    solicitudIngredientesListNewSolicitudIngredientesToAttach = em.getReference(solicitudIngredientesListNewSolicitudIngredientesToAttach.getClass(), solicitudIngredientesListNewSolicitudIngredientesToAttach.getCodSolicitud());
                    attachedSolicitudIngredientesListNew.add(solicitudIngredientesListNewSolicitudIngredientesToAttach);
                }
            } else {
                solicitudIngredientesListNew = new ArrayList<>();
            }
            solicitudIngredientesListNew = attachedSolicitudIngredientesListNew;
            receta.setSolicitudIngredientesList(solicitudIngredientesListNew);
            List<Comentario> attachedComentarioListNew = new ArrayList<Comentario>();
            if (comentarioListNew != null) {
                for (Comentario comentarioListNewComentarioToAttach : comentarioListNew) {
                    comentarioListNewComentarioToAttach = em.getReference(comentarioListNewComentarioToAttach.getClass(), comentarioListNewComentarioToAttach.getComentarioPK());
                    attachedComentarioListNew.add(comentarioListNewComentarioToAttach);
                }

            } else {
                comentarioListNew = new ArrayList<>();
            }
            comentarioListNew = attachedComentarioListNew;
            receta.setComentarioList(comentarioListNew);
            List<IngredientesReceta> attachedIngredientesRecetaListNew = new ArrayList<IngredientesReceta>();
             if (ingredientesRecetaListNew != null) {
            for (IngredientesReceta ingredientesRecetaListNewIngredientesRecetaToAttach : ingredientesRecetaListNew) {
                ingredientesRecetaListNewIngredientesRecetaToAttach = em.getReference(ingredientesRecetaListNewIngredientesRecetaToAttach.getClass(), ingredientesRecetaListNewIngredientesRecetaToAttach.getIngredientesRecetaPK());
                attachedIngredientesRecetaListNew.add(ingredientesRecetaListNewIngredientesRecetaToAttach);
            }
             }else {
                ingredientesRecetaListNew = new ArrayList<>();
            }
            ingredientesRecetaListNew = attachedIngredientesRecetaListNew;
            receta.setIngredientesRecetaList(ingredientesRecetaListNew);
            receta = em.merge(receta);
            if (autorOld != null && !autorOld.equals(autorNew)) {
                autorOld.getRecetaList().remove(receta);
                autorOld = em.merge(autorOld);
            }
            if (autorNew != null && !autorNew.equals(autorOld)) {
                autorNew.getRecetaList().add(receta);
                autorNew = em.merge(autorNew);
            }
            for (Dieta dietaListOldDieta : dietaListOld) {
                if (!dietaListNew.contains(dietaListOldDieta)) {
                    dietaListOldDieta.getRecetaList().remove(receta);
                    dietaListOldDieta = em.merge(dietaListOldDieta);
                }
            }
            for (Dieta dietaListNewDieta : dietaListNew) {
                if (!dietaListOld.contains(dietaListNewDieta)) {
                    dietaListNewDieta.getRecetaList().add(receta);
                    dietaListNewDieta = em.merge(dietaListNewDieta);
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.getRecetaList().remove(receta);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    usuarioListNewUsuario.getRecetaList().add(receta);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                }
            }
            for (SolicitudIngredientes solicitudIngredientesListNewSolicitudIngredientes : solicitudIngredientesListNew) {
                if (!solicitudIngredientesListOld.contains(solicitudIngredientesListNewSolicitudIngredientes)) {
                    Receta oldRecetaOfSolicitudIngredientesListNewSolicitudIngredientes = solicitudIngredientesListNewSolicitudIngredientes.getReceta();
                    solicitudIngredientesListNewSolicitudIngredientes.setReceta(receta);
                    solicitudIngredientesListNewSolicitudIngredientes = em.merge(solicitudIngredientesListNewSolicitudIngredientes);
                    if (oldRecetaOfSolicitudIngredientesListNewSolicitudIngredientes != null && !oldRecetaOfSolicitudIngredientesListNewSolicitudIngredientes.equals(receta)) {
                        oldRecetaOfSolicitudIngredientesListNewSolicitudIngredientes.getSolicitudIngredientesList().remove(solicitudIngredientesListNewSolicitudIngredientes);
                        oldRecetaOfSolicitudIngredientesListNewSolicitudIngredientes = em.merge(oldRecetaOfSolicitudIngredientesListNewSolicitudIngredientes);
                    }
                }
            }
            for (Comentario comentarioListNewComentario : comentarioListNew) {
                if (!comentarioListOld.contains(comentarioListNewComentario)) {
                    Receta oldRecetaOfComentarioListNewComentario = comentarioListNewComentario.getReceta();
                    comentarioListNewComentario.setReceta(receta);
                    comentarioListNewComentario = em.merge(comentarioListNewComentario);
                    if (oldRecetaOfComentarioListNewComentario != null && !oldRecetaOfComentarioListNewComentario.equals(receta)) {
                        oldRecetaOfComentarioListNewComentario.getComentarioList().remove(comentarioListNewComentario);
                        oldRecetaOfComentarioListNewComentario = em.merge(oldRecetaOfComentarioListNewComentario);
                    }
                }
            }
            for (IngredientesReceta ingredientesRecetaListNewIngredientesReceta : ingredientesRecetaListNew) {
                if (!ingredientesRecetaListOld.contains(ingredientesRecetaListNewIngredientesReceta)) {
                    Receta oldReceta1OfIngredientesRecetaListNewIngredientesReceta = ingredientesRecetaListNewIngredientesReceta.getReceta1();
                    ingredientesRecetaListNewIngredientesReceta.setReceta1(receta);
                    ingredientesRecetaListNewIngredientesReceta = em.merge(ingredientesRecetaListNewIngredientesReceta);
                    if (oldReceta1OfIngredientesRecetaListNewIngredientesReceta != null && !oldReceta1OfIngredientesRecetaListNewIngredientesReceta.equals(receta)) {
                        oldReceta1OfIngredientesRecetaListNewIngredientesReceta.getIngredientesRecetaList().remove(ingredientesRecetaListNewIngredientesReceta);
                        oldReceta1OfIngredientesRecetaListNewIngredientesReceta = em.merge(oldReceta1OfIngredientesRecetaListNewIngredientesReceta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = receta.getCodReceta();
                if (findReceta(id) == null) {
                    throw new NonexistentEntityException("The receta with id " + id + " no longer exists.");
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
            Receta receta;
            try {
                receta = em.getReference(Receta.class, id);
                receta.getCodReceta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The receta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SolicitudIngredientes> solicitudIngredientesListOrphanCheck = receta.getSolicitudIngredientesList();
            for (SolicitudIngredientes solicitudIngredientesListOrphanCheckSolicitudIngredientes : solicitudIngredientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Receta (" + receta + ") cannot be destroyed since the SolicitudIngredientes " + solicitudIngredientesListOrphanCheckSolicitudIngredientes + " in its solicitudIngredientesList field has a non-nullable receta field.");
            }
            List<Comentario> comentarioListOrphanCheck = receta.getComentarioList();
            for (Comentario comentarioListOrphanCheckComentario : comentarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Receta (" + receta + ") cannot be destroyed since the Comentario " + comentarioListOrphanCheckComentario + " in its comentarioList field has a non-nullable receta field.");
            }
            List<IngredientesReceta> ingredientesRecetaListOrphanCheck = receta.getIngredientesRecetaList();
            for (IngredientesReceta ingredientesRecetaListOrphanCheckIngredientesReceta : ingredientesRecetaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Receta (" + receta + ") cannot be destroyed since the IngredientesReceta " + ingredientesRecetaListOrphanCheckIngredientesReceta + " in its ingredientesRecetaList field has a non-nullable receta1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario autor = receta.getAutor();
            if (autor != null) {
                autor.getRecetaList().remove(receta);
                autor = em.merge(autor);
            }
            List<Dieta> dietaList = receta.getDietaList();
            for (Dieta dietaListDieta : dietaList) {
                dietaListDieta.getRecetaList().remove(receta);
                dietaListDieta = em.merge(dietaListDieta);
            }
            List<Usuario> usuarioList = receta.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.getRecetaList().remove(receta);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            em.remove(receta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Receta> findRecetaEntities() {
        return findRecetaEntities(true, -1, -1);
    }

    public List<Receta> findRecetaEntities(int maxResults, int firstResult) {
        return findRecetaEntities(false, maxResults, firstResult);
    }

    private List<Receta> findRecetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Receta.class));
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

    public Receta findReceta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Receta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Receta> rt = cq.from(Receta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List buscaRecetasNombre(String n) {

        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT r FROM Receta r WHERE r.nombre LIKE '%" + n + "%'");

            return q.getResultList();
        } finally {
            em.close();
        }

    }

    public List<Receta> recetasPorTipo(String t) {

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Receta> tq = em.createNamedQuery("Receta.findByTipoPlato", Receta.class);
            tq.setParameter("tipoPlato", t);

            TypedQuery<Receta> tq2 = em.createNamedQuery("Receta.findByAceptada", Receta.class);
            tq2.setParameter("aceptada", true);

            List resultado = new ArrayList();

            List l1 = tq.getResultList();
            List l2 = tq2.getResultList();

            resultado.addAll(l1);
            resultado.retainAll(l2);

            return resultado;
        } finally {
            em.close();
        }

    }

    public List<Receta> recetasPendientes() {

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Receta> tq = em.createNamedQuery("Receta.findByAceptada", Receta.class);
            tq.setParameter("aceptada", false);
            return tq.getResultList();
        } finally {
            em.close();
        }

    }

    public List usuariosConMasRecetas() {

        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT count(r), r.autor FROM Receta r GROUP BY r.autor");
            return q.getResultList();
        } finally {
            em.close();
        }

    }

    public List recetasPorFecha() {

        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT count(r), r.fechaInsercion FROM Receta r GROUP BY r.fechaInsercion");
            return q.getResultList();
        } finally {
            em.close();
        }

    }

}
