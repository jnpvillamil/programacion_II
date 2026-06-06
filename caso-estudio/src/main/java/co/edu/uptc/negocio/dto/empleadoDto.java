package co.edu.uptc.negocio.dto;

public class empleadoDto {

    private String nombre;

    public empleadoDto() {}

    public empleadoDto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "empleadoDto [nombre=" + nombre + "]";
    }
}