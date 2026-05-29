package co.edu.uptc.persistencia;

import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaConsultas {

    public List<Producto> productosConStockBajoMinimo() {
        List<Producto> lista = new ArrayList<>();
        String sql = """
                SELECT codigo_producto, nombre_producto, precio_compra, precio_venta,
                       stock_actual, stock_minimo, stock_maximo, activo
                FROM productos
                WHERE activo = 1 AND stock_actual < stock_minimo
                ORDER BY stock_actual ASC
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setCodigoProducto(rs.getString("codigo_producto"));
                p.setNombreProducto(rs.getString("nombre_producto"));
                p.setPrecioCompra(rs.getDouble("precio_compra"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setStockActual(rs.getInt("stock_actual"));
                p.setStockMinimo(rs.getInt("stock_minimo"));
                p.setStockMaximo(rs.getInt("stock_maximo"));
                p.setActivo(rs.getBoolean("activo"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Compra> comprasPorProveedorEnRango(String criterioProveedor, LocalDate inicio, LocalDate fin) {
        List<Compra> lista = new ArrayList<>();
        String sql = """
                SELECT co.factura_proveedor, co.fecha, co.total_compra, co.iva
                FROM compras co
                LEFT JOIN proveedores p ON co.codigo_proveedor = p.codigo_proveedor
                WHERE (co.codigo_proveedor = ? OR p.nit = ?)
                  AND co.fecha BETWEEN ? AND ?
                ORDER BY co.fecha DESC
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, criterioProveedor);
            ps.setString(2, criterioProveedor);
            ps.setDate(3, Date.valueOf(inicio));
            ps.setDate(4, Date.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Compra compra = new Compra();
                    compra.setFacturaProveedor(rs.getString("factura_proveedor"));
                    compra.setFecha(rs.getDate("fecha").toLocalDate().atStartOfDay());
                    compra.setTotalCompra(rs.getDouble("total_compra"));
                    compra.setIva(rs.getDouble("iva"));
                    lista.add(compra);
                }
            }
        } catch (Exception e) {
            System.err.println("Error en comprasPorProveedorEnRango: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public double totalVentasMensual(int anio, int mes) {
        String sql = """
                SELECT COALESCE(SUM(total_venta), 0) AS total
                FROM ventas
                WHERE YEAR(fecha_hora) = ? AND MONTH(fecha_hora) = ?
                  AND (estado IS NULL OR estado <> 'ANULADA')
                """;
        return consultarTotal(sql, ps -> {
            ps.setInt(1, anio);
            ps.setInt(2, mes);
        });
    }

    public double totalVentasAnual(int anio) {
        String sql = """
                SELECT COALESCE(SUM(total_venta), 0) AS total
                FROM ventas
                WHERE YEAR(fecha_hora) = ?
                  AND (estado IS NULL OR estado <> 'ANULADA')
                """;
        return consultarTotal(sql, ps -> {
            ps.setInt(1, anio);
        });
    }

    @FunctionalInterface
    private interface StatementConfigurer {
        void configure(PreparedStatement ps) throws Exception;
    }

    private double consultarTotal(String sql, StatementConfigurer configurer) {
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            configurer.configure(ps);
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
}
