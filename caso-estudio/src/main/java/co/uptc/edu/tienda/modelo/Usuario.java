package co.uptc.edu.tienda.modelo;

import co.uptc.edu.tienda.enums.RolEnum;

public class Usuario {
 
	private int idUsuario;
	private String correo;
	private String password;
	private RolEnum rol;
	
	public Usuario() {
		
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public RolEnum getRol() {
		return rol;
	}
	public void setRol(RolEnum rol) {
		this.rol = rol;
	}


}
