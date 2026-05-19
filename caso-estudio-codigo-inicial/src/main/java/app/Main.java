package app;



//paso 5 crear la clase principal//

	import model.Cliente;
	import service.ClienteService;

	import java.util.ArrayList;
	import java.util.List;

	public class Main {

	    public static void main(String[] args) {

	        ClienteService service = new ClienteService();

	        List<Cliente> lista = new ArrayList<>();

	        lista.add(new Cliente(
	                "001",
	                "Diana",
	                "Estupiñan",
	                "Calle 10",
	                "300123456",
	                "diana@gmail.com"
	        ));

	        lista.add(new Cliente(
	                "002",
	                "Esteban",
	                "Muñoz",
	                "Carrera 20",
	                "301987654",
	                "muñoz@gmail.com"
	        ));

	        service.guardarClientes(lista);

	        List<Cliente> clientes = service.obtenerClientes();

	        for (Cliente c : clientes) {

	            System.out.println(c);
	        }
	    }
	}	
	
	
	
	
	
	

