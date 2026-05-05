package co.edu.uptc.gui;

import co.edu.uptc.negocio.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelVenta extends JPanel {
    private JTextField txtCod, txtCant;
    private JButton btnAdd, btnPay;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private GestionProducto gProd;
    private GestionCliente gCli;
    private GestionVenta gVen;
    private Venta ventaActual = new Venta();
    private Color azulOscuro = new Color(13, 38, 64);

    public PanelVenta(GestionProducto gProd, GestionCliente gCli, GestionVenta gVen, Evento ev) {
        this.gProd = gProd;
        this.gCli = gCli;
        this.gVen = gVen;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(230, 230, 230));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel superior para entradas
        JPanel pnlInputs = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlInputs.setBackground(Color.WHITE);
        pnlInputs.add(new JLabel("Código:")); txtCod = new JTextField(10); pnlInputs.add(txtCod);
        pnlInputs.add(new JLabel("Cantidad:")); txtCant = new JTextField(5); pnlInputs.add(txtCant);
        
        btnAdd = new JButton("Agregar"); 
        btnAdd.addActionListener(ev); 
        pnlInputs.add(btnAdd);

        add(pnlInputs, BorderLayout.NORTH);

        // Tabla central
        modelo = new DefaultTableModel(new String[]{"Producto", "Cant.", "Subtotal"}, 0);
        add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);

        // Panel inferior con Total y Botón de Pago
        JPanel pnlSur = new JPanel(new BorderLayout());
        pnlSur.setBackground(new Color(230, 230, 230));

        lblTotal = new JLabel("TOTAL A PAGAR: $0.0  ");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setBorder(new EmptyBorder(10, 0, 10, 0));

        btnPay = new JButton("FINALIZAR VENTA Y COBRAR");
        btnPay.setBackground(azulOscuro);
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnPay.setPreferredSize(new Dimension(0, 55));
        btnPay.setFocusPainted(false);
        btnPay.setBorderPainted(false);
        btnPay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPay.addActionListener(ev);

        pnlSur.add(lblTotal, BorderLayout.NORTH);
        pnlSur.add(btnPay, BorderLayout.SOUTH);
        
        add(pnlSur, BorderLayout.SOUTH);
    }

    public void agregar() {
        try {
            Producto p = gProd.buscarProducto(txtCod.getText());
            if (p != null) {
                int c = Integer.parseInt(txtCant.getText());
                ventaActual.agregarDetalle(p, c);
                modelo.addRow(new Object[]{p.getNombre(), c, (p.getPrecioVenta() * c)});
                lblTotal.setText("TOTAL A PAGAR: $" + ventaActual.getTotal() + "  ");
                txtCod.setText("");
                txtCant.setText("");
                txtCod.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida");
        }
    }

    public void finalizar() {
        if (ventaActual.getTotal() > 0) {
            gVen.registrarVenta(ventaActual);
            ventaActual = new Venta();
            modelo.setRowCount(0);
            lblTotal.setText("TOTAL A PAGAR: $0.0  ");
            JOptionPane.showMessageDialog(this, "Venta registrada exitosamente");
        } else {
            JOptionPane.showMessageDialog(this, "La venta está vacía");
        }
    }

    public JButton getBtnAgregar() { return btnAdd; }
    public JButton getBtnFinalizar() { return btnPay; }
}