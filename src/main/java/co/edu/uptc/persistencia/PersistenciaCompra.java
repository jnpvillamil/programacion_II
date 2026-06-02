package co.edu.uptc.persistencia;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleCompra;
import co.edu.uptc.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCompra {

    private static final String SQL_INSERT_COMPRA = """
            INSERT INTO compra (numero_factura_proveedor, nit_proveedor, total)
            VALUES (?, ?, ?)
            """;

    private static final String SQL_INSERT_DETALLE = """
            INSERT INTO detalle_compra (
                numero_factura_proveedor, codigo_producto, cantidad, costo_unitario, subtotal
            ) VALUES (?, ?, ?, ?, ?)
            """;

    private static final String SQL_EXISTE_PROVEEDOR = """
            SELECT 1 FROM proveedor WHERE nit = ? AND estado = 'Activo' LIMIT 1
            """;

    private static final String SQL_EXISTE_COMPRA = """
            SELECT 1 FROM compra WHERE numero_factura_proveedor = ? LIMIT 1
            """;

    private static final String SQL_SELECT_RESUMEN = """
            SELECT numero_factura_proveedor, nit_proveedor, fecha, total
            FROM compra
            ORDER BY fecha DESC
            """;

    public void guardar(Compra compra) {
        Connection conexion = null;
        try {
            conexion = ConexionSql.getConexion();
            conexion.setAutoCommit(false);
            guardarEnTransaccion(conexion, compra);
            conexion.commit();
        } catch (SQLException excepcion) {
            revertirTransaccion(conexion);
            throw ExcepcionAccesoDatos.desde(excepcion);
        } finally {
            restablecerConexion(conexion);
        }
    }

    public void guardarEnTransaccion(Connection conexion, Compra compra) throws SQLException {
        validarProveedor(conexion, compra.getProveedor().getNit());
        validarFacturaUnica(conexion, compra.getNumeroFacturaProveedor());

        PreparedStatement sentenciaCompra = null;
        try {
            sentenciaCompra = conexion.prepareStatement(SQL_INSERT_COMPRA);
            sentenciaCompra.setString(1, compra.getNumeroFacturaProveedor());
            sentenciaCompra.setString(2, compra.getProveedor().getNit());
            sentenciaCompra.setDouble(3, compra.getTotal());
            sentenciaCompra.executeUpdate();
        } finally {
            cerrarRecurso(sentenciaCompra);
        }

        PreparedStatement sentenciaDetalle = null;
        try {
            sentenciaDetalle = conexion.prepareStatement(SQL_INSERT_DETALLE);
            for (DetalleCompra detalle : compra.getListaDetalles()) {
                String codigoProducto = detalle.getProducto().getCodigoInterno();
                Producto producto = PersistenciaProducto.buscarPorCodigo(conexion, codigoProducto);
                if (producto == null) {
                    throw new SQLException("Producto no encontrado: " + codigoProducto);
                }
                if (!producto.isActivo()) {
                    throw new SQLException("Producto inactivo: " + codigoProducto);
                }
                if (producto.getStockActual() + detalle.getCantidad() > producto.getStockMaximo()) {
                    throw new SQLException(
                            "El stock final supera el máximo permitido para el producto: "
                                    + producto.getNombreProducto());
                }

                int filasStock = PersistenciaProducto.incrementarStock(
                        conexion, codigoProducto, detalle.getCantidad());
                if (filasStock == 0) {
                    throw new SQLException(
                            "No fue posible incrementar el stock del producto: "
                                    + producto.getNombreProducto());
                }

                sentenciaDetalle.setString(1, compra.getNumeroFacturaProveedor());
                sentenciaDetalle.setString(2, codigoProducto);
                sentenciaDetalle.setInt(3, detalle.getCantidad());
                sentenciaDetalle.setDouble(4, detalle.getCostoUnitario());
                sentenciaDetalle.setDouble(5, detalle.getSubtotal());
                sentenciaDetalle.addBatch();
            }
            sentenciaDetalle.executeBatch();
        } finally {
            cerrarRecurso(sentenciaDetalle);
        }
    }

    public List<CompraDTO> listarResumen() {
        List<CompraDTO> lista = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            conexion = ConexionSql.getConexion();
            sentencia = conexion.prepareStatement(SQL_SELECT_RESUMEN);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                Timestamp fecha = resultado.getTimestamp("fecha");
                LocalDateTime fechaCompra = fecha != null ? fecha.toLocalDateTime() : LocalDateTime.now();
                lista.add(new CompraDTO(
                        resultado.getString("numero_factura_proveedor"),
                        resultado.getString("nit_proveedor"),
                        fechaCompra,
                        resultado.getDouble("total")));
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

    private void validarProveedor(Connection conexion, String nit) throws SQLException {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            sentencia = conexion.prepareStatement(SQL_EXISTE_PROVEEDOR);
            sentencia.setString(1, nit);
            resultado = sentencia.executeQuery();
            if (!resultado.next()) {
                throw new SQLException("Proveedor no registrado o inactivo con NIT: " + nit);
            }
        } finally {
            cerrarRecurso(resultado);
            cerrarRecurso(sentencia);
        }
    }

    private void validarFacturaUnica(Connection conexion, String numeroFactura) throws SQLException {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        try {
            sentencia = conexion.prepareStatement(SQL_EXISTE_COMPRA);
            sentencia.setString(1, numeroFactura);
            resultado = sentencia.executeQuery();
            if (resultado.next()) {
                throw new SQLException("Ya existe una compra con la factura: " + numeroFactura);
            }
        } finally {
            cerrarRecurso(resultado);
            cerrarRecurso(sentencia);
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
