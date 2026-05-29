package co.uptc.edu.co.modelo.enums;

public enum FormaPago {
    EFECTIVO("Efectivo"),
    TRANSFERENCIA("Transferencia"),
    TARJETA("Tarjeta"),
    CREDITO("Crédito");
	
	 private final String texto;
	 FormaPago(String texto) {
	        this.texto = texto;
	    }

	    @Override
	    public String toString() {
	        return texto;
	    }
}
