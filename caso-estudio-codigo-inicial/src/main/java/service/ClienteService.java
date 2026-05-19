package service;

//paso 4 creasr el servicio que utiliza el DAO//

	
	import dao.ClienteDAO;
	import dao.ClienteDAOImpl;
	import model.Cliente;

	import java.util.List;

	public class ClienteService {

	    private ClienteDAO clienteDAO;

	    public ClienteService() {

	        clienteDAO = new ClienteDAOImpl();
	    }

	    public void guardarClientes(List<Cliente> clientes) {

	        clienteDAO.guardarClientes(clientes);
	    }

	    public List<Cliente> obtenerClientes() {

	        return clienteDAO.leerClientes();
	    }
	}

