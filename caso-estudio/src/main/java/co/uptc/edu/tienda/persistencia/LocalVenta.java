package co.uptc.edu.tienda.persistencia;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.uptc.edu.tienda.interfaces.IGestionVenta;
import co.uptc.edu.tienda.modelo.Venta;

public class LocalVenta implements IGestionVenta {

    private final Gson gson =
            new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final String RUTA = "ventas.json";

    @Override
    public void guardar(Venta venta) {
        List<Venta> lista = leerVentas();
        lista.add(venta);
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Venta> leerVentas() {

        File archivo =
                new File(RUTA);

        if(!archivo.exists()) {

            return new ArrayList<>();
        }

        try {

            FileReader reader =
                    new FileReader(archivo);

            Type tipoLista =
                    new TypeToken<List<Venta>>(){}.getType();

            List<Venta> lista =
                    gson.fromJson(reader, tipoLista);

            reader.close();

            if(lista == null) {

                return new ArrayList<>();
            }

            return lista;

        } catch(Exception e) {

            System.out.println(
                    "Error al leer ventas");

            e.printStackTrace();
        }

        return new ArrayList<>();
    }
    
    
   

    @Override
    public void actualizar(Venta venta) {
        List<Venta> lista = leerVentas();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNumeroFactura()
                    .equalsIgnoreCase(venta.getNumeroFactura())) {
                lista.set(i, venta);
                break;
            }
        }
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}