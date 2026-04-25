package co.uptc.edu.co.modelo.enums;

public enum TipoMovimientoInventarioEnum {
	ENTRADA("Entrada"), SALIDA("Salida");

	private final String texto;

	TipoMovimientoInventarioEnum(String texto) {
		this.texto = texto;
	}

	@Override
	public String toString() {
		return texto;
	}
}