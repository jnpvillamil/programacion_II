package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class CompraGUI extends JFrame {

    private JTextField txtFacturaProveedor;
    private JTextField txtCodigoProveedor;
    private JTextField txtTotalCompra;
    private JButton btnRegistrar;
    private JButton btnBuscar;
    private JButton btnLimpiar;

    public CompraGUI() {
        setTitle("Gestión de Compras");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
  

        txtFacturaProveedor = new JTextField();
        txtCodigoProveedor = new JTextField();
        txtTotalCompra = new JTextField();

        panel.add(new JLabel("Factura proveedor:"));
        panel.add(txtFacturaProveedor);
        panel.add(new JLabel("Código proveedor:"));
        panel.add(txtCodigoProveedor);
        panel.add(new JLabel("Total compra:"));
        panel.add(txtTotalCompra);

        JPanel botones = new JPanel();
        btnRegistrar = new JButton("Registrar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.setActionCommand("REGISTRAR_COMPRA");
        btnBuscar.setActionCommand("BUSCAR_COMPRA");
        btnLimpiar.setActionCommand("LIMPIAR_COMPRA");

        botones.add(btnRegistrar);
        botones.add(btnBuscar);
        botones.add(btnLimpiar);

        panelBase.add(panel, BorderLayout.CENTER);
        panelBase.add(botones, BorderLayout.SOUTH);
        add(panelBase);
    }

    public String getFacturaProveedor() {
        return txtFacturaProveedor.getText().trim();
    }

    public String getCodigoProveedor() {
        return txtCodigoProveedor.getText().trim();
    }

    public double getTotalCompra() {
        return Double.parseDouble(txtTotalCompra.getText().trim());
    }

    public void setTotalCompra(double total) {
        txtTotalCompra.setText(String.valueOf(total));
    }

    public void limpiarFormulario() {
        txtFacturaProveedor.setText("");
        txtCodigoProveedor.setText("");
        txtTotalCompra.setText("");
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

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}