package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.IGestionProveedor;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.Util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProveedor implements IGestionProveedor {
    
    private ConexionBD conexionBD = new ConexionBD();
    
    @Override
    public void crear(Proveedor proveedor) {
        String sql = "INSERT INTO proveedores (codigo, razon_social, nit, direccion, telefono, email, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, proveedor.getCodigo());
            ps.setString(2, proveedor.getRazonSocial());
            ps.setString(3, proveedor.getNit());
            ps.setString(4, proveedor.getDireccion());
            ps.setString(5, proveedor.getTelefono());
            ps.setString(6, proveedor.getCorreo());
            ps.setBoolean(7, proveedor.isActivo());
            
            ps.executeUpdate();
            System.out.println(" Proveedor guardado en BD: " + proveedor.getCodigo());
            
        } catch (SQLException e) {
            System.err.println(" Error al guardar proveedor: " + e.getMessage());
        }
    }
    
    @Override
    public void actualizar(Proveedor proveedor) {
        String sql = "UPDATE proveedores SET razon_social=?, nit=?, direccion=?, telefono=?, email=?, activo=? WHERE codigo=?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, proveedor.getRazonSocial());
            ps.setString(2, proveedor.getNit());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getCorreo());
            ps.setBoolean(6, proveedor.isActivo());
            ps.setString(7, proveedor.getCodigo());
            
            ps.executeUpdate();
            System.out.println("✅ Proveedor actualizado: " + proveedor.getCodigo());
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar proveedor: " + e.getMessage());
        }
    }
    
    @Override
    public void eliminar(String codigo) {
        String sql = "UPDATE proveedores SET activo = 0 WHERE codigo = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            ps.executeUpdate();
            System.out.println(" Proveedor inactivado: " + codigo);
            
        } catch (SQLException e) {
            System.err.println(" Error al inactivar proveedor: " + e.getMessage());
        }
    }
    
    @Override
    public Proveedor buscar(String codigo) {
        String sql = "SELECT * FROM proveedores WHERE codigo = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Proveedor proveedor = new Proveedor(
                    rs.getString("codigo"),
                    rs.getString("razon_social"),
                    rs.getString("nit"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                proveedor.setActivo(rs.getBoolean("activo"));
                return proveedor;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al buscar proveedor: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public Proveedor buscarPorNit(String nit) {
        String sql = "SELECT * FROM proveedores WHERE nit = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nit);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Proveedor proveedor = new Proveedor(
                    rs.getString("codigo"),
                    rs.getString("razon_social"),
                    rs.getString("nit"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                proveedor.setActivo(rs.getBoolean("activo"));
                return proveedor;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al buscar por NIT: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<Proveedor> listar() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                    rs.getString("codigo"),
                    rs.getString("razon_social"),
                    rs.getString("nit"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                proveedor.setActivo(rs.getBoolean("activo"));
                proveedores.add(proveedor);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar proveedores: " + e.getMessage());
        }
        return proveedores;
    }
    
    @Override
    public List<Proveedor> listarActivos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores WHERE activo = 1";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                    rs.getString("codigo"),
                    rs.getString("razon_social"),
                    rs.getString("nit"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                proveedor.setActivo(true);
                proveedores.add(proveedor);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar proveedores activos: " + e.getMessage());
        }
        return proveedores;
    }
    
    @Override
    public boolean existe(String codigo) {
        String sql = "SELECT COUNT(*) FROM proveedores WHERE codigo = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al verificar existencia: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean existeNit(String nit) {
        String sql = "SELECT COUNT(*) FROM proveedores WHERE nit = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nit);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al verificar NIT: " + e.getMessage());
        }
        return false;
    }
}