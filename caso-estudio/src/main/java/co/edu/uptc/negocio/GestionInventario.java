package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.persistencia.PersistenciaProducto;

import java.util.List;

/**
 * Capa de negocio para la gestión de inventario/productos.
 * 
 * APLICACIÓN DE PRINCIPIOS SOLID:
 * - S (Single Responsibility): Solo gestiona lógica de negocio de productos
 * - D (Dependency Inversion): Depende de Repositorio<Producto>, no de implementación concreta
 * - O (Open/Closed): Abierto a nuevas implementaciones de persistencia
 */
public class GestionInventario {

    private Repositorio<Producto> repositorioProducto;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param repositorioProducto Implementación de Repositorio<Producto>
     */
    public GestionInventario(Repositorio<Producto> repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    /**
     * Constructor por defecto: usa PersistenciaProducto.
     * Mantiene compatibilidad con código existente.
     */
    public GestionInventario() {
        this(new PersistenciaProducto());
    }

    public boolean registrarProducto(Producto producto) {
        if (repositorioProducto.buscarPorId(producto.getCodigoProducto()) != null) {
            return false; 
        }
        if (producto.getStockMinimo() >= producto.getStockMaximo()) {
            return false; 
        }
        repositorioProducto.guardar(producto);
        return true;
    }

    public boolean actualizarProducto(Producto producto) {
        if (repositorioProducto.buscarPorId(producto.getCodigoProducto()) == null) {
            return false; 
        }
        repositorioProducto.actualizar(producto);
        return true;
    }

    public boolean descontarStock(String codigoProducto, int cantidad) {
        Producto producto = repositorioProducto.buscarPorId(codigoProducto);
        if (producto != null && producto.isActivo()) {
            if (producto.getStockActual() >= cantidad) {
                producto.setStockActual(producto.getStockActual() - cantidad);
                repositorioProducto.actualizar(producto);
                return true;
            }
        }
        return false; 
    }

    public Producto buscarProducto(String codigoProducto) {
        return repositorioProducto.buscarPorId(codigoProducto);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return repositorioProducto.listar();
    }
}