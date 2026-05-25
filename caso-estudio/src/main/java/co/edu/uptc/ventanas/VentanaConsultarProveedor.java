package co.edu.uptc.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

@SuppressWarnings("serial")
public class VentanaConsultarProveedor extends JInternalFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnModificar, btnRefrescar;

    public VentanaConsultarProveedor() {
        super("Listado General de Proveedores", true, true, true, true);
        setSize(850, 450);

        modelo = new DefaultTableModel(null, new String[]{"NIT", "Nombre Empresa", "Teléfono", "ID Ciudad", "Dirección"});
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
        try (Connection c = con.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM proveedores")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("nit_proveedor"), rs.getString("nombre_empresa"),
                    rs.getString("telefono"), rs.getInt("id_ciudad"), rs.getString("direccion_detallada")
                });
            }
        } catch (Exception ex) {
            System.out.println("Error al cargar proveedores: " + ex.getMessage());
        }
    }

    private void ejecutarModificacion() {
        if (tabla.isEditing()) {
            tabla.getCellEditor().stopCellEditing();
        }

        int filaSel = tabla.getSelectedRow();
        if (filaSel == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor de la tabla primero.");
            return;
        }

        co.edu.uptc.conexion.Conexion con = new co.edu.uptc.conexion.Conexion();
        try (Connection c = con.getConnection()) {
            String sql = "UPDATE proveedores SET nombre_empresa=?, telefono=?, id_ciudad=?, direccion_detallada=? WHERE nit_proveedor=?";
            PreparedStatement ps = c.prepareStatement(sql);
            
            ps.setString(1, modelo.getValueAt(filaSel, 1).toString().trim());
            ps.setString(2, modelo.getValueAt(filaSel, 2).toString().trim());
            ps.setInt(3, Integer.parseInt(modelo.getValueAt(filaSel, 3).toString().trim()));
            ps.setString(4, modelo.getValueAt(filaSel, 4).toString().trim());
            ps.setString(5, modelo.getValueAt(filaSel, 0).toString().trim()); 

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "¡Proveedor actualizado en la Base de Datos!");
            cargarDatos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar proveedor: Verifique que el ID Ciudad sea un número.");
        }
    }
}