package edu.uptc.edu.Tiendaminorista.negociodto;

import java.util.Arrays;

public class CredencialDto {

	private String usuario;
	private byte[] password;
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "CredencialDto [usuario=" + usuario + ", password=" + Arrays.toString(password) + "]";
	}
		
}
