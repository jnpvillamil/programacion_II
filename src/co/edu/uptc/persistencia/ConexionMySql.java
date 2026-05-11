package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Preparación para conexión a MySQL.

public class ConexionMySql {
    private static final String URL = "jdbc:mysql://localhost:3306/tienda_minorista";
    private static final String USUARIO = "root";
    private static final String CLAVE = "123456";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}