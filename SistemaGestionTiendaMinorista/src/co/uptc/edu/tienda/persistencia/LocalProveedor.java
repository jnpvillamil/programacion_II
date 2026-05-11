package co.uptc.edu.tienda.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.interfaces.IGestionProveedor;
import co.uptc.edu.tienda.negocio.Proveedor;

public class LocalProveedor implements IGestionProveedor {

	private List<Proveedor> listaProveedores;
	
	
	
	public LocalProveedor() {
		super();
		// TODO Auto-generated constructor stub
		this.listaProveedores = new ArrayList<>();
	}

	@Override
	public void guardar(Proveedor proveedor) {
		// TODO Auto-generated method stub
		listaProveedores.add(proveedor);
	}

	@Override
	public void actualizar(Proveedor proveedor) {
		// TODO Auto-generated method stub
		for (int i = 0; i < listaProveedores.size(); i++) {
	        // Buscamos por el código único
	        if (listaProveedores.get(i).getCodigoProveedor() == proveedor.getCodigoProveedor()) {
	            listaProveedores.set(i, proveedor);
	            return; 
	        }
	    }
	}

	@Override
	public void eliminar(int codigoProveedor) {
		// TODO Auto-generated method stub
		listaProveedores.removeIf(p -> p.getCodigoProveedor() == codigoProveedor);
	}

	@Override
	public List<Proveedor> listar() {
		// TODO Auto-generated method stub
		return listaProveedores;
	}



	@Override
	public Proveedor buscar(int codigoProveedor) {
		// TODO Auto-generated method stub
		for(Proveedor p: listaProveedores) {
			if(p.getCodigoProveedor() == codigoProveedor) {
				return p;
			}
		}
		return null;
	}


}
