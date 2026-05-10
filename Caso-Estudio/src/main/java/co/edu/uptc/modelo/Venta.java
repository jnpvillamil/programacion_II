package co.edu.uptc.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private String numeroFactura;
    private LocalDateTime fechaHora;
    private Cliente cliente;
    private List<DetalleVenta> detalles;
    private double subtotal;
    private double iva;
    private double total;
    private String formaPago;  
    private String estado;      
    private String usuarioRegistro;
    
    public Venta(String numeroFactura, Cliente cliente, String formaPago, String usuarioRegistro) {
        this.numeroFactura = numeroFactura;
        this.fechaHora = LocalDateTime.now();
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
        this.formaPago = formaPago;
        this.estado = "Activa";
        this.usuarioRegistro = usuarioRegistro;
        this.subtotal = 0;
        this.iva = 0;
        this.total = 0;
    }
    
    
    public void agregarDetalle(Producto producto, int cantidad) {
        
        if(producto.getStockActual() < cantidad) {
            throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
        }
        
        DetalleVenta detalle = new DetalleVenta(producto, cantidad, producto.getPrecioVenta());
        detalles.add(detalle);
        
        
        producto.disminuirStock(cantidad);
        
      
        recalcularTotales();
    }
    
    private void recalcularTotales() {
        subtotal = 0;
        for(DetalleVenta d : detalles) {
            subtotal += d.getSubtotal();
        }
        
        
        iva = subtotal * 0.19;
        total = subtotal + iva;
    }
    
  
    public void anular() {
        if(!"Activa".equals(estado)) {
            throw new RuntimeException("Solo se pueden anular ventas activas");
        }
        
        
        for(DetalleVenta d : detalles) {
            d.getProducto().aumentarStock(d.getCantidad());
        }
        
        this.estado = "Anulada";
    }
    
    
    public String getNumeroFactura() { return numeroFactura; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public Cliente getCliente() { return cliente; }
    public List<DetalleVenta> getDetalles() { return detalles; }
    public double getSubtotal() { return subtotal; }
    public double getIva() { return iva; }
    public double getTotal() { return total; }
    public String getFormaPago() { return formaPago; }
    public String getEstado() { return estado; }
    
    
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setFormaPago(String formaPago) { this.formaPago = formaPago; }
}