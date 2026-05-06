package co.uptc.edu.co.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class PanelVenta extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Ventas";
    private static final String TEXTO_TOTAL_INICIAL = "Total de ventas: 0";
    private static final String TEXTO_TOTAL = "Total de ventas: ";

    private static final String OPCION_TODOS = "Todos";

    private static final String[] COLUMNAS = {
            "N° Factura",
            "Fecha",
            "Hora",
            "Cliente",
            "Forma de Pago",
            "Impuestos",
            "Total",
            "Estado"
    };

    private JButton botonNuevaVenta;
    private JButton botonAnularVenta;
    private JButton botonRegistrarDevolucion;
    private JButton botonVerDetalle;
    private JButton botonFactura;

    private JTextField campoBuscarFactura;
    
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboFormaPago;
    private JComboBox<String> comboEstado;

    public PanelVenta() {
        super();
        inicializarComponentesVenta();
        configurarPanelVenta();
        agregarComponentesVenta();
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

    private void inicializarComponentesVenta() {
        botonNuevaVenta = new JButton("Nueva Venta");
        botonAnularVenta = new JButton("Anular");
        botonRegistrarDevolucion = new JButton("Devolución");
        botonVerDetalle = new JButton("Ver Detalle");
        botonFactura = new JButton("Factura");

        campoBuscarFactura = new JTextField(20);

        comboCliente = new JComboBox<>();
        comboCliente.addItem(OPCION_TODOS);

        comboFormaPago = new JComboBox<>();
        comboFormaPago.addItem(OPCION_TODOS);
        comboFormaPago.addItem("Efectivo");
        comboFormaPago.addItem("Tarjeta");
        comboFormaPago.addItem("Transferencia");
        comboFormaPago.addItem("Crédito");
        
        comboEstado = new JComboBox<>();
        comboEstado.addItem(OPCION_TODOS);
        comboEstado.addItem("ACTIVA");
        comboEstado.addItem("ANULADA");
        comboEstado.addItem("DEVUELTA");
    }

    private void configurarPanelVenta() {
    	 configurarBotonBase(botonNuevaVenta);
         configurarBotonBase(botonAnularVenta);
         configurarBotonBase(botonRegistrarDevolucion);
         configurarBotonBase(botonVerDetalle);
         configurarBotonBase(botonFactura);
         
    }

    private void agregarComponentesVenta() {
        panelBotones.add(botonNuevaVenta);
        panelBotones.add(botonAnularVenta);
        panelBotones.add(botonRegistrarDevolucion);
        panelBotones.add(botonVerDetalle);
        panelBotones.add(botonFactura);
        
        agregarFiltro("Buscar Factura:", campoBuscarFactura);
        agregarFiltro("Cliente:", comboCliente);
        agregarFiltro("Pago:", comboFormaPago);
        agregarFiltro("Estado:", comboEstado);
       
    }

    public void inicializarEventos(Evento evento) {
    	botonNuevaVenta.setActionCommand(Evento.CMD_NUEVA_VENTA);
        botonNuevaVenta.addActionListener(evento);

        botonAnularVenta.setActionCommand(Evento.CMD_ANULAR_VENTA);
        botonAnularVenta.addActionListener(evento);

        botonRegistrarDevolucion.setActionCommand(Evento.CMD_DEVOLUCION_VENTA);
        botonRegistrarDevolucion.addActionListener(evento);

        botonVerDetalle.setActionCommand(Evento.CMD_VER_DETALLE_VENTA);
        botonVerDetalle.addActionListener(evento);

        botonFactura.setActionCommand(Evento.CMD_FACTURA_VENTA);
        botonFactura.addActionListener(evento);

    }

    public void actualizarTotalVentas(int total) {
        actualizarTextoTotal(TEXTO_TOTAL, total);
    }
    
    
}