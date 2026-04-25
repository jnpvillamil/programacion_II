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
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.enums.CategoriaProductoEnum;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class DialogProducto extends JDialog {

    private JTextField campoCodigo;
    private JTextField campoNombre;
    private JComboBox<CategoriaProductoEnum> comboCategoria;
    private JTextField campoPrecioVenta;
    private JTextField campoPrecioCompra;
    private JTextField campoStockActual;
    private JTextField campoStockMinimo;
    private JTextField campoStockMaximo;

    private JButton botonGuardar;
    private JButton botonCancelar;

    private boolean modoEdicion;

    public DialogProducto(Frame propietario) {
        this(propietario, null);
    }

    public DialogProducto(Frame propietario, Evento evento) {
        super(propietario, "Registrar Producto", true);
        modoEdicion = false;
        inicializarComponentes();
        configurarDialogo();
        agregarComponentes();
        inicializarEventos(evento);
    }

    private void inicializarComponentes() {
        campoCodigo = new JTextField(25);
        campoNombre = new JTextField(25);

        comboCategoria = new JComboBox<>(CategoriaProductoEnum.values());

        campoPrecioVenta = new JTextField(25);
        campoPrecioCompra = new JTextField(25);
        campoStockActual = new JTextField(25);
        campoStockMinimo = new JTextField(25);
        campoStockMaximo = new JTextField(25);

        botonGuardar = new JButton("Guardar");
        botonCancelar = new JButton("Cancelar");

        botonGuardar.setBackground(new Color(46, 125, 50));
        botonGuardar.setForeground(Color.WHITE);

        botonCancelar.setBackground(new Color(220, 220, 220));
    }

    private void configurarDialogo() {
        setSize(420, 590);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 25, 15));

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
        panelPrincipal.add(new JLabel("Nombre:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoNombre, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Categoría:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(comboCategoria, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Precio de venta:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoPrecioVenta, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Precio de compra:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoPrecioCompra, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Stock actual:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoStockActual, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Stock mínimo:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoStockMinimo, gbc);

        gbc.gridy++;
        panelPrincipal.add(new JLabel("Stock máximo:"), gbc);

        gbc.gridy++;
        panelPrincipal.add(campoStockMaximo, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(22, 0, 10, 0);
        panelPrincipal.add(panelBotones, gbc);

        add(panelPrincipal);
    }

    private void inicializarEventos(Evento evento) {
        botonCancelar.addActionListener(e -> dispose());

        if (evento != null) {
            botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_PRODUCTO);
            botonGuardar.addActionListener(evento);
        } else {
            botonGuardar.addActionListener(e -> {
                try {
                    Producto producto = obtenerProducto();
                    JOptionPane.showMessageDialog(
                            this,
                            "Producto válido: " + producto.getNombreProducto(),
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            ex.getMessage(),
                            "Error de validación",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            });
        }
    }

    public void configurarModoEdicion() {
        modoEdicion = true;
        setTitle("Editar Producto");
        botonGuardar.setText("Actualizar");
        botonGuardar.setActionCommand(Evento.CMD_CONFIRMAR_EDICION_PRODUCTO);
        campoCodigo.setEditable(false);
    }

    public Producto obtenerProducto() throws Exception {
        String codigo = campoCodigo.getText().trim();
        String nombre = campoNombre.getText().trim();
        CategoriaProductoEnum categoria = (CategoriaProductoEnum) comboCategoria.getSelectedItem();

        String textoPrecioVenta = campoPrecioVenta.getText().trim();
        String textoPrecioCompra = campoPrecioCompra.getText().trim();
        String textoStockActual = campoStockActual.getText().trim();
        String textoStockMinimo = campoStockMinimo.getText().trim();
        String textoStockMaximo = campoStockMaximo.getText().trim();

        validarCamposObligatorios(
                codigo,
                nombre,
                textoPrecioVenta,
                textoPrecioCompra,
                textoStockActual,
                textoStockMinimo,
                textoStockMaximo
        );

        double precioVenta = convertirDouble(textoPrecioVenta, "El precio de venta debe ser numérico.");
        double precioCompra = convertirDouble(textoPrecioCompra, "El precio de compra debe ser numérico.");
        int stockActual = convertirEntero(textoStockActual, "El stock actual debe ser un número entero.");
        int stockMinimo = convertirEntero(textoStockMinimo, "El stock mínimo debe ser un número entero.");
        int stockMaximo = convertirEntero(textoStockMaximo, "El stock máximo debe ser un número entero.");

        EstadoEnum estado = EstadoEnum.ACTIVO;

        return new Producto(
                codigo,
                nombre,
                categoria,
                precioCompra,
                precioVenta,
                stockActual,
                stockMinimo,
                stockMaximo,
                estado
        );
    }

    private void validarCamposObligatorios(
            String codigo,
            String nombre,
            String precioVenta,
            String precioCompra,
            String stockActual,
            String stockMinimo,
            String stockMaximo
    ) throws Exception {

        if (codigo.isEmpty()) {
            throw new Exception("El código es obligatorio.");
        }

        if (nombre.isEmpty()) {
            throw new Exception("El nombre del producto es obligatorio.");
        }

        if (precioVenta.isEmpty()) {
            throw new Exception("El precio de venta es obligatorio.");
        }

        if (precioCompra.isEmpty()) {
            throw new Exception("El precio de compra es obligatorio.");
        }

        if (stockActual.isEmpty()) {
            throw new Exception("El stock actual es obligatorio.");
        }

        if (stockMinimo.isEmpty()) {
            throw new Exception("El stock mínimo es obligatorio.");
        }

        if (stockMaximo.isEmpty()) {
            throw new Exception("El stock máximo es obligatorio.");
        }
    }

    private double convertirDouble(String texto, String mensajeError) throws Exception {
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new Exception(mensajeError);
        }
    }

    private int convertirEntero(String texto, String mensajeError) throws Exception {
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new Exception(mensajeError);
        }
    }

    public void cargarProducto(Producto producto) {
        campoCodigo.setText(producto.getCodigoProducto());
        campoNombre.setText(producto.getNombreProducto());
        comboCategoria.setSelectedItem(producto.getCategoria());
        campoPrecioVenta.setText(String.valueOf(producto.getPrecioVenta()));
        campoPrecioCompra.setText(String.valueOf(producto.getPrecioCompra()));
        campoStockActual.setText(String.valueOf(producto.getStockActual()));
        campoStockMinimo.setText(String.valueOf(producto.getStockMinimo()));
        campoStockMaximo.setText(String.valueOf(producto.getStockMaximo()));
    }
}