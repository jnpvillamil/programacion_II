package co.edu.uptc.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private String db = "caso_estudio_db";
    
    private String url = "jdbc:mysql://localhost:3308/" + db + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    
    private String user = "root";
    private String pass = ""; 

    private Connection connection = null;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
            
            if (connection != null) {
                System.out.println(">>> ¡Conexión exitosa con la base de datos en el puerto 3308!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: No se encontró el Driver JAR. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("ERROR SQL: " + e.getMessage());
        }
        return connection;
    }

    public void desconectar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println(">>> Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar: " + e.getMessage());
        }
    }
}