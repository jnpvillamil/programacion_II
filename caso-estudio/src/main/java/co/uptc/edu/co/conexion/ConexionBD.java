package co.uptc.edu.co.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

	private static final String SERVIDOR = "mysql-224b3750-proyecto-sistema-gestion-contable.e.aivencloud.com";
	private static final String PUERTO = "19516";

	private static final String NOMBRE_BASE_DATOS = "proyecto_sistema_gestion_contable";

	private static final String URL = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + NOMBRE_BASE_DATOS
			+ "?useSSL=false&allowPublicKeyRetrieval=true";

	private static final String USUARIO = "";
	private static final String PASSWORD = "";

	public static Connection getConexion() throws SQLException {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USUARIO, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new SQLException("El driver MySQL no está cargado: " + e.getMessage());
		}
	}
	
	public static void desconectar(Connection conexion) {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
			}
		}catch(SQLException e) {
			System.out.println("Error al cerrar conexion: " + e.getMessage());
		}
		
		
	}
}