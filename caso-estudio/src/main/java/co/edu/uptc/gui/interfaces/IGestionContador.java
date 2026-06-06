package co.edu.uptc.gui.interfaces;

import java.util.List;

import co.edu.uptc.gui.modelo.Contador;

public interface IGestionContador {
	
	public void guardar(Contador contador);

	public void actualizar(Contador contador);

	public void eliminar(Long idContador);
	
	Contador buscar(Long idContador);
	
 
	List<Contador> listar();

}
