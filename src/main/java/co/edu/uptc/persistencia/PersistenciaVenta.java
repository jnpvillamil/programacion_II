package co.edu.uptc.persistencia;

import co.edu.uptc.enums.EstadoVenta;
import co.edu.uptc.interfaces.RepositorioVenta;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaVenta implements RepositorioVenta {

    private static final String SQL_INSERT_VENTA = """
            INSERT INTO ventas (
                identificacion_cliente, forma_pago, subtotal, iva, total, estado
            ) VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_INSERT_DETALLE = """
            INSERT INTO detalles_ventas (
                numero_factura, codigo_producto, cantidad, precio_unitario, subtotal
            ) VALUES (?, ?, ?, ?, ?)
            """;

    private static final String SQL_SELECT_ESTADO = """
            SELECT estado FROM ventas WHERE numero_factura = ?
            """;

    private static final String SQL_ANULAR = """
            UPDATE ventas SET estado = 'ANULADA' WHERE numero_factura = ? AND estado = 'ACTIVA'
            """;

    private static final String SQL_DETALLES_POR_FACTURA = """
            SELECT codigo_producto, cantidad
            FROM detalles_ventas
            WHERE numero_factura = ?
            """;

    private static final String SQL_EXISTE_CLIENTE = """
            SELECT 1 FROM clientes WHERE identificacion = ? LIMIT 1
            """;

    @Override
    public void guardar(Venta venta) {
        try (Connection conn = ConexionSql.getConexion()) {
            conn.setAutoCommit(false);
            try {
                validarCliente(conn, venta.getCliente().getIdentificacion());

                int numeroFactura;
                try (PreparedStatement pstmtVenta = conn.prepareStatement(SQL_INSERT_VENTA, Statement.RETURN_GENERATED_KEYS)) {
                    pstmtVenta.setString(1, venta.getCliente().getIdentificacion());
                    pstmtVenta.setString(2, venta.getFormaPago().name());
                    pstmtVenta.setDouble(3, venta.getSubtotal());
                    pstmtVenta.setDouble(4, venta.getIva());
                    pstmtVenta.setDouble(5, venta.getTotal());
                    pstmtVenta.setString(6, EstadoVenta.ACTIVA.name());
                    pstmtVenta.executeUpdate();

                    try (ResultSet rs = pstmtVenta.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("No se generó el número de factura.");
                        }
                        numeroFactura = rs.getInt(1);
                    }
                }

                venta.setNumeroFactura(String.valueOf(numeroFactura));
                venta.setEstado(EstadoVenta.ACTIVA);

                try (PreparedStatement pstmtDetalle = conn.prepareStatement(SQL_INSERT_DETALLE)) {
                    for (DetalleVenta detalle : venta.getListaDetalles()) {
                        String codigoProducto = detalle.getProducto().getCodigoInterno();
                        int filasStock = PersistenciaProducto.descontarStock(conn, codigoProducto, detalle.getCantidad());
                        if (filasStock == 0) {
                            throw new SQLException("Stock insuficiente o producto inactivo: " + codigoProducto);
                        }

                        pstmtDetalle.setInt(1, numeroFactura);
                        pstmtDetalle.setString(2, codigoProducto);
                        pstmtDetalle.setInt(3, detalle.getCantidad());
                        pstmtDetalle.setDouble(4, detalle.getPrecioUnitario());
                        pstmtDetalle.setDouble(5, detalle.getSubtotal());
                        pstmtDetalle.addBatch();
                    }
                    pstmtDetalle.executeBatch();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw ExcepcionAccesoDatos.desde(e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
    }

    @Override
    public void anularVenta(String numeroFactura) {
        int idFactura = parsearNumeroFactura(numeroFactura);

        try (Connection conn = ConexionSql.getConexion()) {
            conn.setAutoCommit(false);
            try {
                String estadoActual = consultarEstado(conn, idFactura);
                if (estadoActual == null) {
                    throw new SQLException("Factura no encontrada: " + numeroFactura);
                }
                if (!EstadoVenta.ACTIVA.name().equals(estadoActual)) {
                    throw new SQLException("La factura ya está anulada o no puede modificarse.");
                }

                List<DetalleVenta> detalles = cargarDetallesMinimos(conn, idFactura);
                try (PreparedStatement pstmtAnular = conn.prepareStatement(SQL_ANULAR)) {
                    pstmtAnular.setInt(1, idFactura);
                    if (pstmtAnular.executeUpdate() == 0) {
                        throw new SQLException("No fue posible anular la factura " + numeroFactura);
                    }
                }

                for (DetalleVenta detalle : detalles) {
                    PersistenciaProducto.devolverStock(conn, detalle.getProducto().getCodigoInterno(), detalle.getCantidad());
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw ExcepcionAccesoDatos.desde(e);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
    }

    @Override
    public void eliminar(String id) {
        throw new UnsupportedOperationException("Use anularVenta para trazabilidad de devoluciones.");
    }

    @Override
    public List<Venta> listar() {
        return new ArrayList<>();
    }

    @Override
    public Venta buscarPorId(String id) {
        return null;
    }

    private void validarCliente(Connection conn, String identificacion) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_EXISTE_CLIENTE)) {
            pstmt.setString(1, identificacion);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Cliente no registrado con identificación: " + identificacion);
                }
            }
        }
    }

    private String consultarEstado(Connection conn, int numeroFactura) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ESTADO)) {
            pstmt.setInt(1, numeroFactura);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("estado");
                }
            }
        }
        return null;
    }

    private List<DetalleVenta> cargarDetallesMinimos(Connection conn, int numeroFactura) throws SQLException {
        List<DetalleVenta> detalles = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DETALLES_POR_FACTURA)) {
            pstmt.setInt(1, numeroFactura);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    co.edu.uptc.modelo.Producto producto = new co.edu.uptc.modelo.Producto(
                            rs.getString("codigo_producto"), "", null, 0, 0, 0, 0, 0);
                    detalles.add(new DetalleVenta(producto, rs.getInt("cantidad"), 0));
                }
            }
        }
        return detalles;
    }

    private int parsearNumeroFactura(String numeroFactura) {
        if (numeroFactura == null || numeroFactura.isBlank()) {
            throw new IllegalArgumentException("Número de factura inválido.");
        }
        try {
            return Integer.parseInt(numeroFactura.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Número de factura inválido: " + numeroFactura, e);
        }
    }
}
