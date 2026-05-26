package co.edu.uptc.persistencia;

import co.edu.uptc.utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersistenciaReportes {

    // CUS22
    public ResultSet reporteProductoMasVendido() {

        ResultSet rs = null;

        try {

            Connection con =
                    ConexionBD.getConexion();

            String sql =
                    "SELECT nombre, SUM(cantidad) AS total_vendido " +
                    "FROM detalle_venta " +
                    "GROUP BY nombre " +
                    "ORDER BY total_vendido DESC";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            rs = ps.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rs;
    }

    // CUS23
    public ResultSet reporteMejorCliente() {

        ResultSet rs = null;

        try {

            Connection con =
                    ConexionBD.getConexion();

            String sql =
                    "SELECT cliente, SUM(total) AS total_compras " +
                    "FROM venta " +
                    "GROUP BY cliente " +
                    "ORDER BY total_compras DESC";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            rs = ps.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rs;
    }

    // CUS24
    public ResultSet reporteMetodoPago() {

        ResultSet rs = null;

        try {

            Connection con =
                    ConexionBD.getConexion();

            String sql =
                    "SELECT forma_pago, COUNT(*) AS cantidad, " +
                    "SUM(total) AS total_recaudado " +
                    "FROM venta " +
                    "GROUP BY forma_pago";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            rs = ps.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rs;
    }

    // CUS25
    public ResultSet reporteInventario() {

        ResultSet rs = null;

        try {

            Connection con =
                    ConexionBD.getConexion();

            String sql =
                    "SELECT codigo, nombre, stock_actual, " +
                    "stock_minimo " +
                    "FROM producto";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            rs = ps.executeQuery();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return rs;
    }
}