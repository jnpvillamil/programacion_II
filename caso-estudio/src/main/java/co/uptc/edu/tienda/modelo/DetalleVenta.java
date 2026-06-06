package co.uptc.edu.tienda.modelo;

public class DetalleVenta {
    private Producto producto;
    private int cantidad;
    private double precioUnitario; // Guardamos el precio del momento (por si cambia en el inventario mañana)
    private double impuestos;

    public DetalleVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecioVenta(); // Tomamos el precio actual del producto
        this.impuestos = (double) cantidad * precioUnitario * producto.getPorcentajeIva();
    }

    // El subtotal requerido se calcula dinámicamente
    public double getSubtotal() {
        return (this.cantidad * this.precioUnitario) + this.impuestos;
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

	public double getImpuestos() {
		return impuestos;
	}

    
}