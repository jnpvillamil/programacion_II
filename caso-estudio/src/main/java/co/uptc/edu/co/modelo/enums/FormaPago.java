package co.uptc.edu.co.modelo.enums;

public enum FormaPago {
    EFECTIVO("Efectivo"),
    TRANSFERENCIA("Traferencia"),
    TARJETA("Tarjeta"),
    CREDITO("Credito");
	
	 private final String texto;
	 FormaPago(String texto) {
	        this.texto = texto;
	    }

	    @Override
	    public String toString() {
	        return texto;
	    }
}