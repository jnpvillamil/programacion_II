package co.edu.uptc.Tiendaminorista.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.Datos.Productodt;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductoPersistencia {

    // ruta donde se guarda el archivo JSON
    private static final String RUTA = "datos/productos.json";

    private Gson gson;

    public ProductoPersistencia() {
        // con setPrettyPrinting el archivo queda mas legible
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void guardarProductos(List<Productodt> lista) {
        // si la carpeta no existe la crea
        java.io.File carpeta = new java.io.File("datos");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        try (FileWriter escritor = new FileWriter(RUTA)) {
            gson.toJson(lista, escritor);
        } catch (IOException e) {
            System.out.println("Error guardando productos: " + e.getMessage());
        }
    }

    public List<Productodt> cargarProductos() {
        java.io.File archivo = new java.io.File(RUTA);

        // si no existe el archivo devuelve lista vacia
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (FileReader lector = new FileReader(RUTA)) {
            Type tipoLista = new TypeToken<List<Productodt>>() {}.getType();
            List<Productodt> lista = gson.fromJson(lector, tipoLista);

            // gson puede devolver null si el archivo esta vacio
            if (lista == null) {
                return new ArrayList<>();
            }
            return lista;

        } catch (IOException e) {
            System.out.println("Error cargando productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
