/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Receta;
import java.util.ArrayList;
import java.util.List;
import DTO.SolicitudIngredientes;
import DTO.ListaCompra;
import DTO.Comentario;
import DTO.Usuario;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;
import respaldos.loginRespaldo;

/**
 *
 * @author CRIS
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getRecetaList() == null) {
            usuario.setRecetaList(new ArrayList<Receta>());
        }
        if (usuario.getRecetaList1() == null) {
            usuario.setRecetaList1(new ArrayList<Receta>());
        }
        if (usuario.getSolicitudIngredientesList() == null) {
            usuario.setSolicitudIngredientesList(new ArrayList<SolicitudIngredientes>());
        }
        if (usuario.getListaCompraList() == null) {
            usuario.setListaCompraList(new ArrayList<ListaCompra>());
        }
        if (usuario.getComentarioList() == null) {
            usuario.setComentarioList(new ArrayList<Comentario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Receta> attachedRecetaList = new ArrayList<Receta>();
            for (Receta recetaListRecetaToAttach : usuario.getRecetaList()) {
                recetaListRecetaToAttach = em.getReference(recetaListRecetaToAttach.getClass(), recetaListRecetaToAttach.getCodReceta());
                attachedRecetaList.add(recetaListRecetaToAttach);
            }
            usuario.setRecetaList(attachedRecetaList);
            List<Receta> attachedRecetaList1 = new ArrayList<Receta>();
            for (Receta recetaList1RecetaToAttach : usuario.getRecetaList1()) {
                recetaList1RecetaToAttach = em.getReference(recetaList1RecetaToAttach.getClass(), recetaList1RecetaToAttach.getCodReceta());
                attachedRecetaList1.add(recetaList1RecetaToAttach);
            }
            usuario.setRecetaList1(attachedRecetaList1);
            List<SolicitudIngredientes> attachedSolicitudIngredientesList = new ArrayList<SolicitudIngredientes>();
            for (SolicitudIngredientes solicitudIngredientesListSolicitudIngredientesToAttach : usuario.getSolicitudIngredientesList()) {
                solicitudIngredientesListSolicitudIngredientesToAttach = em.getReference(solicitudIngredientesListSolicitudIngredientesToAttach.getClass(), solicitudIngredientesListSolicitudIngredientesToAttach.getCodSolicitud());
                attachedSolicitudIngredientesList.add(solicitudIngredientesListSolicitudIngredientesToAttach);
            }
            usuario.setSolicitudIngredientesList(attachedSolicitudIngredientesList);
            List<ListaCompra> attachedListaCompraList = new ArrayList<ListaCompra>();
            for (ListaCompra listaCompraListListaCompraToAttach : usuario.getListaCompraList()) {
                listaCompraListListaCompraToAttach = em.getReference(listaCompraListListaCompraToAttach.getClass(), listaCompraListListaCompraToAttach.getListaCompraPK());
                attachedListaCompraList.add(listaCompraListListaCompraToAttach);
            }
            usuario.setListaCompraList(attachedListaCompraList);
            List<Comentario> attachedComentarioList = new ArrayList<Comentario>();
            for (Comentario comentarioListComentarioToAttach : usuario.getComentarioList()) {
                comentarioListComentarioToAttach = em.getReference(comentarioListComentarioToAttach.getClass(), comentarioListComentarioToAttach.getComentarioPK());
                attachedComentarioList.add(comentarioListComentarioToAttach);
            }
            usuario.setComentarioList(attachedComentarioList);
            em.persist(usuario);
            for (Receta recetaListReceta : usuario.getRecetaList()) {
                recetaListReceta.getUsuarioList().add(usuario);
                recetaListReceta = em.merge(recetaListReceta);
            }
            for (Receta recetaList1Receta : usuario.getRecetaList1()) {
                Usuario oldAutorOfRecetaList1Receta = recetaList1Receta.getAutor();
                recetaList1Receta.setAutor(usuario);
                recetaList1Receta = em.merge(recetaList1Receta);
                if (oldAutorOfRecetaList1Receta != null) {
                    oldAutorOfRecetaList1Receta.getRecetaList1().remove(recetaList1Receta);
                    oldAutorOfRecetaList1Receta = em.merge(oldAutorOfRecetaList1Receta);
                }
            }
            for (SolicitudIngredientes solicitudIngredientesListSolicitudIngredientes : usuario.getSolicitudIngredientesList()) {
                Usuario oldUsuarioOfSolicitudIngredientesListSolicitudIngredientes = solicitudIngredientesListSolicitudIngredientes.getUsuario();
                solicitudIngredientesListSolicitudIngredientes.setUsuario(usuario);
                solicitudIngredientesListSolicitudIngredientes = em.merge(solicitudIngredientesListSolicitudIngredientes);
                if (oldUsuarioOfSolicitudIngredientesListSolicitudIngredientes != null) {
                    oldUsuarioOfSolicitudIngredientesListSolicitudIngredientes.getSolicitudIngredientesList().remove(solicitudIngredientesListSolicitudIngredientes);
                    oldUsuarioOfSolicitudIngredientesListSolicitudIngredientes = em.merge(oldUsuarioOfSolicitudIngredientesListSolicitudIngredientes);
                }
            }
            for (ListaCompra listaCompraListListaCompra : usuario.getListaCompraList()) {
                Usuario oldUsuario1OfListaCompraListListaCompra = listaCompraListListaCompra.getUsuario1();
                listaCompraListListaCompra.setUsuario1(usuario);
                listaCompraListListaCompra = em.merge(listaCompraListListaCompra);
                if (oldUsuario1OfListaCompraListListaCompra != null) {
                    oldUsuario1OfListaCompraListListaCompra.getListaCompraList().remove(listaCompraListListaCompra);
                    oldUsuario1OfListaCompraListListaCompra = em.merge(oldUsuario1OfListaCompraListListaCompra);
                }
            }
            for (Comentario comentarioListComentario : usuario.getComentarioList()) {
                Usuario oldUsuario1OfComentarioListComentario = comentarioListComentario.getUsuario1();
                comentarioListComentario.setUsuario1(usuario);
                comentarioListComentario = em.merge(comentarioListComentario);
                if (oldUsuario1OfComentarioListComentario != null) {
                    oldUsuario1OfComentarioListComentario.getComentarioList().remove(comentarioListComentario);
                    oldUsuario1OfComentarioListComentario = em.merge(oldUsuario1OfComentarioListComentario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getNick()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getNick());
            List<Receta> recetaListOld = persistentUsuario.getRecetaList();
            List<Receta> recetaListNew = usuario.getRecetaList();
            List<Receta> recetaList1Old = persistentUsuario.getRecetaList1();
            List<Receta> recetaList1New = usuario.getRecetaList1();
            List<SolicitudIngredientes> solicitudIngredientesListOld = persistentUsuario.getSolicitudIngredientesList();
            List<SolicitudIngredientes> solicitudIngredientesListNew = usuario.getSolicitudIngredientesList();
            List<ListaCompra> listaCompraListOld = persistentUsuario.getListaCompraList();
            List<ListaCompra> listaCompraListNew = usuario.getListaCompraList();
            List<Comentario> comentarioListOld = persistentUsuario.getComentarioList();
            List<Comentario> comentarioListNew = usuario.getComentarioList();
            List<String> illegalOrphanMessages = null;
            for (Receta recetaList1OldReceta : recetaList1Old) {
                if (!recetaList1New.contains(recetaList1OldReceta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Receta " + recetaList1OldReceta + " since its autor field is not nullable.");
                }
            }
            for (SolicitudIngredientes solicitudIngredientesListOldSolicitudIngredientes : solicitudIngredientesListOld) {
                if (!solicitudIngredientesListNew.contains(solicitudIngredientesListOldSolicitudIngredientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudIngredientes " + solicitudIngredientesListOldSolicitudIngredientes + " since its usuario field is not nullable.");
                }
            }
            for (ListaCompra listaCompraListOldListaCompra : listaCompraListOld) {
                if (!listaCompraListNew.contains(listaCompraListOldListaCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ListaCompra " + listaCompraListOldListaCompra + " since its usuario1 field is not nullable.");
                }
            }
            for (Comentario comentarioListOldComentario : comentarioListOld) {
                if (!comentarioListNew.contains(comentarioListOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioListOldComentario + " since its usuario1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Receta> attachedRecetaListNew = new ArrayList<Receta>();
            for (Receta recetaListNewRecetaToAttach : recetaListNew) {
                recetaListNewRecetaToAttach = em.getReference(recetaListNewRecetaToAttach.getClass(), recetaListNewRecetaToAttach.getCodReceta());
                attachedRecetaListNew.add(recetaListNewRecetaToAttach);
            }
            recetaListNew = attachedRecetaListNew;
            usuario.setRecetaList(recetaListNew);
            List<Receta> attachedRecetaList1New = new ArrayList<Receta>();
            for (Receta recetaList1NewRecetaToAttach : recetaList1New) {
                recetaList1NewRecetaToAttach = em.getReference(recetaList1NewRecetaToAttach.getClass(), recetaList1NewRecetaToAttach.getCodReceta());
                attachedRecetaList1New.add(recetaList1NewRecetaToAttach);
            }
            recetaList1New = attachedRecetaList1New;
            usuario.setRecetaList1(recetaList1New);
            List<SolicitudIngredientes> attachedSolicitudIngredientesListNew = new ArrayList<SolicitudIngredientes>();
            for (SolicitudIngredientes solicitudIngredientesListNewSolicitudIngredientesToAttach : solicitudIngredientesListNew) {
                solicitudIngredientesListNewSolicitudIngredientesToAttach = em.getReference(solicitudIngredientesListNewSolicitudIngredientesToAttach.getClass(), solicitudIngredientesListNewSolicitudIngredientesToAttach.getCodSolicitud());
                attachedSolicitudIngredientesListNew.add(solicitudIngredientesListNewSolicitudIngredientesToAttach);
            }
            solicitudIngredientesListNew = attachedSolicitudIngredientesListNew;
            usuario.setSolicitudIngredientesList(solicitudIngredientesListNew);
            List<ListaCompra> attachedListaCompraListNew = new ArrayList<ListaCompra>();
            for (ListaCompra listaCompraListNewListaCompraToAttach : listaCompraListNew) {
                listaCompraListNewListaCompraToAttach = em.getReference(listaCompraListNewListaCompraToAttach.getClass(), listaCompraListNewListaCompraToAttach.getListaCompraPK());
                attachedListaCompraListNew.add(listaCompraListNewListaCompraToAttach);
            }
            listaCompraListNew = attachedListaCompraListNew;
            usuario.setListaCompraList(listaCompraListNew);
            List<Comentario> attachedComentarioListNew = new ArrayList<Comentario>();
            for (Comentario comentarioListNewComentarioToAttach : comentarioListNew) {
                comentarioListNewComentarioToAttach = em.getReference(comentarioListNewComentarioToAttach.getClass(), comentarioListNewComentarioToAttach.getComentarioPK());
                attachedComentarioListNew.add(comentarioListNewComentarioToAttach);
            }
            comentarioListNew = attachedComentarioListNew;
            usuario.setComentarioList(comentarioListNew);
            usuario = em.merge(usuario);
            for (Receta recetaListOldReceta : recetaListOld) {
                if (!recetaListNew.contains(recetaListOldReceta)) {
                    recetaListOldReceta.getUsuarioList().remove(usuario);
                    recetaListOldReceta = em.merge(recetaListOldReceta);
                }
            }
            for (Receta recetaListNewReceta : recetaListNew) {
                if (!recetaListOld.contains(recetaListNewReceta)) {
                    recetaListNewReceta.getUsuarioList().add(usuario);
                    recetaListNewReceta = em.merge(recetaListNewReceta);
                }
            }
            for (Receta recetaList1NewReceta : recetaList1New) {
                if (!recetaList1Old.contains(recetaList1NewReceta)) {
                    Usuario oldAutorOfRecetaList1NewReceta = recetaList1NewReceta.getAutor();
                    recetaList1NewReceta.setAutor(usuario);
                    recetaList1NewReceta = em.merge(recetaList1NewReceta);
                    if (oldAutorOfRecetaList1NewReceta != null && !oldAutorOfRecetaList1NewReceta.equals(usuario)) {
                        oldAutorOfRecetaList1NewReceta.getRecetaList1().remove(recetaList1NewReceta);
                        oldAutorOfRecetaList1NewReceta = em.merge(oldAutorOfRecetaList1NewReceta);
                    }
                }
            }
            for (SolicitudIngredientes solicitudIngredientesListNewSolicitudIngredientes : solicitudIngredientesListNew) {
                if (!solicitudIngredientesListOld.contains(solicitudIngredientesListNewSolicitudIngredientes)) {
                    Usuario oldUsuarioOfSolicitudIngredientesListNewSolicitudIngredientes = solicitudIngredientesListNewSolicitudIngredientes.getUsuario();
                    solicitudIngredientesListNewSolicitudIngredientes.setUsuario(usuario);
                    solicitudIngredientesListNewSolicitudIngredientes = em.merge(solicitudIngredientesListNewSolicitudIngredientes);
                    if (oldUsuarioOfSolicitudIngredientesListNewSolicitudIngredientes != null && !oldUsuarioOfSolicitudIngredientesListNewSolicitudIngredientes.equals(usuario)) {
                        oldUsuarioOfSolicitudIngredientesListNewSolicitudIngredientes.getSolicitudIngredientesList().remove(solicitudIngredientesListNewSolicitudIngredientes);
                        oldUsuarioOfSolicitudIngredientesListNewSolicitudIngredientes = em.merge(oldUsuarioOfSolicitudIngredientesListNewSolicitudIngredientes);
                    }
                }
            }
            for (ListaCompra listaCompraListNewListaCompra : listaCompraListNew) {
                if (!listaCompraListOld.contains(listaCompraListNewListaCompra)) {
                    Usuario oldUsuario1OfListaCompraListNewListaCompra = listaCompraListNewListaCompra.getUsuario1();
                    listaCompraListNewListaCompra.setUsuario1(usuario);
                    listaCompraListNewListaCompra = em.merge(listaCompraListNewListaCompra);
                    if (oldUsuario1OfListaCompraListNewListaCompra != null && !oldUsuario1OfListaCompraListNewListaCompra.equals(usuario)) {
                        oldUsuario1OfListaCompraListNewListaCompra.getListaCompraList().remove(listaCompraListNewListaCompra);
                        oldUsuario1OfListaCompraListNewListaCompra = em.merge(oldUsuario1OfListaCompraListNewListaCompra);
                    }
                }
            }
            for (Comentario comentarioListNewComentario : comentarioListNew) {
                if (!comentarioListOld.contains(comentarioListNewComentario)) {
                    Usuario oldUsuario1OfComentarioListNewComentario = comentarioListNewComentario.getUsuario1();
                    comentarioListNewComentario.setUsuario1(usuario);
                    comentarioListNewComentario = em.merge(comentarioListNewComentario);
                    if (oldUsuario1OfComentarioListNewComentario != null && !oldUsuario1OfComentarioListNewComentario.equals(usuario)) {
                        oldUsuario1OfComentarioListNewComentario.getComentarioList().remove(comentarioListNewComentario);
                        oldUsuario1OfComentarioListNewComentario = em.merge(oldUsuario1OfComentarioListNewComentario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getNick();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getNick();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Receta> recetaList1OrphanCheck = usuario.getRecetaList1();
            for (Receta recetaList1OrphanCheckReceta : recetaList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Receta " + recetaList1OrphanCheckReceta + " in its recetaList1 field has a non-nullable autor field.");
            }
            List<SolicitudIngredientes> solicitudIngredientesListOrphanCheck = usuario.getSolicitudIngredientesList();
            for (SolicitudIngredientes solicitudIngredientesListOrphanCheckSolicitudIngredientes : solicitudIngredientesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the SolicitudIngredientes " + solicitudIngredientesListOrphanCheckSolicitudIngredientes + " in its solicitudIngredientesList field has a non-nullable usuario field.");
            }
            List<ListaCompra> listaCompraListOrphanCheck = usuario.getListaCompraList();
            for (ListaCompra listaCompraListOrphanCheckListaCompra : listaCompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the ListaCompra " + listaCompraListOrphanCheckListaCompra + " in its listaCompraList field has a non-nullable usuario1 field.");
            }
            List<Comentario> comentarioListOrphanCheck = usuario.getComentarioList();
            for (Comentario comentarioListOrphanCheckComentario : comentarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Comentario " + comentarioListOrphanCheckComentario + " in its comentarioList field has a non-nullable usuario1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Receta> recetaList = usuario.getRecetaList();
            for (Receta recetaListReceta : recetaList) {
                recetaListReceta.getUsuarioList().remove(usuario);
                recetaListReceta = em.merge(recetaListReceta);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public Usuario usuarioConectado() {

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession sesion = (HttpSession) ctx.getSession(true);
        loginRespaldo ir = (loginRespaldo) sesion.getAttribute("loginRespaldo");
        return ir.getUsuarioLogueado();

    }

    
}
