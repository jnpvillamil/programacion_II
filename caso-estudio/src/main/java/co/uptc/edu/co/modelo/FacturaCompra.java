package co.uptc.edu.co.modelo;

import java.time.LocalDateTime;

import co.uptc.edu.co.modelo.enums.EstadoFacturaCompra;

public class FacturaCompra {
	private String numeroFacturaProveedor;
	private LocalDateTime fechaCompra;
	private String codigoProveedor;
	private String producto;
	private int cantidad;
	private Double costoUnitario;
	private Double impuestos;
	private Double totalCompra;
	private EstadoFacturaCompra estado;

	public FacturaCompra() {
	}

	
	public FacturaCompra(String numeroFacturaProveedor, LocalDateTime fechaCompra, String codigoProveedor,
			String producto, int cantidad, Double costoUnitario, Double impuestos, Double totalCompra,
			EstadoFacturaCompra estado) {
		this.numeroFacturaProveedor = numeroFacturaProveedor;
		this.fechaCompra = fechaCompra;
		this.codigoProveedor = codigoProveedor;
		this.producto = producto;
		this.cantidad = cantidad;
		this.costoUnitario = costoUnitario;
		this.impuestos = impuestos;
		this.totalCompra = totalCompra;
		this.estado = estado;
	}


	public String getNumeroFacturaProveedor() {
		return numeroFacturaProveedor;
	}


	public void setNumeroFacturaProveedor(String numeroFacturaProveedor) {
		this.numeroFacturaProveedor = numeroFacturaProveedor;
	}


	public LocalDateTime getFechaCompra() {
		return fechaCompra;
	}


	public void setFechaCompra(LocalDateTime fechaCompra) {
		this.fechaCompra = fechaCompra;
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


	public Double getCostoUnitario() {
		return costoUnitario;
	}


	public void setCostoUnitario(Double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}


	public Double getImpuestos() {
		return impuestos;
	}


	public void setImpuestos(Double impuestos) {
		this.impuestos = impuestos;
	}


	public Double getTotalCompra() {
		return totalCompra;
	}


	public void setTotalCompra(Double totalCompra) {
		this.totalCompra = totalCompra;
	}


	public EstadoFacturaCompra getEstado() {
		return estado;
	}


	public void setEstado(EstadoFacturaCompra estado) {
		this.estado = estado;
	}
	
	
}
