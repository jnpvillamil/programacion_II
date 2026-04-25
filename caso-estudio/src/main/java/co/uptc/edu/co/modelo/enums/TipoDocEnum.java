package co.uptc.edu.co.modelo.enums;

public enum TipoDocEnum {
    CC("CC"),
    TI("TI"),
    CE("CE"),
    NIT("NIT"),
    PASAPORTE("Pasaporte");

    private final String texto;

    TipoDocEnum(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}