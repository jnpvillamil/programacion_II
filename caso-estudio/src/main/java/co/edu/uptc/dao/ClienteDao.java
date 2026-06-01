package co.edu.uptc.dao;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDao {

    public void registrarCliente(Cliente miCliente) throws SQLException {
        Conexion conex = new Conexion();
        String sql = "INSERT INTO cliente (codigo, nombre, apellido, tipo_documento, numero_documento, "
                   + "telefono, direccion, pais_id, ciudad_id, tipo_cliente, activo, correo_electronico, "
                   + "responsable_tributario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {
            
            pst.setString(1, miCliente.getCodigo());
            pst.setString(2, miCliente.getNombre());
            pst.setString(3, miCliente.getApellido());
            pst.setString(4, miCliente.getTipoDocumento() != null ? miCliente.getTipoDocumento().name() : "");
            pst.setString(5, miCliente.getNumeroDocumento());
            pst.setString(6, miCliente.getTelefono());
            pst.setString(7, miCliente.getDireccion());
            pst.setInt(8, miCliente.getPaisId());
            pst.setInt(9, miCliente.getCiudadId());
            pst.setString(10, miCliente.getTipoCliente() != null ? miCliente.getTipoCliente().name() : "");
            pst.setInt(11, miCliente.isActivo() ? 1 : 0);
            pst.setString(12, miCliente.getCorreoElectronico());
            pst.setString(13, miCliente.getResponsableTributariamente() != null ? miCliente.getResponsableTributariamente().name() : "");
            
            pst.executeUpdate();
        } finally {
            conex.desconectar();
        }
    }

    public void actualizarCliente(Cliente miCliente) throws SQLException {
        Conexion conex = new Conexion();
        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, tipo_documento = ?, numero_documento = ?, "
                   + "telefono = ?, direccion = ?, pais_id = ?, ciudad_id = ?, tipo_cliente = ?, activo = ?, "
                   + "correo_electronico = ?, responsable_tributario = ? WHERE codigo = ?";
        
        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {
            
            pst.setString(1, miCliente.getNombre());
            pst.setString(2, miCliente.getApellido());
            pst.setString(3, miCliente.getTipoDocumento() != null ? miCliente.getTipoDocumento().name() : "");
            pst.setString(4, miCliente.getNumeroDocumento());
            pst.setString(5, miCliente.getTelefono());
            pst.setString(6, miCliente.getDireccion());
            pst.setInt(7, miCliente.getPaisId());
            pst.setInt(8, miCliente.getCiudadId());
            pst.setString(9, miCliente.getTipoCliente() != null ? miCliente.getTipoCliente().name() : "");
            pst.setInt(10, miCliente.isActivo() ? 1 : 0);
            pst.setString(11, miCliente.getCorreoElectronico());
            pst.setString(12, miCliente.getResponsableTributariamente() != null ? miCliente.getResponsableTributariamente().name() : "");
            pst.setString(13, miCliente.getCodigo()); 
            
            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró ningún cliente registrado con el código: " + miCliente.getCodigo());
            }
        } finally {
            conex.desconectar();
        }
    }


    public void eliminarCliente(String codigo) throws SQLException {
        Conexion conex = new Conexion();
        String sql = "DELETE FROM cliente WHERE codigo = ?";
        
        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {
            
            pst.setString(1, codigo);
            
            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo eliminar. No existe un cliente con el código especificado.");
            }
        } finally {
            conex.desconectar();
        }
    }
}