package co.uptc.edu.co.persistencia;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.uptc.edu.co.interfaces.ProductoDAO;
import co.uptc.edu.co.modelo.Producto;

public class ProductoJSONDAO implements ProductoDAO {
	
	  private static final String RUTA = "productos.json";
	    private Gson gson;

	    public ProductoJSONDAO() {
	        gson = new GsonBuilder().setPrettyPrinting().create();
	    }

	    @Override
	    public void guardarProductos(List<Producto> productos) throws Exception {
	        try (FileWriter writer = new FileWriter(RUTA)) {
	            gson.toJson(productos, writer);
	        }
	    }

	    @Override
	    public List<Producto> leerProductos() throws Exception {
	        try (FileReader reader = new FileReader(RUTA)) {
	            Type tipoLista = new TypeToken<List<Producto>>() {}.getType();
	            List<Producto> productos = gson.fromJson(reader, tipoLista);
	            return productos != null ? productos : new ArrayList<>();
	        } catch (Exception e) {
	            return new ArrayList<>();
	        }
	    }
	
	

}
