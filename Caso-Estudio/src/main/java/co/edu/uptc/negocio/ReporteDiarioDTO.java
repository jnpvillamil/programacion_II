package co.edu.uptc.negocio;

import java.util.List;
import java.util.Map;

public class ReporteDiarioDTO {
    
    private String fecha;
    private double totalVentas;
    private double totalCompras;
    private double utilidadBruta;
    private List<Map<String, Object>> ventasPorFormaPago;
    private List<Map<String, Object>> productosMasVendidos;
    private Map<String, Double> resumenContable;
    
   
    public ReporteDiarioDTO() {}
    

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    
    public double getTotalVentas() { return totalVentas; }
    public void setTotalVentas(double totalVentas) { this.totalVentas = totalVentas; }
    
    public double getTotalCompras() { return totalCompras; }
    public void setTotalCompras(double totalCompras) { this.totalCompras = totalCompras; }
    
    public double getUtilidadBruta() { return utilidadBruta; }
    public void setUtilidadBruta(double utilidadBruta) { this.utilidadBruta = utilidadBruta; }
    
    public List<Map<String, Object>> getVentasPorFormaPago() { return ventasPorFormaPago; }
    public void setVentasPorFormaPago(List<Map<String, Object>> ventasPorFormaPago) { 
        this.ventasPorFormaPago = ventasPorFormaPago; 
    }
    
    public List<Map<String, Object>> getProductosMasVendidos() { return productosMasVendidos; }
    public void setProductosMasVendidos(List<Map<String, Object>> productosMasVendidos) { 
        this.productosMasVendidos = productosMasVendidos; 
    }
    
    public Map<String, Double> getResumenContable() { return resumenContable; }
    public void setResumenContable(Map<String, Double> resumenContable) { 
        this.resumenContable = resumenContable; 
    }
}