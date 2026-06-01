package co.uptc.edu.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import co.uptc.edu.modelo.Cliente;
import co.uptc.edu.modelo.Proveedor;
import co.uptc.edu.persistencia.ClienteDAO;
import co.uptc.edu.persistencia.CompraDAO;
import co.uptc.edu.persistencia.ProductoDAO;
import co.uptc.edu.persistencia.ProveedorDAO;
import co.uptc.edu.persistencia.VentaDAO;
import java.sql.ResultSet;

import co.uptc.edu.persistencia.ProductoDAO;

import java.awt.*;
import java.sql.ResultSet;

public class ModuloConsultas extends JFrame {
	private JComboBox<String> cbClientesHistorial;

	private JTable tablaHistorial;

	private DefaultTableModel modeloHistorial;
	private JTable tablaStock;

	private DefaultTableModel modeloStock;
	private JComboBox<String> cbProveedores;

	private JTextField txtFechaInicio;

	private JTextField txtFechaFin;

	private JTable tablaComprasProveedor;

	private DefaultTableModel modeloComprasProveedor;

	public ModuloConsultas() {

	    setTitle("Consultas del Sistema");
	    setSize(1200, 800);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	    JTabbedPane tabs = new JTabbedPane();

	    tabs.add("Ventas por Fecha", crearConsultaVentas());
	    tabs.add("Compras por Proveedor", crearConsultaCompras());
	    tabs.add("Stock Bajo Mínimo", crearConsultaStock());
	    tabs.add("Historial Cliente", crearConsultaHistorial());
	    tabs.add("Movimientos Contables", crearConsultaContable());

	    add(tabs);

	    cargarClientesHistorial();
	}

    // =====================================================
    // 1️⃣ VENTAS POR FECHA
    // =====================================================
    private JPanel crearConsultaVentas() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Ventas por Fecha"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filtros.add(new JLabel("Fecha:"));
        filtros.add(new JTextField(10));
        filtros.add(new JButton("Buscar"));

        panel.add(filtros, BorderLayout.NORTH);

