package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.IGestionCompra;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleCompra;
import java.time.LocalDateTime;
import java.util.List;

public class GestionCompraNegocio {

    private final IGestionCompra persistencia;

    public GestionCompraNegocio(IGestionCompra persistencia) {
        this.persistencia = persistencia;
    }

    //  MÉTODOS PÚBLICOS 

    public void registrarCompra(Compra compra) {
        validarCompraNoNula(compra);
        validarFacturaProveedor(compra.getNumeroFacturaProveedor());
        validarUnicidadFactura(compra.getNumeroFacturaProveedor());
        validarProveedor(compra.getProveedor());
        validarDetalles(compra.getDetalles());
        validarFechaHora(compra.getFechaHora());
        validarEstadoInicial(compra.getEstado());
        persistencia.crearCompra(compra);
    }

    public void anularCompra(String numeroFacturaProveedor) {
        if (numeroFacturaProveedor == null || numeroFacturaProveedor.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de factura del proveedor es obligatorio.");
        }
        Compra compra = persistencia.buscarCompra(numeroFacturaProveedor);
        if (compra == null) {
            throw new IllegalArgumentException("No existe una compra con factura: " + numeroFacturaProveedor);
        }
        if ("Anulada".equals(compra.getEstado())) {
            throw new IllegalStateException("La compra ya está anulada.");
        }
        persistencia.anularCompra(numeroFacturaProveedor);
    }

    public Compra buscarCompra(String numeroFacturaProveedor) {
        if (numeroFacturaProveedor == null || numeroFacturaProveedor.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de factura es obligatorio.");
        }
        return persistencia.buscarCompra(numeroFacturaProveedor);
    }

    public List<Compra> listarTodasLasCompras() {
        return persistencia.listarCompras();
    }

    //  MÉTODOS PRIVADOS DE VALIDACIÓN 

    private void validarCompraNoNula(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La compra no puede ser nula.");
        }
    }

    private void validarFacturaProveedor(String factura) {
        if (factura == null || factura.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de factura del proveedor es obligatorio.");
        }
        if (factura.trim().length() < 3) {
            throw new IllegalArgumentException("La factura debe tener al menos 3 caracteres.");
        }
    }

    private void validarUnicidadFactura(String factura) {
        Compra existente = persistencia.buscarCompra(factura);
        if (existente != null) {
            throw new IllegalStateException("Ya existe una compra registrada con esa factura: " + factura);
        }
    }

    private void validarProveedor(co.edu.uptc.modelo.Proveedor proveedor) {
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo.");
        }
        if (proveedor.getCodigo() == null || proveedor.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código del proveedor no es válido.");
        }
    }

    private void validarDetalles(List<DetalleCompra> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un detalle (producto).");
        }
        for (DetalleCompra detalle : detalles) {
            if (detalle.getProducto() == null) {
                throw new IllegalArgumentException("Un detalle tiene producto nulo.");
            }
            if (detalle.getProducto().getCodigo() == null || detalle.getProducto().getCodigo().trim().isEmpty()) {
                throw new IllegalArgumentException("Código de producto inválido en un detalle.");
            }
            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero. Producto: " + detalle.getProducto().getCodigo());
            }
            if (detalle.getPrecioCompra() <= 0) {
                throw new IllegalArgumentException("El precio de compra debe ser mayor a cero. Producto: " + detalle.getProducto().getCodigo());
            }
        }
    }

    private void validarFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora de la compra no pueden ser nulas.");
        }
        if (fechaHora.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de la compra no puede ser futura.");
        }
    }

    private void validarEstadoInicial(String estado) {
        if (!"Activa".equals(estado)) {
            throw new IllegalArgumentException("La compra debe crearse con estado 'Activa'.");
        }
    }
}