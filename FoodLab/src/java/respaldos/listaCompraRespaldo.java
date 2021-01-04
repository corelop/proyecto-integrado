/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respaldos;

import DAO.IngredienteJpaController;
import DAO.ListaCompraJpaController;
import DAO.UsuarioJpaController;
import DAO.exceptions.NonexistentEntityException;
import DTO.Ingrediente;
import DTO.ListaCompra;
import DTO.ListaCompraPK;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author CRIS
 */
public class listaCompraRespaldo {

    private EntityManagerFactory emf;
    private UsuarioJpaController usuarioJPA;
    private IngredienteJpaController ingredienteJPA;
    private ListaCompraJpaController listaComptraJPA;
    private List listaCompra;
    private List ingredientes;
    private int ing;
    private String msj;
    

    private String ctd;
    private String unidad;

    public listaCompraRespaldo() {
        emf = Persistence.createEntityManagerFactory("FoodLabPU");
        usuarioJPA = new UsuarioJpaController(emf);
        ingredienteJPA = new IngredienteJpaController(emf);
        listaComptraJPA = new ListaCompraJpaController(emf);
        ingredientes = cargaIngredientes();
        listaCompra = usuarioJPA.usuarioConectado().getListaCompraList();
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    
    
    public List getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List ingredientes) {
        this.ingredientes = ingredientes;
    }

    public int getIng() {
        return ing;
    }

    public void setIng(int ing) {
        this.ing = ing;
    }

    public List getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(List listaCompra) {
        this.listaCompra = listaCompra;
    }

    public String getCtd() {
        return ctd;
    }

    public void setCtd(String ctd) {
        this.ctd = ctd;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    //*******************************************************
    public void addLista() {

        ListaCompra nuevaEntrada = new ListaCompra();
        if (!listaComptraJPA.listaPorIng(ing).isEmpty()) {
            ListaCompra lc = listaComptraJPA.listaPorIng(ing).get(0);
            if (lc.getUnidad().equals(unidad)) {
                String c = lc.getCantidad();
                int n, n2, r;
                if (c.split("/").length == 2) {
                    n = Integer.parseInt((c.split("/")[0])) / Integer.parseInt((c.split("/")[1]));
                } else {
                    n = Integer.parseInt(c);
                }
                n2 = Integer.parseInt(ctd);
                r = n + n2;
                lc.setCantidad(String.valueOf(r));
                try {
                    listaComptraJPA.edit(lc);
                } catch (Exception ex) {
                    Logger.getLogger(listaCompraRespaldo.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    nuevaEntrada.setListaCompraPK((new ListaCompraPK(usuarioJPA.usuarioConectado().getNick(), ing)));
                    nuevaEntrada.setCantidad(ctd);
                    nuevaEntrada.setUnidad(unidad);
                    nuevaEntrada.setIngrediente1(ingredienteJPA.findIngrediente(ing));
                    nuevaEntrada.setUsuario1(usuarioJPA.usuarioConectado());
                    listaComptraJPA.create(nuevaEntrada);
                } catch (Exception ex) {
                    Logger.getLogger(listaCompraRespaldo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                nuevaEntrada.setListaCompraPK((new ListaCompraPK(usuarioJPA.usuarioConectado().getNick(), ing)));
                nuevaEntrada.setCantidad(ctd);
                nuevaEntrada.setUnidad(unidad);
                nuevaEntrada.setIngrediente1(ingredienteJPA.findIngrediente(ing));
                nuevaEntrada.setUsuario1(usuarioJPA.usuarioConectado());
                listaComptraJPA.create(nuevaEntrada);
                listaCompra.add(nuevaEntrada);
                usuarioJPA.usuarioConectado().setListaCompraList(listaCompra);
                msj="Ingrediente añadido correctamente.";
            } catch (Exception ex) {
                Logger.getLogger(listaCompraRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
         
    }

    public List cargaIngredientes() {
        List v = new ArrayList();
        List i = ingredienteJPA.findIngredienteEntities();
        for (Object object : i) {
            Ingrediente ing = (Ingrediente) object;
            v.add(new SelectItem(ing.getCodIngrediente(), ing.getNombre()));
        }

        Collections.sort(v, new Comparator<SelectItem>() {
            @Override
            public int compare(SelectItem o1, SelectItem o2) {
                return o1.getLabel().compareTo(o2.getLabel());
            }
        });

        return v;
    }
    
    public void eliminar(ListaCompraPK clave){
    
        try {
            usuarioJPA.usuarioConectado().getListaCompraList().remove(listaComptraJPA.findListaCompra(clave));
            listaComptraJPA.destroy(clave);
             msj="Ingrediente eliminado correctamente.";
           
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(listaCompraRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
