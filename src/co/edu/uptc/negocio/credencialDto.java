package co.edu.uptc.negocio;

import java.util.Arrays;

public class credencialDto {
	
	
	private String Usuario;
	private byte[] contraseña;
	
	
	
	public String getUsuario() {
		return Usuario;
	}
	public void setUsuario(String usuario) {
		Usuario = usuario;
	}
	public byte[] getContraseña() {
		return contraseña;
	}
	public void setContraseña(byte[] contraseña) {
		this.contraseña = contraseña;
	}
	@Override
	public String toString() {
		return "credencialDto [Usuario=" + Usuario + ", contraseña=" + Arrays.toString(contraseña) + "]";
	}

}
