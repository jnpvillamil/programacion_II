package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private Color azulOscuro = new Color(13, 38, 64);

    public VentanaLogin() {
        setTitle("Acceso al Sistema");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel pnlHeader = new JPanel();
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setBackground(azulOscuro);
        pnlHeader.setPreferredSize(new Dimension(400, 140));
        
        pnlHeader.add(Box.createVerticalGlue());
        JLabel lblT1 = new JLabel("INICIO DE");
        lblT1.setForeground(Color.WHITE);
        lblT1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblT1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblT2 = new JLabel("SESIÓN");
        lblT2.setForeground(Color.WHITE);
        lblT2.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblT2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pnlHeader.add(lblT1);
        pnlHeader.add(lblT2);
        pnlHeader.add(Box.createVerticalGlue());
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlCentro = new JPanel(new GridBagLayout());
        pnlCentro.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        pnlCentro.add(new JLabel("Usuario:"), gbc);
        txtUsuario = new JTextField();
        txtUsuario.setPreferredSize(new Dimension(280, 35));
        gbc.gridy = 1;
        pnlCentro.add(txtUsuario, gbc);

        gbc.gridy = 2;
        pnlCentro.add(new JLabel("Contraseña:"), gbc);
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(280, 35));
        gbc.gridy = 3;
        pnlCentro.add(txtPassword, gbc);

        btnIngresar = new JButton("INGRESAR");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setBackground(azulOscuro); 
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setOpaque(true);
        btnIngresar.setBorderPainted(false); 
        btnIngresar.setPreferredSize(new Dimension(280, 45));
        
        gbc.gridy = 4;
        gbc.insets = new Insets(30, 40, 10, 40);
        pnlCentro.add(btnIngresar, gbc);

        add(pnlCentro, BorderLayout.CENTER);

        btnIngresar.addActionListener(e -> {
            if (txtUsuario.getText().equals("admin") && new String(txtPassword.getPassword()).equals("12345")) {
                new VentanaPrincipal().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Acceso denegado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}