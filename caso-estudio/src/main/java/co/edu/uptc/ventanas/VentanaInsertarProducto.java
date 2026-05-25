package co.edu.uptc.ventanas;

import javax.swing.*;
import java.awt.event.*;
import co.edu.uptc.dao.ProductoDao;
import co.edu.uptc.vo.ProductoVo;

@SuppressWarnings("serial")
public class VentanaInsertarProducto extends JFrame implements ActionListener {
    private JTextField txtCod, txtNom, txtCat, txtPreC, txtPreV, txtStk, txtStkM;
    private JButton btnGuardar;

    public VentanaInsertarProducto() {
        setTitle("Registrar Producto"); setSize(400, 420); setLayout(null); setLocationRelativeTo(null);
        
        JLabel lbl1 = new JLabel("Código:"); lbl1.setBounds(30, 20, 100, 25); add(lbl1);
        txtCod = new JTextField(); txtCod.setBounds(150, 20, 180, 25); add(txtCod);

        JLabel lbl2 = new JLabel("Nombre:"); lbl2.setBounds(30, 60, 100, 25); add(lbl2);
        txtNom = new JTextField(); txtNom.setBounds(150, 60, 180, 25); add(txtNom);

        JLabel lbl3 = new JLabel("Categoría:"); lbl3.setBounds(30, 100, 100, 25); add(lbl3);
        txtCat = new JTextField(); txtCat.setBounds(150, 100, 180, 25); add(txtCat);

        JLabel lbl4 = new JLabel("Precio Compra:"); lbl4.setBounds(30, 140, 100, 25); add(lbl4);
        txtPreC = new JTextField(); txtPreC.setBounds(150, 140, 180, 25); add(txtPreC);

        JLabel lbl5 = new JLabel("Precio Venta:"); lbl5.setBounds(30, 180, 100, 25); add(lbl5);
        txtPreV = new JTextField(); txtPreV.setBounds(150, 180, 180, 25); add(txtPreV);

        JLabel lbl6 = new JLabel("Stock Actual:"); lbl6.setBounds(30, 220, 100, 25); add(lbl6);
        txtStk = new JTextField(); txtStk.setBounds(150, 220, 180, 25); add(txtStk);

        JLabel lbl7 = new JLabel("Stock Mínimo:"); lbl7.setBounds(30, 260, 100, 25); add(lbl7);
        txtStkM = new JTextField(); txtStkM.setBounds(150, 260, 180, 25); add(txtStkM);

        btnGuardar = new JButton("Guardar"); btnGuardar.setBounds(150, 310, 110, 30);
        btnGuardar.addActionListener(this); add(btnGuardar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGuardar) {
            try {
                ProductoVo prod = new ProductoVo();
                prod.setCodigoProducto(txtCod.getText());
                prod.setNombreProducto(txtNom.getText());
                prod.setCategoria(txtCat.getText());
                prod.setPrecioCompra(Double.parseDouble(txtPreC.getText()));
                prod.setPrecioVenta(Double.parseDouble(txtPreV.getText()));
                prod.setStock(Integer.parseInt(txtStk.getText()));
                prod.setStockMinimo(Integer.parseInt(txtStkM.getText()));

                ProductoDao dao = new ProductoDao();
                dao.registrarProducto(prod);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Campos numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}