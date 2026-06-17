package co.edu.uptc.sistienda.compras.modelo;

import co.edu.uptc.sistienda.modelo.Producto;

public class DetalleCompra {

	private Producto producto;

	private int cantidad;

	private double precioCompra;

	private double subtotal;

	public DetalleCompra() {
	}

	public DetalleCompra(Producto producto, int cantidad, double precioCompra) {
		this.producto = producto;
		this.cantidad = cantidad;
		this.precioCompra = precioCompra;
		calcularSubtotal();
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
		calcularSubtotal();
	}

	public double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(double precioCompra) {
		this.precioCompra = precioCompra;
		calcularSubtotal();
	}

	public double getSubtotal() {
		return subtotal;
	}

	private void calcularSubtotal() {
		subtotal = cantidad * precioCompra;
	}
}