/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import DAO.RecetaJpaController;
import DTO.Receta;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author CRIS
 */
public class ultimasRecetas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONObject obj = new JSONObject();
        RecetaJpaController recetaJPA;
        recetaJPA = new RecetaJpaController(Persistence.createEntityManagerFactory("FoodLabPU"));

        try {
            ResultSet datos = conectaBD("SELECT * FROM receta WHERE aceptada = TRUE ORDER BY fecha_insercion DESC LIMIT 0,4");

            while (datos.next()) {
                Receta receta = recetaJPA.findReceta(Integer.parseInt(datos.getString("cod_receta")));

                JSONObject obj2 = new JSONObject();
                obj2.put("cod_receta", receta.getCodReceta());
                obj2.put("nombre", receta.getNombre());
                obj2.put("autor", receta.getAutor().getNick()); 
                obj2.put("fecha_insercion", receta.getFechaInsercion().toString());
                
                obj.put(datos.getString("foto"), obj2);
                
            }

        } catch (InstantiationException ex) {
            Logger.getLogger(ultimasRecetas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ultimasRecetas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ultimasRecetas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ultimasRecetas.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.setContentType("application/json");
        response.getWriter().print(obj.toString());
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    protected ResultSet conectaBD(String consulta) throws InstantiationException, IllegalAccessException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        String sqlStr = "";
        ResultSet rset;
        try {
            //Paso 1: Cargar el driver JDBC.
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            // Paso 2: Conectarse a la Base de Datos utilizando la clase Connection
            String userName = "servidores";
            String password = "servidores";
            //URL de la base de datos(equipo, puerto, base de datos)
            String url = "jdbc:mysql://localhost/recetas";
            conn = DriverManager.getConnection(url, userName, password);
            // Paso 3: Crear sentencias SQL, utilizando objetos de tipo Statement
            stmt = conn.createStatement();

            return rset = stmt.executeQuery(consulta);

        } catch (ClassNotFoundException ex) {
            ex.getMessage();
            ex.printStackTrace();
        }
        return null;

    }//-------------        

}
