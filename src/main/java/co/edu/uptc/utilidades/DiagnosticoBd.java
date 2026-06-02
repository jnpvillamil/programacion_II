package co.edu.uptc.utilidades;

import co.edu.uptc.persistencia.ConexionSql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public final class DiagnosticoBd {

    private DiagnosticoBd() {
    }

    public static void main(String[] args) throws Exception {
        String[] tablas = {"usuario", "cliente", "proveedor", "producto", "venta", "compra", "movimiento_contable"};
        try (Connection conexion = ConexionSql.getConexion();
             Statement sentencia = conexion.createStatement()) {
            for (String tabla : tablas) {
                try (ResultSet rs = sentencia.executeQuery("SELECT COUNT(*) FROM " + tabla)) {
                    rs.next();
                    System.out.println(tabla + ": " + rs.getInt(1));
                }
            }
        } finally {
            ConexionSql.cerrarPool();
        }
    }
}
