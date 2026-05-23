package co.uptc.edu.tienda.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.co.tienda.configs.CompraConfig;
import co.uptc.edu.tienda.modelo.Compra;
import co.uptc.edu.tienda.modelo.DetalleCompra;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Proveedor;
import co.uptc.edu.tienda.negocio.GestionCompra;
import co.uptc.edu.tienda.persistencia.LocalProducto;
import co.uptc.edu.tienda.persistencia.LocalProveedor;

public class PanelCompra extends JPanel {

    private JComboBox<Proveedor> comboProveedor;

    private JTextField txtNit;

    private JTable tablaProductos;

    private JTable tablaDetalle;

    private DefaultTableModel modeloProductos;

    private DefaultTableModel modeloDetalle;

    private JTextField txtCantidad;

    private JLabel lblTotal;

    private JButton btnAgregar;

    private JButton btnFinalizar;

    private List<DetalleCompra> listaDetalle;

    private GestionCompra gestCompra;

    public PanelCompra() {

        setLayout(new BorderLayout(10,10));

        setBackground(new Color(240,240,240));

        listaDetalle = new ArrayList<>();

        // CONFIG NEGOCIO

        CompraConfig config = new CompraConfig();

        gestCompra = config.getGestion();

        // TITULO

        JLabel titulo =
                new JLabel(
                        "MODULO DE COMPRAS",
                        SwingConstants.CENTER);

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24));

        titulo.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10));

        add(titulo, BorderLayout.NORTH);

        // PANEL PRINCIPAL

        JPanel principal =
                new JPanel(
                        new BorderLayout(10,10));

        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10));

        principal.setBackground(
                new Color(240,240,240));

        add(principal, BorderLayout.CENTER);

        // PANEL DATOS

        JPanel datos =
                new JPanel(
                        new GridLayout(2,2,10,10));

        datos.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos Compra"));

        comboProveedor =
                new JComboBox<>();

        txtNit =
                new JTextField();

        txtNit.setEditable(false);

        datos.add(new JLabel("Proveedor"));
        datos.add(comboProveedor);

        datos.add(new JLabel("NIT"));
        datos.add(txtNit);

        principal.add(datos,
                BorderLayout.NORTH);

        // TABLA PRODUCTOS

        modeloProductos =
                new DefaultTableModel() {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        modeloProductos.addColumn("Codigo");
        modeloProductos.addColumn("Producto");
        modeloProductos.addColumn("Precio Compra");
        modeloProductos.addColumn("Stock");

        tablaProductos =
                new JTable(modeloProductos);

        tablaProductos.setRowHeight(25);

        JScrollPane scrollProductos =
                new JScrollPane(tablaProductos);

        scrollProductos.setBorder(
                BorderFactory.createTitledBorder(
                        "Productos"));

        // TABLA DETALLE

        modeloDetalle =
                new DefaultTableModel() {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        modeloDetalle.addColumn("Producto");
        modeloDetalle.addColumn("Cantidad");
        modeloDetalle.addColumn("Precio");
        modeloDetalle.addColumn("Subtotal");

        tablaDetalle =
                new JTable(modeloDetalle);

        tablaDetalle.setRowHeight(25);

        JScrollPane scrollDetalle =
                new JScrollPane(tablaDetalle);

        scrollDetalle.setBorder(
                BorderFactory.createTitledBorder(
                        "Detalle Compra"));

        JPanel centro =
                new JPanel(
                        new GridLayout(2,1,10,10));

        centro.setBackground(
                new Color(240,240,240));

        centro.add(scrollProductos);
        centro.add(scrollDetalle);

        principal.add(centro,
                BorderLayout.CENTER);

        // PANEL INFERIOR

        JPanel inferior =
                new JPanel();

        inferior.setBorder(
                BorderFactory.createTitledBorder(
                        "Acciones"));

        txtCantidad =
                new JTextField(5);

        btnAgregar =
                new JButton("Agregar Producto");

        btnFinalizar =
                new JButton("Finalizar Compra");

        btnAgregar.setBackground(
                new Color(52,152,219));

        btnAgregar.setForeground(
                Color.WHITE);

        btnFinalizar.setBackground(
                new Color(46,204,113));

        btnFinalizar.setForeground(
                Color.WHITE);

        lblTotal =
                new JLabel("TOTAL: $0");

        lblTotal.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        20));

        lblTotal.setForeground(
                new Color(192,57,43));

        inferior.add(new JLabel("Cantidad"));
        inferior.add(txtCantidad);
        inferior.add(btnAgregar);
        inferior.add(lblTotal);
        inferior.add(btnFinalizar);

        principal.add(inferior,
                BorderLayout.SOUTH);

        // CARGAR DATOS

        cargarProveedores();

        cargarProductos();

        // EVENTO PROVEEDOR

        comboProveedor.addActionListener(e -> {

            Proveedor p =
                    (Proveedor)
                            comboProveedor.getSelectedItem();

            if(p != null) {

                txtNit.setText(
                        String.valueOf(
                                p.getNit()));
            }
        });

        // EVENTO AGREGAR

        btnAgregar.addActionListener(e -> {

            agregarProductoDetalle();
        });

        // EVENTO FINALIZAR

        btnFinalizar.addActionListener(e -> {

            finalizarCompra();
        });
    }

    // CARGAR PROVEEDORES

    private void cargarProveedores() {

        LocalProveedor local =
                new LocalProveedor();

        List<Proveedor> lista =
                local.leerProveedores();

        for(Proveedor p : lista) {

            comboProveedor.addItem(p);
        }
    }

    // CARGAR PRODUCTOS

    private void cargarProductos() {

        LocalProducto local =
                new LocalProducto();

        List<Producto> lista =
                local.listar();

        for(Producto p : lista) {

            modeloProductos.addRow(
                    new Object[] {

                            p.getCodigoProducto(),
                            p.getNombreProducto(),
                            p.getPrecioCompra(),
                            p.getStockActual()
                    });
        }
    }

    // AGREGAR PRODUCTO

    private void agregarProductoDetalle() {

        int fila =
                tablaProductos.getSelectedRow();

        if(fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un producto");

            return;
        }

        try {

            int codigo =
                    Integer.parseInt(
                            modeloProductos.getValueAt(
                                    fila,
                                    0).toString());

            String nombre =
                    modeloProductos.getValueAt(
                            fila,
                            1).toString();

            double precio =
                    Double.parseDouble(
                            modeloProductos.getValueAt(
                                    fila,
                                    2).toString());

            int stock =
                    Integer.parseInt(
                            modeloProductos.getValueAt(
                                    fila,
                                    3).toString());

            int cantidad =
                    Integer.parseInt(
                            txtCantidad.getText());

            if(cantidad <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cantidad inválida");

                return;
            }

            double subtotal =
                    precio * cantidad;

            modeloDetalle.addRow(
                    new Object[] {

                            nombre,
                            cantidad,
                            precio,
                            subtotal
                    });

            int nuevoStock =
                    stock + cantidad;

            modeloProductos.setValueAt(
                    nuevoStock,
                    fila,
                    3);

            Producto producto =
                    new Producto();

            producto.setCodigoProducto(codigo);

            producto.setNombreProducto(nombre);

            producto.setPrecioCompra(precio);

            producto.setStockActual(nuevoStock);

            DetalleCompra detalle =
                    new DetalleCompra();

            detalle.setProducto(producto);

            detalle.setCantidad(cantidad);

            detalle.setPrecioCompra(precio);

            detalle.setSubtotal(subtotal);

            listaDetalle.add(detalle);

            calcularTotal();

            txtCantidad.setText("");

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cantidad inválida");
        }
    }

    // CALCULAR TOTAL

    private void calcularTotal() {

        double total = 0;

        for(int i = 0;
                i < modeloDetalle.getRowCount();
                i++) {

            total += Double.parseDouble(
                    modeloDetalle.getValueAt(
                            i,
                            3).toString());
        }

        lblTotal.setText(
                "TOTAL: $" + total);
    }

    // FINALIZAR COMPRA

    private void finalizarCompra() {

        try {

            if(listaDetalle.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe agregar productos");

                return;
            }

            Proveedor proveedor =
                    (Proveedor)
                            comboProveedor.getSelectedItem();

            if(proveedor == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione proveedor");

                return;
            }

            Compra compra =
                    new Compra();

            compra.setProveedor(proveedor);

            compra.setFechaCompra(
                    LocalDate.now().toString());

            compra.setDetalles(listaDetalle);

            gestCompra.guardarCompra(compra);

            JOptionPane.showMessageDialog(
                    this,
                    "Compra registrada correctamente");

            limpiarCompra();

        } catch(Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar compra");
        }
    }

    // LIMPIAR

    private void limpiarCompra() {

        modeloDetalle.setRowCount(0);

        listaDetalle.clear();

        txtCantidad.setText("");

        lblTotal.setText("TOTAL: $0");
    }
}