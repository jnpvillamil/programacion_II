package co.edu.uptc.ventanas;

import java.awt.GridLayout;
import javax.swing.*;
import java.sql.*;

@SuppressWarnings("serial")
public class VentanaActualizarProducto extends JInternalFrame { 
    
    private JTextField txtIdProducto, txtNombreProd, txtPrecio, txtStock;
    private JButton btnGuardar;

    public VentanaActualizarProducto() {
        super("Gestión de Productos (Insertar / Actualizar)", true, true, true, true);
        setSize(450, 350);
        getContentPane().setLayout(new GridLayout(5, 2, 10, 10));

        getContentPane().add(new JLabel("  ID / Código Producto:"));
        getContentPane().add(txtIdProducto = new JTextField());

        getContentPane().add(new JLabel("  Nombre del Producto:"));
        getContentPane().add(txtNombreProd = new JTextField());

        getContentPane().add(new JLabel("  Precio de Venta:"));
        getContentPane().add(txtPrecio = new JTextField());

        getContentPane().add(new JLabel("  Stock / Cantidad:"));
        getContentPane().add(txtStock = new JTextField());

        getContentPane().add(new JLabel(""));
        getContentPane().add(btnGuardar = new JButton("Guardar Producto"));

        btnGuardar.addActionListener(e -> guardarProducto());
    }

    private void guardarProducto() {
        co.edu.uptc.conexion.Conexion con = new co.edu.uptc.conexion.Conexion();
        try (Connection c = con.getConnection()) {
            String sql = "INSERT INTO productos (id_producto, nombre, precio, stock) VALUES (?, ?, ?, ?) "
                       + "ON DUPLICATE KEY UPDATE nombre=?, precio=?, stock=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, txtIdProducto.getText());
            ps.setString(2, txtNombreProd.getText());
            ps.setDouble(3, Double.parseDouble(txtPrecio.getText()));
            ps.setInt(4, Integer.parseInt(txtStock.getText()));
            
            ps.setString(5, txtNombreProd.getText());
            ps.setDouble(6, Double.parseDouble(txtPrecio.getText()));
            ps.setInt(7, Integer.parseInt(txtStock.getText()));
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Producto guardado con éxito.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}