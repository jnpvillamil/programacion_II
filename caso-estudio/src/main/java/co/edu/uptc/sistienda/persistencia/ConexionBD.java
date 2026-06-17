package co.edu.uptc.sistienda.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase se encarga de abrir la conexión con MySQL (XAMPP).
 * Todas las clases BD la usan para conectarse a la base de datos.
 */
public class ConexionBD {

    private static final String DIRECCION = "jdbc:mariadb://localhost:3306/sistienda";
    private static final String USUARIO   = "root";
    private static final String CLAVE     = ""; 

    //Abre la conexión con la base de datos y la devuelve lista para usar
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(DIRECCION, USUARIO, CLAVE);
    }
}

