package co.edu.uptc.persistencia;

import co.edu.uptc.enums.CategoriaProducto;
import co.edu.uptc.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaProducto {

    private static final String SQL_INSERT = """
            INSERT INTO producto (
                codigo_interno, nombre_producto, categoria, precio_compra, precio_venta,
                stock_actual, stock_minimo, stock_maximo, activo
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_DELETE = """
            DELETE FROM producto WHERE codigo_interno = ?
            """;

    private static final String SQL_SELECT_ALL = """
            SELECT codigo_interno, nombre_producto, categoria, precio_compra, precio_venta,
                   stock_actual, stock_minimo, stock_maximo, activo
            FROM producto
            """;

    private static final String SQL_SELECT_BY_ID = """
            SELECT codigo_interno, nombre_producto, categoria, precio_compra, precio_venta,
                   stock_actual, stock_minimo, stock_maximo, activo
            FROM producto
            WHERE codigo_interno = ?
            """;

    static final String SQL_DESCONTAR_STOCK = """
            UPDATE producto
            SET stock_actual = stock_actual - ?
            WHERE codigo_interno = ? AND stock_actual >= ? AND activo = 1
            """;

    static final String SQL_DEVOLVER_STOCK = """
            UPDATE producto
            SET stock_actual = stock_actual + ?
            WHERE codigo_interno = ?
            """;

    private static final String SQL_ACTIVAR_POR_CODIGO = """
            UPDATE producto SET activo = 1 WHERE codigo_interno = ?
            """;

    private static final String SQL_INACTIVAR_POR_CODIGO = """
            UPDATE producto SET activo = 0 WHERE codigo_interno = ?
            """;

    static final String SQL_INCREMENTAR_STOCK = """
            UPDATE producto
            SET stock_actual = stock_actual + ?
            WHERE codigo_interno = ?
              AND (stock_actual + ?) <= stock_maximo
              AND activo = 1
            """;

    private static final String SQL_SELECT_BY_ID_CONEXION = """
            SELECT codigo_interno, nombre_producto, categoria, precio_compra, precio_venta,
                   stock_actual, stock_minimo, stock_maximo, activo
            FROM producto
            WHERE codigo_interno = ?
            """;

    public void guardar(Producto producto) {
        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT)) {

            pstmt.setString(1, producto.getCodigoInterno());
            pstmt.setString(2, producto.getNombreProducto());
            pstmt.setString(3, producto.getCategoria() != null ? producto.getCategoria().name() : CategoriaProducto.VIVERES.name());
            pstmt.setDouble(4, producto.getPrecioCompra());
            pstmt.setDouble(5, producto.getPrecioVenta());
            pstmt.setInt(6, producto.getStockActual());
            pstmt.setInt(7, producto.getStockMinimo());
            pstmt.setInt(8, producto.getStockMaximo());
            pstmt.setBoolean(9, producto.isActivo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
    }

    public void eliminar(String id) {
        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
    }

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
        return lista;
    }

    public Producto buscarPorId(String id) {
        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
        return null;
    }

    static int descontarStock(Connection conn, String codigoProducto, int cantidad) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DESCONTAR_STOCK)) {
            pstmt.setInt(1, cantidad);
            pstmt.setString(2, codigoProducto);
            pstmt.setInt(3, cantidad);
            return pstmt.executeUpdate();
        }
    }

    static void devolverStock(Connection conn, String codigoProducto, int cantidad) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_DEVOLVER_STOCK)) {
            pstmt.setInt(1, cantidad);
            pstmt.setString(2, codigoProducto);
            pstmt.executeUpdate();
        }
    }

    static int incrementarStock(Connection conn, String codigoProducto, int cantidad) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_INCREMENTAR_STOCK)) {
            pstmt.setInt(1, cantidad);
            pstmt.setString(2, codigoProducto);
            pstmt.setInt(3, cantidad);
            return pstmt.executeUpdate();
        }
    }

    static Producto buscarPorCodigo(Connection conn, String codigoInterno) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_ID_CONEXION)) {
            pstmt.setString(1, codigoInterno);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProducto(rs);
                }
            }
        }
        return null;
    }

    private static Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto(
                rs.getString("codigo_interno"),
                rs.getString("nombre_producto"),
                CategoriaProducto.valueOf(rs.getString("categoria")),
                rs.getDouble("precio_compra"),
                rs.getDouble("precio_venta"),
                rs.getInt("stock_actual"),
                rs.getInt("stock_minimo"),
                rs.getInt("stock_maximo")
        );
        producto.setActivo(rs.getBoolean("activo"));
        return producto;
    }

    public void activarPorCodigoInterno(String codigoInterno) {
        ejecutarActualizacionActivo(SQL_ACTIVAR_POR_CODIGO, codigoInterno);
    }

    public void inactivarPorCodigoInterno(String codigoInterno) {
        ejecutarActualizacionActivo(SQL_INACTIVAR_POR_CODIGO, codigoInterno);
    }

    private void ejecutarActualizacionActivo(String sql, String codigoInterno) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, codigoInterno);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }
}
