package co.edu.uptc.enums;

public enum TipoMovimientoContable {
    INGRESO("Ingreso"),
    EGRESO("Egreso");
    
    private String descripcion;
    
    TipoMovimientoContable(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() { return descripcion; }
}