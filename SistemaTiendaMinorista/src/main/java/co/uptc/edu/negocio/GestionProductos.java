package co.uptc.edu.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.modelo.Producto;
import co.uptc.edu.interfaces.IProductoDAO;
import co.uptc.edu.persistencia.ProductoDAO;


public class GestionProductos {
	private IProductoDAO productoDAO;

    private static List<Producto> listaProductos;
    public GestionProductos() {

        this(new ProductoDAO());
    }

    public GestionProductos(IProductoDAO productoDAO) {
    	
    	this.productoDAO = productoDAO;

        if(listaProductos == null){
            listaProductos = new ArrayList<>();
        }
    }

    // ================= REGISTRAR =================
    public boolean registrarProducto(Producto p){

        if(buscarProducto(p.getCodigo()) != null){
            return false;
        }

        listaProductos.add(p);
        
        productoDAO.guardarProducto(p);

        return true;
    }

    // ================= BUSCAR =================
    public Producto buscarProducto(String codigo){

        for(Producto p : listaProductos){

            if(p.getCodigo().equalsIgnoreCase(codigo)){
                return p;
            }
        }

        return null;
    }

    // ================= MODIFICAR =================
    public boolean modificarProducto(Producto nuevo){

        Producto p = buscarProducto(nuevo.getCodigo());

        if(p != null){

            p.setNombre(nuevo.getNombre());
            p.setCategoria(nuevo.getCategoria());
            p.setPrecioCompra(nuevo.getPrecioCompra());
            p.setPrecioVenta(nuevo.getPrecioVenta());
            p.setStockActual(nuevo.getStockActual());
            p.setStockMinimo(nuevo.getStockMinimo());
            p.setEstado(nuevo.getEstado());

            return true;
        }

        return false;
    }

    // ================= AUMENTAR STOCK =================
    public void aumentarStock(String codigo, int cantidad){

        Producto p = buscarProducto(codigo);

        if(p != null){

            p.setStockActual(
                    p.getStockActual() + cantidad
            );
        }
    }

    // ================= ACTUALIZAR PRECIO =================
    public boolean actualizarPrecio(String codigo, double nuevoPrecioVenta){

        Producto p = buscarProducto(codigo);

        if(p != null){

            p.setPrecioVenta(nuevoPrecioVenta);

            return true;
        }

        return false;
    }

    // ================= LISTAR =================
    public List<Producto> obtenerProductos(){

        return listaProductos;
    }
}