/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author CRIS
 */
public class buscarIngredientes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String ingrediente = request.getParameter("ingrediente");
   

        JSONObject obj = new JSONObject();

        if (ingrediente != null) {

            try {

                ResultSet datos = conectaBD("SELECT i.nombre FROM ingrediente i WHERE i.nombre LIKE '"+ingrediente+"%' ORDER BY i.nombre");
              
                while (datos.next()) {

                    obj.put(datos.getString(1), datos.getString(1));

    
                }
           
            } catch (SQLException | JSONException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(buscarIngredientes.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        response.setContentType("application/json");
        response.getWriter().print(obj.toString());

    }//doPost

    //----------
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);

    }
    //----------

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
