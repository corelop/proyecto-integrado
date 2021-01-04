/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respaldos;

import DAO.*;
import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Alergeno;
import DTO.AlergenosIngrediente;
import DTO.AlergenosIngredientePK;
import DTO.Ingrediente;
import DTO.IngredientesReceta;
import DTO.IngredientesRecetaPK;
import DTO.Receta;
import DTO.Usuario;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author CRIS
 */
public class adminRespaldo {

    private EntityManagerFactory emf;
    private RecetaJpaController recetaJPA;
    private UsuarioJpaController usuarioJPA;
    private DietaJpaController dietaJPA;
    private IngredienteJpaController ingredienteJPA;
    private IngredientesRecetaJpaController ingredienteRecetaJPA;
    private AlergenosIngredienteJpaController alergenosIngJPA;
    private AlergenoJpaController alergenoJPA;
    private SolicitudIngredientesJpaController solicitudIngJPA;

    private List solicitudes;
    private List usuarios;
    private List recetas;
    private List alergenos;
    private int nSolicitudes;
    private Receta recetaEdit;
    private String[] dietas;
    private String[] alergenosSeleccionados;
    private String cal;
    private String msj;

    private BarChartModel estadisticasTipo;
    private PieChartModel estadisticasAutor;
    private LineChartModel estadisticasMes;

    public adminRespaldo() {

        emf = Persistence.createEntityManagerFactory("FoodLabPU");
        recetaJPA = new RecetaJpaController(emf);
        usuarioJPA = new UsuarioJpaController(emf);
        dietaJPA = new DietaJpaController(emf);
        ingredienteJPA = new IngredienteJpaController(emf);
        ingredienteRecetaJPA = new IngredientesRecetaJpaController(emf);
        alergenoJPA = new AlergenoJpaController(emf);
        alergenosIngJPA = new AlergenosIngredienteJpaController(emf);
        solicitudIngJPA = new SolicitudIngredientesJpaController(emf);

        solicitudes = cargarSolicitudes();
        nSolicitudes = solicitudes.size();

        alergenos = cargarAlergenos();
        usuarios = usuarioJPA.findUsuarioEntities();
        recetas = recetaJPA.findRecetaEntities();
        // alergenosSeleccionados = new String[15];

        recetaEdit = new Receta();
        cargarEstadisticas();
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String[] getAlergenosSeleccionados() {
        return alergenosSeleccionados;
    }

    public void setAlergenosSeleccionados(String[] alergenosSeleccionados) {
        this.alergenosSeleccionados = alergenosSeleccionados;
    }

    public PieChartModel getEstadisticasAutor() {
        return estadisticasAutor;
    }

    public void setEstadisticasAutor(PieChartModel estadisticasAutor) {
        this.estadisticasAutor = estadisticasAutor;
    }

    public LineChartModel getEstadisticasMes() {
        return estadisticasMes;
    }

    public void setEstadisticasMes(LineChartModel estadisticasMes) {
        this.estadisticasMes = estadisticasMes;
    }

    public List getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List usuarios) {
        this.usuarios = usuarios;
    }

    public List getRecetas() {
        return recetas;
    }

    public void setRecetas(List recetas) {
        this.recetas = recetas;
    }

    public List getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(List alergenos) {
        this.alergenos = alergenos;
    }

    public CartesianChartModel getEstadisticasTipo() {
        return estadisticasTipo;
    }

    public void setEstadisticasTipo(BarChartModel estadisticasTipo) {
        this.estadisticasTipo = estadisticasTipo;
    }

    public Receta getRecetaEdit() {
        return recetaEdit;
    }

    public void setRecetaEdit(Receta recetaEdit) {
        this.recetaEdit = recetaEdit;
    }

    public int getnSolicitudes() {
        return nSolicitudes;
    }

    public void setnSolicitudes(int nSolicitudes) {
        this.nSolicitudes = nSolicitudes;
    }

