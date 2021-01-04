package ajax;

/*import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;*/

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import org.json.JSONObject;

public class compruebaNick extends HttpServlet {
// El metodo doGet() se ejecuta una vez por cada peticion HTTP GET.
//HttpServletRequest request;
private String userName;
private String password;
private String url;
private String driver;
String nombre="";



    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
        
    PrintWriter out = response.getWriter();
    String mail="";
    boolean acceso=false;
        try {
           
           
          
            mail =request.getParameter("nick");
           
            acceso=compruebaUsuario(mail);
            
        
          
          out.println(acceso);
            System.out.println(acceso);
 
         //----------------------------------------------------------**/
         out.flush();
           
        
      } catch (Exception ex) {
            out.println(ex);
        }
  
}
public void doPost (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
doGet(request, response);  // Redirecciona la petici�n POST al m�todo doGet()
}


//--------------
public boolean compruebaUsuario(String nick){//, String nombre){
    try {    
        Connection conn = null;
        Statement stmt = null;
        String sqlStr="";
        userName="servidores";
	password="servidores";
        url="jdbc:mysql://localhost/recetas";
	driver="com.mysql.jdbc.Driver";
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(compruebaNick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(compruebaNick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(compruebaNick.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        // Paso 2: Conectarse a la Base de Datos utilizando la clase Connection
        conn = DriverManager.getConnection(url, userName, password);
        
        // Paso 3: Crear sentencias SQL, utilizando objetos de tipo Statement
        stmt = conn.createStatement();
        
        sqlStr ="select * from usuario where nick ='"+ nick+"'";

        // Paso 5: Ejecutar las sentencias SQL a traves de los objetos Statement
        ResultSet rset = stmt.executeQuery(sqlStr);
        
        if ( rset.next()){
              return false;
        }
        else return true;
        
    } catch (SQLException ex) {
        Logger.getLogger(compruebaNick.class.getName()).log(Level.SEVERE, null, ex);
    } 
    return false;
   
}

}
