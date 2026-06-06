package co.uptc.edu.tienda.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private static final String URL = "jdbc:mariadb://127.0.0.1:3306/tienda_minorista";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
        	System.out.println("Error SQL código: " + e.getErrorCode());
            System.out.println("Error SQL mensaje: " + e.getMessage());
            System.out.println("Error SQL estado: " + e.getSQLState());
            e.printStackTrace();
        }
        return connection;
    }

    public void desconectar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar: " + e.getMessage());
        }
    }
}