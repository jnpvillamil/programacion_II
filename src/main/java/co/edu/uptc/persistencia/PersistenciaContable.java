package co.edu.uptc.persistencia;

import co.edu.uptc.dto.MovimientoResumenDTO;
import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.dto.ReporteUtilidadDTO;
import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.interfaces.RepositorioSistema;
import co.edu.uptc.modelo.MovimientoContable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaContable implements RepositorioSistema {

    private static final String SQL_INSERT = """
            INSERT INTO movimiento_contable (
                codigo_transaccion, fecha_movimiento, tipo_movimiento,
                cuenta_contable, valor_movimiento, descripcion
            ) VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_SELECT_ALL = """
            SELECT codigo_transaccion, fecha_movimiento, tipo_movimiento,
                   cuenta_contable, valor_movimiento, descripcion
            FROM movimiento_contable
            ORDER BY fecha_movimiento DESC
            """;

    private static final String SQL_DELETE = """
            DELETE FROM movimiento_contable WHERE codigo_transaccion = ?
            """;

    private static final String SQL_SELECT_BY_ID = """
            SELECT codigo_transaccion, fecha_movimiento, tipo_movimiento,
                   cuenta_contable, valor_movimiento, descripcion
            FROM movimiento_contable
            WHERE codigo_transaccion = ?
            """;

    private static final String SQL_SUMAR_VENTA_DIA = """
            SELECT COALESCE(SUM(total), 0)
            FROM venta
            WHERE estado = 'ACTIVA' AND DATE(fecha_hora) = CURDATE()
            """;

    private static final String SQL_SUMAR_VENTA_MES = """
            SELECT COALESCE(SUM(total), 0)
            FROM venta
            WHERE estado = 'ACTIVA'
              AND MONTH(fecha_hora) = MONTH(CURDATE())
              AND YEAR(fecha_hora) = YEAR(CURDATE())
            """;

    private static final String SQL_SUMAR_VENTA_ANIO = """
            SELECT COALESCE(SUM(total), 0)
            FROM venta
            WHERE estado = 'ACTIVA' AND YEAR(fecha_hora) = YEAR(CURDATE())
            """;

    private static final String SQL_UTILIDAD_BRUTA = """
            SELECT COALESCE(SUM(dv.subtotal), 0) - COALESCE(SUM(dv.cantidad * p.precio_compra), 0)
            FROM detalle_venta dv
            INNER JOIN venta v ON v.numero_factura = dv.numero_factura
            INNER JOIN producto p ON p.codigo_interno = dv.codigo_producto
            WHERE v.estado = 'ACTIVA'
              AND YEAR(v.fecha_hora) = YEAR(CURDATE())
            """;

    private static final String SQL_RANKING_PRODUCTO = """
            SELECT p.nombre_producto, SUM(dv.cantidad) AS cantidad_vendida
            FROM detalle_venta dv
            INNER JOIN venta v ON v.numero_factura = dv.numero_factura
            INNER JOIN producto p ON p.codigo_interno = dv.codigo_producto
            WHERE v.estado = 'ACTIVA'
              AND YEAR(v.fecha_hora) = YEAR(CURDATE())
            GROUP BY p.codigo_interno, p.nombre_producto
            ORDER BY cantidad_vendida DESC
            LIMIT 5
            """;

    private static final String SQL_RANKING_CLIENTE = """
            SELECT CONCAT(c.nombres, ' ', c.apellidos) AS nombre_cliente,
                   SUM(v.total) AS monto_total
            FROM venta v
            INNER JOIN cliente c ON c.identificacion = v.identificacion_cliente
            WHERE v.estado = 'ACTIVA'
              AND YEAR(v.fecha_hora) = YEAR(CURDATE())
            GROUP BY c.identificacion, c.nombres, c.apellidos
            ORDER BY monto_total DESC
            LIMIT 5
            """;

    private static final String SQL_BALANCE_INGRESO = """
            SELECT COALESCE(SUM(valor_movimiento), 0)
            FROM movimiento_contable
            WHERE tipo_movimiento = 'INGRESO'
            """;

    private static final String SQL_BALANCE_EGRESO = """
            SELECT COALESCE(SUM(valor_movimiento), 0)
            FROM movimiento_contable
            WHERE tipo_movimiento = 'EGRESO'
            """;

    private static final String SQL_UTILIDAD_POR_PRODUCTO = """
            SELECT p.nombre_producto,
                   COALESCE(SUM(dv.subtotal - (dv.cantidad * p.precio_compra)), 0) AS utilidad_bruta
            FROM detalle_venta dv
            INNER JOIN venta v ON v.numero_factura = dv.numero_factura
            INNER JOIN producto p ON p.codigo_interno = dv.codigo_producto
            WHERE v.estado = 'ACTIVA'
              AND YEAR(v.fecha_hora) = YEAR(CURDATE())
            GROUP BY p.codigo_interno, p.nombre_producto
            ORDER BY utilidad_bruta DESC
            LIMIT 10
            """;

    @Override
    public void guardarMovimientoContable(MovimientoContable movimiento) {
        try (Connection conexion = ConexionSql.getConexion()) {
            insertarMovimiento(conexion, movimiento);
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public void guardarEnTransaccion(Connection conexion, MovimientoContable movimiento) throws SQLException {
        insertarMovimiento(conexion, movimiento);
    }

    public void eliminar(String id) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_DELETE)) {
            sentencia.setString(1, id);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    @Override
    public List<MovimientoContable> listarMovimientoContable() {
        List<MovimientoContable> lista = new ArrayList<>();
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                lista.add(mapearMovimiento(resultado));
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return lista;
    }

    public MovimientoContable buscarPorId(String id) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_BY_ID)) {
            sentencia.setString(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearMovimiento(resultado);
                }
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return null;
    }

    @Override
    public List<MovimientoResumenDTO> listarResumenMovimiento() {
        List<MovimientoResumenDTO> lista = new ArrayList<>();
        for (MovimientoContable movimiento : listarMovimientoContable()) {
            lista.add(new MovimientoResumenDTO(
                    movimiento.getFechaMovimiento(),
                    movimiento.getTipoMovimiento().name(),
                    movimiento.getCuentaContable(),
                    movimiento.getValorMovimiento(),
                    movimiento.getDescripcion()));
        }
        return lista;
    }

    @Override
    public ReporteFinancieroDTO construirReporteFinanciero() {
        try (Connection conexion = ConexionSql.getConexion()) {
            double totalDiario = consultarDouble(conexion, SQL_SUMAR_VENTA_DIA);
            double totalMensual = consultarDouble(conexion, SQL_SUMAR_VENTA_MES);
            double totalAnual = consultarDouble(conexion, SQL_SUMAR_VENTA_ANIO);
            double utilidadBruta = consultarDouble(conexion, SQL_UTILIDAD_BRUTA);
            double totalIngreso = consultarDouble(conexion, SQL_BALANCE_INGRESO);
            double totalEgreso = consultarDouble(conexion, SQL_BALANCE_EGRESO);

            List<ReporteFinancieroDTO.RankingProductoDTO> rankingProducto = new ArrayList<>();
            try (PreparedStatement sentencia = conexion.prepareStatement(SQL_RANKING_PRODUCTO);
                 ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    rankingProducto.add(new ReporteFinancieroDTO.RankingProductoDTO(
                            resultado.getString("nombre_producto"),
                            resultado.getInt("cantidad_vendida")));
                }
            }

            List<ReporteFinancieroDTO.RankingClienteDTO> rankingCliente = new ArrayList<>();
            try (PreparedStatement sentencia = conexion.prepareStatement(SQL_RANKING_CLIENTE);
                 ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    rankingCliente.add(new ReporteFinancieroDTO.RankingClienteDTO(
                            resultado.getString("nombre_cliente"),
                            resultado.getDouble("monto_total")));
                }
            }

            return new ReporteFinancieroDTO(
                    LocalDateTime.now().toString(),
                    new ReporteFinancieroDTO.ResumenPeriodoVentaDTO(totalDiario, totalMensual, totalAnual),
                    utilidadBruta,
                    rankingProducto,
                    rankingCliente,
                    new ReporteFinancieroDTO.BalanceResumenDTO(
                            totalIngreso,
                            totalEgreso,
                            totalIngreso - totalEgreso));
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    @Override
    public List<ReporteUtilidadDTO> listarReporteUtilidad() {
        List<ReporteUtilidadDTO> lista = new ArrayList<>();
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_UTILIDAD_POR_PRODUCTO);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                lista.add(new ReporteUtilidadDTO(
                        resultado.getString("nombre_producto"),
                        resultado.getDouble("utilidad_bruta")));
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return lista;
    }

    private void insertarMovimiento(Connection conexion, MovimientoContable movimiento) throws SQLException {
        try (PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERT)) {
            sentencia.setString(1, movimiento.getCodigoTransaccion());
            sentencia.setTimestamp(2, Timestamp.valueOf(movimiento.getFechaMovimiento()));
            sentencia.setString(3, movimiento.getTipoMovimiento().name());
            sentencia.setString(4, movimiento.getCuentaContable());
            sentencia.setDouble(5, movimiento.getValorMovimiento());
            sentencia.setString(6, movimiento.getDescripcion());
            sentencia.executeUpdate();
        }
    }

    private MovimientoContable mapearMovimiento(ResultSet resultado) throws SQLException {
        Timestamp fecha = resultado.getTimestamp("fecha_movimiento");
        return new MovimientoContable(
                resultado.getString("codigo_transaccion"),
                fecha != null ? fecha.toLocalDateTime() : LocalDateTime.now(),
                TipoMovimiento.valueOf(resultado.getString("tipo_movimiento")),
                resultado.getString("cuenta_contable"),
                resultado.getDouble("valor_movimiento"),
                resultado.getString("descripcion"));
    }

    private double consultarDouble(Connection conexion, String sql) throws SQLException {
        try (PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultado = sentencia.executeQuery()) {
            if (resultado.next()) {
                return resultado.getDouble(1);
            }
        }
        return 0;
    }
}
