package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogDetalleContable extends JDialog {

    private JTextField campoCodigo;
    private JTextField campoFecha;
    private JTextField campoTipo;
    private JTextField campoCuenta;
    private JTextField campoValor;
    private JTextField campoDescripcion;
    private JTextField campoOrigen;
    private JTextField campoReferencia;

    private JButton botonCerrar;

    public DialogDetalleContable(Frame propietario) {
        super(propietario, "Detalle Movimiento Contable", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos();
    }

    private void inicializarComponentes() {
        campoCodigo = new JTextField(15);
        campoFecha = new JTextField(15);
        campoTipo = new JTextField(15);
        campoCuenta = new JTextField(15);
        campoValor = new JTextField(15);
        campoDescripcion = new JTextField(20);
        campoOrigen = new JTextField(15);
        campoReferencia = new JTextField(15);

        campoCodigo.setEditable(false);
        campoFecha.setEditable(false);
        campoTipo.setEditable(false);
        campoCuenta.setEditable(false);
        campoValor.setEditable(false);
        campoDescripcion.setEditable(false);
        campoOrigen.setEditable(false);
        campoReferencia.setEditable(false);

        botonCerrar = new JButton("Cerrar");
    }

    private void configurarDialogo() {
        setSize(700, 420);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelPrincipal.add(crearPanelDatos(), BorderLayout.CENTER);
        panelPrincipal.add(crearPanelBotones(), BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelDatos() {
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Información del movimiento"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoCodigo, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 3;
        panelDatos.add(campoFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoTipo, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Cuenta:"), gbc);

        gbc.gridx = 3;
        panelDatos.add(campoCuenta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatos.add(new JLabel("Valor:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoValor, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Origen:"), gbc);

        gbc.gridx = 3;
        panelDatos.add(campoOrigen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelDatos.add(new JLabel("Referencia:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoReferencia, gbc);

        gbc.gridx = 2;
        panelDatos.add(new JLabel("Descripción:"), gbc);

        gbc.gridx = 3;
        panelDatos.add(campoDescripcion, gbc);

        return panelDatos;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonCerrar);
        return panelBotones;
    }

    private void inicializarEventos() {
        botonCerrar.addActionListener(e -> dispose());
    }

    public void cargarMovimiento(String codigo, String fecha, String tipo,
            String cuenta, String valor, String descripcion,
            String origen, String referencia) {

        campoCodigo.setText(codigo);
        campoFecha.setText(fecha);
        campoTipo.setText(tipo);
        campoCuenta.setText(cuenta);
        campoValor.setText(valor);
        campoDescripcion.setText(descripcion);
        campoOrigen.setText(origen);
        campoReferencia.setText(referencia);
    }
}