package co.edu.uptc.modelo;

/**
 * Clase abstracta que define los atributos de seguridad para los usuarios del sistema
 */
public abstract class Usuario extends Persona {
    private String usuario;
    private String clave;

    public Usuario(String nombre, String apellido, String identificacion, String direccion, String telefono, String usuario, String clave) {
        super(nombre, apellido, identificacion, direccion, telefono);
        this.usuario = usuario;
        this.clave = clave;
    }

    /**
     * Método polimórfico para obtener el rol del usuario.
     */
    public abstract String obtenerRol();

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}