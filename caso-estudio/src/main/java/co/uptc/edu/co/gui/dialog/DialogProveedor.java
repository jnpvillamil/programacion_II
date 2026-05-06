package co.uptc.edu.co.gui.dialog;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import co.uptc.edu.co.gui.Evento;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class DialogProveedor extends JDialog {

    private JTextField campoCodigo;
    private JTextField campoRazonSocial;
    private JTextField campoNit;
    private JTextField campoDireccion;
    private JTextField campoTelefono;
    private JTextField campoCorreoElectronico;

    private JButton botonGuardar;
    private JButton botonCancelar;

    private boolean modoEdicion;
    private EstadoEnum estadoActual;
    
    public DialogProveedor(Frame propietario) {
        this(propietario, null);
    }

    public DialogProveedor(Frame propietario, Evento evento) {
        super(propietario, "Registrar Proveedor", true);
        modoEdicion = false;
        estadoActual = EstadoEnum.ACTIVO;
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos(evento);
    }

    private void inicializarComponentes() {
        campoCodigo = new JTextField(25);
        campoRazonSocial = new JTextField(25);
        campoNit = new JTextField(25);
        campoDireccion = new JTextField(25);
        campoTelefono = new JTextField(25);
        campoCorreoElectronico = new JTextField(25);

        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");

        botonGuardar.setBackground(new Color(46, 125, 50));
        botonGuardar.setForeground(Color.WHITE);

        botonCancelar.setBackground(new Color(220, 220, 220));
    }

    private void configurarDialogo() {
        setSize(420, 500);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 4, 0);

        panelPrincipal.add(new JLabel("Código:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoCodigo, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Razón social:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoRazonSocial, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("NIT:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoNit, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Dirección:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoDireccion, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Teléfono:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoTelefono, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Correo electrónico:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoCorreoElectronico, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        panelPrincipal.add(panelBotones, gbc);

        add(panelPrincipal);
    }

    private void inicializarEventos(Evento evento) {
        botonCancelar.addActionListener(e -> dispose());

        if (evento != null) {
            botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_PROVEEDOR);
            botonGuardar.addActionListener(evento);
        } else {
            botonGuardar.addActionListener(e -> {
                JOptionPane.showMessageDialog(
                        this,
                        "Formulario de proveedor abierto correctamente.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
        }
    }

    public void configurarModoEdicion() {
        modoEdicion = true;
        setTitle("Editar Proveedor");
        botonGuardar.setText("Actualizar");
        botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_EDICION_PROVEEDOR);
        campoCodigo.setEditable(false);
    }
    public Proveedor obtenerProveedor() throws Exception {
        validarCampos();

        EstadoEnum estado = modoEdicion ? estadoActual : EstadoEnum.ACTIVO;

        return new Proveedor(
                campoCodigo.getText().trim(),
                campoRazonSocial.getText().trim(),
                campoNit.getText().trim(),
                campoDireccion.getText().trim(),
                campoTelefono.getText().trim(),
                campoCorreoElectronico.getText().trim(),
                estado
        );
    }

public void cargarProveedor(Proveedor proveedor) {
    campoCodigo.setText(proveedor.getCodigoProveedor());
    campoRazonSocial.setText(proveedor.getRazonSocial());
    campoNit.setText(proveedor.getNit());
    campoDireccion.setText(proveedor.getDireccion());
    campoTelefono.setText(proveedor.getTelefono());
    campoCorreoElectronico.setText(proveedor.getCorreoElectronico());
    estadoActual = proveedor.getEstado();
}

private void validarCampos() throws Exception {
    if (campoCodigo.getText().trim().isEmpty()) {
        throw new Exception("El código es obligatorio.");
    }

    if (campoRazonSocial.getText().trim().isEmpty()) {
        throw new Exception("La razón social es obligatoria.");
    }

    if (campoNit.getText().trim().isEmpty()) {
        throw new Exception("El NIT es obligatorio.");
    }

    if (campoDireccion.getText().trim().isEmpty()) {
        throw new Exception("La dirección es obligatoria.");
    }

    if (campoTelefono.getText().trim().isEmpty()) {
        throw new Exception("El teléfono es obligatorio.");
    }

    if (campoCorreoElectronico.getText().trim().isEmpty()) {
        throw new Exception("El correo electrónico es obligatorio.");
    }
}
}