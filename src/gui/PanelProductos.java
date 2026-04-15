package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class PanelProductos extends JPanel {

	private JTable tabla;
	private DefaultTableModel modelo;
	
    private JButton btnRegistrar;
    private JButton btnModificar;
    private JButton btnInactivar;
    private JButton btnConsultar;
    private JButton btnHistorial;
    private JButton btnVolver;

    public PanelProductos(ActionListener evento) {

        setLayout(new BorderLayout());

     
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 10, 10));

        btnRegistrar = new JButton("Registrar Producto");
        btnModificar = new JButton("Modificar Producto");
        btnInactivar = new JButton("Inactivar Producto");
        btnConsultar = new JButton("Consultar Producto");
        btnHistorial = new JButton("Historial");
        btnVolver = new JButton("Volver");

        btnRegistrar.setActionCommand(Evento.REGISTRAR_PRODUCTO);
        btnModificar.setActionCommand(Evento.MODIFICAR_PRODUCTO);
        btnInactivar.setActionCommand(Evento.INACTIVAR_PRODUCTO);
        btnConsultar.setActionCommand(Evento.CONSULTAR_PRODUCTO);
        btnHistorial.setActionCommand(Evento.HISTORIAL_PRODUCTO);
        btnVolver.setActionCommand(Evento.VOLVER_PRODUCTOS);

        btnRegistrar.addActionListener(evento);
        btnModificar.addActionListener(evento);
        btnInactivar.addActionListener(evento);
        btnConsultar.addActionListener(evento);
        btnHistorial.addActionListener(evento);
        btnVolver.addActionListener(evento);

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
        modelo.addColumn("Precio");
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