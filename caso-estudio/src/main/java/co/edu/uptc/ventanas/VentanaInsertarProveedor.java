package co.edu.uptc.ventanas;

import java.awt.GridLayout;
import javax.swing.*;
import java.sql.*;

@SuppressWarnings("serial")
public class VentanaInsertarProveedor extends JInternalFrame { 
    private JTextField txtNit, txtNombre, txtDireccion, txtTelefono;
    private JComboBox<String> comboCiudad;
    private JButton btnGuardar;

    public VentanaInsertarProveedor() {
        super("Registrar Proveedor - Escala", true, true, true, true);
        setSize(450, 350); 
        getContentPane().setLayout(new GridLayout(6, 2, 10, 10));

        getContentPane().add(new JLabel("  NIT Proveedor:"));
        getContentPane().add(txtNit = new JTextField());
        getContentPane().add(new JLabel("  Razón Social / Nombre:"));
        getContentPane().add(txtNombre = new JTextField());
        getContentPane().add(new JLabel("  Ciudad:"));
        getContentPane().add(comboCiudad = new JComboBox<>(new String[]{"1 - Bogotá", "2 - Medellín", "3 - Tunja"}));
        getContentPane().add(new JLabel("  Dirección:"));
        getContentPane().add(txtDireccion = new JTextField());
        getContentPane().add(new JLabel("  Teléfono:"));
        getContentPane().add(txtTelefono = new JTextField());

        getContentPane().add(new JLabel(""));
        getContentPane().add(btnGuardar = new JButton("Guardar Proveedor"));

        btnGuardar.addActionListener(e -> guardarProveedor());
    }

    private void guardarProveedor() {
        co.edu.uptc.conexion.Conexion con = new co.edu.uptc.conexion.Conexion();
        try (Connection c = con.getConnection()) {
            String sql = "INSERT INTO proveedores (nit_proveedor, nombre_empresa, telefono, id_ciudad, direccion_detallada) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, txtNit.getText());
            ps.setString(2, txtNombre.getText());
            ps.setString(3, txtTelefono.getText());
            
            String[] partes = comboCiudad.getSelectedItem().toString().split(" - ");
            ps.setInt(4, Integer.parseInt(partes[0]));
            ps.setString(5, txtDireccion.getText());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Proveedor guardado con éxito.");
            txtNit.setText(""); txtNombre.setText(""); txtDireccion.setText(""); txtTelefono.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar proveedor: " + ex.getMessage());
        }
    }
}