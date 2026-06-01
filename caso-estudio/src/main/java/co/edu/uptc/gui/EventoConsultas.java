package co.edu.uptc.gui;

import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.GestionConsultas;
import co.edu.uptc.utilidades.FormateadorMoneda;
import co.edu.uptc.utilidades.ManejadorFechas;

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

public class EventoConsultas implements ActionListener {

    public static final String CMD_CONSULTAR = "CMD_EJECUTAR_CONSULTA";
    public static final String CMD_CAMBIAR_TIPO = "CMD_CAMBIAR_TIPO_CONSULTA";

    private final VentanaPrincipal ventanaPrincipal;
    private final PanelConsultas panel;
    private final GestionConsultas gestion;

    public EventoConsultas(VentanaPrincipal ventanaPrincipal,
                           PanelConsultas panel,
                           GestionConsultas gestion) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panel = panel;
        this.gestion = gestion;

        suscribir(panel.getBtnConsultar(), CMD_CONSULTAR);

        panel.getCbTipoConsulta().setActionCommand(CMD_CAMBIAR_TIPO);
        panel.getCbTipoConsulta().addActionListener(this);
    }

    private void suscribir(javax.swing.JButton boton, String comando) {
        boton.setActionCommand(comando);
        boton.addActionListener(this);
    }

    private void cambiarTipoConsulta() {
        panel.actualizarFiltrosVisibles();
    }

    private void consultar() {
        String tipo = (String) panel.getCbTipoConsulta().getSelectedItem();
        DefaultTableModel modelo = panel.getModeloTabla();
        modelo.setRowCount(0);

        if (PanelConsultas.STOCK_BAJO.equals(tipo)) {
            consultarStockBajo(modelo);
        } else if (PanelConsultas.COMPRAS_PROVEEDOR.equals(tipo)) {
            consultarComprasProveedor(modelo);
        } else if (PanelConsultas.RESUMEN_CONTABLE.equals(tipo)) {
            consultarResumenContable(modelo);
        } else if (PanelConsultas.TOTAL_VENTAS.equals(tipo)) {
            consultarTotalVentas(modelo);
        } else if (PanelConsultas.UTILIDAD_FINANCIERA.equals(tipo)) {
            consultarUtilidadFinanciera(modelo);
        }
    }

    private void consultarStockBajo(DefaultTableModel modelo) {
        modelo.setColumnIdentifiers(new String[]{"Código", "Producto", "Stock", "Stock Mínimo"});
        List<Producto> productos = gestion.consultarStockBajoMinimo();
        for (Producto producto : productos) {
            modelo.addRow(new Object[]{
                    producto.getCodigoProducto(),
                    producto.getNombreProducto(),
                    producto.getStockActual(),
                    producto.getStockMinimo()
            });
        }
    }

    private void consultarComprasProveedor(DefaultTableModel modelo) {
        LocalDate inicio = obtenerFecha(panel.getSpFechaInicio());
        LocalDate fin = obtenerFecha(panel.getSpFechaFin());
        ResultadoOperacion resultado = gestion.validarConsultaComprasProveedor(
                panel.getTxtProveedor().getText(), inicio, fin);
        if (!resultado.isExito()) {
            mostrarAdvertencia(resultado.getMensaje());
            return;
        }

        modelo.setColumnIdentifiers(new String[]{"Factura", "Fecha", "Total", "Proveedor"});
        List<Compra> compras = resultado.getDato();
        for (Compra compra : compras) {
            modelo.addRow(new Object[]{
                    compra.getFacturaProveedor(),
                    ManejadorFechas.formatearFecha(compra.getFecha()),
                    FormateadorMoneda.formatearPeso(compra.getTotalCompra()),
                    compra.getProveedor() != null ? compra.getProveedor().getNombre() : ""
            });
        }
    }

    private void consultarResumenContable(DefaultTableModel modelo) {
        LocalDate inicio = obtenerFecha(panel.getSpFechaInicio());
        LocalDate fin = obtenerFecha(panel.getSpFechaFin());
        ResultadoOperacion validacion = gestion.validarRangoFechas(inicio, fin);
        if (!validacion.isExito()) {
            mostrarAdvertencia(validacion.getMensaje());
            return;
        }

        modelo.setColumnIdentifiers(new String[]{"Cuenta", "Saldo"});
        Map<String, Double> resumen = gestion.consultarResumenContable(inicio, fin);
        for (Map.Entry<String, Double> entry : resumen.entrySet()) {
            modelo.addRow(new Object[]{entry.getKey(), FormateadorMoneda.formatearPeso(entry.getValue())});
        }
    }

    private void consultarTotalVentas(DefaultTableModel modelo) {
        LocalDate fecha = obtenerFecha(panel.getSpFechaInicio());
        String periodo = (String) panel.getCbPeriodo().getSelectedItem();
        double total = gestion.consultarTotalVentas(periodo, fecha);

        modelo.setColumnIdentifiers(new String[]{"Periodo", "Fecha Referencia", "Total Ventas"});
        modelo.addRow(new Object[]{periodo, fecha, FormateadorMoneda.formatearPeso(total)});
    }

    private void consultarUtilidadFinanciera(DefaultTableModel modelo) {
        LocalDate inicio = obtenerFecha(panel.getSpFechaInicio());
        LocalDate fin = obtenerFecha(panel.getSpFechaFin());
        ResultadoOperacion validacion = gestion.validarRangoFechas(inicio, fin);
        if (!validacion.isExito()) {
            mostrarAdvertencia(validacion.getMensaje());
            return;
        }

        ReporteFinancieroDTO reporte = gestion.consultarUtilidadFinanciera(inicio, fin);
        modelo.setColumnIdentifiers(new String[]{"Periodo", "Ingresos", "Egresos", "Utilidad Bruta"});
        modelo.addRow(new Object[]{
                reporte.getPeriodo(),
                FormateadorMoneda.formatearPeso(reporte.getTotalIngresos()),
                FormateadorMoneda.formatearPeso(reporte.getTotalEgresos()),
                FormateadorMoneda.formatearPeso(reporte.getUtilidadBruta())
        });
    }

    private LocalDate obtenerFecha(javax.swing.JSpinner spinner) {
        Date valor = (Date) spinner.getValue();
        return Instant.ofEpochMilli(valor.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(panel, mensaje, "Consulta", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (CMD_CONSULTAR.equals(comando)) {
            consultar();
        } else if (CMD_CAMBIAR_TIPO.equals(comando)) {
            cambiarTipoConsulta();
        }
    }
}
