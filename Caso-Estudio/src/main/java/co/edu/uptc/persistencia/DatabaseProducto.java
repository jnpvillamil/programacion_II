package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.GestionProducto;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.Util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProducto implements GestionProducto {
    
    private ConexionBD conexionBD = new ConexionBD();
    
    @Override
    public void crear(Producto p) {
        String sql = "INSERT INTO productos (codigo, nombre, categoria, precio_compra, precio_venta, stock_actual, stock_minimo, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getCategoria());
            ps.setDouble(4, p.getPrecioCompra());
            ps.setDouble(5, p.getPrecioVenta());
            ps.setInt(6, p.getStockActual());
            ps.setInt(7, p.getStockMinimo());
            ps.setBoolean(8, p.isActivo());
            ps.executeUpdate();
            System.out.println(" Producto guardado en BD: " + p.getCodigo());
            
        } catch (SQLException e) {
            System.err.println(" Error al guardar: " + e.getMessage());
        }
    }
    
    @Override
    public Producto buscar(String codigo) {
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Producto p = new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta"),
                    rs.getInt("stock_actual"),
                    rs.getInt("stock_minimo")
                );
                p.setActivo(rs.getBoolean("activo"));
                return p;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al buscar: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public void actualizar(Producto p) {
        String sql = "UPDATE productos SET nombre=?, categoria=?, precio_compra=?, precio_venta=?, stock_actual=?, stock_minimo=?, activo=? WHERE codigo=?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecioCompra());
            ps.setDouble(4, p.getPrecioVenta());
            ps.setInt(5, p.getStockActual());
            ps.setInt(6, p.getStockMinimo());
            ps.setBoolean(7, p.isActivo());
            ps.setString(8, p.getCodigo());
            ps.executeUpdate();
            System.out.println(" Producto actualizado: " + p.getCodigo());
            
        } catch (SQLException e) {
            System.err.println(" Error al actualizar: " + e.getMessage());
        }
    }
    
    @Override
    public void eliminar(String codigo) {
        String sql = "UPDATE productos SET activo = 0 WHERE codigo = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            ps.executeUpdate();
            System.out.println(" Producto inactivado: " + codigo);
            
        } catch (SQLException e) {
            System.err.println(" Error al inactivar: " + e.getMessage());
        }
    }
    
    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta"),
                    rs.getInt("stock_actual"),
                    rs.getInt("stock_minimo")
                );
                p.setActivo(rs.getBoolean("activo"));
                productos.add(p);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar: " + e.getMessage());
        }
        return productos;
    }
    
    @Override
    public List<Producto> listarActivos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE activo = 1";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta"),
                    rs.getInt("stock_actual"),
                    rs.getInt("stock_minimo")
                );
                p.setActivo(true);
                productos.add(p);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar activos: " + e.getMessage());
        }
        return productos;
    }
    
    @Override
    public List<Producto> listarPorStockMinimo() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE activo = 1 AND stock_actual < stock_minimo";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio_compra"),
                    rs.getDouble("precio_venta"),
                    rs.getInt("stock_actual"),
                    rs.getInt("stock_minimo")
                );
                p.setActivo(true);
                productos.add(p);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar stock bajo: " + e.getMessage());
        }
        return productos;
    }
    
    @Override
    public boolean existe(String codigo) {
        String sql = "SELECT COUNT(*) FROM productos WHERE codigo = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia: " + e.getMessage());
        }
        return false;
    }
}
