package co.edu.uptc.negocio.dto;

public class empleadoDto {

	private int codigoEmpleado;
	private String nombre;

	public empleadoDto() {
	}

	public empleadoDto(int codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public int getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(int codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "empleadoDto [codigoEmpleado=" + codigoEmpleado + ", nombre=" + nombre + "]";
	}
}