package co.edu.uptc.controlador;

import co.edu.uptc.dto.CarritoItemDTO;
import co.edu.uptc.gui.PanelVentas;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.negocio.GestionClientes;
import co.edu.uptc.negocio.GestionInventario;
import co.edu.uptc.negocio.GestionVentas;
import co.edu.uptc.utilidades.FormateadorMoneda;
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

            // Validar stock disponible en memoria considerando lo que ya está en el carrito
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

        double iva = subtotalGlobal * 0.19; // IVA por defecto
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
            List<DetalleVenta> detalles = new ArrayList<>();
            for (CarritoItemDTO item : carritoTemporal) {
                detalles.add(new DetalleVenta(item.getProducto(), item.getCantidad(), item.getPrecioUnitario(), item.getSubtotal()));
            }

            Venta venta = new Venta();
            venta.setNumeroFactura(gestionVentas.generarNumeroFactura());
            venta.setCliente(clienteActual);
            venta.setProductosVendidos(detalles);
            venta.setFormaPago(vistaVentas.getCbFormaPago().getSelectedItem().toString());
            
            boolean exito = gestionVentas.procesarVenta(venta);
            
            if (exito) {
                JOptionPane.showMessageDialog(vistaVentas, "Venta registrada exitosamente.\nFactura: " + venta.getNumeroFactura(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarTodo();
            } else {
                JOptionPane.showMessageDialog(vistaVentas, "Hubo un error procesando la venta (Posible problema de stock).", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaVentas, "Error crítico al finalizar la venta: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
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