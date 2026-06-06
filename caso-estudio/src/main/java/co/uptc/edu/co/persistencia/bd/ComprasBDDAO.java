package co.uptc.edu.co.persistencia.bd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.DetalleCompra;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.enums.EstadoCompraEnum;
import co.uptc.edu.co.modelo.enums.FormaPago;
import co.uptc.edu.co.util.LogUtil;

public class ComprasBDDAO implements IGestionCompra {

    private static final String TABLA_COMPRAS = "compras";
    private static final String TABLA_DETALLE_COMPRAS = "detalle_compras";

    private static final String SQL_INSERTAR_COMPRA = "INSERT INTO " + TABLA_COMPRAS
            + " (numeroFacturaProveedor, fecha, codigoProveedor, formaPago, subtotal, impuestos, totalCompra, estado, motivoAnulacion)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERTAR_DETALLE = "INSERT INTO " + TABLA_DETALLE_COMPRAS
            + " (idCompra, codigoProducto, cantidad, costoUnitario, subtotal, impuestos, total)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR_COMPRA = "UPDATE " + TABLA_COMPRAS
            + " SET numeroFacturaProveedor = ?, fecha = ?, codigoProveedor = ?, formaPago = ?, subtotal = ?, impuestos = ?, totalCompra = ?, estado = ?, motivoAnulacion = ?"
            + " WHERE numeroFacturaProveedor = ?";

    private static final String SQL_BUSCAR_COMPRA = "SELECT c.numeroFacturaProveedor, c.fecha, c.codigoProveedor, p.razonSocial, c.formaPago, c.subtotal, c.impuestos, c.totalCompra, c.estado, c.motivoAnulacion"
            + " FROM " + TABLA_COMPRAS + " c LEFT JOIN proveedores p ON c.codigoProveedor = p.codigoProveedor"
            + " WHERE c.numeroFacturaProveedor = ?";

    private static final String SQL_BUSCAR_ID_COMPRA = "SELECT idCompra FROM " + TABLA_COMPRAS
            + " WHERE numeroFacturaProveedor = ?";

    private static final String SQL_LISTAR_COMPRAS = "SELECT c.numeroFacturaProveedor, c.fecha, c.codigoProveedor, p.razonSocial, c.formaPago, c.subtotal, c.impuestos, c.totalCompra, c.estado, c.motivoAnulacion"
            + " FROM " + TABLA_COMPRAS + " c LEFT JOIN proveedores p ON c.codigoProveedor = p.codigoProveedor"
            + " ORDER BY c.fecha DESC";

    private static final String SQL_LISTAR_DETALLES = "SELECT dc.idDetalle, dc.idCompra, dc.codigoProducto, p.nombreProducto, dc.cantidad, dc.costoUnitario, dc.subtotal, dc.impuestos, dc.total"
            + " FROM " + TABLA_DETALLE_COMPRAS + " dc LEFT JOIN productos p ON dc.codigoProducto = p.codigoProducto"
            + " WHERE dc.idCompra = ?";

    private static final String SQL_ELIMINAR_DETALLES = "DELETE FROM " + TABLA_DETALLE_COMPRAS + " WHERE idCompra = ?";

    private static final String SQL_ELIMINAR_COMPRA = "DELETE FROM " + TABLA_COMPRAS
            + " WHERE numeroFacturaProveedor = ?";

