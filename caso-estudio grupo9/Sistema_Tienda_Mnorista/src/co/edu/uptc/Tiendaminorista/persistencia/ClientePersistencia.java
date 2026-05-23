package co.edu.uptc.Tiendaminorista.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.Datos.Clientedt;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClientePersistencia {

    private static final String RUTA = "datos/clientes.json";

    private Gson gson;

    public ClientePersistencia() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void guardarClientes(List<Clientedt> lista) {
        java.io.File carpeta = new java.io.File("datos");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        try (FileWriter escritor = new FileWriter(RUTA)) {
            gson.toJson(lista, escritor);
        } catch (IOException e) {
            System.out.println("Error guardando clientes: " + e.getMessage());
        }
    }

    public List<Clientedt> cargarClientes() {
        java.io.File archivo = new java.io.File(RUTA);

        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (FileReader lector = new FileReader(RUTA)) {
            Type tipoLista = new TypeToken<List<Clientedt>>() {}.getType();
            List<Clientedt> lista = gson.fromJson(lector, tipoLista);

            if (lista == null) {
                return new ArrayList<>();
            }
            return lista;

        } catch (IOException e) {
            System.out.println("Error cargando clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
} 
