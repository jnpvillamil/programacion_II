package co.edu.uptc.negocio;
public abstract class Persona {
    protected String codigo;
    protected String nombreRazonSocial;
    protected String direccion;
    protected String telefono;
    protected boolean activo;

    public Persona(String codigo, String nombre) {
        this.codigo = codigo; this.nombreRazonSocial = nombre; this.activo = true;
    }
    public void inactivar() { this.activo = false; }
    public String getNombreRazonSocial() { return nombreRazonSocial; }
}