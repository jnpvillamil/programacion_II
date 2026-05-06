package co.edu.uptc.caso.estudio.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.negocio.dto.proveedorDto;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ProveedorDAOImpl implements ProveedorDAO {

    private final Gson gson = new Gson();

    @Override
    public void guardarProveedores(List<proveedorDto> proveedores, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(proveedores, writer);
            System.out.println("Proveedores guardados en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public List<proveedorDto> leerProveedores(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<List<proveedorDto>>(){}.getType();
            return gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return null;
        }
    }
}