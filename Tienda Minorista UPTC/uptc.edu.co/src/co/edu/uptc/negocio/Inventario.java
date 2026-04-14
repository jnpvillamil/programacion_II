package co.edu.uptc.negocio;
import java.util.ArrayList;

public class Inventario {
    private ArrayList<Producto> productos;

    public Inventario() {
        this.productos = new ArrayList<>();
    }
    public void registrarMovimiento(String codigo, int cantidad, String tipo, String motivo) {}
    public void registrarProducto(Producto p) { productos.add(p); }
    public void eliminarProducto(String cod) { productos.removeIf(p -> p.getCodigo().equals(cod)); }
    public Producto buscarProducto(String cod) {
        for (Producto p : productos) if (p.getCodigo().equals(cod)) return p;
        return null;
    }
    public ArrayList<Producto> getProductos() { return productos; }
}