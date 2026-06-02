package co.edu.uptc.gui;

import co.edu.uptc.dto.ReporteConsolidadoDiarioDTO;
import co.edu.uptc.dto.ReportesDTO;
import co.edu.uptc.negocio.GestionReportes;
import co.edu.uptc.utilidades.ExportadorDatos;
import co.edu.uptc.utilidades.FormateadorMoneda;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EventoReportes implements ActionListener {

    public static final String CMD_GENERAR = "CMD_GENERAR_REPORTE";
    public static final String CMD_EXPORTAR_JSON = "CMD_EXPORTAR_JSON";
    public static final String CMD_CAMBIAR_TIPO = "CMD_CAMBIAR_TIPO_REPORTE";

    private final VentanaPrincipal ventanaPrincipal;
    private final PanelReportes panel;
    private final GestionReportes gestion;

    public EventoReportes(VentanaPrincipal ventanaPrincipal,
                          PanelReportes panel,
                          GestionReportes gestion) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panel = panel;
        this.gestion = gestion;

        suscribir(panel.getBtnGenerar(), CMD_GENERAR);
        suscribir(panel.getBtnGenerarJSON(), CMD_EXPORTAR_JSON);

        panel.getCbTipoReporte().setActionCommand(CMD_CAMBIAR_TIPO);
        panel.getCbTipoReporte().addActionListener(this);
    }

    private void suscribir(javax.swing.JButton boton, String comando) {
        boton.setActionCommand(comando);
        boton.addActionListener(this);
    }

    private void cambiarTipoReporte() {
        boolean consolidado = PanelReportes.REPORTE_CONSOLIDADO.equals(
                panel.getCbTipoReporte().getSelectedItem());
        panel.getLblFechaReporte().setVisible(consolidado);
        panel.getSpFechaReporte().setVisible(consolidado);
    }

    private void generarReporte() {
        String tipo = (String) panel.getCbTipoReporte().getSelectedItem();
        DefaultTableModel modelo = panel.getModeloTabla();
        modelo.setRowCount(0);

        if (PanelReportes.REPORTE_CONSOLIDADO.equals(tipo)) {
            generarConsolidado(modelo);
        } else if ("Mejor Cliente".equals(tipo)) {
            generarMejorCliente(modelo);
        } else if ("Producto Más Vendido".equals(tipo)) {
            generarProductoMasVendido(modelo);
        } else if ("Ventas por Método de Pago".equals(tipo)) {
            generarVentasPorMetodoPago(modelo);
        } else if ("Estado de Inventario".equals(tipo)) {
            generarEstadoInventario(modelo);
        }
    }

    private void generarMejorCliente(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Identificación", "Nombre", "Total Comprado"});
        List<ReportesDTO.MejorClienteItem> items = gestion.obtenerReporteMejorCliente();
        for (ReportesDTO.MejorClienteItem item : items) {
            modelo.addRow(new Object[]{
                    item.getIdentificacion(),
                    item.getNombre(),
                    FormateadorMoneda.formatearPeso(item.getTotalComprado())
            });
        }
    }

    private void generarProductoMasVendido(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Código", "Producto", "Cantidad Vendida"});
        List<ReportesDTO.ProductoVendidoItem> items = gestion.obtenerReporteProductoMasVendido();
        for (ReportesDTO.ProductoVendidoItem item : items) {
            modelo.addRow(new Object[]{item.getCodigo(), item.getNombre(), item.getCantidad()});
        }
    }

    private void generarVentasPorMetodoPago(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Forma de Pago", "Total Ventas"});
        List<ReportesDTO.VentaMetodoPagoItem> items = gestion.obtenerReporteVentasPorMetodoPago();
        for (ReportesDTO.VentaMetodoPagoItem item : items) {
            modelo.addRow(new Object[]{
                    item.getFormaPago(),
                    FormateadorMoneda.formatearPeso(item.getTotalVenta())
            });
        }
    }

    private void generarEstadoInventario(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Código", "Producto", "Stock", "Valorización"});
        List<ReportesDTO.InventarioItem> items = gestion.obtenerReporteEstadoInventario();
        for (ReportesDTO.InventarioItem item : items) {
            modelo.addRow(new Object[]{
                    item.getCodigo(),
                    item.getNombre(),
                    item.getStockActual(),
                    FormateadorMoneda.formatearPeso(item.getValorizacion())
            });
        }
    }

    private void generarConsolidado(DefaultTableModel modelo) {
        LocalDate fecha = obtenerFechaSpinner();
        ReporteConsolidadoDiarioDTO consolidado = gestion.generarConsolidadoDiario(fecha);
        if (consolidado == null) {
            JOptionPane.showMessageDialog(panel, "No se pudo generar el consolidado.");
            return;
        }

        modelo.setColumnIdentifiers(new String[]{"Concepto", "Valor"});
        modelo.addRow(new Object[]{"Fecha", consolidado.getFecha()});
        modelo.addRow(new Object[]{"Total Ventas", FormateadorMoneda.formatearPeso(consolidado.getTotalVentas())});
        modelo.addRow(new Object[]{"Total Compras", FormateadorMoneda.formatearPeso(consolidado.getTotalCompras())});
        modelo.addRow(new Object[]{"Utilidad Bruta", FormateadorMoneda.formatearPeso(consolidado.getUtilidadBruta())});

        for (ReporteConsolidadoDiarioDTO.VentaFormaPagoItem item : consolidado.getVentasPorFormaPago()) {
            modelo.addRow(new Object[]{
                    "Venta " + item.getFormaPago(),
                    FormateadorMoneda.formatearPeso(item.getTotal())
            });
        }
        for (ReporteConsolidadoDiarioDTO.ProductoMasVendidoItem item : consolidado.getProductosMasVendidos()) {
            modelo.addRow(new Object[]{
                    "Top " + item.getNombre(),
                    item.getCantidad() + " und."
            });
        }
        for (Map.Entry<String, Double> entry : consolidado.getResumenContable().entrySet()) {
            modelo.addRow(new Object[]{
                    entry.getKey(),
                    FormateadorMoneda.formatearPeso(entry.getValue())
            });
        }
    }

    private void exportarJson() {
        String tipo = (String) panel.getCbTipoReporte().getSelectedItem();
        String ruta = "reporte_" + System.currentTimeMillis() + ".json";

        if (PanelReportes.REPORTE_CONSOLIDADO.equals(tipo)) {
            LocalDate fecha = obtenerFechaSpinner();
            ReporteConsolidadoDiarioDTO consolidado = gestion.generarConsolidadoDiario(fecha);
            if (consolidado != null && ExportadorDatos.exportarConsolidadoDiario(consolidado, ruta)) {
                JOptionPane.showMessageDialog(panel, "JSON exportado: " + ruta);
                return;
            }
        } else {
            if (ExportadorDatos.exportarTablaAJson(panel.getModeloTabla(), tipo, ruta)) {
                JOptionPane.showMessageDialog(panel, "JSON exportado: " + ruta);
                return;
            }
        }
        JOptionPane.showMessageDialog(panel, "No se pudo exportar el reporte.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private LocalDate obtenerFechaSpinner() {
        Date valor = (Date) panel.getSpFechaReporte().getValue();
        return Instant.ofEpochMilli(valor.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (CMD_GENERAR.equals(comando)) {
            generarReporte();
        } else if (CMD_EXPORTAR_JSON.equals(comando)) {
            exportarJson();
        } else if (CMD_CAMBIAR_TIPO.equals(comando)) {
            cambiarTipoReporte();
        }
    }
}
