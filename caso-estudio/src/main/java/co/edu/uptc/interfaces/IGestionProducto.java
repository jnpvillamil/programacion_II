package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.negocio.dto.productoDto;

public interface IGestionProducto {
    public void guardar(productoDto producto);
    public void actualizar(productoDto producto);
    public void eliminar(int codigoProducto);
    public productoDto buscar(int codigoProducto);
    public List<productoDto> listar();
}
