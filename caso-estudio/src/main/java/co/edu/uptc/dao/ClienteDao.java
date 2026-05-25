package co.edu.uptc.dao;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.vo.ClienteVo;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ClienteDao {
    
    public void registrarCliente(ClienteVo miCliente) {
        Conexion conexion = new Conexion();
        try {
            Connection conn = conexion.getConnection();
            if (conn != null) {
                Statement estatuto = conn.createStatement();
                String consulta = "INSERT INTO clientes (codigo, nombre, apellido, tipo_documento, numero_documento, telefono, id_ciudad, direccion_detallada, correo, tipo_cliente, activo) VALUES ("
                        + "'" + miCliente.getCodigo() + "', "
                        + "'" + miCliente.getNombre() + "', "
                        + "'" + miCliente.getApellido() + "', "
                        + "'" + miCliente.getTipoDocumento() + "', "
                        + "'" + miCliente.getNumeroDocumento() + "', "
                        + "'" + miCliente.getTelefono() + "', "
                        + "" + miCliente.getIdCiudad() + ", "
                        + "'" + miCliente.getDireccionDetallada() + "', "
                        + "'" + miCliente.getCorreo() + "', "
                        + "'" + miCliente.getTipoCliente() + "', " 
                        + miCliente.isActivo() + ")";
                
                estatuto.executeUpdate(consulta);
                JOptionPane.showMessageDialog(null, "Cliente Registrado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                estatuto.close();
            } else {
                JOptionPane.showMessageDialog(null, "Error de conexión con el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            conexion.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la BD al registrar: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarCliente(ClienteVo miCliente) {
        Conexion conexion = new Conexion();
        try {
            Connection conn = conexion.getConnection();
            if (conn != null) {
                Statement estatuto = conn.createStatement();
                String consulta = "UPDATE clientes SET "
                        + "nombre = '" + miCliente.getNombre() + "', "
                        + "apellido = '" + miCliente.getApellido() + "', "
                        + "tipo_documento = '" + miCliente.getTipoDocumento() + "', "
                        + "numero_documento = '" + miCliente.getNumeroDocumento() + "', "
                        + "telefono = '" + miCliente.getTelefono() + "', "
                        + "id_ciudad = " + miCliente.getIdCiudad() + ", "
                        + "direccion_detallada = '" + miCliente.getDireccionDetallada() + "', "
                        + "correo = '" + miCliente.getCorreo() + "', "
                        + "tipo_cliente = '" + miCliente.getTipoCliente() + "', "
                        + "activo = " + miCliente.isActivo() + " "
                        + "WHERE codigo = '" + miCliente.getCodigo() + "'";
                
                estatuto.executeUpdate(consulta);
                JOptionPane.showMessageDialog(null, "Cliente Actualizado exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                estatuto.close();
            }
            conexion.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la BD al actualizar: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<ClienteVo> consultarTodosLosClientes() {
        ArrayList<ClienteVo> listaClientes = new ArrayList<>();
        Conexion conexion = new Conexion();
        try {
            Connection conn = conexion.getConnection();
            if (conn != null) {
                Statement estatuto = conn.createStatement();
                String consulta = "SELECT codigo, nombre, apellido, tipo_documento, numero_documento, telefono, id_ciudad, direccion_detallada, correo, tipo_cliente, activo FROM clientes";
                ResultSet rs = estatuto.executeQuery(consulta);
                
                while (rs.next()) {
                    ClienteVo cliente = new ClienteVo();
                    cliente.setCodigo(rs.getString("codigo"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setTipoDocumento(rs.getString("tipo_documento"));
                    cliente.setNumeroDocumento(rs.getString("numero_documento"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setIdCiudad(rs.getInt("id_ciudad"));
                    cliente.setDireccionDetallada(rs.getString("direccion_detallada"));
                    cliente.setCorreo(rs.getString("correo"));
                    cliente.setTipoCliente(rs.getString("tipo_cliente"));
                    cliente.setActivo(rs.getBoolean("activo"));
                    
                    listaClientes.add(cliente);
                }
                rs.close();
                estatuto.close();
            }
            conexion.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la BD al consultar: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
        return listaClientes;
    }
}