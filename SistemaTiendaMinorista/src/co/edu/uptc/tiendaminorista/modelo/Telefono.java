package co.edu.uptc.tiendaminorista.modelo;

public class Telefono {
	
	private String marca;
	private String numero;
	private String modelo;
	private double precio;

	public String getMarca() { 
		return marca; 
	}
	
	public String getNumero() {
		return numero;
	}
	
	public String getModelo() { 
		return modelo; 
	}
	
	public double getPrecio() {
		return precio;
	}
	
	// Setters
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public void setModelo(String modelo) { 
		this.modelo = modelo;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
}