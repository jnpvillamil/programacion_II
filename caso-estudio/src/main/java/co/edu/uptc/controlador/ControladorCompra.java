package co.edu.uptc.controlador;

import co.edu.uptc.dto.CarritoItemDTO;
import co.edu.uptc.gui.PanelCompra;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.negocio.GestionCompras;
import co.edu.uptc.negocio.GestionInventario;
import co.edu.uptc.negocio.GestionProveedor;
import co.edu.uptc.utilidades.FormateadorMoneda;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ControladorCompra {

    private final PanelCompra vista;
    private final GestionCompras negocio;
    private final GestionProveedor gestionProveedor;
    private final GestionInventario gestionInventario;

    private Proveedor proveedorActual;
    private final List<CarritoItemDTO> carritoTemporal;

    public ControladorCompra(PanelCompra vista, GestionCompras negocio, GestionProveedor gestionProveedor,
                             GestionInventario gestionInventario) {
        this.vista = vista;
        this.negocio = negocio;
        this.gestionProveedor = gestionProveedor;
        this.gestionInventario = gestionInventario;
        this.carritoTemporal = new ArrayList<>();

        vista.getBtnBuscarProveedor().addActionListener(e -> buscarProveedor());
        vista.getBtnAgregarProducto().addActionListener(e -> agregarProductoCarrito());
        vista.getBtnFinalizarCompra().addActionListener(e -> finalizarCompra());
    }

    private void buscarProveedor() {
        String identificacion = vista.getTxtIdentificacionProveedor().getText();
        if (ValidadorEntradas.esVacio(identificacion)) {
            JOptionPane.showMessageDialog(vista, "Ingrese el NIT o código del proveedor.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Proveedor proveedor = gestionProveedor.buscarPorIdentificacion(identificacion.trim());
        if (proveedor != null && proveedor.isActivo()) {
            proveedorActual = proveedor;
            vista.getLblNombreProveedor().setText("Proveedor: " + proveedor.getNombre());
        } else {
            proveedorActual = null;
            vista.getLblNombreProveedor().setText("Proveedor: NO ENCONTRADO O INACTIVO");
            JOptionPane.showMessageDialog(vista, "Proveedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProductoCarrito() {
        String codigo = vista.getTxtCodigoProducto().getText();
        String cantidadStr = vista.getTxtCantidad().getText();
        String costoStr = vista.getTxtCostoUnitario().getText();

        if (ValidadorEntradas.esVacio(codigo) || !ValidadorEntradas.esNumero(cantidadStr)
                || !ValidadorEntradas.esNumero(costoStr)) {
            JOptionPane.showMessageDialog(vista, "Complete código, cantidad y costo unitario con valores válidos.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            double costoUnitario = Double.parseDouble(costoStr);

            if (cantidad <= 0 || costoUnitario <= 0) {
                JOptionPane.showMessageDialog(vista, "Cantidad y costo deben ser mayores a cero.", "Validación",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Producto producto = gestionInventario.buscarProducto(codigo.trim());
            if (producto == null || !producto.isActivo()) {
                JOptionPane.showMessageDialog(vista, "Producto no encontrado o inactivo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            double subtotal = costoUnitario * cantidad;
            carritoTemporal.add(new CarritoItemDTO(producto, cantidad, costoUnitario, subtotal));

            vista.getTxtCodigoProducto().setText("");
            vista.getTxtCantidad().setText("");
            vista.getTxtCostoUnitario().setText("");
            actualizarTablaCarrito();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Cantidad o costo con formato inválido.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualizarTablaCarrito() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        double subtotalGlobal = 0;
        for (CarritoItemDTO item : carritoTemporal) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigoProducto(),
                    item.getProducto().getNombreProducto(),
                    item.getCantidad(),
                    FormateadorMoneda.formatearPeso(item.getPrecioUnitario()),
                    FormateadorMoneda.formatearPeso(item.getSubtotal())
            });
            subtotalGlobal += item.getSubtotal();
        }

        double iva = subtotalGlobal * 0.19;
        double total = subtotalGlobal + iva;
        vista.getLblTotalCompra().setText("TOTAL COMPRA: " + FormateadorMoneda.formatearPeso(total));
    }

    private void finalizarCompra() {
        if (proveedorActual == null) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un proveedor antes de registrar la compra.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (ValidadorEntradas.esVacio(vista.getTxtFacturaProveedor().getText())) {
            JOptionPane.showMessageDialog(vista, "Ingrese el número de factura del proveedor.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carritoTemporal.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Agregue al menos un producto a la compra.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<DetalleVenta> detalles = new ArrayList<>();
            for (CarritoItemDTO item : carritoTemporal) {
                detalles.add(new DetalleVenta(
                        item.getProducto(),
                        item.getCantidad(),
                        item.getPrecioUnitario(),
                        item.getSubtotal()));
            }

            Compra compra = new Compra();
            compra.setFacturaProveedor(vista.getTxtFacturaProveedor().getText().trim());
            compra.setProveedor(proveedorActual);
            compra.setProductosComprados(detalles);

            boolean exito = negocio.procesarCompra(compra);
            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Compra registrada exitosamente.\nFactura: " + compra.getFacturaProveedor()
                                + "\nTotal: " + FormateadorMoneda.formatearPeso(compra.getTotalCompra()),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "No se pudo registrar la compra. Revise conexion a BD, proveedor y tablas compras/detalles_compras.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al registrar la compra: " + ex.getMessage(),
                    "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        proveedorActual = null;
        carritoTemporal.clear();

        vista.getTxtFacturaProveedor().setText("");
        vista.getTxtIdentificacionProveedor().setText("");
        vista.getTxtCodigoProducto().setText("");
        vista.getTxtCantidad().setText("");
        vista.getTxtCostoUnitario().setText("");
        vista.getLblNombreProveedor().setText("Proveedor: NO SELECCIONADO");
        actualizarTablaCarrito();
    }
}
