package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.GestionCliente;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.Util.ConexionBD;
import co.edu.uptc.enums.TipoDocumentoEnum;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCliente implements GestionCliente {
    
    private ConexionBD conexionBD = new ConexionBD();
    
    @Override
    public void crear(Cliente cliente) {
        String sql = "INSERT INTO clientes (codigo, nombre, tipo_identificacion, numero_identificacion, direccion, telefono, tipo_cliente, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getCodigo());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getTipoIdentificacion().name());
            ps.setString(4, cliente.getNumeroIdentificacion());
            ps.setString(5, cliente.getDireccion());
            ps.setString(6, cliente.getTelefono());
            ps.setString(7, cliente.getTipoCliente());
            ps.setBoolean(8, cliente.isActivo());
            
            ps.executeUpdate();
            System.out.println(" Cliente guardado en BD: " + cliente.getCodigo());
            
        } catch (SQLException e) {
            System.err.println(" Error al guardar cliente: " + e.getMessage());
        }
    }
    
    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, tipo_identificacion=?, numero_identificacion=?, direccion=?, telefono=?, tipo_cliente=?, activo=? WHERE codigo=?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTipoIdentificacion().name());
            ps.setString(3, cliente.getNumeroIdentificacion());
            ps.setString(4, cliente.getDireccion());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getTipoCliente());
            ps.setBoolean(7, cliente.isActivo());
            ps.setString(8, cliente.getCodigo());
            
            ps.executeUpdate();
            System.out.println(" Cliente actualizado: " + cliente.getCodigo());
            
        } catch (SQLException e) {
            System.err.println(" Error al actualizar cliente: " + e.getMessage());
        }
    }
    
    @Override
    public void eliminar(String codigo) {
        String sql = "UPDATE clientes SET activo = 0 WHERE codigo = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            ps.executeUpdate();
            System.out.println(" Cliente inactivado: " + codigo);
            
        } catch (SQLException e) {
            System.err.println(" Error al inactivar cliente: " + e.getMessage());
        }
    }
    
    @Override
    public Cliente buscar(String codigo) {
        String sql = "SELECT * FROM clientes WHERE codigo = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                TipoDocumentoEnum tipoId = TipoDocumentoEnum.fromString(rs.getString("tipo_identificacion"));
                
                Cliente cliente = new Cliente(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    tipoId,
                    rs.getString("numero_identificacion"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("tipo_cliente")
                );
                cliente.setActivo(rs.getBoolean("activo"));
                return cliente;
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                TipoDocumentoEnum tipoId = TipoDocumentoEnum.fromString(rs.getString("tipo_identificacion"));
                
                Cliente cliente = new Cliente(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    tipoId,
                    rs.getString("numero_identificacion"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("tipo_cliente")
                );
                cliente.setActivo(rs.getBoolean("activo"));
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar clientes: " + e.getMessage());
        }
        return clientes;
    }
    
    @Override
    public List<Cliente> listarActivos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE activo = 1";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                TipoDocumentoEnum tipoId = TipoDocumentoEnum.fromString(rs.getString("tipo_identificacion"));
                
                Cliente cliente = new Cliente(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    tipoId,
                    rs.getString("numero_identificacion"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("tipo_cliente")
                );
                cliente.setActivo(true);
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar clientes activos: " + e.getMessage());
        }
        return clientes;
    }
    
    @Override
    public boolean existe(String codigo) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE codigo = ?";
        
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
}