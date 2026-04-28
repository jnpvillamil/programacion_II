package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.gui.modelo.Compra;
import co.edu.uptc.gui.modelo.Proveedor;

@SuppressWarnings("serial")
public class CompraGUI extends JFrame {

    private JTextField txtFacturaProveedor;
    private JTextField txtCodigoProveedor;
    private JTextField txtTotalCompra;

    private JButton btnRegistrar;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JButton btnActualizar;
    private JButton btnEliminar;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public CompraGUI() {
        setTitle("Gestión de Compras");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {

        JPanel panelBase = new JPanel(new BorderLayout());
        panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 8, 8));

        txtFacturaProveedor = new JTextField();
        txtCodigoProveedor = new JTextField();
        txtTotalCompra = new JTextField();

        panelForm.add(new JLabel("Factura proveedor:"));
        panelForm.add(txtFacturaProveedor);

        panelForm.add(new JLabel("Código proveedor:"));
        panelForm.add(txtCodigoProveedor);

        panelForm.add(new JLabel("Total compra:"));
        panelForm.add(txtTotalCompra);

        JPanel panelBotones = new JPanel();

        btnRegistrar = new JButton("Registrar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        btnRegistrar.setActionCommand("REGISTRAR_COMPRA");
        btnBuscar.setActionCommand("BUSCAR_COMPRA");
        btnLimpiar.setActionCommand("LIMPIAR_COMPRA");
        btnActualizar.setActionCommand("ACTUALIZAR_COMPRA");
        btnEliminar.setActionCommand("ELIMINAR_COMPRA");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.setColumnIdentifiers(new String[]{
                "Factura", "Proveedor", "Total", "Fecha"
        });

        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        panelBase.add(panelForm, BorderLayout.NORTH);
        panelBase.add(scroll, BorderLayout.CENTER);
        panelBase.add(panelBotones, BorderLayout.SOUTH);

        add(panelBase);
    }


    public Compra obtenerCompraFormulario() {
        Compra compra = new Compra();
        compra.setNumeroFacturaProveedor(txtFacturaProveedor.getText().trim());
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo(txtCodigoProveedor.getText().trim());
        compra.setProveedor(proveedor);
        compra.setTotal(getTotalCompra());
        return compra;
    }

    public void cargarCompraFormulario(Compra compra) {

        if (compra == null) return;

        txtFacturaProveedor.setText(compra.getNumeroFacturaProveedor());

        if (compra.getProveedor() != null) {
            txtCodigoProveedor.setText(compra.getProveedor().getCodigo());
        } else {
            txtCodigoProveedor.setText("");
        }

        txtTotalCompra.setText(String.valueOf(compra.getTotal()));
    }

    public void cargarTabla(List<Compra> compras) {

        modeloTabla.setRowCount(0);

        for (Compra c : compras) {
            modeloTabla.addRow(new Object[]{
                    c.getNumeroFacturaProveedor(),
                    (c.getProveedor() != null ? c.getProveedor().getCodigo() : ""),
                    c.getTotal(),
                    c.getFechaHora()
            });
        }
    }

    
    public String getFacturaProveedor() {
        return txtFacturaProveedor.getText().trim();
    }

    public String getCodigoProveedor() {
        return txtCodigoProveedor.getText().trim();
    }

    public double getTotalCompra() {
        try {
            return Double.parseDouble(txtTotalCompra.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public void setTotalCompra(double total) {
        txtTotalCompra.setText(String.valueOf(total));
    }

    public JTable getTabla() {
        return tabla;
    }

    public void limpiarFormulario() {
        txtFacturaProveedor.setText("");
        txtCodigoProveedor.setText("");
        txtTotalCompra.setText("");
        tabla.clearSelection();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JButton getBtnActualizar() { return btnActualizar; }
    public JButton getBtnEliminar() { return btnEliminar; }
}