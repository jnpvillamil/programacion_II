package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.IRepositorioVenta;
import co.edu.uptc.enums.CategoriaProducto;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaVentas implements IRepositorioVenta {

    @Override
    public void guardarVenta(Venta venta) {
        String sqlCabecera = """
            INSERT INTO ventas (numero_factura, fecha, codigo_cliente, nombre_cliente,
                subtotal, iva, total, forma_pago)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        String sqlDetalle = """
            INSERT INTO detalles_ventas (numero_factura, codigo_producto, cantidad, precio_unitario, subtotal)
            VALUES (?, ?, ?, ?, ?)
            """;

        Connection con = ConexionBD.getConexion();
        if (con == null) {
            return;
        }

        try {
            con.setAutoCommit(false);
            Date fecha = Date.valueOf(venta.getFechaHora().toLocalDate());
            String codigoCliente = venta.getCliente() != null ? venta.getCliente().getCodigoCliente() : null;
            String nombreCliente = venta.getCliente() != null ? venta.getCliente().getNombre() : "";

            try (PreparedStatement ps = con.prepareStatement(sqlCabecera)) {
                ps.setString(1, venta.getNumeroFactura());
                ps.setDate(2, fecha);
                ps.setString(3, codigoCliente);
                ps.setString(4, nombreCliente);
                ps.setDouble(5, venta.getSubtotal());
                ps.setDouble(6, venta.getIvaAplicado());
                ps.setDouble(7, venta.getTotalVenta());
                ps.setString(8, venta.getFormaPago() != null ? venta.getFormaPago().name() : FormaPago.EFECTIVO.name());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {
                for (DetalleVenta dv : venta.getProductosVendidos()) {
                    ps.setString(1, venta.getNumeroFactura());
                    ps.setString(2, dv.getProducto().getCodigoProducto());
                    ps.setInt(3, dv.getCantidad());
                    ps.setDouble(4, dv.getPrecioUnitario());
                    ps.setDouble(5, dv.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback venta: " + ex.getMessage());
            }
            System.err.println("Error al guardar venta en BD: " + e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autocommit: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Venta> consultarHistorialCliente(String identificacion) {
        List<Venta> lista = new ArrayList<>();
        String sql = """
            SELECT v.numero_factura, v.fecha, v.codigo_cliente, v.nombre_cliente,
                   v.subtotal, v.iva, v.total, v.forma_pago
            FROM ventas v
            LEFT JOIN clientes c ON v.codigo_cliente = c.codigo_cliente
            WHERE c.numero_identificacion = ?
               OR c.codigo_cliente = ?
               OR v.codigo_cliente = ?
            ORDER BY v.fecha DESC
            """;

        Connection con = ConexionBD.getConexion();
        if (con == null) {
            return lista;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, identificacion);
            ps.setString(2, identificacion);
            ps.setString(3, identificacion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearVenta(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar historial de cliente: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public List<Venta> consultarVentasPorFecha(String fecha) {
        List<Venta> lista = new ArrayList<>();
        String sql = """
            SELECT numero_factura, fecha, codigo_cliente, nombre_cliente,
                   subtotal, iva, total, forma_pago
            FROM ventas
            WHERE DATE_FORMAT(fecha, '%d/%m/%Y') = ?
              AND (estado IS NULL OR estado <> 'ANULADA')
            ORDER BY numero_factura
            """;

        Connection con = ConexionBD.getConexion();
        if (con == null) {
            return lista;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, normalizarFechaConsulta(fecha));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearVenta(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar ventas por fecha: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Venta buscarVentaPorFactura(String numeroFactura) {
        String sql = """
            SELECT numero_factura, fecha, codigo_cliente, nombre_cliente,
                   subtotal, iva, total, forma_pago
            FROM ventas
            WHERE numero_factura = ?
              AND (estado IS NULL OR estado <> 'ANULADA')
            """;

        Connection con = ConexionBD.getConexion();
        if (con == null) {
            return null;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numeroFactura);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Venta venta = mapearVenta(rs);
                    venta.setProductosVendidos(cargarDetallesVenta(con, numeroFactura));
                    return venta;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar venta por factura: " + e.getMessage());
        }

        return null;
    }

    private List<DetalleVenta> cargarDetallesVenta(Connection con, String numeroFactura) throws SQLException {
        List<DetalleVenta> detalles = new ArrayList<>();
        String sql = """
            SELECT dv.codigo_producto, dv.cantidad, dv.precio_unitario, dv.subtotal,
                   p.nombre_producto, p.categoria
            FROM detalles_ventas dv
            JOIN productos p ON dv.codigo_producto = p.codigo_producto
            WHERE dv.numero_factura = ?
            """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numeroFactura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setCodigoProducto(rs.getString("codigo_producto"));
                    producto.setNombreProducto(rs.getString("nombre_producto"));
                    producto.setCategoria(CategoriaProducto.desdeTexto(rs.getString("categoria")));

                    detalles.add(new DetalleVenta(
                            producto,
                            rs.getInt("cantidad"),
                            rs.getDouble("precio_unitario"),
                            rs.getDouble("subtotal")));
                }
            }
        }
        return detalles;
    }

    @Override
    public boolean anularVenta(String numeroFactura) {
        String sql = "UPDATE ventas SET estado = 'ANULADA' WHERE numero_factura = ? AND (estado IS NULL OR estado <> 'ANULADA')";

        Connection con = ConexionBD.getConexion();
        if (con == null) {
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numeroFactura);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al anular venta: " + e.getMessage());
            return false;
        }
    }

    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setNumeroFactura(rs.getString("numero_factura"));

        Date fecha = rs.getDate("fecha");
        if (fecha != null) {
            LocalDate localDate = fecha.toLocalDate();
            venta.setFechaHora(localDate.atStartOfDay());
        }

        Cliente cliente = new Cliente();
        cliente.setCodigoCliente(rs.getString("codigo_cliente"));
        cliente.setNombre(rs.getString("nombre_cliente"));
        venta.setCliente(cliente);

        venta.setSubtotal(rs.getDouble("subtotal"));
        venta.setIvaAplicado(rs.getDouble("iva"));
        venta.setTotalVenta(rs.getDouble("total"));
        venta.setFormaPago(FormaPago.desdeTexto(rs.getString("forma_pago")));
        return venta;
    }

    private String normalizarFechaConsulta(String fecha) {
        if (fecha == null || fecha.isBlank()) {
            return "";
        }
        String valor = fecha.trim();
        if (valor.length() >= 10) {
            return valor.substring(0, 10);
        }
        return valor;
    }
}