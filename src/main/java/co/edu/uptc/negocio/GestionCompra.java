package co.edu.uptc.negocio;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.interfaces.ProveedorUsuarioSesion;
import co.edu.uptc.interfaces.RepositorioComercial;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleCompra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;

import java.util.List;

public class GestionCompra {

    private static final double PORCENTAJE_IVA = 0.19;

    private final RepositorioComercial persistenciaComercial;
    private final GestionProducto gestionProducto;
    private final GestionContable gestionContable;
    private final GestionProveedor gestionProveedor;
    private final ServicioAuditoria servicioAuditoria;
    private final ProveedorUsuarioSesion proveedorUsuarioSesion;

    public GestionCompra(RepositorioComercial persistenciaComercial,
                         GestionProducto gestionProducto,
                         GestionContable gestionContable,
                         GestionProveedor gestionProveedor,
                         ServicioAuditoria servicioAuditoria,
                         ProveedorUsuarioSesion proveedorUsuarioSesion) {
        this.persistenciaComercial = persistenciaComercial;
        this.gestionProducto = gestionProducto;
        this.gestionContable = gestionContable;
        this.gestionProveedor = gestionProveedor;
        this.servicioAuditoria = servicioAuditoria;
        this.proveedorUsuarioSesion = proveedorUsuarioSesion;
    }

    public String registrarCompra(Compra compra) {
        validarCompra(compra);
        calcularTotale(compra);
        double subtotal = calcularSubtotal(compra);
        double iva = calcularIVA(subtotal);
        persistenciaComercial.guardarCompra(compra, c -> gestionContable.construirAsientoCompra(c, subtotal, iva));
        servicioAuditoria.registrarCompra(compra, proveedorUsuarioSesion.obtenerLoginOperador());
        return "Compra registrada correctamente. Factura proveedor " + compra.getNumeroFacturaProveedor();
    }

    public List<CompraDTO> listarCompra() {
        return persistenciaComercial.listarCompra();
    }

    private void validarCompra(Compra compra) {
        if (compra == null) {
            throw new IllegalStateException("La compra no puede ser nula.");
        }
        if (compra.getNumeroFacturaProveedor() == null || compra.getNumeroFacturaProveedor().isBlank()) {
            throw new IllegalStateException("Debe indicar el número de factura del proveedor.");
        }
        if (compra.getProveedor() == null || compra.getProveedor().getNit() == null
                || compra.getProveedor().getNit().isBlank()) {
            throw new IllegalStateException("Debe indicar el NIT del proveedor.");
        }
        if (compra.getListaDetalles() == null || compra.getListaDetalles().isEmpty()) {
            throw new IllegalStateException("Debe agregar al menos un producto a la compra.");
        }

        Proveedor proveedor = gestionProveedor.buscarPorNit(compra.getProveedor().getNit().trim());
        if (proveedor == null) {
            throw new IllegalStateException("El proveedor no está registrado en el sistema.");
        }
        if (!proveedor.isActivo()) {
            throw new IllegalStateException("El proveedor se encuentra inactivo.");
        }
        compra.setProveedor(proveedor);

        for (DetalleCompra detalle : compra.getListaDetalles()) {
            if (detalle.getCantidad() <= 0) {
                throw new IllegalStateException("La cantidad debe ser mayor a cero.");
            }
            if (detalle.getCostoUnitario() <= 0) {
                throw new IllegalStateException("El costo unitario debe ser mayor a cero.");
            }
            String codigo = detalle.getProducto().getCodigoInterno();
            Producto producto = gestionProducto.buscarProducto(codigo);
            if (producto == null) {
                throw new IllegalStateException("Producto no encontrado: " + codigo);
            }
            if (!producto.isActivo()) {
                throw new IllegalStateException("Producto inactivo: " + codigo);
            }
            if (producto.getStockActual() + detalle.getCantidad() > producto.getStockMaximo()) {
                throw new IllegalStateException(
                        "El stock final supera el máximo permitido para el producto: "
                                + producto.getNombreProducto());
            }
            detalle.setProducto(producto);
        }
    }

    private void calcularTotale(Compra compra) {
        double subtotal = calcularSubtotal(compra);
        double iva = calcularIVA(subtotal);
        compra.setTotal(redondear(subtotal + iva));
    }

    private double calcularSubtotal(Compra compra) {
        double subtotal = 0;
        for (DetalleCompra detalle : compra.getListaDetalles()) {
            subtotal += detalle.getSubtotal();
        }
        return redondear(subtotal);
    }

    private double calcularIVA(double subtotal) {
        return redondear(subtotal * PORCENTAJE_IVA);
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
