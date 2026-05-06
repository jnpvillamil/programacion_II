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

public class DialogAgregarProductoCompra extends JDialog {

    private JTextField campoCodigo;
    private JTextField campoProducto;
    private JTextField campoCantidad;
    private JTextField campoCostoUnitario;
    private JTextField campoImpuesto;
    private JTextField campoSubtotal;

    private JButton botonAgregar;
    private JButton botonCancelar;

    public DialogAgregarProductoCompra(Frame propietario) {
        super(propietario, "Agregar Producto a la Compra", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
    }

    private void inicializarComponentes() {
        campoCodigo = new JTextField(15);
        campoProducto = new JTextField(15);
        campoCantidad = new JTextField(15);
        campoCostoUnitario = new JTextField(15);
        campoImpuesto = new JTextField(15);
        campoSubtotal = new JTextField(15);

        botonAgregar = new JButton("Agregar");
        botonCancelar = new JButton("Cancelar");
    }

    private void configurarDialogo() {
        setSize(500, 350);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelPrincipal.add(crearPanelFormulario(), BorderLayout.CENTER);
        panelPrincipal.add(crearPanelBotones(), BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private JPanel crearPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del producto"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(campoCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Producto:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(campoProducto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(campoCantidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Costo Unitario:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(campoCostoUnitario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Impuesto:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(campoImpuesto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelFormulario.add(new JLabel("Subtotal:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(campoSubtotal, gbc);

        return panelFormulario;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonAgregar);
        panelBotones.add(botonCancelar);
        return panelBotones;
    }

    public JTextField getCampoCodigo() {
        return campoCodigo;
    }

    public JTextField getCampoProducto() {
        return campoProducto;
    }

    public JTextField getCampoCantidad() {
        return campoCantidad;
    }

    public JTextField getCampoCostoUnitario() {
        return campoCostoUnitario;
    }

    public JTextField getCampoImpuesto() {
        return campoImpuesto;
    }

    public JTextField getCampoSubtotal() {
        return campoSubtotal;
    }

    public JButton getBotonAgregar() {
        return botonAgregar;
    }

    public JButton getBotonCancelar() {
        return botonCancelar;
    }
}