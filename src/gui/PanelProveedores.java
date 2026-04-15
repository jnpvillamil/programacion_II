package gui;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modelo.Proveedor;

import java.awt.*;
import java.awt.event.ActionListener;

public class PanelProveedores extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JButton btnRegistrar;
    private JButton btnConsultar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnVolver;

    public PanelProveedores(ActionListener evento) {

        setLayout(new BorderLayout());

      
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("NIT");
        modelo.addColumn("Teléfono");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

     
        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 10, 10));

        btnRegistrar = new JButton("Registrar");
        btnConsultar = new JButton("Consultar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnVolver = new JButton("Volver");

        btnRegistrar.setActionCommand(Evento.REGISTRAR_PROVEEDOR);
        btnConsultar.setActionCommand(Evento.CONSULTAR_PROVEEDOR);
        btnModificar.setActionCommand(Evento.MODIFICAR_PROVEEDOR);
        btnEliminar.setActionCommand(Evento.ELIMINAR_PROVEEDOR);
        btnVolver.setActionCommand(Evento.VOLVER_PROVEEDORES);

        btnRegistrar.addActionListener(evento);
        btnConsultar.addActionListener(evento);
        btnModificar.addActionListener(evento);
        btnEliminar.addActionListener(evento);
        btnVolver.addActionListener(evento);

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);
        JPanel panelVolver = new JPanel();
        panelVolver.add(btnVolver);

        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelVolver, BorderLayout.SOUTH);
    }

    public JTable getTabla() {
        return tabla;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
 
}