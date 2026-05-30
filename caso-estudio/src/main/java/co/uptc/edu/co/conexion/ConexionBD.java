package co.uptc.edu.co.conexion;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConexionBD {

	private static final String SERVIDOR = "mysql-256a53-proyecto-sistema-gestion-contable.d.aivencloud.com";
	private static final String PUERTO = "19516";
	private static final String NOMBRE_BASE_DATOS = "proyecto_sistema_gestion_contable";

	private static final String URL = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + NOMBRE_BASE_DATOS
			+ "?useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";

	private static final String USUARIO = "";
	private static final String PASSWORD = "";

	private static final HikariDataSource DATA_SOURCE = crearDataSource();

	private static HikariDataSource crearDataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(URL);
		config.setUsername(USUARIO);
		config.setPassword(PASSWORD);
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setMaximumPoolSize(5);
		config.setMinimumIdle(1);
		config.setConnectionTimeout(10000);
		config.setIdleTimeout(300000);
		config.setMaxLifetime(1800000);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		return new HikariDataSource(config);
	}

	public static Connection getConexion() throws SQLException {
		return DATA_SOURCE.getConnection();
	}

	public static void cerrarPool() {
		if (!DATA_SOURCE.isClosed()) {
			DATA_SOURCE.close();
		}
	}
}
