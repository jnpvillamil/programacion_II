package co.edu.uptc.persistencia;

import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.enums.EstadoVenta;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaVenta {

    private static final String SQL_INSERT_VENTA = """
            INSERT INTO venta (
                identificacion_cliente, forma_pago, subtotal, iva, total, estado
            ) VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_INSERT_DETALLE = """
            INSERT INTO detalle_venta (
                numero_factura, codigo_producto, cantidad, precio_unitario, subtotal
            ) VALUES (?, ?, ?, ?, ?)
            """;

    private static final String SQL_SELECT_ESTADO = """
            SELECT estado FROM venta WHERE numero_factura = ?
            """;

    private static final String SQL_ANULAR = """
            UPDATE venta SET estado = 'ANULADA' WHERE numero_factura = ? AND estado = 'ACTIVA'
            """;

    private static final String SQL_DETALLES_POR_FACTURA = """
            SELECT codigo_producto, cantidad
            FROM detalle_venta
            WHERE numero_factura = ?
            """;

    private static final String SQL_SELECT_VENTA = """
            SELECT numero_factura, identificacion_cliente, subtotal, iva, total, estado
            FROM venta
            WHERE numero_factura = ?
            """;

    private static final String SQL_SELECT_RESUMEN = """
            SELECT v.numero_factura,
                   v.fecha_hora,
                   v.identificacion_cliente,
                   CONCAT(c.nombres, ' ', c.apellidos) AS nombre_cliente,
                   v.subtotal,
                   v.iva,
                   v.total,
                   v.forma_pago,
                   v.estado
            FROM venta v
            INNER JOIN cliente c ON c.identificacion = v.identificacion_cliente
            ORDER BY v.fecha_hora DESC
            """;

    private static final String SQL_EXISTE_CLIENTE = """
            SELECT 1 FROM cliente WHERE identificacion = ? LIMIT 1
            """;

    public void guardar(Venta venta) {
        Connection conexion = null;
        try {
            conexion = ConexionSql.getConexion();
            conexion.setAutoCommit(false);
            guardarEnTransaccion(conexion, venta);
            conexion.commit();
        } catch (SQLException excepcion) {
            revertirTransaccion(conexion);
            throw ExcepcionAccesoDatos.desde(excepcion);
        } finally {
            restablecerConexion(conexion);
        }
    }

    public void guardarEnTransaccion(Connection conexion, Venta venta) throws SQLException {
        validarCliente(conexion, venta.getCliente().getIdentificacion());

        int numeroFactura;
        PreparedStatement sentenciaVenta = null;
        ResultSet claveGenerada = null;
        try {
            sentenciaVenta = conexion.prepareStatement(SQL_INSERT_VENTA, Statement.RETURN_GENERATED_KEYS);
            sentenciaVenta.setString(1, venta.getCliente().getIdentificacion());
            sentenciaVenta.setString(2, venta.getFormaPago().name());
            sentenciaVenta.setDouble(3, venta.getSubtotal());
            sentenciaVenta.setDouble(4, venta.getIva());
            sentenciaVenta.setDouble(5, venta.getTotal());
            sentenciaVenta.setString(6, EstadoVenta.ACTIVA.name());
            sentenciaVenta.executeUpdate();

            claveGenerada = sentenciaVenta.getGeneratedKeys();
            if (!claveGenerada.next()) {
                throw new SQLException("No se generó el número de factura.");
            }
            numeroFactura = claveGenerada.getInt(1);
        } finally {
            cerrarRecurso(claveGenerada);
            cerrarRecurso(sentenciaVenta);
        }

        venta.setNumeroFactura(String.valueOf(numeroFactura));
        venta.setEstado(EstadoVenta.ACTIVA);

        PreparedStatement sentenciaDetalle = null;
        try {
            sentenciaDetalle = conexion.prepareStatement(SQL_INSERT_DETALLE);
            for (DetalleVenta detalle : venta.getListaDetalles()) {
                String codigoProducto = detalle.getProducto().getCodigoInterno();
                co.edu.uptc.modelo.Producto productoBd =
                        PersistenciaProducto.buscarPorCodigo(conexion, codigoProducto);
                if (productoBd == null) {
                    throw new SQLException("Producto no encontrado: " + codigoProducto);
                }
                if (productoBd.getStockActual() < detalle.getCantidad()) {
                    throw new SQLException(
                            "Stock insuficiente para el producto: " + productoBd.getNombreProducto());
                }

                int filasStock = PersistenciaProducto.descontarStock(conexion, codigoProducto, detalle.getCantidad());
                if (filasStock == 0) {
                    throw new SQLException(
                            "Stock insuficiente para el producto: " + productoBd.getNombreProducto());
                }

                sentenciaDetalle.setInt(1, numeroFactura);
                sentenciaDetalle.setString(2, codigoProducto);
                sentenciaDetalle.setInt(3, detalle.getCantidad());
                sentenciaDetalle.setDouble(4, detalle.getPrecioUnitario());
                sentenciaDetalle.setDouble(5, detalle.getSubtotal());
                sentenciaDetalle.addBatch();
            }
            sentenciaDetalle.executeBatch();
        } finally {
            cerrarRecurso(sentenciaDetalle);
        }
    }

    public void anularVenta(String numeroFactura) {
        Connection conexion = null;
        try {
            conexion = ConexionSql.getConexion();
            conexion.setAutoCommit(false);
            anularEnTransaccion(conexion, numeroFactura);
            conexion.commit();
        } catch (SQLException excepcion) {
            revertirTransaccion(conexion);
            throw ExcepcionAccesoDatos.desde(excepcion);
        } finally {
            restablecerConexion(conexion);
        }
    }

    public void anularEnTransaccion(Connection conexion, String numeroFactura) throws SQLException {
        int idFactura = parsearNumeroFactura(numeroFactura);

        String estadoActual = consultarEstado(conexion, idFactura);
        if (estadoActual == null) {
            throw new SQLException("Factura no encontrada: " + numeroFactura);
        }
        if (!EstadoVenta.ACTIVA.name().equals(estadoActual)) {
            throw new SQLException("La factura ya está anulada o no puede modificarse.");
        }

        List<DetalleVenta> detalles = cargarDetallesMinimos(conexion, idFactura);
        PreparedStatement sentenciaAnular = null;
        try {
            sentenciaAnular = conexion.prepareStatement(SQL_ANULAR);
            sentenciaAnular.setInt(1, idFactura);
            if (sentenciaAnular.executeUpdate() == 0) {
                throw new SQLException("No fue posible anular la factura " + numeroFactura);
            }
        } finally {
            cerrarRecurso(sentenciaAnular);
        }

        for (DetalleVenta detalle : detalles) {
            PersistenciaProducto.devolverStock(conexion, detalle.getProducto().getCodigoInterno(), detalle.getCantidad());
        }
    }

    public List<VentaDTO> listarResumen() {
        List<VentaDTO> lista = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionSql.getConexion();
            sentencia = conexion.prepareStatement(SQL_SELECT_RESUMEN);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Timestamp fecha = resultado.getTimestamp("fecha_hora");
                LocalDateTime fechaHora = fecha != null ? fecha.toLocalDateTime() : LocalDateTime.now();
                lista.add(new VentaDTO(
                        String.valueOf(resultado.getInt("numero_factura")),
                        fechaHora,
                        resultado.getString("identificacion_cliente"),
                        resultado.getString("nombre_cliente"),
                        resultado.getDouble("subtotal"),
                        resultado.getDouble("iva"),
                        resultado.getDouble("total"),
                        FormaPago.valueOf(resultado.getString("forma_pago")),
                        EstadoVenta.valueOf(resultado.getString("estado"))));
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        } finally {
            cerrarRecurso(resultado);
            cerrarRecurso(sentencia);
            cerrarRecurso(conexion);
        }
        return lista;
    }

    public Venta buscarPorNumeroFactura(String numeroFactura) {
        int idFactura = parsearNumeroFactura(numeroFactura);
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionSql.getConexion();
            sentencia = conexion.prepareStatement(SQL_SELECT_VENTA);
            sentencia.setInt(1, idFactura);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                co.edu.uptc.modelo.Cliente cliente = new co.edu.uptc.modelo.Cliente(
                        "", "", resultado.getString("identificacion_cliente"), "", "", "",
                        co.edu.uptc.enums.TipoIdentificacion.CC,
                        co.edu.uptc.enums.TipoCliente.MINORISTA);
                Venta venta = new Venta(cliente, co.edu.uptc.enums.FormaPago.EFECTIVO);
                venta.setNumeroFactura(String.valueOf(resultado.getInt("numero_factura")));
                venta.setSubtotal(resultado.getDouble("subtotal"));
                venta.setIva(resultado.getDouble("iva"));
                venta.setTotal(resultado.getDouble("total"));
                venta.setEstado(EstadoVenta.valueOf(resultado.getString("estado")));
                return venta;
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        } finally {
            cerrarRecurso(resultado);
            cerrarRecurso(sentencia);
            cerrarRecurso(conexion);
        }
        return null;
    }

    private void validarCliente(Connection conexion, String identificacion) throws SQLException {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            sentencia = conexion.prepareStatement(SQL_EXISTE_CLIENTE);
            sentencia.setString(1, identificacion);
            resultado = sentencia.executeQuery();
            if (!resultado.next()) {
                throw new SQLException("Cliente no registrado con identificación: " + identificacion);
            }
        } finally {
            cerrarRecurso(resultado);
            cerrarRecurso(sentencia);
        }
    }

    private String consultarEstado(Connection conexion, int numeroFactura) throws SQLException {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            sentencia = conexion.prepareStatement(SQL_SELECT_ESTADO);
            sentencia.setInt(1, numeroFactura);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                return resultado.getString("estado");
            }
        } finally {
            cerrarRecurso(resultado);
            cerrarRecurso(sentencia);
        }
        return null;
    }

    private List<DetalleVenta> cargarDetallesMinimos(Connection conexion, int numeroFactura) throws SQLException {
        List<DetalleVenta> detalles = new ArrayList<>();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            sentencia = conexion.prepareStatement(SQL_DETALLES_POR_FACTURA);
            sentencia.setInt(1, numeroFactura);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                co.edu.uptc.modelo.Producto producto = new co.edu.uptc.modelo.Producto(
                        resultado.getString("codigo_producto"), "", null, 0, 0, 0, 0, 0);
                detalles.add(new DetalleVenta(producto, resultado.getInt("cantidad"), 0));
            }
        } finally {
            cerrarRecurso(resultado);
            cerrarRecurso(sentencia);
        }
        return detalles;
    }

    private int parsearNumeroFactura(String numeroFactura) {
        if (numeroFactura == null || numeroFactura.isBlank()) {
            throw new IllegalArgumentException("Número de factura inválido.");
        }
        try {
            return Integer.parseInt(numeroFactura.trim());
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException("Número de factura inválido: " + numeroFactura, excepcion);
        }
    }

    private void revertirTransaccion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.rollback();
            } catch (SQLException excepcion) {
                throw ExcepcionAccesoDatos.desde(excepcion);
            }
        }
    }

    private void restablecerConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException excepcion) {
                throw ExcepcionAccesoDatos.desde(excepcion);
            } finally {
                cerrarRecurso(conexion);
            }
        }
    }

    private void cerrarRecurso(AutoCloseable recurso) {
        if (recurso != null) {
            try {
                recurso.close();
            } catch (Exception excepcion) {
                throw new ExcepcionAccesoDatos("No fue posible cerrar el recurso JDBC.", excepcion);
            }
        }
    }
}
