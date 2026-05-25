package co.uptc.edu.co.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

	private static final String HOST = "mysql-224b3750-proyecto-sistema-gestion-contable.e.aivencloud.com";
	private static final String PORT = "19516";

	private static final String DB_NAME = "proyecto_sistema_gestion_contable";

	private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
			+ "?useSSL=false&allowPublicKeyRetrieval=true";

	private static final String USER = "";
	private static final String PASSWORD = "";

	public static Connection getConexion() throws SQLException {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new SQLException("El driver MySQL no está cargado: " + e.getMessage());
		}
	}
}