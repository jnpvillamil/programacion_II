package co.edu.uptc.negocio;

import java.util.List;

import co.edu.uptc.interfaces.IGestionGato;
import co.edu.uptc.modelo.Gato;
import co.edu.uptc.persistencia.DatabaseGato;


public class GestionGatoNegocio implements IGestionGato {
	
	private DatabaseGato persistencia;
	
	public GestionGatoNegocio() {
		persistencia = new DatabaseGato();
	}

	@Override
	public void crear(Gato gato) {
		if (gato.getColor() == null || gato.getColor().isEmpty()) {
			System.out.println("El color del gato no puede estar vacío.");
			return;
		}
		
		persistencia.guardarGato(gato);
		System.out.println("Gato creado exitosamente con color: " + gato.getColor());
		
	}

}
