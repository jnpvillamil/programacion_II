package co.uptc.edu.co.modelo.enums;

public enum TipoPersona {
	
	PERSONA_NATURAL("Persona Natural"),
	PERSONA_JURIDICA("Persona Juridica");
	
	private final String texto;
	
	TipoPersona(String texto){
		
		this.texto = texto;
	}
	
	@Override
	public String toString() {
		return texto;
	}

}
