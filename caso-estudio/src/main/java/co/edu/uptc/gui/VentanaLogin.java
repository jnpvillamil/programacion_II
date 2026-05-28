package co.edu.uptc.gui;

import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar;

    public VentanaLogin() {
        setTitle("Tienda Minorista");
        setSize(420, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panelPrincipal.setBackground(ConstructorComponentes.GRIS_FONDO);

        JLabel lblTitulo = ConstructorComponentes.crearLabelTitulo("Inicio de sesión");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel panelUsuario = new JPanel(new BorderLayout(0, 6));
        panelUsuario.setOpaque(false);
        panelUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        panelUsuario.add(ConstructorComponentes.crearLabelLogin("Usuario:"), BorderLayout.NORTH);
        txtUsuario = ConstructorComponentes.crearCampoLogin();
        panelUsuario.add(txtUsuario, BorderLayout.CENTER);

        JPanel panelClave = new JPanel(new BorderLayout(0, 6));
        panelClave.setOpaque(false);
        panelClave.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        panelClave.add(ConstructorComponentes.crearLabelLogin("Contraseña:"), BorderLayout.NORTH);
        txtClave = ConstructorComponentes.crearCampoClaveLogin();
        panelClave.add(txtClave, BorderLayout.CENTER);

        btnIngresar = ConstructorComponentes.crearBotonPrimario("INGRESAR");
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(280, 38));

        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(20));
        panelPrincipal.add(panelUsuario);
        panelPrincipal.add(Box.createVerticalStrut(12));
        panelPrincipal.add(panelClave);
        panelPrincipal.add(Box.createVerticalStrut(24));
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

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtClave.setText("");
    }
}
