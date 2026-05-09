package dao;
import model.Cliente;
import java.util.List;

//PASO 2crear interfaz DAO//
public interface ClienteDAO {
	

	    void guardarClientes(List<Cliente> clientes);

	    List<Cliente> leerClientes();
	    
	    
	    
	}

