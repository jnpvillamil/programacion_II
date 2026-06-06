package co.edu.uptc.tiendaminorista.modelo;

public class Telefono {
	
	private String marca;
	private String numero;
	private String modelo;
	private double precio;

	public String getNumero() {
		return numero;
	}

	public String getMarca() { 
		return marca; 
	}
    
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getModelo() { 
		return modelo; 
	}
	
	public void setModelo(String modelo) { 
		this.modelo = modelo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setMarca(String string) {
		// TODO Auto-generated method stub
		
	}


}
