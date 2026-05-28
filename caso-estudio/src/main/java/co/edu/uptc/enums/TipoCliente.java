package co.edu.uptc.enums;

public enum TipoCliente {

	MINORISTA, MAYORISTA;

	public static TipoCliente desdeTexto(String valor) {
		if (valor == null || valor.isBlank()) {
			return MINORISTA;
		}
		try {
			return valueOf(valor.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return MINORISTA;
		}
	}
}
