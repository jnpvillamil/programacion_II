package co.edu.uptc.negocio;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.interfaces.RepositorioComercial;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleCompra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.utilidades.ExportadorDatos;

import java.time.LocalDateTime;
import java.util.List;

public class GestionCompra {

    private static final double PORCENTAJE_IVA = 0.19;
    private static final String RUTA_LOG_AUDITORIA = "logs/auditoria_compra.txt";

    private final RepositorioComercial persistenciaComercial;
    private final GestionProducto gestionProducto;
    private final GestionContable gestionContable;
    private final GestionProveedor gestionProveedor;

    public GestionCompra(RepositorioComercial persistenciaComercial,
                         GestionProducto gestionProducto,
                         GestionContable gestionContable,
                         GestionProveedor gestionProveedor) {
        this.persistenciaComercial = persistenciaComercial;
        this.gestionProducto = gestionProducto;
        this.gestionContable = gestionContable;
        this.gestionProveedor = gestionProveedor;
    }

    public String registrarCompra(Compra compra) {
        validarCompra(compra);
        calcularTotale(compra);
        double subtotal = calcularSubtotal(compra);
        double iva = calcularIVA(subtotal);
        persistenciaComercial.guardarCompra(compra, c -> gestionContable.construirAsientoCompra(c, subtotal, iva));
        registrarLogCompra(compra, subtotal, iva);
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

    private void registrarLogCompra(Compra compra, double subtotal, double iva) {
        String linea = String.format(
                "COMPRA|Factura=%s|Proveedor=%s|Subtotal=%.2f|Iva=%.2f|Total=%.2f|Fecha=%s",
                compra.getNumeroFacturaProveedor(),
                compra.getProveedor().getNit(),
                subtotal,
                iva,
                compra.getTotal(),
                LocalDateTime.now());
        ExportadorDatos.exportarPlano(List.of(linea), RUTA_LOG_AUDITORIA);
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
