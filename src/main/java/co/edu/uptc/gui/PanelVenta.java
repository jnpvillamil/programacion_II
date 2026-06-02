package co.edu.uptc.gui;

import co.edu.uptc.dto.CarritoVentaDTO;
import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.interfaces.ManejadorEventoComercial;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.ConstructorComponentes;
import co.edu.uptc.utilidades.FormateadorMoneda;
import co.edu.uptc.utilidades.ManejadorFechas;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelVenta extends JPanel {

    private final ManejadorEventoComercial manejadorEventoComercial;

    private final List<CarritoVentaDTO> carrito = new ArrayList<>();
    private DefaultTableModel modeloTablaCarrito;
    private DefaultTableModel modeloTablaHistorial;

    private JTextField txtCedula;
    private JTextField txtProducto;
    private JTextField txtCantidad;
    private JTextField txtNumeroFactura;
    private JComboBox<FormaPago> cbFormaPago;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    public PanelVenta(ManejadorEventoComercial manejadorEventoComercial) {
        this.manejadorEventoComercial = manejadorEventoComercial;

        setLayout(new BorderLayout(20, 20));
        ConstructorComponentes.aplicarFondoPanel(this);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(ConstructorComponentes.crearTituloModulo("Módulo de Ventas"), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirPanelInferior(), BorderLayout.SOUTH);

        inicializarPanel();
    }

    public void inicializarPanel() {
        actualizarTablaHistorial();
    }

    private JPanel construirPanelCentral() {
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        ConstructorComponentes.aplicarFondoPanel(panelCentro);

        panelCentro.add(construirPanelFormulario(), BorderLayout.NORTH);

        JSplitPane divisor = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                construirTablaCarrito(),
                construirTablaHistorial());
        divisor.setResizeWeight(0.45);
        divisor.setDividerLocation(220);
        panelCentro.add(divisor, BorderLayout.CENTER);
        return panelCentro;
    }

    private JPanel construirPanelFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        ConstructorComponentes.aplicarFondoPanel(panelFormulario);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.weightx = 0.5;

        txtCedula = ConstructorComponentes.crearCampoTexto();
        cbFormaPago = new JComboBox<>(FormaPago.values());
        txtProducto = ConstructorComponentes.crearCampoTexto();
        txtCantidad = ConstructorComponentes.crearCampoTexto();
        txtCantidad.setText("1");
        txtNumeroFactura = ConstructorComponentes.crearCampoTexto();

        gbc.gridy = 0;
        gbc.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Cliente (Cédula):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCedula, gbc);
        gbc.gridx = 2;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Forma de pago:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(cbFormaPago, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Producto (Código):"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtProducto, gbc);
        gbc.gridx = 2;
        panelFormulario.add(ConstructorComponentes.crearEtiquetaNegrita("Cantidad:"), gbc);
        gbc.gridx = 3;
        panelFormulario.add(txtCantidad, gbc);

        gbc.gridy = 1;
        gbc.gridx = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAgregar = ConstructorComponentes.crearBotonGuardar("Añadir al Carrito");
        btnAgregar.addActionListener(evento -> agregarAlCarrito());
        panelFormulario.add(btnAgregar, gbc);

        JPanel panelAnulacion = new JPanel(new GridBagLayout());
        ConstructorComponentes.aplicarFondoPanel(panelAnulacion);
        panelAnulacion.setBorder(BorderFactory.createTitledBorder("Anulaciones"));

        GridBagConstraints gbcAnulacion = new GridBagConstraints();
        gbcAnulacion.fill = GridBagConstraints.HORIZONTAL;
        gbcAnulacion.insets = new Insets(8, 10, 8, 10);
        gbcAnulacion.weightx = 0.5;

        gbcAnulacion.gridy = 0;
        gbcAnulacion.gridx = 0;
        panelAnulacion.add(ConstructorComponentes.crearEtiquetaNegrita("Factura a anular:"), gbcAnulacion);
        gbcAnulacion.gridx = 1;
        panelAnulacion.add(txtNumeroFactura, gbcAnulacion);

        gbcAnulacion.gridy = 1;
        gbcAnulacion.gridx = 0;
        gbcAnulacion.gridwidth = 2;
        gbcAnulacion.fill = GridBagConstraints.NONE;
        gbcAnulacion.anchor = GridBagConstraints.CENTER;
        JButton btnAnular = ConstructorComponentes.crearBotonPeligro("Anular Factura");
        btnAnular.addActionListener(evento -> anularFactura());
        panelAnulacion.add(btnAnular, gbcAnulacion);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(panelAnulacion, gbc);

        return panelFormulario;
    }

    private JScrollPane construirTablaCarrito() {
        String[] columnas = {"Cant.", "Código", "Descripción", "Precio Unit.", "Subtotal"};
        modeloTablaCarrito = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modeloTablaCarrito);
        ConstructorComponentes.darEstiloTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Carrito de la venta actual"));
        return scroll;
    }

    private JScrollPane construirTablaHistorial() {
        String[] columnas = {"Factura", "Fecha", "Cliente", "Subtotal", "IVA", "Total", "Pago", "Estado"};
        modeloTablaHistorial = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modeloTablaHistorial);
        ConstructorComponentes.darEstiloTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de ventas registradas"));
        return scroll;
    }

    private JPanel construirPanelInferior() {
        JPanel panelSur = new JPanel(new BorderLayout());
        ConstructorComponentes.aplicarFondoPanel(panelSur);

        JPanel panelTotales = new JPanel(new GridLayout(1, 3, 20, 5));
        ConstructorComponentes.aplicarFondoPanel(panelTotales);
        lblSubtotal = ConstructorComponentes.crearEtiquetaNegrita("Subtotal: $ 0.00");
        lblIva = ConstructorComponentes.crearEtiquetaNegrita("IVA (19%): $ 0.00");
        lblTotal = ConstructorComponentes.crearEtiquetaNegrita("Total: $ 0.00");
        panelTotales.add(lblSubtotal);
        panelTotales.add(lblIva);
        panelTotales.add(lblTotal);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        ConstructorComponentes.aplicarFondoPanel(panelAcciones);
        JButton btnFinalizar = ConstructorComponentes.crearBotonGuardar("Finalizar Venta");
        btnFinalizar.addActionListener(evento -> finalizarVenta());
        panelAcciones.add(btnFinalizar);

        panelSur.add(panelTotales, BorderLayout.CENTER);
        panelSur.add(panelAcciones, BorderLayout.SOUTH);
        return panelSur;
    }

    private void agregarAlCarrito() {
        String codigo = txtProducto.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del producto.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException excepcion) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero mayor a cero.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Producto producto = manejadorEventoComercial.buscarProducto(codigo);
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado con código: " + codigo,
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!producto.isActivo()) {
            JOptionPane.showMessageDialog(this, "El producto está inactivo.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (producto.getStockActual() < cantidad) {
            JOptionPane.showMessageDialog(this,
                    "Stock insuficiente. Disponible: " + producto.getStockActual(),
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CarritoVentaDTO item = new CarritoVentaDTO(producto, cantidad, producto.getPrecioVenta());
        carrito.add(item);
        refrescarTablaCarritoYTotales();
        txtProducto.setText("");
        txtCantidad.setText("1");
    }

    private void finalizarVenta() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un producto al carrito.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtCedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la cédula del cliente.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = manejadorEventoComercial.buscarCliente(txtCedula.getText().trim());
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "El cliente no está registrado.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Venta venta = new Venta(cliente, (FormaPago) cbFormaPago.getSelectedItem());
        for (CarritoVentaDTO item : carrito) {
            venta.getListaDetalles().add(new DetalleVenta(item.producto(), item.cantidad(), item.precioUnitario()));
        }
        manejadorEventoComercial.calcularTotales(venta);

        String mensaje = manejadorEventoComercial.realizarVenta(venta);
        boolean exito = !mensaje.startsWith("Error:");
        JOptionPane.showMessageDialog(this, mensaje, exito ? "Venta exitosa" : "Error en venta",
                exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

        if (exito) {
            limpiarPantalla();
            actualizarTablaHistorial();
        }
    }

    private void refrescarTablaCarritoYTotales() {
        modeloTablaCarrito.setRowCount(0);
        for (CarritoVentaDTO item : carrito) {
            modeloTablaCarrito.addRow(new Object[]{
                    item.cantidad(),
                    item.producto().getCodigoInterno(),
                    item.producto().getNombreProducto(),
                    item.precioUnitario(),
                    item.subtotal()
            });
        }

        Venta ventaPreview = new Venta(
                new Cliente("", "", "0", "", "", "0", TipoIdentificacion.CC, TipoCliente.MINORISTA),
                FormaPago.EFECTIVO);
        for (CarritoVentaDTO item : carrito) {
            ventaPreview.getListaDetalles().add(new DetalleVenta(item.producto(), item.cantidad(), item.precioUnitario()));
        }
        manejadorEventoComercial.calcularTotales(ventaPreview);

        lblSubtotal.setText("Subtotal: " + FormateadorMoneda.formatear(ventaPreview.getSubtotal()));
        lblIva.setText("IVA (19%): " + FormateadorMoneda.formatear(ventaPreview.getIva()));
        lblTotal.setText("Total: " + FormateadorMoneda.formatear(ventaPreview.getTotal()));
    }

    private void actualizarTablaHistorial() {
        modeloTablaHistorial.setRowCount(0);
        try {
            List<VentaDTO> lista = manejadorEventoComercial.obtenerListadoVenta();
            for (VentaDTO venta : lista) {
                modeloTablaHistorial.addRow(new Object[]{
                        venta.getNumeroFactura(),
                        ManejadorFechas.formatearFecha(venta.getFecha()),
                        venta.getNombreCliente(),
                        FormateadorMoneda.formatear(venta.getSubtotal()),
                        FormateadorMoneda.formatear(venta.getIva()),
                        FormateadorMoneda.formatear(venta.getTotal()),
                        venta.getFormaPago().name(),
                        venta.getEstado().name()
                });
            }
        } catch (ExcepcionAccesoDatos excepcion) {
            JOptionPane.showMessageDialog(
                    this,
                    UtilidadMensajeAccesoDatos.mensajeCliente(excepcion),
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void anularFactura() {
        String numeroFactura = txtNumeroFactura.getText().trim();
        if (numeroFactura.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el número de factura a anular.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea anular la factura N° " + numeroFactura + "?",
                "Confirmar anulación",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        String mensaje = manejadorEventoComercial.anularVenta(numeroFactura);
        boolean exito = !mensaje.startsWith("Error:");
        JOptionPane.showMessageDialog(this, mensaje, exito ? "Anulación exitosa" : "Error en anulación",
                exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        if (exito) {
            txtNumeroFactura.setText("");
            actualizarTablaHistorial();
        }
    }

    private void limpiarPantalla() {
        carrito.clear();
        modeloTablaCarrito.setRowCount(0);
        txtCedula.setText("");
        txtProducto.setText("");
        txtCantidad.setText("1");
        cbFormaPago.setSelectedIndex(0);
        lblSubtotal.setText("Subtotal: $ 0.00");
        lblIva.setText("IVA (19%): $ 0.00");
        lblTotal.setText("Total: $ 0.00");
    }
}
