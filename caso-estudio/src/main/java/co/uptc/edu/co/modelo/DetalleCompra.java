package co.uptc.edu.co.modelo;

public class DetalleCompra {
	private Producto producto;      
    private int cantidad;            
    private double costoUnitario;    
    private double subtotal;         
    private double impuestos;        
    private double totalCompra;
	
	public DetalleCompra() {
	}
	
	public DetalleCompra(Producto producto, int cantidad, double costoUnitario, double subtotal, double impuestos,
			double totalCompra) {
		this.producto = producto;
		this.cantidad = cantidad;
		this.costoUnitario = costoUnitario;
		this.subtotal = subtotal;
		this.impuestos = impuestos;
		this.totalCompra = totalCompra;
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

	public double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
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

}
