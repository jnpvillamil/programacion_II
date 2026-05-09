package co.edu.uptc.Tiendaminorista.persistencia;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import co.edu.uptc.Tiendaminorista.interfaces.IGestionProveedor;
import co.edu.uptc.Tiendaminorista.modelo.Proveedor;

public class LocalProveedor implements IGestionProveedor {
    private final String RUTA = "proveedores.json";
    private final Gson gson = new Gson();

    private List<Proveedor> leer() {
        try (FileReader reader = new FileReader(RUTA)) {
            Type tipoLista = new TypeToken<List<Proveedor>>(){}.getType();
            List<Proveedor> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void escribir(List<Proveedor> lista) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void guardar(Proveedor proveedor) {
        List<Proveedor> lista = leer();
   
        if (proveedor.getCodigo() == null) {
            proveedor.setCodigo("PRO" + (lista.size() + 1));
        }
        proveedor.setActivo(true);
        lista.add(proveedor);
        escribir(lista);
    }

    @Override
    public List<Proveedor> listar() {
        return leer();
    }

    @Override
    public void actualizar(Proveedor proveedor) {
        List<Proveedor> lista = leer();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equals(proveedor.getCodigo())) {
                lista.set(i, proveedor);
                break;
            }
        }
        escribir(lista);
    }

    @Override
    public void desactivar(String codigo) {
        List<Proveedor> lista = leer();
              for (Proveedor p : lista) {
            if (p.getCodigo().equals(codigo)) {
                p.setActivo(false);
                break;
            }
        }
        escribir(lista);
    }

    @Override
    public void activar(String codigo) {
        List<Proveedor> lista = leer();
        for (Proveedor p : lista) {
            if (p.getCodigo().equals(codigo)) {
                p.setActivo(true);
                break;
            }
        }
        escribir(lista);
    }
}