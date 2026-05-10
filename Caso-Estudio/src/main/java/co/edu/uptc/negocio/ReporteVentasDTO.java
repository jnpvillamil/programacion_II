package co.edu.uptc.negocio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReporteVentasDTO {
    private LocalDate fecha;
    private double totalVentas;
    private double totalIVA;
    private int numeroFacturas;
    private Map<String, Double> ventasPorFormaPago;
    private List<Object[]> productosMasVendidos;
    private List<Object[]> clientesTop;
    
   
    public ReporteVentasDTO() {}
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public double getTotalVentas() { return totalVentas; }
    public void setTotalVentas(double totalVentas) { this.totalVentas = totalVentas; }
    
    public double getTotalIVA() { return totalIVA; }
    public void setTotalIVA(double totalIVA) { this.totalIVA = totalIVA; }
    
    public int getNumeroFacturas() { return numeroFacturas; }
    public void setNumeroFacturas(int numeroFacturas) { this.numeroFacturas = numeroFacturas; }
    
    public Map<String, Double> getVentasPorFormaPago() { return ventasPorFormaPago; }
    public void setVentasPorFormaPago(Map<String, Double> ventasPorFormaPago) { 
        this.ventasPorFormaPago = ventasPorFormaPago; 
    }
    
    public List<Object[]> getProductosMasVendidos() { return productosMasVendidos; }
    public void setProductosMasVendidos(List<Object[]> productosMasVendidos) { 
        this.productosMasVendidos = productosMasVendidos; 
    }
    
    public List<Object[]> getClientesTop() { return clientesTop; }
    public void setClientesTop(List<Object[]> clientesTop) { 
        this.clientesTop = clientesTop; 
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE DE VENTAS ===\n");
        sb.append("Fecha: ").append(fecha).append("\n");
        sb.append("Total Ventas: $").append(String.format("%,.2f", totalVentas)).append("\n");
        sb.append("Total IVA: $").append(String.format("%,.2f", totalIVA)).append("\n");
        sb.append("Número Facturas: ").append(numeroFacturas).append("\n");
        return sb.toString();
    }
}