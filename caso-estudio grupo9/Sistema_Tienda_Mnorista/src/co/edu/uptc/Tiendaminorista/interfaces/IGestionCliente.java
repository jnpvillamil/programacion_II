package co.edu.uptc.Tiendaminorista.interfaces;
import java.util.List;

import co.edu.uptc.Tiendaminorista.modelo.*;
public interface IGestionCliente {

	public void guardar(Cliente cliente);
	
	public void actualizar(Cliente cliente);
	
	public List<Cliente> listar();
	
    public void desactivar(String codigo);
    
    public void activar(String codigo);
}
