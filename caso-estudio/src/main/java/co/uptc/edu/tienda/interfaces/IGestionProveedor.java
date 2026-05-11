package co.uptc.edu.tienda.interfaces;



import java.util.List;

import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.modelo.Proveedor;


public interface IGestionProveedor {
	public void guardar(List<Proveedor> proveedores);
	
	public void actualizar(Proveedor proveedor);
	
	public void eliminar(int codigoProveedor);
	
	public Proveedor buscar(int codigoProveedor);

	
	public List<Proveedor> leerProveedores();
	
	void cambiarEstado(int codigoProveedor, EstadoEnum nuevoEstado);

	
	
}
