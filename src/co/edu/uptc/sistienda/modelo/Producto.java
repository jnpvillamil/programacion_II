package co.edu.uptc.sistienda.modelo;

import co.edu.uptc.sistienda.modelo.enums.CategoriaProductoEnum;

public class Producto {

	private String codigoInterno;
	private String nombreProducto;
	private CategoriaProductoEnum categoria;
	private double precioCompra;
	private double precioVenta;
	private int stockActual;
	private int stockMinimo;
	private boolean activo;

	public Producto() {
		this.activo = true;
	}

	public Producto(String codigoInterno, String nombreProducto, CategoriaProductoEnum categoria,
			double precioCompra, double precioVenta, int stockActual, int stockMinimo) {
		this.codigoInterno = codigoInterno;
		this.nombreProducto = nombreProducto;
		this.categoria = categoria;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.stockActual = stockActual;
		this.stockMinimo = stockMinimo;
		this.activo = true;
	}

	public String getCodigoInterno() {
		return codigoInterno;
	}

	public void setCodigoInterno(String codigoInterno) {
		this.codigoInterno = codigoInterno;
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

	public double getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(double precioCompra) {
		this.precioCompra = precioCompra;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public int getStockActual() {
		return stockActual;
	}

	public void setStockActual(int stockActual) {
		this.stockActual = stockActual;
	}

	public int getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public boolean tieneStockBajoMinimo() {
		return stockActual < stockMinimo;
	}
}
