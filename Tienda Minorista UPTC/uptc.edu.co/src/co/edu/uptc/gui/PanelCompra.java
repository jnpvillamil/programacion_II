package co.edu.uptc.gui;

import co.edu.uptc.negocio.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelCompra extends JPanel {
    private JTextField txtCod, txtCant;
    private JButton btnAdd, btnSave;
    private DefaultTableModel modelo;
    private GestionProducto gProd;
    private GestionCompra gCom;
    private Compra compraActual = new Compra();
    private Color azulOscuro = new Color(13, 38, 64);

    public PanelCompra(GestionProducto gProd, GestionCompra gCom, Evento ev) {
        this.gProd = gProd;
        this.gCom = gCom;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(230, 230, 230));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pnlInputs = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlInputs.setBackground(Color.WHITE);
        pnlInputs.add(new JLabel("Codigo Producto:")); txtCod = new JTextField(10); pnlInputs.add(txtCod);
        pnlInputs.add(new JLabel("Cantidad:")); txtCant = new JTextField(5); pnlInputs.add(txtCant);
        btnAdd = new JButton("Cargar al Carrito"); 
        btnAdd.addActionListener(ev); 
        pnlInputs.add(btnAdd);

        add(pnlInputs, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Producto", "Cantidad", "Costo Subtotal"}, 0);
        add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);

        btnSave = new JButton("REGISTRAR COMPRA");
        btnSave.setBackground(azulOscuro);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSave.setPreferredSize(new Dimension(0, 55));
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addActionListener(ev);
        
        add(btnSave, BorderLayout.SOUTH);
    }

    public void agregar() {
        try {
            Producto p = gProd.buscarProducto(txtCod.getText());
            if (p != null) {
                int c = Integer.parseInt(txtCant.getText());
                compraActual.agregarDetalle(p, c);
                modelo.addRow(new Object[]{p.getNombre(), c, (p.getPrecioCompra() * c)});
                txtCod.setText("");
                txtCant.setText("");
                txtCod.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Producto no registrado en inventario");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique la cantidad");
        }
    }

    public void finalizar() {
        if (modelo.getRowCount() > 0) {
            gCom.registrarCompra(compraActual);
            compraActual = new Compra();
            modelo.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Stock actualizado y compra guardada");
        }
    }

    public JButton getBtnAgregar() { return btnAdd; }
    public JButton getBtnFinalizar() { return btnSave; }
}