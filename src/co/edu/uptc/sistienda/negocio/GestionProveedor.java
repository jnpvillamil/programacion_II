package co.edu.uptc.sistienda.negocio;

import java.util.List;

import co.edu.uptc.sistienda.interfaces.IGestionProveedor;
import co.edu.uptc.sistienda.modelo.Proveedor;

public class GestionProveedor {

	private IGestionProveedor repositorioProveedor;

	public GestionProveedor(IGestionProveedor repositorioProveedor) {
		this.repositorioProveedor = repositorioProveedor;
	}

	public void registrarNuevoProveedor(Proveedor proveedor) throws Exception {
		if (proveedor.getCodigoProveedor() == null || proveedor.getCodigoProveedor().trim().isEmpty()) {
			throw new Exception("El código del proveedor no puede estar vacío.");
		}
		if (proveedor.getRazonSocial() == null || proveedor.getRazonSocial().trim().isEmpty()) {
			throw new Exception("La razón social del proveedor no puede estar vacía.");
		}
		if (proveedor.getNit() == null || proveedor.getNit().trim().isEmpty()) {
			throw new Exception("El NIT del proveedor no puede estar vacío.");
		}
		if (repositorioProveedor.buscarProveedorPorCodigo(proveedor.getCodigoProveedor()) != null) {
			throw new Exception("Ya existe un proveedor con el código: " + proveedor.getCodigoProveedor());
		}
		repositorioProveedor.guardarProveedor(proveedor);
	}

	public void modificarProveedor(Proveedor proveedor) throws Exception {
		if (repositorioProveedor.buscarProveedorPorCodigo(proveedor.getCodigoProveedor()) == null) {
			throw new Exception("No se encontró el proveedor con código: " + proveedor.getCodigoProveedor());
		}
		repositorioProveedor.actualizarProveedor(proveedor);
	}

	public void inactivarProveedor(String codigoProveedor) throws Exception {
		if (repositorioProveedor.buscarProveedorPorCodigo(codigoProveedor) == null) {
			throw new Exception("No se encontró el proveedor con código: " + codigoProveedor);
		}
		repositorioProveedor.inactivarProveedor(codigoProveedor);
	}
	
	public void activarProveedor(String codigoProveedor) throws Exception {
		if (repositorioProveedor.buscarProveedorPorCodigo(codigoProveedor) == null) {
			throw new Exception("No se encontró el proveedor con código: " + codigoProveedor);
		}
		repositorioProveedor.activarProveedor(codigoProveedor);
	}

	public Proveedor consultarProveedorPorCodigo(String codigoProveedor) {
		return repositorioProveedor.buscarProveedorPorCodigo(codigoProveedor);
	}

	public List<Proveedor> obtenerListaProveedores() {
		return repositorioProveedor.obtenerListaProveedores();
	}
}
