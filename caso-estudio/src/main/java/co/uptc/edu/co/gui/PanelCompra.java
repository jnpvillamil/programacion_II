package co.uptc.edu.co.gui;

import javax.swing.*;
import java.awt.*;

public class PanelCompra extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Compras";
    private static final String TEXTO_TOTAL_INICIAL = "Total de compras: 0";
    private static final String TEXTO_TOTAL = "Total de compras: ";

    private static final String OPCION_TODOS = "Todos";

    private static final String[] COLUMNAS = {
            "Factura Proveedor",
            "Fecha",
            "Proveedor",
            "Subtotal",
            "Impuestos",
            "Total",
            "Estado"
    };

    private JButton botonNuevaCompra;
    private JButton botonAnular;
    private JButton botonDetalle;

    private JTextField campoBuscar;
    private JComboBox<String> comboProveedor;

    public PanelCompra() {
        super();
        inicializarComponentesCompra();
        configurarPanelCompra();
        agregarComponentesCompra();
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

    private void inicializarComponentesCompra() {
        botonNuevaCompra = new JButton("Nueva Compra");
        botonAnular = new JButton("Anular");
        botonDetalle = new JButton("Ver Detalle");

        campoBuscar = new JTextField(20);

        comboProveedor = new JComboBox<>();
        comboProveedor.addItem(OPCION_TODOS);
     
    }

    private void configurarPanelCompra() {
    	 configurarBotonBase(botonNuevaCompra);
         configurarBotonBase(botonAnular);
         configurarBotonBase(botonDetalle);

    }

    private void agregarComponentesCompra() {
        panelBotones.add(botonNuevaCompra);
        panelBotones.add(botonAnular);
        panelBotones.add(botonDetalle);

        agregarFiltro("Buscar Factura:", campoBuscar);
        agregarFiltro("Proveedor:", comboProveedor);

    }

    public void inicializarEventos(Evento evento) {
    	 botonNuevaCompra.setActionCommand(Evento.CMD_NUEVA_COMPRA);
         botonNuevaCompra.addActionListener(evento);

         botonDetalle.setActionCommand(Evento.CMD_VER_DETALLE_COMPRA);
         botonDetalle.addActionListener(evento);

         botonAnular.setActionCommand(Evento.CMD_ANULAR_COMPRA);
         botonAnular.addActionListener(evento);

    }

    public void actualizarTotalCompras(int total) {
        actualizarTextoTotal(TEXTO_TOTAL, total);
    }
}