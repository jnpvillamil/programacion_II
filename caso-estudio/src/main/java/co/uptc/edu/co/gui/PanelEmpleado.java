package co.uptc.edu.co.gui;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import co.uptc.edu.co.modelo.Empleado;

public class PanelEmpleado extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Empleados";
    private static final String TEXTO_TOTAL_INICIAL = "Total de salarios: 0";
    private static final String TEXTO_TOTAL = "Total de salarios: ";

    private static final DecimalFormat FORMATO_MONEDA = crearFormatoMoneda();

    private static final Object[] COLUMNAS = {
    		 "Cargo",  "Salario"
    };

    private List<Empleado> empleadosCargados;
    private JButton botonNuevo;

    public PanelEmpleado() {
    	super();
        empleadosCargados = new ArrayList<>();
        inicializarComponentesEmpleado();
        configurarPanelEmpleado();
        agregarComponentesEmpleado();
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

    private void inicializarComponentesEmpleado() {
        botonNuevo = new JButton("Nuevo");
    }

    private void configurarPanelEmpleado() {
        configurarBotonBase(botonNuevo);
    }

    private void agregarComponentesEmpleado() {
        panelBotones.add(botonNuevo);
    }

    public void inicializarEventos(Evento evento) {
        botonNuevo.setActionCommand(Evento.CMD_NUEVO_EMPLEADO);
        botonNuevo.addActionListener(evento);
    }

    public void cargarEmpleados(List<Empleado> empleados) {
        this.empleadosCargados = new ArrayList<>(empleados);
        cargarTabla();
    }
    private void cargarTabla() {
        limpiarTabla();
        int total = 0;

        for (Empleado empleado : empleadosCargados) {
           
            Object[] fila = {
                
                empleado.getCargoEmpleado(),             
                formatearMoneda(empleado.getSalarioEmpleado())
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
