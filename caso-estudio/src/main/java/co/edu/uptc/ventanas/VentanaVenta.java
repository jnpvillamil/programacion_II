package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import co.edu.uptc.gui.modelo.Venta;

@SuppressWarnings("serial")
public class VentanaVenta extends JFrame implements ActionListener {

    private JTextField campoFactura, campoCliente, campoProducto, campoPrecio, campoCantidad, campoTotal;
    private JButton botonFichar, botonLimpiar;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;

    public VentanaVenta() {
        setTitle("Módulo de Facturación y Ventas ");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        iniciarComponentesFormulario();
        iniciarComponenteTabla();
    }

    private void iniciarComponentesFormulario() {
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 10, 10));
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

        campoCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotalVenta();
            }
        });

        panelForm.add(new JLabel("Total Venta ($):"));
        campoTotal = new JTextField();
        campoTotal.setEditable(false);
        
        campoTotal.setBackground(new Color(240, 240, 240));
        panelForm.add(campoTotal);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botonFichar = new JButton(" FACTURAR ");
        botonLimpiar = new JButton("Limpiar Formulario");

        botonFichar.addActionListener(this);
        botonLimpiar.addActionListener(this);

        panelAcciones.add(botonFichar);
        panelAcciones.add(botonLimpiar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout(5, 5));
        contenedorSuperior.add(panelForm, BorderLayout.CENTER);
        contenedorSuperior.add(panelAcciones, BorderLayout.SOUTH);

        add(contenedorSuperior, BorderLayout.NORTH);
    }

    private void iniciarComponenteTabla() {
        String[] columnas = {"N° Factura", "Cod. Cliente", "Cod. Producto", "Cantidad", "Total Facturado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaVentas = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaVentas);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de Ventas Registradas en el Turno"));
        add(scroll, BorderLayout.CENTER);
    }

    private void calcularTotalVenta() {
        try {
            if (!campoPrecio.getText().isEmpty() && !campoCantidad.getText().isEmpty()) {
                double precio = Double.parseDouble(campoPrecio.getText());
                int cantidad = Integer.parseInt(campoCantidad.getText());
                campoTotal.setText(String.valueOf(precio * cantidad));
            } else {
                campoTotal.setText("");
            }
        } catch (NumberFormatException ex) {
            campoTotal.setText("Error datos");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLimpiar) {
            limpiarCampos();
            return;
        }

        if (e.getSource() == botonFichar) {
            if (campoFactura.getText().isEmpty() || campoCliente.getText().isEmpty() || campoProducto.getText().isEmpty() || campoCantidad.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos de la venta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Venta venta = new Venta(
                        campoFactura.getText(),
                        campoCliente.getText(),
                        campoProducto.getText(),
                        Integer.parseInt(campoCantidad.getText()),
                        Double.parseDouble(campoTotal.getText())
                );

                venta.registrarVenta();

                Object[] fila = {
                    venta.getCodigoFactura(),
                    venta.getCodigoCliente(),
                    venta.getCodigoProducto(),
                    venta.getCantidad(),
                    venta.getTotal()
                };
                modeloTabla.addRow(fila);
                
                JOptionPane.showMessageDialog(this, "¡Venta registrada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Verifique los valores de precio y cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        campoFactura.setText("");
        campoCliente.setText("");
        campoProducto.setText("");
        campoPrecio.setText("");
        campoCantidad.setText("");
        campoTotal.setText("");
    }
}