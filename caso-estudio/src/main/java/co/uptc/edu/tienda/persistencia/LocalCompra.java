package co.uptc.edu.tienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.uptc.edu.tienda.interfaces.IGestionCompra;
import co.uptc.edu.tienda.modelo.Compra;

public class LocalCompra implements IGestionCompra {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String RUTA = "comprasProveedor.json";

    @Override
    public void guardar(Compra compra) {
        // Lee la lista, agrega la nueva, sobrescribe
        List<Compra> lista = cargar();
        lista.add(compra);
        guardarArchivo(lista);
    }

    private void guardarArchivo(List<Compra> lista) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (Exception e) {
            System.out.println("Error al guardar compra: " + e.getMessage());
        }
    }

    @Override
    public List<Compra> cargar() {
        File file = new File(RUTA);
        if (!file.exists() || file.length() == 0) return new ArrayList<>();
        try {
            Reader reader = new FileReader(file);
            Type tipo = new TypeToken<List<Compra>>(){}.getType();
            List<Compra> lista = gson.fromJson(reader, tipo);
            reader.close();
            return lista == null ? new ArrayList<>() : lista;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}