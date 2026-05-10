package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.config.TiendaConfig;

public class DialogoProducto extends DialogoCentral {
    
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<String> cbCategoria;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStockActual;
    private JTextField txtStockMinimo;
    
    private Producto producto;
    
    public DialogoProducto(JFrame parent, boolean esCrear, Producto productoExistente) {
        super(parent, esCrear ? "Nuevo Producto" : "Modificar Producto", esCrear);
        
        if(!esCrear && productoExistente != null) {
            this.producto = productoExistente;
            cargarDatos();
        }
    }
    
    @Override
    protected void inicializarCampos() {
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        cbCategoria = new JComboBox<>(new String[]{"Víveres", "Aseo", "Papelería", "Otros"});
        txtPrecioCompra = new JTextField();
        txtPrecioVenta = new JTextField();
        txtStockActual = new JTextField();
        txtStockMinimo = new JTextField();
        
        add(new JLabel("Código:"));
        add(txtCodigo);
        
        add(new JLabel("Nombre:"));
        add(txtNombre);
        
        add(new JLabel("Categoría:"));
        add(cbCategoria);
        
        add(new JLabel("Precio Compra:"));
        add(txtPrecioCompra);
        
        add(new JLabel("Precio Venta:"));
        add(txtPrecioVenta);
        
        add(new JLabel("Stock Actual:"));
        add(txtStockActual);
        
        add(new JLabel("Stock Mínimo:"));
        add(txtStockMinimo);
    }
    
    private void cargarDatos() {
        txtCodigo.setText(producto.getCodigo());
        txtCodigo.setEnabled(false);  
        
        txtNombre.setText(producto.getNombre());
        
     
        String cat = producto.getCategoria();
        for(int i = 0; i < cbCategoria.getItemCount(); i++) {
            if(cbCategoria.getItemAt(i).equals(cat)) {
                cbCategoria.setSelectedIndex(i);
                break;
            }
        }
        
        txtPrecioCompra.setText(String.valueOf(producto.getPrecioCompra()));
        txtPrecioVenta.setText(String.valueOf(producto.getPrecioVenta()));
        txtStockActual.setText(String.valueOf(producto.getStockActual()));
        txtStockMinimo.setText(String.valueOf(producto.getStockMinimo()));
    }
    
    @Override
    protected boolean validarCampos() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioCompraStr = txtPrecioCompra.getText().trim();
        String precioVentaStr = txtPrecioVenta.getText().trim();
        String stockActualStr = txtStockActual.getText().trim();
        String stockMinimoStr = txtStockMinimo.getText().trim();
        
       
        if(codigo.isEmpty() || nombre.isEmpty() || precioCompraStr.isEmpty() || 
           precioVentaStr.isEmpty() || stockActualStr.isEmpty() || stockMinimoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return false;
        }
        
      
        if(esCrear && TiendaConfig.getInstancia().getGestionProducto().existe(codigo)) {
            JOptionPane.showMessageDialog(this, "El código de producto ya existe");
            return false;
        }
        
        
        if(!nombre.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras, números y espacios");
            return false;
        }
        
     
        double precioCompra, precioVenta;
        int stockActual, stockMinimo;
        
        try {
            precioCompra = Double.parseDouble(precioCompraStr);
            precioVenta = Double.parseDouble(precioVentaStr);
            stockActual = Integer.parseInt(stockActualStr);
            stockMinimo = Integer.parseInt(stockMinimoStr);
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precios y stocks deben ser números válidos");
            return false;
        }
        
       
        if(precioCompra <= 0) {
            JOptionPane.showMessageDialog(this, "El precio de compra debe ser mayor a 0");
            return false;
        }
        
        if(precioVenta <= precioCompra) {
            JOptionPane.showMessageDialog(this, "El precio de venta debe ser mayor al precio de compra");
            return false;
        }
        
        if(stockActual < 0) {
            JOptionPane.showMessageDialog(this, "El stock actual no puede ser negativo");
            return false;
        }
        
        if(stockMinimo < 0) {
            JOptionPane.showMessageDialog(this, "El stock mínimo no puede ser negativo");
            return false;
        }
        
        
        producto = new Producto(
            codigo,
            nombre,
            cbCategoria.getSelectedItem().toString(),
            precioCompra,
            precioVenta,
            stockActual,
            stockMinimo
        );
        
        return true;
    }
    
    public Producto getProducto() {
        return producto;
    }
}