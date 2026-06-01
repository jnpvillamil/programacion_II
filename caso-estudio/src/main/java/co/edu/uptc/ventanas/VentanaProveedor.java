package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import co.edu.uptc.gui.modelo.Proveedor;
import co.edu.uptc.gui.negocio.GestionProveedor;

@SuppressWarnings("serial")
public class VentanaProveedor extends JFrame implements ActionListener {
    
    private JTextField campoNit, campoRazonSocial, campoTelefono, campoDireccion, campoCorreo, campoProducto;
    private JButton botonGuardar, botonActualizar, botonEliminar, botonLimpiar;
    private JTable tablaProveedores;
    private DefaultTableModel modeloTabla;
    private GestionProveedor gestionProveedor;

    public VentanaProveedor() {
        setTitle("Módulo Maestro de Proveedores");
        setSize(1020, 680); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(17, 17));

        gestionProveedor = new GestionProveedor();

        iniciarComponentesFormulario();
        iniciarComponenteTabla();
        cargarDatosTabla();
    }

    private void iniciarComponentesFormulario() {
 
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 10, 10));

        javax.swing.border.Border bordeLineaGrueso = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        panelForm.setBorder(BorderFactory.createTitledBorder(bordeLineaGrueso, " Datos de la Empresa / Proveedor "));

        panelForm.add(new JLabel("NIT (*clave):", SwingConstants.RIGHT));
        campoNit = new JTextField();
        panelForm.add(campoNit);

        panelForm.add(new JLabel("Razón Social:", SwingConstants.RIGHT));
        campoRazonSocial = new JTextField();
        panelForm.add(campoRazonSocial);

        panelForm.add(new JLabel("Teléfono:", SwingConstants.RIGHT));
        campoTelefono = new JTextField();
        panelForm.add(campoTelefono);

        panelForm.add(new JLabel("Dirección:", SwingConstants.RIGHT));
        campoDireccion = new JTextField();
        panelForm.add(campoDireccion);

        panelForm.add(new JLabel("Correo Electrónico:", SwingConstants.RIGHT));
        campoCorreo = new JTextField();
        panelForm.add(campoCorreo);

        panelForm.add(new JLabel("Producto Suministrado:", SwingConstants.RIGHT));
        campoProducto = new JTextField();
        panelForm.add(campoProducto);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botonGuardar = new JButton("Guardar Nuevo");
        botonActualizar = new JButton("Guardar Modificación"); 
        botonEliminar = new JButton("Eliminar Seleccionado"); 
        botonLimpiar = new JButton("Limpiar Campos");

        botonGuardar.addActionListener(this);
        botonActualizar.addActionListener(this);
        botonEliminar.addActionListener(this);
        botonLimpiar.addActionListener(this);

        panelAcciones.add(botonGuardar); 
        panelAcciones.add(botonActualizar);
        panelAcciones.add(botonEliminar); 
        panelAcciones.add(botonLimpiar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout(5, 5));
        contenedorSuperior.add(panelForm, BorderLayout.CENTER);
        contenedorSuperior.add(panelAcciones, BorderLayout.SOUTH);

        contenedorSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        add(contenedorSuperior, BorderLayout.NORTH);
    }

    private void iniciarComponenteTabla() {

        String[] columnas = {"NIT", "Razón Social", "Teléfono", "Dirección", "Correo Electrónico", "Producto Suministrado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaProveedores = new JTable(modeloTabla); 
        tablaProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProveedores.setRowHeight(22);
        
        tablaProveedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaProveedores.getSelectedRow();
                if (fila >= 0) {
                    campoNit.setText(modeloTabla.getValueAt(fila, 0) != null ? modeloTabla.getValueAt(fila, 0).toString() : "");
                    campoRazonSocial.setText(modeloTabla.getValueAt(fila, 1) != null ? modeloTabla.getValueAt(fila, 1).toString() : "");
                    campoTelefono.setText(modeloTabla.getValueAt(fila, 2) != null ? modeloTabla.getValueAt(fila, 2).toString() : "");
                    campoDireccion.setText(modeloTabla.getValueAt(fila, 3) != null ? modeloTabla.getValueAt(fila, 3).toString() : "");
                    campoCorreo.setText(modeloTabla.getValueAt(fila, 4) != null ? modeloTabla.getValueAt(fila, 4).toString() : "");
                    campoProducto.setText(modeloTabla.getValueAt(fila, 5) != null ? modeloTabla.getValueAt(fila, 5).toString() : "");
                    campoNit.setEditable(false); 
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaProveedores);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(" Proveedores Registrados "));
 
        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        contenedorTabla.add(scrollTabla, BorderLayout.CENTER);

        add(contenedorTabla, BorderLayout.CENTER);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); 
        try {
            List<Proveedor> lista = gestionProveedor.listarProveedores();
            for (Proveedor p : lista) {

                Object[] fila = {
                    p.getNit(), 
                    p.getRazonSocial(),
                    p.getTelefono(),
                    p.getDireccion() != null ? p.getDireccion() : "",
                    p.getCorreo() != null ? p.getCorreo() : "",
                    p.getProductoSuministrado() != null ? p.getProductoSuministrado() : ""
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLimpiar) { limpiarCampos(); return; }

        String nit = campoNit.getText().trim();

        if (e.getSource() == botonEliminar) {
            if (nit.isEmpty()) { 
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un registro de la tabla."); 
                return; 
            }
            int r = JOptionPane.showConfirmDialog(this, "¿Eliminar proveedor con NIT " + nit + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                try { 
                    gestionProveedor.eliminarProveedorLocal(nit); 
                    JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
                }
                limpiarCampos(); 
                cargarDatosTabla();
            }
            return;
        }

        if (nit.isEmpty() || campoRazonSocial.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos NIT y Razón Social son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Proveedor proveedor = new Proveedor(
                campoNit.getText().trim(),
                campoRazonSocial.getText().trim(),
                campoTelefono.getText().trim(),
                campoDireccion.getText().trim(),
                campoCorreo.getText().trim(),   
                campoProducto.getText().trim()    
            );

        try {
            if (e.getSource() == botonGuardar) {
                gestionProveedor.registrarProveedorLocal(proveedor);
                JOptionPane.showMessageDialog(this, "¡Proveedor registrado con éxito!");
            } else if (e.getSource() == botonActualizar) {
                gestionProveedor.actualizarProveedorLocal(proveedor);
                JOptionPane.showMessageDialog(this, "¡Modificación guardada exitosamente!");
            }
            limpiarCampos(); 
            cargarDatosTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en la transacción: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        campoNit.setText(""); campoRazonSocial.setText(""); campoTelefono.setText("");
        campoDireccion.setText(""); campoCorreo.setText(""); campoProducto.setText("");
        campoNit.setEditable(true);
        tablaProveedores.clearSelection();
    }
}