package co.edu.uptc.gui;

import co.edu.uptc.dto.CarritoItemDTO;
import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.negocio.GestionCompras;
import co.edu.uptc.negocio.GestionProveedor;
import co.edu.uptc.utilidades.FormateadorMoneda;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EventoCompra implements ActionListener {

    public static final String CMD_BUSCAR_PROVEEDOR = "CMD_BUSCAR_PROVEEDOR_COMPRA";
    public static final String CMD_AGREGAR_PRODUCTO = "CMD_AGREGAR_PRODUCTO_COMPRA";
    public static final String CMD_FINALIZAR_COMPRA = "CMD_FINALIZAR_COMPRA";

    private final VentanaPrincipal ventanaPrincipal;
    private final PanelCompra panel;
    private final GestionCompras gestionCompras;
    private final GestionProveedor gestionProveedor;

    private Proveedor proveedorActual;
    private final List<CarritoItemDTO> carrito = new ArrayList<>();

    public EventoCompra(VentanaPrincipal ventanaPrincipal,
                        PanelCompra panel,
                        GestionCompras gestionCompras,
                        GestionProveedor gestionProveedor) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panel = panel;
        this.gestionCompras = gestionCompras;
        this.gestionProveedor = gestionProveedor;

        suscribir(panel.getBtnBuscarProveedor(), CMD_BUSCAR_PROVEEDOR);
        suscribir(panel.getBtnAgregarProducto(), CMD_AGREGAR_PRODUCTO);
        suscribir(panel.getBtnFinalizarCompra(), CMD_FINALIZAR_COMPRA);
    }

    private void suscribir(javax.swing.JButton boton, String comando) {
        boton.setActionCommand(comando);
        boton.addActionListener(this);
    }

    private void buscarProveedor() {
        ResultadoOperacion resultado = gestionProveedor.buscarProveedorActivo(
                panel.getTxtIdentificacionProveedor().getText());
        if (resultado.isExito()) {
            proveedorActual = resultado.getDato();
            panel.getLblNombreProveedor().setText("Proveedor: " + proveedorActual.getNombre());
        } else {
            proveedorActual = null;
            panel.getLblNombreProveedor().setText("Proveedor: NO ENCONTRADO O INACTIVO");
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProducto() {
        ResultadoOperacion resultado = gestionCompras.validarAgregarLineaCompra(
                panel.getTxtCodigoProducto().getText(),
                panel.getTxtCantidad().getText(),
                panel.getTxtCostoUnitario().getText());
        if (resultado.isExito()) {
            carrito.add(resultado.getDato());
            actualizarTablaCarrito();
            panel.getTxtCodigoProducto().setText("");
            panel.getTxtCantidad().setText("");
            panel.getTxtCostoUnitario().setText("");
        } else {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void finalizarCompra() {
        Compra compra = new Compra();
        compra.setFacturaProveedor(panel.getTxtFacturaProveedor().getText().trim());
        compra.setProveedor(proveedorActual);
        compra.setProductosComprados(convertirDetalles(carrito));

        ResultadoOperacion resultado = gestionCompras.procesarCompraConResultado(compra);
        if (resultado.isExito()) {
            Compra compraRegistrada = resultado.getDato();
            JOptionPane.showMessageDialog(panel,
                    resultado.getMensaje() + "\nFactura: " + compraRegistrada.getFacturaProveedor()
                            + "\nTotal: " + FormateadorMoneda.formatearPeso(compraRegistrada.getTotalCompra()),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<DetalleVenta> convertirDetalles(List<CarritoItemDTO> items) {
        List<DetalleVenta> detalles = new ArrayList<>();
        for (CarritoItemDTO item : items) {
            detalles.add(new DetalleVenta(
                    item.getProducto(),
                    item.getCantidad(),
                    item.getPrecioUnitario(),
                    item.getSubtotal()));
        }
        return detalles;
    }

    private void actualizarTablaCarrito() {
        DefaultTableModel modelo = panel.getModeloTabla();
        modelo.setRowCount(0);
        double total = 0.0;
        for (CarritoItemDTO item : carrito) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigoProducto(),
                    item.getProducto().getNombreProducto(),
                    item.getCantidad(),
                    item.getPrecioUnitario(),
                    item.getSubtotal()
            });
            total += item.getSubtotal();
        }
        panel.getLblTotalCompra().setText("TOTAL COMPRA: " + FormateadorMoneda.formatearPeso(total));
    }

    private void limpiarFormulario() {
        proveedorActual = null;
        carrito.clear();
        panel.getTxtFacturaProveedor().setText("");
        panel.getTxtIdentificacionProveedor().setText("");
        panel.getTxtCodigoProducto().setText("");
        panel.getTxtCantidad().setText("");
        panel.getTxtCostoUnitario().setText("");
        panel.getLblNombreProveedor().setText("Proveedor: NO SELECCIONADO");
        panel.getLblTotalCompra().setText("TOTAL COMPRA: $ 0.00");
        panel.getModeloTabla().setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (CMD_BUSCAR_PROVEEDOR.equals(comando)) {
            buscarProveedor();
        } else if (CMD_AGREGAR_PRODUCTO.equals(comando)) {
            agregarProducto();
        } else if (CMD_FINALIZAR_COMPRA.equals(comando)) {
            finalizarCompra();
        }
    }
}
