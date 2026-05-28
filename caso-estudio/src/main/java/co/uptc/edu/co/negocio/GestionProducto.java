package co.uptc.edu.co.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionProducto;
import co.uptc.edu.co.modelo.MovimientoInventario;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.enums.EstadoEnum;
import co.uptc.edu.co.modelo.enums.TipoMovimientoInventarioEnum;
import co.uptc.edu.co.interfaces.ProductoDAO;

public class GestionProducto implements IGestionProducto {

    private List<Producto> productos;
    private List<MovimientoInventario> movimientos;
    private final ProductoDAO productoDAO;

    public GestionProducto(ProductoDAO productoDAO) {

        if (productoDAO == null) {
            throw new IllegalArgumentException("El ProductoDAO no puede ser nulo.");
                   
        }

        this.productoDAO = productoDAO;

        try {
            productos = productoDAO.listarProducto();
        } catch (Exception e) {
            productos = new ArrayList<>();
            System.out.println(
                "Error al cargar productos: " + e.getMessage()
            );
        }

        movimientos = new ArrayList<>();
    }
    
    private void recargarProductos() throws Exception {
        productos = productoDAO.listarProducto();
    }

    @Override
    public Producto buscarProductoPorCodigo(String codigo) {
        try {
        	return productoDAO.buscarPorCodigo(codigo);
        } catch (Exception e) {
        	System.out.println("Error al buscar productos: " + e.getMessage());
        	return null;
        }
    }

    @Override
    public List<Producto> obtenerProductos() {
        // Recargar antes de devolver la lista para mostrar el stock persistido más reciente.
        try {
            recargarProductos();
        } catch (Exception e) {
            System.out.println("Error al recargar productos: " + e.getMessage());
        }

        return new ArrayList<>(productos);
    }

    @Override
    public List<MovimientoInventario> obtenerMovimientos() {
        return new ArrayList<>(movimientos);
    }

    @Override
    public void registrarProducto(Producto producto) throws Exception {
        validarProducto(producto);

        if (buscarProductoPorCodigo(producto.getCodigoProducto()) != null) {
            throw new Exception("Ya existe un producto con ese código.");
        }

        producto.setEstado(EstadoEnum.ACTIVO);
        productoDAO.guardarProducto(producto);
        recargarProductos();
    }
    
    @Override
    public void actualizarProducto(Producto productoActualizado) throws Exception {
        validarProducto(productoActualizado);

        Producto productoExistente = buscarProductoPorCodigo(productoActualizado.getCodigoProducto());

        if (productoExistente == null) {
            throw new Exception("No se encontró el producto a actualizar.");
        }

        productoExistente.setNombreProducto(productoActualizado.getNombreProducto());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setPrecioCompra(productoActualizado.getPrecioCompra());
        productoExistente.setPrecioVenta(productoActualizado.getPrecioVenta());
        productoExistente.setStockActual(productoActualizado.getStockActual());
        productoExistente.setStockMinimo(productoActualizado.getStockMinimo());
        productoExistente.setStockMaximo(productoActualizado.getStockMaximo());
        
        productoDAO.actualizarProducto(productoExistente);
        recargarProductos(); 
    }

    @Override
    public void cambiarEstadoProducto(String codigo) throws Exception {
        Producto producto = buscarProductoPorCodigo(codigo);

        if (producto == null) {
            throw new Exception("No se encontró el producto.");
        }

        if (producto.estaActivo()) {
            producto.setEstado(EstadoEnum.INACTIVO);
        } else {
            producto.setEstado(EstadoEnum.ACTIVO);
        }
        productoDAO.actualizarProducto(producto);
        recargarProductos();
    }

    private void validarProducto(Producto producto) throws Exception {
        if (producto == null) {
            throw new Exception("El producto no puede ser nulo.");
        }

        if (producto.getCodigoProducto() == null || producto.getCodigoProducto().trim().isEmpty()) {
            throw new Exception("El código del producto es obligatorio.");
        }

        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new Exception("El nombre del producto es obligatorio.");
        }

