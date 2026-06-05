package co.edu.uptc.gui.interfaces;

import java.util.List;
import co.edu.uptc.gui.modelo.Proveedor;

public interface IGestionDeProveedor {

	public void guardarProveedor(Proveedor proveedor);

	public void actualizarProveedor(Proveedor proveedor);

	public void inactivarProveedor(String codigoProveedor);
	
	public void activarProveedor(String codigoProveedor);

	public Proveedor buscarProveedorPorCodigo(String codigoProveedor);

	public List<Proveedor> obtenerListaProveedores();
}