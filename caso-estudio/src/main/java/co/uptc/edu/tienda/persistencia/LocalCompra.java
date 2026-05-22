package co.uptc.edu.tienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final String RUTA = "comprasProveedor.json";

    @Override
    public void guardar(List<Compra> lista) {

        try (FileWriter writer = new FileWriter(RUTA)) {

            gson.toJson(lista, writer);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public List<Compra> cargar() {

        try {

            File file = new File(RUTA);

            if (!file.exists() || file.length() == 0) {

                return new ArrayList<>();
            }

            Reader reader = new FileReader(file);

            Type tipo =
                    new TypeToken<List<Compra>>() {}.getType();

            List<Compra> lista =
                    gson.fromJson(reader, tipo);

            reader.close();

            if (lista == null) {

                return new ArrayList<>();
            }

            return lista;

        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

    @Override
    public void agregarCompra(Compra compra) {

        List<Compra> lista = cargar();

        lista.add(compra);

        guardar(lista);
    }

    @Override
    public void actualizarCompra(Compra compra) {

        List<Compra> lista = cargar();

        for (int i = 0; i < lista.size(); i++) {

            if (lista.get(i).getIdCompra()
                    == compra.getIdCompra()) {

                lista.set(i, compra);

                break;
            }
        }

        guardar(lista);
    }

    @Override
    public boolean eliminarCompra(int id) {

        List<Compra> lista = cargar();

        boolean eliminado =
                lista.removeIf(
                        compra ->
                                compra.getIdCompra() == id);

        guardar(lista);

        return eliminado;
    }

    @Override
    public Compra buscarCompra(int id) {

        List<Compra> lista = cargar();

        for (Compra compra : lista) {

            if (compra.getIdCompra() == id) {

                return compra;
            }
        }

        return null;
    }

    @Override
    public List<Compra> listarCompras() {

        return cargar();
    }
}