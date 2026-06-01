package co.uptc.edu.co.modelo.dto;

public class ResumenClienteDTO {

	private String codigoCliente;
	private String nombreCliente;
	private int cantidadCompras;
	private double totalComprado;

	public ResumenClienteDTO(String codigoCliente, String nombreCliente, int cantidadCompras, double totalComprado) {
		this.codigoCliente = codigoCliente;
		this.nombreCliente = nombreCliente;
		this.cantidadCompras = cantidadCompras;
		this.totalComprado = totalComprado;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public int getCantidadCompras() {
		return cantidadCompras;
	}

	public void setCantidadCompras(int cantidadCompras) {
		this.cantidadCompras = cantidadCompras;
	}

	public double getTotalComprado() {
		return totalComprado;
	}

	public void setTotalComprado(double totalComprado) {
		this.totalComprado = totalComprado;
	}
}
