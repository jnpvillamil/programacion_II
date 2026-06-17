package co.edu.uptc.sistienda.modelo.enums;

public enum TipoPersonaEnum {
	NATURAL("Natural"), 
	JURIDICA("Juridica");
	
	private String descripcion;
	
	TipoPersonaEnum(String descripcion){
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