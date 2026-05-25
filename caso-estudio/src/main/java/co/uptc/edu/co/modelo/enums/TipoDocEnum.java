package co.uptc.edu.co.modelo.enums;

public enum TipoDocEnum {
    CC("CC"),
    NIT("NIT"),
    CE("CE"),
    PA("PA");
   
    private final String texto;

    TipoDocEnum(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}