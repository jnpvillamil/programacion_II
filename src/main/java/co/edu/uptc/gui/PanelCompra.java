package co.edu.uptc.gui;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.interfaces.ManejadorEventoComercial;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleCompra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.ConstructorComponentes;
import co.edu.uptc.utilidades.FormateadorMoneda;
import co.edu.uptc.utilidades.ManejadorFechas;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PanelCompra extends JPanel {

    private final ManejadorEventoComercial manejadorEventoComercial;

    private final List<DetalleCompra> detalleCompra = new ArrayList<>();
    private DefaultTableModel modeloTablaDetalle;
    private DefaultTableModel modeloTablaHistorial;

    private JTextField txtFactura;
    private JTextField txtNitProveedor;
    private JTextField txtCodigoProducto;
    private JTextField txtCantidad;
    private JTextField txtCostoUnitario;
    private JLabel lblTotal;

    public PanelCompra(ManejadorEventoComercial manejadorEventoComercial) {
        this.manejadorEventoComercial = manejadorEventoComercial;

        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(ConstructorComponentes.crearEtiquetaNegrita("MÓDULO DE COMPRAS"), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirPanelInferior(), BorderLayout.SOUTH);

        inicializarPanel();
    }

    public void inicializarPanel() {
        actualizarTablaHistorial();
    }

    private JPanel construirPanelCentral() {
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        panelCentro.add(construirPanelCabecera(), BorderLayout.NORTH);

        JSplitPane divisor = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                construirTablaDetalle(),
                construirTablaHistorial());
        divisor.setResizeWeight(0.45);
        divisor.setDividerLocation(220);
        panelCentro.add(divisor, BorderLayout.CENTER);
        return panelCentro;
    }

    private JPanel construirPanelCabecera() {
        JPanel panelCabecera = new JPanel(new GridLayout(3, 4, 10, 10));
        panelCabecera.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        txtFactura = ConstructorComponentes.crearCampoTexto();
        txtNitProveedor = ConstructorComponentes.crearCampoTexto();
        txtCodigoProducto = ConstructorComponentes.crearCampoTexto();
        txtCantidad = ConstructorComponentes.crearCampoTexto();
        txtCantidad.setText("1");
        txtCostoUnitario = ConstructorComponentes.crearCampoTexto();

        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Factura Proveedor:"));
        panelCabecera.add(txtFactura);
        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("NIT Proveedor:"));
        panelCabecera.add(txtNitProveedor);

        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Producto (Código):"));
        panelCabecera.add(txtCodigoProducto);
        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Cantidad:"));
        panelCabecera.add(txtCantidad);

        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Costo Unitario:"));
        panelCabecera.add(txtCostoUnitario);
        panelCabecera.add(new JLabel(""));
        JButton btnAgregar = ConstructorComponentes.crearBotonGuardar("Añadir Ingreso");
        btnAgregar.addActionListener(evento -> agregarDetalle());
        panelCabecera.add(btnAgregar);

        return panelCabecera;
    }

    private JScrollPane construirTablaDetalle() {
        String[] columna = {"Cant.", "Código", "Descripción", "Costo Unit.", "Subtotal"};
        modeloTablaDetalle = new DefaultTableModel(columna, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modeloTablaDetalle);
        ConstructorComponentes.darEstiloTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Detalle de la compra actual"));
        return scroll;
    }

    private JScrollPane construirTablaHistorial() {
        String[] columna = {"Factura", "NIT Proveedor", "Fecha", "Total"};
        modeloTablaHistorial = new DefaultTableModel(columna, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modeloTablaHistorial);
        ConstructorComponentes.darEstiloTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de compras registradas"));
        return scroll;
    }

    private JPanel construirPanelInferior() {
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        lblTotal = ConstructorComponentes.crearEtiquetaNegrita("Costo Total: $ 0.00");
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelTotal.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        panelTotal.add(lblTotal);

        JPanel panelAccion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelAccion.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        JButton btnRegistrar = ConstructorComponentes.crearBotonGuardar("Registrar Compra");
        btnRegistrar.addActionListener(evento -> registrarCompra());
        panelAccion.add(btnRegistrar);

        panelSur.add(panelTotal, BorderLayout.CENTER);
        panelSur.add(panelAccion, BorderLayout.SOUTH);
        return panelSur;
    }

    private void agregarDetalle() {
        String codigo = txtCodigoProducto.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del producto.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad;
        double costoUnitario;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            costoUnitario = Double.parseDouble(txtCostoUnitario.getText().trim());
            if (cantidad <= 0 || costoUnitario <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException excepcion) {
            JOptionPane.showMessageDialog(this,
                    "Cantidad y costo unitario deben ser números válidos mayores a cero.",
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
        if (producto.getStockActual() + cantidad > producto.getStockMaximo()) {
            JOptionPane.showMessageDialog(this,
                    "El stock final superaría el máximo permitido para: " + producto.getNombreProducto(),
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DetalleCompra detalle = new DetalleCompra(producto, cantidad, costoUnitario);
        detalleCompra.add(detalle);
        refrescarTablaDetalleYTotal();
        txtCodigoProducto.setText("");
        txtCantidad.setText("1");
        txtCostoUnitario.setText("");
    }

    private void registrarCompra() {
        if (detalleCompra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un producto a la compra.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtFactura.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el número de factura del proveedor.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtNitProveedor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el NIT del proveedor.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Proveedor proveedor = new Proveedor("", "", txtNitProveedor.getText().trim(),
                "", "", "", "", txtNitProveedor.getText().trim(), "");
        Compra compra = new Compra(txtFactura.getText().trim(), LocalDateTime.now(), proveedor);
        compra.getListaDetalles().addAll(detalleCompra);

        String mensaje = manejadorEventoComercial.registrarCompra(compra);
        boolean exito = !mensaje.startsWith("Error:");
        JOptionPane.showMessageDialog(this, mensaje, exito ? "Compra exitosa" : "Error en compra",
                exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

        if (exito) {
            limpiarPantalla();
            actualizarTablaHistorial();
        }
    }

    private void refrescarTablaDetalleYTotal() {
        modeloTablaDetalle.setRowCount(0);
        double subtotal = 0;
        for (DetalleCompra detalle : detalleCompra) {
            modeloTablaDetalle.addRow(new Object[]{
                    detalle.getCantidad(),
                    detalle.getProducto().getCodigoInterno(),
                    detalle.getProducto().getNombreProducto(),
                    detalle.getCostoUnitario(),
                    detalle.getSubtotal()
            });
            subtotal += detalle.getSubtotal();
        }
        double iva = Math.round(subtotal * 0.19 * 100.0) / 100.0;
        lblTotal.setText("Costo Total: " + FormateadorMoneda.formatear(subtotal + iva));
    }

    private void actualizarTablaHistorial() {
        modeloTablaHistorial.setRowCount(0);
        try {
            List<CompraDTO> lista = manejadorEventoComercial.obtenerListadoCompra();
            for (CompraDTO compra : lista) {
                modeloTablaHistorial.addRow(new Object[]{
                        compra.getNumeroFacturaProveedor(),
                        compra.getNitProveedor(),
                        ManejadorFechas.formatearFecha(compra.getFecha()),
                        FormateadorMoneda.formatear(compra.getTotal())
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

    private void limpiarPantalla() {
        detalleCompra.clear();
        modeloTablaDetalle.setRowCount(0);
        txtFactura.setText("");
        txtNitProveedor.setText("");
        txtCodigoProducto.setText("");
        txtCantidad.setText("1");
        txtCostoUnitario.setText("");
        lblTotal.setText("Costo Total: $ 0.00");
    }
}
