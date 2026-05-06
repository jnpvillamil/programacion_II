package co.uptc.edu.co.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;


public class PanelContabilidad extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión Contable";
    private static final String TEXTO_TOTAL_INICIAL = "Total de movimientos: 0";
    private static final String TEXTO_TOTAL = "Total de movimientos: ";

    private static final String OPCION_TODOS = "Todos";
    private static final String OPCION_TODAS = "Todas";

    private static final String[] COLUMNAS = {
            "Código Transacción",
            "Fecha",
            "Tipo Movimiento",
            "Cuenta Contable",
            "Valor",
            "Descripción"
    };

    private JButton botonVerDetalle;
    private JTextField campoBuscar;

    private JComboBox<String> comboTipoMovimiento;
    private JComboBox<String> comboCuenta;

    public PanelContabilidad() {
        super();
        inicializarComponentesContabilidad();
        configurarPanelContabilidad();
        agregarComponentesContabilidad();
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

    private void inicializarComponentesContabilidad() {
        botonVerDetalle = new JButton("Ver Detalle");
       
        campoBuscar = new JTextField(20);

        comboTipoMovimiento = new JComboBox<>();
        comboTipoMovimiento.addItem(OPCION_TODOS);
        comboTipoMovimiento.addItem("Ingreso");
        comboTipoMovimiento.addItem("Egreso");

        comboCuenta = new JComboBox<>();
        comboCuenta.addItem(OPCION_TODAS);
        comboCuenta.addItem("Caja");
        comboCuenta.addItem("Bancos");
        comboCuenta.addItem("Inventario");
        comboCuenta.addItem("Ingresos por Ventas");
        comboCuenta.addItem("IVA Generado");
        comboCuenta.addItem("IVA Descontable");
        comboCuenta.addItem("Proveedores");
    }

    private void configurarPanelContabilidad() {
    	 configurarBotonBase(botonVerDetalle);
    }

    private void agregarComponentesContabilidad() {
    	  panelBotones.add(botonVerDetalle);

          agregarFiltro("Buscar:", campoBuscar);
          agregarFiltro("Tipo:", comboTipoMovimiento);
          agregarFiltro("Cuenta:", comboCuenta);
    }

    public void inicializarEventos(Evento evento) {
    	botonVerDetalle.setActionCommand(Evento.CMD_VER_DETALLE_CONTABLE);
 	    botonVerDetalle.addActionListener(evento);

    }

    public void actualizarTotalMovimientos(int total) {
        actualizarTextoTotal(TEXTO_TOTAL, total);
    }
}