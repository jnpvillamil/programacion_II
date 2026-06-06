package co.uptc.edu.tienda.interfaces;



import java.util.List;

import co.uptc.edu.tienda.negocio.Proveedor;


public interface IGestionProveedor {
	public void guardar(Proveedor proveedor);
	
	public void actualizar(Proveedor proveedor);
	
	public void eliminar(int codigoProveedor);
	
	public Proveedor buscar(int codigoProveedor);

	
	public List<Proveedor> listar();

	
	
}
