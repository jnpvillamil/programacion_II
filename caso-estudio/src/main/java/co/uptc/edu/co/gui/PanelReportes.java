package co.uptc.edu.co.gui;

import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class PanelReportes extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Reportes";
    private static final String TEXTO_TOTAL_INICIAL = "Registros del reporte: 0";
    private static final String TEXTO_TOTAL = "Registros del reporte: ";

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
    	etiquetaTipoReporte = new JLabel("Tipo de reporte:");
        etiquetaFecha = new JLabel("Fecha:");
        etiquetaMes = new JLabel("Mes:");
        etiquetaAnio = new JLabel("Año:");
        etiquetaFechaInicio = new JLabel("Desde:");
        etiquetaFechaFin = new JLabel("Hasta:");

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
    	 configurarBotonBase(botonGenerar);
         asignarFiltroCombo(comboTipoReporte, this::actualizarFiltros);
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
       // por hacer
    }

    private void configurarVentasDiarias() {
    	 mostrarComponentes(etiquetaFecha, campoFecha);
         actualizarColumnas(COLUMNAS_VENTAS_DIARIAS);
    }

    private void configurarVentasMensuales() {
    	 mostrarComponentes(etiquetaMes, campoMes, etiquetaAnio, campoAnio);
         actualizarColumnas(COLUMNAS_VENTAS_MENSUALES);
    }

    private void configurarVentasAnuales() {
    	  mostrarComponentes(etiquetaAnio, campoAnio);
          actualizarColumnas(COLUMNAS_VENTAS_ANUALES);
    }

    private void configurarUtilidadBruta() {
    	mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
        actualizarColumnas(COLUMNAS_UTILIDAD_BRUTA);
    }

    private void configurarProductosMasVendidos() {
    	 mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
         actualizarColumnas(COLUMNAS_PRODUCTOS_MAS_VENDIDOS);
    }
    private void configurarClientesMayorCompra() {
    	mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
        actualizarColumnas(COLUMNAS_CLIENTES_MAYOR_COMPRA);
    }

    private void configurarVentasPorFormaPago() {
    	 mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
         actualizarColumnas(COLUMNAS_VENTAS_FORMA_PAGO);
    }

    private void configurarInventarioValorizado() {
    	 actualizarColumnas(COLUMNAS_INVENTARIO_VALORIZADO);
    }

    private void configurarResumenContable() {
    	mostrarComponentes(etiquetaFechaInicio, campoFechaInicio, etiquetaFechaFin, campoFechaFin);
        actualizarColumnas(COLUMNAS_RESUMEN_CONTABLE);
    }

    private void ocultarFiltros() {
    	  ocultarComponentes(
                  etiquetaFecha, campoFecha,
                  etiquetaMes, campoMes,
                  etiquetaAnio, campoAnio,
                  etiquetaFechaInicio, campoFechaInicio,
                  etiquetaFechaFin, campoFechaFin
          );
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