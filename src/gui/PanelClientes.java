package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelClientes extends JPanel {

    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnInactivar;
    private JButton btnConsultar;
    private JButton btnHistorial;
    private JButton btnVolver;

    private JTable tabla;
    private DefaultTableModel modelo;
    
    public PanelClientes(ActionListener evento) {

        setLayout(new BorderLayout());

    
        btnRegistrar = new JButton("Registrar Cliente");
        btnModificar = new JButton("Modificar Cliente");
        btnInactivar = new JButton("Inactivar Cliente");
        btnConsultar = new JButton("Consultar Cliente");
        btnHistorial = new JButton("Historial Cliente");
        btnVolver = new JButton("Volver");

        btnRegistrar.setActionCommand(Evento.REGISTRAR_CLIENTE);
        btnModificar.setActionCommand(Evento.MODIFICAR_CLIENTE);
        btnInactivar.setActionCommand(Evento.INACTIVAR_CLIENTE);
        btnConsultar.setActionCommand(Evento.CONSULTAR_CLIENTE);
        btnHistorial.setActionCommand(Evento.HISTORIAL_CLIENTE);
        btnVolver.setActionCommand(Evento.VOLVER_CLIENTES);

        btnRegistrar.addActionListener(evento);
        btnModificar.addActionListener(evento);
        btnInactivar.addActionListener(evento);
        btnConsultar.addActionListener(evento);
        btnHistorial.addActionListener(evento);
        btnVolver.addActionListener(evento);

        
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 10, 10));

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnHistorial);
        JPanel panelVolver = new JPanel();
        panelVolver.add(btnVolver);

       
        modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Estado");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

       
        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelVolver, BorderLayout.SOUTH);
    } 
    public DefaultTableModel getModelo() {
        return modelo;
    }
    public JTable getTabla() {
        return tabla;
    }
    }