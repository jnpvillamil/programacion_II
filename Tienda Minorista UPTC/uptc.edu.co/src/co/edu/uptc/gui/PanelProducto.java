package co.edu.uptc.gui;

import co.edu.uptc.negocio.GestionProducto;
import co.edu.uptc.negocio.Producto;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProducto extends JPanel {
    private JTextField tCod, tNom, tPre, tStk;
    private JButton btnG, btnE;
    private DefaultTableModel modelo;
    private GestionProducto gProd;
    private Color azulOscuro = new Color(13, 38, 64);

    public PanelProducto(GestionProducto gProd, Evento ev) {
        this.gProd = gProd;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(230, 230, 230));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pnlForm = new JPanel(new GridLayout(1, 8, 10, 0));
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(new EmptyBorder(15, 15, 15, 15));
        pnlForm.add(new JLabel("Codigo:")); tCod = new JTextField(); pnlForm.add(tCod);
        pnlForm.add(new JLabel("Nombre:")); tNom = new JTextField(); pnlForm.add(tNom);
        pnlForm.add(new JLabel("Precio:")); tPre = new JTextField(); pnlForm.add(tPre);
        pnlForm.add(new JLabel("Stock:")); tStk = new JTextField(); pnlForm.add(tStk);

        add(pnlForm, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Código", "Nombre", "Precio", "Stock"}, 0);
        add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);

        JPanel pnlActionBar = new JPanel(new GridLayout(1, 2));
        pnlActionBar.setBackground(azulOscuro);
        pnlActionBar.setPreferredSize(new Dimension(0, 50));
        
        btnG = new JButton("GUARDAR EN INVENTARIO"); 
        configurarBoton(btnG);
        btnG.addActionListener(ev);
        
        btnE = new JButton("ELIMINAR DE INVENTARIO"); 
        configurarBoton(btnE);
        btnE.setForeground(new Color(255, 100, 100));
        btnE.addActionListener(ev);
        
        pnlActionBar.add(btnG); 
        pnlActionBar.add(btnE);

        add(pnlActionBar, BorderLayout.SOUTH);
    }

    private void configurarBoton(JButton btn) {
        btn.setBackground(azulOscuro);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void guardarProducto() {
        try {
            gProd.registrarProducto(new Producto(tCod.getText(), tNom.getText(), "", 0, Double.parseDouble(tPre.getText()), Integer.parseInt(tStk.getText())));
            actualizar();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Inventario actualizado");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Datos numéricos inválidos");
        }
    }

    public void eliminarProducto() {
        if (!tCod.getText().isEmpty()) {
            gProd.eliminarProducto(tCod.getText());
            actualizar();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto eliminado del inventario");
        }
    }

    private void actualizar() {
        modelo.setRowCount(0);
        for (Producto p : gProd.getProductos()) {
            modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecioVenta(), p.getStock()});
        }
    }

    private void limpiarCampos() {
        tCod.setText(""); tNom.setText(""); tPre.setText(""); tStk.setText("");
        tCod.requestFocus();
    }

    public JButton getBtnGuardar() { return btnG; }
    public JButton getBtnEliminar() { return btnE; }
}