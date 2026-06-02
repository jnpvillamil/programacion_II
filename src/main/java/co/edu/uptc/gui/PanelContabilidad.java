package co.edu.uptc.gui;

import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.dto.ReporteUtilidadDTO;
import co.edu.uptc.dto.MovimientoResumenDTO;
import co.edu.uptc.interfaces.ManejadorEventoSistema;
import co.edu.uptc.utilidades.ConstructorComponentes;
import co.edu.uptc.utilidades.FormateadorMoneda;
import co.edu.uptc.utilidades.ManejadorFechas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelContabilidad extends JPanel {

    private final ManejadorEventoSistema manejadorEventoSistema;

    private DefaultTableModel modeloMovimiento;
    private DefaultTableModel modeloUtilidad;
    private JLabel lblResumenVenta;
    private JLabel lblUtilidadBruta;
    private JLabel lblBalance;

    public PanelContabilidad(ManejadorEventoSistema manejadorEventoSistema) {
        this.manejadorEventoSistema = manejadorEventoSistema;

        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = ConstructorComponentes.crearEtiquetaNegrita("CONTABILIDAD Y REPORTE FINANCIERO");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirPanelInferior(), BorderLayout.SOUTH);

        cargarMovimiento();
    }

    private JPanel construirPanelCentral() {
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 15, 0));
        panelCentral.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        String[] columnaMovimiento = {"Fecha", "Tipo", "Cuenta", "Valor", "Descripción"};
        modeloMovimiento = new DefaultTableModel(columnaMovimiento, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tablaMovimiento = new JTable(modeloMovimiento);
        ConstructorComponentes.darEstiloTabla(tablaMovimiento);

        String[] columnaUtilidad = {"Producto", "Utilidad Bruta"};
        modeloUtilidad = new DefaultTableModel(columnaUtilidad, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tablaUtilidad = new JTable(modeloUtilidad);
        ConstructorComponentes.darEstiloTabla(tablaUtilidad);

        JPanel panelMovimiento = new JPanel(new BorderLayout(5, 5));
        panelMovimiento.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        panelMovimiento.add(ConstructorComponentes.crearEtiquetaNegrita("Movimiento Contable"), BorderLayout.NORTH);
        panelMovimiento.add(new JScrollPane(tablaMovimiento), BorderLayout.CENTER);

        JPanel panelUtilidad = new JPanel(new BorderLayout(5, 5));
        panelUtilidad.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        panelUtilidad.add(ConstructorComponentes.crearEtiquetaNegrita("Utilidad por Producto"), BorderLayout.NORTH);
        panelUtilidad.add(new JScrollPane(tablaUtilidad), BorderLayout.CENTER);

        panelCentral.add(panelMovimiento);
        panelCentral.add(panelUtilidad);
        return panelCentral;
    }

    private JPanel construirPanelInferior() {
        JPanel panelSur = new JPanel(new BorderLayout(10, 10));
        panelSur.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        JPanel panelResumen = new JPanel(new GridLayout(3, 1, 5, 5));
        panelResumen.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        lblResumenVenta = ConstructorComponentes.crearEtiquetaNegrita(
                "Ventas: Diario $0 | Mensual $0 | Anual $0");
        lblUtilidadBruta = ConstructorComponentes.crearEtiquetaNegrita("Utilidad Bruta del Periodo: $ 0.00");
        lblBalance = ConstructorComponentes.crearEtiquetaNegrita(
                "Balance: Ingreso $0 | Egreso $0 | Saldo Neto $0");
        panelResumen.add(lblResumenVenta);
        panelResumen.add(lblUtilidadBruta);
        panelResumen.add(lblBalance);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        JButton btnReporte = ConstructorComponentes.crearBotonInformativo("Generar Reporte");
        btnReporte.addActionListener(evento -> generarReporte());
        panelBoton.add(btnReporte);

        panelSur.add(panelResumen, BorderLayout.CENTER);
        panelSur.add(panelBoton, BorderLayout.SOUTH);
        return panelSur;
    }

    private void cargarMovimiento() {
        modeloMovimiento.setRowCount(0);
        try {
            for (MovimientoResumenDTO movimiento : manejadorEventoSistema.listarResumenMovimiento()) {
                modeloMovimiento.addRow(new Object[]{
                        ManejadorFechas.formatearFecha(movimiento.fecha()),
                        movimiento.tipoMovimiento(),
                        movimiento.cuenta(),
                        FormateadorMoneda.formatear(movimiento.valor()),
                        movimiento.descripcion()
                });
            }
        } catch (IllegalStateException excepcion) {
            JOptionPane.showMessageDialog(this, excepcion.getMessage(), "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarReporte() {
        try {
            ReporteFinancieroDTO reporte = manejadorEventoSistema.generarConsolidadoJson();
            mostrarReporte(reporte);
            cargarUtilidadDesdeReporte();
            cargarMovimiento();
            JOptionPane.showMessageDialog(this,
                    "Reporte generado en reportes_financieros.json",
                    "Reporte financiero",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException excepcion) {
            JOptionPane.showMessageDialog(this, excepcion.getMessage(), "Error al generar reporte",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarReporte(ReporteFinancieroDTO reporte) {
        if (reporte == null || reporte.resumenPeriodoVenta() == null) {
            reporte = manejadorEventoSistema.leerReporteFinanciero();
        }
        ReporteFinancieroDTO.ResumenPeriodoVentaDTO resumenVenta = reporte.resumenPeriodoVenta();
        ReporteFinancieroDTO.BalanceResumenDTO balance = reporte.balanceResumen();

        lblResumenVenta.setText(String.format(
                "Ventas: Diario %s | Mensual %s | Anual %s",
                FormateadorMoneda.formatear(resumenVenta.totalDiario()),
                FormateadorMoneda.formatear(resumenVenta.totalMensual()),
                FormateadorMoneda.formatear(resumenVenta.totalAnual())));
        lblUtilidadBruta.setText("Utilidad Bruta del Periodo: "
                + FormateadorMoneda.formatear(reporte.utilidadBrutaPeriodo()));
        lblBalance.setText(String.format(
                "Balance: Ingreso %s | Egreso %s | Saldo Neto %s",
                FormateadorMoneda.formatear(balance.totalIngreso()),
                FormateadorMoneda.formatear(balance.totalEgreso()),
                FormateadorMoneda.formatear(balance.saldoNeto())));
    }

    private void cargarUtilidadDesdeReporte() {
        modeloUtilidad.setRowCount(0);
        try {
            for (ReporteUtilidadDTO utilidad : manejadorEventoSistema.listarReporteUtilidad()) {
                modeloUtilidad.addRow(new Object[]{
                        utilidad.nombreProducto(),
                        FormateadorMoneda.formatear(utilidad.utilidadBruta())
                });
            }
        } catch (IllegalStateException excepcion) {
            JOptionPane.showMessageDialog(this, excepcion.getMessage(), "Error al cargar utilidad",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
