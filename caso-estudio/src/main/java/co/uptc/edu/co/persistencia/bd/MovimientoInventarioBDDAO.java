package co.uptc.edu.co.persistencia.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.IGestionInventario;
import co.uptc.edu.co.modelo.MovimientoInventario;
import co.uptc.edu.co.util.LogUtil;

public class MovimientoInventarioBDDAO implements IGestionInventario {

    private static final String SQL_INSERTAR_MOVIMIENTO =
            "INSERT INTO movimientos_inventario (codigoProducto, tipoMovimiento, cantidad, fecha, descripcion) "
                    + "VALUES (?, ?, ?, ?, ?)";

    @Override
    public void guardar(MovimientoInventario movimiento) throws Exception {
        LogUtil.info("Entrando a guardar movimiento inventario. codigoProducto="
                + (movimiento != null ? movimiento.getCodigoProducto() : "null"));

        try (Connection conexion = ConexionBD.getConexion();
                PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_MOVIMIENTO)) {

            prepararInsert(sentencia, movimiento);
            sentencia.executeUpdate();

        } catch (SQLException e) {
            LogUtil.error("Error en guardar movimiento inventario: " + e.getMessage(), e);
            throw new Exception("Error al guardar movimiento de inventario: " + e.getMessage(), e);
        }
    }

    @Override
    public void guardar(Connection conexion, MovimientoInventario movimiento) throws Exception {
        LogUtil.info("Entrando a guardar movimiento inventario con conexion. codigoProducto="
                + (movimiento != null ? movimiento.getCodigoProducto() : "null"));

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_MOVIMIENTO)) {
            prepararInsert(sentencia, movimiento);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            LogUtil.error("Error en guardar movimiento inventario con conexion: " + e.getMessage(), e);
            throw new Exception("Error al guardar movimiento de inventario: " + e.getMessage(), e);
        }
    }

    @Override
    public void guardar(Connection conexion, List<MovimientoInventario> movimientos) throws Exception {
        LogUtil.info("Entrando a guardar movimientos inventario. count="
                + (movimientos != null ? movimientos.size() : 0));

        if (movimientos == null || movimientos.isEmpty()) {
            return;
        }

        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERTAR_MOVIMIENTO)) {
            for (MovimientoInventario movimiento : movimientos) {
                prepararInsert(sentencia, movimiento);
                sentencia.addBatch();
            }

            sentencia.executeBatch();

        } catch (SQLException e) {
            LogUtil.error("Error en guardar movimientos inventario: " + e.getMessage(), e);
            throw new Exception("Error al guardar movimientos de inventario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<MovimientoInventario> listar() throws Exception {
        throw new UnsupportedOperationException("Método listar() no implementado para movimientos de inventario.");
    }

    private void prepararInsert(PreparedStatement sentencia, MovimientoInventario movimiento) throws SQLException {
        sentencia.setString(1, movimiento.getCodigoProducto());
        sentencia.setString(2, movimiento.getTipoMovimiento().name());
        sentencia.setInt(3, movimiento.getCantidad());
        sentencia.setDate(4, java.sql.Date.valueOf(movimiento.getFechaMovimiento()));
        sentencia.setString(5, movimiento.getDescripcion());
    }
}