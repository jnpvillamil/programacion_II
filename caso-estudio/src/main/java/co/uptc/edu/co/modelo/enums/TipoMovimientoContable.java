package co.uptc.edu.co.modelo.enums;

public enum TipoMovimientoContable {
    INGRESO("Ingreso"),
    EGRESO("Egreso");
    
    
    private final String texto;

    TipoMovimientoContable(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}
