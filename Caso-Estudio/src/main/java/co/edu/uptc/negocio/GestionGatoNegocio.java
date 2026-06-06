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
		
		persistencia.crear(gato);
		System.out.println("Gato creado exitosamente con color: " + gato.getColor());
		
	}

	@Override
	public List<Gato> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}
>>>>>>> 224a08b2e75d789d1f7fd954cddc9efad27eafda

import co.edu.uptc.interfaces.IGestionGato;
import co.edu.uptc.modelo.Gato;
import java.util.List;

public class GestionGatoNegocio implements IGestionGato {
    
    private final IGestionGato persistencia;
    
    public GestionGatoNegocio(IGestionGato persistencia) {
        this.persistencia = persistencia;
    }
    
    @Override
    public void crear(Gato gato) {
        // Validaciones
        
        if (gato.getRaza() == null || gato.getRaza().trim().isEmpty())
            throw new RuntimeException("La Raza es obligatoria");
 
        
        
        persistencia.crear(gato);
        System.out.println(" Gato guardado: " + gato.getColor());
    }
    
}
