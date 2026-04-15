package guisubpanel;

import javax.swing.*;

import gui.Evento;

import java.awt.*;
import java.awt.event.ActionListener;

public class PanelRegistrarCompra extends JPanel {

    private JTextField txtCodigoCompra;
    private JTextField txtCodigoProveedor;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;

    private JComboBox<String> cbFormaPago;

    private JButton btnCalcular;
    private JButton btnGuardar;
    private JButton btnVolver;

    public PanelRegistrarCompra(ActionListener evento) {

        setLayout(new GridLayout(11, 2, 10, 10));

        txtCodigoCompra = new JTextField();
        txtCodigoProveedor = new JTextField();
        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();
        txtPrecio = new JTextField();
        txtSubtotal = new JTextField();
        txtIva = new JTextField();
        txtTotal = new JTextField();

        cbFormaPago = new JComboBox<>(new String[]{"Efectivo", "Transferencia"});

        btnCalcular = new JButton("Calcular");
        btnGuardar = new JButton("Registrar Compra");
        btnVolver = new JButton("Volver");

        btnCalcular.setActionCommand(Evento.CALCULAR_COMPRA);
        btnGuardar.setActionCommand(Evento.GUARDAR_COMPRA);
        btnVolver.setActionCommand(Evento.CANCELAR_COMPRA);

        btnCalcular.addActionListener(evento);
        btnGuardar.addActionListener(evento);
        btnVolver.addActionListener(evento);

        add(new JLabel("Código Compra"));
        add(txtCodigoCompra);

        add(new JLabel("Código Proveedor"));
        add(txtCodigoProveedor);

        add(new JLabel("Código Producto"));
        add(txtCodigoProducto);

        add(new JLabel("Cantidad"));
        add(txtCantidad);

        add(new JLabel("Precio"));
        add(txtPrecio);

        add(new JLabel("Subtotal"));
        add(txtSubtotal);

        add(new JLabel("IVA (19%)"));
        add(txtIva);

        add(new JLabel("Total"));
        add(txtTotal);

        add(new JLabel("Forma de Pago"));
        add(cbFormaPago);

        add(btnCalcular);
        add(btnGuardar);

        add(new JLabel(""));
        add(btnVolver);
    }

    public JTextField getTxtCodigoCompra() {
        return txtCodigoCompra;
    }

    public JTextField getTxtCodigoProveedor() {
        return txtCodigoProveedor;
    }

    public JTextField getTxtCodigoProducto() {
        return txtCodigoProducto;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public JTextField getTxtIva() {
        return txtIva;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public JComboBox<String> getCbFormaPago() {
        return cbFormaPago;
    }

    public void limpiarCampos() {
        txtCodigoCompra.setText("");
        txtCodigoProveedor.setText("");
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        txtSubtotal.setText("");
        txtIva.setText("");
        txtTotal.setText("");
    }
}
