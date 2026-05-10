package co.edu.uptc.enums;

public enum TipoDocumentoEnum {
    CC("Cédula de Ciudadanía"),
    NIT("Número de Identificación Tributaria"),
    CE("Cédula de Extranjería"),
    PA("Pasaporte");
    
    private String descripcion;
    
    TipoDocumentoEnum(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public static TipoDocumentoEnum fromString(String texto) {
        for(TipoDocumentoEnum tipo : TipoDocumentoEnum.values()) {
            if(tipo.name().equals(texto) || tipo.getDescripcion().equals(texto)) {
                return tipo;
            }
        }
        return CC;
    }
}