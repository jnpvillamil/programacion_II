package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.IGestionVenta;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import java.util.List;

public class GestionVentaNegocio {

    private final IGestionVenta persistencia;

    public GestionVentaNegocio(IGestionVenta persistencia) {
        this.persistencia = persistencia;
    }

    // ===== MÉTODOS PÚBLICOS =====
    public void registrarVenta(Venta venta) {
        validarVentaNula(venta);
        validarCliente(venta.getCliente());
        validarFormaPago(venta.getFormaPago());
        validarDetalles(venta.getDetalles());
        validarTotales(venta);
        persistencia.crearVenta(venta);
    }

    public void anularVenta(String numeroFactura) {
        if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de factura es obligatorio.");
        }
        Venta venta = persistencia.buscarVenta(numeroFactura);
        if (venta == null) {
            throw new IllegalArgumentException("No existe una venta con número de factura: " + numeroFactura);
        }
        persistencia.anularVenta(numeroFactura);
    }

    public List<Venta> listarVentas() {
        return persistencia.listarVentas();
    }
    
    public Venta buscarVenta(String numeroFactura) {
        if (numeroFactura == null || numeroFactura.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de factura es obligatorio.");
        }

        Venta venta = persistencia.buscarVenta(numeroFactura);
        if (venta == null) {
            throw new IllegalArgumentException("No existe una venta con número de factura: " + numeroFactura);
        }

        return venta;
    }


    // ===== VALIDACIONES PRIVADAS =====
    private void validarVentaNula(Venta venta) {
        if (venta == null) throw new IllegalArgumentException("La venta no puede ser nula.");
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Debe seleccionar un cliente.");
        }
        if (cliente.getCodigo() == null || cliente.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El cliente debe tener un código válido.");
        }
        if (!cliente.isActivo()) {
            throw new IllegalArgumentException("El cliente seleccionado está inactivo.");
        }
    }

    private void validarFormaPago(String formaPago) {
        if (formaPago == null || formaPago.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar una forma de pago.");
        }
    }

    private void validarDetalles(List<DetalleVenta> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("Debe agregar al menos un producto a la venta.");
        }
        for (DetalleVenta d : detalles) {
            if (d.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad del producto " 
                    + d.getProducto().getNombre() + " debe ser mayor a cero.");
            }
        }
    }

    private void validarTotales(Venta venta) {
        if (venta.getTotal() <= 0) {
            throw new IllegalArgumentException("El total de la venta debe ser mayor a cero.");
        }
    }
}
