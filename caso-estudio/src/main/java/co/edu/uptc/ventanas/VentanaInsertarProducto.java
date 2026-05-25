package co.edu.uptc.ventanas;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class VentanaInsertarProducto extends JInternalFrame {

    private JLabel lblCodigo, lblNombre, lblCategoria, lblPrecioCompra, lblPrecioVenta, lblStockActual, lblStockMinimo;
    private JTextField txtCodigo, txtNombre, txtPrecioCompra, txtPrecioVenta, txtStockActual, txtStockMinimo;
    private JComboBox<String> comboCategoria;
    private JButton btnGuardar;

    public VentanaInsertarProducto() {

        super("Registrar Producto - Moderno", true, true, true, true);
        setSize(480, 520);
        getContentPane().setBackground(new Color(240, 244, 248)); 
        setLayout(null);

        Font fuenteLabel = new Font("Arial", Font.BOLD, 13);

        lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(40, 30, 120, 25);
        lblCodigo.setFont(fuenteLabel);
        add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(180, 30, 240, 28);
        add(txtCodigo);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(40, 80, 120, 25);
        lblNombre.setFont(fuenteLabel);
        add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(180, 80, 240, 28);
        add(txtNombre);

        lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(40, 130, 120, 25);
        lblCategoria.setFont(fuenteLabel);
        add(lblCategoria);

        String[] categorias = {"Seleccione...", "Abarrotes", "Lácteos", "Carnes", "Bebidas", "Limpieza"};
        comboCategoria = new JComboBox<>(categorias);
        comboCategoria.setBounds(180, 130, 240, 28);
        add(comboCategoria);

        lblPrecioCompra = new JLabel("Precio Compra:");
        lblPrecioCompra.setBounds(40, 180, 120, 25);
        lblPrecioCompra.setFont(fuenteLabel);
        add(lblPrecioCompra);

        txtPrecioCompra = new JTextField();
        txtPrecioCompra.setBounds(180, 180, 240, 28);
        add(txtPrecioCompra);

        lblPrecioVenta = new JLabel("Precio Venta:");
        lblPrecioVenta.setBounds(40, 230, 120, 25);
        lblPrecioVenta.setFont(fuenteLabel);
        add(lblPrecioVenta);

        txtPrecioVenta = new JTextField();
        txtPrecioVenta.setBounds(180, 230, 240, 28);
        add(txtPrecioVenta);

        lblStockActual = new JLabel("Stock Actual:");
        lblStockActual.setBounds(40, 280, 120, 25);
        lblStockActual.setFont(fuenteLabel);
        add(lblStockActual);

        txtStockActual = new JTextField();
        txtStockActual.setBounds(180, 280, 240, 28);
        add(txtStockActual);

        lblStockMinimo = new JLabel("Stock Mínimo:");
        lblStockMinimo.setBounds(40, 330, 120, 25);
        lblStockMinimo.setFont(fuenteLabel);
        add(lblStockMinimo);

        txtStockMinimo = new JTextField();
        txtStockMinimo.setBounds(180, 330, 240, 28);
        add(txtStockMinimo);

        btnGuardar = new JButton("Guardar Producto");
        btnGuardar.setBounds(140, 400, 180, 35);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        add(btnGuardar);
    }
}