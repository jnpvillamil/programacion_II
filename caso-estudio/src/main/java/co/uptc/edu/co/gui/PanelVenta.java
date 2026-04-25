package co.uptc.edu.co.gui;

import javax.swing.*;
import java.awt.*;

public class PanelVenta extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Ventas";
    private static final String TEXTO_TOTAL_INICIAL = "Total de ventas: 0";
    private static final String TEXTO_TOTAL = "Total de ventas: ";

    private static final String OPCION_TODOS = "Todos";

    private static final String[] COLUMNAS = {
            "Factura",
            "Fecha",
            "Hora",
            "Cliente",
            "Forma de Pago",
            "Medio de Pago",
            "IVA",
            "Retenciones",
            "Total",
            "Estado"
    };

    private JButton botonNuevaVenta;
    private JButton botonAnularVenta;
    private JButton botonVerDetalle;
    private JButton botonFactura;

    private JTextField campoBuscar;
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboPago;

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
        botonVerDetalle = new JButton("Ver Detalle");
        botonFactura = new JButton("Factura");

        campoBuscar = new JTextField(20);

        comboCliente = new JComboBox<>();
        comboCliente.addItem(OPCION_TODOS);
        comboCliente.addItem("Cliente 1");
        comboCliente.addItem("Cliente 2");

        comboPago = new JComboBox<>();
        comboPago.addItem(OPCION_TODOS);
        comboPago.addItem("Efectivo");
        comboPago.addItem("Tarjeta");
        comboPago.addItem("Transferencia");
    }

    private void configurarPanelVenta() {
        botonNuevaVenta.setBackground(Color.WHITE);
        botonAnularVenta.setBackground(Color.WHITE);
        botonVerDetalle.setBackground(Color.WHITE);
        botonFactura.setBackground(Color.WHITE);
    }

    private void agregarComponentesVenta() {
        panelBotones.add(botonNuevaVenta);
        panelBotones.add(botonAnularVenta);
        panelBotones.add(botonVerDetalle);
        panelBotones.add(botonFactura);

        panelFiltros.add(new JLabel("Buscar Factura:"));
        panelFiltros.add(campoBuscar);

        panelFiltros.add(new JLabel("Cliente:"));
        panelFiltros.add(comboCliente);

        panelFiltros.add(new JLabel("Pago:"));
        panelFiltros.add(comboPago);
    }

    public void inicializarEventos(Evento evento) {
        // por hacer
    }

    public void actualizarTotalVentas(int total) {
        actualizarTextoTotal(TEXTO_TOTAL, total);
    }
}