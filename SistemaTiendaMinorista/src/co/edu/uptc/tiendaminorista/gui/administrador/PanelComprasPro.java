package co.edu.uptc.tiendaminorista.gui.administrador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import co.edu.uptc.tiendaminorista.gui.Evento;
import co.edu.uptc.tiendaminorista.gui.PanelPrincipal;
import co.edu.uptc.tiendaminorista.modelo.Producto;
import co.edu.uptc.tiendaminorista.modelo.Proveedor;
import co.edu.uptc.tiendaminorista.modelo.CompraPro;
import co.edu.uptc.tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.tiendaminorista.negocio.GestionProveedor;
import co.edu.uptc.tiendaminorista.persistencia.LocalCompraPro;

public class PanelComprasPro extends JPanel implements ActionListener {

    private JComboBox<Proveedor> comboProveedores;
    private JComboBox<Producto> comboProductos;
    private JTextField txtCantidad;
    private JLabel lblPrecioUnitario, lblTotal;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private GestionProveedor gestionProveedor;
    private GestionProducto gestionProducto;
    private LocalCompraPro localCompra;
    private JButton btnComprar, btnVolver, btnLimpiar;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private List<CompraPro> historialCompras;

    public PanelComprasPro(Evento e, GestionProveedor gestionProveedor, GestionProducto gestionProducto) {
        this.gestionProveedor = gestionProveedor;
        this.gestionProducto = gestionProducto;
        this.localCompra = new LocalCompraPro();
        this.historialCompras = localCompra.leer();
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("COMPRAS A PROVEEDORES", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        
        //PanelFormulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Compra"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        //Proveedor
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelFormulario.add(new JLabel("Proveedor:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        comboProveedores = new JComboBox<>();
        comboProveedores.setPreferredSize(new Dimension(200, 28));
        panelFormulario.add(comboProveedores, gbc);

        //Producto
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1;
        comboProductos = new JComboBox<>();
        comboProductos.setPreferredSize(new Dimension(200, 28));
        comboProductos.addActionListener(evt -> actualizarPrecioYTotal());
        panelFormulario.add(comboProductos, gbc);

        //Cantidad
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        txtCantidad = new JTextField();
        txtCantidad.setPreferredSize(new Dimension(150, 28));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                actualizarPrecioYTotal();
            }
        });
        panelFormulario.add(txtCantidad, gbc);

        //PrecioUnitario
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Precio Unitario:"), gbc);
        gbc.gridx = 1;
        lblPrecioUnitario = new JLabel("$0");
        lblPrecioUnitario.setFont(new Font("Arial", Font.BOLD, 13));
        lblPrecioUnitario.setForeground(new Color(0, 100, 0));
        panelFormulario.add(lblPrecioUnitario, gbc);

        //Total
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Total:"), gbc);
        gbc.gridx = 1;
        lblTotal = new JLabel("$0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setForeground(new Color(0, 0, 150));
        panelFormulario.add(lblTotal, gbc);

        //BotonesDeAccion
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnComprar = new JButton("COMPRAR");
        btnComprar.setBackground(new Color(60, 179, 113));
        btnComprar.setForeground(Color.WHITE);
        btnComprar.setFocusPainted(false);
        btnComprar.addActionListener(this);
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(evt -> limpiarCampos());
        
        panelBotones.add(btnComprar);
        panelBotones.add(btnLimpiar);
        panelFormulario.add(panelBotones, gbc);

        //TablaHistorial 
        String[] columnas = {"Fecha", "Proveedor", "Producto", "Cantidad", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.getTableHeader().setReorderingAllowed(false);
        tablaHistorial.setRowHeight(25);
        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Historial de Compras"));
        scrollTabla.setPreferredSize(new Dimension(450, 0));

        panelCentral.add(panelFormulario, BorderLayout.WEST);
        panelCentral.add(scrollTabla, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVolver = new JButton("Volver");
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e);
        btnVolver.setActionCommand(Evento.CANCELAR);
        panelInferior.add(btnVolver);
        add(panelInferior, BorderLayout.SOUTH);

        cargarDatos();
    }

    private void cargarDatos() {
        //CargarProveedores
        List<Proveedor> proveedores = gestionProveedor.listarProveedores();
        comboProveedores.removeAllItems();
        for (Proveedor p : proveedores) {
            if (p.isActivo()) {
                comboProveedores.addItem(p);
            }
        }

        //CargarProductosYActualizar
        actualizarListaProductos();
        actualizarHistorial();
    }

    private void actualizarListaProductos() {
        List<Producto> productos = gestionProducto.listarProductos();
        comboProductos.removeAllItems();
        for (Producto p : productos) {
            if (p.isActivo()) {
                comboProductos.addItem(p);
            }
        }
        actualizarPrecioYTotal();
    }

    private void actualizarPrecioYTotal() {
        Producto p = (Producto) comboProductos.getSelectedItem();
        if (p != null) {
            lblPrecioUnitario.setText(String.format("$%,.0f", p.getPrecioCompra()));
            try {
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());
                if (cantidad > 0) {
                    double total = p.getPrecioCompra() * cantidad;
                    lblTotal.setText(String.format("$%,.0f", total));
                } else {
                    lblTotal.setText("$0");
                }
            } catch (NumberFormatException e) {
                lblTotal.setText("$0");
            }
        } else {
            lblPrecioUnitario.setText("$0");
            lblTotal.setText("$0");
        }
    }

    private void actualizarHistorial() {
        modeloTabla.setRowCount(0);
        for (int i = historialCompras.size() - 1; i >= 0; i--) {
            CompraPro c = historialCompras.get(i);
            modeloTabla.addRow(new Object[]{
                c.getFechaFormateada(),
                c.getProveedor(),
                c.getProducto(),
                c.getCantidad(),
                String.format("$%,.0f", c.getTotal())
            });
        }
    }

    private void limpiarCampos() {
        txtCantidad.setText("");
        actualizarPrecioYTotal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("COMPRAR")) {
            realizarCompra();
        }
    }

