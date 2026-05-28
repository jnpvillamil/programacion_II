package co.edu.uptc.tiendaminorista.gui.administrador;

import co.edu.uptc.tiendaminorista.gui.Evento;
import co.edu.uptc.tiendaminorista.enums.CategoriaProducto;
import co.edu.uptc.tiendaminorista.modelo.Producto;
import co.edu.uptc.tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.tiendaminorista.persistencia.LocalProducto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PanelProductos extends JPanel {

    // campos del formulario
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JComboBox<CategoriaProducto> comboCategorias;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStockActual;
    private JTextField txtStockMinimo;
    private JTextField txtBuscar;

    // tabla de productos
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // botones de accion
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnActivar;
    private JButton btnLimpiar;
    private JButton btnReporteJSON;
    private JButton btnStockBajo;

    // capa de negocio
    private GestionProducto gestionProducto;

    public PanelProductos(Evento e) {
        this.gestionProducto = new GestionProducto(new LocalProducto());
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

    // ───────── CONSTRUCCION DE PANTALLA ─────────

    private JPanel crearPanelTitulo() {
        JPanel panelTitulo = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Productos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.add(new JLabel("Buscar:"));
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

        // formulario de datos
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

        // tabla con columnas del caso de estudio
        String[] columnas = {"Código", "Nombre", "Categoría", "P.Compra", "P.Venta", "Stock", "StockMín.", "Estado"};
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

        btnAgregar     = new JButton("Agregar");
        btnModificar   = new JButton("Modificar");
        btnEliminar    = new JButton("Inactivar");
        btnActivar     = new JButton("Activar");
        btnLimpiar     = new JButton("Limpiar");
        btnReporteJSON = new JButton("Reporte JSON");
        btnStockBajo   = new JButton("Stock bajo");

        btnAgregar.setBackground(new Color(70, 130, 180));
        btnAgregar.setForeground(Color.WHITE);
        btnModificar.setBackground(new Color(60, 179, 113));
        btnModificar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(205, 92, 92));
        btnEliminar.setForeground(Color.WHITE);
        btnActivar.setBackground(new Color(255, 165, 0));
        btnActivar.setForeground(Color.WHITE);
        btnReporteJSON.setBackground(new Color(148, 103, 189));
        btnReporteJSON.setForeground(Color.WHITE);
        btnStockBajo.setBackground(new Color(255, 99, 71));
        btnStockBajo.setForeground(Color.WHITE);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActivar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnReporteJSON);
        panelBotones.add(btnStockBajo);
        return panelBotones;
    }

    // ───────── LISTENERS / EVENTOS ─────────

    private void configurarListeners(Evento evento) {
        // al hacer click en la tabla se cargan los datos en el formulario
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    String cat = modeloTabla.getValueAt(fila, 2).toString();
                    try {
                        comboCategorias.setSelectedItem(CategoriaProducto.valueOf(cat));
                    } catch (Exception ignored) {}
                    txtPrecioCompra.setText(modeloTabla.getValueAt(fila, 3).toString());
                    txtPrecioVenta.setText(modeloTabla.getValueAt(fila, 4).toString());
                    txtStockActual.setText(modeloTabla.getValueAt(fila, 5).toString());
                    txtStockMinimo.setText(modeloTabla.getValueAt(fila, 6).toString());
                    // no dejar editar el codigo al modificar
                    txtCodigo.setEditable(false);
                }
            }
        });

        btnAgregar.addActionListener(ev -> agregarProducto());
        btnModificar.addActionListener(ev -> modificarProducto());
        btnEliminar.addActionListener(ev -> inactivarProducto());
        btnActivar.addActionListener(ev -> activarProducto());
        btnLimpiar.addActionListener(ev -> limpiarCampos());
        btnReporteJSON.addActionListener(ev -> generarReporteJSON());
        btnStockBajo.addActionListener(ev -> mostrarProductosBajoStock());
    }

    // ───────── OPERACIONES CRUD ─────────

    private void agregarProducto() {
        if (!validarCampos()) return;

        String codigo = txtCodigo.getText().trim();

        // verificar que no exista ya ese codigo en la BD
        Producto existente = gestionProducto.buscarPorCodigo(codigo);
        if (existente != null) {
            JOptionPane.showMessageDialog(this,
                "Ya existe un producto con el código: " + codigo,
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Producto p = construirProductoDesdeFormulario();
        try {
            gestionProducto.registrarProducto(p);
            cargarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this,
                "Producto guardado en la base de datos.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Primero seleccione un producto de la tabla.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;

        Producto p = construirProductoDesdeFormulario();
        gestionProducto.actualizarProducto(p);
        cargarTabla();
        limpiarCampos();
        JOptionPane.showMessageDialog(this,
            "Producto actualizado en la base de datos.",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    // inactivar - no borra el registro, solo cambia el estado a inactivo
    private void inactivarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un producto de la tabla para inactivar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = modeloTabla.getValueAt(fila, 0).toString();
        String estado = modeloTabla.getValueAt(fila, 7).toString();

        if ("Inactivo".equals(estado)) {
            JOptionPane.showMessageDialog(this,
                "El producto ya está inactivo. Use 'Activar' para reactivarlo.",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Inactivar el producto con código " + codigo + "?\n"
            + "El producto quedará registrado pero no estará disponible.",
            "Confirmar inactivación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            gestionProducto.desactivarProducto(codigo);
            cargarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this,
                "Producto inactivado correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // activar un producto que estaba inactivo
    private void activarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccione un producto de la tabla para activar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = modeloTabla.getValueAt(fila, 0).toString();
        String estado = modeloTabla.getValueAt(fila, 7).toString();

        if ("Activo".equals(estado)) {
            JOptionPane.showMessageDialog(this,
                "El producto ya está activo.",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        gestionProducto.activarProducto(codigo);
        cargarTabla();
        limpiarCampos();
        JOptionPane.showMessageDialog(this,
            "Producto activado correctamente.",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    // buscar en la tabla por codigo o nombre
    private void buscarProducto() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            cargarTabla();
            return;
        }
        modeloTabla.setRowCount(0);
        for (Producto p : gestionProducto.listarProductos()) {
            if (p.getCodigo().toLowerCase().contains(busqueda)
                    || p.getNombre().toLowerCase().contains(busqueda)) {
                agregarFilaTabla(p);
            }
        }
    }

    // mostrar productos con stock por debajo del minimo
    private void mostrarProductosBajoStock() {
        List<Producto> bajos = gestionProducto.listarProductosBajoStock();
        if (bajos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos los productos tienen stock suficiente.",
                "Stock OK", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modeloTabla.setRowCount(0);
        for (Producto p : bajos) {
            agregarFilaTabla(p);
        }
        JOptionPane.showMessageDialog(this,
            "Se encontraron " + bajos.size() + " producto(s) bajo el stock mínimo.",
            "Alerta de stock", JOptionPane.WARNING_MESSAGE);
    }

    // ───────── REPORTE JSON ─────────

    private void generarReporteJSON() {
        List<Producto> lista = gestionProducto.listarProductos();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No hay productos para exportar.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(lista);
        String rutaArchivo = "reporte_productos.json";

        try (java.io.FileWriter fw = new java.io.FileWriter(rutaArchivo, false)) {
            fw.write(json);
            double totalInventario = gestionProducto.calcularValorInventario();
            long productosActivos = lista.stream().filter(Producto::isActivo).count();
            JOptionPane.showMessageDialog(this,
                "Reporte exportado a: " + rutaArchivo
                + "\n\nTotal productos: " + lista.size()
                + "\nProductos activos: " + productosActivos
                + "\nValor total inventario: $" + String.format("%,.2f", totalInventario),
                "Reporte generado", JOptionPane.INFORMATION_MESSAGE);
        } catch (java.io.IOException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar el reporte: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ───────── UTILIDADES ─────────

    // construir objeto Producto a partir de lo que el usuario ingreso
    private Producto construirProductoDesdeFormulario() {
        Producto p = new Producto();
        p.setCodigo(txtCodigo.getText().trim());
        p.setNombre(txtNombre.getText().trim());
        p.setCategoria((CategoriaProducto) comboCategorias.getSelectedItem());
        p.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText().trim()));
        p.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText().trim()));
        p.setStockActual(Integer.parseInt(txtStockActual.getText().trim()));
        p.setStockMinimo(Integer.parseInt(txtStockMinimo.getText().trim()));
        p.setActivo(true);
        return p;
    }

    // validar que todos los campos requeridos esten llenos y sean validos
    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()
                || txtNombre.getText().trim().isEmpty()
                || txtPrecioCompra.getText().trim().isEmpty()
                || txtPrecioVenta.getText().trim().isEmpty()
                || txtStockActual.getText().trim().isEmpty()
                || txtStockMinimo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos los campos son obligatorios.",
                "Campos incompletos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double pc = Double.parseDouble(txtPrecioCompra.getText().trim());
            double pv = Double.parseDouble(txtPrecioVenta.getText().trim());
            int sa   = Integer.parseInt(txtStockActual.getText().trim());
            int sm   = Integer.parseInt(txtStockMinimo.getText().trim());
            if (pc < 0 || pv < 0 || sa < 0 || sm < 0) {
                JOptionPane.showMessageDialog(this,
                    "Los valores numéricos no pueden ser negativos.",
                    "Error de valores", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Precios y stocks deben ser números válidos.",
                "Error de formato", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // cargar todos los productos en la tabla
    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : gestionProducto.listarProductos()) {
            agregarFilaTabla(p);
        }
    }

    // agregar una fila a la tabla con los datos del producto
    private void agregarFilaTabla(Producto p) {
        String estado = p.isActivo() ? "Activo" : "Inactivo";
        modeloTabla.addRow(new Object[]{
            p.getCodigo(),
            p.getNombre(),
            p.getCategoria() != null ? p.getCategoria().name() : "",
            String.format("%.2f", p.getPrecioCompra()),
            String.format("%.2f", p.getPrecioVenta()),
            p.getStockActual(),
            p.getStockMinimo(),
            estado
        });
    }

    // limpiar todos los campos del formulario
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
