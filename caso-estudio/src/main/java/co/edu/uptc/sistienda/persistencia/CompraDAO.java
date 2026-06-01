package co.edu.uptc.sistienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import co.edu.uptc.sistienda.compras.modelo.Compra;
import co.edu.uptc.sistienda.interfaces.IGestionCompra;

public class CompraDAO implements IGestionCompra {

    private static final String RUTA_ARCHIVO = "datos/compras.json";

    private List<Compra> listaCompras;

    private final Gson gson;

    public CompraDAO() {
        // Se registra el adaptador para LocalDate evitando el error de acceso por reflexión
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

                    @Override
                    public void write(JsonWriter out, LocalDate value) throws IOException {
                        if (value == null) {
                            out.nullValue();
                        } else {
                            out.value(formatter.format(value));
                        }
                    }

                    @Override
                    public LocalDate read(JsonReader in) throws IOException {
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                            return null;
                        }
                        return LocalDate.parse(in.nextString(), formatter);
                    }
                })
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

            Type tipoLista = new TypeToken<List<Compra>>() {}.getType();

            List<Compra> lista = gson.fromJson(reader, tipoLista);

            return lista != null ? lista : new ArrayList<>();

        } catch (IOException e) {
            System.err.println("Error al leer compras.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void guardarEnArchivo() {
        File archivo = new File(RUTA_ARCHIVO);

        archivo.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(archivo)) {

            gson.toJson(listaCompras, writer);

        } catch (IOException e) {
            System.err.println("Error al guardar compras.json: " + e.getMessage());
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
            if (compra.getNumeroCompra().equals(numeroCompra)) {
                compra.setAnulada(true);
                guardarEnArchivo();
                return;
            }
        }
    }

    @Override
    public Compra buscarCompra(String numeroCompra) {
        for (Compra compra : listaCompras) {
            if (compra.getNumeroCompra().equals(numeroCompra)) {
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