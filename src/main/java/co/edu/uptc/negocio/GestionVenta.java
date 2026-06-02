package co.edu.uptc.negocio;

import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.interfaces.RepositorioComercial;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.ExportadorDatos;

import java.time.LocalDateTime;
import java.util.List;

public class GestionVenta {

    private static final double PORCENTAJE_IVA = 0.19;
    private static final String RUTA_LOG_AUDITORIA = "logs/auditoria_venta.txt";

    private final RepositorioComercial persistenciaComercial;
    private final GestionProducto gestionProducto;
    private final GestionContable gestionContable;
    private final GestionCliente gestionCliente;

    public GestionVenta(RepositorioComercial persistenciaComercial, GestionProducto gestionProducto,
                        GestionContable gestionContable, GestionCliente gestionCliente) {
        this.persistenciaComercial = persistenciaComercial;
        this.gestionProducto = gestionProducto;
        this.gestionContable = gestionContable;
        this.gestionCliente = gestionCliente;
    }

    public String realizarVenta(Venta venta) {
        validarVenta(venta);
        calcularTotales(venta);
        persistenciaComercial.guardarVenta(venta, gestionContable::construirAsientoVenta);
        registrarLogVenta(venta, "REGISTRADA");
        return "Venta registrada correctamente. Factura N° " + venta.getNumeroFactura();
    }

    public String anularVenta(String numeroFactura) {
        if (numeroFactura == null || numeroFactura.isBlank()) {
            throw new IllegalStateException("Debe indicar un número de factura válido.");
        }

        Venta ventaAnulada = consultarVenta(numeroFactura);
        if (ventaAnulada == null) {
            throw new IllegalStateException("Factura no encontrada: " + numeroFactura);
        }

        persistenciaComercial.anularVenta(numeroFactura, gestionContable::construirAsientoAnulacionVenta);
        ventaAnulada.setEstado(co.edu.uptc.enums.EstadoVenta.ANULADA);
        registrarLogVenta(ventaAnulada, "ANULADA");
        return "Factura " + numeroFactura + " anulada. Inventario restaurado.";
    }

    public void calcularTotales(Venta venta) {
        double subtotal = 0;
        for (DetalleVenta detalle : venta.getListaDetalles()) {
            subtotal += detalle.getSubtotal();
        }
        double iva = redondear(subtotal * PORCENTAJE_IVA);
        double total = redondear(subtotal + iva);
        venta.setSubtotal(redondear(subtotal));
        venta.setIva(iva);
        venta.setTotal(total);
    }

    public double calcularIVA(double subtotal) {
        return redondear(subtotal * PORCENTAJE_IVA);
    }

    public Producto buscarProducto(String codigoInterno) {
        return gestionProducto.buscarProducto(codigoInterno);
    }

    public Cliente buscarClientePorIdentificacion(String identificacion) {
        return gestionCliente.buscarPorIdentificacion(identificacion);
    }

    public List<VentaDTO> listarVenta() {
        return persistenciaComercial.listarVenta();
    }

    private void validarVenta(Venta venta) {
        if (venta == null || venta.getCliente() == null) {
            throw new IllegalStateException("Datos de cliente incompletos.");
        }
        String identificacion = venta.getCliente().getIdentificacion();
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalStateException("Debe indicar la identificación del cliente.");
        }
        if (venta.getFormaPago() == null) {
            throw new IllegalStateException("Debe seleccionar la forma de pago.");
        }
        if (venta.getListaDetalles() == null || venta.getListaDetalles().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío.");
        }

        Cliente cliente = gestionCliente.buscarCliente(identificacion.trim());
        if (cliente == null) {
            throw new IllegalStateException("El cliente no está registrado en el sistema.");
        }
        if (!cliente.isActivo()) {
            throw new IllegalStateException("El cliente se encuentra inactivo.");
        }
        venta.setCliente(cliente);

        for (DetalleVenta detalle : venta.getListaDetalles()) {
            if (detalle.getCantidad() <= 0) {
                throw new IllegalStateException("La cantidad debe ser mayor a cero.");
            }
            String codigo = detalle.getProducto().getCodigoInterno();
            Producto producto = gestionProducto.buscarProducto(codigo);
            if (producto == null) {
                throw new IllegalStateException("Producto no encontrado: " + codigo);
            }
            if (!producto.isActivo()) {
                throw new IllegalStateException("Producto inactivo: " + codigo);
            }
            if (producto.getStockActual() < detalle.getCantidad()) {
                throw new IllegalStateException(
                        "Stock insuficiente para el producto: " + producto.getNombreProducto());
            }
            detalle.setProducto(producto);
            detalle.setPrecioUnitario(producto.getPrecioVenta());
        }
    }

    private Venta consultarVenta(String numeroFactura) {
        return persistenciaComercial.buscarVentaPorNumeroFactura(numeroFactura);
    }

    private void registrarLogVenta(Venta venta, String accion) {
        String linea = String.format(
                "VENTA|%s|Factura=%s|Cliente=%s|Subtotal=%.2f|Iva=%.2f|Total=%.2f|Fecha=%s",
                accion,
                venta.getNumeroFactura(),
                venta.getCliente().getIdentificacion(),
                venta.getSubtotal(),
                venta.getIva(),
                venta.getTotal(),
                LocalDateTime.now());
        ExportadorDatos.exportarPlano(List.of(linea), RUTA_LOG_AUDITORIA);
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
