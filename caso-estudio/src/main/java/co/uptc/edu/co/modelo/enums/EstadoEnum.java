package co.uptc.edu.co.modelo.enums;

public enum EstadoEnum {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String texto;

    EstadoEnum(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}