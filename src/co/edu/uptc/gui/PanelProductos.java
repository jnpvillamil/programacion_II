package co.edu.uptc.gui;

import co.edu.uptc.negocio.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProductos extends JPanel {
    private SistemaGestion sis;
    private DefaultTableModel modelo;
    private JTextField txtCodigo, txtNombre, txtCat, txtPc, txtPv, txtStock, txtStockMin;

    public PanelProductos(SistemaGestion sis) {
        this.sis = sis;
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(244, 246, 249));

        JLabel lblTitulo = new JLabel("Gestión de Productos e Inventario");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(44, 62, 80)); // Letra oscura
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setOpaque(false);

        JPanel form = new JPanel(new GridLayout(4, 4, 10, 10));
        form.setOpaque(false);
        
        txtCodigo = new JTextField(); txtNombre = new JTextField();
        txtCat = new JTextField(); txtPc = new JTextField();
        txtPv = new JTextField(); txtStock = new JTextField();
        txtStockMin = new JTextField();

        // Método auxiliar para no repetir código creando JLabels
        form.add(crearLabel("Código:")); form.add(txtCodigo);
        form.add(crearLabel("Nombre:")); form.add(txtNombre);
        form.add(crearLabel("Categoría:")); form.add(txtCat);
        form.add(crearLabel("P. Compra:")); form.add(txtPc);
        form.add(crearLabel("P. Venta:")); form.add(txtPv);
        form.add(crearLabel("Stock Inicial:")); form.add(txtStock);
        form.add(crearLabel("Stock Mínimo:")); form.add(txtStockMin);

        panelCentro.add(form, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar Producto");
        btnGuardar.setBackground(new Color(46, 204, 113)); 
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGuardar.setOpaque(true); // <--- CORRECCIÓN DE RENDERIZADO
        btnGuardar.setBorderPainted(false); // <--- CORRECCIÓN DE RENDERIZADO
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        panelBotones.add(btnGuardar);
        panelCentro.add(panelBotones, BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.CENTER);

        modelo = new DefaultTableModel(new String[]{"Código", "Nombre", "Categoría", "P. Venta", "Stock", "Alerta Min."}, 0);
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(44, 62, 80));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 250));
        add(scroll, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> {
            try {
                ValidadorDatos.validarCadenaNoVacia(txtCodigo.getText(), "Código");
                ValidadorDatos.validarCadenaNoVacia(txtNombre.getText(), "Nombre");
                double pc = ValidadorDatos.validarDecimalPositivo(txtPc.getText(), "Precio Compra");
                double pv = ValidadorDatos.validarDecimalPositivo(txtPv.getText(), "Precio Venta");
                int stock = ValidadorDatos.validarEnteroPositivo(txtStock.getText(), "Stock Inicial");
                int stockMin = ValidadorDatos.validarEnteroPositivo(txtStockMin.getText(), "Stock Mínimo");

                Producto p = new Producto(txtCodigo.getText(), txtNombre.getText(), txtCat.getText(), pc, pv, stock, stockMin);
                sis.getGestorProducto().registrarProducto(p); // Llama a la lógica
                
                cargarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Producto registrado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.BLACK); // Asegurar contraste alto
        return lbl;
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        // Evita NullPointerException si la persistencia no está enlazada
        if(sis.getGestorProducto() != null) {
            for (Producto p : sis.getGestorProducto().obtenerProductos()) {
                String alerta = p.validarStockMinimo() ? "⚠️ BAJO STOCK" : "OK";
                modelo.addRow(new Object[]{p.getCodigoProducto(), p.getNombreProducto(), p.getCategoria(), p.getPrecioVenta(), p.getStockActual(), alerta});
            }
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText(""); txtNombre.setText(""); txtCat.setText("");
        txtPc.setText(""); txtPv.setText(""); txtStock.setText(""); txtStockMin.setText("");
    }
}