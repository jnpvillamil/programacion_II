package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelLogin extends JPanel {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public PanelLogin(ActionListener evento) {

        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("Inicio de Sesión"));

        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        btnIngresar = new JButton("Ingresar");

        btnIngresar.setActionCommand(Evento.LOGIN);
        btnIngresar.addActionListener(evento);

        add(new JLabel("Usuario"));
        add(txtUsuario);

        add(new JLabel("Contraseña"));
        add(txtPassword);

        add(new JLabel(""));
        add(btnIngresar);
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }
}