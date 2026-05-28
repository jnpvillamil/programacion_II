package co.edu.uptc.negocio;

import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.persistencia.PersistenciaProducto;

import java.util.List;

public class GestionInventario {

    private Repositorio<Producto> repositorioProducto;

    public GestionInventario(Repositorio<Producto> repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

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

    public boolean registrarMovimientoInventario(String codigo, int cantidad, String tipo) {
        if (cantidad <= 0) {
            return false;
        }

        Producto producto = repositorioProducto.buscarPorId(codigo);
        if (producto == null) {
            return false;
        }

        if ("ENTRADA".equalsIgnoreCase(tipo)) {
            producto.setStockActual(producto.getStockActual() + cantidad);
        } else if ("SALIDA".equalsIgnoreCase(tipo)) {
            if (producto.getStockActual() < cantidad) {
                return false;
            }
            producto.setStockActual(producto.getStockActual() - cantidad);
        } else {
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

    public List<ProductoResumenDTO> listarResumen() {
        return repositorioProducto.listar().stream()
                .filter(Producto::isActivo)
                .map(p -> new ProductoResumenDTO(
                        p.getCodigoProducto(),
                        p.getNombreProducto(),
                        p.getCategoria() != null ? p.getCategoria().name() : "",
                        p.getPrecioVenta(),
                        p.getStockActual()))
                .toList();
    }
}
