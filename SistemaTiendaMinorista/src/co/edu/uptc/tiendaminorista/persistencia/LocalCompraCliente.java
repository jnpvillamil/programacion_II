package co.edu.uptc.tiendaminorista.persistencia;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.tiendaminorista.interfaces.IGestionCompraCli;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;

public class LocalCompraCliente implements IGestionCompraCli {
    
    private static final String RUTA = System.getProperty("user.dir") + File.separator + "compras_clientes.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private List<CompasCliente> leer() {
        File archivo = new File(RUTA);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        
        try (FileReader reader = new FileReader(archivo)) {
            Type tipo = new TypeToken<List<CompasCliente>>() {}.getType();
            List<CompasCliente> lista = gson.fromJson(reader, tipo);
            return lista != null ? lista : new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error leyendo el archivo: " + RUTA);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void escribir(List<CompasCliente> lista) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardarCompra(CompasCliente compra) {
        List<CompasCliente> lista = leer();
        if (compra != null) {
            lista.add(compra);
            escribir(lista);
        }
    }

    @Override
    public List<CompasCliente> obtenerTodasLasCompras() {
        return leer();
    }
}