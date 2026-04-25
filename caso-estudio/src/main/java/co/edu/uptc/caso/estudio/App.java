package co.edu.uptc.caso.estudio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
	static String bd = "caso_estudio";
	   static String login = "root";
	   static String password = "";
	   static String url = "jdbc:mariadb://localhost/"+bd;

	   static Connection conn = null;

    public static void main( String[] args )
    {
    	 try{
             //obtenemos el driver de para mysql
             Class.forName("org.mariadb.jdbc.Driver");
             //obtenemos la conexión
             conn = DriverManager.getConnection(url,login,password);

             if (conn!=null){
                System.out.println("Conección a base de datos "+bd+" OK");
             }
          }
          catch(SQLException e){
             System.out.println(e);
          }catch(ClassNotFoundException e){
             System.out.println(e);
          }catch(Exception e){
             System.out.println(e);
          }
    }
}
