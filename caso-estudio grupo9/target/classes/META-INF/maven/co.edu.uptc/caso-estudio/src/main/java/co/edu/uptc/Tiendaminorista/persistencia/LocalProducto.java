package co.edu.uptc.Tiendaminorista.persistencia;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.Tiendaminorista.interfaces.IGestionProducto;
import co.edu.uptc.Tiendaminorista.modelo.Producto;

public class LocalProducto implements IGestionProducto {
    private final String RUTA = "productos.json";
    private final Gson gson = new Gson();

    private List<Producto> leer() {
        try (FileReader reader = new FileReader(RUTA)) {
            Type tipoLista = new TypeToken<List<Producto>>(){}.getType();
            List<Producto> res = gson.fromJson(reader, tipoLista);
            return res != null ? res : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void escribir(List<Producto> lista) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Producto producto) {
        List<Producto> lista = leer();
        producto.setCodigo("PRD" + (lista.size() + 1));
        producto.setActivo(true);
        lista.add(producto);
        escribir(lista);
    }

    @Override
    public void registrarMovimientoInventario(String codigo, int cantidad) {
        List<Producto> lista = leer();
        for (Producto p : lista) {
            if (p.getCodigo().equals(codigo)) {
                p.setStockActual(p.getStockActual() + cantidad);
                break;
            }
        }
        escribir(lista); 
    }

    @Override
    public List<Producto> listar() {
        return leer();
    }

    @Override
    public void actualizar(Producto producto) {
        List<Producto> lista = leer();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equals(producto.getCodigo())) {
                lista.set(i, producto);
                break;
            }
        }
        escribir(lista);
    }

    @Override
    public void desactivar(String codigo) {
        List<Producto> lista = leer();
        for (Producto p : lista) {
            if (p.getCodigo().equals(codigo)) {
                p.setActivo(false);
                break;
            }
        }
        escribir(lista);
    }

    @Override
    public void activar(String codigo) {
        List<Producto> lista = leer();
        for (Producto p : lista) {
            if (p.getCodigo().equals(codigo)) {
                p.setActivo(true);
                break;
            }
        }
        escribir(lista);
    }
}