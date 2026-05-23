package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.interfaces.IPersistenciaProveedor;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.utilidades.ConexionBD;

public class PersistenciaProveedor implements IPersistenciaProveedor {

    public boolean guardar(Proveedor objeto) {
        String sql = "INSERT INTO proveedores (codigo_proveedor, razon_social, nit, direccion, telefono, email, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, objeto.getCodigoProveedor());
            ps.setString(2, objeto.getNombre()); 
            ps.setString(3, objeto.getIdentificacion()); 
            ps.setString(4, objeto.getDireccion()); 
            ps.setString(5, objeto.getTelefono()); 
            ps.setString(6, objeto.getCorreoElectronico());
            ps.setBoolean(7, objeto.isActivo());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al guardar proveedor: " + e.getMessage());
            return false;
        }
    }

    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores WHERE estado = true"; 
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setCodigoProveedor(rs.getString("codigo_proveedor"));
                p.setNombre(rs.getString("razon_social")); // Heredado de Persona
                p.setIdentificacion(rs.getString("nit")); // Heredado de Persona
                p.setDireccion(rs.getString("direccion")); // Heredado de Persona
                p.setTelefono(rs.getString("telefono")); // Heredado de Persona
                p.setCorreoElectronico(rs.getString("email"));
                p.setActivo(rs.getBoolean("estado"));
                
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Proveedor objeto) {
        String sql = "UPDATE proveedores SET razon_social = ?, nit = ?, direccion = ?, telefono = ?, email = ?, estado = ? WHERE codigo_proveedor = ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, objeto.getNombre());
            ps.setString(2, objeto.getIdentificacion());
            ps.setString(3, objeto.getDireccion());
            ps.setString(4, objeto.getTelefono());
            ps.setString(5, objeto.getCorreoElectronico());
            ps.setBoolean(6, objeto.isActivo());
            ps.setString(7, objeto.getCodigoProveedor()); // El WHERE va al final
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String id) {
        String sql = "UPDATE proveedores SET estado = false WHERE codigo_proveedor = ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar (inactivar) proveedor: " + e.getMessage());
            return false;
        }
    }

    public Proveedor buscarPorId(String id) {
        Proveedor p = null;
        String sql = "SELECT * FROM proveedores WHERE codigo_proveedor = ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Proveedor();
                    p.setCodigoProveedor(rs.getString("codigo_proveedor"));
                    p.setNombre(rs.getString("razon_social"));
                    p.setIdentificacion(rs.getString("nit"));
                    p.setDireccion(rs.getString("direccion"));
                    p.setTelefono(rs.getString("telefono"));
                    p.setCorreoElectronico(rs.getString("email"));
                    p.setActivo(rs.getBoolean("estado"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar proveedor: " + e.getMessage());
        }
        return p;
    }
}