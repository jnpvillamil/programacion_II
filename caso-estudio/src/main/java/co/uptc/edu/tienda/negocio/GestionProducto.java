package co.uptc.edu.tienda.negocio;

import java.util.List;
import co.uptc.edu.tienda.interfaces.IGestionProducto;
import co.uptc.edu.tienda.modelo.Producto;

public class GestionProducto {

    private final IGestionProducto gestionP;

    public GestionProducto(IGestionProducto gestionP) {
        super();
        this.gestionP = gestionP;
    }

    public void guardar(Producto nuevo) {
        if (nuevo.getNombreProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre obligatorio");
        }
        if (nuevo.getPrecioCompra() <= 0 || nuevo.getPrecioVenta() <= 0) {
            throw new IllegalArgumentException("Precios deben ser mayores a 0");
        }
        if (nuevo.getStockActual() < 0) {
            throw new IllegalArgumentException("Stock no puede ser negativo");
        }
        if (nuevo.getStockActual() < nuevo.getStockMinimo()) {
            throw new IllegalArgumentException("Stock no puede ser menor al mínimo");
        }
        if (nuevo.getStockActual() > nuevo.getStockMaximo()) {
            throw new IllegalArgumentException("Stock no puede ser mayor al máximo");
        }
        // BD asigna el ID automáticamente
        gestionP.guardar(nuevo);
    }

    public void actualizar(Producto producto) throws Exception {
        if (producto.getStockActual() < 0) {
            throw new Exception("El stock no puede ser negativo");
        }
        if (producto.getStockActual() < producto.getStockMinimo()) {
            throw new Exception("El stock no puede ser menor al stock mínimo ("
                    + producto.getStockMinimo() + ")");
        }
        if (producto.getStockActual() > producto.getStockMaximo()) {
            throw new Exception("El stock no puede ser mayor al stock máximo ("
                    + producto.getStockMaximo() + ")");
        }
        gestionP.actualizar(producto);
    }

    public Producto buscar(int codigoProducto) {
        return gestionP.buscar(codigoProducto);
    }

    public List<Producto> listar() {
        return gestionP.listar();
    }

    public void inactivar(int codigoProducto) throws Exception {
        Producto existente = gestionP.buscar(codigoProducto);
        if (existente == null) {
            throw new Exception("El producto no existe");
        }
        if (!existente.isActivo()) {
            throw new Exception("El producto ya está inactivo");
        }
        gestionP.cambiarEstado(codigoProducto); // inactiva
    }

    public void activar(int codigoProducto) throws Exception {
        Producto existente = gestionP.buscar(codigoProducto);
        if (existente == null) {
            throw new Exception("El producto no existe");
        }
        if (existente.isActivo()) {
            throw new Exception("El producto ya está activo");
        }
        gestionP.cambiarEstado(codigoProducto); // activa
    }

    public void guardarTodos(List<Producto> productos) {
        gestionP.guardarTodos(productos);
    }
}