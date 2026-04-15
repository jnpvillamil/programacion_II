package co.edu.uptc.sistienda.modelo.enums;

public enum CategoriaProductoEnum {
	VIVERES("Víveres"),
	ASEO("Artículos de Aseo"),
	PAPELERIA("Papelería"),
	OTRO("Otro");

	private String descripcion;

	CategoriaProductoEnum(String descripcion) {
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
