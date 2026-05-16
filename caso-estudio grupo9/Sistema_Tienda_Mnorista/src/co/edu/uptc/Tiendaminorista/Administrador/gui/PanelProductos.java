package co.edu.uptc.Tiendaminorista.Administrador.gui;
import co.edu.uptc.Tiendaminorista.Gui.Evento;

import co.edu.uptc.Datos.CategoriaProducto;
import co.edu.uptc.Datos.Productodt;
import co.edu.uptc.Tiendaminorista.persistencia.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelProductos extends JPanel {

    // Campos del formulario
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<CategoriaProducto> comboCategorias;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStockActual;
    private JTextField txtStockMinimo;
    private JTextField txtBuscar;

    // Tabla
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Botones
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnReporteJSON;

    public PanelProductos(Evento e) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(crearPanelTitulo(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelBotones(e), BorderLayout.SOUTH);

        cargarTabla();
        configurarListeners(e);
    }

    public PanelProductos() {
        this(null);
    }

    // ─────────────────── CONSTRUCCIÓN DE UI ───────────────────

    private JPanel crearPanelTitulo() {
        JPanel panelTitulo = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Productos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.add(new JLabel("Buscar por código:"));
        txtBuscar = new JTextField(15);
        panelBusqueda.add(txtBuscar);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(ev -> buscarProducto());
        panelBusqueda.add(btnBuscar);
        JButton btnVerTodos = new JButton("Ver todos");
        btnVerTodos.addActionListener(ev -> {
            txtBuscar.setText("");
            cargarTabla();
        });
        panelBusqueda.add(btnVerTodos);

        panelTitulo.add(titulo, BorderLayout.WEST);
        panelTitulo.add(panelBusqueda, BorderLayout.EAST);
        return panelTitulo;
    }

    private JPanel crearPanelCentral() {
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del producto"));
        panelForm.setPreferredSize(new Dimension(320, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtCodigo = new JTextField(15);
        txtNombre = new JTextField(15);
        comboCategorias = new JComboBox<>(CategoriaProducto.values());
        txtPrecioCompra = new JTextField(15);
        txtPrecioVenta = new JTextField(15);
        txtStockActual = new JTextField(15);
        txtStockMinimo = new JTextField(15);

        Object[][] campos = {
            {"Código:", txtCodigo},
            {"Nombre:", txtNombre},
            {"Categoría:", comboCategorias},
            {"Precio compra ($):", txtPrecioCompra},
            {"Precio venta ($):", txtPrecioVenta},
            {"Stock actual:", txtStockActual},
            {"Stock mínimo:", txtStockMinimo}
        };

        for (int i = 0; i < campos.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            panelForm.add(new JLabel((String) campos[i][0]), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            panelForm.add((Component) campos[i][1], gbc);
        }

        String[] columnas = {"Código", "Nombre", "Categoría", "P. Compra", "P. Venta", "Stock", "Stock Mín.", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setRowHeight(22);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(550, 300));

        panelCentral.add(panelForm, BorderLayout.WEST);
        panelCentral.add(scroll, BorderLayout.CENTER);
        return panelCentral;
    }

    private JPanel crearPanelBotones(Evento e) {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Acciones"));

        btnAgregar    = new JButton("Agregar");
        btnModificar  = new JButton("Modificar");
        btnEliminar   = new JButton("Eliminar");
        btnLimpiar    = new JButton("Limpiar campos");
        btnReporteJSON = new JButton("Generar reporte JSON");

        btnAgregar.setBackground(new Color(70, 130, 180));
        btnAgregar.setForeground(Color.WHITE);
        btnModificar.setBackground(new Color(60, 179, 113));
        btnModificar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(205, 92, 92));
        btnEliminar.setForeground(Color.WHITE);
        btnReporteJSON.setBackground(new Color(148, 103, 189));
        btnReporteJSON.setForeground(Color.WHITE);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnReporteJSON);
        return panelBotones;
    }

    // ─────────────────── LISTENERS ───────────────────

    private void configurarListeners(Evento evento) {
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    String cat = modeloTabla.getValueAt(fila, 2).toString();
                    try { comboCategorias.setSelectedItem(CategoriaProducto.valueOf(cat)); } catch (Exception ignored) {}
                    txtPrecioCompra.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtPrecioVenta.setText(modeloTabla.getValueAt(fila, 4).toString());
                    txtStockActual.setText(modeloTabla.getValueAt(fila, 5).toString());
                    txtStockMinimo.setText(modeloTabla.getValueAt(fila, 6).toString());
                    txtCodigo.setEditable(false);
                }
            }
        });

        btnAgregar.addActionListener(ev -> agregarProducto());
        btnModificar.addActionListener(ev -> modificarProducto());
        btnEliminar.addActionListener(ev -> eliminarProducto());
        btnLimpiar.addActionListener(ev -> limpiarCampos());
        btnReporteJSON.addActionListener(ev -> generarReporteJSON());
    }

    // ─────────────────── OPERACIONES CRUD ───────────────────

    private void agregarProducto() {
        if (!validarCampos()) return;

        String codigo = txtCodigo.getText().trim();
        if (Producto_negocio.getInstance().existeCodigo(codigo)) {
            JOptionPane.showMessageDialog(this, "Ya existe un producto con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Productodt p = construirProductoDesdeFormulario();
        Producto_negocio.getInstance().agregarProducto(p);
        cargarTabla();
        limpiarCampos();
        JOptionPane.showMessageDialog(this, "Producto agregado y guardado en JSON.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void modificarProducto() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;

        Productodt p = construirProductoDesdeFormulario();
        boolean ok = Producto_negocio.getInstance().actualizarProducto(p);
        if (ok) {
            cargarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto actualizado y guardado en JSON.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String codigo = modeloTabla.getValueAt(fila, 0).toString();
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el producto con código " + codigo + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            Producto_negocio.getInstance().eliminarProducto(codigo);
            cargarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Producto eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void buscarProducto() {
        String busqueda = txtBuscar.getText().trim();
        if (busqueda.isEmpty()) { cargarTabla(); return; }
        modeloTabla.setRowCount(0);
        for (Productodt p : Producto_negocio.getInstance().getListaProductos()) {
            if (p.getCodigo().contains(busqueda) || p.getNombre().toLowerCase().contains(busqueda.toLowerCase())) {
                agregarFilaTabla(p);
            }
        }
    }

    // ─────────────────── REPORTE JSON ───────────────────

    private void generarReporteJSON() {
        java.util.List<Productodt> lista = Producto_negocio.getInstance().getListaProductos();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos para exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(lista);

        // Ruta fija al archivo de persistencia del proyecto
        String rutaArchivo = "Sistema_Tienda_Mnorista/productos.json";

        try (java.io.FileWriter fw = new java.io.FileWriter(rutaArchivo, false)) { // false = sobrescribir
            fw.write(json);
            double totalInventario = lista.stream()
                .mapToDouble(p -> p.getPrecioVenta() * p.getStockActual())
                .sum();
            JOptionPane.showMessageDialog(this,
                "Archivo actualizado exitosamente:\n" + rutaArchivo
                + "\n\nProductos: " + lista.size()
                + "\nValor total inventario: $" + String.format("%.2f", totalInventario),
                "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ─────────────────── UTILIDADES ───────────────────

    private Productodt construirProductoDesdeFormulario() {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        CategoriaProducto categoria = (CategoriaProducto) comboCategorias.getSelectedItem();
        double precioCompra = Double.parseDouble(txtPrecioCompra.getText().trim());
        double precioVenta  = Double.parseDouble(txtPrecioVenta.getText().trim());
        int stockActual     = Integer.parseInt(txtStockActual.getText().trim());
        int stockMinimo     = Integer.parseInt(txtStockMinimo.getText().trim());
        return new Productodt(codigo, nombre, categoria, precioCompra, precioVenta, stockActual, stockMinimo);
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()
                || txtNombre.getText().trim().isEmpty()
                || txtPrecioCompra.getText().trim().isEmpty()
                || txtPrecioVenta.getText().trim().isEmpty()
                || txtStockActual.getText().trim().isEmpty()
                || txtStockMinimo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double pc = Double.parseDouble(txtPrecioCompra.getText().trim());
            double pv = Double.parseDouble(txtPrecioVenta.getText().trim());
            int sa   = Integer.parseInt(txtStockActual.getText().trim());
            int sm   = Integer.parseInt(txtStockMinimo.getText().trim());
            if (pc < 0 || pv < 0 || sa < 0 || sm < 0) {
                JOptionPane.showMessageDialog(this, "Los valores numéricos no pueden ser negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precios y stocks deben ser valores numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Productodt p : Producto_negocio.getInstance().getListaProductos()) {
            agregarFilaTabla(p);
        }
    }

    private void agregarFilaTabla(Productodt p) {
        String estado = p.isActivo() ? "Activo" : "Inactivo";
        modeloTabla.addRow(new Object[]{
            p.getCodigo(),
            p.getNombre(),
            p.getCategoria().name(),
            p.getPrecioCompra(),
            p.getPrecioVenta(),
            p.getStockActual(),
            p.getStockMinimo(),
            estado
        });
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        comboCategorias.setSelectedIndex(0);
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtStockActual.setText("");
        txtStockMinimo.setText("");
        txtCodigo.setEditable(true);
        tabla.clearSelection();
    }
}