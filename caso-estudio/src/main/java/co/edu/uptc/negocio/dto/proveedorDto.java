package co.edu.uptc.negocio.dto;

public class proveedorDto {

	// Eliminamos el contador estático porque MySQL se encargará del AUTO_INCREMENT
	private int codigoProveedor;
	private String razonSocial;
	private String nit;
	private String direccion;
	private long telefono;
	private String correo;

	// Constructor vacío (ahora sin el contador++)
	public proveedorDto() {
	}

	// Constructor útil para cuando traemos datos de la BD
	public proveedorDto(int codigoProveedor, String razonSocial, String nit, String direccion, long telefono,
			String correo) {
		this.codigoProveedor = codigoProveedor;
		this.razonSocial = razonSocial;
		this.nit = nit;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correo = correo;
	}

	// --- GETTERS Y SETTERS ---

	public int getCodigoProveedor() {
		return codigoProveedor;
	}

	// ¡NUEVO! Vital para que el DAO pueda guardar el ID que viene de MySQL
	public void setCodigoProveedor(int codigoProveedor) {
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Override
	public String toString() {
		return "proveedorDto [codigoProveedor=" + codigoProveedor + ", razonSocial=" + razonSocial + ", nit=" + nit
				+ ", direccion=" + direccion + ", telefono=" + telefono + ", correo=" + correo + "]";
	}
}