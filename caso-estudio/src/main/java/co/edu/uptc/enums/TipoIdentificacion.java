package co.edu.uptc.enums;

public enum TipoIdentificacion {

	CC, NIT, CE, PA;

	public static TipoIdentificacion desdeTexto(String valor) {
		if (valor == null || valor.isBlank()) {
			return CC;
		}
		try {
			return valueOf(valor.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return CC;
		}
	}
}
