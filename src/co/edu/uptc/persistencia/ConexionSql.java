package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSql {
    
    // Apunta directamente a la carpeta "bd" que creamos en la raíz de tu proyecto
    private static final String URL = "jdbc:sqlite:bd/tienda.db";

    public static Connection obtenerConexion() throws SQLException {
        // SQLite no utiliza usuario ni clave, solo la URL del archivo
        return DriverManager.getConnection(URL);
    }
}