        String[] columnas = {"Factura", "Cliente", "Total", "IVA"};
        JTable tabla = new JTable(new DefaultTableModel(columnas, 0));
        tabla.setRowHeight(28);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        return panel;
    }

    // =====================================================
    // 2️⃣ COMPRAS POR PROVEEDOR
    // =====================================================
    private JPanel crearConsultaCompras() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Compras por Proveedor"));

        JPanel filtros =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                15,
                                10
                        )
                );

        filtros.add(new JLabel("Proveedor:"));

        cbProveedores = new JComboBox<>();

        cargarProveedoresConsulta();

        filtros.add(cbProveedores);

        filtros.add(new JLabel("Desde:"));

        txtFechaInicio = new JTextField(8);

        filtros.add(txtFechaInicio);

        filtros.add(new JLabel("Hasta:"));

        txtFechaFin = new JTextField(8);

        filtros.add(txtFechaFin);

        JButton btnBuscar =
                new JButton("Buscar");

        filtros.add(btnBuscar);

        panel.add(
                filtros,
                BorderLayout.NORTH
        );

        String[] columnas = {

                "Factura",
                "Fecha",
                "Total",
                "IVA"
        };

        modeloComprasProveedor =
                new DefaultTableModel(
                        columnas,
                        0
                );

        tablaComprasProveedor =
                new JTable(
                        modeloComprasProveedor
                );

        tablaComprasProveedor.setRowHeight(28);

        panel.add(
                new JScrollPane(
                        tablaComprasProveedor
                ),
                BorderLayout.CENTER
        );

        btnBuscar.addActionListener(e -> {

            buscarComprasProveedor();

        });

        return panel;
    }

    // =====================================================
    // 3️⃣ PRODUCTOS BAJO STOCK
    // =====================================================
    private JPanel crearConsultaStock() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(
                new TitledBorder(
                        "Productos con Stock Bajo Mínimo"
                )
        );

        JPanel filtros =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        JButton btnActualizar =
                new JButton("Actualizar Lista");

        filtros.add(btnActualizar);

        panel.add(
                filtros,
                BorderLayout.NORTH
        );

        String[] columnas = {

                "Código",
                "Producto",
                "Stock Actual",
                "Stock Mínimo"
        };

        modeloStock =
                new DefaultTableModel(
                        columnas,
                        0
                );

        tablaStock =
                new JTable(modeloStock);

        tablaStock.setRowHeight(28);

        panel.add(
                new JScrollPane(tablaStock),
                BorderLayout.CENTER
        );

        btnActualizar.addActionListener(e -> {

            cargarStockBajoMinimo();

        });

        return panel;
    }

    // =====================================================
    // 4️⃣ HISTORIAL CLIENTE
    // =====================================================
    private JPanel crearConsultaHistorial() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Historial de Compras del Cliente"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));

        filtros.add(new JLabel("Cliente:"));

        cbClientesHistorial = new JComboBox<>();

        filtros.add(cbClientesHistorial);

        JButton btnBuscar = new JButton("Buscar");

        filtros.add(btnBuscar);

        panel.add(filtros, BorderLayout.NORTH);

        String[] columnas = {
                "Fecha",
                "Producto",
                "Cantidad",
                "Subtotal"
        };

        modeloHistorial =
                new DefaultTableModel(columnas,0);

        tablaHistorial =
                new JTable(modeloHistorial);

        tablaHistorial.setRowHeight(28);

        panel.add(
                new JScrollPane(tablaHistorial),
                BorderLayout.CENTER
        );

        btnBuscar.addActionListener(e -> {
            buscarHistorialCliente();
        });

        return panel;
    }

    // =====================================================
    // 5️⃣ MOVIMIENTOS CONTABLES
    // =====================================================
    private JPanel crearConsultaContable() {

        JPanel panel = new JPanel(new BorderLayout(15,15));
        panel.setBorder(new TitledBorder("Movimientos Contables"));

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filtros.add(new JLabel("Cuenta:"));
        filtros.add(new JComboBox<>(new String[]{
                "Caja",
                "Bancos",
                "Ingresos por Ventas",
                "Inventario",
                "IVA Generado",
                "IVA Descontable",
                "Proveedores"
        }));
        filtros.add(new JLabel("Desde:"));
        filtros.add(new JTextField(8));
        filtros.add(new JLabel("Hasta:"));
        filtros.add(new JTextField(8));
        filtros.add(new JButton("Buscar"));

        panel.add(filtros, BorderLayout.NORTH);

        String[] columnas = {"Código", "Fecha", "Tipo", "Débito", "Crédito", "Descripción"};
        JTable tabla = new JTable(new DefaultTableModel(columnas, 0));
        tabla.setRowHeight(28);

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        return panel;
    }
    private void cargarClientesHistorial(){

        ClienteDAO dao = new ClienteDAO();

        cbClientesHistorial.removeAllItems();

        for(Cliente c : dao.obtenerClientes()){

            cbClientesHistorial.addItem(
                    c.getCodigo()
                    + "-"
                    + c.getNombre()
            );
        }
    }
    
    private void buscarHistorialCliente(){

        modeloHistorial.setRowCount(0);

        if(cbClientesHistorial.getSelectedItem() == null){
            return;
        }

        String codigoCliente =
                cbClientesHistorial
                .getSelectedItem()
                .toString()
                .split("-")[0];

        VentaDAO dao = new VentaDAO();

        try{

            ResultSet rs =
                    dao.historialCliente(codigoCliente);

            while(rs.next()){

                modeloHistorial.addRow(
                        new Object[]{

                                rs.getDate("fecha"),
                                rs.getString("codigo_producto"),
                                rs.getInt("cantidad"),
                                rs.getDouble("subtotal")
                        }
                );
            }

        }catch(Exception e){

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error consultando historial"
            );
        }
    }
    private void cargarStockBajoMinimo(){

        modeloStock.setRowCount(0);

        ProductoDAO dao =
                new ProductoDAO();

        try{

            ResultSet rs =
                    dao.obtenerStockBajoMinimo();

            while(rs.next()){

                modeloStock.addRow(
                        new Object[]{

                                rs.getString("codigo"),

                                rs.getString("nombre"),

                                rs.getInt("stock_actual"),

                                rs.getInt("stock_minimo")
                        }
                );
            }

        }catch(Exception e){

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error cargando productos"
            );
        }
    }
    private void cargarProveedoresConsulta(){

        cbProveedores.removeAllItems();

        ProveedorDAO dao =
                new ProveedorDAO();

        for(Proveedor p : dao.obtenerProveedores()){

            cbProveedores.addItem(
                    p.getCodigo()
                    + " - "
                    + p.getRazonSocial()
            );
        }
    }
    private void buscarComprasProveedor(){

        modeloComprasProveedor.setRowCount(0);

        if(cbProveedores.getSelectedItem() == null){
            return;
        }

        String codigoProveedor =
                cbProveedores
                .getSelectedItem()
                .toString()
                .split(" - ")[0];

        CompraDAO dao =
                new CompraDAO();

        try{

            ResultSet rs =
                    dao.comprasPorProveedor(
                            codigoProveedor,
                            txtFechaInicio.getText(),
                            txtFechaFin.getText()
                    );

            while(rs.next()){

                modeloComprasProveedor.addRow(
                        new Object[]{

                                rs.getString(
                                        "numero_factura"
                                ),

                                rs.getDate(
                                        "fecha"
                                ),

                                rs.getDouble(
                                        "total"
                                ),

                                rs.getDouble(
                                        "iva"
                                )
                        }
                );
            }

        }catch(Exception e){

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error consultando compras"
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModuloConsultas().setVisible(true);
        });
    }
}