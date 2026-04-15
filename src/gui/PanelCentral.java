package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelCentral extends JPanel {

    private JButton btnProductos;
    private JButton btnClientes;
    private JButton btnVentas;
    private JButton btnCompras;
    private JButton btnReportes;
    private JButton btnProveedores;
    private JButton btnSalir;

    public PanelCentral(ActionListener evento) {

        setLayout(new GridLayout(4, 3, 10, 10));

        btnProductos = new JButton("Productos");
        btnClientes = new JButton("Clientes");
        btnVentas = new JButton("Ventas");
        btnCompras = new JButton("Compras");
        btnReportes = new JButton("Reportes");
        btnProveedores = new JButton("Proveedores");
        btnSalir = new JButton("Salir");

        btnProductos.setActionCommand(Evento.PRODUCTOS);
        btnClientes.setActionCommand(Evento.CLIENTES);
        btnVentas.setActionCommand(Evento.VENTAS);
        btnCompras.setActionCommand(Evento.COMPRAS);
        btnReportes.setActionCommand(Evento.REPORTES);
        btnProveedores.setActionCommand(Evento.PROVEEDORES);
        btnSalir.setActionCommand(Evento.SALIR);

        btnProductos.addActionListener(evento);
        btnClientes.addActionListener(evento);
        btnVentas.addActionListener(evento);
        btnCompras.addActionListener(evento);
        btnReportes.addActionListener(evento);
        btnProveedores.addActionListener(evento);
        btnSalir.addActionListener(evento);

        add(btnProductos);
        add(btnClientes);
        add(btnVentas);
        add(btnCompras);
        add(btnReportes);
        add(btnProveedores);
        add(btnSalir);
    }
}
