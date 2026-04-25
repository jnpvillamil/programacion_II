package co.uptc.edu.co.modelo;

import java.time.LocalDateTime;

public class Venta {

	private String numeroFactura;
	private LocalDateTime fechaHora;
	private String cliente;
	private String producto;
	private int cantidad;
	private double precioUnitario;
	private double subtotal;
	private String formaPago;
	private double impuestos;
	
	public Venta() {
	}

	
	public Venta(String numeroFactura, LocalDateTime fechaHora, String cliente, String producto, int cantidad,
			double precioUnitario, double subtotal, String formaPago, double impuestos) {
		this.numeroFactura = numeroFactura;
		this.fechaHora = fechaHora;
		this.cliente = cliente;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.subtotal = subtotal;
		this.formaPago = formaPago;
		this.impuestos = impuestos;
	}


	public String getNumeroFactura() {
		return numeroFactura;
	}


	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}


	public LocalDateTime getFechaHora() {
		return fechaHora;
	}


	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}


	public String getCliente() {
		return cliente;
	}


	public void setCliente(String cliente) {
		this.cliente = cliente;
	}


	public String getProducto() {
		return producto;
	}


	public void setProducto(String producto) {
		this.producto = producto;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public double getPrecioUnitario() {
		return precioUnitario;
	}


	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}


	public double getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}


	public String getFormaPago() {
		return formaPago;
	}


	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}


	public double getImpuestos() {
		return impuestos;
	}


	public void setImpuestos(double impuestos) {
		this.impuestos = impuestos;
	}


	@Override
	public String toString() {
		return "Venta [numeroFactura=" + numeroFactura + ", fechaHora=" + fechaHora + ", cliente=" + cliente
				+ ", producto=" + producto + "]";
	}
	
}
