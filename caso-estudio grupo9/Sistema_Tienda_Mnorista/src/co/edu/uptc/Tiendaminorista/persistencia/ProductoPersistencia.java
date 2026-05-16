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

    // Apunta al productos.json en la raíz del proyecto (donde ya existe)
    private static final String RUTA = "productos.json";

    private Gson gson;

    public ProductoPersistencia() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void guardarProductos(List<Productodt> lista) {
        // FileWriter con false sobreescribe, no agrega al final
        try (FileWriter escritor = new FileWriter(RUTA, false)) {
            gson.toJson(lista, escritor);
        } catch (IOException e) {
            System.out.println("Error guardando productos: " + e.getMessage());
        }
    }

    public List<Productodt> cargarProductos() {
        java.io.File archivo = new java.io.File(RUTA);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (FileReader lector = new FileReader(RUTA)) {
            Type tipoLista = new TypeToken<List<Productodt>>() {}.getType();
            List<Productodt> lista = gson.fromJson(lector, tipoLista);
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