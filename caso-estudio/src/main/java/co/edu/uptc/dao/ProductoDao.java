package co.edu.uptc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.gui.modelo.Producto;
import co.edu.uptc.conexion.Conexion;

public class ProductoDao {

    public List<Producto> listarProductos() throws Exception {
        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT codigo, nombre, precio_compra, precio_venta, stock, stock_minimo FROM producto ORDER BY codigo ASC";
        
        Conexion conex = new Conexion();
        
        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta"),
                    rs.getInt("stock"),
                    rs.getInt("stock_minimo")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error interno en ProductoDao.listarProductos: " + e.getMessage());
            throw e; // Propagamos el error para diagnosticarlo si la conexión falla
        }
        
        return lista; // Retorna la lista (vacía o con datos), jamás null
    }

    public void eliminarProducto(String codigo) throws Exception {
        String sql = "DELETE FROM producto WHERE codigo = ?";
        Conexion conex = new Conexion();
        try (Connection c = conex.getConnection(); 
             PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setString(1, codigo);
            pst.executeUpdate();
        }
    }

    public void registrarProducto(Producto p) throws Exception {
        String sql = "INSERT INTO producto (codigo, nombre, precio_compra, precio_venta, stock, stock_minimo) VALUES (?, ?, ?, ?, ?, ?)";
        Conexion conex = new Conexion();
        try (Connection c = conex.getConnection(); 
             PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setString(1, p.getCodigo());
            pst.setString(2, p.getNombre());
            pst.setDouble(3, p.getPrecioCompra());
            pst.setDouble(4, p.getPrecioVenta());
            pst.setInt(5, p.getStock());
            pst.setInt(6, p.getStockMinimo());
            pst.executeUpdate();
        }
    }

    public void actualizarProducto(Producto p) throws Exception {
        String sql = "UPDATE producto SET nombre=?, precio_compra=?, precio_venta=?, stock=?, stock_minimo=? WHERE codigo=?";
        Conexion conex = new Conexion();
        try (Connection c = conex.getConnection(); 
             PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setString(1, p.getNombre());
            pst.setDouble(2, p.getPrecioCompra());
            pst.setDouble(3, p.getPrecioVenta());
            pst.setInt(4, p.getStock());
            pst.setInt(5, p.getStockMinimo());
            pst.setString(6, p.getCodigo());
            pst.executeUpdate();
        }
    }
}