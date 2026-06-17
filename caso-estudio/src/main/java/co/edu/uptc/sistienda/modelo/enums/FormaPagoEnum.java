package co.edu.uptc.sistienda.modelo.enums;

public enum FormaPagoEnum {
	EFECTIVO("Efectivo"),
	TRANSFERENCIA("Transferencia"),
	TARJETA("Tarjeta"),
	CREDITO("Crédito");
	
	private String descripcion;
	
	FormaPagoEnum(String descripcion){
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
