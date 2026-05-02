package co.edu.uptc.gui;

import java.awt.*;
import javax.swing.*;

public class ventanaPrincipal extends JFrame {

    private Eventos evento;

    public ventanaPrincipal(Eventos evento) {
        this.evento = evento;
        construirVentana();
    }

    private void construirVentana() {
        setTitle("Tienda Minorista");
        setSize(380, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelMenu = new JPanel(new GridLayout(2, 2, 10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JButton bClientes    = new JButton(Eventos.CLIENTES);
        JButton bProductos   = new JButton(Eventos.PRODUCTOS);
        JButton bProveedores = new JButton(Eventos.PROVEEDORES);
        JButton bVentas      = new JButton(Eventos.VENTAS);

        panelMenu.add(bClientes);
        panelMenu.add(bProductos);
        panelMenu.add(bProveedores);
        panelMenu.add(bVentas);

        JPanel panelSalir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSalir.add(new JButton(Eventos.SALIR) {{ addActionListener(evento); }});

        add(panelMenu,  BorderLayout.CENTER);
        add(panelSalir, BorderLayout.SOUTH);

        bClientes.addActionListener(evento);
        bProductos.addActionListener(evento);
        bProveedores.addActionListener(evento);
        bVentas.addActionListener(evento);
    }

    public static void main(String[] args) {
        Eventos e = new Eventos();
        ventanaPrincipal vp = new ventanaPrincipal(e);
        e.setVentanaPrincipal(vp);
        vp.setVisible(true);
    }
}
