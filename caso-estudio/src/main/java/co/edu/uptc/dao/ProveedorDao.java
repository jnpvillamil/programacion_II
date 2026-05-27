package co.edu.uptc.dao;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Proveedor;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProveedorDao {

    public void registrarProveedor(Proveedor prov) {
        Conexion conex = new Conexion();
        try {
            Statement estatuto = conex.getConnection().createStatement();
            String consulta = "INSERT INTO proveedor (nit, razon_social, telefono, direccion, producto) VALUES ('"
                    + prov.getNit() + "', '"
                    + prov.getRazonSocial() + "', '"
                    + prov.getTelefono() + "', '"
                    + prov.getDireccion() + "', '"
                    + prov.getProductoSuministrado() + "')";

            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Proveedor registrado exitosamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            estatuto.close();
            conex.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarProveedor(Proveedor prov) {
        Conexion conex = new Conexion();
        try {
            Statement estatuto = conex.getConnection().createStatement();
            String consulta = "UPDATE proveedor SET "
                    + "razon_social = '" + prov.getRazonSocial() + "', "
                    + "telefono = '" + prov.getTelefono() + "', "
                    + "direccion = '" + prov.getDireccion() + "', "
                    + "producto = '" + prov.getProductoSuministrado() + "' "
                    + "WHERE nit = '" + prov.getNit() + "'";

            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Proveedor actualizado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            estatuto.close();
            conex.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}