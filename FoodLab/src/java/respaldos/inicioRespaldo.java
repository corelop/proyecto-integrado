/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respaldos;

import DAO.DietaJpaController;
import DAO.IngredientesRecetaJpaController;
import DAO.RecetaJpaController;
import DTO.Dieta;
import DTO.IngredientesReceta;
import DTO.Receta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author CRIS
 */
public class inicioRespaldo {

    /**
     * Creates a new instance of inicioRespaldo
     */
    private EntityManagerFactory emf;

    private RecetaJpaController recetaJPA;
    private IngredientesRecetaJpaController ingredientesRecetaJPA;
    private DietaJpaController dietaJPA;
    private ArrayList<Receta> ultimasRecetas;

    private String dietaSeleccionada;
    private ArrayList<SelectItem> dietas;
    private List<String> ingredientes;

    private HtmlInputHidden cod;
    private int codigo;

    private List recetasNombre;
    private String nReceta;
    private boolean pintarRecetas;

    public inicioRespaldo() {
        emf = Persistence.createEntityManagerFactory("FoodLabPU");
        recetaJPA = new RecetaJpaController(emf);
        dietaJPA = new DietaJpaController(emf);
        ingredientesRecetaJPA = new IngredientesRecetaJpaController(emf);
        dietas = cargarDietas();

        pintarRecetas = false;
        recetasNombre = new ArrayList();

    }

    public ArrayList<Receta> getUltimasRecetas() {
        return ultimasRecetas;
    }

    public void setUltimasRecetas(ArrayList<Receta> ultimasRecetas) {
        this.ultimasRecetas = ultimasRecetas;
    }

    public String getDietaSeleccionada() {
        return dietaSeleccionada;
    }

    public void setDietaSeleccionada(String dietaSeleccionada) {
        this.dietaSeleccionada = dietaSeleccionada;
    }

    public ArrayList<SelectItem> getDietas() {
        return dietas;
    }

    public void setDietas(ArrayList<SelectItem> dietas) {
        this.dietas = dietas;
    }

    public HtmlInputHidden getCod() {
        return cod;
    }

    public void setCod(HtmlInputHidden cod) {
        this.cod = cod;
    }

    public List getRecetasNombre() {
        return recetasNombre;
    }

    public void setRecetasNombre(List recetasNombre) {
        this.recetasNombre = recetasNombre;
    }

    public String getnReceta() {
        return nReceta;
    }

    public void setnReceta(String nReceta) {
        this.nReceta = nReceta;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public boolean isPintarRecetas() {
        return pintarRecetas;
    }

    public void setPintarRecetas(boolean pintarRecetas) {
        this.pintarRecetas = pintarRecetas;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    //----------------------
    public ArrayList cargarDietas() {

        ArrayList a = new ArrayList();
        List<Dieta> dietasL = dietaJPA.findDietaEntities();

        for (Object o : dietasL) {
            Dieta d = (Dieta) o;
            a.add(new SelectItem(d.getCodDieta(), d.getTipo()));
        }

        return a;
    }

    public ArrayList datosReceta(String cod) {
        ArrayList a = new ArrayList();

        Receta r = recetaJPA.findReceta(Integer.parseInt(cod));

        a.add(r.getNombre());
        a.add(r.getAutor());
        a.add(r.getFechaInsercion());

        return a;
    }

    public String asignarCod() {

        Map<String, String> p = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if (p.get("formOculto:codRecetaOculto") == null) {

            for (int i = 0; i < 100; i++) {

                Object[] valores = p.values().toArray();
                codigo = Integer.parseInt(valores[1].toString());
                return "ver";

            }

        } else {
            codigo = Integer.parseInt(p.get("formOculto:codRecetaOculto"));
            return "ver";
        }
        //cod = 1;

        return "ver";
    }

    public void buscaNombre() {

        recetasNombre = recetaJPA.buscaRecetasNombre(nReceta);
        pintarRecetas = true;

    }

    public String buscaReceta() {

        String tipo = "";
        Map<String, String> m = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Object[] p = m.values().toArray();

        tipo = p[1].toString();
        recetasNombre = recetaJPA.recetasPorTipo(tipo);
        pintarRecetas = true;
        return "Ver";

    }

    public String buscaIngredientes() {

        recetasNombre = new ArrayList();

        List ir = ingredientesRecetaJPA.findIngredientesRecetaEntities();

        for (Object object : ir) {

            IngredientesReceta ingRec = (IngredientesReceta) object;
            String n = ingRec.getIngrediente1().getNombre();
            if (ingredientes.contains(n)) {
                if (!recetasNombre.contains(ingRec.getReceta1())) {
                    recetasNombre.add(ingRec.getReceta1());
                }

            }

        }

        pintarRecetas = true;
        return "Ver";

    }

    public String buscaReceta2() {

        Collection<String> v = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().values();

        String di = v.toArray()[1].toString();

        List d = dietaJPA.dietasPorTipo(di);
        Dieta dieta = (Dieta) d.get(0);
        recetasNombre = dieta.getRecetaList();
        pintarRecetas = true;
        return "Ver";

    }

    public void buscaDieta() {

        Dieta dieta = dietaJPA.findDieta(Integer.parseInt(dietaSeleccionada));

        recetasNombre = dieta.getRecetaList();
        pintarRecetas = true;

    }

    public String volverInicio() {
        pintarRecetas = false;
        recetasNombre = new ArrayList();

        return "volver";
    }

}
