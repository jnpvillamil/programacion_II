package co.uptc.edu.co.modelo;

import java.time.LocalDate;

public class Compra {
	private String numeroFacturaProveedor;
	private LocalDate fecha;
	private String codigoProveedor;
	private String producto;
	private int cantidad;
	private double costoUnitario;
	private double impuestos;
	private double totalCompra;

	public Compra() {
	}

	public Compra(String numeroFacturaProveedor, LocalDate fecha, String codigoProveedor,
			String producto, int cantidad, double costoUnitario, double impuestos, double totalCompra) {
		this.numeroFacturaProveedor = numeroFacturaProveedor;
		this.fecha = fecha;
		this.codigoProveedor = codigoProveedor;
		this.producto = producto;
		this.cantidad = cantidad;
		this.costoUnitario = costoUnitario;
		this.impuestos = impuestos;
		this.totalCompra = totalCompra;
	}

	public String getNumeroFacturaProveedor() {
		return numeroFacturaProveedor;
	}

	public void setNumeroFacturaProveedor(String numeroFacturaProveedor) {
		this.numeroFacturaProveedor = numeroFacturaProveedor;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getCodigoProveedor() {
		return codigoProveedor;
	}

	public void setCodigoProveedor(String codigoProveedor) {
		this.codigoProveedor = codigoProveedor;
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

	public double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public double getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(double impuestos) {
		this.impuestos = impuestos;
	}

	public double getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(double totalCompra) {
		this.totalCompra = totalCompra;
	}

	@Override
	public String toString() {
		return "Compra [numeroFacturaProveedor=" + numeroFacturaProveedor + ", fecha=" + fecha
				+ ", codigoProveedor=" + codigoProveedor + ", producto=" + producto + "]";
	

}

}
