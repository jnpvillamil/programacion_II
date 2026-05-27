package co.edu.uptc.dao;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Producto;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProductoDao {

    public void registrarProducto(Producto prod) {
        Conexion conex = new Conexion();

        String consulta = "INSERT INTO producto (codigo, nombre, precio_venta, stock, nit_proveedor) VALUES (?, ?, ?, ?, ?)";
        
        try (java.sql.Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(consulta)) {
            
            pst.setString(1, prod.getCodigo());
            pst.setString(2, prod.getNombre());
            pst.setDouble(3, prod.getPrecio()); 
            pst.setInt(4, prod.getCantidadInventario()); 
            pst.setString(5, "800123456-1"); 

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto e Inventario guardados exitosamente en la BD", "Información", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            System.out.println("Error SQL al registrar producto: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al guardar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            conex.desconectar();
        }
    }

    public void actualizarProducto(Producto prod) {
        Conexion conex = new Conexion();
 
        String consulta = "UPDATE producto SET nombre = ?, precio_venta = ?, stock = ? WHERE codigo = ?";
        
        try (java.sql.Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(consulta)) {
            
            pst.setString(1, prod.getNombre());
            pst.setDouble(2, prod.getPrecio());
            pst.setInt(3, prod.getCantidadInventario());
            pst.setString(4, prod.getCodigo());

            int filasAfectadas = pst.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Inventario y Producto actualizados correctamente en MySQL", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún producto con el código especificado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar producto: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al modificar inventario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            conex.desconectar();
        }
    }
}