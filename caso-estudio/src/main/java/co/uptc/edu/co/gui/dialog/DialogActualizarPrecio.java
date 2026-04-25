package co.uptc.edu.co.gui.dialog;

import java.awt.Color;
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

import co.uptc.edu.co.gui.Evento;
import co.uptc.edu.co.modelo.Producto;

public class DialogActualizarPrecio extends JDialog {

    private JTextField campoCodigo;
    private JTextField campoPrecioCompra;
    private JTextField campoPrecioVenta;

    private JButton botonActualizar;
    private JButton botonCancelar;

    public DialogActualizarPrecio(Frame propietario) {
        this(propietario, null);
    }

    public DialogActualizarPrecio(Frame propietario, Evento evento) {
        super(propietario, "Actualizar Precio de Producto", true);
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos(evento);
    }

    private void inicializarComponentes() {
        campoCodigo = new JTextField(25);
        campoPrecioCompra = new JTextField(25);
        campoPrecioVenta = new JTextField(25);

        botonActualizar = new JButton("Actualizar");
        botonCancelar = new JButton("Cancelar");

        botonActualizar.setBackground(new Color(33, 150, 243));
        botonActualizar.setForeground(Color.WHITE);

        botonCancelar.setBackground(new Color(220, 220, 220));
    }

    private void configurarDialogo() {
        setSize(360, 260);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);

        panelPrincipal.add(new JLabel("Código del producto:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(campoCodigo, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 0, 5, 0);
        panelPrincipal.add(new JLabel("Nuevo precio de compra:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelPrincipal.add(campoPrecioCompra, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 0, 5, 0);
        panelPrincipal.add(new JLabel("Nuevo precio de venta:"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        panelPrincipal.add(campoPrecioVenta, gbc);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.add(botonActualizar);
        panelBotones.add(botonCancelar);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 0, 0);
        panelPrincipal.add(panelBotones, gbc);

        add(panelPrincipal);
    }

    private void inicializarEventos(Evento evento) {
        botonCancelar.addActionListener(e -> dispose());

        if (evento != null) {
            botonActualizar.setActionCommand(Evento.CMD_CONFIRMAR_ACTUALIZACION_PRECIO_PRODUCTO);
            botonActualizar.addActionListener(evento);
        } else {
            botonActualizar.addActionListener(e -> dispose());
        }
    }

    public void cargarProducto(Producto producto) {
        campoCodigo.setText(producto.getCodigoProducto());
        campoCodigo.setEditable(false);
        campoPrecioCompra.setText(String.valueOf(producto.getPrecioCompra()));
        campoPrecioVenta.setText(String.valueOf(producto.getPrecioVenta()));
    }

    public String obtenerCodigoProducto() throws Exception {
        String codigo = campoCodigo.getText().trim();

        if (codigo.isEmpty()) {
            throw new Exception("El código del producto es obligatorio.");
        }

        return codigo;
    }

    public double obtenerNuevoPrecioCompra() throws Exception {
        String textoPrecioCompra = campoPrecioCompra.getText().trim();

        if (textoPrecioCompra.isEmpty()) {
            throw new Exception("El precio de compra es obligatorio.");
        }

        try {
            double precioCompra = Double.parseDouble(textoPrecioCompra);

            if (precioCompra < 0) {
                throw new Exception("El precio de compra no puede ser negativo.");
            }

            return precioCompra;
        } catch (NumberFormatException e) {
            throw new Exception("El precio de compra debe ser numérico.");
        }
    }

    public double obtenerNuevoPrecioVenta() throws Exception {
        String textoPrecioVenta = campoPrecioVenta.getText().trim();

        if (textoPrecioVenta.isEmpty()) {
            throw new Exception("El precio de venta es obligatorio.");
        }

        try {
            double precioVenta = Double.parseDouble(textoPrecioVenta);

            if (precioVenta < 0) {
                throw new Exception("El precio de venta no puede ser negativo.");
            }

            return precioVenta;
        } catch (NumberFormatException e) {
            throw new Exception("El precio de venta debe ser numérico.");
        }
    }
}