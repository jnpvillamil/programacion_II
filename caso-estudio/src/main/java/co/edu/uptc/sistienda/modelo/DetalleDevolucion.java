package co.edu.uptc.sistienda.modelo;

/**
 * Representa un producto devuelto dentro de una devolución.
 * Puede ser una cantidad parcial de lo que se compró originalmente.
 */
public class DetalleDevolucion {

    private DetalleVenta ventaOriginal;
    private int cantidadDevuelta;
    private String motivo;

    public DetalleDevolucion() {
    }

    public DetalleDevolucion(DetalleVenta ventaOriginal, int cantidadDevuelta) {
        this.ventaOriginal = ventaOriginal;
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public DetalleDevolucion(DetalleVenta ventaOriginal, int cantidadDevuelta, String motivo) {
        this(ventaOriginal, cantidadDevuelta);
        this.motivo = motivo;
    }

    // Calcula cuánto se le reembolsa al cliente por esa devolución o producto
    public double getSubtotalReembolso() {
        if (ventaOriginal == null) return 0;
        double precioConDescuento = ventaOriginal.getPrecioUnitario()
                * (1 - ventaOriginal.getDescuentoDto() / 100.0);
        return precioConDescuento * cantidadDevuelta;
    }

    public DetalleVenta getVentaOriginal() {
        return ventaOriginal;
    }

    public void setVentaOriginal(DetalleVenta ventaOriginal) {
        this.ventaOriginal = ventaOriginal;
    }

    public int getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(int cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}