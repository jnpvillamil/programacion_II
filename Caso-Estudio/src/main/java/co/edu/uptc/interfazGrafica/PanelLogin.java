package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import java.awt.*;

public class PanelLogin extends JPanel {
    
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private Evento evento;
    
    public PanelLogin(Evento evento) {
        this.evento = evento;
        setLayout(new GridBagLayout());
        setBackground(Color.GRAY);  
        
        
        JPanel panelInterno = new JPanel();
        panelInterno.setLayout(new BorderLayout(10, 10));
        panelInterno.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),  
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        panelInterno.setBackground(Color.WHITE);
        
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBackground(Color.WHITE);
        
        
        JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.BLACK);
        
        JLabel lblSubtitulo = new JLabel("Inicie sesión para continuar", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubtitulo.setForeground(Color.GRAY);
        
        JPanel panelTitulos = new JPanel();
        panelTitulos.setLayout(new GridLayout(2, 1, 0, 5));
        panelTitulos.setBackground(Color.WHITE);
        panelTitulos.add(lblTitulo);
        panelTitulos.add(lblSubtitulo);
        
        
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        
       
        JLabel lblVacio = new JLabel("");
        
        
        JButton btnLogin = new JButton("INGRESAR");
        btnLogin.setBackground(null);  
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);  
        btnLogin.setActionCommand(Evento.LOGIN);
        btnLogin.addActionListener(evento);
        
        
        panelFormulario.add(lblUsuario);
        panelFormulario.add(txtUsuario);
        panelFormulario.add(lblPassword);
        panelFormulario.add(txtPassword);
        panelFormulario.add(lblVacio);
        panelFormulario.add(btnLogin);
        
    
        panelInterno.add(panelTitulos, BorderLayout.NORTH);
        panelInterno.add(panelFormulario, BorderLayout.CENTER);
        
        
        add(panelInterno);
        
        // Credenciales por defecto
        txtUsuario.setText("admin");
        txtPassword.setText("1234");
    }
    
    public String getUsuario() { return txtUsuario.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
}