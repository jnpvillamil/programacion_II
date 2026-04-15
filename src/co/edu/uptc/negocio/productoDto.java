package co.edu.uptc.negocio;

public class productoDto {

    private String codigo;
    private String nombre;
    private String categoria;
    private double precioCompra;
    private double precioVenta;
    private int stockActual;
    private int stockMinimo;

    public String getCodigo() { 
    	return codigo; 
    	}
    
    public void setCodigo(String codigo) {
    	this.codigo = codigo;
    	}

    public String getNombre() {
    	return nombre;
    	}
    
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    	}

    public String getCategoria() {
    	return categoria;
    	}
    
    public void setCategoria(String categoria) {
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

    @Override
    public String toString() {
        return "productoDto [codigo=" + codigo + ", nombre=" + nombre + ", categoria=" + categoria + ", precioCompra=" + precioCompra + ", precioVenta=" + precioVenta + ", stockActual=" + stockActual +", stockMinimo=" + stockMinimo + "]";
    }
}