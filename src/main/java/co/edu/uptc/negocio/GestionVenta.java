package co.edu.uptc.negocio;

import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.interfaces.RepositorioVenta;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;

public class GestionVenta {

    private static final double PORCENTAJE_IVA = 0.19;

    private final RepositorioVenta persistenciaVenta;
    private final GestionProducto gestionProducto;
    private final GestionContable gestionContable;
    private final GestionCliente gestionCliente;

    public GestionVenta(RepositorioVenta persistenciaVenta, GestionProducto gestionProducto,
                          GestionContable gestionContable, GestionCliente gestionCliente) {
        this.persistenciaVenta = persistenciaVenta;
        this.gestionProducto = gestionProducto;
        this.gestionContable = gestionContable;
        this.gestionCliente = gestionCliente;
    }

    public String realizarVenta(Venta venta) {
        validarVenta(venta);
        calcularTotales(venta);
        persistenciaVenta.guardar(venta);
        registrarContabilidad(venta);
        return "Venta registrada correctamente. Factura N° " + venta.getNumeroFactura();
    }

    public String anularVenta(String numeroFactura) {
        if (numeroFactura == null || numeroFactura.isBlank()) {
            throw new IllegalStateException("Debe indicar un número de factura válido.");
        }
        persistenciaVenta.anularVenta(numeroFactura);
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
                throw new IllegalStateException("Stock insuficiente para " + producto.getNombreProducto()
                        + ". Disponible: " + producto.getStockActual());
            }
            detalle.setProducto(producto);
            detalle.setPrecioUnitario(producto.getPrecioVenta());
        }
    }

    private void registrarContabilidad(Venta venta) {
        String referencia = "Factura " + venta.getNumeroFactura();
        gestionContable.registrarPartidaDoble(
                venta.getTotal(), TipoMovimiento.INGRESO, "Caja/Bancos", "Cobro " + referencia);
        gestionContable.registrarPartidaDoble(
                venta.getSubtotal(), TipoMovimiento.INGRESO, "Ingresos por ventas", "Venta " + referencia);
        gestionContable.registrarPartidaDoble(
                venta.getIva(), TipoMovimiento.INGRESO, "IVA Generado", "IVA " + referencia);
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
