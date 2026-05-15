
package co.edu.uptc.gui;

import co.edu.uptc.controlador.ControladorProducto;
import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.enums.CategoriaProducto;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.utilidades.ConstructorComponentes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelProducto extends JPanel {
    private ControladorProducto controlador;
    private JTextField txtCodigo, txtNombre, txtPrecioCompra, txtPrecioVenta, txtStockActual, txtStockMinimo, txtStockMaximo;
    private JComboBox<CategoriaProducto> cbCategoria;
    private DefaultTableModel modeloTabla;
    private JTable tablaProductos;

    public PanelProducto(ControladorProducto controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(ConstructorComponentes.crearEtiquetaNegrita("GESTIÓN DE PRODUCTOS E INVENTARIO"), BorderLayout.NORTH);
        
        inicializarFormulario();
        inicializarTabla();
    }

    private void inicializarFormulario() {
        JPanel panelContenedorForm = new JPanel(new BorderLayout());
        panelContenedorForm.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.weightx = 0.5;

        txtCodigo = ConstructorComponentes.crearCampoTexto();
        txtNombre = ConstructorComponentes.crearCampoTexto();
        cbCategoria = new JComboBox<>(CategoriaProducto.values());
        txtPrecioCompra = ConstructorComponentes.crearCampoTexto();
        txtPrecioVenta = ConstructorComponentes.crearCampoTexto();
        txtStockActual = ConstructorComponentes.crearCampoTexto();
        txtStockMinimo = ConstructorComponentes.crearCampoTexto();
        txtStockMaximo = ConstructorComponentes.crearCampoTexto();

        // Fila 1
        gbc.gridy = 0; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Código Interno:"), gbc);
        gbc.gridx = 1; panelForm.add(txtCodigo, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Nombre Producto:"), gbc);
        gbc.gridx = 3; panelForm.add(txtNombre, gbc);

        // Fila 2
        gbc.gridy = 1; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Categoría:"), gbc);
        gbc.gridx = 1; panelForm.add(cbCategoria, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Stock Inicial:"), gbc);
        gbc.gridx = 3; panelForm.add(txtStockActual, gbc);

        // Fila 3
        gbc.gridy = 2; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Precio Compra ($):"), gbc);
        gbc.gridx = 1; panelForm.add(txtPrecioCompra, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Stock Mínimo:"), gbc);
        gbc.gridx = 3; panelForm.add(txtStockMinimo, gbc);

        // Fila 4
        gbc.gridy = 3; gbc.gridx = 0; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Precio Venta ($):"), gbc);
        gbc.gridx = 1; panelForm.add(txtPrecioVenta, gbc);
        gbc.gridx = 2; panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Stock Máximo:"), gbc);
        gbc.gridx = 3; panelForm.add(txtStockMaximo, gbc);

        // Botones de acción AZULES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        
        JButton btnBuscar = ConstructorComponentes.crearBotonAccion("Buscar", ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnEditar = ConstructorComponentes.crearBotonAccion("Editar", ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnInactivar = ConstructorComponentes.crearBotonAccion("Inactivar", ConstructorComponentes.COLOR_AZUL_ACCION);
        JButton btnGuardar = ConstructorComponentes.crearBotonAccion("Guardar", ConstructorComponentes.COLOR_AZUL_ACCION);

        btnGuardar.addActionListener(e -> guardarProducto());
        btnBuscar.addActionListener(e -> buscarProducto());
        btnEditar.addActionListener(e -> editarProducto());
        btnInactivar.addActionListener(e -> inactivarProducto());

        panelBotones.add(btnBuscar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnInactivar);
        panelBotones.add(btnGuardar);

        panelContenedorForm.add(panelForm, BorderLayout.NORTH);
        panelContenedorForm.add(panelBotones, BorderLayout.SOUTH);
        add(panelContenedorForm, BorderLayout.CENTER);
    }

    private void inicializarTabla() {
        String[] columnas = {"Código", "Nombre", "Categoría", "P. Venta", "Stock", "Alerta"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        ConstructorComponentes.darEstiloTabla(tablaProductos);
        
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setPreferredSize(new Dimension(0, 250));
        add(scroll, BorderLayout.SOUTH);
    }

    private Producto extraerProductoFormulario() throws NumberFormatException {
        return new Producto(
            txtCodigo.getText().trim(),
            txtNombre.getText().trim(),
            (CategoriaProducto) cbCategoria.getSelectedItem(),
            Double.parseDouble(txtPrecioCompra.getText().trim()),
            Double.parseDouble(txtPrecioVenta.getText().trim()),
            Integer.parseInt(txtStockActual.getText().trim()),
            Integer.parseInt(txtStockMinimo.getText().trim()),
            Integer.parseInt(txtStockMaximo.getText().trim())
        );
    }

    private void guardarProducto() {
        try {
            Producto nuevo = extraerProductoFormulario();
            String msj = controlador.registrarProducto(nuevo);
            JOptionPane.showMessageDialog(this, msj);
            actualizarTabla();
            limpiarFormulario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Verifique que los campos de precios y stock contengan solo números.");
        }
    }

    private void buscarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código interno a buscar:");
        if (codigo != null && !codigo.trim().isEmpty()) {
            Producto p = controlador.buscarProducto(codigo.trim());
            if (p != null) {
                txtCodigo.setText(p.getCodigoInterno());
                txtNombre.setText(p.getNombreProducto());
                cbCategoria.setSelectedItem(p.getCategoria());
                txtPrecioCompra.setText(String.valueOf(p.getPrecioCompra()));
                txtPrecioVenta.setText(String.valueOf(p.getPrecioVenta()));
                txtStockActual.setText(String.valueOf(p.getStockActual()));
                txtStockMinimo.setText(String.valueOf(p.getStockMinimo()));
                txtStockMaximo.setText(String.valueOf(p.getStockMaximo()));
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        }
    }

    private void editarProducto() {
        try {
            Producto p = extraerProductoFormulario();
            String msj = controlador.modificarProducto(p);
            JOptionPane.showMessageDialog(this, msj);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error de formato numérico.");
        }
    }

    private void inactivarProducto() {
        String codigo = txtCodigo.getText().trim();
        if (!codigo.isEmpty()) {
            String msj = controlador.inactivarProducto(codigo);
            JOptionPane.showMessageDialog(this, msj);
            actualizarTabla();
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, "Busque un producto primero para inactivarlo.");
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<ProductoResumenDTO> lista = controlador.obtenerListadoResumen();
        for (ProductoResumenDTO dto : lista) {
            modeloTabla.addRow(new Object[]{
                dto.getCodigo(), dto.getNombre(), dto.getCategoria(), 
                "$" + dto.getPrecioVenta(), dto.getStockActual(), dto.getAlertaMinima()
            });
        }
    }

    private void limpiarFormulario() {
        txtCodigo.setText(""); txtNombre.setText("");
        txtPrecioCompra.setText(""); txtPrecioVenta.setText("");
        txtStockActual.setText(""); txtStockMinimo.setText(""); txtStockMaximo.setText("");
    }
}
