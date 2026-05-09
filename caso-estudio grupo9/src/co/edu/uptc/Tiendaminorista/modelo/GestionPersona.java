package co.edu.uptc.Tiendaminorista.modelo;

import java.util.ArrayList;
import java.util.List;

public class GestionPersona {
	private List<Cliente> clientes;
	private List<Proveedor> proveedores;
	
	public GestionPersona() {
		super();
		clientes = new ArrayList<Cliente>();
		proveedores = new ArrayList<Proveedor>();
	}
	public void agregarcliente(Cliente Clientes) {
		clientes.add(Clientes);
	}
	public List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	public List<Proveedor> getProveedores() {
		return proveedores;
	}
	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}
}
