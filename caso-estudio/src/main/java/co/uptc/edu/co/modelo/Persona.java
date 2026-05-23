package co.uptc.edu.co.modelo;

import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class Persona {

	protected String direccion;
	protected String telefono;
	protected EstadoEnum estado;

	public Persona() {
	}

	public Persona(String direccion, String telefono, EstadoEnum estado) {
		this.direccion = direccion;
		this.telefono = telefono;
		this.estado = estado;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public EstadoEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoEnum estado) {
		this.estado = estado;
	}

	public boolean estaActivo() {
		return estado == EstadoEnum.ACTIVO;
	}
}
