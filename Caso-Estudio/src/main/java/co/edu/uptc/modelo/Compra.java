package co.edu.uptc.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private String numeroFacturaProveedor;
    private LocalDateTime fechaHora;
    private Proveedor proveedor;
    private List<DetalleCompra> detalles;
    private double subtotal;
    private double iva;
    private double total;
    private String estado;  
    private String usuarioRegistro;
    
    public Compra(String numeroFacturaProveedor, Proveedor proveedor, String usuarioRegistro) {
        this.numeroFacturaProveedor = numeroFacturaProveedor;
        this.fechaHora = LocalDateTime.now();
        this.proveedor = proveedor;
        this.detalles = new ArrayList<>();
        this.estado = "Activa";
        this.usuarioRegistro = usuarioRegistro;
        this.subtotal = 0;
        this.iva = 0;
        this.total = 0;
    }
    
  
    public void agregarDetalle(Producto producto, int cantidad, double precioCompra) {
        DetalleCompra detalle = new DetalleCompra(producto, cantidad, precioCompra);
        detalles.add(detalle);
        
       
        producto.setPrecioCompra(precioCompra);
        producto.aumentarStock(cantidad);
        
        
        recalcularTotales();
    }
    
    private void recalcularTotales() {
        subtotal = 0;
        for(DetalleCompra d : detalles) {
            subtotal += d.getSubtotal();
        }
        
        
        iva = subtotal * 0.19;
        total = subtotal + iva;
    }
    
   
    public void anular() {
        if(!"Activa".equals(estado)) {
            throw new RuntimeException("Solo se pueden anular compras activas");
        }
        
       
        for(DetalleCompra d : detalles) {
            d.getProducto().disminuirStock(d.getCantidad());
        }
        
        this.estado = "Anulada";
    }
    
   
    public String getNumeroFacturaProveedor() { return numeroFacturaProveedor; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public Proveedor getProveedor() { return proveedor; }
    public List<DetalleCompra> getDetalles() { return detalles; }
    public double getSubtotal() { return subtotal; }
    public double getIva() { return iva; }
    public double getTotal() { return total; }
    public String getEstado() { return estado; }
    
  
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
}