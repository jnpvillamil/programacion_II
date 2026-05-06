package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class DialogDetalleCompra extends JDialog {

    private JTextField campoNumeroFactura;
    private JTextField campoFecha;
    private JTextField campoProveedor;
    private JTextField campoSubtotal;
    private JTextField campoImpuestos;
    private JTextField campoTotal;

    private JTable tablaDetalleCompra;
    private DefaultTableModel modeloTabla;

    private JButton botonCerrar;

    public DialogDetalleCompra(Frame propietario) {
        super(propietario, "Detalle de Compra", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos();
    }

    private void inicializarComponentes() {
        campoNumeroFactura = new JTextField(15);
        campoFecha = new JTextField(15);
        campoProveedor = new JTextField(25);
        campoSubtotal = new JTextField(15);
        campoImpuestos = new JTextField(15);
        campoTotal = new JTextField(15);

        campoNumeroFactura.setEditable(false);
        campoFecha.setEditable(false);
        campoProveedor.setEditable(false);
        campoSubtotal.setEditable(false);
        campoImpuestos.setEditable(false);
        campoTotal.setEditable(false);

        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(new Object[] {
                "Código", "Producto", "Cantidad", "Costo Unitario", "Impuesto", "Subtotal"
        });

        tablaDetalleCompra = new JTable(modeloTabla);
        tablaDetalleCompra.setEnabled(false);
        tablaDetalleCompra.setRowHeight(24);

        botonCerrar = new JButton("Cerrar");
    }

    private void configurarDialogo() {
        setSize(900, 550);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelPrincipal.add(crearPanelDatos(), BorderLayout.NORTH);
        panelPrincipal.add(crearPanelTabla(), BorderLayout.CENTER);
        panelPrincipal.add(crearPanelInferior(), BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelDatos() {
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Información de la compra"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("N° Factura:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoNumeroFactura, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 3;
        panelDatos.add(campoFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Proveedor:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        panelDatos.add(campoProveedor, gbc);

        return panelDatos;
    }

    private JPanel crearPanelTabla() {
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Productos comprados"));

        JScrollPane scrollTabla = new JScrollPane(tablaDetalleCompra);
        scrollTabla.setPreferredSize(new Dimension(850, 250));

        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        return panelTabla;
    }

    private JPanel crearPanelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));

        JPanel panelResumen = new JPanel(new GridBagLayout());
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen de la compra"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelResumen.add(new JLabel("Subtotal:"), gbc);

        gbc.gridx = 1;
        panelResumen.add(campoSubtotal, gbc);

        gbc.gridx = 2;
        panelResumen.add(new JLabel("Impuestos:"), gbc);

        gbc.gridx = 3;
        panelResumen.add(campoImpuestos, gbc);

        gbc.gridx = 4;
        panelResumen.add(new JLabel("Total:"), gbc);

        gbc.gridx = 5;
        panelResumen.add(campoTotal, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonCerrar);

        panelInferior.add(panelResumen, BorderLayout.NORTH);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        return panelInferior;
    }

    private void inicializarEventos() {
        botonCerrar.addActionListener(e -> dispose());
    }

    public void cargarCompra(String numeroFactura, String fecha, String proveedor,
            String subtotal, String impuestos, String total) {
        campoNumeroFactura.setText(numeroFactura);
        campoFecha.setText(fecha);
        campoProveedor.setText(proveedor);
        campoSubtotal.setText(subtotal);
        campoImpuestos.setText(impuestos);
        campoTotal.setText(total);
    }

    public void agregarDetalle(String codigo, String producto, String cantidad,
            String costoUnitario, String impuesto, String subtotal) {
        modeloTabla.addRow(new Object[] {
                codigo, producto, cantidad, costoUnitario, impuesto, subtotal
        });
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    public JButton getBotonCerrar() {
        return botonCerrar;
    }

    public JTable getTablaDetalleCompra() {
        return tablaDetalleCompra;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }
}