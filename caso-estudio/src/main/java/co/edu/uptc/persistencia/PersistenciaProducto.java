package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.enums.CategoriaProducto;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaProducto implements Repositorio<Producto> {

    @Override
    public void guardar(Producto producto) {
        String sql = """
            INSERT INTO productos (codigo_producto, nombre_producto, categoria, precio_compra, precio_venta,
                stock_actual, stock_minimo, stock_maximo, activo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            llenarStatement(ps, producto);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar producto en BD: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        String sql = "UPDATE productos SET activo = 0 WHERE codigo_producto = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al inactivar producto: " + e.getMessage());
        }
    }

    @Override
    public Producto buscarPorId(String id) {
        String sql = "SELECT * FROM productos WHERE codigo_producto = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = """
            UPDATE productos SET nombre_producto = ?, categoria = ?, precio_compra = ?, precio_venta = ?,
                stock_actual = ?, stock_minimo = ?, stock_maximo = ?, activo = ?
            WHERE codigo_producto = ?
            """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto.getNombreProducto());
            ps.setString(2, producto.getCategoria() != null ? producto.getCategoria().name() : "");
            ps.setDouble(3, producto.getPrecioCompra());
            ps.setDouble(4, producto.getPrecioVenta());
            ps.setInt(5, producto.getStockActual());
            ps.setInt(6, producto.getStockMinimo());
            ps.setInt(7, producto.getStockMaximo());
            ps.setBoolean(8, producto.isActivo());
            ps.setString(9, producto.getCodigoProducto());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    private void llenarStatement(PreparedStatement ps, Producto p) throws SQLException {
        ps.setString(1, p.getCodigoProducto());
        ps.setString(2, p.getNombreProducto());
        ps.setString(3, p.getCategoria() != null ? p.getCategoria().name() : "");
        ps.setDouble(4, p.getPrecioCompra());
        ps.setDouble(5, p.getPrecioVenta());
        ps.setInt(6, p.getStockActual());
        ps.setInt(7, p.getStockMinimo());
        ps.setInt(8, p.getStockMaximo());
        ps.setBoolean(9, p.isActivo());
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setCodigoProducto(rs.getString("codigo_producto"));
        p.setNombreProducto(rs.getString("nombre_producto"));
        p.setCategoria(CategoriaProducto.desdeTexto(leerCategoria(rs)));
        p.setPrecioCompra(rs.getDouble("precio_compra"));
        p.setPrecioVenta(rs.getDouble("precio_venta"));
        p.setStockActual(rs.getInt("stock_actual"));
        p.setStockMinimo(rs.getInt("stock_minimo"));
        p.setStockMaximo(rs.getInt("stock_maximo"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }

    private String leerCategoria(ResultSet rs) throws SQLException {
        String categoria = rs.getString("categoria");
        return categoria != null ? categoria : "";
    }
}
