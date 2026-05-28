package co.edu.uptc.enums;

public enum FormaPago {

	EFECTIVO, TRANSFERENCIA, TARJETA, CREDITO;

	public static FormaPago desdeTexto(String valor) {
		if (valor == null || valor.isBlank()) {
			return EFECTIVO;
		}
		try {
			return valueOf(valor.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			return EFECTIVO;
		}
	}
}
