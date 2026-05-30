package co.edu.uptc.gui.modelo;

public class Producto {
    private String codigo;
    private String nombre;
    private double precioCompra;
    private double precioVenta;  
    private int cantidadInventario;

    public Producto() {}

    public Producto(String codigo, String nombre, double precioCompra, double precioVenta, int cantidadInventario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.cantidadInventario = cantidadInventario;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getCantidadInventario() { return cantidadInventario; }
    public void setCantidadInventario(int cantidadInventario) { this.cantidadInventario = cantidadInventario; }

	public double getPrecio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void registrar() {
		// TODO Auto-generated method stub
		
	}

	public void modificar() {
		// TODO Auto-generated method stub
		
	}
}