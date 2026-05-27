package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Proveedor;

@SuppressWarnings("serial")
public class VentanaProveedor extends JFrame implements ActionListener {
    
    private JTextField campoNit, campoRazonSocial, campoTelefono, campoDireccion, campoProducto;
    private JButton botonGuardar, botonActualizar, botonLimpiar;
    private JTable tablaProveedores;
    private DefaultTableModel modeloTabla;

    public VentanaProveedor() {
        setTitle("Módulo Maestro de Proveedores - UPTC");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        iniciarComponentesFormulario();
        iniciarComponenteTabla();
        cargarDatosTabla();
    }

    private void iniciarComponentesFormulario() {
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Empresa / Proveedor"));

        panelForm.add(new JLabel("NIT (*clave):"));
        campoNit = new JTextField();
        panelForm.add(campoNit);

        panelForm.add(new JLabel("Razón Social:"));
        campoRazonSocial = new JTextField();
        panelForm.add(campoRazonSocial);

        panelForm.add(new JLabel("Teléfono:"));
        campoTelefono = new JTextField();
        panelForm.add(campoTelefono);

        panelForm.add(new JLabel("Dirección:"));
        campoDireccion = new JTextField();
        panelForm.add(campoDireccion);

        panelForm.add(new JLabel("Producto Suministrado:"));
        campoProducto = new JTextField();
        panelForm.add(campoProducto);

        panelForm.add(new JLabel("")); panelForm.add(new JLabel(""));

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botonGuardar = new JButton("Guardar Nuevo");
        botonActualizar = new JButton("Modificar Seleccionado");
        botonLimpiar = new JButton("Limpiar Campos");

        botonGuardar.addActionListener(this);
        botonActualizar.addActionListener(this);
        botonLimpiar.addActionListener(this);

        panelAcciones.add(botonGuardar);
        panelAcciones.add(botonActualizar);
        panelAcciones.add(botonLimpiar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout(5, 5));
        contenedorSuperior.add(panelForm, BorderLayout.CENTER);
        contenedorSuperior.add(panelAcciones, BorderLayout.SOUTH);

        add(contenedorSuperior, BorderLayout.NORTH);
    }

    private void iniciarComponenteTabla() {
        String[] columnas = {"NIT", "Razón Social", "Teléfono", "Dirección", "Producto Suministrado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaProveedores = new JTable(modeloTabla);
        tablaProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tablaProveedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaProveedores.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    campoNit.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
                    campoRazonSocial.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
                    campoTelefono.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
                    campoDireccion.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
                    campoProducto.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
                    campoNit.setEditable(false);
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaProveedores);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Proveedores Registrados"));
        add(scrollTabla, BorderLayout.CENTER);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0); 
        Conexion conex = new Conexion();
        
        try {
            Connection c = conex.getConnection();
            if (c != null) {
                Statement st = c.createStatement();

                ResultSet rs = st.executeQuery("SELECT nit, razon_social, telefono, direccion, correo FROM proveedor");
                
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("nit"),
                        rs.getString("razon_social"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("correo")
                    };
                    modeloTabla.addRow(fila);
                }
                
                st.close();
                conex.desconectar();
            }
        } catch (Exception e) {
            System.out.println("Error al cargar tabla proveedores: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al poblar proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLimpiar) {
            limpiarCampos();
            return;
        }

        Proveedor proveedor = new Proveedor(
                campoNit.getText(), campoRazonSocial.getText(), campoTelefono.getText(),
                campoDireccion.getText(), campoProducto.getText()
        );

        if (e.getSource() == botonGuardar) {
            proveedor.registrar();
        } else if (e.getSource() == botonActualizar) {
            proveedor.modificar();
        }
        
        limpiarCampos();
        cargarDatosTabla();
    }

    private void limpiarCampos() {
        campoNit.setText(""); campoRazonSocial.setText(""); campoTelefono.setText("");
        campoDireccion.setText(""); campoProducto.setText("");
        campoNit.setEditable(true);
        tablaProveedores.clearSelection();
    }
}