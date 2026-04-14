package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

import co.edu.uptc.enums.FormaPagoEnum;

@SuppressWarnings("serial")
public class VentaGUI extends JFrame {

    private JTextField txtFactura;
    private JTextField txtCodigoCliente;
    private JComboBox<FormaPagoEnum> cbFormaPago;
    private JTextField txtTotalVenta;

    private JButton btnRegistrar;
    private JButton btnBuscar;
    private JButton btnAnular;
    private JButton btnLimpiar;

    public VentaGUI() {
        setTitle("Gestión de Ventas");
        setSize(500, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
    	
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));

        txtFactura = new JTextField();
        txtCodigoCliente = new JTextField();
        cbFormaPago = new JComboBox<>(FormaPagoEnum.values());
        txtTotalVenta = new JTextField();

        panel.add(new JLabel("Factura:"));
        panel.add(txtFactura);
        panel.add(new JLabel("Código cliente:"));
        panel.add(txtCodigoCliente);
        panel.add(new JLabel("Forma de pago:"));
        panel.add(cbFormaPago);
        panel.add(new JLabel("Total venta:"));
        panel.add(txtTotalVenta);

        JPanel botones = new JPanel();
        btnRegistrar = new JButton("Registrar");
        btnBuscar = new JButton("Buscar");
        btnAnular = new JButton("Anular");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.setActionCommand("REGISTRAR_VENTA");
        btnBuscar.setActionCommand("BUSCAR_VENTA");
        btnAnular.setActionCommand("ANULAR_VENTA");
        btnLimpiar.setActionCommand("LIMPIAR_VENTA");

        botones.add(btnRegistrar);
        botones.add(btnBuscar);
        botones.add(btnAnular);
        botones.add(btnLimpiar);

        panelBase.add(panel, BorderLayout.CENTER);
        panelBase.add(botones, BorderLayout.SOUTH);
        
        add(panelBase);
    }

    public String getFactura() {
        return txtFactura.getText().trim();
    }

    public String getCodigoCliente() {
        return txtCodigoCliente.getText().trim();
    }

    public FormaPagoEnum getFormaPago() {
        return (FormaPagoEnum) cbFormaPago.getSelectedItem();
    }

    public double getTotalVenta() {
        return Double.parseDouble(txtTotalVenta.getText().trim());
    }

    public void setTotalVenta(double total) {
        txtTotalVenta.setText(String.valueOf(total));
    }

    public void limpiarFormulario() {
        txtFactura.setText("");
        txtCodigoCliente.setText("");
        txtTotalVenta.setText("");
        cbFormaPago.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnAnular() {
        return btnAnular;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}