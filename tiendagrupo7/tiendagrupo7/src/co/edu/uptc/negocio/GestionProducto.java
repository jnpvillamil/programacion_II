package co.edu.uptc.negocio;

import co.edu.uptc.modelo.Producto;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.persistencia.PersistenciaProducto;
import java.util.List;

public class GestionProducto {
    private Repositorio<Producto> repositorio;

    public GestionProducto() {
        this.repositorio = new PersistenciaProducto();
    }

    public String registrarProducto(Producto producto) {
        if (repositorio.buscarPorId(producto.getCodigoProducto()) != null) {
            return "Error: El código del producto ya existe.";
        }
        
        if (producto.getStockActual() < 0) {
            return "Error: El stock inicial no puede ser negativo.";
        }

        repositorio.guardar(producto);
        return "Producto registrado exitosamente.";
    }

    public List<Producto> obtenerInventarioCompleto() {
        return repositorio.listar();
    }

    public void actualizarStock(String codigo, int cantidad) {
        Producto p = repositorio.buscarPorId(codigo);
        if (p != null) {
            p.setStockActual(p.getStockActual() + cantidad);
            repositorio.actualizar(p);
            
            if (p.getStockActual() <= p.getStockMinimo()) {
                System.out.println("ALERTA: Producto " + p.getNombreProducto() + " bajo stock mínimo.");
            }
        }
    }
    
    public void eliminarProducto(String codigo) {
        repositorio.eliminar(codigo);
    }
}