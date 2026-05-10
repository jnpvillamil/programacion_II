package co.edu.uptc.Tiendaminorista.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.Datos.Proveedordt;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProveedorPersistencia {

    private static final String RUTA = "datos/proveedores.json";

    private Gson gson;

    public ProveedorPersistencia() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void guardarProveedores(List<Proveedordt> lista) {
        java.io.File carpeta = new java.io.File("datos");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        try (FileWriter escritor = new FileWriter(RUTA)) {
            gson.toJson(lista, escritor);
        } catch (IOException e) {
            System.out.println("Error guardando proveedores: " + e.getMessage());
        }
    }

    public List<Proveedordt> cargarProveedores() {
        java.io.File archivo = new java.io.File(RUTA);

        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (FileReader lector = new FileReader(RUTA)) {
            Type tipoLista = new TypeToken<List<Proveedordt>>() {}.getType();
            List<Proveedordt> lista = gson.fromJson(lector, tipoLista);

            if (lista == null) {
                return new ArrayList<>();
            }
            return lista;

        } catch (IOException e) {
            System.out.println("Error cargando proveedores: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

