package co.edu.uptc.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class MenuPrincipal extends JFrame implements ActionListener {

    private JButton btnClientes, btnProveedores, btnProductos, btnReporteVentas, btnSalir;

    public MenuPrincipal() {
        setTitle("Panel de Control - Sistema de Gestión");
        setSize(450, 470); 
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        iniciarComponentes();
    }

    private void iniciarComponentes() {
 
        setLayout(new BorderLayout(15, 15));

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));

        JLabel lblTitulo = new JLabel("Menú de Administración", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        
        JLabel lblSubtitulo = new JLabel("Seleccione el módulo que desea gestionar:", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));

        panelSuperior.add(lblTitulo);
        panelSuperior.add(lblSubtitulo);

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));

        btnClientes = new JButton("Gestionar Clientes");
        btnClientes.setFont(new Font("Arial", Font.BOLD, 14));
        btnClientes.addActionListener(this);

        btnProveedores = new JButton("Gestionar Proveedores");
        btnProveedores.setFont(new Font("Arial", Font.BOLD, 14));
        btnProveedores.addActionListener(this);

        btnProductos = new JButton("Gestionar Productos e Inventario");
        btnProductos.setFont(new Font("Arial", Font.BOLD, 14));
        btnProductos.addActionListener(this);

        btnReporteVentas = new JButton("Ver Reporte de Ventas");
        btnReporteVentas.setFont(new Font("Arial", Font.BOLD, 14));
        btnReporteVentas.addActionListener(this);

        panelBotones.add(btnClientes);
        panelBotones.add(btnProveedores);
        panelBotones.add(btnProductos);
        panelBotones.add(btnReporteVentas); 

        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 25, 10));
        
        btnSalir = new JButton("Cerrar Sesión");
        btnSalir.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSalir.addActionListener(this);
        panelInferior.add(btnSalir);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClientes) {
            VentanaInsertar vClientes = new VentanaInsertar();
            vClientes.setVisible(true);
            vClientes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else if (e.getSource() == btnProveedores) {
            VentanaProveedor vProveedores = new VentanaProveedor();
            vProveedores.setVisible(true);
            vProveedores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else if (e.getSource() == btnProductos) {
            VentanaProducto vProductos = new VentanaProducto();
            vProductos.setVisible(true);
            vProductos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else if (e.getSource() == btnReporteVentas) {
            VentanaReporteVentas vReporte = new VentanaReporteVentas();
            vReporte.setVisible(true);
            vReporte.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else if (e.getSource() == btnSalir) {
            this.dispose();
            co.edu.uptc.gui.LoginGUI login = new co.edu.uptc.gui.LoginGUI();
            login.setVisible(true);
        }
    }
}