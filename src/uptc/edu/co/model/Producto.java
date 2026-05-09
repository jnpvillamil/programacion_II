package uptc.edu.co.model;


public class Producto {

    
    private String codigo;   
    private String nombre;   
    private double precio;   
    private int    stock;    

    
    
    public Producto(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock  = stock;
    }

    
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int    getStock()  { return stock; }

    
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setStock(int stock)      { this.stock  = stock; }

    
    public String toLineaArchivo() {
        return codigo + "," + nombre + "," + precio + "," + stock;
    }

    
    public static Producto desdeLinea(String linea) {
        try {
            String[] partes = linea.split(",");
            if (partes.length < 4) return null;
            String codigo = partes[0].trim();
            String nombre = partes[1].trim();
            double precio = Double.parseDouble(partes[2].trim());
            int    stock  = Integer.parseInt(partes[3].trim());
            return new Producto(codigo, nombre, precio, stock);
        } catch (NumberFormatException e) {
            
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("Producto{codigo='%s', nombre='%s', precio=%.2f, stock=%d}",
                             codigo, nombre, precio, stock);
    }
}
