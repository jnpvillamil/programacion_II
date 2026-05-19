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
    public String getUsuario() { return usuario; }
    public String getRol() { return rol; }
}