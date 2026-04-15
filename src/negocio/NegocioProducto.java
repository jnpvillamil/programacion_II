package negocio;

import modelo.Producto;
import java.util.ArrayList;

public class NegocioProducto {

    private ArrayList<Producto> lista;

    public NegocioProducto() {
        lista = new ArrayList<>();
    }

  
    public void agregarProducto(Producto p) {
        lista.add(p);
    }
    
    public boolean existeProducto(String codigo) {
        return buscarProducto(codigo) != null;
    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : lista) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    
    public void inactivarProducto(String codigo) {

        for (Producto p : lista) {
            if (p.getCodigo().equals(codigo)) {
                p.setActivo(false);
                break;
            }
        }
    }
    

    
    public ArrayList<Producto> getLista() {
        return lista;
    }
}