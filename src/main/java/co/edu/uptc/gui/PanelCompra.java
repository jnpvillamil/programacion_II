package co.edu.uptc.gui;

import co.edu.uptc.controlador.ControladorCompra;
import co.edu.uptc.controlador.ControladorProducto;
import co.edu.uptc.controlador.ControladorProveedor;
import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleCompra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.utilidades.ConstructorComponentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PanelCompra extends JPanel {

    private ControladorCompra controladorCompra;
    private ControladorProducto controladorProducto;
    private ControladorProveedor controladorProveedor;

    // Cabecera de la compra
    private JTextField txtNumeroFactura;
    private JComboBox<String> cbProveedor;

    // Añadir producto al detalle
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JTextField txtCostoUnitario;

    // Tabla de detalles
    private DefaultTableModel modeloTablaDetalle;
    private JTable tablaDetalle;

    // Tabla de compras registradas
    private DefaultTableModel modeloTablaCompras;
    private JTable tablaCompras;

    // Totales
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    // Lista en memoria del detalle actual
    private List<DetalleCompra> detallesActuales;

    public PanelCompra(ControladorCompra controladorCompra,
                       ControladorProducto controladorProducto,
                       ControladorProveedor controladorProveedor) {
        this.controladorCompra = controladorCompra;
        this.controladorProducto = controladorProducto;
        this.controladorProveedor = controladorProveedor;
        this.detallesActuales = new ArrayList<>();

        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = ConstructorComponentes.crearEtiquetaNegrita("GESTIÓN DE COMPRAS");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        panelCentro.add(construirFormularioCabecera(), BorderLayout.NORTH);
        panelCentro.add(construirSeccionDetalle(), BorderLayout.CENTER);
        panelCentro.add(construirPanelTotalesYBotones(), BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.CENTER);
        add(construirTablaComprasRegistradas(), BorderLayout.SOUTH);

        cargarProveedores();
        actualizarTablaCompras();
    }

    // ── FORMULARIO DE CABECERA ──────────────────────────────────────────────
    private JPanel construirFormularioCabecera() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.weightx = 0.5;

        txtNumeroFactura = ConstructorComponentes.crearCampoTexto();
        cbProveedor = new JComboBox<>();

        gbc.gridy = 0; gbc.gridx = 0;
        panel.add(ConstructorComponentes.crearEtiquetaNegrita("Factura Proveedor:"), gbc);
        gbc.gridx = 1; panel.add(txtNumeroFactura, gbc);
        gbc.gridx = 2; panel.add(ConstructorComponentes.crearEtiquetaNegrita("Proveedor:"), gbc);
        gbc.gridx = 3; panel.add(cbProveedor, gbc);

        return panel;
    }

    // ── SECCIÓN AÑADIR PRODUCTO ─────────────────────────────────────────────
    private JPanel construirSeccionDetalle() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        // Fila de inputs para agregar producto
        JPanel panelInputProducto = new JPanel(new GridBagLayout());
        panelInputProducto.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.weightx = 0.3;

        txtCodigoProducto = ConstructorComponentes.crearCampoTexto();
        txtCantidad = ConstructorComponentes.crearCampoTexto();
        txtCostoUnitario = ConstructorComponentes.crearCampoTexto();
        JButton btnAgregar = ConstructorComponentes.crearBotonAccion("Añadir Producto", ConstructorComponentes.COLOR_AZUL_ACCION);
        btnAgregar.addActionListener(e -> agregarProductoDetalle());

        gbc.gridy = 0; gbc.gridx = 0;
        panelInputProducto.add(ConstructorComponentes.crearEtiquetaNegrita("Código Producto:"), gbc);
        gbc.gridx = 1; panelInputProducto.add(txtCodigoProducto, gbc);
        gbc.gridx = 2; panelInputProducto.add(ConstructorComponentes.crearEtiquetaNegrita("Cantidad:"), gbc);
        gbc.gridx = 3; panelInputProducto.add(txtCantidad, gbc);
        gbc.gridx = 4; panelInputProducto.add(ConstructorComponentes.crearEtiquetaNegrita("Costo Unitario ($):"), gbc);
        gbc.gridx = 5; panelInputProducto.add(txtCostoUnitario, gbc);
        gbc.gridx = 6; gbc.weightx = 0; panelInputProducto.add(btnAgregar, gbc);

        // Tabla de detalle de la compra actual
        String[] cols = {"Código", "Descripción", "Cantidad", "Costo Unitario", "Subtotal"};
        modeloTablaDetalle = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaDetalle = new JTable(modeloTablaDetalle);
        ConstructorComponentes.darEstiloTabla(tablaDetalle);
        tablaDetalle.setFillsViewportHeight(true);
        tablaDetalle.setBackground(Color.WHITE);
        tablaDetalle.getTableHeader().setOpaque(false);
        JScrollPane scroll = new JScrollPane(tablaDetalle);
        scroll.setPreferredSize(new Dimension(0, 160));

        panel.add(panelInputProducto, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ── TOTALES Y BOTONES ───────────────────────────────────────────────────
    private JPanel construirPanelTotalesYBotones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        // Totales alineados a la derecha
        JPanel panelTotales = new JPanel(new GridLayout(3, 2, 5, 2));
        panelTotales.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        lblSubtotal = ConstructorComponentes.crearEtiquetaNegrita("Subtotal: $ 0.00");
        lblIva      = ConstructorComponentes.crearEtiquetaNegrita("IVA (19%): $ 0.00");
        lblTotal    = ConstructorComponentes.crearEtiquetaNegrita("Total: $ 0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelTotales.add(new JLabel()); panelTotales.add(lblSubtotal);
        panelTotales.add(new JLabel()); panelTotales.add(lblIva);
        panelTotales.add(new JLabel()); panelTotales.add(lblTotal);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        JButton btnBuscar   = ConstructorComponentes.crearBotonAccion("Buscar",           ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnEliminar = ConstructorComponentes.crearBotonAccion("Quitar Producto",   ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnLimpiar  = ConstructorComponentes.crearBotonAccion("Limpiar",           ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnRegistrar= ConstructorComponentes.crearBotonAccion("Registrar Compra",  ConstructorComponentes.COLOR_VERDE_GUARDAR);

        btnBuscar.addActionListener(e -> buscarCompra());
        btnEliminar.addActionListener(e -> quitarProductoSeleccionado());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRegistrar.addActionListener(e -> registrarCompra());

        panelBotones.add(btnBuscar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRegistrar);

        panel.add(panelTotales, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.EAST);
        return panel;
    }

    // ── TABLA DE COMPRAS REGISTRADAS ────────────────────────────────────────
    private JScrollPane construirTablaComprasRegistradas() {
        JLabel lblTitulo = ConstructorComponentes.crearEtiquetaNegrita("Compras Registradas");

        String[] cols = {"N° Factura", "Proveedor", "Fecha", "Subtotal", "IVA", "Total"};
        modeloTablaCompras = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaCompras = new JTable(modeloTablaCompras);
        ConstructorComponentes.darEstiloTabla(tablaCompras);
        tablaCompras.setFillsViewportHeight(true);
        tablaCompras.setBackground(Color.WHITE);
        tablaCompras.getTableHeader().setOpaque(false);

        JScrollPane scroll = new JScrollPane(tablaCompras);
        scroll.getViewport().setBackground(Color.WHITE);  // ← ahora sí existe
        scroll.setPreferredSize(new Dimension(0, 180));
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de Compras"));
        return scroll;
    }

    // ── LÓGICA DE NEGOCIO ───────────────────────────────────────────────────
    private void cargarProveedores() {
        cbProveedor.removeAllItems();
        try {
        	List<ProveedorResumenDTO> proveedores = controladorProveedor.obtenerListadoResumen();
        	for (ProveedorResumenDTO p : proveedores) {
        	    cbProveedor.addItem(p.getNit() + " - " + p.getRazonSocial());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los proveedores.");
        }
    }

    private void agregarProductoDetalle() {
        try {
            String codigo = txtCodigoProducto.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            double costoUnitario = Double.parseDouble(txtCostoUnitario.getText().trim());

            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el código del producto.");
                return;
            }
            if (cantidad <= 0 || costoUnitario <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad y el costo unitario deben ser mayores a cero.");
                return;
            }

            Producto producto = controladorProducto.buscarProducto(codigo);
            if (producto == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado con el código: " + codigo);
                return;
            }

            DetalleCompra detalle = new DetalleCompra(producto, cantidad, costoUnitario);
            detallesActuales.add(detalle);

            modeloTablaDetalle.addRow(new Object[]{
                producto.getCodigoInterno(),
                producto.getNombreProducto(),
                cantidad,
                String.format("$ %.2f", costoUnitario),
                String.format("$ %.2f", detalle.getSubtotal())
            });

            actualizarTotales();
            txtCodigoProducto.setText("");
            txtCantidad.setText("");
            txtCostoUnitario.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Verifique que la cantidad y el costo sean números válidos.");
        }
    }

    private void quitarProductoSeleccionado() {
        int fila = tablaDetalle.getSelectedRow();
        if (fila >= 0) {
            detallesActuales.remove(fila);
            modeloTablaDetalle.removeRow(fila);
            actualizarTotales();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla para quitarlo.");
        }
    }

    private void registrarCompra() {
        try {
            String numeroFactura = txtNumeroFactura.getText().trim();
            if (cbProveedor.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un proveedor.");
                return;
            }

            // Obtener NIT del proveedor desde el combo (formato "NIT - Nombre")
            String seleccion = cbProveedor.getSelectedItem().toString();
            String nit = seleccion.split(" - ")[0].trim();
            Proveedor proveedor = controladorProveedor.buscarProveedor(nit);

            Compra compra = new Compra(numeroFactura, LocalDateTime.now(), proveedor);
            compra.setListaDetalles(new ArrayList<>(detallesActuales));

            controladorCompra.registrarCompra(compra);
            JOptionPane.showMessageDialog(this,
                "Compra registrada exitosamente.\n" +
                "Subtotal: $ " + String.format("%.2f", compra.getSubtotal()) + "\n" +
                "IVA (19%): $ " + String.format("%.2f", compra.getIva()) + "\n" +
                "Total: $ " + String.format("%.2f", compra.getTotal()));

            actualizarTablaCompras();
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar la compra: " + ex.getMessage());
        }
    }

    private void buscarCompra() {
        String numero = JOptionPane.showInputDialog(this, "Ingrese el número de factura a buscar:");
        if (numero != null && !numero.trim().isEmpty()) {
            Compra compra = controladorCompra.buscarCompra(numero.trim());
            if (compra != null) {
                JOptionPane.showMessageDialog(this,
                    "Factura: " + compra.getNumeroFacturaProveedor() + "\n" +
                    		"Proveedor: " + compra.getProveedor().getRazonSocial() + "\n" +
                    "Fecha: " + compra.getFecha() + "\n" +
                    "Subtotal: $ " + String.format("%.2f", compra.getSubtotal()) + "\n" +
                    "IVA: $ " + String.format("%.2f", compra.getIva()) + "\n" +
                    "Total: $ " + String.format("%.2f", compra.getTotal()) + "\n" +
                    "Productos: " + compra.getListaDetalles().size());
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró una compra con esa factura.");
            }
        }
    }

    private void actualizarTotales() {
        double subtotal = 0;
        for (DetalleCompra d : detallesActuales) {
            subtotal += d.getSubtotal();
        }
        double iva   = subtotal * 0.19;
        double total = subtotal + iva;

        lblSubtotal.setText(String.format("Subtotal: $ %.2f", subtotal));
        lblIva.setText(String.format("IVA (19%%): $ %.2f", iva));
        lblTotal.setText(String.format("Total: $ %.2f", total));
    }

    private void actualizarTablaCompras() {
        modeloTablaCompras.setRowCount(0);
        List<CompraDTO> lista = controladorCompra.obtenerListadoResumen();
        for (CompraDTO dto : lista) {
            modeloTablaCompras.addRow(new Object[]{
                dto.getNumeroFacturaProveedor(),
                dto.getNombreProveedor(),
                dto.getFecha() != null ? dto.getFecha().toLocalDate() : "",
                String.format("$ %.2f", dto.getSubtotal()),
                String.format("$ %.2f", dto.getIva()),
                String.format("$ %.2f", dto.getTotal())
            });
        }
    }

    private void limpiarFormulario() {
        txtNumeroFactura.setText("");
        txtCodigoProducto.setText("");
        txtCantidad.setText("");
        txtCostoUnitario.setText("");
        detallesActuales.clear();
        modeloTablaDetalle.setRowCount(0);
        actualizarTotales();
        if (cbProveedor.getItemCount() > 0) cbProveedor.setSelectedIndex(0);
    }
}