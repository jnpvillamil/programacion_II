package co.edu.uptc.Tiendaminorista.negocio;
import co.edu.uptc.Tiendaminorista.modelo.Proveedor;
import co.edu.uptc.Tiendaminorista.interfaces.IGestionProveedor;


public class GestionProveedor {

	private IGestionProveedor proveedores;

	public GestionProveedor(IGestionProveedor proveedores) {
		super();
		this.proveedores = proveedores;
	}

	public void agregarProveedor(Proveedor proveedor) {
		proveedores.guardar(proveedor);
	}

	public void actualizarProveedor(Proveedor proveedor) {
		proveedores.actualizar(proveedor);
	}

	public void desactivarProveedor(String codigo) {
		proveedores.desactivar(codigo);
	}

	public void activarProveedor(String codigo) {
		proveedores.activar(codigo);
	}

	public java.util.List<Proveedor> listarProveedores() {
		return proveedores.listar();
	}
}
