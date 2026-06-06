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

import co.uptc.edu.tienda.interfaces.IGestionContable;
import co.uptc.edu.tienda.modelo.MovimientoContable;

public class LocalContable implements IGestionContable {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String RUTA = "movimientos_contables.json";

    @Override
    public void guardar(MovimientoContable movimiento) {
        List<MovimientoContable> lista = cargar();
        lista.add(movimiento);
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (Exception e) {
            System.out.println("Error al guardar movimiento contable: " + e.getMessage());
        }
    }

    @Override
    public List<MovimientoContable> cargar() {
        File file = new File(RUTA);
        if (!file.exists() || file.length() == 0) return new ArrayList<>();
        try {
            Reader reader = new FileReader(file);
            Type tipo = new TypeToken<List<MovimientoContable>>(){}.getType();
            List<MovimientoContable> lista = gson.fromJson(reader, tipo);
            reader.close();
            return lista == null ? new ArrayList<>() : lista;
        } catch (Exception e) {
            System.out.println("Error al cargar movimientos contables: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}