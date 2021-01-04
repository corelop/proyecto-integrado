/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respaldos;

import DAO.IngredienteJpaController;
import DAO.IngredientesRecetaJpaController;
import DAO.RecetaJpaController;
import DAO.SolicitudIngredientesJpaController;
import DTO.Ingrediente;
import DTO.IngredientesReceta;
import DTO.IngredientesRecetaPK;
import DTO.Receta;
import DTO.SolicitudIngredientes;
import DTO.Usuario;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CRIS
 */
public class misRecetasRespaldo implements Serializable {

    private EntityManagerFactory emf;
    private List recetasSubidas;
    private List recetasFav;
    private String msj;

    private Part imgReceta;

    private Receta recetaPendiente;

    private RecetaJpaController recetaJPA;
    private IngredienteJpaController ingredienteJPA;
    private IngredientesRecetaJpaController ingredienteRecetaJPA;
    private SolicitudIngredientesJpaController solicitudIngredienteRecetaJPA;

    public misRecetasRespaldo() {

        emf = Persistence.createEntityManagerFactory("FoodLabPU");
        recetaJPA = new RecetaJpaController(emf);
        ingredienteJPA = new IngredienteJpaController(emf);
        ingredienteRecetaJPA = new IngredientesRecetaJpaController(emf);
        solicitudIngredienteRecetaJPA = new SolicitudIngredientesJpaController(emf);
        recetasSubidas = new ArrayList();
        recetasFav = new ArrayList();
        recetaPendiente = new Receta();
        msj = "";

        cargarRecetas();

    }

    public Part getImgReceta() {
        return imgReceta;
    }

    public void setImgReceta(Part imgReceta) {
        this.imgReceta = imgReceta;
    }

    public List getRecetasSubidas() {
        return recetasSubidas;
    }

    public void setRecetasSubidas(List recetasSubidas) {
        this.recetasSubidas = recetasSubidas;
    }

    public List getRecetasFav() {
        return recetasFav;
    }

    public void setRecetasFav(List recetasFav) {
        this.recetasFav = recetasFav;
    }

    public Receta getRecetaPendiente() {
        return recetaPendiente;
    }

    public void setRecetaPendiente(Receta recetaPendiente) {
        this.recetaPendiente = recetaPendiente;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    //**************************************************
    private void cargarRecetas() {

        Usuario usu = obtenerUsu();

        recetasFav = usu.getRecetaList();
        recetasSubidas = usu.getRecetaList1();
    }

    public void addReceta() {

        Usuario usu = obtenerUsu();

        Map<String, String> p = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String nombre = p.get("formNuevaReceta:nombre");
        String preparacion = p.get("formNuevaReceta:preparacion");

        recetaPendiente.setNombre(nombre);
        recetaPendiente.setContenido(preparacion);
        recetaPendiente.setAutor(usu);
        recetaPendiente.setFechaInsercion(new Date());
        recetaPendiente.setAceptada(false);
        recetaPendiente.setFoto(imgReceta.getSubmittedFileName());
      

        recetaJPA.create(recetaPendiente);
        msj = "Solicitud enviada con éxito.";

        for (int i = 0; i < p.size(); i++) {

            if (p.get("n3" + i) != null) {
                Ingrediente ing = ingredienteJPA.ingPorNombre(p.get("n3" + i)).get(0);
                IngredientesReceta ir = new IngredientesReceta(new IngredientesRecetaPK(recetaPendiente.getCodReceta(), ing.getCodIngrediente()));
                ir.setCantidad(p.get("n1" + i));
                ir.setUnidadMedida(p.get("n2" + i));
                ir.setIngrediente1(ing);
                ir.setReceta1(recetaPendiente);
                
                try {
                    ingredienteRecetaJPA.create(ir);
                   
                } catch (Exception ex) {
                    Logger.getLogger(misRecetasRespaldo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        for (int i = 0; i < p.size(); i++) {

            SolicitudIngredientes s = new SolicitudIngredientes();

            if (p.get("s" + i) != null) {
                String v[] = p.get("s" + i).split(",");
                s.setNombre(v[0]);
                s.setCantidad(v[1]);
                if (v.length==3) {
                    s.setDescripcion(v[2]);
                }
                s.setReceta(recetaPendiente);
                s.setUsuario(usu);

                try {
                    solicitudIngredienteRecetaJPA.create(s);
                   
                } catch (Exception ex) {
                    Logger.getLogger(misRecetasRespaldo.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

        String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/img/recetas");

        InputStream input = null;
        File subida = new File(ruta);

        try {
            input = imgReceta.getInputStream();
            File archivo = new File(subida, imgReceta.getSubmittedFileName());

            Files.copy(input, archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(misRecetasRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(misRecetasRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(misRecetasRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private Usuario obtenerUsu() {

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession sesion = (HttpSession) ctx.getSession(true);
        loginRespaldo lr = (loginRespaldo) sesion.getAttribute("loginRespaldo");
        Usuario usu = lr.getUsuarioLogueado();

        return usu;
    }

}
