package co.edu.uptc.sistienda.modelo.enums;

public enum TipoClienteEnum {
	MINORISTA("Minorista"),
	MAYORISTA("Mayorista");

	private String descripcion;

	TipoClienteEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public String toString() {
		return descripcion;
	}
}
