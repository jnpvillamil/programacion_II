package co.uptc.edu.tienda.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.interfaces.IGestionInventario;
import co.uptc.edu.tienda.modelo.MovimientoInventario;

public class LocalInventario implements IGestionInventario {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String RUTA = "inventario.json";

    @Override
    public void guardar(List<MovimientoInventario> movimientos) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(movimientos, writer);
        } catch (Exception e) {
            System.out.println("Error al guardar inventario: " + e.getMessage());
        }
    }

    @Override
    public List<MovimientoInventario> leerMovimientos() {
        File archivo = new File(RUTA);
        if (!archivo.exists()) return new ArrayList<>();
        try {
            FileReader reader = new FileReader(archivo);
            Type tipo = new TypeToken<List<MovimientoInventario>>(){}.getType();
            List<MovimientoInventario> lista = gson.fromJson(reader, tipo);
            reader.close();
            return lista == null ? new ArrayList<>() : lista;
        } catch (Exception e) {
            System.out.println("Error al leer inventario: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}