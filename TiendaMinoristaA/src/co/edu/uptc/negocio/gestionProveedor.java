package co.edu.uptc.negocio;

import java.util.List;
import co.edu.uptc.interfaces.IGestionProveedor;
import co.edu.uptc.negocio.dto.proveedorDto;
import co.edu.uptc.persistencia.LocalProveedor;

public class gestionProveedor {

    private IGestionProveedor iProveedor;

    public gestionProveedor() {
        this.iProveedor = new LocalProveedor();
    }

    public void registrar(proveedorDto proveedor) throws Exception {
        if (proveedor == null) throw new Exception("No se tiene informacion del proveedor");
        iProveedor.guardar(proveedor);
    }

    public void modificar(proveedorDto proveedor) throws Exception {
        if (proveedor == null) throw new Exception("No se tiene informacion del proveedor");
        iProveedor.actualizar(proveedor);
    }

    public void inactivar(int codigo) throws Exception {
        iProveedor.eliminar(codigo);
    }

    public proveedorDto buscar(int codigo) throws Exception {
        return iProveedor.buscar(codigo);
    }

    public List<proveedorDto> listar() {
        return iProveedor.listar();
    }
}
