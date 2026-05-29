package co.edu.uptc.controlador;

import co.edu.uptc.dto.ReporteConsolidadoDiarioDTO;
import co.edu.uptc.dto.ReportesDTO;
import co.edu.uptc.gui.PanelReportes;
import co.edu.uptc.negocio.GestionReportes;
import co.edu.uptc.utilidades.ExportadorDatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ControladorReportes {

    private static final String MEJOR_CLIENTE = "Mejor Cliente";
    private static final String PRODUCTO_MAS_VENDIDO = "Producto Más Vendido";
    private static final String VENTAS_METODO_PAGO = "Ventas por Método de Pago";
    private static final String ESTADO_INVENTARIO = "Estado de Inventario";

    private final PanelReportes vista;
    private final GestionReportes negocio;
    private final Map<String, Consumer<DefaultTableModel>> generadoresReporte;
    private String reporteActual;
    private ReporteConsolidadoDiarioDTO consolidadoActual;

    public ControladorReportes(PanelReportes vista, GestionReportes negocio) {
        this.vista = vista;
        this.negocio = negocio;
        this.generadoresReporte = crearGeneradoresReporte();
        this.inicializarEventos();
    }

    private Map<String, Consumer<DefaultTableModel>> crearGeneradoresReporte() {
        Map<String, Consumer<DefaultTableModel>> generadores = new HashMap<>();
        generadores.put(MEJOR_CLIENTE, this::generarMejorCliente);
        generadores.put(PRODUCTO_MAS_VENDIDO, this::generarProductoMasVendido);
        generadores.put(VENTAS_METODO_PAGO, this::generarVentasPorMetodoPago);
        generadores.put(ESTADO_INVENTARIO, this::generarEstadoInventario);
        return generadores;
    }

    private void inicializarEventos() {
        vista.getCbTipoReporte().addActionListener(e -> actualizarControlesFecha());
        vista.getBtnGenerar().addActionListener(e -> generarReporteDinamico());
        vista.getBtnGenerarJSON().addActionListener(e -> exportarJSON());
        actualizarControlesFecha();
    }

    private void actualizarControlesFecha() {
        boolean esConsolidado = esReporteConsolidado();
        vista.getLblFechaReporte().setVisible(esConsolidado);
        vista.getSpFechaReporte().setVisible(esConsolidado);
        if (!esConsolidado) {
            consolidadoActual = null;
        }
    }

    private boolean esReporteConsolidado() {
        Object seleccion = vista.getCbTipoReporte().getSelectedItem();
        return PanelReportes.REPORTE_CONSOLIDADO.equals(seleccion);
    }

    private LocalDate obtenerFechaSeleccionada() {
        Date fecha = (Date) vista.getSpFechaReporte().getValue();
        Instant instante = fecha.toInstant();
        return instante.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void generarReporteDinamico() {
        String seleccion = vista.getCbTipoReporte().getSelectedItem().toString();
        reporteActual = seleccion;
        consolidadoActual = null;
        DefaultTableModel modelo = vista.getModeloTabla();

        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        if (esReporteConsolidado()) {
            generarVistaConsolidado(modelo);
            return;
        }

        Consumer<DefaultTableModel> generador = generadoresReporte.get(seleccion);
        if (generador != null) {
            generador.accept(modelo);
        }
    }

    private void generarMejorCliente(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Cédula", "Nombre", "Total Comprado"});
        for (ReportesDTO.MejorClienteItem item : negocio.obtenerReporteMejorCliente()) {
            modelo.addRow(new Object[]{
                    item.getIdentificacion(),
                    item.getNombre(),
                    item.getTotalComprado()
            });
        }
    }

    private void generarProductoMasVendido(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Código", "Producto", "Unidades Vendidas"});
        for (ReportesDTO.ProductoVendidoItem item : negocio.obtenerReporteProductoMasVendido()) {
            modelo.addRow(new Object[]{
                    item.getCodigo(),
                    item.getNombre(),
                    item.getCantidad()
            });
        }
    }

    private void generarVentasPorMetodoPago(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Método de Pago", "Ingresos Totales"});
        for (ReportesDTO.VentaMetodoPagoItem item : negocio.obtenerReporteVentasPorMetodoPago()) {
            modelo.addRow(new Object[]{
                    item.getFormaPago(),
                    item.getTotalVenta()
            });
        }
    }

    private void generarEstadoInventario(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Código", "Producto", "Stock Actual", "Valorización Bodega"});
        for (ReportesDTO.InventarioItem item : negocio.obtenerReporteEstadoInventario()) {
            modelo.addRow(new Object[]{
                    item.getCodigo(),
                    item.getNombre(),
                    item.getStockActual(),
                    item.getValorizacion()
            });
        }
    }

    private void generarVistaConsolidado(DefaultTableModel modelo) {
        consolidadoActual = negocio.generarConsolidadoDiario(obtenerFechaSeleccionada());
        if (consolidadoActual == null) {
            JOptionPane.showMessageDialog(vista,
                    "No se pudo generar el resumen consolidado.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        modelo.setColumnIdentifiers(new String[]{"Indicador", "Valor"});

        modelo.addRow(new Object[]{"Fecha", consolidadoActual.getFecha()});
        modelo.addRow(new Object[]{"Total ventas", consolidadoActual.getTotalVentas()});
        modelo.addRow(new Object[]{"Total compras", consolidadoActual.getTotalCompras()});
        modelo.addRow(new Object[]{"Utilidad bruta", consolidadoActual.getUtilidadBruta()});

        for (ReporteConsolidadoDiarioDTO.VentaFormaPagoItem item : consolidadoActual.getVentasPorFormaPago()) {
            modelo.addRow(new Object[]{
                    "Ventas " + item.getFormaPago(),
                    item.getTotal()
            });
        }

        for (ReporteConsolidadoDiarioDTO.ProductoMasVendidoItem item : consolidadoActual.getProductosMasVendidos()) {
            modelo.addRow(new Object[]{
                    "Top producto: " + item.getNombre(),
                    item.getCantidad() + " unidades"
            });
        }

        for (Map.Entry<String, Double> cuenta : consolidadoActual.getResumenContable().entrySet()) {
            modelo.addRow(new Object[]{
                    "Contabilidad: " + cuenta.getKey(),
                    cuenta.getValue()
            });
        }
    }

    private void exportarJSON() {
        if (esReporteConsolidado()) {
            exportarConsolidadoJSON();
            return;
        }

        DefaultTableModel modelo = vista.getModeloTabla();
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista,
                    "No hay datos para exportar. Genere un reporte primero.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreReporte = reporteActual != null
                ? reporteActual
                : vista.getCbTipoReporte().getSelectedItem().toString();
        String ruta = "reporte_" + nombreReporte.toLowerCase()
                .replace(" ", "_")
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                + ".json";

        boolean exito = ExportadorDatos.exportarTablaAJson(modelo, nombreReporte, ruta);
        if (exito) {
            JOptionPane.showMessageDialog(vista,
                    "Reporte exportado correctamente en:\n" + ruta,
                    "Exportación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista,
                    "No se pudo exportar el reporte a JSON.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarConsolidadoJSON() {
        LocalDate fecha = obtenerFechaSeleccionada();
        Date fechaJava = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String ruta = "reporte_consolidado_" + fecha + ".json";

        boolean exito = negocio.generarResumenDiarioJSON(fechaJava, ruta);
        if (exito) {
            JOptionPane.showMessageDialog(vista,
                    "Resumen diario JSON exportado en:\n" + ruta,
                    "Exportación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista,
                    "No se pudo exportar el resumen diario JSON.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