        if (producto.getCategoria() == null) {
            throw new Exception("La categoría es obligatoria.");
        }

        if (!producto.getCodigoProducto().matches("[A-Z0-9]{3,10}")) {
            throw new Exception("El código debe ser alfanumérico, en mayúsculas, y tener entre 3 y 10 caracteres.");
        }

        if (!producto.getNombreProducto().matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ .]+")) {
            throw new Exception("El nombre solo debe contener letras, numeros, espacios.");
        }

        if (producto.getPrecioCompra() <= 0) {
            throw new Exception("El precio de compra debe ser mayor que 0.");
        }

        if (producto.getPrecioVenta() <= 0) {
            throw new Exception("El precio de venta debe ser mayor que 0.");
        }

        if (producto.getPrecioVenta() < producto.getPrecioCompra()) {
            throw new Exception("El precio de venta no puede ser menor al precio de compra.");
        }

        if (producto.getStockActual() < 0) {
            throw new Exception("El stock actual no puede ser negativo.");
        }

        if (producto.getStockMinimo() < 0) {
            throw new Exception("El stock mínimo no puede ser negativo.");
        }

        if (producto.getStockMaximo() <= 0) {
            throw new Exception("El stock máximo debe ser mayor que 0.");
        }

        if (producto.getStockActual() > producto.getStockMaximo()) {
            throw new Exception("El stock actual no puede ser mayor que el stock máximo.");
        }

        if (producto.getStockMinimo() > producto.getStockMaximo()) {
            throw new Exception("El stock mínimo no puede ser mayor que el stock máximo.");
        }
    }


    @Override
    public void registrarMovimientoInventario(String codigo, String tipoMovimiento, int cantidad) throws Exception {
        Producto producto = buscarProductoPorCodigo(codigo);

        if (producto == null) {
            throw new Exception("No se encontró el producto.");
        }

        if (cantidad <= 0) {
            throw new Exception("La cantidad debe ser mayor que cero.");
        }

        TipoMovimientoInventarioEnum movimientoEnum;
        try {
            movimientoEnum = TipoMovimientoInventarioEnum.valueOf(tipoMovimiento.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("Tipo de movimiento no válido.");
        }

        if (movimientoEnum == TipoMovimientoInventarioEnum.ENTRADA) {
            int nuevoStock = producto.getStockActual() + cantidad;

            if (nuevoStock > producto.getStockMaximo()) {
                throw new Exception("La entrada supera el stock máximo permitido.");
            }

            producto.setStockActual(nuevoStock);

        } else if (movimientoEnum == TipoMovimientoInventarioEnum.SALIDA) {
            if (cantidad > producto.getStockActual()) {
                throw new Exception("No hay stock suficiente para realizar la salida.");
            }

            producto.setStockActual(producto.getStockActual() - cantidad);
        }

      
        MovimientoInventario movimiento = new MovimientoInventario(
                codigo,
                movimientoEnum,
                cantidad,
                LocalDate.now(),
                "Movimiento registrado"
            );

        movimientos.add(movimiento);
        productoDAO.actualizarProducto(producto);
        productoDAO.registrarMovimiento(movimiento);
        recargarProductos();
    }

    @Override
    public String generarCodigoProducto() {
    	
    	int mayor = 0;
    	
    	for (Producto producto : productos) {
    		String codigo = producto.getCodigoProducto();
    		
    		if(codigo != null && codigo.matches("P\\d{5}")) {
    			int numero = Integer.parseInt(codigo.substring(1));
    			
    			if(numero > mayor) {
    				mayor = numero;
    			}
    		}
    	}		 
    	
    	return String.format("P%05d", mayor + 1);
    			
    }
    
    public void recargar() throws Exception{
    	recargarProductos();
    }
    
}
