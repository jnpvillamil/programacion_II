package co.uptc.edu.co.interfaces;

import java.util.List;

import co.uptc.edu.co.modelo.Proveedor;

public interface ProveedorDAO {
	void guardarProveedor(Proveedor proveedor) throws Exception;

	void actualizarProveedor(Proveedor proveedor) throws Exception;

	Proveedor buscarProveedorPorCodigo(String codigo) throws Exception;

	List<Proveedor> listarProveedor() throws Exception;
}
