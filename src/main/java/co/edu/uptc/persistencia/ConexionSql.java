package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Punto único de acceso JDBC hacia MySQL Cloud (Aiven).
 */
public final class ConexionSql {

    private static final String URL =
            "jdbc:mysql://mysql-tiendaminorista26-uptc-2026.e.aivencloud.com:19414/defaultdb"
            + "?useSSL=true&trustServerCertificate=true&serverTimezone=UTC";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_XgqVf9CJ12bcfuHOTGa";

    private ConexionSql() {
    }

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado en el classpath.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
