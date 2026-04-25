package co.uptc.edu.co.modelo.enums;

public enum CategoriaProductoEnum {
    ASEO("Aseo"),
    ALIMENTOS("Alimentos"),
    BEBIDAS("Bebidas"),
    PAPELERIA("Papelería");

    private final String texto;

    CategoriaProductoEnum(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}