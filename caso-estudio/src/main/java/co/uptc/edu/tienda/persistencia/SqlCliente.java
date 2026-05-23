package co.uptc.edu.tienda.persistencia;

import java.util.List;

import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.interfaces.IGestionCliente;
import co.uptc.edu.tienda.modelo.Cliente;

public class SqlCliente implements IGestionCliente{

	@Override
	public void guardar(List<Cliente> clientes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualizar(Cliente cliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar(int codigoCliente) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cliente buscar(int codigoCliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> leerClientes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cambiarEstado(int codigoCliente, EstadoEnum nuevoEstado) {
		// TODO Auto-generated method stub
		
	}

}
