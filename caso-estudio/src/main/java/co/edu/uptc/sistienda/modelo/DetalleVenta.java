package co.edu.uptc.sistienda.modelo;

import co.edu.uptc.sistienda.modelo.enums.TipoImpuestoEnum;

public class DetalleVenta {
	
	private Producto producto; 
	private int cantidad; 
	private double precioUnitario;
	private double descuentoDto; 
	
	public DetalleVenta(Producto producto, int cantidad, double precioUnitario) {
		this.producto       =producto; 
		this.cantidad       =cantidad; 
		this.precioUnitario =precioUnitario; 
		this.descuentoDto   =0.0; 
	}
	
	//Cálculos
	public double getSubtotalBruto() {
		return cantidad * precioUnitario; 
	}
	public double getValorDescuento() {
		return getSubtotalBruto() * (descuentoDto / 100.0);
	}
	public double getSubtotal() {
		return getSubtotalBruto() - getValorDescuento();
	}
	
	//Etiqueta del impuesto que se toma directamente del producto
	public String getDescripcionImpuesto() {
		if(producto !=null && producto.getTipoImpuesto() !=null) {
			return producto.getTipoImpuesto().getDescripcion();
		}
			return TipoImpuestoEnum.IVA.getDescripcion();
	}
	
	//Aplica solo para las tarifas que tienen 19% y cuando el impuesto del producto es IVA
	public double getValorImpuesto() {
		if(producto !=null && producto.getTipoImpuesto() == TipoImpuestoEnum.IVA) {
			return getSubtotal() * 0.19;
		}
		return 0.0;
	}
	
	public double getValorTotal() {
		return getSubtotal() + getValorImpuesto(); 
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
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public double getDescuentoDto() {
		return descuentoDto;
	}

	public void setDescuentoDto(double descuentoDto) {
		this.descuentoDto = descuentoDto;
	}
	
	
}
