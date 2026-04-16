package co.edu.uptc.gui;

import javax.swing.*;

public class PanelProveedores extends JPanel {

    JTextField txtCodigo;
    JTextField txtRazonSocial;
    JTextField txtTelefono;
    JTextField txtCorreo;

    JButton btnGuardar;

    public PanelProveedores(){

        txtCodigo = new JTextField(10);
        txtRazonSocial = new JTextField(10);
        txtTelefono = new JTextField(10);
        txtCorreo = new JTextField(10);

        btnGuardar = new JButton("Guardar");

        add(new JLabel("Código"));
        add(txtCodigo);

        add(new JLabel("Razón Social"));
        add(txtRazonSocial);

        add(new JLabel("Teléfono"));
        add(txtTelefono);

        add(new JLabel("Correo"));
        add(txtCorreo);

        add(btnGuardar);
    }

    public void registrarProveedor(){}

    public void modificarProveedor(){}
}