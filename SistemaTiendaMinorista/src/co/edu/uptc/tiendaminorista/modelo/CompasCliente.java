package co.edu.uptc.tiendaminorista.modelo;

import java.util.Date;
import java.text.SimpleDateFormat;

public class CompasCliente {
    
    private Cliente cliente;    
    private Producto producto;   
    private int cantidad;        
    private double totalCompra;  
    private Date fecha;        

    public CompasCliente() {
        this.fecha = new Date();
    }

    public CompasCliente(Cliente cliente, Producto producto, int cantidad) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = new Date();
        calcularTotal();
    }


    public void calcularTotal() {
        if (this.producto != null) {
            this.totalCompra = this.cantidad * this.producto.getPrecioCompra();
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        calcularTotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularTotal(); 
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaFormateada() {
        if (fecha == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(fecha);
    }
}