package guisubpanel;

import javax.swing.*;

import gui.Evento;

import java.awt.*;
import java.awt.event.ActionListener;

public class PanelRegistrarVenta extends JPanel {

    private JTextField txtCodigoVenta;
    private JTextField txtCodigoCliente;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JTextField txtPrecioUnitario;
    private JTextField txtSubtotal;
    private JTextField txtIva;
    private JTextField txtTotal;

    private JComboBox<String> cbFormaPago;

    private JButton btnCalcular;
    private JButton btnGuardar;
    private JButton btnVolver;

    public PanelRegistrarVenta(ActionListener evento) {

        setLayout(new GridLayout(11, 2, 10, 10));

        txtCodigoVenta = new JTextField();
        txtCodigoCliente = new JTextField();
        txtCodigoProducto = new JTextField();
        txtCantidad = new JTextField();
        txtPrecioUnitario = new JTextField();
        txtSubtotal = new JTextField();
        txtIva = new JTextField();
        txtTotal = new JTextField();

        cbFormaPago = new JComboBox<>(new String[]{"Efectivo", "Tarjeta", "Transferencia"});

        btnCalcular = new JButton("Calcular");
        btnGuardar = new JButton("Registrar Venta");
        btnVolver = new JButton("Volver");

        btnCalcular.setActionCommand(Evento.CALCULAR_VENTA);
        btnGuardar.setActionCommand(Evento.GUARDAR_VENTA);
        btnVolver.setActionCommand(Evento.CANCELAR_VENTA);

        btnCalcular.addActionListener(evento);
        btnGuardar.addActionListener(evento);
        btnVolver.addActionListener(evento);

        add(new JLabel("Código Venta"));
        add(txtCodigoVenta);

        add(new JLabel("Código Cliente"));
        add(txtCodigoCliente);

        add(new JLabel("Código Producto"));
        add(txtCodigoProducto);

        add(new JLabel("Cantidad"));
        add(txtCantidad);

        add(new JLabel("Precio Unitario"));
        add(txtPrecioUnitario);

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

    public JTextField getTxtCodigoVenta() {
        return txtCodigoVenta;
    }

    public JTextField getTxtCodigoCliente() {
        return txtCodigoCliente;
    }

    public JTextField getTxtCodigoProducto() {
        return txtCodigoProducto;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JTextField getTxtPrecioUnitario() {
        return txtPrecioUnitario;
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
        txtCodigoVenta.setText("");
        txtCodigoCliente.setText("");
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
        txtPrecioUnitario.setText("");
        txtSubtotal.setText("");
        txtIva.setText("");
        txtTotal.setText("");
        txtSubtotal.setEditable(false);
        txtIva.setEditable(false);
        txtTotal.setEditable(false);
    }
}
    