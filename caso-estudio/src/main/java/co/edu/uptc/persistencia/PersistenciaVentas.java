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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaVentas implements IRepositorioVenta {

    private static final String SELECT_VENTA = """
            SELECT v.numero_factura, v.fecha_hora, v.cliente_id AS codigo_cliente,
                   COALESCE(c.nombre_completo, '') AS nombre_cliente,
                   v.subtotal, v.iva_aplicado AS iva, v.total_venta AS total, v.forma_pago
            """;

    private static final String FILTRO_NO_ANULADA = """
              AND (v.estado IS NULL OR v.estado <> 'ANULADA')
            """;

    @Override
    public boolean guardarVenta(Venta venta) {
        String sqlCabecera = """
            INSERT INTO ventas (numero_factura, fecha_hora, cliente_id,
                subtotal, iva_aplicado, total_venta, forma_pago, estado)
            VALUES (?, ?, ?, ?, ?, ?, ?, 'ACTIVA')
            """;
        String sqlDetalle = """
            INSERT INTO detalles_ventas (numero_factura, producto_codigo, cantidad, precio_unitario, subtotal)
            VALUES (?, ?, ?, ?, ?)
            """;

        Connection con = ConexionBD.getConexion();
        if (con == null) {
            System.err.println("Error al guardar venta en BD: no hay conexion.");
            return false;
        }

        try {
            con.setAutoCommit(false);
            String clienteId = venta.getCliente() != null ? venta.getCliente().getCodigoCliente() : null;

            try (PreparedStatement ps = con.prepareStatement(sqlCabecera)) {
                ps.setString(1, venta.getNumeroFactura());
                ps.setTimestamp(2, Timestamp.valueOf(venta.getFechaHora()));
                ps.setString(3, clienteId);
                ps.setDouble(4, venta.getSubtotal());
                ps.setDouble(5, venta.getIvaAplicado());
                ps.setDouble(6, venta.getTotalVenta());
                ps.setString(7, venta.getFormaPago() != null ? venta.getFormaPago().name() : FormaPago.EFECTIVO.name());
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
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback venta: " + ex.getMessage());
            }
            System.err.println("Error al guardar venta en BD: " + e.getMessage());
            return false;
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
        String sql = SELECT_VENTA + """
            FROM ventas v
            LEFT JOIN clientes c ON v.cliente_id = c.codigo_cliente
            WHERE (c.numero_identificacion = ?
               OR c.codigo_cliente = ?
               OR v.cliente_id = ?)
            """ + FILTRO_NO_ANULADA + """
            ORDER BY v.fecha_hora DESC
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
        String sql = SELECT_VENTA + """
            FROM ventas v
            LEFT JOIN clientes c ON v.cliente_id = c.codigo_cliente
            WHERE DATE_FORMAT(v.fecha_hora, '%d/%m/%Y') = ?
            """ + FILTRO_NO_ANULADA + """
            ORDER BY v.numero_factura
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
        String sql = SELECT_VENTA + """
            FROM ventas v
            LEFT JOIN clientes c ON v.cliente_id = c.codigo_cliente
            WHERE v.numero_factura = ?
            """ + FILTRO_NO_ANULADA;

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
            SELECT dv.producto_codigo AS codigo_producto, dv.cantidad, dv.precio_unitario, dv.subtotal,
                   p.nombre_producto, p.categoria
            FROM detalles_ventas dv
            JOIN productos p ON dv.producto_codigo = p.codigo_producto
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

        Timestamp fechaHora = rs.getTimestamp("fecha_hora");
        if (fechaHora != null) {
            venta.setFechaHora(fechaHora.toLocalDateTime());
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
