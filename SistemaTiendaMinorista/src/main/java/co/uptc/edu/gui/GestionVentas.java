package co.uptc.edu.gui;

import co.uptc.edu.modelo.Producto;
import co.uptc.edu.modelo.Venta;
import co.uptc.edu.negocio.ControlVentas;
import co.uptc.edu.negocio.GestionProductos;
import co.uptc.edu.persistencia.VentaDAO;
import co.uptc.edu.persistencia.ClienteDAO;
import co.uptc.edu.persistencia.ProductoDAO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import co.uptc.edu.modelo.Cliente;
import co.uptc.edu.negocio.GestionClientes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GestionVentas extends JFrame {
	
	

    // ================= TABLAS =================
    private JTable tablaProductos;
    private JTable tablaVenta;

    // ================= MODELOS =================
    private DefaultTableModel modeloProductos;
    private DefaultTableModel modeloVenta;

    // ================= NEGOCIO =================
    private GestionProductos gestionProductos;
    private ControlVentas controlVentas;
    private VentaDAO ventaDAO;
    private ClienteDAO clienteDAO;
    private ProductoDAO productoDAO;

    // ================= CAMPOS =================
    private JTextField txtIVA;
    private JTextField txtTotal;
    
    private JTextField txtFecha;
    private JTextField txtHora;

    private JComboBox<String> cbClientes;

    private GestionClientes gestionClientes;

    // ================= CONSTRUCTOR =================
    public GestionVentas() {

        gestionProductos = new GestionProductos();
        controlVentas = new ControlVentas();
        gestionClientes = new GestionClientes();
        ventaDAO = new VentaDAO();
        clienteDAO = new ClienteDAO();
        productoDAO = new ProductoDAO();

        setTitle("Gestión de Ventas");
        setSize(1300, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15,15));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelResumen(), BorderLayout.SOUTH);

        cargarProductos();
    }

    // =====================================================
    // PANEL SUPERIOR
    // =====================================================
    private JPanel crearPanelSuperior() {

        JPanel panel =
                new JPanel(new GridLayout(2,4,15,15));

        panel.setBorder(
                new TitledBorder("Datos de la Venta")
        );

        JTextField txtFactura = new JTextField();

        txtFecha = new JTextField();
        txtHora = new JTextField();

        cbClientes = new JComboBox<>();

        // FECHA ACTUAL
        LocalDate fechaActual = LocalDate.now();

        DateTimeFormatter formatoFecha =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        txtFecha.setText(
                fechaActual.format(formatoFecha)
        );

        // HORA ACTUAL
        LocalTime horaActual = LocalTime.now();

        DateTimeFormatter formatoHora =
                DateTimeFormatter.ofPattern("HH:mm");

        txtHora.setText(
                horaActual.format(formatoHora)
        );

        txtFecha.setEditable(false);
        txtHora.setEditable(false);

        // CARGAR CLIENTES
        cbClientes.addItem("Seleccione Cliente");

        for(Cliente c : clienteDAO.obtenerClientes()){

        	if(c.isActivo()) {
            cbClientes.addItem(
            		c.getCodigo()
            		+"-"
            		+ c.getNombre()
        	 );
        	}
        }
        	

        panel.add(new JLabel("Número Factura:"));
        panel.add(txtFactura);

        panel.add(new JLabel("Fecha:"));
        panel.add(txtFecha);

        panel.add(new JLabel("Hora:"));
        panel.add(txtHora);

        panel.add(new JLabel("Cliente:"));
        panel.add(cbClientes);

        return panel;
    }

    // =====================================================
    // PANEL CENTRAL
    // =====================================================
    private JPanel crearPanelCentral() {

        JPanel panel = new JPanel(new GridLayout(2,1,15,15));

        panel.add(crearPanelProductos());
        panel.add(crearPanelVenta());

        return panel;
    }

    // =====================================================
    // PANEL PRODUCTOS DISPONIBLES
    // =====================================================
    private JPanel crearPanelProductos() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(
                new TitledBorder("Detalle de Productos")
        );

        String[] columnas = {
                "Código",
                "Producto",
                "Stock",
                "Precio"
        };

        modeloProductos =
                new DefaultTableModel(columnas,0){

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

        tablaProductos =
                new JTable(modeloProductos);

        tablaProductos.setRowHeight(25);

        panel.add(
                new JScrollPane(tablaProductos),
                BorderLayout.CENTER
        );

        JButton btnAgregar =
                new JButton("Agregar Producto");

        btnAgregar.addActionListener(
                e -> agregarProductoVenta()
        );

        JPanel botones = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        botones.add(btnAgregar);

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    // =====================================================
    // PANEL PRODUCTOS EN VENTA
    // =====================================================
    private JPanel crearPanelVenta() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(
                new TitledBorder("Productos en Venta")
        );

        String[] columnas = {
                "Código",
                "Producto",
                "Cantidad",
                "Precio",
                "Subtotal"
        };

        modeloVenta =
                new DefaultTableModel(columnas,0){

                    @Override
                    public boolean isCellEditable(int row, int column) {

                        return false;
                    }
                };

        tablaVenta = new JTable(modeloVenta);

        tablaVenta.setRowHeight(25);

        panel.add(
                new JScrollPane(tablaVenta),
                BorderLayout.CENTER
        );

        // ================= BOTONES =================
        JPanel botones = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        JButton btnEliminar =
                new JButton("Eliminar Producto");

        JButton btnLimpiar =
                new JButton("Limpiar Lista");

        btnEliminar.addActionListener(
                e -> eliminarProductoVenta()
        );

        btnLimpiar.addActionListener(
                e -> limpiarVenta()
        );

        botones.add(btnEliminar);
        botones.add(btnLimpiar);

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    // =====================================================
    // CARGAR PRODUCTOS
    // =====================================================
    private void cargarProductos() {

        modeloProductos.setRowCount(0);

        for (Producto p : productoDAO.obtenerProductos()) {

            modeloProductos.addRow(new Object[]{

                    p.getCodigo(),
                    p.getNombre(),
                    p.getStockActual(),
                    p.getPrecioVenta()
            });
        }
    }

    // =====================================================
    // AGREGAR PRODUCTO A VENTA
    // =====================================================
    private void agregarProductoVenta() {

        int fila =
                tablaProductos.getSelectedRow();

        if(fila == -1){

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un producto"
            );

            return;
        }

        String codigo =
                modeloProductos.getValueAt(fila,0).toString();

        String nombre =
                modeloProductos.getValueAt(fila,1).toString();

        int stock =
                Integer.parseInt(
                        modeloProductos.getValueAt(fila,2).toString()
                );

        double precio =
                Double.parseDouble(
                        modeloProductos.getValueAt(fila,3).toString()
                );

        String cantidadTexto =
                JOptionPane.showInputDialog(
                        this,
                        "Ingrese cantidad:"
                );

        if(cantidadTexto == null){
            return;
        }

        int cantidad;

        try{

            cantidad =
                    Integer.parseInt(cantidadTexto);

        }catch(Exception ex){

            JOptionPane.showMessageDialog(
                    this,
                    "Cantidad inválida"
            );

            return;
        }

        // VALIDAR STOCK
        if(cantidad > stock){

            JOptionPane.showMessageDialog(
                    this,
                    "Stock insuficiente"
            );

            return;
        }

        double subtotal =
                cantidad * precio;

        modeloVenta.addRow(new Object[]{

                codigo,
                nombre,
                cantidad,
                precio,
                subtotal
        });

        calcularTotales();
    }

    // =====================================================
    // ELIMINAR PRODUCTO VENTA
    // =====================================================
    private void eliminarProductoVenta() {

        int fila =
                tablaVenta.getSelectedRow();

        if(fila >= 0){

            modeloVenta.removeRow(fila);

            calcularTotales();

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un producto"
            );
        }
    }

    // =====================================================
    // LIMPIAR VENTA
    // =====================================================
    private void limpiarVenta() {

        modeloVenta.setRowCount(0);

        calcularTotales();
    }

    // =====================================================
    // CALCULAR TOTALES
    // =====================================================
    private void calcularTotales() {

        double subtotal = 0;

        for(int i = 0; i < modeloVenta.getRowCount(); i++){

            subtotal += Double.parseDouble(
                    modeloVenta.getValueAt(i,4).toString()
            );
        }

        double iva = subtotal * 0.19;

        double total = subtotal + iva;

        txtIVA.setText(
                String.format("%.2f", iva)
        );

        txtTotal.setText(
                String.format("%.2f", total)
        );
    }

    // =====================================================
    // REGISTRAR VENTA
    // =====================================================
    private void registrarVenta() {

        if (modeloVenta.getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay productos en venta"
            );

            return;
        }

        String clienteCodigo =
                cbClientes.getSelectedItem()
                .toString()
                .split("-")[0];

        String fechaBD =
                LocalDate.now().toString();

        for (int i = 0; i < modeloVenta.getRowCount(); i++) {

            String codigo =
                    modeloVenta.getValueAt(i, 0).toString();

            String nombre =
                    modeloVenta.getValueAt(i, 1).toString();

            int cantidad =
                    Integer.parseInt(
                            modeloVenta.getValueAt(i, 2).toString()
                    );

            double precio =
                    Double.parseDouble(
                            modeloVenta.getValueAt(i, 3).toString()
                    );

            Venta venta =
                    new Venta(
                            codigo,
                            nombre,
                            cantidad,
                            precio
                    );

            boolean guardado =
                    ventaDAO.guardarVenta(
                            fechaBD,
                            clienteCodigo,
                            venta
                    );

            if (!guardado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Error guardando venta"
                );

                return;
            }

            productoDAO.descontarStock(
                    codigo,
                    cantidad
            );
        }

        JOptionPane.showMessageDialog(
                this,
                "Venta registrada correctamente"
        );

        limpiarVenta();

        cargarProductos();
    }

    // =====================================================
    // PANEL RESUMEN
    // =====================================================
    private JPanel crearPanelResumen() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(
                new TitledBorder("Resumen de la Venta")
        );

        JPanel datos =
                new JPanel(new GridLayout(3,2,15,10));

        txtIVA = new JTextField();
        txtTotal = new JTextField();

        txtIVA.setEditable(false);
        txtTotal.setEditable(false);

        datos.add(new JLabel("IVA:"));
        datos.add(txtIVA);

        datos.add(new JLabel("Total Venta:"));
        datos.add(txtTotal);

        datos.add(new JLabel("Forma de Pago:"));

        JPanel pagos =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        pagos.add(new JCheckBox("Efectivo"));
        pagos.add(new JCheckBox("Transferencia"));
        pagos.add(new JCheckBox("Tarjeta"));
        pagos.add(new JCheckBox("Crédito"));

        datos.add(pagos);

        panel.add(datos, BorderLayout.CENTER);

        // ================= BOTONES =================
        JPanel botones =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnRegistrar =
                new JButton("Registrar Venta");

       
        btnRegistrar.addActionListener(
                e -> registrarVenta()
        );

        botones.add(btnRegistrar);


        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    // =====================================================
    // MAIN
    // =====================================================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new GestionVentas().setVisible(true);
        });
    }
}