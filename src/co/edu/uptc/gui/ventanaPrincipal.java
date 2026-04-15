package co.edu.uptc.gui;

import java.awt.*;
import javax.swing.*;

public class ventanaPrincipal extends JFrame {

    public JButton botonClientes;
    public JButton botonProductos;
    public JButton botonProveedores;
    public JButton botonVentas;
    public JButton botonCompras;      // NUEVO
    public JButton botonReportes;
    public JButton botonSalir;
    public JPanel  panelMenu;
    public JPanel  panelContenido;

    private Eventos evento;

    public ventanaPrincipal() {
        setTitle("SGCC - Tienda Minorista");
        setSize(450, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Grid 2x3 → ahora 2x3 también pero con Compras
        panelMenu = new JPanel(new GridLayout(2, 3, 10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        botonClientes    = new JButton(Eventos.CLIENTES);
        botonProductos   = new JButton(Eventos.PRODUCTOS);
        botonProveedores = new JButton(Eventos.PROVEEDORES);
        botonVentas      = new JButton(Eventos.VENTAS);
        botonCompras     = new JButton(Eventos.COMPRAS);   // NUEVO
        botonReportes    = new JButton(Eventos.REPORTES);
        botonSalir       = new JButton(Eventos.SALIR);

        panelMenu.add(botonClientes);
        panelMenu.add(botonProductos);
        panelMenu.add(botonProveedores);
        panelMenu.add(botonVentas);
        panelMenu.add(botonCompras);    // NUEVO
        panelMenu.add(botonReportes);
        // Salir en su propio panel abajo
        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSalir.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        panelSalir.add(botonSalir);

        panelContenido = new JPanel(new BorderLayout());
        panelContenido.add(panelMenu,   BorderLayout.CENTER);
        panelContenido.add(panelSalir,  BorderLayout.SOUTH);

        add(panelContenido, BorderLayout.CENTER);

        // SIN login — se abre directo
        evento = new Eventos(this);

        botonClientes.addActionListener(evento);
        botonProductos.addActionListener(evento);
        botonProveedores.addActionListener(evento);
        botonVentas.addActionListener(evento);
        botonCompras.addActionListener(evento);   // NUEVO
        botonReportes.addActionListener(evento);
        botonSalir.addActionListener(evento);
    }

    public static void main(String[] args) {
        ventanaPrincipal ventana = new ventanaPrincipal();
        ventana.setVisible(Boolean.TRUE);
    }
}