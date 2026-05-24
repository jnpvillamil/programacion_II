package co.uptc.edu.tienda.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.tienda.enums.FormaPagoEnum;
import co.uptc.edu.tienda.modelo.Cliente;
import co.uptc.edu.tienda.modelo.Producto;

public class PanelVenta extends JPanel {

    private JComboBox<Cliente> comboClientes;
    private JComboBox<FormaPagoEnum> comboPago;
    private JTextField txtDocumento;
    private JTable tablaProductos;
    private JTable tablaDetalle;
    private DefaultTableModel modeloProductos;
    private DefaultTableModel modeloDetalle;
    private JTextField txtCantidad;
    private JLabel lblTotal;
    private JButton btnAgregar;
    private JButton btnFinalizar;
    private List<Producto> todosProductos = new ArrayList<>();


    public PanelVenta(Evento evento) {

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 240, 240));

        // =====================================
        // TITULO
        // =====================================
        JLabel titulo = new JLabel("MODULO DE VENTAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // =====================================
        // PANEL PRINCIPAL
        // =====================================
        JPanel principal = new JPanel(new BorderLayout(10, 10));
        principal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        principal.setBackground(new Color(240, 240, 240));
        add(principal, BorderLayout.CENTER);

        // =====================================
        // PANEL DATOS
        // =====================================
        JPanel datos = new JPanel(new GridLayout(4, 2, 10, 10));
        datos.setBorder(BorderFactory.createTitledBorder("Datos Venta"));

        comboClientes = new JComboBox<>();
        comboPago = new JComboBox<>(FormaPagoEnum.values());
        txtDocumento = new JTextField();
        txtDocumento.setEditable(false);
        JTextField txtBuscarProducto = new JTextField();
        
        txtBuscarProducto.getDocument().addDocumentListener(
        	    new javax.swing.event.DocumentListener() {
        	        public void insertUpdate(javax.swing.event.DocumentEvent e) {
        	            filtrarProductos(txtBuscarProducto.getText());
        	        }
        	        public void removeUpdate(javax.swing.event.DocumentEvent e) {
        	            filtrarProductos(txtBuscarProducto.getText());
        	        }
        	        public void changedUpdate(javax.swing.event.DocumentEvent e) {
        	            filtrarProductos(txtBuscarProducto.getText());
        	        }
        	    }
        	);
        
        
        datos.add(new JLabel("Cliente"));
        datos.add(comboClientes);
        datos.add(new JLabel("Documento"));
        datos.add(txtDocumento);
        datos.add(new JLabel("Forma Pago"));
        datos.add(comboPago);
        datos.add(new JLabel("Buscar Producto"));
        datos.add(txtBuscarProducto);

        principal.add(datos, BorderLayout.NORTH);

        // =====================================
        // TABLA PRODUCTOS
        // =====================================
        modeloProductos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloProductos.addColumn("Codigo");
        modeloProductos.addColumn("Producto");
        modeloProductos.addColumn("Precio");
        modeloProductos.addColumn("IVA");
        modeloProductos.addColumn("Stock");

        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setRowHeight(25);

        JScrollPane scrollProductos = new JScrollPane(tablaProductos);
        scrollProductos.setBorder(BorderFactory.createTitledBorder("Productos Disponibles"));

        // =====================================
        // TABLA DETALLE
        // =====================================
        modeloDetalle = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloDetalle.addColumn("Producto");
        modeloDetalle.addColumn("Cantidad");
        modeloDetalle.addColumn("Precio");
        modeloDetalle.addColumn("Subtotal");

        tablaDetalle = new JTable(modeloDetalle);
        tablaDetalle.setRowHeight(25);

        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder("Detalle Venta"));

        JPanel centro = new JPanel(new GridLayout(2, 1, 10, 10));
        centro.setBackground(new Color(240, 240, 240));
        centro.add(scrollProductos);
        centro.add(scrollDetalle);

        principal.add(centro, BorderLayout.CENTER);

        // =====================================
        // PANEL INFERIOR
        // =====================================
        JPanel inferior = new JPanel();
        inferior.setBorder(BorderFactory.createTitledBorder("Acciones"));

        txtCantidad = new JTextField(5);

        btnAgregar = new JButton("Agregar Producto");
        btnAgregar.setBackground(new Color(52, 152, 219));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setActionCommand(Evento.AGREGAR_PRODUCTO_VTA);
        btnAgregar.addActionListener(evento);

        btnFinalizar = new JButton("Finalizar Venta");
        btnFinalizar.setBackground(new Color(46, 204, 113));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setActionCommand(Evento.FINALIZAR_VTA);
        btnFinalizar.addActionListener(evento);



        lblTotal = new JLabel("TOTAL: $0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 20));
        lblTotal.setForeground(new Color(192, 57, 43));

        inferior.add(new JLabel("Cantidad"));
        inferior.add(txtCantidad);
        inferior.add(btnAgregar);
        inferior.add(lblTotal);
        inferior.add(btnFinalizar);


        principal.add(inferior, BorderLayout.SOUTH);

        // =====================================
        // EVENTO COMBO CLIENTE
        // =====================================
        comboClientes.addActionListener(e -> {
            Cliente c = (Cliente) comboClientes.getSelectedItem();
            if (c != null) {
                txtDocumento.setText(String.valueOf(c.getNumeroDocumento()));
            }
        });
    }

    // =====================================
    // MÉTODOS DE ACCESO PARA VentanaPrincipal
    // =====================================

    public void poblarClientes(List<Cliente> clientes) {
        comboClientes.removeAllItems();
        for (Cliente c : clientes) {
            if (c.getEstado().name().equals("ACTIVO")) {
                comboClientes.addItem(c);
            }
        }
    }

    public void poblarProductos(List<Producto> productos) {
        this.todosProductos = productos; // guarda la lista completa
        modeloProductos.setRowCount(0);
        for (Producto p : productos) {
            if (p.isActivo() && p.getStockActual() > 0) {
                modeloProductos.addRow(new Object[]{
                    p.getCodigoProducto(),
                    p.getNombreProducto(),
                    p.getPrecioVenta(),
                    (p.getPorcentajeIva() * 100) + "%",
                    p.getStockActual()
                });
            }
        }
    }
    
    private void filtrarProductos(String texto) {
        modeloProductos.setRowCount(0);
        for (Producto p : todosProductos) {
            if (!p.isActivo() || p.getStockActual() <= 0) continue;
            // Busca por nombre o por código
            boolean coincide = p.getNombreProducto()
                    .toLowerCase()
                    .contains(texto.toLowerCase())
                    || String.valueOf(p.getCodigoProducto()).contains(texto);
            if (coincide) {
                modeloProductos.addRow(new Object[]{
                    p.getCodigoProducto(),
                    p.getNombreProducto(),
                    p.getPrecioVenta(),
                    (p.getPorcentajeIva() * 100) + "%",
                    p.getStockActual()
                });
            }
        }
    }

    public int getFilaProductoSeleccionada() {
        return tablaProductos.getSelectedRow();
    }

    public int getCodigoProductoSeleccionado() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) return -1;
        return Integer.parseInt(modeloProductos.getValueAt(fila, 0).toString());
    }

    public String getNombreProductoSeleccionado() {
        return modeloProductos.getValueAt(tablaProductos.getSelectedRow(), 1).toString();
    }

    public double getPrecioProductoSeleccionado() {
        return Double.parseDouble(modeloProductos.getValueAt(tablaProductos.getSelectedRow(), 2).toString());
    }

    public int getStockProductoSeleccionado() {
        return Integer.parseInt(modeloProductos.getValueAt(tablaProductos.getSelectedRow(), 4).toString());
    }

    public int getCantidad() {
        return Integer.parseInt(txtCantidad.getText().trim());
    }

    public Cliente getClienteSeleccionado() {
        return (Cliente) comboClientes.getSelectedItem();
    }

    public FormaPagoEnum getFormaPagoSeleccionada() {
        return (FormaPagoEnum) comboPago.getSelectedItem();
    }

    public void agregarFilaDetalle(String nombre, int cantidad, double precio, double subtotal) {
        modeloDetalle.addRow(new Object[]{nombre, cantidad, precio, subtotal});
    }

    public void actualizarStockTabla(int fila, int nuevoStock) {
        modeloProductos.setValueAt(nuevoStock, fila, 4);
    }

    public void actualizarTotal(double total) {
        lblTotal.setText("TOTAL: $" + String.format("%.2f", total));
    }

    public int getFilasDetalle() {
        return modeloDetalle.getRowCount();
    }

    public double getSubtotalDetalle(int fila) {
        return Double.parseDouble(modeloDetalle.getValueAt(fila, 3).toString());
    }

    public void limpiar() {
        modeloDetalle.setRowCount(0);
        txtCantidad.setText("");
        lblTotal.setText("TOTAL: $0");
    }

    public String getTxtCantidad() {
        return txtCantidad.getText().trim();
    }
    
    public double getIvaProductoSeleccionado() {
        String iva = modeloProductos
                .getValueAt(tablaProductos.getSelectedRow(), 3)
                .toString()
                .replace("%", "")
                .trim();
        return Double.parseDouble(iva) / 100;
    }
}