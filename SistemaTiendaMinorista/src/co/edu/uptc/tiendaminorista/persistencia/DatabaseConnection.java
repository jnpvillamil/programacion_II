package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {

    private static final String HOST     = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME  = "tienda_minorista";
    private static final String URL      = HOST + DB_NAME
            + "?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true";
    private static final String USER     = "root";
    private static final String PASSWORD = "";  
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    
    public static void iniciarBaseDatos() {
        crearBaseDeDatos();
        crearTablas();
    }


    private static void crearBaseDeDatos() {
        String urlSinBD = HOST + "?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true";
        try (Connection conn = DriverManager.getConnection(urlSinBD, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME
                    + " CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci");
        } catch (SQLException e) {
            System.err.println("Error creando la base de datos: " + e.getMessage());
        }
    }

    private static void crearTablas() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS clientes (" +
                "  codigo               VARCHAR(50)  NOT NULL," +
                "  nombre               VARCHAR(150) NOT NULL," +
                "  tipoIdentificacion   VARCHAR(20)  DEFAULT NULL," +
                "  numeroIdentificacion VARCHAR(30)  DEFAULT NULL," +
                "  direccion            VARCHAR(200) DEFAULT NULL," +
                "  telefono             VARCHAR(30)  DEFAULT NULL," +
                "  tipoCliente          VARCHAR(50)  DEFAULT NULL," +
                "  activo               TINYINT(1)   NOT NULL DEFAULT 1," +
                "  PRIMARY KEY (codigo)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS proveedores (" +
                "  codigo    VARCHAR(50)  NOT NULL," +
                "  nombre    VARCHAR(150) NOT NULL," +
                "  nit       VARCHAR(30)  DEFAULT NULL," +
                "  correo    VARCHAR(100) DEFAULT NULL," +
                "  direccion VARCHAR(200) DEFAULT NULL," +
                "  telefono  VARCHAR(30)  DEFAULT NULL," +
                "  activo    TINYINT(1)   NOT NULL DEFAULT 1," +
                "  PRIMARY KEY (codigo)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS productos (" +
                "  codigo        VARCHAR(50)  NOT NULL," +
                "  nombre        VARCHAR(150) NOT NULL," +
                "  categoria     VARCHAR(50)  DEFAULT NULL," +
                "  precio_compra DOUBLE       NOT NULL DEFAULT 0," +
                "  precio_venta  DOUBLE       NOT NULL DEFAULT 0," +
                "  stock_actual  INT          NOT NULL DEFAULT 0," +
                "  stock_minimo  INT          NOT NULL DEFAULT 0," +
                "  activo        TINYINT(1)   NOT NULL DEFAULT 1," +
                "  PRIMARY KEY (codigo)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS compras_proveedor (" +
                "  id              INT          NOT NULL AUTO_INCREMENT," +
                "  fecha           DATE         DEFAULT NULL," +
                "  proveedor       VARCHAR(150) DEFAULT NULL," +
                "  producto        VARCHAR(150) DEFAULT NULL," +
                "  cantidad        INT          NOT NULL DEFAULT 0," +
                "  precio_unitario DOUBLE       NOT NULL DEFAULT 0," +
                "  total           DOUBLE       NOT NULL DEFAULT 0," +
                "  PRIMARY KEY (id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS ventas_cliente (" +
                "  id              INT          NOT NULL AUTO_INCREMENT," +
                "  codigo_cliente  VARCHAR(50)  DEFAULT NULL," +
                "  nombre_cliente  VARCHAR(150) DEFAULT NULL," +
                "  codigo_producto VARCHAR(50)  DEFAULT NULL," +
                "  nombre_producto VARCHAR(150) DEFAULT NULL," +
                "  cantidad        INT          NOT NULL DEFAULT 0," +
                "  total           DOUBLE       NOT NULL DEFAULT 0," +
                "  fecha           VARCHAR(60)  DEFAULT NULL," +
                "  PRIMARY KEY (id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS empleados (" +
                "  correo        VARCHAR(100) NOT NULL," +
                "  password      VARCHAR(100) NOT NULL," +
                "  tipo_empleado VARCHAR(50)  DEFAULT 'Empleado'," +
                "  PRIMARY KEY (correo)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS movimientos_contables (" +
                "  codigo                VARCHAR(20)  NOT NULL," +
                "  fecha                 DATE         DEFAULT NULL," +
                "  tipo                  VARCHAR(20)  DEFAULT NULL," +
                "  cuenta_contable       VARCHAR(100) DEFAULT NULL," +
                "  debito                DOUBLE       NOT NULL DEFAULT 0," +
                "  credito               DOUBLE       NOT NULL DEFAULT 0," +
                "  descripcion           VARCHAR(255) DEFAULT NULL," +
                "  documento_relacionado VARCHAR(100) DEFAULT NULL," +
                "  PRIMARY KEY (codigo)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");
            
            stmt.execute(
            	    "CREATE TABLE IF NOT EXISTS practica (" +
            	    "  texto1 VARCHAR(100) NOT NULL," +
            	    "  texto2 VARCHAR(150) DEFAULT NULL," +
            	    "  PRIMARY KEY (texto1)" +
            	    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

            System.out.println("Base de datos lista.");

        } catch (SQLException e) {
            System.err.println("Error creando tablas: " + e.getMessage());
        }
    }
}
