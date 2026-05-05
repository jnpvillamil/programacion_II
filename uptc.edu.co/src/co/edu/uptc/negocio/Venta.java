package co.edu.uptc.negocio;
import java.util.ArrayList;
import java.util.UUID;

public class Venta {
    private String numeroFactura;
    private String cufe;
    private ArrayList<DetalleVenta> detalles;
    private double subtotal;
    private double iva;
    private double total;
    private Cliente cliente;

    public Venta() {
        this.detalles = new ArrayList<>();
        this.cufe = UUID.randomUUID().toString().toUpperCase(); 
    }

    public void agregarDetalle(Producto p, int cantidad) {
        DetalleVenta dv = new DetalleVenta(p, cantidad);
        detalles.add(dv);
        subtotal += dv.getSubtotal();

        this.iva = subtotal * 0.19;
        this.total = subtotal + iva;
        p.reducirStock(cantidad);
    }

    public double getSubtotal() { return subtotal; }
    public double getIva() { return iva; }
    public double getTotal() { return total; }
    public String getCufe() { return cufe; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}