package co.uptc.edu.tienda.gui;

import javax.swing.*;

import co.uptc.edu.tienda.modelo.Producto;

public class DialogoProducto extends JDialog {

    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStock;
    private JTextField txtStockMinimo;
    private JTextField txtStockMaximo;

    private JButton btnGuardar;
    private JButton btnCerrar;

    private boolean isCrear;
    private int codigoActual;
    private Evento evento;

    public DialogoProducto(Evento evento, String titulo, boolean isCrear) {

        this.evento = evento;
        this.isCrear = isCrear;

        setTitle(titulo);
        setModal(true);
        setLayout(null);
        setSize(400, 500);
        setLocationRelativeTo(null);

        iniciarComponentes();
    }

    private void iniciarComponentes() {

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(150, 30, 180, 25);
        add(txtNombre);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(30, 70, 100, 25);
        add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(150, 70, 180, 25);
        add(txtCategoria);

        JLabel lblPrecioCompra = new JLabel("Precio Compra:");
        lblPrecioCompra.setBounds(30, 110, 120, 25);
        add(lblPrecioCompra);

        txtPrecioCompra = new JTextField();
        txtPrecioCompra.setBounds(150, 110, 180, 25);
        add(txtPrecioCompra);

        JLabel lblPrecioVenta = new JLabel("Precio Venta:");
        lblPrecioVenta.setBounds(30, 150, 120, 25);
        add(lblPrecioVenta);

        txtPrecioVenta = new JTextField();
        txtPrecioVenta.setBounds(150, 150, 180, 25);
        add(txtPrecioVenta);

        JLabel lblStock = new JLabel("Stock:");
        lblStock.setBounds(30, 190, 100, 25);
        add(lblStock);

        txtStock = new JTextField();
        txtStock.setBounds(150, 190, 180, 25);
        add(txtStock);

        JLabel lblStockMinimo = new JLabel("Stock Mínimo:");
        lblStockMinimo.setBounds(30, 230, 120, 25);
        add(lblStockMinimo);

        txtStockMinimo = new JTextField();
        txtStockMinimo.setBounds(150, 230, 180, 25);
        add(txtStockMinimo);

        JLabel lblStockMaximo = new JLabel("Stock Máximo:");
        lblStockMaximo.setBounds(30, 270, 120, 25);
        add(lblStockMaximo);

        txtStockMaximo = new JTextField();
        txtStockMaximo.setBounds(150, 270, 180, 25);
        add(txtStockMaximo);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(80, 340, 100, 30);
        add(btnGuardar);

        btnCerrar = new JButton("Cancelar");
        btnCerrar.setBounds(200, 340, 100, 30);
        add(btnCerrar);

        asignarEventos();
    }

    private void asignarEventos() {

        if (isCrear) {
            btnGuardar.setActionCommand(Evento.GUARDAR_PRD);
        } else {
            btnGuardar.setActionCommand(Evento.EDITAR_PRD);
        }

        btnCerrar.setActionCommand(Evento.CANCELAR_PRD);

        btnGuardar.addActionListener(evento);
        btnCerrar.addActionListener(evento);
    }

    public Producto capturarDatos() {

        int codigo = isCrear ? (int)(Math.random() * 1000) : codigoActual;

        try {

            return new Producto(
                codigo,
                txtNombre.getText(),
                txtCategoria.getText(),
                Double.parseDouble(txtPrecioCompra.getText()),
                Double.parseDouble(txtPrecioVenta.getText()),
                Integer.parseInt(txtStock.getText()),
                Integer.parseInt(txtStockMinimo.getText()),
                Integer.parseInt(txtStockMaximo.getText())
            );

        } catch (Exception e) {

            throw new IllegalArgumentException("Datos inválidos: " + e.getMessage());
        }
    }

    public void cargarDatos(Producto p) {

        codigoActual = p.getCodigoProducto();

        txtNombre.setText(p.getNombreProducto());
        txtCategoria.setText(p.getCategoria());
        txtPrecioCompra.setText(String.valueOf(p.getPrecioCompra()));
        txtPrecioVenta.setText(String.valueOf(p.getPrecioVenta()));
        txtStock.setText(String.valueOf(p.getStockActual()));
        txtStockMinimo.setText(String.valueOf(p.getStockMinimo()));
        txtStockMaximo.setText(String.valueOf(p.getStockMaximo()));
    }
}