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
            "IVA",
            "Total",
            "Estado"
    };

    private JButton botonNuevaCompra;
    private JButton botonEditar;
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
        botonEditar = new JButton("Editar");
        botonAnular = new JButton("Anular");
        botonDetalle = new JButton("Ver Detalle");

        campoBuscar = new JTextField(20);

        comboProveedor = new JComboBox<>();
        comboProveedor.addItem(OPCION_TODOS);
        comboProveedor.addItem("Proveedor 1");
        comboProveedor.addItem("Proveedor 2");
    }

    private void configurarPanelCompra() {
        botonNuevaCompra.setBackground(Color.WHITE);
        botonEditar.setBackground(Color.WHITE);
        botonAnular.setBackground(Color.WHITE);
        botonDetalle.setBackground(Color.WHITE);
    }

    private void agregarComponentesCompra() {
        panelBotones.add(botonNuevaCompra);
        panelBotones.add(botonEditar);
        panelBotones.add(botonAnular);
        panelBotones.add(botonDetalle);

        panelFiltros.add(new JLabel("Buscar Factura:"));
        panelFiltros.add(campoBuscar);
        panelFiltros.add(new JLabel("Proveedor:"));
        panelFiltros.add(comboProveedor);
    }

    public void inicializarEventos(Evento evento) {
        // por hacer
    }

    public void actualizarTotalCompras(int total) {
        actualizarTextoTotal(TEXTO_TOTAL, total);
    }
}