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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.tienda.enums.FormaPagoEnum;
import co.uptc.edu.tienda.modelo.Cliente;
import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Venta;
import co.uptc.edu.tienda.negocio.GestionVenta;
import co.uptc.edu.tienda.negocio.VentaConfig;
import co.uptc.edu.tienda.persistencia.LocalCliente;
import co.uptc.edu.tienda.persistencia.LocalProducto;

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

    private List<DetalleVenta> listaDetalle;

    // =====================================
    // GESTION VENTA
    // =====================================

    private GestionVenta gestVenta;

    public PanelVenta() {

        setLayout(new BorderLayout(10,10));

        setBackground(new Color(240,240,240));

        listaDetalle = new ArrayList<>();

        // =====================================
        // CONFIGURACION NEGOCIO
        // =====================================

        VentaConfig config =
                new VentaConfig();

        gestVenta =
                config.getGestVenta();

        // =====================================
        // TITULO
        // =====================================

        JLabel titulo =
                new JLabel(
                        "MODULO DE VENTAS",
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

        // =====================================
        // PANEL PRINCIPAL
        // =====================================

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

        // =====================================
        // PANEL DATOS
        // =====================================

        JPanel datos =
                new JPanel(
                        new GridLayout(3,2,10,10));

        datos.setBorder(
                BorderFactory.createTitledBorder(
                        "Datos Venta"));

        comboClientes =
                new JComboBox<>();

        comboPago =
                new JComboBox<>(
                        FormaPagoEnum.values());

        txtDocumento =
                new JTextField();

        txtDocumento.setEditable(false);

        datos.add(new JLabel("Cliente"));
        datos.add(comboClientes);

        datos.add(new JLabel("Documento"));
        datos.add(txtDocumento);

        datos.add(new JLabel("Forma Pago"));
        datos.add(comboPago);

        principal.add(datos,
                BorderLayout.NORTH);

        // =====================================
        // TABLA PRODUCTOS
        // =====================================

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
        modeloProductos.addColumn("Precio");
        modeloProductos.addColumn("Stock");

        tablaProductos =
                new JTable(modeloProductos);

        tablaProductos.setRowHeight(25);

        JScrollPane scrollProductos =
                new JScrollPane(tablaProductos);

        scrollProductos.setBorder(
                BorderFactory.createTitledBorder(
                        "Productos"));

        // =====================================
        // TABLA DETALLE
        // =====================================

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
                        "Detalle Venta"));

        JPanel centro =
                new JPanel(
                        new GridLayout(2,1,10,10));

        centro.setBackground(
                new Color(240,240,240));

        centro.add(scrollProductos);
        centro.add(scrollDetalle);

        principal.add(centro,
                BorderLayout.CENTER);

        // =====================================
        // PANEL INFERIOR
        // =====================================

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
                new JButton("Finalizar Venta");

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

        // =====================================
        // CARGAR DATOS
        // =====================================

        cargarClientes();

        cargarProductos();

        // =====================================
        // EVENTO CLIENTE
        // =====================================

        comboClientes.addActionListener(e -> {

            Cliente c =
                    (Cliente)
                            comboClientes.getSelectedItem();

            if(c != null) {

                txtDocumento.setText(
                        String.valueOf(
                                c.getNumeroDocumento()));
            }
        });

        // =====================================
        // EVENTO AGREGAR PRODUCTO
        // =====================================

        btnAgregar.addActionListener(e -> {

            agregarProductoDetalle();
        });

        // =====================================
        // EVENTO FINALIZAR VENTA
        // =====================================

        btnFinalizar.addActionListener(e -> {

            try {

                if(listaDetalle.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Debe agregar productos");

                    return;
                }

                Cliente cliente =
                        (Cliente)
                                comboClientes.getSelectedItem();

                if(cliente == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Seleccione un cliente");

                    return;
                }

                FormaPagoEnum formaPago =
                        (FormaPagoEnum)
                                comboPago.getSelectedItem();

                // =====================================
                // CREAR VENTA DESDE NEGOCIO
                // =====================================

                Venta venta =
                        gestVenta.crearVenta(
                                cliente,
                                formaPago);

                venta.setDetalles(listaDetalle);

                gestVenta.calcularTotal(venta);

                gestVenta.guardarVenta(venta);

                JOptionPane.showMessageDialog(
                        this,
                        "Venta registrada correctamente");

                limpiarVenta();

            } catch(Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Error al guardar venta:\n"
                        + ex.getMessage());
            }
        });
    }

    // =====================================
    // CARGAR CLIENTES
    // =====================================

    private void cargarClientes() {

        LocalCliente local =
                new LocalCliente();

        List<Cliente> lista =
                local.leerClientes();

        for(Cliente c : lista) {

            if(c.getEstado().name().equals("ACTIVO")) {

                comboClientes.addItem(c);
            }
        }
    }

    // =====================================
    // CARGAR PRODUCTOS
    // =====================================

    private void cargarProductos() {

        LocalProducto local =
                new LocalProducto();

        List<Producto> lista =
                local.listar();

        for(Producto p : lista) {

            if(p.isActivo()
                    && p.getStockActual() > 0) {

                modeloProductos.addRow(
                        new Object[] {

                                p.getCodigoProducto(),
                                p.getNombreProducto(),
                                p.getPrecioVenta(),
                                p.getStockActual()
                        });
            }
        }
    }

    // =====================================
    // AGREGAR PRODUCTO
    // =====================================

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

            if(txtCantidad.getText()
                    .trim()
                    .isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese una cantidad");

                return;
            }

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

            // VALIDAR CANTIDAD

            if(cantidad <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cantidad inválida");

                return;
            }

            // VALIDAR STOCK

            if(cantidad > stock) {

                JOptionPane.showMessageDialog(
                        this,
                        "Stock insuficiente");

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

            // DESCONTAR STOCK EN TABLA

            int nuevoStock =
                    stock - cantidad;

            modeloProductos.setValueAt(
                    nuevoStock,
                    fila,
                    3);

            // CREAR PRODUCTO

            Producto producto =
                    new Producto();

            producto.setCodigoProducto(codigo);

            producto.setNombreProducto(nombre);

            producto.setPrecioVenta(precio);

            producto.setStockActual(nuevoStock);

            // CREAR DETALLE

            DetalleVenta detalle =
                    new DetalleVenta(
                            producto,
                            cantidad);

            listaDetalle.add(detalle);

            calcularTotal();

            txtCantidad.setText("");

        } catch(NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cantidad inválida");
        }
    }

    // =====================================
    // CALCULAR TOTAL
    // =====================================

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

    // =====================================
    // LIMPIAR VENTA
    // =====================================

    private void limpiarVenta() {

        modeloDetalle.setRowCount(0);

        listaDetalle.clear();

        txtCantidad.setText("");

        lblTotal.setText("TOTAL: $0");
    }
}