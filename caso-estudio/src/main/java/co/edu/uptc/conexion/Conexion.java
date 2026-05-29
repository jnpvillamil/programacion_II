package co.edu.uptc.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	// Rutas y credenciales por defecto de XAMPP
	private static final String URL = "jdbc:mysql://localhost:3306/marketsys";
	private static final String USUARIO = "root";
	private static final String CLAVE = ""; // En XAMPP, la contraseña suele estar vacía

	private Connection connection;

	public Conexion() {
		try {
			// 1. Nos aseguramos de que el Driver esté cargado en el proyecto
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. Establecemos la conexión con la base de datos
			connection = DriverManager.getConnection(URL, USUARIO, CLAVE);
			System.out.println("✅ ¡Conexión a MySQL (mitienda) exitosa!");

		} catch (ClassNotFoundException e) {
			System.err.println("❌ Error: Falta el Driver (mysql-connector.jar) en el Build Path.");
		} catch (SQLException e) {
			System.err.println("❌ Error de credenciales o la base de datos no está encendida: " + e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void desconectar() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("🔒 Conexión cerrada correctamente.");
			}
		} catch (SQLException e) {
			System.err.println("Error al cerrar la conexión: " + e.getMessage());
		}
	}
}