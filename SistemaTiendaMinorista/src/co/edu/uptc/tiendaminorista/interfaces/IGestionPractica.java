package co.edu.uptc.tiendaminorista.interfaces;

import java.util.List;

import co.edu.uptc.tiendaminorista.modelo.PersonaPractica;

public interface IGestionPractica {
	void guardar(PersonaPractica persona);
	
	List<PersonaPractica>obtenerTodasLasPersonas();
	
	boolean actualizar(String texto1Buscado, String nuevoTexto2);
	
	boolean eliminar (String texto1Buscado);
}
