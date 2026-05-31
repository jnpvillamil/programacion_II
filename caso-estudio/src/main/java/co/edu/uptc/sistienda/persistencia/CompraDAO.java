package co.edu.uptc.sistienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.sistienda.compras.modelo.Compra;
import co.edu.uptc.sistienda.interfaces.IGestionCompra;

public class CompraDAO implements IGestionCompra {

    private static final String RUTA_ARCHIVO = "datos/compras.json";

    private List<Compra> listaCompras;

    private final Gson gson;

    public CompraDAO() {

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        listaCompras = cargarDesdeArchivo();
    }

    private List<Compra> cargarDesdeArchivo() {

        File archivo = new File(RUTA_ARCHIVO);

        archivo.getParentFile().mkdirs();

        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(archivo)) {

            Type tipoLista =
                    new TypeToken<List<Compra>>() {
                    }.getType();

            List<Compra> lista =
                    gson.fromJson(reader, tipoLista);

            return lista != null
                    ? lista
                    : new ArrayList<>();

        } catch (IOException e) {

            System.err.println(
                    "Error al leer compras.json: "
                            + e.getMessage());

            return new ArrayList<>();
        }
    }

    private void guardarEnArchivo() {

        File archivo = new File(RUTA_ARCHIVO);

        archivo.getParentFile().mkdirs();

        try (FileWriter writer =
                     new FileWriter(archivo)) {

            gson.toJson(listaCompras, writer);

        } catch (IOException e) {

            System.err.println(
                    "Error al guardar compras.json: "
                            + e.getMessage());
        }
    }

    @Override
    public void guardarCompra(Compra compra) {

        listaCompras.add(compra);

        guardarEnArchivo();
    }

    @Override
    public void anularCompra(String numeroCompra) {

        for (Compra compra : listaCompras) {

            if (compra.getNumeroCompra()
                    .equals(numeroCompra)) {

                compra.setAnulada(true);

                guardarEnArchivo();

                return;
            }
        }
    }

    @Override
    public Compra buscarCompra(String numeroCompra) {

        for (Compra compra : listaCompras) {

            if (compra.getNumeroCompra()
                    .equals(numeroCompra)) {

                return compra;
            }
        }

        return null;
    }

    @Override
    public List<Compra> obtenerListaCompras() {

        return new ArrayList<>(listaCompras);
    }
}
