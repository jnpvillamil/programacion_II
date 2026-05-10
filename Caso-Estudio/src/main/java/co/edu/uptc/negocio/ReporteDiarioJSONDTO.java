package co.edu.uptc.negocio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReporteDiarioJSONDTO {
    
    private String fecha;
    private double total_ventas;
    private double total_compras;
    private double utilidad_bruta;
    private List<Map<String, Object>> ventas_por_forma_pago;
    private List<Map<String, Object>> productos_mas_vendidos;
    private Map<String, Double> resumen_contable;
    
    
    public ReporteDiarioJSONDTO() {}
    
   
    public ReporteDiarioJSONDTO(LocalDate fecha, double totalVentas, double totalCompras, 
                                 double utilidadBruta, List<Map<String, Object>> ventasPorFormaPago,
                                 List<Map<String, Object>> productosMasVendidos,
                                 Map<String, Double> resumenContable) {
        this.fecha = fecha.toString();
        this.total_ventas = totalVentas;
        this.total_compras = totalCompras;
        this.utilidad_bruta = utilidadBruta;
        this.ventas_por_forma_pago = ventasPorFormaPago;
        this.productos_mas_vendidos = productosMasVendidos;
        this.resumen_contable = resumenContable;
    }
    
   
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    
    public double getTotal_ventas() { return total_ventas; }
    public void setTotal_ventas(double total_ventas) { this.total_ventas = total_ventas; }
    
    public double getTotal_compras() { return total_compras; }
    public void setTotal_compras(double total_compras) { this.total_compras = total_compras; }
    
    public double getUtilidad_bruta() { return utilidad_bruta; }
    public void setUtilidad_bruta(double utilidad_bruta) { this.utilidad_bruta = utilidad_bruta; }
    
    public List<Map<String, Object>> getVentas_por_forma_pago() { return ventas_por_forma_pago; }
    public void setVentas_por_forma_pago(List<Map<String, Object>> ventas_por_forma_pago) { 
        this.ventas_por_forma_pago = ventas_por_forma_pago; 
    }
    
    public List<Map<String, Object>> getProductos_mas_vendidos() { return productos_mas_vendidos; }
    public void setProductos_mas_vendidos(List<Map<String, Object>> productos_mas_vendidos) { 
        this.productos_mas_vendidos = productos_mas_vendidos; 
    }
    
    public Map<String, Double> getResumen_contable() { return resumen_contable; }
    public void setResumen_contable(Map<String, Double> resumen_contable) { 
        this.resumen_contable = resumen_contable; 
    }
}