package co.edu.uptc.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import co.edu.uptc.dto.Producto;
import co.edu.uptc.gui.negocio.GestionProducto;

@SuppressWarnings("serial")
public class ProductoGUI extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStock;
    private JTextField txtStockMinimo;

    private JButton btnRegistrar;

    private GestionProducto gestionProducto;

    public ProductoGUI() {

        gestionProducto = new GestionProducto();

        setTitle("Registrar Producto");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtCategoria = new JTextField();
        txtPrecioCompra = new JTextField();
        txtPrecioVenta = new JTextField();
        txtStock = new JTextField();
        txtStockMinimo = new JTextField();

        btnRegistrar = new JButton("Registrar");

        add(new JLabel("Codigo"));
        add(txtCodigo);

        add(new JLabel("Nombre"));
        add(txtNombre);

        add(new JLabel("Categoria"));
        add(txtCategoria);

        add(new JLabel("Precio Compra"));
        add(txtPrecioCompra);

        add(new JLabel("Precio Venta"));
        add(txtPrecioVenta);

        add(new JLabel("Stock"));
        add(txtStock);

        add(new JLabel("Stock Minimo"));
        add(txtStockMinimo);

        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> registrarProducto());

    }

    public void registrarProducto() {

        try {

            Producto producto = new Producto(
                    txtCodigo.getText(),
                    txtNombre.getText(),
                    txtCategoria.getText(),
                    Double.parseDouble(txtPrecioCompra.getText()),
                    Double.parseDouble(txtPrecioVenta.getText()),
                    Integer.parseInt(txtStock.getText()),
                    Integer.parseInt(txtStockMinimo.getText()));

            boolean registrado = gestionProducto.registrarProducto(producto);

            if (registrado) {
                JOptionPane.showMessageDialog(null, "Producto registrado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar producto");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Datos invalidos");
        }

    }

}