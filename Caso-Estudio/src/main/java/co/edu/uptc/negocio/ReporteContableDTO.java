package co.edu.uptc.negocio;

import java.time.LocalDate;
import java.util.Map;

public class ReporteContableDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double ingresos;
    private double egresos;
    private double utilidadBruta;
    private double ivaGenerado;
    private double ivaDescontable;
    private double ivaPorPagar;
    private Map<String, Double> balanceCuentas;
    
   
    public ReporteContableDTO() {}
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    
    public double getIngresos() { return ingresos; }
    public void setIngresos(double ingresos) { this.ingresos = ingresos; }
    
    public double getEgresos() { return egresos; }
    public void setEgresos(double egresos) { this.egresos = egresos; }
    
    public double getUtilidadBruta() { return utilidadBruta; }
    public void setUtilidadBruta(double utilidadBruta) { this.utilidadBruta = utilidadBruta; }
    
    public double getIvaGenerado() { return ivaGenerado; }
    public void setIvaGenerado(double ivaGenerado) { this.ivaGenerado = ivaGenerado; }
    
    public double getIvaDescontable() { return ivaDescontable; }
    public void setIvaDescontable(double ivaDescontable) { this.ivaDescontable = ivaDescontable; }
    
    public double getIvaPorPagar() { return ivaPorPagar; }
    public void setIvaPorPagar(double ivaPorPagar) { this.ivaPorPagar = ivaPorPagar; }
    
    public Map<String, Double> getBalanceCuentas() { return balanceCuentas; }
    public void setBalanceCuentas(Map<String, Double> balanceCuentas) { 
        this.balanceCuentas = balanceCuentas; 
    }
}