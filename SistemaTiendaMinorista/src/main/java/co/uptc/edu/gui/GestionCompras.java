package co.uptc.edu.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import co.uptc.edu.modelo.Compra;
import co.uptc.edu.modelo.DetalleCompra;
import co.uptc.edu.modelo.Proveedor;

public class GestionCompras extends JFrame {

    // ================= COMPONENTES =================
    private JTextField txtFactura;
    private JTextField txtFecha;

    private JComboBox<String> cbProveedor;

    private JTable tabla;
    private DefaultTableModel modelo;

    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;

    // ================= NEGOCIO =================
    private co.uptc.edu.negocio.GestionCompras gestion;
    private co.uptc.edu.negocio.GestionProveedores gestionProveedores;

    // ================= CONSTRUCTOR =================
    public GestionCompras(
            co.uptc.edu.negocio.GestionProveedores gestionProveedores) {

        this.gestionProveedores = gestionProveedores;

        gestion = new co.uptc.edu.negocio.GestionCompras();

        setTitle("Gestión de Compras");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15,15));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelDetalle(), BorderLayout.CENTER);
        add(crearPanelResumen(), BorderLayout.SOUTH);

        cargarProveedores();
    }

    // ================= CARGAR PROVEEDORES =================
    private void cargarProveedores() {

        cbProveedor.removeAllItems();

        cbProveedor.addItem("Seleccione proveedor");

        for (Proveedor p : gestionProveedores.obtenerProveedores()) {

            if (p.isActivo()) {

                cbProveedor.addItem(
                        p.getCodigo() +
                        " - " +
                        p.getRazonSocial()
                );
            }
        }
    }

    // ================= PANEL SUPERIOR =================
    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(
                new TitledBorder("Información General")
        );

        txtFactura = new JTextField(15);

        txtFecha = new JTextField("DD/MM/AAAA", 15);

        cbProveedor = new JComboBox<>();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);

        // FACTURA
        gbc.gridx = 0;
        gbc.gridy = 0;

        panel.add(new JLabel("Factura:"), gbc);

        gbc.gridx = 1;

        panel.add(txtFactura, gbc);

        // FECHA
        gbc.gridx = 2;

        panel.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 3;

        panel.add(txtFecha, gbc);

        // PROVEEDOR
        gbc.gridx = 0;
        gbc.gridy = 1;

        panel.add(new JLabel("Proveedor:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(cbProveedor, gbc);

        return panel;
    }

    // ================= PANEL DETALLE =================
    private JPanel crearPanelDetalle() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(
                new TitledBorder("Detalle de Productos")
        );

        String[] columnas = {
                "Código",
                "Producto",
                "Cantidad",
                "Costo",
                "Subtotal"
        };

        // ================= MODELO =================
        modelo = new DefaultTableModel(columnas, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {

                // Subtotal NO editable
                return column != 4;
            }
        };

        // ================= TABLA =================
        tabla = new JTable(modelo);

        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);

        panel.add(scroll, BorderLayout.CENTER);

        // ================= CALCULAR AUTOMÁTICO =================
        modelo.addTableModelListener(e -> {

            // evitar ciclo infinito
            if (e.getColumn() == 4) {
                return;
            }

            for (int i = 0; i < modelo.getRowCount(); i++) {

                try {

                    Object cantidadObj =
                            modelo.getValueAt(i, 2);

                    Object costoObj =
                            modelo.getValueAt(i, 3);

                    if (cantidadObj != null &&
                            costoObj != null &&
                            !cantidadObj.toString().isEmpty() &&
                            !costoObj.toString().isEmpty()) {

                        int cantidad = Integer.parseInt(
                                cantidadObj.toString());

                        double costo = Double.parseDouble(
                                costoObj.toString());

                        double subtotal =
                                cantidad * costo;

                        modelo.setValueAt(
                                subtotal,
                                i,
                                4
                        );
                    }

                } catch (Exception ex) {

                    modelo.setValueAt(0, i, 4);
                }
            }

            calcularTotales();
        });

        // ================= BOTONES =================
        JPanel panelBotones = new JPanel();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // ================= AGREGAR FILA =================
        btnAgregar.addActionListener(e -> {

            modelo.addRow(new Object[]{
                    "",
                    "",
                    "",
                    "",
                    ""
            });
        });

        // ================= ELIMINAR FILA =================
        btnEliminar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila >= 0) {

                modelo.removeRow(fila);

                calcularTotales();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione una fila"
                );
            }
        });

        // ================= LIMPIAR =================
        btnLimpiar.addActionListener(e -> {

            modelo.setRowCount(0);

            calcularTotales();
        });

        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    // ================= PANEL RESUMEN =================
    private JPanel crearPanelResumen() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(
                new TitledBorder("Resumen")
        );

        JPanel panelTotales =
                new JPanel(new GridLayout(3,2,10,10));

        txtSubtotal = new JTextField();
        txtIVA = new JTextField();
        txtTotal = new JTextField();

        txtSubtotal.setEditable(false);
        txtIVA.setEditable(false);
        txtTotal.setEditable(false);

        panelTotales.add(new JLabel("Subtotal:"));
        panelTotales.add(txtSubtotal);

        panelTotales.add(new JLabel("IVA 19%:"));
        panelTotales.add(txtIVA);

        panelTotales.add(new JLabel("TOTAL:"));
        panelTotales.add(txtTotal);

        panel.add(panelTotales, BorderLayout.CENTER);

        // ================= BOTÓN REGISTRAR =================
        JButton btnRegistrar =
                new JButton("Registrar Compra");

        btnRegistrar.addActionListener(e -> {
        	
            // GUARDAR EDICIÓN ACTUAL DE LA TABLA
            if (tabla.isEditing()) {
                tabla.getCellEditor().stopCellEditing();
            }

            // VALIDAR FACTURA
            if (txtFactura.getText().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese factura"
                );

                return;
            }

            // VALIDAR PROVEEDOR
            if (cbProveedor.getSelectedIndex() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione proveedor"
                );

                return;
            }

            // VALIDAR PRODUCTOS
            if (modelo.getRowCount() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Agregue productos"
                );

                return;
            }

         // ================= VALIDAR CÓDIGOS REPETIDOS =================
            for (int i = 0; i < modelo.getRowCount(); i++) {

                Object objCodigo1 = modelo.getValueAt(i, 0);

                if (objCodigo1 == null) {
                    continue;
                }

                String codigo1 = objCodigo1.toString().trim();

                if (codigo1.isEmpty()) {
                    continue;
                }

                for (int j = i + 1; j < modelo.getRowCount(); j++) {

                    Object objCodigo2 = modelo.getValueAt(j, 0);

                    if (objCodigo2 == null) {
                        continue;
                    }

                    String codigo2 = objCodigo2.toString().trim();

                    if (codigo2.isEmpty()) {
                        continue;
                    }

                    if (codigo1.equalsIgnoreCase(codigo2)) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Código repetido en filas "
                                + (i + 1)
                                + " y "
                                + (j + 1)
                        );

                        return;
                    }
                }
            }
            // ================= CREAR COMPRA =================
            Compra compra = new Compra(
                    txtFactura.getText(),
                    txtFecha.getText(),
                    cbProveedor.getSelectedItem().toString()
            );

            // ================= AGREGAR DETALLES =================
            for (int i = 0; i < modelo.getRowCount(); i++) {

                try {

                    DetalleCompra detalle =
                            new DetalleCompra(

                                    modelo.getValueAt(i,0).toString(),

                                    modelo.getValueAt(i,1).toString(),

                                    Integer.parseInt(
                                            modelo.getValueAt(i,2).toString()
                                    ),

                                    Double.parseDouble(
                                            modelo.getValueAt(i,3).toString()
                                    )
                            );

                    compra.agregarDetalle(detalle);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Error en fila " + (i + 1)
                    );

                    return;
                }
            }

            // ================= REGISTRAR =================
            if (gestion.registrarCompra(compra)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Compra registrada correctamente\n\n" +
                        "Subtotal: " + txtSubtotal.getText() +
                        "\nIVA: " + txtIVA.getText() +
                        "\nTOTAL: " + txtTotal.getText()
                );

                limpiarTodo();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Factura duplicada"
                );
            }
        });

        panel.add(btnRegistrar, BorderLayout.SOUTH);

        return panel;
    }

    // ================= CALCULAR TOTALES =================
    private void calcularTotales() {

        double subtotalGeneral = 0;

        for (int i = 0; i < modelo.getRowCount(); i++) {

            try {

                Object valor =
                        modelo.getValueAt(i, 4);

                if (valor != null &&
                        !valor.toString().isEmpty()) {

                    subtotalGeneral += Double.parseDouble(
                            valor.toString()
                    );
                }

            } catch (Exception ex) {

            }
        }

        double iva = subtotalGeneral * 0.19;

        double total = subtotalGeneral + iva;

        txtSubtotal.setText(
                String.format("%.2f", subtotalGeneral)
        );

        txtIVA.setText(
                String.format("%.2f", iva)
        );

        txtTotal.setText(
                String.format("%.2f", total)
        );
    }

    // ================= LIMPIAR =================
    private void limpiarTodo() {

        modelo.setRowCount(0);

        txtFactura.setText("");

        txtFecha.setText("");

        txtSubtotal.setText("");

        txtIVA.setText("");

        txtTotal.setText("");

        cbProveedor.setSelectedIndex(0);
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        co.uptc.edu.negocio.GestionProveedores gp =
                new co.uptc.edu.negocio.GestionProveedores();

        gp.registrarProveedor(
                new Proveedor(
                        "1234",
                        "Proveedor Demo",
                        "123",
                        "Calle 1",
                        "3000000000",
                        "correo@gmail.com"
                )
        );

        new GestionCompras(gp).setVisible(true);
    }
}