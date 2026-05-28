package co.edu.uptc.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private Connection conexion;
    private static final String URL = "jdbc:mysql://localhost:3306/tienda_gestion";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "root123";  
    
    public Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(" Error de conexión: " + e.getMessage());
        }
        return conexion;
    }
    
    public void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println(" Conexión cerrada");
            } catch (SQLException e) {
                System.out.println(" Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
