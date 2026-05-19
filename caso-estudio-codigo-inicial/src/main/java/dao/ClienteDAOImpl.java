package dao;

	//implementar el DAO conGson//
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import model.Cliente;

	public class ClienteDAOImpl implements ClienteDAO {

	    private static final String ARCHIVO = "clientes.json";

	    private Gson gson = new Gson();

	    public void guardarClientes(List<Cliente> clientes) {

	        try (FileWriter writer = new FileWriter(ARCHIVO)) {

	            gson.toJson(clientes, writer);

	        } catch (Exception e) {

	            e.printStackTrace();
	        }
	    }

	    public List<Cliente> leerClientes() {

	        try (FileReader reader = new FileReader(ARCHIVO)) {

	            Type tipoLista = new TypeToken<ArrayList<Cliente>>(){}.getType();

	            return gson.fromJson(reader, tipoLista);

	        } catch (Exception e) {

	            return new ArrayList<>();
	        }
	    }
	}	
	
	
	
	
	
	
	
	

