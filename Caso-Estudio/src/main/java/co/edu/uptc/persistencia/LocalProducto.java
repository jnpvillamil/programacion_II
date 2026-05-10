package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.GestionProducto;
import co.edu.uptc.modelo.Producto;
import java.util.*;

public class LocalProducto implements GestionProducto {
    
    private Map<String, Producto> productos;
    
    public LocalProducto() {
        productos = new HashMap<>();
        
        
        Producto p1 = new Producto("P001", "Arroz 1kg", "Víveres", 2500, 3000, 100, 20);
        Producto p2 = new Producto("P002", "Aceite 1L", "Víveres", 8000, 10000, 50, 10);
        Producto p3 = new Producto("P003", "Jabón Rey", "Aseo", 2000, 3500, 80, 15);
        Producto p4 = new Producto("P004", "Cuaderno 100h", "Papelería", 5000, 8000, 30, 5);
        Producto p5 = new Producto("P005", "Arroz 2kg", "Víveres", 4800, 5800, 45, 10);
        
        productos.put(p1.getCodigo(), p1);
        productos.put(p2.getCodigo(), p2);
        productos.put(p3.getCodigo(), p3);
        productos.put(p4.getCodigo(), p4);
        productos.put(p5.getCodigo(), p5);
    }
    
    @Override
    public void crear(Producto p) {
        productos.put(p.getCodigo(), p);
    }
    
    @Override
    public void actualizar(Producto p) {
        if(productos.containsKey(p.getCodigo())) {
            productos.put(p.getCodigo(), p);
        }
    }
    
    @Override
    public void eliminar(String codigo) {
        Producto p = productos.get(codigo);
        if(p != null) {
            p.setActivo(false);  // Baja lógica
        }
    }
    
    @Override
    public Producto buscar(String codigo) {
        Producto p = productos.get(codigo);
        return (p != null && p.isActivo()) ? p : null;
    }
    
    @Override
    public List<Producto> listar() {
        return new ArrayList<>(productos.values());
    }
    
    @Override
    public List<Producto> listarActivos() {
        List<Producto> activos = new ArrayList<>();
        for(Producto p : productos.values()) {
            if(p.isActivo()) {
                activos.add(p);
            }
        }
        return activos;
    }
    
    @Override
    public List<Producto> listarPorStockMinimo() {
        List<Producto> bajoStock = new ArrayList<>();
        for(Producto p : productos.values()) {
            if(p.isActivo() && p.getStockActual() < p.getStockMinimo()) {
                bajoStock.add(p);
            }
        }
        return bajoStock;
    }
    
    @Override
    public boolean existe(String codigo) {
        Producto p = productos.get(codigo);
        return p != null && p.isActivo();
    }
}