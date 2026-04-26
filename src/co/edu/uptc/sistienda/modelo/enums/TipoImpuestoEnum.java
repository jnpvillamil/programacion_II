package co.edu.uptc.sistienda.modelo.enums;

public enum TipoImpuestoEnum {
	IVA("Iva"), 
	EXLUIDO("Excluido"), 
	EXENO("Exento");

	private String descripcion;

	TipoImpuestoEnum(String descripcion) {
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