    public void guardar(Compra compra) throws Exception {
        LogUtil.info("Entrando a guardar. factura="
                + (compra != null ? compra.getNumeroFacturaProveedor() : "null"));

        try (Connection conexion = ConexionBD.getConexion()) {
            conexion.setAutoCommit(false);

            try {
                guardarCabeceraCompra(conexion, compra);
                int idCompra = obtenerIdCompra(conexion, compra.getNumeroFacturaProveedor());
                guardarDetallesCompra(conexion, compra, idCompra);
                conexion.commit();

            } catch (Exception e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            LogUtil.error("Error en guardar: " + e.getMessage(), e);
            throw new Exception("Error al guardar la compra en el servidor remoto: " + e.getMessage(), e);
        }
    }

 
    public void guardar(Connection conexion, Compra compra) throws Exception {
        LogUtil.info("Entrando a guardar(conn). factura="
                + (compra != null ? compra.getNumeroFacturaProveedor() : "null"));

        guardarCabeceraCompra(conexion, compra);
        int idCompra = obtenerIdCompra(conexion, compra.getNumeroFacturaProveedor());
        guardarDetallesCompra(conexion, compra, idCompra);
    }

   
    public void actualizar(Compra compra) throws Exception {
        LogUtil.info("Entrando a actualizar. factura="
                + (compra != null ? compra.getNumeroFacturaProveedor() : "null"));

        try (Connection conexion = ConexionBD.getConexion()) {
            conexion.setAutoCommit(false);

            try {
                actualizar(conexion, compra);
                conexion.commit();

            } catch (Exception e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            LogUtil.error("Error en actualizar: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void actualizar(Connection conexion, Compra compra) throws Exception {
        LogUtil.info("Entrando a actualizar(conn). factura="
                + (compra != null ? compra.getNumeroFacturaProveedor() : "null"));

        int idCompra = obtenerIdCompra(conexion, compra.getNumeroFacturaProveedor());
        actualizarCabeceraCompra(conexion, compra);
        eliminarDetallescompra(conexion, idCompra);
        guardarDetallesCompra(conexion, compra, idCompra);
    }

    @Override
    public Compra buscar(String numeroFactura) throws Exception {
        LogUtil.info("Entrando a buscar. numeroFactura=" + numeroFactura);

        try (Connection conexion = ConexionBD.getConexion()) {
            return buscar(conexion, numeroFactura);

        } catch (SQLException e) {
            LogUtil.error("Error en buscar: " + e.getMessage(), e);
            throw new Exception("Error al buscar la compra solicitada: " + e.getMessage(), e);
        }
    }

    @Override
    public Compra buscar(Connection conexion, String numeroFactura) throws Exception {
        LogUtil.info("Entrando a buscar(conn). numeroFactura=" + numeroFactura);

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_COMPRA)) {
            sentencia.setString(1, numeroFactura);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    Compra compra = construirCompra(resultado);
                    int idCompra = obtenerIdCompra(conexion, compra.getNumeroFacturaProveedor());
                    compra.setDetalles(listarDetallesCompra(conexion, idCompra));
                    return compra;
                }
            }

            return null;
        }
    }

    @Override
    public List<Compra> listar() throws Exception {
        LogUtil.info("Entrando a listar");

        List<Compra> compras = new ArrayList<>();

        try (Connection conexion = ConexionBD.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_COMPRAS);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                Compra compra = construirCompra(resultado);
                int idCompra = obtenerIdCompra(conexion, compra.getNumeroFacturaProveedor());
                compra.setDetalles(listarDetallesCompra(conexion, idCompra));
                compras.add(compra);
            }

