package co.uptc.edu.tienda.negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.enums.FormaPagoEnum;
import co.uptc.edu.tienda.interfaces.IGestionVenta;
import co.uptc.edu.tienda.modelo.Cliente;
import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Venta;

public class GestionVenta {

    private List<Venta> listaVentas;

    private IGestionVenta iVenta;

    private int consecutivo;

    public GestionVenta(
            IGestionVenta iVenta) {

        this.iVenta = iVenta;

        listaVentas =
                iVenta.leerVentas();

        consecutivo =
                listaVentas.size() + 1;
    }

    // =====================================
    // CREAR VENTA
    // =====================================

    public Venta crearVenta(
            Cliente cliente,
            FormaPagoEnum formaPago) {

        Venta venta =
                new Venta();

        venta.setNumeroFactura(
                VentaConfig.PREFIJO_FACTURA
                + consecutivo++);

        venta.setFechaHora(
                LocalDateTime.now().toString());

        venta.setCliente(cliente);

        venta.setFormaPago(formaPago);

        venta.setDetalles(
                new ArrayList<>());

        venta.setImpuestos(0);

        venta.setTotal(0);

        return venta;
    }

    // =====================================
    // AGREGAR PRODUCTO
    // =====================================

    public void agregarProducto(
            Venta venta,
            Producto producto,
            int cantidad) {

        if(cantidad <= 0) {

            throw new IllegalArgumentException(
                    "Cantidad inválida");
        }

        if(cantidad >
                producto.getStockActual()) {

            throw new IllegalArgumentException(
                    "Stock insuficiente");
        }

        DetalleVenta detalle =
                new DetalleVenta(
                        producto,
                        cantidad);

        venta.getDetalles().add(detalle);

        producto.setStockActual(
                producto.getStockActual()
                - cantidad);

        calcularTotal(venta);
    }

    // =====================================
    // CALCULAR TOTAL
    // =====================================

    public void calcularTotal(
            Venta venta) {

        double subtotal = 0;

        for(DetalleVenta d
                : venta.getDetalles()) {

            subtotal += d.getSubtotal();
        }

        double iva =
                subtotal * VentaConfig.IVA;

        venta.setImpuestos(iva);

        venta.setTotal(
                subtotal + iva);
    }

    // =====================================
    // GUARDAR VENTA
    // =====================================

    public void guardarVenta(
            Venta venta) {

        listaVentas.add(venta);

        iVenta.guardar(listaVentas);
    }

    // =====================================
    // LISTAR
    // =====================================

    public List<Venta> listarVentas() {

        return listaVentas;
    }
}