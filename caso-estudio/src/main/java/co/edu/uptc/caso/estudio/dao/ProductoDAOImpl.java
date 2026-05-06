package co.edu.uptc.caso.estudio.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.negocio.dto.productoDto;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    private final Gson gson = new Gson();

    @Override
    public void guardarProductos(List<productoDto> productos, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(productos, writer);
            System.out.println("Productos guardados en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public List<productoDto> leerProductos(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<List<productoDto>>(){}.getType();
            return gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return null;
        }
    }
}