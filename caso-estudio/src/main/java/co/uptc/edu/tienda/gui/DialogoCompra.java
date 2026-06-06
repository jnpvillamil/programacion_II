package co.uptc.edu.tienda.gui;

import javax.swing.*;
import java.awt.*;

public class DialogoCompra extends JDialog {

    private JTextField txtId;
    private JTextField txtProveedor;
    private JTextField txtProducto;
    private JTextField txtCantidad;
    private JTextField txtPrecio;

    private JButton btnGuardar;

    public DialogoCompra(JFrame padre) {

        super(padre, true);

        setTitle("Compra Proveedor");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("ID"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Proveedor"));
        txtProveedor = new JTextField();
        add(txtProveedor);

        add(new JLabel("Producto"));
        txtProducto = new JTextField();
        add(txtProducto);

        add(new JLabel("Cantidad"));
        txtCantidad = new JTextField();
        add(txtCantidad);

        add(new JLabel("Precio"));
        txtPrecio = new JTextField();
        add(txtPrecio);

        btnGuardar = new JButton("Guardar");
        add(btnGuardar);
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }
}