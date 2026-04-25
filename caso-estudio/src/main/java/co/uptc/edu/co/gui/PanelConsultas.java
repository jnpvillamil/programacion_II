package co.uptc.edu.co.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class PanelConsultas extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Consultas";
    private static final String TEXTO_TOTAL_INICIAL = "Resultados encontrados: 0";
    private static final String TEXTO_TOTAL = "Resultados encontrados: ";

    private static final String CONSULTA_VENTAS_FECHA = "Ventas por fecha";
    private static final String CONSULTA_COMPRAS_PROVEEDOR = "Compras por proveedor";
    private static final String CONSULTA_STOCK_BAJO = "Productos con stock bajo mínimo";
    private static final String CONSULTA_HISTORIAL_CLIENTE = "Historial de compras de cliente";
    private static final String CONSULTA_MOVIMIENTOS_CONTABLES = "Movimientos contables por cuenta y periodo";

    private static final String[] COLUMNAS_INICIALES = { "Resultado" };
    private static final String[] COLUMNAS_VENTAS_FECHA = {
            "Fecha", "Cliente", "Total", "Impuestos"
    };
    private static final String[] COLUMNAS_COMPRAS_PROVEEDOR = {
            "Factura Proveedor", "Fecha", "Proveedor", "Impuestos", "Total Compra"
    };
    private static final String[] COLUMNAS_STOCK_BAJO = {
            "Código", "Producto", "Categoría", "Stock Actual", "Stock Mínimo"
    };
    private static final String[] COLUMNAS_HISTORIAL_CLIENTE = {
            "Factura", "Fecha", "Cliente", "Forma de Pago", "Total"
    };
    private static final String[] COLUMNAS_MOVIMIENTOS_CONTABLES = {
            "Código Transacción", "Fecha", "Tipo Movimiento", "Cuenta", "Valor", "Descripción"
    };

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

    public PanelConsultas() {
        super();
        inicializarComponentesConsultas();
        configurarPanelConsultas();
        agregarComponentesConsultas();
        actualizarFiltros();
    }

    @Override
    protected String obtenerTituloPanel() {
        return TITULO_PANEL;
    }

    @Override
    protected String obtenerTextoTotalInicial() {
        return TEXTO_TOTAL_INICIAL;
    }

    @Override
    protected Object[] obtenerColumnas() {
        return COLUMNAS_INICIALES;
    }

    private void inicializarComponentesConsultas() {
        etiquetaTipoConsulta = new JLabel("Tipo de consulta:");
        etiquetaFecha = new JLabel("Fecha:");
        etiquetaProveedor = new JLabel("Proveedor:");
        etiquetaCliente = new JLabel("Cliente:");
        etiquetaCuenta = new JLabel("Cuenta:");
        etiquetaFechaInicio = new JLabel("Desde:");
        etiquetaFechaFin = new JLabel("Hasta:");

        botonConsultar = new JButton("Consultar");

        comboTipoConsulta = new JComboBox<>();
        comboTipoConsulta.addItem(CONSULTA_VENTAS_FECHA);
        comboTipoConsulta.addItem(CONSULTA_COMPRAS_PROVEEDOR);
        comboTipoConsulta.addItem(CONSULTA_STOCK_BAJO);
        comboTipoConsulta.addItem(CONSULTA_HISTORIAL_CLIENTE);
        comboTipoConsulta.addItem(CONSULTA_MOVIMIENTOS_CONTABLES);

        campoFecha = crearCampoFecha();
        campoFecha.setColumns(8);

        campoProveedor = new JTextField(12);
        campoCliente = new JTextField(12);
        campoCuenta = new JTextField(12);

        campoFechaInicio = crearCampoFecha();
        campoFechaInicio.setColumns(8);

        campoFechaFin = crearCampoFecha();
        campoFechaFin.setColumns(8);
    }

    private void configurarPanelConsultas() {
        botonConsultar.setBackground(Color.WHITE);
        comboTipoConsulta.addActionListener(e -> actualizarFiltros());
    }

    private void agregarComponentesConsultas() {
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
    }

    private void actualizarFiltros() {
        String tipoConsulta = comboTipoConsulta.getSelectedItem().toString();

        ocultarFiltros();

        if (tipoConsulta.equals(CONSULTA_VENTAS_FECHA)) {
            configurarVentasPorFecha();
        } else if (tipoConsulta.equals(CONSULTA_COMPRAS_PROVEEDOR)) {
            configurarComprasPorProveedor();
        } else if (tipoConsulta.equals(CONSULTA_STOCK_BAJO)) {
            configurarStockBajoMinimo();
        } else if (tipoConsulta.equals(CONSULTA_HISTORIAL_CLIENTE)) {
            configurarHistorialCliente();
        } else if (tipoConsulta.equals(CONSULTA_MOVIMIENTOS_CONTABLES)) {
            configurarMovimientosContables();
        }

        limpiarTabla();
        actualizarTextoTotal(TEXTO_TOTAL, 0);

        revalidate();
        repaint();
    }

    public void inicializarEventos(Evento evento) {
        // por hacer
    }

    private void configurarVentasPorFecha() {
        etiquetaFecha.setVisible(true);
        campoFecha.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_VENTAS_FECHA);
    }

    private void configurarComprasPorProveedor() {
        etiquetaProveedor.setVisible(true);
        campoProveedor.setVisible(true);
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_COMPRAS_PROVEEDOR);
    }

    private void configurarStockBajoMinimo() {
        modeloTabla.setColumnIdentifiers(COLUMNAS_STOCK_BAJO);
    }

    private void configurarHistorialCliente() {
        etiquetaCliente.setVisible(true);
        campoCliente.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_HISTORIAL_CLIENTE);
    }

    private void configurarMovimientosContables() {
        etiquetaCuenta.setVisible(true);
        campoCuenta.setVisible(true);
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_MOVIMIENTOS_CONTABLES);
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