package co.edu.uptc.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private String bd = "caso_estudio_db"; 
    private String url = "jdbc:mariadb://localhost:3308/" + bd;
    private String user = "root";
    private String pass = ""; 
    private Connection conn = null;

    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("org.mariadb.jdbc.Driver");
                
                Properties props = new Properties();
                props.setProperty("user", user);
                props.setProperty("password", pass);
                props.setProperty("gssapiMechanism", "none"); 
                
                conn = DriverManager.getConnection(url, props);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conn;
    }

    public void desconectar() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar: " + e.getMessage());
        }
    }
}