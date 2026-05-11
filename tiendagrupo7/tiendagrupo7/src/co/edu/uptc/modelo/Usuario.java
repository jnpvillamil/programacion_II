package co.edu.uptc.modelo;

public abstract class Usuario extends Persona {
    private String usuario;
    private String clave;

    public Usuario(String nombre, String identificacion, String direccion, String telefono, String usuario, String clave) {
        super(nombre, identificacion, direccion, telefono);
        this.usuario = usuario;
        this.clave = clave;
    }

    public Usuario() {
        super();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public abstract String obtenerRol();
}