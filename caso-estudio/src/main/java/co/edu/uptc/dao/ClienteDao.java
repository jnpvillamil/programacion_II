package co.edu.uptc.dao;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Cliente;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClienteDao {

    public void registrarCliente(Cliente miCliente) {
        Conexion conex = new Conexion();
        try {
            Statement estatuto = conex.getConnection().createStatement();


            String consulta = "INSERT INTO cliente (codigo, nombre, apellido, tipo_documento, numero_documento, "
                    + "telefono, direccion, pais_id, ciudad_id, tipo_cliente, activo, correo_electronico, responsable_tributario) VALUES ('"
                    + miCliente.getCodigo() + "', '"
                    + miCliente.getNombre() + "', '"
                    + miCliente.getApellido() + "', '"
                    + (miCliente.getTipoDocumento() != null ? miCliente.getTipoDocumento().name() : "") + "', '"
                    + miCliente.getNumeroDocumento() + "', '"
                    + miCliente.getTelefono() + "', '"
                    + miCliente.getDireccion() + "', "
                    + miCliente.getPaisId() + ", "
                    + miCliente.getCiudadId() + ", '" 
                    + (miCliente.getTipoCliente() != null ? miCliente.getTipoCliente().name() : "") + "', "
                    + (miCliente.isActivo() ? 1 : 0) + ", '"
                    + miCliente.getCorreoElectronico() + "', '"
                    + (miCliente.getResponsableTributariamente() != null ? miCliente.getResponsableTributariamente().name() : "") + "')";

            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente en la BD", "Información", JOptionPane.INFORMATION_MESSAGE);

            estatuto.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error SQL al registrar: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "No se pudo registrar el cliente en la BD", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarCliente(Cliente miCliente) {
        Conexion conex = new Conexion();
        try {
            Statement estatuto = conex.getConnection().createStatement();

            String consulta = "UPDATE cliente SET "
                    + "nombre = '" + miCliente.getNombre() + "', "
                    + "apellido = '" + miCliente.getApellido() + "', "
                    + "tipo_documento = '" + (miCliente.getTipoDocumento() != null ? miCliente.getTipoDocumento().name() : "") + "', "
                    + "numero_documento = '" + miCliente.getNumeroDocumento() + "', "
                    + "telefono = '" + miCliente.getTelefono() + "', "
                    + "direccion = '" + miCliente.getDireccion() + "', "
                    + "pais_id = " + miCliente.getPaisId() + ", "
                    + "ciudad_id = " + miCliente.getCiudadId() + ", "
                    + "tipo_cliente = '" + (miCliente.getTipoCliente() != null ? miCliente.getTipoCliente().name() : "") + "', "
                    + "activo = " + (miCliente.isActivo() ? 1 : 0) + ", "
                    + "correo_electronico = '" + miCliente.getCorreoElectronico() + "', "
                    + "responsable_tributario = '" + (miCliente.getResponsableTributariamente() != null ? miCliente.getResponsableTributariamente().name() : "") + "' "
                    + "WHERE codigo = '" + miCliente.getCodigo() + "'";

            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente en la BD", "Información", JOptionPane.INFORMATION_MESSAGE);

            estatuto.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el cliente en la BD", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}