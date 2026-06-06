package co.uptc.edu.co.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionCompra;
import co.uptc.edu.co.interfaces.IGestionConsultas;
import co.uptc.edu.co.interfaces.IGestionMovimientoContable;
import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.interfaces.IGestionVenta;
import co.uptc.edu.co.modelo.Compra;
import co.uptc.edu.co.modelo.MovimientoContable;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Venta;

public class GestionConsultas implements IGestionConsultas {

    private final IGestionVenta gestionVenta;
    private final IGestionCompra gestionCompra;
    private final IGestionProducto gestionProducto;
    private final IGestionMovimientoContable gestionMovimientoContable;

    public GestionConsultas(IGestionVenta gestionVenta, IGestionCompra gestionCompra,
            IGestionProducto gestionProducto, IGestionMovimientoContable gestionMovimientoContable) {

        if (gestionVenta == null) {
            throw new IllegalArgumentException("La gestionVenta no puede ser nula.");
        }
        if (gestionCompra == null) {
            throw new IllegalArgumentException("La gestionCompra no puede ser nula.");
        }
        if (gestionProducto == null) {
            throw new IllegalArgumentException("La gestionProducto no puede ser nula.");
        }
        if (gestionMovimientoContable == null) {
            throw new IllegalArgumentException("La gestionMovimientoContable no puede ser nula.");
        }

        this.gestionVenta = gestionVenta;
        this.gestionCompra = gestionCompra;
        this.gestionProducto = gestionProducto;
        this.gestionMovimientoContable = gestionMovimientoContable;
    }

    @Override
    public List<Venta> obtenerVentasPorFecha(LocalDate fecha) throws Exception {
        if (fecha == null) {
            throw new Exception("La fecha es obligatoria.");
        }

        return gestionVenta.listarPorFecha(fecha);
    }

    @Override
    public List<Compra> obtenerComprasPorProveedor(String codigoProveedor, LocalDate fechaInicio,
            LocalDate fechaFin) throws Exception {
        if (codigoProveedor == null || codigoProveedor.isBlank()) {
            throw new Exception("Debe seleccionar un proveedor.");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new Exception("Debe ingresar las fechas de inicio y fin.");
        }

        List<Compra> compras = gestionCompra.listar();
        List<Compra> comprasFiltradas = new ArrayList<>();

        for (Compra c : compras) {
            if (c != null && c.getCodigoProveedor() != null
                    && c.getCodigoProveedor().equalsIgnoreCase(codigoProveedor)) {
                if (c.getFecha() != null && estaDentroDelPeriodo(c.getFecha(), fechaInicio, fechaFin)) {
                    comprasFiltradas.add(c);
                }
            }
        }

        return comprasFiltradas;
    }

    @Override
    public List<Producto> obtenerProductosStockBajo() throws Exception {
        List<Producto> productos = gestionProducto.listar();
        List<Producto> productosBajo = new ArrayList<>();

        for (Producto p : productos) {
            if (p != null && p.estaActivo() && p.stockBajoMinimo()) {
                productosBajo.add(p);
            }
        }

        return productosBajo;
    }

    @Override
    public List<Venta> obtenerHistorialCliente(String codigoCliente) throws Exception {
        if (codigoCliente == null || codigoCliente.isBlank()) {
            throw new Exception("Debe seleccionar un cliente.");
        }

        List<Venta> ventas = gestionVenta.listar();
        List<Venta> historial = new ArrayList<>();

        for (Venta v : ventas) {
            if (v != null && v.getCodigoCliente() != null
                    && v.getCodigoCliente().equalsIgnoreCase(codigoCliente)) {
                historial.add(v);
            }
        }

        return historial;
    }

    @Override
    public List<MovimientoContable> obtenerMovimientosContables(String cuenta, String tipoMovimiento,
            LocalDate fechaInicio, LocalDate fechaFin) throws Exception {
        if (cuenta == null || cuenta.isBlank()) {
            throw new Exception("Debe seleccionar una cuenta.");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new Exception("Debe ingresar las fechas de inicio y fin.");
        }

        List<MovimientoContable> movimientos = gestionMovimientoContable.listarMovimientos();
        List<MovimientoContable> movimientosFiltrados = new ArrayList<>();

        for (MovimientoContable m : movimientos) {
            if (m != null && m.getCuentaContable() != null
                    && m.getCuentaContable().equalsIgnoreCase(cuenta)
                    && m.getFecha() != null
                    && estaDentroDelPeriodo(m.getFecha(), fechaInicio, fechaFin)) {

                boolean coincideTipo = true;

                if (tipoMovimiento != null && !tipoMovimiento.isBlank()
                        && !"Todos".equalsIgnoreCase(tipoMovimiento)) {
                    if (m.getTipoMovimientoContable() == null) {
                        coincideTipo = false;
                    } else {
                        coincideTipo = m.getTipoMovimientoContable().name().equalsIgnoreCase(tipoMovimiento);
                    }
                }

                if (coincideTipo) {
                    movimientosFiltrados.add(m);
                }
            }
        }

        return movimientosFiltrados;
    }

    private boolean estaDentroDelPeriodo(LocalDate fecha, LocalDate fechaInicio, LocalDate fechaFin) {
        return !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin);
    }
}