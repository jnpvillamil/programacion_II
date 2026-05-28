package co.edu.uptc.enums;

public enum CategoriaProducto {

	VIVERES, ASEO, PAPELERIA;

	public static CategoriaProducto desdeTexto(String valor) {
		if (valor == null || valor.isBlank()) {
			return VIVERES;
		}
		try {
			return valueOf(valor.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return VIVERES;
		}
	}
}
