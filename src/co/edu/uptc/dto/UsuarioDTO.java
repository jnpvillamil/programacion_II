package co.edu.uptc.dto;

public class UsuarioDTO {
    private String nombre;
    private String usuario;
    private String rol;

    public UsuarioDTO(String nombre, String usuario, String rol) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}