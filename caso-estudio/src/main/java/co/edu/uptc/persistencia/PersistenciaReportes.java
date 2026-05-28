package co.edu.uptc.persistencia;

import co.edu.uptc.dto.ReporteConsolidadoDiarioDTO;
import co.edu.uptc.dto.ReportesDTO;
import co.edu.uptc.utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaReportes {

    public List<ReportesDTO.ProductoVendidoItem> reporteProductoMasVendido() {
        List<ReportesDTO.ProductoVendidoItem> items = new ArrayList<>();
        String sql = """
                SELECT p.codigo_producto AS codigo, p.nombre_producto AS nombre,
                       SUM(dv.cantidad) AS cantidad
                FROM detalles_ventas dv
                JOIN productos p ON dv.codigo_producto = p.codigo_producto
                GROUP BY p.codigo_producto, p.nombre_producto
                ORDER BY cantidad DESC
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new ReportesDTO.ProductoVendidoItem(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ReportesDTO.MejorClienteItem> reporteMejorCliente() {
        List<ReportesDTO.MejorClienteItem> items = new ArrayList<>();
        String sql = """
                SELECT COALESCE(c.numero_identificacion, v.codigo_cliente) AS identificacion,
                       COALESCE(c.nombre_completo, v.nombre_cliente, 'Sin nombre') AS nombre,
                       SUM(v.total) AS total_comprado
                FROM ventas v
                LEFT JOIN clientes c ON v.codigo_cliente = c.codigo_cliente
                WHERE (v.estado IS NULL OR v.estado <> 'ANULADA')
                GROUP BY COALESCE(c.numero_identificacion, v.codigo_cliente),
                         COALESCE(c.nombre_completo, v.nombre_cliente, 'Sin nombre')
                ORDER BY total_comprado DESC
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new ReportesDTO.MejorClienteItem(
                        rs.getString("identificacion"),
                        rs.getString("nombre"),
                        rs.getDouble("total_comprado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ReportesDTO.VentaMetodoPagoItem> reporteMetodoPago() {
        List<ReportesDTO.VentaMetodoPagoItem> items = new ArrayList<>();
        String sql = """
                SELECT forma_pago, SUM(total) AS total_venta
                FROM ventas
                WHERE (estado IS NULL OR estado <> 'ANULADA')
                GROUP BY forma_pago
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new ReportesDTO.VentaMetodoPagoItem(
                        rs.getString("forma_pago"),
                        rs.getDouble("total_venta")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ReportesDTO.InventarioItem> reporteInventario() {
        List<ReportesDTO.InventarioItem> items = new ArrayList<>();
        String sql = """
                SELECT codigo_producto AS codigo, nombre_producto AS nombre,
                       stock_actual, (stock_actual * precio_compra) AS valorizacion
                FROM productos
                WHERE activo = 1
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new ReportesDTO.InventarioItem(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("stock_actual"),
                        rs.getDouble("valorizacion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public double totalVentasPorFecha(LocalDate fecha) {
        String sql = """
                SELECT COALESCE(SUM(total), 0) AS total
                FROM ventas
                WHERE fecha = ?
                  AND (estado IS NULL OR estado <> 'ANULADA')
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public double totalComprasPorFecha(LocalDate fecha) {
        String sql = """
                SELECT COALESCE(SUM(total_compra), 0) AS total
                FROM compras
                WHERE fecha = ?
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public List<ReporteConsolidadoDiarioDTO.VentaFormaPagoItem> ventasPorFormaPagoPorFecha(LocalDate fecha) {
        List<ReporteConsolidadoDiarioDTO.VentaFormaPagoItem> items = new ArrayList<>();
        String sql = """
                SELECT forma_pago, COALESCE(SUM(total), 0) AS total_venta
                FROM ventas
                WHERE fecha = ?
                  AND (estado IS NULL OR estado <> 'ANULADA')
                GROUP BY forma_pago
                ORDER BY total_venta DESC
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(new ReporteConsolidadoDiarioDTO.VentaFormaPagoItem(
                            rs.getString("forma_pago"),
                            rs.getDouble("total_venta")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<ReporteConsolidadoDiarioDTO.ProductoMasVendidoItem> productosMasVendidosPorFecha(
            LocalDate fecha, int limite) {
        List<ReporteConsolidadoDiarioDTO.ProductoMasVendidoItem> items = new ArrayList<>();
        String sql = """
                SELECT p.codigo_producto AS codigo, p.nombre_producto AS nombre,
                       SUM(dv.cantidad) AS cantidad
                FROM detalles_ventas dv
                JOIN ventas v ON dv.numero_factura = v.numero_factura
                JOIN productos p ON dv.codigo_producto = p.codigo_producto
                WHERE v.fecha = ?
                  AND (v.estado IS NULL OR v.estado <> 'ANULADA')
                GROUP BY p.codigo_producto, p.nombre_producto
                ORDER BY cantidad DESC
                LIMIT ?
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fecha));
            ps.setInt(2, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(new ReporteConsolidadoDiarioDTO.ProductoMasVendidoItem(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getInt("cantidad")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
