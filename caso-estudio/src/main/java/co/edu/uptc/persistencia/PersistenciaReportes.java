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

    private static final String FILTRO_VENTAS_ACTIVAS = """
            WHERE (v.estado IS NULL OR v.estado <> 'ANULADA')
            """;

    public List<ReportesDTO.ProductoVendidoItem> reporteProductoMasVendido() {
        List<ReportesDTO.ProductoVendidoItem> items = new ArrayList<>();
        String sql = """
                SELECT p.codigo_producto AS codigo, p.nombre_producto AS nombre,
                       SUM(dv.cantidad) AS cantidad
                FROM detalles_ventas dv
                JOIN ventas v ON dv.numero_factura = v.numero_factura
                JOIN productos p ON dv.producto_codigo = p.codigo_producto
                """ + FILTRO_VENTAS_ACTIVAS + """
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
            System.err.println("Error en reporteProductoMasVendido: " + e.getMessage());
        }
        return items;
    }

    public List<ReportesDTO.MejorClienteItem> reporteMejorCliente() {
        List<ReportesDTO.MejorClienteItem> items = new ArrayList<>();
        String sql = """
                SELECT COALESCE(c.numero_identificacion, v.cliente_id) AS identificacion,
                       COALESCE(c.nombre_completo, 'Sin nombre') AS nombre,
                       SUM(v.total_venta) AS total_comprado
                FROM ventas v
                LEFT JOIN clientes c ON v.cliente_id = c.codigo_cliente
                """ + FILTRO_VENTAS_ACTIVAS + """
                GROUP BY COALESCE(c.numero_identificacion, v.cliente_id),
                         COALESCE(c.nombre_completo, 'Sin nombre')
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
            System.err.println("Error en reporteMejorCliente: " + e.getMessage());
        }
        return items;
    }

    public List<ReportesDTO.VentaMetodoPagoItem> reporteMetodoPago() {
        List<ReportesDTO.VentaMetodoPagoItem> items = new ArrayList<>();
        String sql = """
                SELECT forma_pago, SUM(total_venta) AS total_venta
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
            System.err.println("Error en reporteMetodoPago: " + e.getMessage());
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
            System.err.println("Error en reporteInventario: " + e.getMessage());
        }
        return items;
    }

    public double totalVentasPorFecha(LocalDate fecha) {
        String sql = """
                SELECT COALESCE(SUM(total_venta), 0) AS total
                FROM ventas
                WHERE DATE(fecha_hora) = ?
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
            System.err.println("Error en totalVentasPorFecha: " + e.getMessage());
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
            System.err.println("Error en totalComprasPorFecha: " + e.getMessage());
        }
        return 0.0;
    }

    public List<ReporteConsolidadoDiarioDTO.VentaFormaPagoItem> ventasPorFormaPagoPorFecha(LocalDate fecha) {
        List<ReporteConsolidadoDiarioDTO.VentaFormaPagoItem> items = new ArrayList<>();
        String sql = """
                SELECT forma_pago, COALESCE(SUM(total_venta), 0) AS total_venta
                FROM ventas
                WHERE DATE(fecha_hora) = ?
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
            System.err.println("Error en ventasPorFormaPagoPorFecha: " + e.getMessage());
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
                JOIN productos p ON dv.producto_codigo = p.codigo_producto
                WHERE DATE(v.fecha_hora) = ?
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
            System.err.println("Error en productosMasVendidosPorFecha: " + e.getMessage());
        }
        return items;
    }
}
