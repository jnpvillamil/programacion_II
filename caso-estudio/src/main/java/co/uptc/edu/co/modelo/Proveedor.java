package co.uptc.edu.co.modelo;

public class Proveedor {
	private String nombreProveedor ;
	private String codigoProveedor;
	private String razonSocial ;
	private String NIT ;
	private String direccion ;
	private String telefono ;
	private String correoElectronico ;
	
	public Proveedor(String nombreProveedor, String codigoProveedor, String razonSocial, String nIT, String direccion,
			String telefono, String correoElectronico) {
		this.nombreProveedor = nombreProveedor;
		this.codigoProveedor = codigoProveedor;
		this.razonSocial = razonSocial;
		NIT = nIT;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correoElectronico = correoElectronico;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
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

	public String getNIT() {
		return NIT;
	}

	public void setNIT(String nIT) {
		NIT = nIT;
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

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	@Override
	public String toString() {
		return "Proveedor [nombreProveedor=" + nombreProveedor + ", codigoProveedor=" + codigoProveedor + "]";
	}


}
