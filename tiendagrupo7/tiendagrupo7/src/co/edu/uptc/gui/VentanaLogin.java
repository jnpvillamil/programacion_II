package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar;

    public VentanaLogin() {
        setTitle("Tienda Minorista 🩷");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(4, 1, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panelPrincipal.setBackground(ConstructorComponentes.GRIS_FONDO);

        JLabel lblTitulo = ConstructorComponentes.crearLabelTitulo("Inicio de Sesión");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelUsuario = new JPanel(new BorderLayout(5, 5));
        panelUsuario.setOpaque(false);
        panelUsuario.add(new JLabel("Usuario:"), BorderLayout.NORTH);
        txtUsuario = ConstructorComponentes.crearCampoTexto();
        panelUsuario.add(txtUsuario, BorderLayout.CENTER);

        JPanel panelClave = new JPanel(new BorderLayout(5, 5));
        panelClave.setOpaque(false);
        panelClave.add(new JLabel("Contraseña:"), BorderLayout.NORTH);
        txtClave = new JPasswordField();
        txtClave.setBorder(txtUsuario.getBorder());
        panelClave.add(txtClave, BorderLayout.CENTER);

        btnIngresar = ConstructorComponentes.crearBotonPrimario("INGRESAR");

        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(panelUsuario);
        panelPrincipal.add(panelClave);
        panelPrincipal.add(btnIngresar);

        this.add(panelPrincipal);
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }

    public String getUsuario() {
        return txtUsuario.getText();
    }

    public String getClave() {
        return new String(txtClave.getPassword());
    }
}