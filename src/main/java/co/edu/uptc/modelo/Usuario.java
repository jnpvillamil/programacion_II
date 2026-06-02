package co.edu.uptc.modelo;

public abstract class Usuario extends Persona {
    private String usuario;
    private String clave;
    private boolean activo;

    public Usuario(String nombre, String apellido, String identificacion, String direccion, String telefono,
                   String usuario, String clave) {
        super(nombre, apellido, identificacion, direccion, telefono);
        this.usuario = usuario;
        this.clave = clave;
        this.activo = true;
    }

    public abstract String obtenerRol();

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
