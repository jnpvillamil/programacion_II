package co.edu.uptc.caso.estudio.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.negocio.dto.clienteDto;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    private final Gson gson = new Gson();

    @Override
    public void guardarClientes(List<clienteDto> clientes, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(clientes, writer);
            System.out.println("Clientes guardados en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public List<clienteDto> leerClientes(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type tipoLista = new TypeToken<List<clienteDto>>(){}.getType();
            return gson.fromJson(reader, tipoLista);
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return null;
        }
    }
}