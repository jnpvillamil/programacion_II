package co.uptc.edu.co.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class PanelReportes extends JPanel {

    private JLabel etiquetaTitulo;
    private JLabel etiquetaTotal;

    private JLabel etiquetaTipoReporte;
    private JLabel etiquetaFecha;
    private JLabel etiquetaMes;
    private JLabel etiquetaAnio;
    private JLabel etiquetaFechaInicio;
    private JLabel etiquetaFechaFin;

    private JButton botonGenerar;

    private JComboBox<String> comboTipoReporte;

    private JFormattedTextField campoFecha;
    private JTextField campoMes;
    private JFormattedTextField campoAnio;
    private JFormattedTextField campoFechaInicio;
    private JFormattedTextField campoFechaFin;

    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;

    private JPanel panelSuperior;
    private JPanel panelFiltros;
    private JPanel panelInferior;

    public PanelReportes() {
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();
        actualizarFiltros();
    }

    private void inicializarComponentes() {
        etiquetaTitulo = new JLabel("Gestión de Reportes");
        etiquetaTotal = new JLabel("Registros del reporte: 0");

        etiquetaTipoReporte = new JLabel("Tipo de reporte:");
        etiquetaFecha = new JLabel("Fecha:");
        etiquetaMes = new JLabel("Mes:");
        etiquetaAnio = new JLabel("Año:");
        etiquetaFechaInicio = new JLabel("Desde:");
        etiquetaFechaFin = new JLabel("Hasta:");

        botonGenerar = new JButton("Generar");

        comboTipoReporte = new JComboBox<>();
        comboTipoReporte.addItem("Ventas diarias");
        comboTipoReporte.addItem("Ventas mensuales");
        comboTipoReporte.addItem("Ventas anuales");
        comboTipoReporte.addItem("Utilidad bruta");
        comboTipoReporte.addItem("Productos más vendidos");
        comboTipoReporte.addItem("Clientes con mayor volumen de compra");
        comboTipoReporte.addItem("Comparación de ventas por forma de pago");
        comboTipoReporte.addItem("Estado de inventario valorizado");
        comboTipoReporte.addItem("Resumen contable por periodo");

        campoFecha = crearCampoFecha();
        campoFecha.setColumns(8);

        campoMes = new JTextField(6);

        campoAnio = crearCampoAnio();
        campoAnio.setColumns(6);

        campoFechaInicio = crearCampoFecha();
        campoFechaInicio.setColumns(8);

        campoFechaFin = crearCampoFecha();
        campoFechaFin.setColumns(8);

        modeloTabla = new DefaultTableModel();
        tablaReportes = new JTable(modeloTabla);

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

        tablaReportes.setRowHeight(25);
        tablaReportes.getTableHeader().setReorderingAllowed(false);
        tablaReportes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        comboTipoReporte.addActionListener(e -> actualizarFiltros());
    }

    private void agregarComponentes() {
        panelFiltros.add(etiquetaTipoReporte);
        panelFiltros.add(comboTipoReporte);

        panelFiltros.add(etiquetaFecha);
        panelFiltros.add(campoFecha);

        panelFiltros.add(etiquetaMes);
        panelFiltros.add(campoMes);

        panelFiltros.add(etiquetaAnio);
        panelFiltros.add(campoAnio);

        panelFiltros.add(etiquetaFechaInicio);
        panelFiltros.add(campoFechaInicio);

        panelFiltros.add(etiquetaFechaFin);
        panelFiltros.add(campoFechaFin);

        panelFiltros.add(botonGenerar);

        JPanel panelCabecera = new JPanel();
        panelCabecera.setLayout(new BorderLayout());
        panelCabecera.setBackground(Color.WHITE);

        panelCabecera.add(etiquetaTitulo, BorderLayout.NORTH);
        panelCabecera.add(panelFiltros, BorderLayout.CENTER);
        
        //Color botones
        botonGenerar.setBackground(Color.WHITE);

        panelSuperior.add(panelCabecera, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(tablaReportes);

        panelInferior.add(etiquetaTotal);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void actualizarFiltros() {
        String tipo = comboTipoReporte.getSelectedItem().toString();

        ocultarFiltros();

        if (tipo.equals("Ventas diarias")) {
            configurarVentasDiarias();
        }

        if (tipo.equals("Ventas mensuales")) {
            configurarVentasMensuales();
        }

        if (tipo.equals("Ventas anuales")) {
            configurarVentasAnuales();
        }

        if (tipo.equals("Utilidad bruta")) {
            configurarUtilidadBruta();
        }

        if (tipo.equals("Productos más vendidos")) {
            configurarProductosMasVendidos();
        }

        if (tipo.equals("Clientes con mayor volumen de compra")) {
            configurarClientesMayorCompra();
        }

        if (tipo.equals("Comparación de ventas por forma de pago")) {
            configurarVentasPorFormaPago();
        }

        if (tipo.equals("Estado de inventario valorizado")) {
            configurarInventarioValorizado();
        }

        if (tipo.equals("Resumen contable por periodo")) {
            configurarResumenContable();
        }

        modeloTabla.setRowCount(0);
        etiquetaTotal.setText("Registros del reporte: 0");

        revalidate();
        repaint();
    }

    private void configurarVentasDiarias() {
        etiquetaFecha.setVisible(true);
        campoFecha.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Fecha", "Total Ventas"
        });
    }

    private void configurarVentasMensuales() {
        etiquetaMes.setVisible(true);
        campoMes.setVisible(true);
        etiquetaAnio.setVisible(true);
        campoAnio.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Mes", "Año", "Total Ventas"
        });
    }

    private void configurarVentasAnuales() {
        etiquetaAnio.setVisible(true);
        campoAnio.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Año", "Total Ventas"
        });
    }

    private void configurarUtilidadBruta() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Total Ventas", "Costo de Ventas", "Utilidad Bruta"
        });
    }

    private void configurarProductosMasVendidos() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Código", "Producto", "Cantidad Vendida"
        });
    }

    private void configurarClientesMayorCompra() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Cliente", "Total Comprado"
        });
    }

    private void configurarVentasPorFormaPago() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Forma de Pago", "Valor"
        });
    }

    private void configurarInventarioValorizado() {
        modeloTabla.setColumnIdentifiers(new String[]{
                "Código", "Producto", "Stock", "Costo Unitario", "Valor Total"
        });
    }

    private void configurarResumenContable() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);

        modeloTabla.setColumnIdentifiers(new String[]{
                "Ingresos", "Egresos", "Utilidad"
        });
    }

    private void ocultarFiltros() {
        etiquetaFecha.setVisible(false);
        campoFecha.setVisible(false);

        etiquetaMes.setVisible(false);
        campoMes.setVisible(false);

        etiquetaAnio.setVisible(false);
        campoAnio.setVisible(false);

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

    private JFormattedTextField crearCampoAnio() {
        try {
            MaskFormatter mascara = new MaskFormatter("####");
            mascara.setPlaceholderCharacter('_');
            return new JFormattedTextField(mascara);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }
}