package co.uptc.edu.tienda.interfaces;

import java.util.List;

import co.uptc.edu.tienda.modelo.Producto;

public interface IGestionProducto {

    public void guardar(List<Producto> productos);

    public void actualizar(Producto producto);

    public void eliminar(int codigoProducto);

    public Producto buscar(int codigoProducto);

    public List<Producto> listar();
    
    public void cambiarEstado(int codigoProducto);
}