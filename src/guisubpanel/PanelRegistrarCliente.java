package guisubpanel;

import javax.swing.*;

import gui.Evento;

import java.awt.*;
import java.awt.event.ActionListener;

public class PanelRegistrarCliente extends JPanel {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtTipoIdentificacion;
    private JTextField txtNumeroIdentificacion;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtTipoCliente;

    private JButton btnGuardar;
    private JButton btnCancelar;

    public PanelRegistrarCliente(ActionListener evento) {

        setLayout(new GridLayout(8, 2, 10, 10));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtTipoIdentificacion = new JTextField();
        txtNumeroIdentificacion = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtTipoCliente = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        btnGuardar.setActionCommand(Evento.GUARDAR_CLIENTE);
        btnCancelar.setActionCommand(Evento.CANCELAR_CLIENTE);

        btnGuardar.addActionListener(evento);
        btnCancelar.addActionListener(evento);

        add(new JLabel("Código"));
        add(txtCodigo);

        add(new JLabel("Nombre / Razón Social"));
        add(txtNombre);

        add(new JLabel("Tipo Identificación"));
        add(txtTipoIdentificacion);

        add(new JLabel("Número Identificación"));
        add(txtNumeroIdentificacion);

        add(new JLabel("Dirección"));
        add(txtDireccion);

        add(new JLabel("Teléfono"));
        add(txtTelefono);

        add(new JLabel("Tipo Cliente"));
        add(txtTipoCliente);

        add(btnGuardar);
        add(btnCancelar);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtTipoIdentificacion() {
        return txtTipoIdentificacion;
    }

    public JTextField getTxtNumeroIdentificacion() {
        return txtNumeroIdentificacion;
    }

    public JTextField getTxtDireccion() {
        return txtDireccion;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtTipoCliente() {
        return txtTipoCliente;
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtTipoIdentificacion.setText("");
        txtNumeroIdentificacion.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtTipoCliente.setText("");
    }
}