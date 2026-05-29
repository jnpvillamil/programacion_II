package co.edu.uptc.negocio.dto;

public class clienteDto {

	private int codigoCliente;
	private String nombre;
	private long telefono;
	private String direccion;
	private String documento;

	// Constructor vacío para cuando creamos un objeto nuevo antes de enviarlo a la
	// BD
	public clienteDto() {
	}

	// Constructor completo (útil cuando traemos datos de la BD)
	public clienteDto(int codigoCliente, String documento, String nombre, long telefono, String direccion) {
		this.codigoCliente = codigoCliente;
		this.documento = documento;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
	}

	// --- Getters y Setters ---

	// IMPORTANTÍSIMO: Necesitas el setCodigoCliente para cuando el DAO
	// llena este objeto con los datos que vienen de MySQL.
	public int getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Override
	public String toString() {
		return "clienteDto [codigo=" + codigoCliente + ", nombre=" + nombre + "]";
	}
}