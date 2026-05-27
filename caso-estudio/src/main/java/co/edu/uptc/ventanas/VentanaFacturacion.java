package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import co.edu.uptc.conexion.Conexion;

@SuppressWarnings("serial")
public class VentanaFacturacion extends JFrame implements ActionListener {

    private JTextField campoFactura, campoCliente, campoProducto, campoPrecio, campoCantidad, campoTotal;
    private JButton botonFacturar, botonLimpiar;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;

    public VentanaFacturacion() {
        setTitle("Módulo de Facturación y Ventas");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        iniciarFormulario();
        iniciarTablaHistorial();

        cargarHistorialVentas(); 
    }

    private void iniciarFormulario() {
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 15, 12));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Nueva Venta (Datos de Facturación)"));

        panelForm.add(new JLabel("N° Factura:"));
        campoFactura = new JTextField();
        panelForm.add(campoFactura);

        panelForm.add(new JLabel("Código Cliente:"));
        campoCliente = new JTextField();
        panelForm.add(campoCliente);

        panelForm.add(new JLabel("Código Producto:"));
        campoProducto = new JTextField();
        panelForm.add(campoProducto);

        panelForm.add(new JLabel("Precio Unitario ($):"));
        campoPrecio = new JTextField();
        panelForm.add(campoPrecio);

        panelForm.add(new JLabel("Cantidad:"));
        campoCantidad = new JTextField();
        panelForm.add(campoCantidad);

        panelForm.add(new JLabel("Total Venta ($):"));
        campoTotal = new JTextField();
        campoTotal.setEditable(false);
        campoTotal.setBackground(new Color(240, 240, 240));
        panelForm.add(campoTotal);

        campoCantidad.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calcularTotalAutomatico();
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        botonFacturar = new JButton("FACTURAR");
        botonLimpiar = new JButton("Limpiar Formulario");

        botonFacturar.addActionListener(this);
        botonLimpiar.addActionListener(this);

        panelBotones.add(botonFacturar);
        panelBotones.add(botonLimpiar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout(5, 5));
        contenedorSuperior.add(panelForm, BorderLayout.CENTER);
        contenedorSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(contenedorSuperior, BorderLayout.NORTH);
    }

    private void iniciarTablaHistorial() {

        String[] columnas = {"N° Factura", "Cod. Cliente", "Cod. Producto", "Cantidad", "Total Facturado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaVentas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historial de Ventas Registradas en el Turno"));
        
        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarHistorialVentas() {
        modeloTabla.setRowCount(0);
        Conexion conex = new Conexion();
        String sql = "SELECT numero_factura, codigo_cliente, codigo_producto, cantidad, total_venta FROM factura_venta";

        try (Connection c = conex.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (c != null) {
                while (rs.next()) {
                    Object[] fila = {
                        rs.getString("numero_factura"),
                        rs.getString("codigo_cliente"),
                        rs.getString("codigo_producto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("total_venta")
                    };
                    modeloTabla.addRow(fila);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar historial: " + e.getMessage());
        } finally {
            conex.desconectar();
        }
    }

    private void calcularTotalAutomatico() {
        try {
            if (!campoPrecio.getText().isEmpty() && !campoCantidad.getText().isEmpty()) {
                double precio = Double.parseDouble(campoPrecio.getText());
                int cantidad = Integer.parseInt(campoCantidad.getText());
                campoTotal.setText(String.valueOf(precio * cantidad));
            }
        } catch (NumberFormatException e) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLimpiar) {
            limpiarFormulario();
        } else if (e.getSource() == botonFacturar) {
            registrarVentaEnBD();
        }
    }

    private void registrarVentaEnBD() {

        calcularTotalAutomatico();

        if (campoFactura.getText().isEmpty() || campoCliente.getText().isEmpty() || 
            campoProducto.getText().isEmpty() || campoCantidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos obligatorios deben estar llenos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conexion conex = new Conexion();
        String sql = "INSERT INTO factura_venta (numero_factura, codigo_cliente, codigo_producto, cantidad, total_venta) VALUES (?, ?, ?, ?, ?)";

        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {

            if (c != null) {
                pst.setString(1, campoFactura.getText().trim());
                pst.setString(2, campoCliente.getText().trim());
                pst.setString(3, campoProducto.getText().trim());
                pst.setInt(4, Integer.parseInt(campoCantidad.getText().trim()));
                pst.setDouble(5, Double.parseDouble(campoTotal.getText().trim()));

                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "¡Factura procesada con éxito!", "Venta Exitosa", JOptionPane.INFORMATION_MESSAGE);
                
                limpiarFormulario();
                cargarHistorialVentas(); 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de BD al facturar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique los formatos de precio o cantidad.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } finally {
            conex.desconectar();
        }
    }

    private void limpiarFormulario() {
        campoFactura.setText("");
        campoCliente.setText("");
        campoProducto.setText("");
        campoPrecio.setText("");
        campoCantidad.setText("");
        campoTotal.setText("");
        campoFactura.requestFocus();
    }
}