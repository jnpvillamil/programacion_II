package co.edu.uptc.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

@SuppressWarnings("serial")
public class VentanaConsultarProducto extends JInternalFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnModificar, btnRefrescar;

    public VentanaConsultarProducto() {
        super("Inventario General de Productos", true, true, true, true);
        setSize(850, 450);

        modelo = new DefaultTableModel(null, new String[]{"ID Producto", "Nombre Producto", "Precio", "Stock Actual"});
        tabla = new JTable(modelo);
        getContentPane().add(new JScrollPane(tabla), BorderLayout.CENTER);


        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRefrescar = new JButton("Refrescar Tabla");
        btnModificar = new JButton("Modificar Seleccionado");
        
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnModificar);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        btnModificar.addActionListener(e -> ejecutarModificacion());
        btnRefrescar.addActionListener(e -> cargarDatos());

        cargarDatos();
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        co.edu.uptc.conexion.Conexion con = new co.edu.uptc.conexion.Conexion();
        try (Connection c = con.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM productos")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("id_producto"), rs.getString("nombre"),
                    rs.getDouble("precio"), rs.getInt("stock")
                });
            }
        } catch (Exception ex) {
            System.out.println("Error al cargar productos: " + ex.getMessage());
        }
    }

    private void ejecutarModificacion() {
        if (tabla.isEditing()) {
            tabla.getCellEditor().stopCellEditing();
        }

        int filaSel = tabla.getSelectedRow();
        if (filaSel == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto de la tabla primero.");
            return;
        }

        co.edu.uptc.conexion.Conexion con = new co.edu.uptc.conexion.Conexion();
        try (Connection c = con.getConnection()) {
            String sql = "UPDATE productos SET nombre=?, precio=?, stock=? WHERE id_producto=?";
            PreparedStatement ps = c.prepareStatement(sql);
            
            ps.setString(1, modelo.getValueAt(filaSel, 1).toString().trim());
            ps.setDouble(2, Double.parseDouble(modelo.getValueAt(filaSel, 2).toString().trim()));
            ps.setInt(3, Integer.parseInt(modelo.getValueAt(filaSel, 3).toString().trim()));
            ps.setString(4, modelo.getValueAt(filaSel, 0).toString().trim());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "¡Producto actualizado en el Inventario!");
            cargarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar producto: Revise que Precio y Stock sean números válidos.");
        }
    }
}