package co.edu.uptc.dao;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Vendedor;
import co.edu.uptc.gui.modelo.Venta;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class VendedorDao {

    public void registrarVentaBD(Venta v) {
        Conexion conex = new Conexion();
        try {
            Statement estatuto = conex.getConnection().createStatement();
            
            String consulta = "INSERT INTO factura_venta (numero_factura, codigo_cliente, codigo_producto, cantidad, total_venta) VALUES ('"
                    + v.getCodigoFactura() + "', '"
                    + v.getCodigoCliente() + "', '"
                    + v.getCodigoProducto() + "', "
                    + v.getCantidad() + ", "
                    + v.getTotal() + ")";

            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "¡Venta guardada y auditada en la Base de Datos!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            estatuto.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error SQL al fichar venta: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al guardar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void registrarVendedor(Vendedor v) {
        Conexion conex = new Conexion();
        try {
            Statement estatuto = conex.getConnection().createStatement();
            String consulta = "INSERT INTO vendedor (codigo, nombre, apellido, tipo_documento, numero_documento, telefono, direccion, sueldo_base, porcentaje_comision) VALUES ('"
                    + v.getCodigo() + "', '" + v.getNombre() + "', '" + v.getApellido() + "', '"
                    + (v.getTipoDocumento() != null ? v.getTipoDocumento().name() : "") + "', '"
                    + v.getNumeroDocumento() + "', '" + v.getTelefono() + "', '" + v.getDireccion() + "', "
                    + v.getSueldoBase() + ", " + v.getPorcentajeComision() + ")";

            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Vendedor registrado con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
            estatuto.close();
            conex.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarVendedor(Vendedor v) {
        Conexion conex = new Conexion();
        try {
            Statement estatuto = conex.getConnection().createStatement();
            String consulta = "UPDATE vendedor SET "
                    + "nombre = '" + v.getNombre() + "', "
                    + "apellido = '" + v.getApellido() + "', "
                    + "tipo_documento = '" + (v.getTipoDocumento() != null ? v.getTipoDocumento().name() : "") + "', "
                    + "numero_documento = '" + v.getNumeroDocumento() + "', "
                    + "telefono = '" + v.getTelefono() + "', "
                    + "direccion = '" + v.getDireccion() + "', "
                    + "sueldo_base = " + v.getSueldoBase() + ", "
                    + "porcentaje_comision = " + v.getPorcentajeComision() + " "
                    + "WHERE codigo = '" + v.getCodigo() + "'";

            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Datos actualizados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            estatuto.close();
            conex.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}