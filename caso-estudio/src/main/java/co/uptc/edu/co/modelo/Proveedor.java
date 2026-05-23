package co.uptc.edu.co.modelo;

import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class Proveedor extends Persona {

	private String codigoProveedor;
	private String razonSocial;
	private String nit;
	private String correoElectronico;

	public Proveedor() {
		super();
	}

	public Proveedor(String codigoProveedor, String razonSocial, String nit, String direccion, String telefono,
			String correoElectronico, EstadoEnum estado) {

		super(direccion, telefono, estado);
		this.codigoProveedor = codigoProveedor;
		this.razonSocial = razonSocial;
		this.nit = nit;
		this.correoElectronico = correoElectronico;

	}

	public String getCodigoProveedor() {
		return codigoProveedor;
	}

	public void setCodigoProveedor(String codigoProveedor) {
		this.codigoProveedor = codigoProveedor;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	@Override
	public String toString() {
		return codigoProveedor + " - " + razonSocial;
	}
}
