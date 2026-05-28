package co.edu.uptc.controlador;

import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.gui.PanelConsultas;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.GestionConsultas;
import co.edu.uptc.utilidades.ManejadorFechas;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class ControladorConsultas {

    private final PanelConsultas vista;
    private final GestionConsultas negocio;

    public ControladorConsultas(PanelConsultas vista, GestionConsultas negocio) {
        this.vista = vista;
        this.negocio = negocio;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getCbTipoConsulta().addActionListener(e -> vista.actualizarFiltrosVisibles());
        vista.getBtnConsultar().addActionListener(e -> ejecutarConsulta());
    }

    private void ejecutarConsulta() {
        String tipo = (String) vista.getCbTipoConsulta().getSelectedItem();
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);
        modelo.setColumnCount(0);

        switch (tipo) {
            case PanelConsultas.STOCK_BAJO -> consultarStockBajo(modelo);
            case PanelConsultas.COMPRAS_PROVEEDOR -> consultarComprasProveedor(modelo);
            case PanelConsultas.RESUMEN_CONTABLE -> consultarResumenContable(modelo);
            case PanelConsultas.TOTAL_VENTAS -> consultarTotalVentas(modelo);
            case PanelConsultas.UTILIDAD_FINANCIERA -> consultarUtilidadFinanciera(modelo);
            default -> {
            }
        }
    }

    private void consultarStockBajo(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Código", "Producto", "Stock actual", "Stock mínimo"});
        for (Producto producto : negocio.consultarStockBajoMinimo()) {
            modelo.addRow(new Object[]{
                    producto.getCodigoProducto(),
                    producto.getNombreProducto(),
                    producto.getStockActual(),
                    producto.getStockMinimo()
            });
        }
    }

    private void consultarComprasProveedor(DefaultTableModel modelo) {
        String codigoProveedor = vista.getTxtProveedor().getText();
        if (ValidadorEntradas.esVacio(codigoProveedor)) {
            mostrarAdvertencia("Ingrese el código del proveedor.");
            return;
        }

        LocalDate inicio = obtenerFecha(vista.getSpFechaInicio());
        LocalDate fin = obtenerFecha(vista.getSpFechaFin());
        if (inicio.isAfter(fin)) {
            mostrarAdvertencia("La fecha inicial no puede ser mayor que la final.");
            return;
        }

        modelo.setColumnIdentifiers(new String[]{"Factura", "Fecha", "Total compra", "IVA"});
        for (Compra compra : negocio.consultarComprasPorProveedor(codigoProveedor, inicio, fin)) {
            modelo.addRow(new Object[]{
                    compra.getFacturaProveedor(),
                    ManejadorFechas.formatearFecha(compra.getFecha()),
                    compra.getTotalCompra(),
                    compra.getIva()
            });
        }
    }

    private void consultarResumenContable(DefaultTableModel modelo) {
        LocalDate inicio = obtenerFecha(vista.getSpFechaInicio());
        LocalDate fin = obtenerFecha(vista.getSpFechaFin());
        if (inicio.isAfter(fin)) {
            mostrarAdvertencia("La fecha inicial no puede ser mayor que la final.");
            return;
        }

        modelo.setColumnIdentifiers(new String[]{"Cuenta contable", "Saldo"});
        Map<String, Double> resumen = negocio.consultarResumenContable(inicio, fin);
        for (Map.Entry<String, Double> entry : resumen.entrySet()) {
            modelo.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }

    private void consultarTotalVentas(DefaultTableModel modelo) {
        String periodo = (String) vista.getCbPeriodo().getSelectedItem();
        LocalDate fecha = obtenerFecha(vista.getSpFechaInicio());
        double total = negocio.consultarTotalVentas(periodo, fecha);

        modelo.setColumnIdentifiers(new String[]{"Periodo", "Referencia", "Total ventas"});
        modelo.addRow(new Object[]{periodo, fecha.toString(), total});
    }

    private void consultarUtilidadFinanciera(DefaultTableModel modelo) {
        LocalDate inicio = obtenerFecha(vista.getSpFechaInicio());
        LocalDate fin = obtenerFecha(vista.getSpFechaFin());
        if (inicio.isAfter(fin)) {
            mostrarAdvertencia("La fecha inicial no puede ser mayor que la final.");
            return;
        }

        ReporteFinancieroDTO reporte = negocio.consultarUtilidadFinanciera(inicio, fin);
        modelo.setColumnIdentifiers(new String[]{"Periodo", "Ingresos", "Egresos", "Utilidad bruta"});
        modelo.addRow(new Object[]{
                reporte.getPeriodo(),
                reporte.getTotalIngresos(),
                reporte.getTotalEgresos(),
                reporte.getUtilidadBruta()
        });
    }

    private LocalDate obtenerFecha(JSpinner spinner) {
        Date fecha = (Date) spinner.getValue();
        Instant instante = fecha.toInstant();
        return instante.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
