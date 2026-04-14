package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.RolUsuarioEnum;
import co.edu.uptc.gui.interfaces.Autenticable;

public class Usuario implements Autenticable {

    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private RolUsuarioEnum rol;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String contrasena, RolUsuarioEnum rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public RolUsuarioEnum getRol() {
        return rol;
    }

    public void setRol(RolUsuarioEnum rol) {
        this.rol = rol;
    }

    @Override
    public boolean iniciarSesion(String usuario, String contrasena) {
        return this.nombreUsuario.equals(usuario) && this.contrasena.equals(contrasena);
    }

    @Override
    public void cerrarSesion() {
        System.out.println("Sesión cerrada");
    }
}