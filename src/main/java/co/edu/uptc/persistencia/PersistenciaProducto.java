package co.edu.uptc.persistencia;

import co.edu.uptc.enums.Categoria;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaProducto implements Repositorio<Producto> {

    private static final String SQL_INSERT = """
            INSERT INTO productos (codigo_producto, nombre_producto, categoria, precio_compra, precio_venta, 
                                   stock_actual, stock_minimo, stock_maximo, activo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                nombre_producto=VALUES(nombre_producto), categoria=VALUES(categoria), 
                precio_compra=VALUES(precio_compra), precio_venta=VALUES(precio_venta), 
                stock_actual=VALUES(stock_actual), stock_minimo=VALUES(stock_minimo), 
                stock_maximo=VALUES(stock_maximo), activo=VALUES(activo)
            """;

    private static final String SQL_DELETE = "UPDATE productos SET activo = false WHERE codigo_producto = ?";

    private static final String SQL_SELECT_ALL = "SELECT * FROM productos";

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM productos WHERE codigo_producto = ?";

    @Override
    public void guardar(Producto p) {
        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT)) {
            pstmt.setString(1, p.getCodigoInterno());
            pstmt.setString(2, p.getNombre());
            pstmt.setString(3, p.getCategoria() != null ? p.getCategoria().name() : Categoria.OTROS.name());
            pstmt.setDouble(4, p.getPrecioCompra());
            pstmt.setDouble(5, p.getPrecioVenta());
            pstmt.setInt(6, p.getStockActual());
            pstmt.setInt(7, p.getStockMinimo());
            pstmt.setInt(8, p.getStockMaximo());
            pstmt.setBoolean(9, p.isActivo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
    }

    @Override
    public void eliminar(String id) {
        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        try (Connection conn = ConexionSql.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
        return lista;
    }

    @Override
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

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto(
                rs.getString("nombre_producto"),
                rs.getString("codigo_producto"),
                rs.getDouble("precio_compra"),
                rs.getDouble("precio_venta"),
                rs.getInt("stock_actual"),
                rs.getInt("stock_minimo"),
                rs.getInt("stock_maximo"),
                Categoria.valueOf(rs.getString("categoria"))
        );
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }

    /**
     * Obtiene una lista simplificada de productos para mostrar en la tabla de la interfaz.
     */
    public List<ProductoResumenDTO> listarResumen() {
        List<ProductoResumenDTO> lista = new ArrayList<>();
        String sql = "SELECT codigo_producto, nombre_producto, categoria, precio_venta, stock_actual, stock_minimo FROM productos";
        
        try (Connection conn = ConexionSql.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int stock = rs.getInt("stock_actual");
                int min = rs.getInt("stock_minimo");
                String alerta = (stock <= min) ? "STOCK BAJO" : "OK";

                lista.add(new ProductoResumenDTO(
                        rs.getString("codigo_producto"),
                        rs.getString("nombre_producto"),
                        rs.getString("categoria"),
                        rs.getDouble("precio_venta"),
                        stock,
                        alerta
                ));
            }
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
        return lista;
    }
}
