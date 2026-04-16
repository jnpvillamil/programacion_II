package co.edu.uptc.negocio;
import java.util.*;

public class Producto {
    private String codigoProducto, nombreProducto, categoria;
    private double precioCompra, precioVenta;
    private int stockActual, stockMinimo;
    private boolean activo;
    
    public Producto(String string, String string2, String string3, double pc, double pv, int k, int l) {
		// TODO Auto-generated constructor stub
	}

	
    public String getNombreProducto() { return nombreProducto; }
    
    public void actualizarStock(int cantidad) { this.stockActual += cantidad; }
    public boolean validarStockMinimo() { return this.stockActual <= this.stockMinimo; }

	public Object getCodigoProducto() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getCategoria() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getPrecioVenta() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getStockActual() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
	    return this.nombreProducto;
	}
}