    private void realizarCompra() {
        Proveedor proveedor = (Proveedor) comboProveedores.getSelectedItem();
        Producto producto = (Producto) comboProductos.getSelectedItem();

        if (proveedor == null || producto == null) {
            JOptionPane.showMessageDialog(this, "Seleccione proveedor y producto");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Cantidad debe ser mayor a 0");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida");
            return;
        }

        double totalCompra = producto.getPrecioCompra() * cantidad;

        int confirm = JOptionPane.showConfirmDialog(this,
            String.format("Confirmar compra:\n\nProveedor: %s\nProducto: %s\nCantidad: %d\nPrecio Unitario: $%,.0f\nTotal: $%,.0f",
                proveedor.getNombre(), producto.getNombre(), cantidad, producto.getPrecioCompra(), totalCompra),
            "Confirmar Compra", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        //ActualizarStock
        producto.setStockActual(producto.getStockActual() + cantidad);
        gestionProducto.actualizarProducto(producto);

        //GuardarEnHistorial
        CompraPro nuevaCompra = new CompraPro(
            LocalDate.now(),
            proveedor.getNombre(),
            producto.getNombre(),
            cantidad,
            producto.getPrecioCompra(),
            totalCompra
        );
        historialCompras.add(nuevaCompra);
        localCompra.guardar(nuevaCompra);

        //RegistrarContabilidad
        PanelPrincipal ventana = (PanelPrincipal) SwingUtilities.getWindowAncestor(this);
        if (ventana != null) {
            ventana.registrarCompra(producto.getNombre(), totalCompra, proveedor.getNit());
        }

        limpiarCampos();
        actualizarListaProductos();
        actualizarHistorial();
        
        JOptionPane.showMessageDialog(this,
            String.format("✅ Compra registrada!\n\nProducto: %s\nCantidad: %d\nTotal: $%,.0f",
                producto.getNombre(), cantidad, totalCompra));
    }

    public void refrescarProveedores() {
        List<Proveedor> proveedores = gestionProveedor.listarProveedores();
        comboProveedores.removeAllItems();
        for (Proveedor p : proveedores) {
            if (p.isActivo()) {
                comboProveedores.addItem(p);
            }
        }
    }
}