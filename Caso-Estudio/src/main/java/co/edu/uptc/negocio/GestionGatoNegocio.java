package co.edu.uptc.negocio;


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

	@Override
	public List<Gato> listarTodos() {
		
		return persistencia.listarTodos();
	}
	
	   private void validarGato(Gato gato) {
		   if (gato.getOjos() == null || gato.getOjos().trim().isEmpty())
	            throw new IllegalArgumentException("El color de ojos es obligatorio");
	   }
    
    
}
