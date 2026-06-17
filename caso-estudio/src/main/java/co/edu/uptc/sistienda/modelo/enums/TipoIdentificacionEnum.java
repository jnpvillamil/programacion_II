package co.edu.uptc.sistienda.modelo.enums;

public enum TipoIdentificacionEnum {
	CC("Cédula de Ciudadanía"),
	NIT("NIT"),
	CE("Cédula de Extranjería"),
	PA("Pasaporte");

	private String descripcion;

	TipoIdentificacionEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public String toString() {
		return name() + " - " + descripcion;
	}
}
