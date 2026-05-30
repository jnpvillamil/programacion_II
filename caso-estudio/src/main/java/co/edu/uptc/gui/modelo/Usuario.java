package co.edu.uptc.gui.modelo;

import co.edu.uptc.enums.RolUsuarioEnum;
import co.edu.uptc.enums.TipoDocumentoEnum;


public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private RolUsuarioEnum rol;

    public Usuario() {}

    public Usuario(int idUsuario, String nombreUsuario, String contrasena, RolUsuarioEnum rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Usuario(String codigo, String nombre, String apellido, TipoDocumentoEnum tipoDocumento,
			String numeroDocumento, String telefono, String direccion) {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public RolUsuarioEnum getRol() { return rol; }
    public void setRol(RolUsuarioEnum rol) { this.rol = rol; }

	public void registrar() {
		// TODO Auto-generated method stub
		
	}

	public void modificar() {
		// TODO Auto-generated method stub
		
	}

	public void inactivar() {
		// TODO Auto-generated method stub
		
	}
}