package co.edu.uptc.tiendaminorista.modelo;

public class Telefono {
	
    private String marca;
	private String numero;
	private String modelo;
	private String precio;

	public String getNumero() {
		return numero;
	}

    public String getMarca() { 
    	return marca; 
    }
    
    public String getModelo() { 
    	return modelo; 
    }
    public String precio() { 
        return precio; 
    }	
    
	public void setNumero(String marca,String numero, String modelo, String precio) {
		this.numero = numero;
	}
}
