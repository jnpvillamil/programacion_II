package co.edu.uptc.controlador;

import co.edu.uptc.dto.CarritoItemDTO;
import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.enums.FormaPago;
import co.edu.uptc.gui.PanelVentas;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.negocio.GestionClientes;
import co.edu.uptc.negocio.GestionInventario;
import co.edu.uptc.negocio.GestionVentas;
import co.edu.uptc.utilidades.FormateadorMoneda;
import co.edu.uptc.utilidades.ManejadorFechas;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControladorVentas {

    private PanelVentas vistaVentas;
    private GestionVentas gestionVentas;
    private GestionInventario gestionInventario;
    private GestionClientes gestionClientes;

    private Cliente clienteActual;
    private List<CarritoItemDTO> carritoTemporal;
    private double totalVentaTemporal;

    public ControladorVentas(PanelVentas vistaVentas, GestionVentas gestionVentas, GestionInventario gestionInventario, GestionClientes gestionClientes) {
        this.vistaVentas = vistaVentas;
        this.gestionVentas = gestionVentas;
        this.gestionInventario = gestionInventario;
        this.gestionClientes = gestionClientes;
        this.carritoTemporal = new ArrayList<>();
        this.totalVentaTemporal = 0;

        inicializarEventos();
    }

    public ControladorVentas(PanelVentas vistaVentas) {
        this(vistaVentas,
             new GestionVentas(new GestionInventario()),
             new GestionInventario(),
             new GestionClientes());
    }

    private void inicializarEventos() {
        vistaVentas.getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCliente();
            }
        });

        vistaVentas.getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProductoCarrito();
            }
        });

        vistaVentas.getBtnFinalizarVenta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarVenta();
            }
        });

        vistaVentas.getBtnHistorialCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarHistorialCliente();
            }
        });

        vistaVentas.getBtnConsultarPorFecha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarVentasPorFecha();
            }
        });

        vistaVentas.getBtnReimprimir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reimprimirComprobante();
            }
        });

        vistaVentas.getBtnAnularVenta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anularVenta();
            }
        });

        vistaVentas.getTablaVentas().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = vistaVentas.getTablaVentas().getSelectedRow();
                if (fila >= 0) {
                    Object factura = vistaVentas.getModeloTablaVentas().getValueAt(fila, 0);
                    vistaVentas.getTxtNumeroFactura().setText(factura != null ? factura.toString() : "");
                }
            }
        });
    }

    private void buscarCliente() {
        String identificacion = vistaVentas.getTxtIdentificacionCliente().getText();
        if (ValidadorEntradas.esVacio(identificacion)) {
            JOptionPane.showMessageDialog(vistaVentas, "Ingrese la identificación del cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Cliente cliente = gestionClientes.buscarCliente(identificacion);
            if (cliente != null && cliente.isActivo()) {
                this.clienteActual = cliente;
                vistaVentas.getLblNombreCliente().setText("Cliente: " + cliente.getNombre());
            } else {
                this.clienteActual = null;
                vistaVentas.getLblNombreCliente().setText("Cliente: NO ENCONTRADO O INACTIVO");
                JOptionPane.showMessageDialog(vistaVentas, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaVentas, "Error en la búsqueda: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProductoCarrito() {
        String codigo = vistaVentas.getTxtCodigoProducto().getText();
        String cantidadStr = vistaVentas.getTxtCantidad().getText();

        if (ValidadorEntradas.esVacio(codigo) || !ValidadorEntradas.esNumero(cantidadStr)) {
            JOptionPane.showMessageDialog(vistaVentas, "Código vacío o cantidad no numérica.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(vistaVentas, "La cantidad debe ser mayor a 0.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Producto producto = gestionInventario.buscarProducto(codigo);
            if (producto == null || !producto.isActivo()) {
                JOptionPane.showMessageDialog(vistaVentas, "Producto no encontrado o inactivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidadEnCarrito = carritoTemporal.stream()
                    .filter(item -> item.getProducto().getCodigoProducto().equals(codigo))
                    .mapToInt(CarritoItemDTO::getCantidad)
                    .sum();

            if ((cantidadEnCarrito + cantidad) > producto.getStockActual()) {
                JOptionPane.showMessageDialog(vistaVentas, "Stock insuficiente. Stock actual: " + producto.getStockActual(), "Aviso Stock", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double subtotal = producto.getPrecioVenta() * cantidad;
            CarritoItemDTO item = new CarritoItemDTO(producto, cantidad, producto.getPrecioVenta(), subtotal);
            carritoTemporal.add(item);

            vistaVentas.getTxtCodigoProducto().setText("");
            vistaVentas.getTxtCantidad().setText("");
            actualizarTablaCarrito();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaVentas, "Error al agregar producto: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaCarrito() {
        DefaultTableModel modelo = vistaVentas.getModeloTabla();
        modelo.setRowCount(0);
        double subtotalGlobal = 0;

        for (CarritoItemDTO item : carritoTemporal) {
            Object[] fila = {
                    item.getProducto().getCodigoProducto(),
                    item.getProducto().getNombreProducto(),
                    item.getCantidad(),
                    FormateadorMoneda.formatearPeso(item.getPrecioUnitario()),
                    FormateadorMoneda.formatearPeso(item.getSubtotal())
            };
            modelo.addRow(fila);
            subtotalGlobal += item.getSubtotal();
        }

        double iva = subtotalGlobal * 0.19;
        this.totalVentaTemporal = subtotalGlobal + iva;
        vistaVentas.getLblTotalPagar().setText("TOTAL A PAGAR: " + FormateadorMoneda.formatearPeso(totalVentaTemporal));
    }

    private void finalizarVenta() {
        if (clienteActual == null) {
            JOptionPane.showMessageDialog(vistaVentas, "Debe seleccionar un cliente antes de finalizar.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carritoTemporal.isEmpty()) {
            JOptionPane.showMessageDialog(vistaVentas, "El carrito está vacío.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            FormaPago formaPago = (FormaPago) vistaVentas.getCbFormaPago().getSelectedItem();
            if (formaPago == null) {
                formaPago = FormaPago.EFECTIVO;
            }
            VentaDTO ventaDto = new VentaDTO(
                    gestionVentas.generarNumeroFactura(),
                    null,
                    clienteActual.getNombre(),
                    new ArrayList<>(carritoTemporal),
                    totalVentaTemporal,
                    formaPago.name());

            Venta venta = gestionVentas.procesarVentaDesdeDto(ventaDto, clienteActual);

            if (venta != null) {
                JOptionPane.showMessageDialog(vistaVentas,
                        "Venta registrada exitosamente.\n" + gestionVentas.generarFactura(venta),
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarTodo();
            } else {
                JOptionPane.showMessageDialog(vistaVentas, "Hubo un error procesando la venta (Posible problema de stock).", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaVentas, "Error crítico al finalizar la venta: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarHistorialCliente() {
        String identificacion = vistaVentas.getTxtIdClienteConsulta().getText();
        if (ValidadorEntradas.esVacio(identificacion)) {
            JOptionPane.showMessageDialog(vistaVentas, "Ingrese la identificación del cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Venta> ventas = gestionVentas.consultarHistorialCliente(identificacion.trim());
            cargarVentasEnTabla(ventas);
            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(vistaVentas, "No se encontraron ventas para ese cliente.", "Consulta", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaVentas, "Error al consultar historial: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarVentasPorFecha() {
        String fecha = vistaVentas.getTxtFechaConsulta().getText();
        if (ValidadorEntradas.esVacio(fecha)) {
            JOptionPane.showMessageDialog(vistaVentas, "Ingrese la fecha en formato dd/MM/yyyy.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Venta> ventas = gestionVentas.consultarVentasPorFecha(fecha.trim());
            cargarVentasEnTabla(ventas);
            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(vistaVentas, "No se encontraron ventas para esa fecha.", "Consulta", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaVentas, "Error al consultar por fecha: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reimprimirComprobante() {
        String numeroFactura = obtenerNumeroFacturaSeleccionado();
        if (ValidadorEntradas.esVacio(numeroFactura)) {
            JOptionPane.showMessageDialog(vistaVentas, "Ingrese o seleccione un número de factura.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String comprobante = gestionVentas.reimprimirComprobante(numeroFactura.trim());
            JOptionPane.showMessageDialog(vistaVentas, comprobante, "Comprobante", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaVentas, "Error al reimprimir: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void anularVenta() {
        String numeroFactura = obtenerNumeroFacturaSeleccionado();
        if (ValidadorEntradas.esVacio(numeroFactura)) {
            JOptionPane.showMessageDialog(vistaVentas, "Ingrese o seleccione un número de factura.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                vistaVentas,
                "¿Desea anular la factura " + numeroFactura + "?",
                "Confirmar anulación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean anulada = gestionVentas.anularVenta(numeroFactura.trim());
            if (anulada) {
                JOptionPane.showMessageDialog(vistaVentas, "Venta anulada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refrescarConsultaActual();
            } else {
                JOptionPane.showMessageDialog(vistaVentas, "No se encontró la factura o ya estaba anulada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaVentas, "Error al anular venta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerNumeroFacturaSeleccionado() {
        int fila = vistaVentas.getTablaVentas().getSelectedRow();
        if (fila >= 0) {
            Object factura = vistaVentas.getModeloTablaVentas().getValueAt(fila, 0);
            if (factura != null && !factura.toString().isBlank()) {
                return factura.toString();
            }
        }
        return vistaVentas.getTxtNumeroFactura().getText();
    }

    private void cargarVentasEnTabla(List<Venta> ventas) {
        DefaultTableModel modelo = vistaVentas.getModeloTablaVentas();
        modelo.setRowCount(0);

        for (Venta venta : ventas) {
            String nombreCliente = "";
            if (venta.getCliente() != null && venta.getCliente().getNombre() != null) {
                nombreCliente = venta.getCliente().getNombre();
            }

            Object[] fila = {
                    venta.getNumeroFactura(),
                    ManejadorFechas.formatearFecha(venta.getFechaHora()),
                    nombreCliente,
                    FormateadorMoneda.formatearPeso(venta.getSubtotal()),
                    FormateadorMoneda.formatearPeso(venta.getIvaAplicado()),
                    FormateadorMoneda.formatearPeso(venta.getTotalVenta()),
                    venta.getFormaPago() != null ? venta.getFormaPago().name() : ""
            };
            modelo.addRow(fila);
        }
    }

    private void refrescarConsultaActual() {
        String identificacion = vistaVentas.getTxtIdClienteConsulta().getText();
        String fecha = vistaVentas.getTxtFechaConsulta().getText();

        if (!ValidadorEntradas.esVacio(identificacion)) {
            cargarVentasEnTabla(gestionVentas.consultarHistorialCliente(identificacion.trim()));
        } else if (!ValidadorEntradas.esVacio(fecha)) {
            cargarVentasEnTabla(gestionVentas.consultarVentasPorFecha(fecha.trim()));
        } else {
            vistaVentas.getModeloTablaVentas().setRowCount(0);
        }
    }

    private void limpiarTodo() {
        this.clienteActual = null;
        this.carritoTemporal.clear();
        this.totalVentaTemporal = 0;

        vistaVentas.getTxtIdentificacionCliente().setText("");
        vistaVentas.getLblNombreCliente().setText("Cliente: NO SELECCIONADO");
        vistaVentas.getTxtCodigoProducto().setText("");
        vistaVentas.getTxtCantidad().setText("");
        vistaVentas.getCbFormaPago().setSelectedIndex(0);

        actualizarTablaCarrito();
    }
}
