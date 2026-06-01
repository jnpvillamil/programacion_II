package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:tienda_minorista.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    public static void inicializarTablas() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS clientes ("
                    + "codigo TEXT PRIMARY KEY,"
                    + "nombre TEXT NOT NULL,"
                    + "tipoIdentificacion TEXT,"
                    + "numeroIdentificacion TEXT,"
                    + "direccion TEXT,"
                    + "telefono TEXT,"
                    + "tipoCliente TEXT,"
                    + "activo INTEGER DEFAULT 1)");

            stmt.execute("CREATE TABLE IF NOT EXISTS proveedores ("
                    + "codigo TEXT PRIMARY KEY,"
                    + "nombre TEXT NOT NULL,"
                    + "nit TEXT,"
                    + "correo TEXT,"
                    + "direccion TEXT,"
                    + "telefono TEXT,"
                    + "activo INTEGER DEFAULT 1)");

            stmt.execute("CREATE TABLE IF NOT EXISTS productos ("
                    + "codigo TEXT PRIMARY KEY,"
                    + "nombre TEXT NOT NULL,"
                    + "categoria TEXT,"
                    + "precio_compra REAL,"
                    + "precio_venta REAL,"
                    + "stock_actual INTEGER,"
                    + "stock_minimo INTEGER,"
                    + "activo INTEGER DEFAULT 1)");

            stmt.execute("CREATE TABLE IF NOT EXISTS compras_proveedor ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "fecha TEXT,"
                    + "proveedor TEXT,"
                    + "producto TEXT,"
                    + "cantidad INTEGER,"
                    + "precio_unitario REAL,"
                    + "total REAL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS ventas_cliente ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "codigo_cliente TEXT,"
                    + "nombre_cliente TEXT,"
                    + "codigo_producto TEXT,"
                    + "nombre_producto TEXT,"
                    + "cantidad INTEGER,"
                    + "total REAL,"
                    + "fecha TEXT)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
