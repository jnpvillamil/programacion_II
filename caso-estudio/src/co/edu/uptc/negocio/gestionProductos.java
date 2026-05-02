package co.edu.uptc.negocio;

import java.util.List;
import co.edu.uptc.interfaces.IGestionProducto;
import co.edu.uptc.negocio.dto.productoDto;
import co.edu.uptc.persistencia.LocalProducto;

public class gestionProductos {

    private IGestionProducto iProducto;

    public gestionProductos() {
        this.iProducto = new LocalProducto();
    }

    public void registrar(productoDto producto) throws Exception {
        if (producto == null) throw new Exception("No se tiene informacion del producto");
        iProducto.guardar(producto);
    }

    public void modificar(productoDto producto) throws Exception {
        if (producto == null) throw new Exception("No se tiene informacion del producto");
        iProducto.actualizar(producto);
    }

    public void inactivar(int codigo) throws Exception {
        iProducto.eliminar(codigo);
    }

    public productoDto buscar(int codigo) throws Exception {
        return iProducto.buscar(codigo);
    }

    public List<productoDto> listar() {
        return iProducto.listar();
    }
}
