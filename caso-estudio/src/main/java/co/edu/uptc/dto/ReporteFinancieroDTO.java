package co.edu.uptc.dto;

public class ReporteFinancieroDTO {
    private String periodo;
    private double totalIngresos;
    private double totalEgresos;
    private double utilidadBruta;

    public ReporteFinancieroDTO(String periodo, double totalIngresos, double totalEgresos, double utilidadBruta) {
        this.periodo = periodo;
        this.totalIngresos = totalIngresos;
        this.totalEgresos = totalEgresos;
        this.utilidadBruta = utilidadBruta;
    }

    public ReporteFinancieroDTO() {
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public double getTotalEgresos() {
        return totalEgresos;
    }

    public void setTotalEgresos(double totalEgresos) {
        this.totalEgresos = totalEgresos;
    }

    public double getUtilidadBruta() {
        return utilidadBruta;
    }

    public void setUtilidadBruta(double utilidadBruta) {
        this.utilidadBruta = utilidadBruta;
    }
}