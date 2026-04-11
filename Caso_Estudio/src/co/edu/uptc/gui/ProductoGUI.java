package co.edu.uptc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.enums.CategoriaProductoEnum;
import co.edu.uptc.gui.modelo.Producto;

@SuppressWarnings("serial")
public class ProductoGUI extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<CategoriaProductoEnum> cbCategoria;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStock;
    private JTextField txtStockMinimo;

    private JButton btnRegistrar; 
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnLimpiar;

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public ProductoGUI() {
        setTitle("Gestión de Productos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
    	JPanel panelBase= new JPanel(new BorderLayout());
    	panelBase.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 8, 8));

        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        cbCategoria = new JComboBox<>(CategoriaProductoEnum.values());
        txtPrecioCompra = new JTextField();
        txtPrecioVenta = new JTextField();
        txtStock = new JTextField();
        txtStockMinimo = new JTextField();

        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(txtCodigo);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(cbCategoria);
        panelFormulario.add(new JLabel("Precio compra:"));
        panelFormulario.add(txtPrecioCompra);
        panelFormulario.add(new JLabel("Precio venta:"));
        panelFormulario.add(txtPrecioVenta);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);
        panelFormulario.add(new JLabel("Stock mínimo:"));
        panelFormulario.add(txtStockMinimo);

        JPanel panelBotones = new JPanel();

        btnRegistrar = new JButton("Registrar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");

        btnRegistrar.setActionCommand("REGISTRAR_PRODUCTO");
        btnModificar.setActionCommand("MODIFICAR_PRODUCTO");
        btnEliminar.setActionCommand("ELIMINAR_PRODUCTO");
        btnBuscar.setActionCommand("BUSCAR_PRODUCTO");
        btnLimpiar.setActionCommand("LIMPIAR_PRODUCTO");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(
                new String[] { "Código", "Nombre", "Categoría", "P. Compra", "P. Venta", "Stock", "Stock Min" }, 0);
        tablaProductos = new JTable(modeloTabla);

        panelBase.add(panelFormulario, BorderLayout.NORTH);
        panelBase.add(panelBotones, BorderLayout.SOUTH);
        panelBase.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        
        add(panelBase);
    }

    public Producto obtenerProductoFormulario() {
        Producto producto = new Producto();
        producto.setCodigoProducto(txtCodigo.getText().trim());
        producto.setNombreProducto(txtNombre.getText().trim());
        producto.setCategoria((CategoriaProductoEnum) cbCategoria.getSelectedItem());
        producto.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText().trim()));
        producto.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText().trim()));
        producto.setStock(Integer.parseInt(txtStock.getText().trim()));
        producto.setStockMinimo(Integer.parseInt(txtStockMinimo.getText().trim()));
        producto.setActivo(true);
        return producto;
    }

    public String obtenerCodigoProducto() {
        return txtCodigo.getText().trim();
    }

    public void cargarProductoEnFormulario(Producto producto) {
        txtCodigo.setText(producto.getCodigoProducto());
        txtNombre.setText(producto.getNombreProducto());
        cbCategoria.setSelectedItem(producto.getCategoria());
        txtPrecioCompra.setText(String.valueOf(producto.getPrecioCompra()));
        txtPrecioVenta.setText(String.valueOf(producto.getPrecioVenta()));
        txtStock.setText(String.valueOf(producto.getStock()));
        txtStockMinimo.setText(String.valueOf(producto.getStockMinimo()));
    }

    public void cargarTabla(List<Producto> productos) {
        modeloTabla.setRowCount(0);
        for (Producto producto : productos) {
            modeloTabla.addRow(new Object[] {
                    producto.getCodigoProducto(),
                    producto.getNombreProducto(),
                    producto.getCategoria(),
                    producto.getPrecioCompra(),
                    producto.getPrecioVenta(),
                    producto.getStock(),
                    producto.getStockMinimo()
            });
        }
    }

    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtStock.setText("");
        txtStockMinimo.setText("");
        cbCategoria.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }
}