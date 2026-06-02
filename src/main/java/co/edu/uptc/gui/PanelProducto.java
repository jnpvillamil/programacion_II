package co.edu.uptc.gui;

import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.interfaces.ManejadorEventoAdministracion;
import co.edu.uptc.enums.CategoriaProducto;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.ConstructorComponentes;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelProducto extends JPanel {

    private final ManejadorEventoAdministracion manejadorEvento;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStockActual;
    private JTextField txtStockMinimo;
    private JTextField txtStockMaximo;
    private JComboBox<CategoriaProducto> cbCategoria;
    private DefaultTableModel modeloTabla;
    private JTable tablaProducto;
    private JButton botonCambiarEstado;

    public PanelProducto(ManejadorEventoAdministracion manejadorEvento) {
        this.manejadorEvento = manejadorEvento;
        setLayout(new BorderLayout(20, 20));
        ConstructorComponentes.aplicarFondoPanel(this);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(ConstructorComponentes.crearTituloModulo("Gestión de Productos e Inventario"), BorderLayout.NORTH);

        inicializarFormulario();
        inicializarTabla();
        actualizarTabla();
    }

    private void inicializarFormulario() {
        JPanel panelContenedorForm = new JPanel(new BorderLayout());
        ConstructorComponentes.aplicarFondoPanel(panelContenedorForm);

        JPanel panelForm = new JPanel(new GridBagLayout());
        ConstructorComponentes.aplicarFondoPanel(panelForm);
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

        gbc.gridy = 0;
        gbc.gridx = 0;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Código Interno:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtCodigo, gbc);
        gbc.gridx = 2;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Nombre Producto:"), gbc);
        gbc.gridx = 3;
        panelForm.add(txtNombre, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Categoría:"), gbc);
        gbc.gridx = 1;
        panelForm.add(cbCategoria, gbc);
        gbc.gridx = 2;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Stock Inicial:"), gbc);
        gbc.gridx = 3;
        panelForm.add(txtStockActual, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Precio Compra ($):"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtPrecioCompra, gbc);
        gbc.gridx = 2;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Stock Mínimo:"), gbc);
        gbc.gridx = 3;
        panelForm.add(txtStockMinimo, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Precio Venta ($):"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtPrecioVenta, gbc);
        gbc.gridx = 2;
        panelForm.add(ConstructorComponentes.crearEtiquetaNegrita("Stock Máximo:"), gbc);
        gbc.gridx = 3;
        panelForm.add(txtStockMaximo, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ConstructorComponentes.aplicarFondoPanel(panelBotones);

        JButton btnBuscar = ConstructorComponentes.crearBotonInformativo("Buscar");
        JButton btnEditar = ConstructorComponentes.crearBotonEditar("Editar");
        botonCambiarEstado = ConstructorComponentes.crearBotonCambiarEstado();
        JButton btnGuardar = ConstructorComponentes.crearBotonGuardar("Guardar");

        btnGuardar.addActionListener(evento -> guardarProducto());
        btnBuscar.addActionListener(evento -> buscarProducto());
        btnEditar.addActionListener(evento -> editarProducto());
        botonCambiarEstado.addActionListener(evento -> cambiarEstadoProducto());

        panelBotones.add(btnBuscar);
        panelBotones.add(btnEditar);
        panelBotones.add(botonCambiarEstado);
        panelBotones.add(btnGuardar);

        panelContenedorForm.add(panelForm, BorderLayout.NORTH);
        panelContenedorForm.add(panelBotones, BorderLayout.SOUTH);
        add(panelContenedorForm, BorderLayout.CENTER);
    }

    private void inicializarTabla() {
        String[] columnas = {"Código", "Nombre", "Categoría", "P. Venta", "Stock", "Alerta", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tablaProducto = new JTable(modeloTabla);
        ConstructorComponentes.darEstiloTabla(tablaProducto);
        tablaProducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProducto.getSelectionModel().addListSelectionListener(evento -> {
            if (evento.getValueIsAdjusting()) {
                return;
            }
            int fila = tablaProducto.getSelectedRow();
            if (fila >= 0) {
                cargarFilaSeleccionada(fila);
                String estado = String.valueOf(modeloTabla.getValueAt(fila, 6));
                if (ConstructorComponentes.esEstadoActivo(estado)) {
                    ConstructorComponentes.configurarBotonInactivar(botonCambiarEstado);
                } else {
                    ConstructorComponentes.configurarBotonActivar(botonCambiarEstado);
                }
            } else {
                ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
            }
        });

        JScrollPane scroll = new JScrollPane(tablaProducto);
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
            String mensaje = manejadorEvento.registrarProducto(nuevo);
            JOptionPane.showMessageDialog(this, mensaje);
            actualizarTabla();
            limpiarFormulario();
        } catch (NumberFormatException excepcion) {
            JOptionPane.showMessageDialog(this,
                    "Error: Verifique que los campos de precios y stock contengan solo números.");
        }
    }

    private void buscarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código interno a buscar:");
        if (codigo != null && !codigo.trim().isEmpty()) {
            Producto producto = manejadorEvento.buscarProducto(codigo.trim());
            if (producto != null) {
                cargarProductoEnFormulario(producto);
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        }
    }

    private void editarProducto() {
        try {
            Producto producto = extraerProductoFormulario();
            String mensaje = manejadorEvento.modificarProducto(producto);
            JOptionPane.showMessageDialog(this, mensaje);
            actualizarTabla();
        } catch (NumberFormatException excepcion) {
            JOptionPane.showMessageDialog(this, "Error de formato numérico.");
        }
    }

    private void cambiarEstadoProducto() {
        if (!botonCambiarEstado.isEnabled()) {
            return;
        }
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla o ingrese el código.");
            return;
        }

        boolean inactivar = "Inactivar".equals(botonCambiarEstado.getText());
        String accion = inactivar ? "inactivar" : "activar";
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea " + accion + " el producto con código " + codigo + "?",
                "Confirmar cambio de estado",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String mensaje = inactivar
                ? manejadorEvento.inactivarProducto(codigo)
                : manejadorEvento.activarProducto(codigo);
        JOptionPane.showMessageDialog(this, mensaje);
        if (mensaje.contains("correctamente")) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<ProductoResumenDTO> lista = manejadorEvento.obtenerListadoResumenProducto();
            for (ProductoResumenDTO dto : lista) {
                modeloTabla.addRow(new Object[]{
                        dto.codigo(),
                        dto.nombre(),
                        dto.categoria(),
                        "$" + dto.precioVenta(),
                        dto.stockActual(),
                        dto.alertaMinima(),
                        dto.estado()
                });
            }
        } catch (ExcepcionAccesoDatos excepcion) {
            mostrarErrorBaseDatos(excepcion);
        }
        tablaProducto.clearSelection();
        ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
    }

    private void mostrarErrorBaseDatos(ExcepcionAccesoDatos excepcion) {
        JOptionPane.showMessageDialog(
                this,
                UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion),
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void cargarFilaSeleccionada(int fila) {
        String codigo = String.valueOf(modeloTabla.getValueAt(fila, 0));
        Producto producto = manejadorEvento.buscarProducto(codigo);
        if (producto != null) {
            cargarProductoEnFormulario(producto);
        }
    }

    private void cargarProductoEnFormulario(Producto producto) {
        txtCodigo.setText(producto.getCodigoInterno());
        txtNombre.setText(producto.getNombreProducto());
        cbCategoria.setSelectedItem(producto.getCategoria());
        txtPrecioCompra.setText(String.valueOf(producto.getPrecioCompra()));
        txtPrecioVenta.setText(String.valueOf(producto.getPrecioVenta()));
        txtStockActual.setText(String.valueOf(producto.getStockActual()));
        txtStockMinimo.setText(String.valueOf(producto.getStockMinimo()));
        txtStockMaximo.setText(String.valueOf(producto.getStockMaximo()));
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtStockActual.setText("");
        txtStockMinimo.setText("");
        txtStockMaximo.setText("");
        cbCategoria.setSelectedIndex(0);
        tablaProducto.clearSelection();
        ConstructorComponentes.configurarBotonEstadoNeutral(botonCambiarEstado);
    }
}
