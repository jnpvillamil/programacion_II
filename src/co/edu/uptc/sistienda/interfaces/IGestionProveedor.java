package co.edu.uptc.sistienda.interfaces;

import java.util.List;
import co.edu.uptc.sistienda.modelo.Proveedor;

public interface IGestionProveedor {

	public void guardarProveedor(Proveedor proveedor);

	public void actualizarProveedor(Proveedor proveedor);

	public void inactivarProveedor(String codigoProveedor);
	
	public void activarProveedor(String codigoProveedor);

	public Proveedor buscarProveedorPorCodigo(String codigoProveedor);

	public List<Proveedor> obtenerListaProveedores();
}
