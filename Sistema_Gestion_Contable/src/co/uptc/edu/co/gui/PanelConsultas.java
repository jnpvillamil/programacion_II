package co.uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class PanelConsultas extends JPanel {

    private JLabel etiquetaTitulo;
    private JLabel etiquetaTotal;

    private JLabel etiquetaTipoConsulta;
    private JLabel etiquetaFecha;
    private JLabel etiquetaProveedor;
    private JLabel etiquetaCliente;
    private JLabel etiquetaCuenta;
    private JLabel etiquetaFechaInicio;
    private JLabel etiquetaFechaFin;

    private JButton botonConsultar;

    private JComboBox<String> comboTipoConsulta;

    private JFormattedTextField campoFecha;
    private JTextField campoProveedor;
    private JTextField campoCliente;
    private JTextField campoCuenta;
    private JFormattedTextField campoFechaInicio;
    private JFormattedTextField campoFechaFin;

    private JTable tablaConsultas;
    private DefaultTableModel modeloTabla;

    private JPanel panelSuperior;
    private JPanel panelFiltros;
    private JPanel panelInferior;

    public PanelConsultas() {
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();
        actualizarFiltros();
    }

    private void inicializarComponentes() {
        etiquetaTitulo = new JLabel("Gestión de Consultas");
        etiquetaTotal = new JLabel("Resultados encontrados: 0");

        etiquetaTipoConsulta = new JLabel("Tipo de consulta:");
        etiquetaFecha = new JLabel("Fecha:");
        etiquetaProveedor = new JLabel("Proveedor:");
        etiquetaCliente = new JLabel("Cliente:");
        etiquetaCuenta = new JLabel("Cuenta:");
        etiquetaFechaInicio = new JLabel("Desde:");
        etiquetaFechaFin = new JLabel("Hasta:");

        botonConsultar = new JButton("Consultar");

        comboTipoConsulta = new JComboBox<>();
        comboTipoConsulta.addItem("Ventas por fecha");
        comboTipoConsulta.addItem("Compras por proveedor");
        comboTipoConsulta.addItem("Productos con stock bajo mínimo");
        comboTipoConsulta.addItem("Historial de compras de cliente");
        comboTipoConsulta.addItem("Movimientos contables por cuenta y periodo");

        campoFecha = crearCampoFecha();
        campoFecha.setColumns(8);

        campoProveedor = new JTextField(12);
        campoCliente = new JTextField(12);
        campoCuenta = new JTextField(12);

        campoFechaInicio = crearCampoFecha();
        campoFechaInicio.setColumns(8);

        campoFechaFin = crearCampoFecha();
        campoFechaFin.setColumns(8);

        modeloTabla = new DefaultTableModel();
        tablaConsultas = new JTable(modeloTabla);

        panelSuperior = new JPanel();
        panelFiltros = new JPanel();
        panelInferior = new JPanel();
    }

    private void configurarPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBackground(Color.WHITE);

        panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.setBackground(Color.WHITE);

        panelInferior.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(Color.WHITE);

        tablaConsultas.setRowHeight(25);
        tablaConsultas.getTableHeader().setReorderingAllowed(false);
        tablaConsultas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        comboTipoConsulta.addActionListener(e -> actualizarFiltros());
    }

    private void agregarComponentes() {
        panelFiltros.add(etiquetaTipoConsulta);
        panelFiltros.add(comboTipoConsulta);

        panelFiltros.add(etiquetaFecha);
        panelFiltros.add(campoFecha);

        panelFiltros.add(etiquetaProveedor);
        panelFiltros.add(campoProveedor);

        panelFiltros.add(etiquetaCliente);
        panelFiltros.add(campoCliente);

        panelFiltros.add(etiquetaCuenta);
        panelFiltros.add(campoCuenta);

        panelFiltros.add(etiquetaFechaInicio);
        panelFiltros.add(campoFechaInicio);

        panelFiltros.add(etiquetaFechaFin);
        panelFiltros.add(campoFechaFin);

        panelFiltros.add(botonConsultar);

        JPanel panelCabecera = new JPanel();
        panelCabecera.setLayout(new BorderLayout());
        panelCabecera.setBackground(Color.WHITE);

        panelCabecera.add(etiquetaTitulo, BorderLayout.NORTH);
        panelCabecera.add(panelFiltros, BorderLayout.CENTER);
        
        //Color botones
        botonConsultar.setBackground(Color.WHITE);

        panelSuperior.add(panelCabecera, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(tablaConsultas);

        panelInferior.add(etiquetaTotal);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void actualizarFiltros() {
        String tipo = comboTipoConsulta.getSelectedItem().toString();

        ocultarFiltros();

        if (tipo.equals("Ventas por fecha")) {
            configurarVentasPorFecha();
        }

        if (tipo.equals("Compras por proveedor")) {
            configurarComprasPorProveedor();
        }

        if (tipo.equals("Productos con stock bajo mínimo")) {
            configurarStockBajoMinimo();
        }

        if (tipo.equals("Historial de compras de cliente")) {
            configurarHistorialCliente();
        }

        if (tipo.equals("Movimientos contables por cuenta y periodo")) {
            configurarMovimientosContables();
        }

        modeloTabla.setRowCount(0);
        etiquetaTotal.setText("Resultados encontrados: 0");

        revalidate();
        repaint();
    }

    private void configurarVentasPorFecha() {
        etiquetaFecha.setVisible(true);
        campoFecha.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Fecha", "Cliente", "Total", "Impuestos"
        });
    }

    private void configurarComprasPorProveedor() {
        etiquetaProveedor.setVisible(true);
        campoProveedor.setVisible(true);
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Factura Proveedor", "Fecha", "Proveedor", "Impuestos", "Total Compra"
        });
    }

    private void configurarStockBajoMinimo() {
        modeloTabla.setColumnIdentifiers(new String[]{
                "Código", "Producto", "Categoría", "Stock Actual", "Stock Mínimo"
        });
    }

    private void configurarHistorialCliente() {
        etiquetaCliente.setVisible(true);
        campoCliente.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Factura", "Fecha", "Cliente", "Forma de Pago", "Total"
        });
    }

    private void configurarMovimientosContables() {
        etiquetaCuenta.setVisible(true);
        campoCuenta.setVisible(true);
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Código Transacción", "Fecha", "Tipo Movimiento", "Cuenta", "Valor", "Descripción"
        });
    }

    private void ocultarFiltros() {
        etiquetaFecha.setVisible(false);
        campoFecha.setVisible(false);

        etiquetaProveedor.setVisible(false);
        campoProveedor.setVisible(false);

        etiquetaCliente.setVisible(false);
        campoCliente.setVisible(false);

        etiquetaCuenta.setVisible(false);
        campoCuenta.setVisible(false);

        etiquetaFechaInicio.setVisible(false);
        campoFechaInicio.setVisible(false);

        etiquetaFechaFin.setVisible(false);
        campoFechaFin.setVisible(false);
    }

    private JFormattedTextField crearCampoFecha() {
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            mascara.setPlaceholderCharacter('_');
            return new JFormattedTextField(mascara);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }
}