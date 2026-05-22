package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;

public class PanelPadreCompra extends JPanel {

    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnActualizar;

    private PanelCompra panelCompra;

    public PanelPadreCompra() {

        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();

        btnAgregar = new JButton("Agregar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        panelCompra = new PanelCompra();

        add(panelBotones, BorderLayout.NORTH);
        add(panelCompra, BorderLayout.CENTER);
    }
}