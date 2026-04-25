package co.uptc.edu.co.modelo.enums;

public enum TipoClienteEnum {
    MAYORISTA("Mayorista"),
    MINORISTA("Minorista");

    private final String texto;

    TipoClienteEnum(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}