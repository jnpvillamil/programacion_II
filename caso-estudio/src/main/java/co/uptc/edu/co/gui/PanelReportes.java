package co.uptc.edu.co.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class PanelReportes extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Reportes";
    private static final String TEXTO_TOTAL_INICIAL = "Registros del reporte: 0";
    private static final String TEXTO_TOTAL = "Registros del reporte: ";

    private static final String TEXTO_LABEL_TIPO_REPORTE = "Tipo de reporte:";
    private static final String TEXTO_LABEL_FECHA = "Fecha:";
    private static final String TEXTO_LABEL_MES = "Mes:";
    private static final String TEXTO_LABEL_ANIO = "Año:";
    private static final String TEXTO_LABEL_FECHA_INICIO = "Desde:";
    private static final String TEXTO_LABEL_FECHA_FIN = "Hasta:";

    private static final String TEXTO_BOTON_GENERAR = "Generar";

    private static final String REPORTE_VENTAS_DIARIAS = "Ventas diarias";
    private static final String REPORTE_VENTAS_MENSUALES = "Ventas mensuales";
    private static final String REPORTE_VENTAS_ANUALES = "Ventas anuales";
    private static final String REPORTE_UTILIDAD_BRUTA = "Utilidad bruta";
    private static final String REPORTE_PRODUCTOS_MAS_VENDIDOS = "Productos más vendidos";
    private static final String REPORTE_CLIENTES_MAYOR_COMPRA = "Clientes con mayor volumen de compra";
    private static final String REPORTE_VENTAS_FORMA_PAGO = "Comparación de ventas por forma de pago";
    private static final String REPORTE_INVENTARIO_VALORIZADO = "Estado de inventario valorizado";
    private static final String REPORTE_RESUMEN_CONTABLE = "Resumen contable por periodo";

    private static final String[] COLUMNAS_INICIALES = { "Resultado" };
    private static final String[] COLUMNAS_VENTAS_DIARIAS = {
            "Fecha", "Total Ventas"
    };
    private static final String[] COLUMNAS_VENTAS_MENSUALES = {
            "Mes", "Año", "Total Ventas"
    };
    private static final String[] COLUMNAS_VENTAS_ANUALES = {
            "Año", "Total Ventas"
    };
    private static final String[] COLUMNAS_UTILIDAD_BRUTA = {
            "Total Ventas", "Costo de Ventas", "Utilidad Bruta"
    };
    private static final String[] COLUMNAS_PRODUCTOS_MAS_VENDIDOS = {
            "Código", "Producto", "Cantidad Vendida"
    };
    private static final String[] COLUMNAS_CLIENTES_MAYOR_COMPRA = {
            "Cliente", "Total Comprado"
    };
    private static final String[] COLUMNAS_VENTAS_FORMA_PAGO = {
            "Forma de Pago", "Valor"
    };
    private static final String[] COLUMNAS_INVENTARIO_VALORIZADO = {
            "Código", "Producto", "Stock", "Costo Unitario", "Valor Total"
    };
    private static final String[] COLUMNAS_RESUMEN_CONTABLE = {
            "Ingresos", "Egresos", "Utilidad"
    };

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

    public PanelReportes() {
        super();
        inicializarComponentesReportes();
        configurarPanelReportes();
        agregarComponentesReportes();
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

    private void inicializarComponentesReportes() {
        etiquetaTipoReporte = new JLabel(TEXTO_LABEL_TIPO_REPORTE);
        etiquetaFecha = new JLabel(TEXTO_LABEL_FECHA);
        etiquetaMes = new JLabel(TEXTO_LABEL_MES);
        etiquetaAnio = new JLabel(TEXTO_LABEL_ANIO);
        etiquetaFechaInicio = new JLabel(TEXTO_LABEL_FECHA_INICIO);
        etiquetaFechaFin = new JLabel(TEXTO_LABEL_FECHA_FIN);

        botonGenerar = new JButton(TEXTO_BOTON_GENERAR);

        comboTipoReporte = new JComboBox<>();
        comboTipoReporte.addItem(REPORTE_VENTAS_DIARIAS);
        comboTipoReporte.addItem(REPORTE_VENTAS_MENSUALES);
        comboTipoReporte.addItem(REPORTE_VENTAS_ANUALES);
        comboTipoReporte.addItem(REPORTE_UTILIDAD_BRUTA);
        comboTipoReporte.addItem(REPORTE_PRODUCTOS_MAS_VENDIDOS);
        comboTipoReporte.addItem(REPORTE_CLIENTES_MAYOR_COMPRA);
        comboTipoReporte.addItem(REPORTE_VENTAS_FORMA_PAGO);
        comboTipoReporte.addItem(REPORTE_INVENTARIO_VALORIZADO);
        comboTipoReporte.addItem(REPORTE_RESUMEN_CONTABLE);

        campoFecha = crearCampoFecha();
        campoFecha.setColumns(8);

        campoMes = new JTextField(6);

        campoAnio = crearCampoAnio();
        campoAnio.setColumns(6);

        campoFechaInicio = crearCampoFecha();
        campoFechaInicio.setColumns(8);

        campoFechaFin = crearCampoFecha();
        campoFechaFin.setColumns(8);
    }

    private void configurarPanelReportes() {
        botonGenerar.setBackground(Color.WHITE);
        comboTipoReporte.addActionListener(e -> actualizarFiltros());
    }

    private void agregarComponentesReportes() {
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
    }

    private void actualizarFiltros() {
        String tipoReporte = comboTipoReporte.getSelectedItem().toString();

        ocultarFiltros();

        if (tipoReporte.equals(REPORTE_VENTAS_DIARIAS)) {
            configurarVentasDiarias();
        } else if (tipoReporte.equals(REPORTE_VENTAS_MENSUALES)) {
            configurarVentasMensuales();
        } else if (tipoReporte.equals(REPORTE_VENTAS_ANUALES)) {
            configurarVentasAnuales();
        } else if (tipoReporte.equals(REPORTE_UTILIDAD_BRUTA)) {
            configurarUtilidadBruta();
        } else if (tipoReporte.equals(REPORTE_PRODUCTOS_MAS_VENDIDOS)) {
            configurarProductosMasVendidos();
        } else if (tipoReporte.equals(REPORTE_CLIENTES_MAYOR_COMPRA)) {
            configurarClientesMayorCompra();
        } else if (tipoReporte.equals(REPORTE_VENTAS_FORMA_PAGO)) {
            configurarVentasPorFormaPago();
        } else if (tipoReporte.equals(REPORTE_INVENTARIO_VALORIZADO)) {
            configurarInventarioValorizado();
        } else if (tipoReporte.equals(REPORTE_RESUMEN_CONTABLE)) {
            configurarResumenContable();
        }

        limpiarTabla();
        actualizarTextoTotal(TEXTO_TOTAL, 0);

        revalidate();
        repaint();
    }

    public void inicializarEventos(Evento evento) {
        // Cuando definas el comando en Evento, lo conectas aquí.
        // botonGenerar.setActionCommand(Evento.CMD_GENERAR_REPORTE);
        // botonGenerar.addActionListener(evento);
    }

    private void configurarVentasDiarias() {
        etiquetaFecha.setVisible(true);
        campoFecha.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_VENTAS_DIARIAS);
    }

    private void configurarVentasMensuales() {
        etiquetaMes.setVisible(true);
        campoMes.setVisible(true);
        etiquetaAnio.setVisible(true);
        campoAnio.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_VENTAS_MENSUALES);
    }

    private void configurarVentasAnuales() {
        etiquetaAnio.setVisible(true);
        campoAnio.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_VENTAS_ANUALES);
    }

    private void configurarUtilidadBruta() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_UTILIDAD_BRUTA);
    }

    private void configurarProductosMasVendidos() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_PRODUCTOS_MAS_VENDIDOS);
    }

    private void configurarClientesMayorCompra() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_CLIENTES_MAYOR_COMPRA);
    }

    private void configurarVentasPorFormaPago() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_VENTAS_FORMA_PAGO);
    }

    private void configurarInventarioValorizado() {
        modeloTabla.setColumnIdentifiers(COLUMNAS_INVENTARIO_VALORIZADO);
    }

    private void configurarResumenContable() {
        etiquetaFechaInicio.setVisible(true);
        campoFechaInicio.setVisible(true);
        etiquetaFechaFin.setVisible(true);
        campoFechaFin.setVisible(true);
        modeloTabla.setColumnIdentifiers(COLUMNAS_RESUMEN_CONTABLE);
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