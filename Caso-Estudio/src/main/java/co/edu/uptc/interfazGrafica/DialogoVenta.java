package co.edu.uptc.interfazGrafica;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.modelo.*;
import co.edu.uptc.config.TiendaConfig;

public class DialogoVenta extends JDialog {
    
    private JComboBox<String> cbClientes;
    private JComboBox<String> cbFormaPago;
    private JTextField txtProductoBuscar;
    private JButton btnAgregarProducto;
    private JTable tablaDetalles;
    private DefaultTableModel modeloDetalles;
    private JLabel lblSubtotal, lblIVA, lblTotal;
    
    private Cliente clienteSeleccionado;
    private List<DetalleVenta> detalles;
    private Venta venta;
    private boolean ok = false;
    
    public DialogoVenta(JFrame parent) {
        super(parent, "Registrar Venta", true);
        detalles = new ArrayList<>();
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
       
        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos de la Venta"));
        
        // Clientes
        panelSuperior.add(new JLabel("Cliente:"));
        cbClientes = new JComboBox<>();
        cargarClientes();
        cbClientes.addActionListener(e -> cargarClienteSeleccionado());
        panelSuperior.add(cbClientes);
        
  
        panelSuperior.add(new JLabel("Forma de Pago:"));
        cbFormaPago = new JComboBox<>(new String[]{"Efectivo", "Transferencia", "Tarjeta", "Crédito"});
        panelSuperior.add(cbFormaPago);
        
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
        modeloDetalles.addColumn("Precio Unit.");
        modeloDetalles.addColumn("Subtotal");
        
        tablaDetalles = new JTable(modeloDetalles);
        JScrollPane scroll = new JScrollPane(tablaDetalles);
        panelCentral.add(scroll, BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior (Totales y botones)
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
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton(" Guardar Venta");
        JButton btnCancelar = new JButton(" Cancelar");
        
        btnGuardar.addActionListener(e -> guardarVenta());
        btnCancelar.addActionListener(e -> {
            ok = false;
            dispose();
        });
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        panelInferior.add(panelBotones, BorderLayout.EAST);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void cargarClientes() {
        cbClientes.removeAllItems();
        for(Cliente c : TiendaConfig.getInstancia().getGestionCliente().listarActivos()) {
            cbClientes.addItem(c.getCodigo() + " - " + c.getNombre());
        }
        if(cbClientes.getItemCount() > 0) {
            cargarClienteSeleccionado();
        }
    }
    
    private void cargarClienteSeleccionado() {
        String seleccion = (String) cbClientes.getSelectedItem();
        if(seleccion != null) {
            String codigo = seleccion.split(" - ")[0];
            clienteSeleccionado = TiendaConfig.getInstancia().getGestionCliente().buscar(codigo);
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
        
        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if(cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a 0");
                return;
            }
            
            if(p.getStockActual() < cantidad) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente. Stock actual: " + p.getStockActual());
                return;
            }
            
           
            DetalleVenta detalle = new DetalleVenta(p, cantidad, p.getPrecioVenta());
            detalles.add(detalle);
            actualizarTablaDetalles();
            actualizarTotales();
            txtProductoBuscar.setText("");
            
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
        }
    }
    
    private void actualizarTablaDetalles() {
        modeloDetalles.setRowCount(0);
        for(DetalleVenta d : detalles) {
            Object[] fila = {
                d.getProducto().getCodigo(),
                d.getProducto().getNombre(),
                d.getCantidad(),
                "$" + d.getPrecioUnitario(),
                "$" + d.getSubtotal()
            };
            modeloDetalles.addRow(fila);
        }
    }
    
    private void actualizarTotales() {
        double subtotal = detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum();
        double iva = subtotal * 0.19;
        double total = subtotal + iva;
        
        lblSubtotal.setText("$" + String.format("%,.2f", subtotal));
        lblIVA.setText("$" + String.format("%,.2f", iva));
        lblTotal.setText("$" + String.format("%,.2f", total));
    }
    
    private void guardarVenta() {
        if(clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }
        
        if(detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto");
            return;
        }
        
       
        String numeroFactura = "FAC-" + System.currentTimeMillis();
        String formaPago = (String) cbFormaPago.getSelectedItem();
        
        venta = new Venta(numeroFactura, clienteSeleccionado, formaPago, "admin");
        
        
        for(DetalleVenta d : detalles) {
            venta.agregarDetalle(d.getProducto(), d.getCantidad());
        }
        
        ok = true;
        dispose();
    }
    
    public boolean isOk() { return ok; }
    public Venta getVenta() { return venta; }
}