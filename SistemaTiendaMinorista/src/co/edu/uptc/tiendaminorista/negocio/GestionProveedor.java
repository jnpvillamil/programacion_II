package co.edu.uptc.tiendaminorista.negocio;

import java.util.List;
import co.edu.uptc.tiendaminorista.interfaces.IGestionProveedor;
import co.edu.uptc.tiendaminorista.modelo.Proveedor;

public class GestionProveedor {
    private IGestionProveedor proveedores;

    public GestionProveedor(IGestionProveedor proveedores) {
        this.proveedores = proveedores;
    }

    public void agregarProveedor(Proveedor proveedor) { proveedores.guardar(proveedor); }
    public void actualizarProveedor(Proveedor proveedor) { proveedores.actualizar(proveedor); }
    public void desactivarProveedor(String codigo) { proveedores.desactivar(codigo); }
    public void activarProveedor(String codigo) { proveedores.activar(codigo); }
    public List<Proveedor> listarProveedores() { return proveedores.listar(); }
}
