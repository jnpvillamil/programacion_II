package co.uptc.edu.co.gui.dialog;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class DialogRegistrarCompra extends JDialog {

    private JTextField campoFacturaProveedor;
    private JTextField campoFecha;
    private JComboBox<String> comboProveedor;
    private JTextField campoProducto;
    private JTextField campoCantidad;
    private JTextField campoCostoUnitario;
    private JTextField campoImpuestos;
    private JTextField campoTotal;

    private JButton botonGuardar;
    private JButton botonCancelar;

    public DialogRegistrarCompra(Frame propietario) {
        this(propietario, null);
    }

    public DialogRegistrarCompra(Frame propietario, Evento evento) {
        super(propietario, "Registrar Compra", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos(evento);
    }

    private void inicializarComponentes() {
        campoFacturaProveedor = new JTextField(25);
        campoFecha = new JTextField(25);

        comboProveedor = new JComboBox<>();
        comboProveedor.addItem("Seleccione proveedor");

        campoProducto = new JTextField(25);
        campoCantidad = new JTextField(25);
        campoCostoUnitario = new JTextField(25);
        campoImpuestos = new JTextField(25);
        campoTotal = new JTextField(25);

        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");

        botonGuardar.setBackground(new Color(46, 125, 50));
        botonGuardar.setForeground(Color.WHITE);

        botonCancelar.setBackground(new Color(220, 220, 220));
    }

    private void configurarDialogo() {
        setSize(420, 620);
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

        panelPrincipal.add(new JLabel("Factura del proveedor:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoFacturaProveedor, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Fecha:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoFecha, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Proveedor:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(comboProveedor, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Producto:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoProducto, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Cantidad:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoCantidad, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Costo unitario:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoCostoUnitario, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Impuestos:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoImpuestos, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Total:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoTotal, gbc);

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
            botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_REGISTRO_COMPRA);
            botonGuardar.addActionListener(evento);
        } else {
            botonGuardar.addActionListener(e -> {
                JOptionPane.showMessageDialog(
                        this,
                        "Formulario de compra abierto correctamente.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
        }
    }
}