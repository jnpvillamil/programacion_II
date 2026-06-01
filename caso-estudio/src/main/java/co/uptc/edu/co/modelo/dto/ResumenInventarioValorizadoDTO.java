package co.uptc.edu.co.modelo.dto;

import co.uptc.edu.co.modelo.enums.CategoriaProductoEnum;

public class ResumenInventarioValorizadoDTO {

	private String codigoProducto;
	private String nombreProducto;
	private CategoriaProductoEnum categoria;
	private int stockActual;
	private double precioCompra;
	private double valorInventario;

	public ResumenInventarioValorizadoDTO(String codigoProducto, String nombreProducto,
			CategoriaProductoEnum categoria, int stockActual, double precioCompra, double valorInventario) {
		this.codigoProducto = codigoProducto;
		this.nombreProducto = nombreProducto;
		this.categoria = categoria;
		this.stockActual = stockActual;
		this.precioCompra = precioCompra;
		this.valorInventario = valorInventario;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public CategoriaProductoEnum getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProductoEnum categoria) {
		this.categoria = categoria;
	}

	public int getStockActual() {
		return stockActual;
	}

	public void setStockActual(int stockActual) {
		this.stockActual = stockActual;
	}

	public double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public double getValorInventario() {
		return valorInventario;
	}

	public void setValorInventario(double valorInventario) {
		this.valorInventario = valorInventario;
	}
}
