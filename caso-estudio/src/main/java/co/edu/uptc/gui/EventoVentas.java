package co.edu.uptc.gui;

import co.edu.uptc.dto.CarritoItemDTO;
import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.negocio.GestionClientes;
import co.edu.uptc.negocio.GestionVentas;
import co.edu.uptc.utilidades.FormateadorMoneda;
import co.edu.uptc.utilidades.ManejadorFechas;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EventoVentas implements ActionListener {

    public static final String CMD_BUSCAR_CLIENTE = "CMD_BUSCAR_CLIENTE_VENTA";
    public static final String CMD_AGREGAR_PRODUCTO = "CMD_AGREGAR_PRODUCTO_VENTA";
    public static final String CMD_FINALIZAR_VENTA = "CMD_FINALIZAR_VENTA";
    public static final String CMD_HISTORIAL_CLIENTE = "CMD_HISTORIAL_CLIENTE";
    public static final String CMD_CONSULTAR_FECHA = "CMD_CONSULTAR_VENTAS_FECHA";
    public static final String CMD_REIMPRIMIR = "CMD_REIMPRIMIR_VENTA";
    public static final String CMD_ANULAR = "CMD_ANULAR_VENTA";

    private final VentanaPrincipal ventanaPrincipal;
    private final PanelVentas panel;
    private final GestionVentas gestionVentas;
    private final GestionClientes gestionClientes;

    private Cliente clienteActual;
    private final List<CarritoItemDTO> carrito = new ArrayList<>();

    public EventoVentas(VentanaPrincipal ventanaPrincipal,
                        PanelVentas panel,
                        GestionVentas gestionVentas,
                        GestionClientes gestionClientes) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panel = panel;
        this.gestionVentas = gestionVentas;
        this.gestionClientes = gestionClientes;

        suscribir(panel.getBtnBuscarCliente(), CMD_BUSCAR_CLIENTE);
        suscribir(panel.getBtnAgregarProducto(), CMD_AGREGAR_PRODUCTO);
        suscribir(panel.getBtnFinalizarVenta(), CMD_FINALIZAR_VENTA);
        suscribir(panel.getBtnHistorialCliente(), CMD_HISTORIAL_CLIENTE);
        suscribir(panel.getBtnConsultarPorFecha(), CMD_CONSULTAR_FECHA);
        suscribir(panel.getBtnReimprimir(), CMD_REIMPRIMIR);
        suscribir(panel.getBtnAnularVenta(), CMD_ANULAR);
    }

    private void suscribir(javax.swing.JButton boton, String comando) {
        boton.setActionCommand(comando);
        boton.addActionListener(this);
    }

    private void buscarCliente() {
        ResultadoOperacion resultado = gestionClientes.buscarClienteParaVenta(
                panel.getTxtIdentificacionCliente().getText());
        if (resultado.isExito()) {
            clienteActual = resultado.getDato();
            panel.getLblNombreCliente().setText("Cliente: " + clienteActual.getNombre());
        } else {
            clienteActual = null;
            panel.getLblNombreCliente().setText("Cliente: NO ENCONTRADO O INACTIVO");
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProducto() {
        ResultadoOperacion resultado = gestionVentas.validarAgregarAlCarrito(
                panel.getTxtCodigoProducto().getText(),
                panel.getTxtCantidad().getText(),
                carrito);
        if (resultado.isExito()) {
            CarritoItemDTO item = resultado.getDato();
            carrito.add(item);
            actualizarTablaCarrito();
            panel.getTxtCodigoProducto().setText("");
            panel.getTxtCantidad().setText("");
        } else {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void finalizarVenta() {
        FormaPago formaPago = (FormaPago) panel.getCbFormaPago().getSelectedItem();
        VentaDTO dto = new VentaDTO(
                gestionVentas.generarNumeroFactura(),
                null,
                clienteActual != null ? clienteActual.getNombre() : "",
                new ArrayList<>(carrito),
                calcularTotalCarrito(),
                formaPago != null ? formaPago.name() : FormaPago.EFECTIVO.name());

        ResultadoOperacion resultado = gestionVentas.procesarVentaConResultado(dto, clienteActual);
        if (resultado.isExito()) {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarPos();
        } else {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarHistorialCliente() {
        ResultadoOperacion validacion = gestionVentas.validarIdentificacionConsulta(
                panel.getTxtIdClienteConsulta().getText());
        if (!validacion.isExito()) {
            JOptionPane.showMessageDialog(panel, validacion.getMensaje());
            return;
        }
        List<Venta> ventas = gestionVentas.consultarHistorialCliente(panel.getTxtIdClienteConsulta().getText().trim());
        cargarTablaVentas(ventas);
    }

    private void consultarPorFecha() {
        ResultadoOperacion validacion = gestionVentas.validarFechaConsulta(panel.getTxtFechaConsulta().getText());
        if (!validacion.isExito()) {
            JOptionPane.showMessageDialog(panel, validacion.getMensaje());
            return;
        }
        List<Venta> ventas = gestionVentas.consultarVentasPorFecha(panel.getTxtFechaConsulta().getText().trim());
        cargarTablaVentas(ventas);
    }

    private void reimprimir() {
        String numeroFactura = obtenerNumeroFactura();
        ResultadoOperacion resultado = gestionVentas.reimprimirComprobanteConValidacion(numeroFactura);
        if (resultado.isExito()) {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Comprobante", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void anular() {
        String numeroFactura = obtenerNumeroFactura();
        ResultadoOperacion resultado = gestionVentas.anularVentaConResultado(numeroFactura);
        if (resultado.isExito()) {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerNumeroFactura() {
        int fila = panel.getTablaVentas().getSelectedRow();
        if (fila != -1) {
            return panel.getModeloTablaVentas().getValueAt(fila, 0).toString();
        }
        return panel.getTxtNumeroFactura().getText().trim();
    }

    private void cargarTablaVentas(List<Venta> ventas) {
        DefaultTableModel modelo = panel.getModeloTablaVentas();
        modelo.setRowCount(0);
        for (Venta venta : ventas) {
            modelo.addRow(new Object[]{
                    venta.getNumeroFactura(),
                    ManejadorFechas.formatearFecha(venta.getFechaHora()),
                    venta.getCliente() != null ? venta.getCliente().getNombre() : "",
                    venta.getSubtotal(),
                    venta.getIvaAplicado(),
                    venta.getTotalVenta(),
                    venta.getFormaPago() != null ? venta.getFormaPago().name() : ""
            });
        }
    }

    private void actualizarTablaCarrito() {
        DefaultTableModel modelo = panel.getModeloTabla();
        modelo.setRowCount(0);
        for (CarritoItemDTO item : carrito) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigoProducto(),
                    item.getProducto().getNombreProducto(),
                    item.getCantidad(),
                    item.getPrecioUnitario(),
                    item.getSubtotal()
            });
        }
        panel.getLblTotalPagar().setText("TOTAL A PAGAR: " + FormateadorMoneda.formatearPeso(calcularTotalCarrito()));
    }

    private double calcularTotalCarrito() {
        return carrito.stream().mapToDouble(CarritoItemDTO::getSubtotal).sum();
    }

    private void limpiarPos() {
        clienteActual = null;
        carrito.clear();
        panel.getTxtIdentificacionCliente().setText("");
        panel.getTxtCodigoProducto().setText("");
        panel.getTxtCantidad().setText("");
        panel.getLblNombreCliente().setText("Cliente: NO SELECCIONADO");
        panel.getLblTotalPagar().setText("TOTAL A PAGAR: $ 0.00");
        panel.getModeloTabla().setRowCount(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (CMD_BUSCAR_CLIENTE.equals(comando)) {
            buscarCliente();
        } else if (CMD_AGREGAR_PRODUCTO.equals(comando)) {
            agregarProducto();
        } else if (CMD_FINALIZAR_VENTA.equals(comando)) {
            finalizarVenta();
        } else if (CMD_HISTORIAL_CLIENTE.equals(comando)) {
            consultarHistorialCliente();
        } else if (CMD_CONSULTAR_FECHA.equals(comando)) {
            consultarPorFecha();
        } else if (CMD_REIMPRIMIR.equals(comando)) {
            reimprimir();
        } else if (CMD_ANULAR.equals(comando)) {
            anular();
        }
    }
}
