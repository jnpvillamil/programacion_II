package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.modelo.*;
import co.edu.uptc.config.TiendaConfig;

public class DialogoCompra extends JDialog {
    
    private JComboBox<String> cbProveedores;
    private JTextField txtFacturaProveedor;
    private JTextField txtProductoBuscar;
    private JButton btnAgregarProducto;
    private JTable tablaDetalles;
    private DefaultTableModel modeloDetalles;
    private JLabel lblSubtotal, lblIVA, lblTotal;
    
    private Proveedor proveedorSeleccionado;
    private List<DetalleCompra> detalles;
    private Compra compra;
    private boolean ok = false;
    
    public DialogoCompra(JFrame parent) {
        super(parent, "Registrar Compra", true);
        detalles = new ArrayList<>();
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
       
        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos de la Compra"));
        
        // Proveedores
        panelSuperior.add(new JLabel("Proveedor:"));
        cbProveedores = new JComboBox<>();
        cargarProveedores();
        cbProveedores.addActionListener(e -> cargarProveedorSeleccionado());
        panelSuperior.add(cbProveedores);
        
        // Factura del proveedor
        panelSuperior.add(new JLabel("Factura Proveedor:"));
        txtFacturaProveedor = new JTextField();
        panelSuperior.add(txtFacturaProveedor);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        
        // Buscar producto
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtProductoBuscar = new JTextField(20);
        btnAgregarProducto = new JButton("➕ Agregar Producto");
        btnAgregarProducto.addActionListener(e -> agregarProducto());
        panelBuscar.add(new JLabel("Buscar Producto (código):"));
        panelBuscar.add(txtProductoBuscar);
        panelBuscar.add(btnAgregarProducto);
        
        panelCentral.add(panelBuscar, BorderLayout.NORTH);
        
      
        modeloDetalles = new DefaultTableModel();
        modeloDetalles.addColumn("Código");
        modeloDetalles.addColumn("Producto");
        modeloDetalles.addColumn("Cantidad");
        modeloDetalles.addColumn("Precio Compra");
        modeloDetalles.addColumn("Subtotal");
        
        tablaDetalles = new JTable(modeloDetalles);
        JScrollPane scroll = new JScrollPane(tablaDetalles);
        panelCentral.add(scroll, BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        
        
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        
        JPanel panelTotales = new JPanel(new GridLayout(3, 2, 10, 5));
        panelTotales.setBorder(BorderFactory.createTitledBorder("Totales"));
        
        lblSubtotal = new JLabel("$0");
        lblIVA = new JLabel("$0");
        lblTotal = new JLabel("$0");
        
        panelTotales.add(new JLabel("Subtotal:"));
        panelTotales.add(lblSubtotal);
        panelTotales.add(new JLabel("IVA (19%):"));
        panelTotales.add(lblIVA);
        panelTotales.add(new JLabel("TOTAL:"));
        panelTotales.add(lblTotal);
        
        panelInferior.add(panelTotales, BorderLayout.WEST);
        
      
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("💾 Guardar Compra");
        JButton btnCancelar = new JButton("❌ Cancelar");
        
        btnGuardar.addActionListener(e -> guardarCompra());
        btnCancelar.addActionListener(e -> {
            ok = false;
            dispose();
        });
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        panelInferior.add(panelBotones, BorderLayout.EAST);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void cargarProveedores() {
        cbProveedores.removeAllItems();
        for(Proveedor p : TiendaConfig.getInstancia().getGestionProveedor().listarActivos()) {
            cbProveedores.addItem(p.getCodigo() + " - " + p.getRazonSocial());
        }
        if(cbProveedores.getItemCount() > 0) {
            cargarProveedorSeleccionado();
        }
    }
    
    private void cargarProveedorSeleccionado() {
        String seleccion = (String) cbProveedores.getSelectedItem();
        if(seleccion != null) {
            String codigo = seleccion.split(" - ")[0];
            proveedorSeleccionado = TiendaConfig.getInstancia().getGestionProveedor().buscar(codigo);
        }
    }
    
    private void agregarProducto() {
        String codigo = txtProductoBuscar.getText().trim();
        if(codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del producto");
            return;
        }
        
        Producto p = TiendaConfig.getInstancia().getGestionProducto().buscar(codigo);
        if(p == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado");
            return;
        }
        
        String cantidadStr = JOptionPane.showInputDialog(this, "Cantidad para " + p.getNombre() + ":");
        if(cantidadStr == null) return;
        
        String precioStr = JOptionPane.showInputDialog(this, "Precio de compra para " + p.getNombre() + ":");
        if(precioStr == null) return;
        
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            double precioCompra = Double.parseDouble(precioStr);
            
            if(cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a 0");
                return;
            }
            
            if(precioCompra <= 0) {
                JOptionPane.showMessageDialog(this, "Precio debe ser mayor a 0");
                return;
            }
            
            
            DetalleCompra detalle = new DetalleCompra(p, cantidad, precioCompra);
            detalles.add(detalle);
            actualizarTablaDetalles();
            actualizarTotales();
            txtProductoBuscar.setText("");
            
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad o precio inválido");
        }
    }
    
    private void actualizarTablaDetalles() {
        modeloDetalles.setRowCount(0);
        for(DetalleCompra d : detalles) {
            Object[] fila = {
                d.getProducto().getCodigo(),
                d.getProducto().getNombre(),
                d.getCantidad(),
                "$" + d.getPrecioCompra(),
                "$" + d.getSubtotal()
            };
            modeloDetalles.addRow(fila);
        }
    }
    
    private void actualizarTotales() {
        double subtotal = detalles.stream().mapToDouble(DetalleCompra::getSubtotal).sum();
        double iva = subtotal * 0.19;
        double total = subtotal + iva;
        
        lblSubtotal.setText("$" + String.format("%,.2f", subtotal));
        lblIVA.setText("$" + String.format("%,.2f", iva));
        lblTotal.setText("$" + String.format("%,.2f", total));
    }
    
    private void guardarCompra() {
        if(proveedorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
            return;
        }
        
        String factura = txtFacturaProveedor.getText().trim();
        if(factura.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el número de factura del proveedor");
            return;
        }
        
        if(detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto");
            return;
        }
        
       
        if(TiendaConfig.getInstancia().getGestionCompra().buscarCompra(factura) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe una compra con esta factura");
            return;
        }
        
        compra = new Compra(factura, proveedorSeleccionado, "admin");
        
       
        for(DetalleCompra d : detalles) {
            compra.agregarDetalle(d.getProducto(), d.getCantidad(), d.getPrecioCompra());
        }
        
        ok = true;
        dispose();
    }
    
    public boolean isOk() { return ok; }
    public Compra getCompra() { return compra; }
}