    public List getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List solicitudes) {
        this.solicitudes = solicitudes;
    }

    public String[] getDietas() {
        return dietas;
    }

    public void setDietas(String[] dietas) {
        this.dietas = dietas;
    }

    //***************************************
    private List cargarSolicitudes() {

        List s = new ArrayList();

        s = recetaJPA.recetasPendientes();

        return s;

    }

    public void aceptar(int c) {

        Map<String, String> m = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Collection<String> p = m.values();
        Object[] parametros = p.toArray();
        recetaEdit.setCodReceta(c);

        recetaEdit.setNombre(parametros[1].toString());
        recetaEdit.setContenido(parametros[2].toString());

        for (int i = 0; i < parametros.length; i++) {
            String parametro = parametros[i].toString();
            if (parametro.equals("ing")) {
                IngredientesReceta ingR = new IngredientesReceta();
                ingR.setCantidad(parametros[i + 1].toString());
                ingR.setUnidadMedida(parametros[i + 2].toString());
                ingR.setReceta1(recetaJPA.findReceta(c));
                ingR.setIngrediente1(ingredienteJPA.ingPorNombre(parametros[i + 3].toString()).get(0));
                ingR.setIngredientesRecetaPK(new IngredientesRecetaPK(c, ingredienteJPA.ingPorNombre(parametros[i + 3].toString()).get(0).getCodIngrediente()));

                try {
                    ingredienteRecetaJPA.create(ingR);
                } catch (Exception ex) {
                    Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
                    msj = "Ha ocurrido un error";
                }
            }

        }

        recetaEdit.setFoto(parametros[parametros.length - 8].toString());
        recetaEdit.setAutor(usuarioJPA.findUsuario(parametros[parametros.length - 7].toString()));

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date fecha = formato.parse(parametros[parametros.length - 6].toString());
            recetaEdit.setFechaInsercion(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }

        recetaEdit.setTipoPlato(parametros[parametros.length - 5].toString());
        recetaEdit.setDificultad(parametros[parametros.length - 4].toString());
        recetaEdit.setAceptada(true);

        for (int i = 0; i < dietas.length; i++) {
            String d = dietas[i];
            if (recetaEdit.getDietaList() == null) {
                recetaEdit.setDietaList(new ArrayList());
            }
            recetaEdit.getDietaList().add(dietaJPA.findDieta(Integer.parseInt(d)));
        }

        // recetaEdit.setIngredientesRecetaList(new ArrayList());
        try {
            recetaJPA.edit(recetaEdit);
            msj = "Receta dada de alta correctamente";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            msj = "Ha ocurrido un error";
        } catch (Exception ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            msj = "Ha ocurrido un error";
        }

    }

    public void rechazar(int c) {

        try {
            recetaJPA.destroy(c);
            msj = "Solicitud rechazada correctamente";
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            msj = "Ha ocurrido un error";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            msj = "Ha ocurrido un error";
        }

    }

    public void aceptarIng(ActionEvent evento) {
        Map<String, String> m = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Collection<String> p = m.values();
        Object[] parametros = p.toArray();

        Ingrediente ing = new Ingrediente();
        ing.setNombre(parametros[1].toString());
        ing.setKcal(Float.parseFloat(cal));
        ingredienteJPA.create(ing);
        int codI = ingredienteJPA.ingPorNombre(ing.getNombre()).get(0).getCodIngrediente();
        for (int j = 0; j < alergenosSeleccionados.length; j++) {
            AlergenosIngrediente ai = new AlergenosIngrediente();
            ai.setAlergeno1(alergenoJPA.findAlergeno(Integer.parseInt(alergenosSeleccionados[j])));
            ai.setIngrediente1(ing);
            ai.setAlergenosIngredientePK(new AlergenosIngredientePK(Integer.parseInt(alergenosSeleccionados[j]), ing.getCodIngrediente()));

            try {
                alergenosIngJPA.create(ai);
                msj = "Ingrediente dado de alta correctamente";
            } catch (Exception ex) {
                Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
                msj = "Ha ocurrido un error";
            }
        }

        try {
            IngredientesReceta ir = new IngredientesReceta(new IngredientesRecetaPK(Integer.parseInt(parametros[3].toString()), codI));
            ir.setCantidad(parametros[2].toString().split(" ")[0]);
            ir.setUnidadMedida(parametros[2].toString().split(" ")[1]);
            ir.setIngrediente1(ing);
            ir.setReceta1(recetaJPA.findReceta(Integer.parseInt(parametros[3].toString())));
            ir.setIngredientesRecetaPK(new IngredientesRecetaPK(Integer.parseInt(parametros[3].toString()), codI));
            ingredienteRecetaJPA.create(ir);

            solicitudIngJPA.destroy(Integer.parseInt(parametros[4].toString()));
            recetaJPA.findReceta(Integer.parseInt(parametros[3].toString())).getSolicitudIngredientesList().remove(solicitudIngJPA.findSolicitudIngredientes(Integer.parseInt(parametros[4].toString())));

        } catch (Exception ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            msj = "Ha ocurrido un error";
        }

    }

    public void rechazarIng(int c) {

        Object[] parametros = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().values().toArray();

        try {
            solicitudIngJPA.destroy(c);
            msj = "Solicitud de nuevo ingrediente rechazado correctamente";
            recetaJPA.findReceta(Integer.parseInt(parametros[3].toString())).getSolicitudIngredientesList().remove(solicitudIngJPA.findSolicitudIngredientes(c));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
            msj = "Ha ocurrido un error";
        }

    }

    public void cargarEstadisticas() {

        estadisticasTipo = new BarChartModel();

        final BarChartSeries nRecetas = new BarChartSeries();
        nRecetas.set("Entrante", recetaJPA.recetasPorTipo("entrante").size());
        nRecetas.set("Principal", recetaJPA.recetasPorTipo("principal").size());
        nRecetas.set("Carne", recetaJPA.recetasPorTipo("carne").size());
        nRecetas.set("Pescado", recetaJPA.recetasPorTipo("pescado").size());
        nRecetas.set("Postre", recetaJPA.recetasPorTipo("postre").size());

        estadisticasTipo.setTitle("Número de recetas por tipo de plato");
        Axis ejeX = estadisticasTipo.getAxis(AxisType.X);
        ejeX.setLabel("Tipo plato");
        Axis ejeY = estadisticasTipo.getAxis(AxisType.Y);
        ejeY.setLabel("Num Recetas");
        ejeY.setMin(0);
        ejeY.setTickInterval("1");

        estadisticasTipo.setAnimate(true);
        estadisticasTipo.setShowPointLabels(true);
        estadisticasTipo.setShowDatatip(false);
        estadisticasTipo.addSeries(nRecetas);

        estadisticasAutor = new PieChartModel();
        Map<String, Number> topUsus = new HashMap();

        List datos = recetaJPA.usuariosConMasRecetas();
        for (Object dato : datos) {
            Object[] o = (Object[]) dato;

            topUsus.put(((Usuario) o[1]).getNick(), (Number) o[0]);
        }

        estadisticasAutor.setTitle("Top 5 usuarios que más recetas suben");
        estadisticasAutor.setLegendLabel("Usuarios");
        estadisticasAutor.setData(topUsus);

        estadisticasMes = new LineChartModel();
        final LineChartSeries nRecetasMes = new LineChartSeries();

        List recetas = recetaJPA.recetasPorFecha();

        Calendar c = Calendar.getInstance();
        int m = c.get(Calendar.MONTH);
        int i = 28;
        m = m + 1;
        if ((m == 1) || (m == 3) || (m == 5) || (m == 7) || (m == 8) || (m == 10) || (m == 12)) {
            i = 31;
        }
        if ((m == 4) || (m == 6) || (m == 8) || (m == 9) || (m == 11)) {
            i = 30;
        }

        for (int j = 0; j <= i; j++) {
            int n = 0;
            for (Object dato : recetas) {
                Object[] o = (Object[]) dato;
                String fecha = o[1].toString();
                String[] f = fecha.split(" ");
                int mes = 0;
                switch (f[1]) {
                    case "Ene":
                        mes = 1;
                        break;
                    case "Feb":
                        mes = 2;
                        break;
                    case "Mar":
                        mes = 3;
                        break;
                    case "Abr":
                        mes = 4;
                        break;
                    case "May":
                        mes = 5;
                        break;
                    case "Jun":
                        mes = 6;
                        break;
                    case "Jul":
                        mes = 7;
                        break;
                    case "Ago":
                        mes = 8;
                        break;
                    case "Sep":
                        mes = 9;
                        break;
                    case "Oct":
                        mes = 10;
                        break;
                    case "Nov":
                        mes = 11;
                        break;
                    case "Dic":
                        mes = 12;
                        break;
                }
                if (mes == m && Integer.parseInt(f[2]) == j) {
                    n += Integer.parseInt(o[0].toString());
                }
            }
            nRecetasMes.set(j, n);
        }
        estadisticasMes.setTitle("Recetas subidas por día este mes");
        Axis ejeX1 = estadisticasMes.getAxis(AxisType.X);
        ejeX1.setLabel("Día");
        ejeX1.setMin(1);
        ejeX1.setMax(i);
        ejeX1.setTickInterval("1");
        Axis ejeY1 = estadisticasMes.getAxis(AxisType.Y);
        ejeY1.setLabel("Num Recetas");
        ejeY1.setMin(0);
        ejeY1.setTickInterval("1");

        estadisticasMes.setAnimate(true);
        estadisticasMes.addSeries(nRecetasMes);

    }

    public List cargarAlergenos() {
        List lista = new ArrayList();
        List alergenos = alergenoJPA.findAlergenoEntities();
        for (Object o : alergenos) {
            Alergeno a = (Alergeno) o;
            lista.add(new SelectItem(a.getCodAlergeno(), a.getNombre()));
        }
        return lista;
    }

    public void borrarUsu(String nick) {

        try {
            usuarioJPA.destroy(nick);
            msj = "Usuario eliminado correctamente";
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void borrarReceta(int cod) {
        try {
            recetaJPA.destroy(cod);
            msj = "Receta eliminada correctamente";
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void hacerAdmin(String nick) {

        Usuario usu = usuarioJPA.findUsuario(nick);
        usu.setAdmin(true);
        try {
            usuarioJPA.edit(usu);
            msj = "Cambios guardados";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void quitarAdmin(String nick) {

        if(!nick.equals("FoodLab")){
        Usuario usu = usuarioJPA.findUsuario(nick);
        usu.setAdmin(false);

        try {
            usuarioJPA.edit(usu);
            msj = "Cambios guardados.";
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(adminRespaldo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }else{
          msj = "Ese es el administrador principal, no se puede editar.";
        
        }

    }

}
