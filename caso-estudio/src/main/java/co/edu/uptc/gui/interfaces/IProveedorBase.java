package co.edu.uptc.gui.interfaces;
import co.edu.uptc.gui.modelo.Proveedor;
import java.util.List;

public interface IProveedorBase {
    public void ejecutarOperacionProveedor(Proveedor proveedor);
    public List<Proveedor> listarProveedores();
}