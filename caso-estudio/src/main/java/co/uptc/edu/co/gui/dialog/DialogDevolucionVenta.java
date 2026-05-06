package co.uptc.edu.co.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import co.uptc.edu.co.gui.Evento;

public class DialogDevolucionVenta extends JDialog {

    private JTextField campoNumeroFactura;
    private JTextField campoCliente;
    private JTextField campoFecha;
    private JTextField campoTotalVenta;
    private JTextField campoEstado;
    private JTextField campoCantidadDevolucion;
    private JTextField campoValorDevolucion;

    private JComboBox<String> comboProducto;

    private JTextArea areaMotivoDevolucion;

    private JButton botonConfirmarDevolucion;
    private JButton botonCancelar;

    public DialogDevolucionVenta(Frame propietario) {
        this(propietario, null);
    }

    public DialogDevolucionVenta(Frame propietario, Evento evento) {
        super(propietario, "Registrar Devolución", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos(evento);
    }

    private void inicializarComponentes() {
        campoNumeroFactura = new JTextField(20);
        campoCliente = new JTextField(20);
        campoFecha = new JTextField(20);
        campoTotalVenta = new JTextField(20);
        campoEstado = new JTextField(20);
        campoCantidadDevolucion = new JTextField(20);
        campoValorDevolucion = new JTextField(20);

        comboProducto = new JComboBox<>();
        comboProducto.addItem("Seleccione producto");

        areaMotivoDevolucion = new JTextArea(4, 20);
        areaMotivoDevolucion.setLineWrap(true);
        areaMotivoDevolucion.setWrapStyleWord(true);

        botonConfirmarDevolucion = new JButton("Confirmar Devolución");
        botonCancelar = new JButton("Cancelar");

        botonConfirmarDevolucion.setBackground(new Color(30, 60, 100));
        botonConfirmarDevolucion.setForeground(Color.WHITE);

        botonCancelar.setBackground(new Color(220, 220, 220));

        campoNumeroFactura.setEditable(false);
        campoCliente.setEditable(false);
        campoFecha.setEditable(false);
        campoTotalVenta.setEditable(false);
        campoEstado.setEditable(false);
        campoValorDevolucion.setEditable(false);
    }

    private void configurarDialogo() {
        setSize(520, 480);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la Venta"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(new JLabel("N° Factura:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoNumeroFactura, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(new JLabel("Cliente:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatos.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelDatos.add(new JLabel("Total Venta:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoTotalVenta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelDatos.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoEstado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelDatos.add(new JLabel("Producto:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(comboProducto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panelDatos.add(new JLabel("Cantidad a devolver:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoCantidadDevolucion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panelDatos.add(new JLabel("Valor a devolver:"), gbc);

        gbc.gridx = 1;
        panelDatos.add(campoValorDevolucion, gbc);

        JPanel panelMotivo = new JPanel(new BorderLayout(5, 5));
        panelMotivo.setBorder(BorderFactory.createTitledBorder("Motivo de devolución"));
        panelMotivo.add(new JScrollPane(areaMotivoDevolucion), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonConfirmarDevolucion);
        panelBotones.add(botonCancelar);

        panelPrincipal.add(panelDatos, BorderLayout.NORTH);
        panelPrincipal.add(panelMotivo, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void inicializarEventos(Evento evento) {
        botonCancelar.addActionListener(e -> dispose());

        if (evento != null) {
            botonConfirmarDevolucion.setActionCommand(Evento.CMD_GUARDAR_DEVOLUCION_VENTA);
            botonConfirmarDevolucion.addActionListener(evento);
        }
    }

    public void cargarVenta(String numeroFactura, String cliente, String fecha, String totalVenta, String estado) {
        campoNumeroFactura.setText(numeroFactura);
        campoCliente.setText(cliente);
        campoFecha.setText(fecha);
        campoTotalVenta.setText(totalVenta);
        campoEstado.setText(estado);
    }

    public void cargarProductos(String[] productos) {
        comboProducto.removeAllItems();
        comboProducto.addItem("Seleccione producto");

        if (productos != null) {
            for (String producto : productos) {
                comboProducto.addItem(producto);
            }
        }
    }

    public String obtenerNumeroFactura() {
        return campoNumeroFactura.getText().trim();
    }

    public String obtenerProductoSeleccionado() {
        return comboProducto.getSelectedItem() != null
                ? comboProducto.getSelectedItem().toString()
                : "";
    }

    public String obtenerCantidad() {
        return campoCantidadDevolucion.getText().trim();
    }

    public String obtenerMotivoDevolucion() {
        return areaMotivoDevolucion.getText().trim();
    }

    public String obtenerValorDevolucion() {
        return campoValorDevolucion.getText().trim();
    }

    public void setValorDevolucion(String valor) {
        campoValorDevolucion.setText(valor);
    }
}