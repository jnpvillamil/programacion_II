package co.edu.uptc.dao;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.vo.ProveedorVo;
import java.sql.*;
import javax.swing.JOptionPane;

public class ProveedorDao {

    public void registrarProveedor(ProveedorVo miProveedor) {
        Conexion conexion = new Conexion();
        try {
            Statement estatuto = conexion.getConnection().createStatement();
            String consulta = "INSERT INTO proveedores (nit_proveedor, nombre_empresa, telefono, direccion) VALUES ("
                    + "'" + miProveedor.getNitProveedor() + "', '" + miProveedor.getNombreEmpresa() + "', "
                    + "'" + miProveedor.getTelefono() + "', '" + miProveedor.getDireccion() + "')";
            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Proveedor Registrado", "Información", JOptionPane.INFORMATION_MESSAGE);
            estatuto.close(); 
            conexion.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarProveedor(ProveedorVo miProveedor) {
        Conexion conexion = new Conexion();
        try {
            Statement estatuto = conexion.getConnection().createStatement();
            String consulta = "UPDATE proveedores SET "
                    + "nombre_empresa = '" + miProveedor.getNombreEmpresa() + "', "
                    + "telefono = '" + miProveedor.getTelefono() + "', direccion = '" + miProveedor.getDireccion() + "' "
                    + "WHERE nit_proveedor = '" + miProveedor.getNitProveedor() + "'";
            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Proveedor Actualizado", "Información", JOptionPane.INFORMATION_MESSAGE);
            estatuto.close(); 
            conexion.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Object[][] consultarTodosLosProveedores() {
        Conexion conexion = new Conexion();
        Object[][] datos = new Object[0][4];
        try {
            Statement estatuto = conexion.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "SELECT * FROM proveedores";
            ResultSet rs = estatuto.executeQuery(consulta);
            
            rs.last(); 
            int filas = rs.getRow(); 
            rs.beforeFirst(); 
            
            datos = new Object[filas][4]; 
            int i = 0;
            
           
            while(rs.next()) {
                datos[i][0] = rs.getString("nit_proveedor");
                datos[i][1] = rs.getString("nombre_empresa");
                datos[i][2] = rs.getString("telefono");
                datos[i][3] = rs.getString("direccion");
                i++;
            }
            rs.close(); 
            estatuto.close(); 
            conexion.desconectar();
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
        return datos;
    }
}