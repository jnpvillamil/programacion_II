package co.edu.uptc.gui;

import co.edu.uptc.controlador.ControladorCliente;
import co.edu.uptc.controlador.ControladorProducto;
import co.edu.uptc.controlador.ControladorVenta;
import co.edu.uptc.dto.CarritoItemDTO;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.utilidades.ConstructorComponentes;
import co.edu.uptc.utilidades.FormateadorMoneda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelVenta extends JPanel {

    private final ControladorVenta controladorVenta;
    private final ControladorProducto controladorProducto;
    private final ControladorCliente controladorCliente;

    private final List<CarritoItemDTO> carrito = new ArrayList<>();
    private DefaultTableModel modeloTabla;

    private JTextField txtCedula;
    private JTextField txtProducto;
    private JTextField txtCantidad;
    private JComboBox<FormaPago> cbFormaPago;
    private JLabel lblSubtotal;
    private JLabel lblIva;
    private JLabel lblTotal;

    public PanelVenta(ControladorVenta controladorVenta, ControladorProducto controladorProducto,
                      ControladorCliente controladorCliente) {
        this.controladorVenta = controladorVenta;
        this.controladorProducto = controladorProducto;
        this.controladorCliente = controladorCliente;

        setLayout(new BorderLayout(20, 20));
        setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(ConstructorComponentes.crearEtiquetaNegrita("MÓDULO DE VENTAS"), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel construirPanelCentral() {
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        JPanel panelCabecera = new JPanel(new GridLayout(3, 4, 10, 10));
        panelCabecera.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        txtCedula = ConstructorComponentes.crearCampoTexto();
        txtProducto = ConstructorComponentes.crearCampoTexto();
        txtCantidad = ConstructorComponentes.crearCampoTexto();
        txtCantidad.setText("1");
        cbFormaPago = new JComboBox<>(FormaPago.values());

        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Cliente (Cédula):"));
        panelCabecera.add(txtCedula);
        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Forma de pago:"));
        panelCabecera.add(cbFormaPago);

        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Producto (Código):"));
        panelCabecera.add(txtProducto);
        panelCabecera.add(ConstructorComponentes.crearEtiquetaNegrita("Cantidad:"));
        panelCabecera.add(txtCantidad);

        JButton btnAgregar = ConstructorComponentes.crearBotonAccion("Añadir al Carrito", ConstructorComponentes.COLOR_AZUL_ACCION);
        panelCabecera.add(new JLabel(""));
        panelCabecera.add(new JLabel(""));
        panelCabecera.add(new JLabel(""));
        panelCabecera.add(btnAgregar);
        btnAgregar.addActionListener(e -> agregarAlCarrito());

        String[] columnas = {"Cant.", "Código", "Descripción", "Precio Unit.", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabla = new JTable(modeloTabla);
        ConstructorComponentes.darEstiloTabla(tabla);

        panelCentro.add(panelCabecera, BorderLayout.NORTH);
        panelCentro.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panelCentro;
    }

    private JPanel construirPanelInferior() {
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);

        JPanel panelTotales = new JPanel(new GridLayout(1, 3, 20, 5));
        panelTotales.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        lblSubtotal = ConstructorComponentes.crearEtiquetaNegrita("Subtotal: $ 0.00");
        lblIva = ConstructorComponentes.crearEtiquetaNegrita("IVA (19%): $ 0.00");
        lblTotal = ConstructorComponentes.crearEtiquetaNegrita("Total: $ 0.00");
        panelTotales.add(lblSubtotal);
        panelTotales.add(lblIva);
        panelTotales.add(lblTotal);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelAcciones.setBackground(ConstructorComponentes.COLOR_FONDO_GRIS);
        JButton btnFinalizar = ConstructorComponentes.crearBotonAccion("Finalizar Venta", ConstructorComponentes.COLOR_VERDE_GUARDAR);
        btnFinalizar.addActionListener(e -> finalizarVenta());
        panelAcciones.add(btnFinalizar);

        panelSur.add(panelTotales, BorderLayout.CENTER);
        panelSur.add(panelAcciones, BorderLayout.SOUTH);
        return panelSur;
    }

    private void agregarAlCarrito() {
        String codigo = txtProducto.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del producto.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero mayor a cero.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Producto producto = controladorProducto.buscarProducto(codigo);
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado con código: " + codigo, "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!producto.isActivo()) {
            JOptionPane.showMessageDialog(this, "El producto está inactivo.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (producto.getStockActual() < cantidad) {
            JOptionPane.showMessageDialog(this,
                    "Stock insuficiente. Disponible: " + producto.getStockActual(),
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CarritoItemDTO item = new CarritoItemDTO(producto, cantidad, producto.getPrecioVenta());
        carrito.add(item);
        refrescarTablaYTotales();
        txtProducto.setText("");
        txtCantidad.setText("1");
    }

    private void finalizarVenta() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe agregar al menos un producto al carrito.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtCedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la cédula del cliente.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = controladorCliente.buscarCliente(txtCedula.getText().trim());
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "El cliente no está registrado.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Venta venta = new Venta(cliente, (FormaPago) cbFormaPago.getSelectedItem());
        for (CarritoItemDTO item : carrito) {
            venta.getListaDetalles().add(new DetalleVenta(item.getProducto(), item.getCantidad(), item.getPrecioUnitario()));
        }
        controladorVenta.calcularTotales(venta);

        String mensaje = controladorVenta.realizarVenta(venta);
        boolean exito = !mensaje.startsWith("Error:");
        JOptionPane.showMessageDialog(this, mensaje, exito ? "Venta exitosa" : "Error en venta",
                exito ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

        if (exito) {
            limpiarPantalla();
        }
    }

    private void refrescarTablaYTotales() {
        modeloTabla.setRowCount(0);
        double subtotal = 0;
        for (CarritoItemDTO item : carrito) {
            modeloTabla.addRow(new Object[]{
                    item.getCantidad(),
                    item.getProducto().getCodigoInterno(),
                    item.getProducto().getNombreProducto(),
                    item.getPrecioUnitario(),
                    item.getSubtotal()
            });
            subtotal += item.getSubtotal();
        }

        Venta ventaPreview = new Venta(
                new Cliente("", "", "0", "", "", "0", TipoIdentificacion.CC, TipoCliente.MINORISTA),
                FormaPago.EFECTIVO);
        for (CarritoItemDTO item : carrito) {
            ventaPreview.getListaDetalles().add(new DetalleVenta(item.getProducto(), item.getCantidad(), item.getPrecioUnitario()));
        }
        controladorVenta.calcularTotales(ventaPreview);

        lblSubtotal.setText("Subtotal: " + FormateadorMoneda.formatear(ventaPreview.getSubtotal()));
        lblIva.setText("IVA (19%): " + FormateadorMoneda.formatear(ventaPreview.getIva()));
        lblTotal.setText("Total: " + FormateadorMoneda.formatear(ventaPreview.getTotal()));
    }

    private void limpiarPantalla() {
        carrito.clear();
        modeloTabla.setRowCount(0);
        txtCedula.setText("");
        txtProducto.setText("");
        txtCantidad.setText("1");
        cbFormaPago.setSelectedIndex(0);
        lblSubtotal.setText("Subtotal: $ 0.00");
        lblIva.setText("IVA (19%): $ 0.00");
        lblTotal.setText("Total: $ 0.00");
    }
}
