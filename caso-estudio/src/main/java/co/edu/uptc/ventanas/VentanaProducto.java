package co.edu.uptc.ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import co.edu.uptc.gui.modelo.Producto;
import co.edu.uptc.dao.ProductoDao;

@SuppressWarnings("serial")
public class VentanaProducto extends JFrame implements ActionListener {

    private JTextField campoCodigo, campoNombre, campoPrecioCompra, campoPrecioVenta, campoStock, campoStockMinimo;
    private JButton botonGuardar, botonActualizar, botonEliminarSeleccionado, botonLimpiar;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public VentanaProducto() {
        setTitle("Módulo de Gestión de Inventario de Productos ");
        setSize(950, 680);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(17, 17));

        iniciarComponentesFormulario();
        iniciarComponenteTabla();
        cargarDatosTabla();
    }

    private void iniciarComponentesFormulario() {
        JPanel contenedorFormulario = new JPanel();
        contenedorFormulario.setLayout(new BoxLayout(contenedorFormulario, BoxLayout.Y_AXIS));
        contenedorFormulario.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel panelForm = new JPanel(new GridLayout(3, 4, 12, 12));

        javax.swing.border.Border bordeLineaGrueso = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
        panelForm.setBorder(BorderFactory.createTitledBorder(bordeLineaGrueso, " Datos del Artículo de Inventario "));

        panelForm.add(new JLabel("Código (*clave):", SwingConstants.RIGHT));
        campoCodigo = new JTextField();
        panelForm.add(campoCodigo);

        panelForm.add(new JLabel("Nombre Producto:", SwingConstants.RIGHT));
        campoNombre = new JTextField();
        panelForm.add(campoNombre);

        panelForm.add(new JLabel("Precio Compra ($):", SwingConstants.RIGHT));
        campoPrecioCompra = new JTextField();
        panelForm.add(campoPrecioCompra);

        panelForm.add(new JLabel("Precio Venta ($):", SwingConstants.RIGHT));
        campoPrecioVenta = new JTextField();
        panelForm.add(campoPrecioVenta);

        panelForm.add(new JLabel("Stock Actual:", SwingConstants.RIGHT));
        campoStock = new JTextField();
        panelForm.add(campoStock);

        panelForm.add(new JLabel("Stock Mínimo:", SwingConstants.RIGHT));
        campoStockMinimo = new JTextField();
        panelForm.add(campoStockMinimo);

        contenedorFormulario.add(panelForm);

        // Panel de acciones 
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 8));
        botonGuardar = new JButton("Guardar Nuevo");
        botonActualizar = new JButton("Guardar Modificación");
        botonEliminarSeleccionado = new JButton("Eliminar Seleccionado");
        botonLimpiar = new JButton("Limpiar Campos");

        botonGuardar.addActionListener(this);
        botonActualizar.addActionListener(this);
        botonEliminarSeleccionado.addActionListener(this);
        botonLimpiar.addActionListener(this);

        panelAcciones.add(botonGuardar);
        panelAcciones.add(botonActualizar);
        panelAcciones.add(botonEliminarSeleccionado);
        panelAcciones.add(botonLimpiar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout(5, 5));
        contenedorSuperior.add(contenedorFormulario, BorderLayout.CENTER);
        contenedorSuperior.add(panelAcciones, BorderLayout.SOUTH);
        contenedorSuperior.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        add(contenedorSuperior, BorderLayout.NORTH);
    }

    private void iniciarComponenteTabla() {
        String[] columnas = {"Código", "Nombre del Producto", "P. Compra", "P. Venta", "Stock Real", "Stock Mín."};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.setRowHeight(22);
        
        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaProductos.getSelectedRow();
                if (fila >= 0) {
                    campoCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
                    campoNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    campoPrecioCompra.setText(modeloTabla.getValueAt(fila, 2).toString());
                    campoPrecioVenta.setText(modeloTabla.getValueAt(fila, 3).toString());
                    campoStock.setText(modeloTabla.getValueAt(fila, 4).toString());
                    campoStockMinimo.setText(modeloTabla.getValueAt(fila, 5).toString());
                    campoCodigo.setEditable(false);
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(" Registros de Inventario en Base de Datos "));

        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        contenedorTabla.add(scrollTabla, BorderLayout.CENTER);
        
        add(contenedorTabla, BorderLayout.CENTER);
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        try {
            ProductoDao dao = new ProductoDao();
            java.util.List<Producto> productos = dao.listarProductos();
            
            if (productos != null) {
                for (Producto p : productos) { 
                    Object[] fila = {
                        p.getCodigo(), 
                        p.getNombre(), 
                        p.getPrecioCompra(), 
                        p.getPrecioVenta(), 
                        p.getStock(), 
                        p.getStockMinimo()
                    };
                    modeloTabla.addRow(fila);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos en la tabla: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonLimpiar) {
            limpiarCampos();
            return;
        }

        String codigo = campoCodigo.getText().trim();

        // ACCIÓN ELIMINAR
        if (e.getSource() == botonEliminarSeleccionado) {
            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto de la tabla para eliminarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar de forma permanente el producto con código: " + codigo + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    ProductoDao dao = new ProductoDao();
                    dao.eliminarProducto(codigo);
                    JOptionPane.showMessageDialog(this, "¡Producto eliminado exitosamente!");
                    limpiarCampos();
                    cargarDatosTabla();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            return;
        }

        if (codigo.isEmpty() || campoNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos Código y Nombre de Producto son obligatorios.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precioCompra = 0, precioVenta = 0;
        int stock = 0, stockMinimo = 0;
        try {
            precioCompra = Double.parseDouble(campoPrecioCompra.getText().trim());
            precioVenta = Double.parseDouble(campoPrecioVenta.getText().trim());
            stock = Integer.parseInt(campoStock.getText().trim());
            stockMinimo = Integer.parseInt(campoStockMinimo.getText().trim());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Los valores de Precios y Existencias deben ser numéricos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Producto producto = new Producto(codigo, campoNombre.getText().trim(), precioCompra, precioVenta, stock, stockMinimo);

        try {
            ProductoDao dao = new ProductoDao();
            if (e.getSource() == botonGuardar) {
                dao.registrarProducto(producto);
                JOptionPane.showMessageDialog(this, "¡Producto registrado con éxito en el inventario!");
            } else if (e.getSource() == botonActualizar) {
                dao.actualizarProducto(producto);
                JOptionPane.showMessageDialog(this, "¡Los cambios del producto fueron guardados con éxito!");
            }
            limpiarCampos();
            cargarDatosTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en la base de datos: " + ex.getMessage(), "Error de Operación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        campoCodigo.setText(""); 
        campoNombre.setText(""); 
        campoPrecioCompra.setText("");
        campoPrecioVenta.setText(""); 
        campoStock.setText(""); 
        campoStockMinimo.setText("");
        campoCodigo.setEditable(true);
        tablaProductos.clearSelection();
    }
}