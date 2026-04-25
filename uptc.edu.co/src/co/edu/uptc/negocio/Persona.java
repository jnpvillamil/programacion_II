package co.edu.uptc.negocio;

public class Persona {
    protected String identificacion;
    protected String nombre;
    protected String direccion;
    protected String telefono;

    public Persona(String identificacion, String nombre, String direccion, String telefono) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
}