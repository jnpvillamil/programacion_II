package co.uptc.edu.tienda.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.conexion.Conexion;
import co.uptc.edu.tienda.interfaces.IGestionProducto;
import co.uptc.edu.tienda.modelo.Producto;

public class SqlProducto implements IGestionProducto {

    @Override
    public void guardar(Producto p) {
        Conexion conex = new Conexion();
        try {
            String sql = "INSERT INTO productos (nombre_producto, categoria, precio_compra, "
                       + "precio_venta, stock_actual, stock_minimo, stock_maximo, activo, porcentaje_iva) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, p.getNombreProducto());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecioCompra());
            ps.setDouble(4, p.getPrecioVenta());
            ps.setInt(5, p.getStockActual());
            ps.setInt(6, p.getStockMinimo());
            ps.setInt(7, p.getStockMaximo());
            ps.setBoolean(8, p.isActivo());
            ps.setDouble(9, p.getPorcentajeIva());
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
    }

    @Override
    public void guardarTodos(List<Producto> productos) {
        for (Producto p : productos) {
            actualizar(p);
        }
    }

    @Override
    public void actualizar(Producto p) {
        Conexion conex = new Conexion();
        try {
            String sql = "UPDATE productos SET nombre_producto=?, categoria=?, precio_compra=?, "
                       + "precio_venta=?, stock_actual=?, stock_minimo=?, stock_maximo=?, "
                       + "activo=?, porcentaje_iva=? WHERE codigo_producto=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, p.getNombreProducto());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecioCompra());
            ps.setDouble(4, p.getPrecioVenta());
            ps.setInt(5, p.getStockActual());
            ps.setInt(6, p.getStockMinimo());
            ps.setInt(7, p.getStockMaximo());
            ps.setBoolean(8, p.isActivo());
            ps.setDouble(9, p.getPorcentajeIva());
            ps.setInt(10, p.getCodigoProducto());
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int codigoProducto) {
        cambiarEstado(codigoProducto);
    }

    @Override
    public Producto buscar(int codigoProducto) {
        Conexion conex = new Conexion();
        try {
            String sql = "SELECT * FROM productos WHERE codigo_producto=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setInt(1, codigoProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> listar() {
        Conexion conex = new Conexion();
        List<Producto> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM productos";
            Statement st = conex.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) lista.add(mapear(rs));
            st.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void cambiarEstado(int codigoProducto) {
        Conexion conex = new Conexion();
        try {
            // Primero leemos el estado actual para invertirlo
            Producto p = buscar(codigoProducto);
            if (p == null) return;
            String sql = "UPDATE productos SET activo=? WHERE codigo_producto=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setBoolean(1, !p.isActivo()); // invierte el estado actual
            ps.setInt(2, codigoProducto);
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado producto: " + e.getMessage());
        }
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        Producto p = new Producto(rs.getInt("codigo_producto"));
        p.setNombreProducto(rs.getString("nombre_producto"));
        p.setCategoria(rs.getString("categoria"));
        p.setPrecioCompra(rs.getDouble("precio_compra"));
        p.setPrecioVenta(rs.getDouble("precio_venta"));
        p.setStockActual(rs.getInt("stock_actual"));
        p.setStockMinimo(rs.getInt("stock_minimo"));
        p.setStockMaximo(rs.getInt("stock_maximo"));
        p.setActivo(rs.getBoolean("activo"));
        p.setPorcentajeIva(rs.getDouble("porcentaje_iva"));
        return p;
    }
}