package co.uptc.edu.tienda.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.interfaces.IGestionProducto;
import co.uptc.edu.tienda.modelo.Producto;

public class LocalProducto implements IGestionProducto {

    private final Gson gson =
            new GsonBuilder().setPrettyPrinting().create();

    private final String RUTA = "productos.json";

    public LocalProducto() {
        super();
    }

    @Override
    public void guardar(Producto producto) {
        List<Producto> lista = listar();
        lista.add(producto);
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar producto: " + e.getMessage());
        }
    }

    @Override
    public void guardarTodos(List<Producto> productos) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(productos, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Producto producto) {
        List<Producto> lista = listar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigoProducto() == producto.getCodigoProducto()) {
                lista.set(i, producto);
                break;
            }
        }
        guardarTodos(lista); // ← cambia guardar por guardarTodos
    }

    @Override
    public void eliminar(int codigoProducto) {
        List<Producto> lista = listar();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigoProducto() == codigoProducto) {
                lista.get(i).setActivo(false); // ← inactivar, no eliminar físicamente
                break;
            }
        }
        guardarTodos(lista); // ← cambia guardar por guardarTodos
    }

    @Override
    public Producto buscar(int codigoProducto) {

        List<Producto> lista = listar();

        if (lista != null) {

            for (Producto p : lista) {

                if (p.getCodigoProducto() == codigoProducto) {

                    return p;
                }
            }
        }

        return null;
    }

    @Override
    public List<Producto> listar() {

        File archivo = new File(RUTA);

        if (!archivo.exists()) {

            return new ArrayList<Producto>();
        }

        try {

            FileReader reader = new FileReader(archivo);

            Type tipoLista =
                    new TypeToken<List<Producto>>() {
                    }.getType();

            List<Producto> lista =
                    gson.fromJson(reader, tipoLista);

            reader.close();

            if (lista == null) {

                return new ArrayList<Producto>();
            }

            return lista;

        } catch (IOException e) {

            System.out.println(
                    "Error al leer: " + e.getMessage());

            return new ArrayList<Producto>();
        }
    }
    
    public void cambiarEstado(int codigoProducto) {
    	Producto p = buscar(codigoProducto);

        if (p != null) {

            p.setActivo(true);

            actualizar(p);
        }
    }
}