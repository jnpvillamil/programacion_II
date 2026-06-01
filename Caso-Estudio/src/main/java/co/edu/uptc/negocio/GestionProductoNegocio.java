package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.IGestionProducto;
import co.edu.uptc.modelo.Producto;
import java.util.List;


 

 
public class GestionProductoNegocio implements IGestionProducto {

    private final IGestionProducto persistencia;

    public GestionProductoNegocio(IGestionProducto persistencia) {
        this.persistencia = persistencia;
    }

    // ========== IMPLEMENTACIÓN DE MÉTODOS DE LA INTERFAZ ==========

    @Override
    public void crear(Producto producto) {
        validarProductoNulo(producto);
        validarCodigoUnico(producto.getCodigo());
        validarCamposObligatorios(producto);
        validarPrecios(producto);
        validarStock(producto);
        validarFormatoNombre(producto.getNombre());
        validarCategoria(producto.getCategoria());

        persistencia.crear(producto);
        System.out.println(" Producto creado exitosamente: " + producto.getCodigo());
    }

    @Override
    public void actualizar(Producto producto) {
        validarProductoNulo(producto);
        
        // Verificar que el producto exista
        Producto existente = persistencia.buscar(producto.getCodigo());
        if (existente == null) {
            throw new IllegalArgumentException("No existe un producto con código: " + producto.getCodigo());
        }
        
        validarCamposObligatorios(producto);
        validarPrecios(producto);
        validarStock(producto);
        validarFormatoNombre(producto.getNombre());
        validarCategoria(producto.getCategoria());

        persistencia.actualizar(producto);
        System.out.println(" Producto actualizado exitosamente: " + producto.getCodigo());
    }

    @Override
    public void eliminar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío.");
        }
        
        Producto producto = persistencia.buscar(codigo);
        if (producto == null) {
            throw new IllegalArgumentException("No existe un producto con código: " + codigo);
        }
        
        if (!producto.isActivo()) {
            throw new IllegalStateException("El producto ya está inactivo.");
        }
        
        // REGLA DE NEGOCIO
        if (producto.getStockActual() > 0) {
            throw new IllegalStateException("No se puede inactivar un producto con stock positivo. Stock actual: " + producto.getStockActual());
        }
        
        persistencia.eliminar(codigo);
        System.out.println("✅ Producto inactivado exitosamente: " + codigo);
    }

    @Override
    public Producto buscar(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código para buscar es obligatorio.");
        }
        return persistencia.buscar(codigo);
    }

    @Override
    public List<Producto> listar() {
        return persistencia.listar();
    }

    @Override
    public List<Producto> listarActivos() {
        return persistencia.listarActivos();
    }

    @Override
    public List<Producto> listarPorStockMinimo() {
        List<Producto> bajoStock = persistencia.listarPorStockMinimo();
        if (bajoStock.isEmpty()) {
            System.out.println(" No hay productos con stock bajo.");
        }
        return bajoStock;
    }

    @Override
    public boolean existe(String codigo) {
        return persistencia.existe(codigo);
    }

    // ========== MÉTODOS ADICIONALES  ==========

    public void agregarProducto(Producto producto) {
        crear(producto);
    }

    public void actualizarProducto(Producto producto) {
        actualizar(producto);
    }

    public void inactivarProducto(String codigo) {
        eliminar(codigo);
    }

    public Producto buscarProducto(String codigo) {
        return buscar(codigo);
    }

    public List<Producto> listarProductosActivos() {
        return listarActivos();
    }

    public List<Producto> listarTodosProductos() {
        return listar();
    }

    public List<Producto> listarProductosStockBajo() {
        return listarPorStockMinimo();
    }

    // ========== MÉTODOS PRIVADOS DE VALIDACIÓN ==========

    private void validarProductoNulo(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
    }

    private void validarCodigoUnico(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del producto es obligatorio.");
        }
        if (codigo.length() > 10) {
            throw new IllegalArgumentException("El código no puede tener más de 10 caracteres.");
        }
        if (persistencia.existe(codigo)) {
            throw new IllegalStateException("Ya existe un producto con el código: " + codigo);
        }
    }

    private void validarCamposObligatorios(Producto producto) {
        // Nombre
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }
        if (producto.getNombre().trim().length() < 3) {
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres.");
        }
        if (producto.getNombre().trim().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede tener más de 100 caracteres.");
        }

        // Categoría
        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría del producto es obligatoria.");
        }
    }

    private void validarPrecios(Producto producto) {
        if (producto.getPrecioCompra() <= 0) {
            throw new IllegalArgumentException("El precio de compra debe ser mayor a 0.");
        }
        if (producto.getPrecioVenta() <= 0) {
            throw new IllegalArgumentException("El precio de venta debe ser mayor a 0.");
        }
        if (producto.getPrecioVenta() <= producto.getPrecioCompra()) {
            throw new IllegalArgumentException("El precio de venta debe ser mayor al precio de compra.");
        }
        
        // Validar precios razonables (opcional)
        if (producto.getPrecioCompra() > 10000000) {
            throw new IllegalArgumentException("El precio de compra no puede ser mayor a 10,000,000.");
        }
        if (producto.getPrecioVenta() > 20000000) {
            throw new IllegalArgumentException("El precio de venta no puede ser mayor a 20,000,000.");
        }
    }

    private void validarStock(Producto producto) {
        if (producto.getStockActual() < 0) {
            throw new IllegalArgumentException("El stock actual no puede ser negativo.");
        }
        if (producto.getStockMinimo() < 0) {
            throw new IllegalArgumentException("El stock mínimo no puede ser negativo.");
        }
        if (producto.getStockMinimo() > producto.getStockActual()) {
            System.out.println(" Advertencia: El stock actual está por debajo del mínimo recomendado.");
        }
    }

    private void validarFormatoNombre(String nombre) {
        // Permitir letras, números, espacios y algunos caracteres especiales básicos
        if (!nombre.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras, números y espacios.");
        }
    }

    private void validarCategoria(String categoria) {
        String[] categoriasValidas = {"Víveres", "Aseo", "Papelería", "Otros"};
        for (String cat : categoriasValidas) {
            if (cat.equals(categoria)) {
                return;
            }
        }
        throw new IllegalArgumentException("Categoría no válida. Debe ser: Víveres, Aseo, Papelería u Otros.");
    }
}