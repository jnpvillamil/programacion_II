package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
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

public class DialogFacturaVenta extends JDialog {

    private JTextField campoNumeroFactura;
    private JTextField campoFecha;
    private JTextField campoHora;

    private JTextField campoCliente;
    private JTextField campoIdentificacion;
    private JTextField campoDireccion;
    private JTextField campoTelefono;

    private JTextField campoSubtotal;
    private JTextField campoImpuestos;
    private JTextField campoTotal;

    private JTable tablaFactura;
    private DefaultTableModel modeloTabla;

    private JButton botonCerrar;

    public DialogFacturaVenta(Frame propietario) {
        super(propietario, "Factura de Venta", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos();
    }

    private void inicializarComponentes() {
        campoNumeroFactura = new JTextField(12);
        campoFecha = new JTextField(12);
        campoHora = new JTextField(12);

        campoCliente = new JTextField(20);
        campoIdentificacion = new JTextField(20);
        campoDireccion = new JTextField(20);
        campoTelefono = new JTextField(20);

        campoSubtotal = new JTextField(12);
        campoImpuestos = new JTextField(12);
        campoTotal = new JTextField(12);

        campoNumeroFactura.setEditable(false);
        campoFecha.setEditable(false);
        campoHora.setEditable(false);
        campoCliente.setEditable(false);
        campoIdentificacion.setEditable(false);
        campoDireccion.setEditable(false);
        campoTelefono.setEditable(false);
        campoSubtotal.setEditable(false);
        campoImpuestos.setEditable(false);
        campoTotal.setEditable(false);

        modeloTabla = new DefaultTableModel(
                new Object[] { "Producto", "Cantidad", "Precio Unitario", "Impuestos", "Subtotal" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaFactura = new JTable(modeloTabla);

        botonCerrar = new JButton("Cerrar");
    }

    private void configurarDialogo() {
        setSize(800, 550);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelPrincipal.add(crearPanelEncabezado(), BorderLayout.NORTH);
        panelPrincipal.add(crearPanelCentro(), BorderLayout.CENTER);
        panelPrincipal.add(crearPanelInferior(), BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelEncabezado() {
        JPanel panelEncabezado = new JPanel(new BorderLayout(10, 10));
        panelEncabezado.setBorder(BorderFactory.createTitledBorder("Factura de Venta"));

        JPanel panelEmpresa = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelEmpresa.add(new JLabel("Empresa:"), gbc);
        gbc.gridx = 1;
        panelEmpresa.add(new JLabel("Tienda Minorista"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelEmpresa.add(new JLabel("NIT:"), gbc);
        gbc.gridx = 1;
        panelEmpresa.add(new JLabel("123456789"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelEmpresa.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        panelEmpresa.add(new JLabel("Puerto Boyacá"), gbc);

        JPanel panelFactura = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(4, 4, 4, 4);
        gbc2.anchor = GridBagConstraints.WEST;

        gbc2.gridx = 0;
        gbc2.gridy = 0;
        panelFactura.add(new JLabel("Factura:"), gbc2);
        gbc2.gridx = 1;
        panelFactura.add(campoNumeroFactura, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 1;
        panelFactura.add(new JLabel("Fecha:"), gbc2);
        gbc2.gridx = 1;
        panelFactura.add(campoFecha, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 2;
        panelFactura.add(new JLabel("Hora:"), gbc2);
        gbc2.gridx = 1;
        panelFactura.add(campoHora, gbc2);

        panelEncabezado.add(panelEmpresa, BorderLayout.WEST);
        panelEncabezado.add(panelFactura, BorderLayout.EAST);

        return panelEncabezado;
    }

    private JPanel crearPanelCentro() {
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));

        JPanel panelCliente = new JPanel(new GridBagLayout());
        panelCliente.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCliente.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        panelCliente.add(campoCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCliente.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 1;
        panelCliente.add(campoIdentificacion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCliente.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        panelCliente.add(campoDireccion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelCliente.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        panelCliente.add(campoTelefono, gbc);

        JScrollPane scrollTabla = new JScrollPane(tablaFactura);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Detalle de la Factura"));

        panelCentro.add(panelCliente, BorderLayout.NORTH);
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        return panelCentro;
    }

    private JPanel crearPanelInferior() {
        JPanel panelInferior = new JPanel(new BorderLayout());

        JPanel panelTotales = new JPanel(new GridBagLayout());
        panelTotales.setBorder(BorderFactory.createTitledBorder("Totales"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.EAST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelTotales.add(new JLabel("Subtotal:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(campoSubtotal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelTotales.add(new JLabel("Impuestos:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(campoImpuestos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelTotales.add(new JLabel("Total:"), gbc);
        gbc.gridx = 1;
        panelTotales.add(campoTotal, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonCerrar);

        panelInferior.add(panelTotales, BorderLayout.CENTER);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        return panelInferior;
    }

    private void inicializarEventos() {
        botonCerrar.addActionListener(e -> dispose());
    }

    public void cargarFactura(String numeroFactura, String fecha, String hora,
            String cliente, String identificacion, String direccion,
            String telefono, String subtotal, String impuestos, String total) {

        campoNumeroFactura.setText(numeroFactura);
        campoFecha.setText(fecha);
        campoHora.setText(hora);
        campoCliente.setText(cliente);
        campoIdentificacion.setText(identificacion);
        campoDireccion.setText(direccion);
        campoTelefono.setText(telefono);
        campoSubtotal.setText(subtotal);
        campoImpuestos.setText(impuestos);
        campoTotal.setText(total);
    }

    public void agregarDetalleFactura(String producto, String cantidad,
            String precioUnitario, String impuestos, String subtotal) {
        modeloTabla.addRow(new Object[] { producto, cantidad, precioUnitario, impuestos, subtotal });
    }

    public void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }
}