/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respaldos;

import DAO.UsuarioJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

/**
 *
 * @author CRIS
 */
public class loginRespaldo {

    private String usuario;
    private String pass;

    private EntityManagerFactory emf;
    private UsuarioJpaController usuarioJPA;

    private Usuario usu;
    private Usuario usuR;
    private Usuario usuarioLogueado;
    private String msj;
    private String descripcion;
    private String passNew;
    private String confirmPass;

    private boolean logueoOK;

    public loginRespaldo() {

        emf = Persistence.createEntityManagerFactory("FoodLabPU");
        usuarioJPA = new UsuarioJpaController(emf);
        usu = new Usuario();
        usuR = new Usuario();
        logueoOK = false;
        descripcion = "Explora, comenta y descubre nuevas recetas cada día. \n\n Puntúa y comparte las recetas de los demás usuarios.";

        FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error de validación ", "Las contraseñas no coinciden."));

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    

    public String getPassNew() {
        return passNew;
    }

    public void setPassNew(String passNew) {
        this.passNew = passNew;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

    public Usuario getUsuR() {
        return usuR;
    }

    public void setUsuR(Usuario usuR) {
        this.usuR = usuR;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isLogueoOK() {
        return logueoOK;
    }

    public void setLogueoOK(boolean logueoOK) {
        this.logueoOK = logueoOK;
    }

    //************************/
    public void comprobarDatos() {

        String msj = "";

        usu = usuarioJPA.findUsuario(usuario);

        if (usu != null) {
            if (usu.getPassword().equals(pass)) {
                usuarioLogueado = usu;
                logueoOK = true;
                descripcion = "";

            } else {
                pass = "";
                usuario = "";
                descripcion = "Contraseña o usuario incorrecto";
            }
        } else {
            pass = "";
            usuario = "";
            descripcion = "Contraseña o usuario incorrecto";
        }

    }

    public void registro() {

        usuR.setAdmin(false);

        try {
            usuarioJPA.create(usuR);
            usuarioLogueado = usuR;
            logueoOK = true;
            descripcion = "";

        } catch (Exception ex) {
            Logger.getLogger(loginRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void logout() {

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ((HttpSession) ctx.getSession(false)).invalidate();
            usu = new Usuario();
            usuR = new Usuario();
            logueoOK = false;
            ctx.redirect("index.xhtml");

        } catch (Exception ex) {
        }
    }

    public void editarPerfil() {

        msj = "";
        try {
            usuarioJPA.edit(usuarioLogueado);
            msj = "Datos actualizados correctamente.";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(loginRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(loginRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cambioPass() {

        if (!confirmPass.equals(usuarioLogueado.getPassword())) {
   
              FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage("Contraseña incorrecta", "La contraseña introducida no es correcta"));
 
        } else {
            usuarioLogueado.setPassword(passNew);
            try {
                usuarioJPA.edit(usuarioLogueado);
                 FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage("Contraseña actualizada correctamente"));
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(loginRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(loginRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
