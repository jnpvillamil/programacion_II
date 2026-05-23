package co.edu.uptc.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
  
    private static final String HOST = "brgcvw4p6lo2rkyfhafz-mysql.services.clever-cloud.com"; 
    private static final String PORT = "3306";
    private static final String DATABASE = "brgcvw4p6lo2rkyfhafz"; 
    private static final String USER = "u2stcidpnoct95ms";
    private static final String PASSWORD = "hdRDLJ9rDeZTMYhzGDOs";
    
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    private static Connection conexion = null;


    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
         
            	
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("¡Conexión exitosa a Clever Cloud (Europa)!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el conector de MySQL en el POM.xml. " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar a la BD: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión con la BD cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}