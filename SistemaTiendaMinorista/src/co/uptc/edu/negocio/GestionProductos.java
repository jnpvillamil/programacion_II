package co.uptc.edu.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.modelo.Producto;

public class GestionProductos {

    private static List<Producto> listaProductos;

    public GestionProductos() {
        if(listaProductos == null){
            listaProductos = new ArrayList<>();
        }
    }

    // 🔥 REGISTRAR
    public boolean registrarProducto(Producto p){

        if(buscarProducto(p.getCodigo()) != null){
            return false;
        }

        listaProductos.add(p);
        return true;
    }

    // 🔍 BUSCAR
    public Producto buscarProducto(String codigo){

        for(Producto p : listaProductos){
            if(p.getCodigo().equals(codigo)){
                return p;
            }
        }
        return null;
    }

    // ✏️ MODIFICAR
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

    // ❌ INACTIVAR
    public boolean inactivarProducto(String codigo){

        Producto p = buscarProducto(codigo);

        if(p != null){
            p.setEstado("Inactivo");
            return true;
        }

        return false;
    }

    // ✅ ACTIVAR
    public boolean activarProducto(String codigo){

        Producto p = buscarProducto(codigo);

        if(p != null){
            p.setEstado("Activo");
            return true;
        }

        return false;
    }

    // 💰 ACTUALIZAR PRECIO
    public boolean actualizarPrecio(String codigo, double nuevoPrecioVenta){

        Producto p = buscarProducto(codigo);

        if(p != null){
            p.setPrecioVenta(nuevoPrecioVenta);
            return true;
        }

        return false;
    }

    // 📋 LISTAR
    public List<Producto> obtenerProductos(){
        return listaProductos;
    }
}