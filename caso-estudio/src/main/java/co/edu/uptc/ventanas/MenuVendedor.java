package co.edu.uptc.ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class MenuVendedor extends JFrame implements ActionListener {
    
    private JButton btnRegistrarVenta, btnAgregarCliente, btnCerrarSesion;

    public MenuVendedor() {
        setTitle("Panel de Operaciones - Rol VENDEDOR ");
        setSize(420, 360);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(15, 15));

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));

        JLabel lblTitulo = new JLabel("Módulo de Ventas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        
        JLabel lblSubtitulo = new JLabel("Área de trabajo para asesores comerciales:", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 13));

        panelSuperior.add(lblTitulo);
        panelSuperior.add(lblSubtitulo);

     
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 50));

        btnRegistrarVenta = new JButton("Registrar Venta");
        btnRegistrarVenta.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarVenta.addActionListener(this);

        btnAgregarCliente = new JButton("Registrar Nuevo Cliente");
        btnAgregarCliente.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgregarCliente.addActionListener(this);

        panelBotones.add(btnRegistrarVenta);
        panelBotones.add(btnAgregarCliente);

        JPanel panelInferior = new JPanel();
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 25, 10));
        
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCerrarSesion.addActionListener(this);
        panelInferior.add(btnCerrarSesion);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregarCliente) {
            VentanaInsertar vClientes = new VentanaInsertar();
            vClientes.setVisible(true);
            vClientes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
        } else if (e.getSource() == btnRegistrarVenta) {
            VentanaVenta vVentas = new VentanaVenta();
            vVentas.setVisible(true);
            vVentas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
        } else if (e.getSource() == btnCerrarSesion) {
            this.dispose();
            co.edu.uptc.gui.LoginGUI login = new co.edu.uptc.gui.LoginGUI();
            login.setVisible(true);
        }
    }
}