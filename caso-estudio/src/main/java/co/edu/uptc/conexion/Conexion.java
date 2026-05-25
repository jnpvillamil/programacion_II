package co.edu.uptc.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private String bd = "caso_estudio_db";
	private String url = "jdbc:mariadb://localhost:3306/" + bd;
	private String user = "root";
	private String pass = "";
	private Connection conn = null;
	
	public Conexion() {
		
		try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void desconectar() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

