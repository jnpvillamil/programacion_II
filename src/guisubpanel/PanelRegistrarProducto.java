package guisubpanel;

import javax.swing.*;

import gui.Evento;

import java.awt.*;
import java.awt.event.ActionListener;

public class PanelRegistrarProducto extends JPanel {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtCategoria;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStockActual;
    private JTextField txtStockMinimo;

    private JButton btnGuardar;
    private JButton btnCancelar;

    public PanelRegistrarProducto(ActionListener evento) {

        setLayout(new GridLayout(8, 2, 10, 10));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtCategoria = new JTextField();
        txtPrecioCompra = new JTextField();
        txtPrecioVenta = new JTextField();
        txtStockActual = new JTextField();
        txtStockMinimo = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        btnGuardar.setActionCommand(Evento.GUARDAR_PRODUCTO);
        btnCancelar.setActionCommand(Evento.CANCELAR_PRODUCTO);

        btnGuardar.addActionListener(evento);
        btnCancelar.addActionListener(evento);

        add(new JLabel("Código"));
        add(txtCodigo);

        add(new JLabel("Nombre"));
        add(txtNombre);

        add(new JLabel("Categoría"));
        add(txtCategoria);

        add(new JLabel("Precio Compra"));
        add(txtPrecioCompra);

        add(new JLabel("Precio Venta"));
        add(txtPrecioVenta);

        add(new JLabel("Stock Actual"));
        add(txtStockActual);

        add(new JLabel("Stock Mínimo"));
        add(txtStockMinimo);

        add(btnGuardar);
        add(btnCancelar);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtCategoria() {
        return txtCategoria;
    }

    public JTextField getTxtPrecioCompra() {
        return txtPrecioCompra;
    }

    public JTextField getTxtPrecioVenta() {
        return txtPrecioVenta;
    }

    public JTextField getTxtStockActual() {
        return txtStockActual;
    }

    public JTextField getTxtStockMinimo() {
        return txtStockMinimo;
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtStockActual.setText("");
        txtStockMinimo.setText("");
    }
}