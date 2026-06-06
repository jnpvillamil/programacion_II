package co.uptc.edu.co.gui;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class PanelEmpleado extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Empleados";
    private static final String TEXTO_TOTAL_INICIAL = "Total de salarios: 0";
    private static final String TEXTO_TOTAL = "Total de salarios: ";

    private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();

    private static final Object[] COLUMNAS = {
            "Salario"
    };

    private List<Double> salariosCargados;

    public PanelEmpleado() {
        super();
        salariosCargados = new ArrayList<>();
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
        return COLUMNAS;
    }

    public void cargarSalarios(List<Double> salarios) {
        salariosCargados = new ArrayList<>(salarios);
        cargarTabla();
    }

    private void cargarTabla() {
        limpiarTabla();

        int total = 0;

        for (Double salarioEmpleado : salariosCargados) {
            Object[] fila = {
                    formatearMoneda(salarioEmpleado)
            };

            modeloTabla.addRow(fila);
            total++;
        }

        actualizarTextoTotal(TEXTO_TOTAL, total);
    }

    private String formatearMoneda(double valor) {
        return FORMATO_MONEDA.format(valor);
    }

    private static DecimalFormat crearFormatoMoneda() {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(',');

        DecimalFormat formato = new DecimalFormat("$ #,##0", simbolos);
        formato.setGroupingUsed(true);
        return formato;
    }
}