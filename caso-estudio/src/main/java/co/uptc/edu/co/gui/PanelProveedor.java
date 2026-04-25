package co.uptc.edu.co.gui;

import javax.swing.*;
import java.awt.*;

public class PanelProveedor extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Proveedores";
    private static final String TEXTO_TOTAL_INICIAL = "Total de proveedores: 0";
    private static final String TEXTO_TOTAL = "Total de proveedores: ";

    private static final String TEXTO_BOTON_CAMBIAR_ESTADO = "Cambiar Estado";

    private static final String[] COLUMNAS = {
            "Código",
            "Razón Social",
            "NIT",
            "Dirección",
            "Teléfono",
            "Correo Electrónico",
            "Estado"
    };

    private JButton botonNuevo;
    private JButton botonEditar;
    private JButton botonCambiarEstado;
    private JButton botonRegistrarCompra;

    private JTextField campoBuscar;

    public PanelProveedor() {
        super();
        inicializarComponentesProveedor();
        configurarPanelProveedor();
        agregarComponentesProveedor();
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

    private void inicializarComponentesProveedor() {
        botonNuevo = new JButton("Nuevo");
        botonEditar = new JButton("Editar");
        botonCambiarEstado = new JButton(TEXTO_BOTON_CAMBIAR_ESTADO);
        botonRegistrarCompra = new JButton("Registrar Compra");

        campoBuscar = new JTextField(20);
    }

    private void configurarPanelProveedor() {
        botonNuevo.setBackground(Color.WHITE);
        botonEditar.setBackground(Color.WHITE);
        botonCambiarEstado.setBackground(Color.WHITE);
        botonRegistrarCompra.setBackground(Color.WHITE);
    }

    private void agregarComponentesProveedor() {
        panelBotones.add(botonNuevo);
        panelBotones.add(botonEditar);
        panelBotones.add(botonCambiarEstado);
        panelBotones.add(botonRegistrarCompra);

        panelFiltros.add(new JLabel("Buscar:"));
        panelFiltros.add(campoBuscar);
    }

    public void inicializarEventos(Evento evento) {
        botonNuevo.setActionCommand(Evento.CMD_NUEVO_PROVEEDOR);
        botonNuevo.addActionListener(evento);

        botonEditar.setActionCommand(Evento.CMD_EDITAR_PROVEEDOR);
        botonEditar.addActionListener(evento);

        botonCambiarEstado.setActionCommand(Evento.CMD_ESTADO_PROVEEDOR);
        botonCambiarEstado.addActionListener(evento);

        botonRegistrarCompra.setActionCommand(Evento.CMD_REGISTRAR_COMPRA_PROVEEDOR);
        botonRegistrarCompra.addActionListener(evento);
    }

    public void actualizarTotalProveedores(int total) {
        actualizarTextoTotal(TEXTO_TOTAL, total);
    }
}