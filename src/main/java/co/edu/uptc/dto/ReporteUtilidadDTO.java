package co.edu.uptc.dto;

public class ReporteUtilidadDTO {
    private String nombreProducto;
    private double utilidadBruta;

    public ReporteUtilidadDTO(String nombreProducto, double utilidadBruta) {
        this.nombreProducto = nombreProducto;
        this.utilidadBruta = utilidadBruta;
    }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public double getUtilidadBruta() { return utilidadBruta; }
    public void setUtilidadBruta(double utilidadBruta) { this.utilidadBruta = utilidadBruta; }
}