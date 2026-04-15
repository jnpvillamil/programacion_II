package guisubpanel;

import javax.swing.*;

import gui.Evento;

import java.awt.*;
import java.awt.event.ActionListener;

public class PanelRegistrarProveedor extends JPanel {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtNit;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtEmail;

    private JButton btnGuardar;
    private JButton btnVolver;

    public PanelRegistrarProveedor(ActionListener evento) {

        setLayout(new GridLayout(7, 2, 10, 10));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtNit = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtEmail = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnVolver = new JButton("Volver");

        btnGuardar.setActionCommand(Evento.GUARDAR_PROVEEDOR);
        btnVolver.setActionCommand(Evento.CANCELAR_PROVEEDOR);

        btnGuardar.addActionListener(evento);
        btnVolver.addActionListener(evento);

        add(new JLabel("Código"));
        add(txtCodigo);

        add(new JLabel("Nombre / Razón Social"));
        add(txtNombre);

        add(new JLabel("NIT"));
        add(txtNit);

        add(new JLabel("Dirección"));
        add(txtDireccion);

        add(new JLabel("Teléfono"));
        add(txtTelefono);

        add(new JLabel("Email"));
        add(txtEmail);

        add(btnGuardar);
        add(btnVolver);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtNit() {
        return txtNit;
    }

    public JTextField getTxtDireccion() {
        return txtDireccion;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtNit.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }
}