            return compras;

        } catch (SQLException e) {
            LogUtil.error("Error en listar: " + e.getMessage(), e);
            throw new Exception("Error al listar las compras: " + e.getMessage(), e);
        }
    }

    public void eliminarCompra(String numeroFactura) throws Exception {
        LogUtil.info("Entrando a eliminarCompra. numeroFactura=" + numeroFactura);

        try (Connection conexion = ConexionBD.getConexion()) {
            conexion.setAutoCommit(false);

            try {
                int idCompra = obtenerIdCompra(conexion, numeroFactura);
                eliminarDetallescompra(conexion, idCompra);

                try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR_COMPRA)) {
                    sentencia.setString(1, numeroFactura);
                    sentencia.executeUpdate();
                }

                conexion.commit();

            } catch (Exception e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            LogUtil.error("Error en eliminarCompra: " + e.getMessage(), e);
            throw e;
        }
    }

    private void guardarCabeceraCompra(Connection conexion, Compra compra) throws SQLException {
        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_COMPRA)) {
            prepararInsertCompra(sentencia, compra);
            sentencia.executeUpdate();
        }
    }

    private void actualizarCabeceraCompra(Connection conexion, Compra compra) throws SQLException {
        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR_COMPRA)) {
            prepararInsertCompra(sentencia, compra);
            sentencia.setString(10, compra.getNumeroFacturaProveedor());
            sentencia.executeUpdate();
        }
    }

    private void guardarDetallesCompra(Connection conexion, Compra compra, int idCompra) throws SQLException {
        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_DETALLE)) {
            for (DetalleCompra detalle : compra.getDetalles()) {
                prepararInsertDetalle(sentencia, idCompra, detalle);
                sentencia.addBatch();
            }

            sentencia.executeBatch();
        }
    }

    private void eliminarDetallescompra(Connection conexion, int idCompra) throws SQLException {
        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR_DETALLES)) {
            sentencia.setInt(1, idCompra);
            sentencia.executeUpdate();
        }
    }

    private List<DetalleCompra> listarDetallesCompra(Connection conexion, int idCompra) throws SQLException {
        List<DetalleCompra> detalles = new ArrayList<>();

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_LISTAR_DETALLES)) {
            sentencia.setInt(1, idCompra);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    detalles.add(construirDetalleCompra(resultado));
                }
            }
        }

        return detalles;
    }

    private int obtenerIdCompra(Connection conexion, String numeroFacturaProveedor) throws SQLException {
        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_BUSCAR_ID_COMPRA)) {
            sentencia.setString(1, numeroFacturaProveedor);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("idCompra");
                }
            }
        }

        throw new SQLException("No se encontró la compra con número de factura " + numeroFacturaProveedor);
    }

    private Compra construirCompra(ResultSet resultado) throws SQLException {
        Compra compra = new Compra();

        compra.setNumeroFacturaProveedor(resultado.getString("numeroFacturaProveedor"));

        Date fechaSql = resultado.getDate("fecha");
        if (fechaSql != null) {
            compra.setFecha(fechaSql.toLocalDate());
        }

        compra.setCodigoProveedor(resultado.getString("codigoProveedor"));
        compra.setProveedor(resultado.getString("razonSocial"));
        compra.setFormaPago(parseFormaPago(resultado.getString("formaPago")));
        compra.setSubtotal(resultado.getDouble("subtotal"));
        compra.setImpuestos(resultado.getDouble("impuestos"));
        compra.setTotalCompra(resultado.getDouble("totalCompra"));

        String estadoStr = resultado.getString("estado");
        if (estadoStr != null && !estadoStr.isEmpty()) {
            compra.setEstado(EstadoCompraEnum.valueOf(estadoStr.trim().toUpperCase()));
        }

        compra.setMotivoAnulacion(resultado.getString("motivoAnulacion"));

        return compra;
    }

    private FormaPago parseFormaPago(String valor) throws SQLException {
        if (valor == null || valor.isBlank()) {
            throw new SQLException("La forma de pago viene vacía en la base de datos.");
        }

        String normalizado = valor.trim();

        for (FormaPago formaPago : FormaPago.values()) {
            if (formaPago.name().equalsIgnoreCase(normalizado)
                    || formaPago.toString().equalsIgnoreCase(normalizado)) {
                return formaPago;
            }
        }

        throw new SQLException("Forma de pago no reconocida: " + valor);
    }

    private DetalleCompra construirDetalleCompra(ResultSet resultado) throws SQLException {
        DetalleCompra detalle = new DetalleCompra();
        Producto producto = new Producto();

        producto.setCodigoProducto(resultado.getString("codigoProducto"));
        producto.setNombreProducto(resultado.getString("nombreProducto"));

        detalle.setProducto(producto);
        detalle.setCantidad(resultado.getInt("cantidad"));
        detalle.setCostoUnitario(resultado.getDouble("costoUnitario"));
        detalle.setSubtotal(resultado.getDouble("subtotal"));
        detalle.setImpuestos(resultado.getDouble("impuestos"));
        detalle.setTotalCompra(resultado.getDouble("total"));

        return detalle;
    }

    private void prepararInsertDetalle(PreparedStatement sentencia, int idCompra, DetalleCompra detalle)
            throws SQLException {

        sentencia.setInt(1, idCompra);
        sentencia.setString(2, detalle.getProducto() != null ? detalle.getProducto().getCodigoProducto() : null);
        sentencia.setInt(3, detalle.getCantidad());
        sentencia.setDouble(4, detalle.getCostoUnitario());
        sentencia.setDouble(5, detalle.getSubtotal());
        sentencia.setDouble(6, detalle.getImpuestos());
        sentencia.setDouble(7, detalle.getTotalCompra());
    }

    private void prepararInsertCompra(PreparedStatement sentencia, Compra compra) throws SQLException {
        sentencia.setString(1, compra.getNumeroFacturaProveedor());
        sentencia.setDate(2, compra.getFecha() != null ? Date.valueOf(compra.getFecha()) : null);
        sentencia.setString(3, compra.getCodigoProveedor());
        sentencia.setString(4, compra.getFormaPago().name());
        sentencia.setDouble(5, compra.getSubtotal());
        sentencia.setDouble(6, compra.getImpuestos());
        sentencia.setDouble(7, compra.getTotalCompra());
        sentencia.setString(8, compra.getEstado() != null ? compra.getEstado().name() : null);
        sentencia.setString(9, compra.getMotivoAnulacion());
    }
}