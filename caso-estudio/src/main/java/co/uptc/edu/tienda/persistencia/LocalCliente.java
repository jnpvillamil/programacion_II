package co.uptc.edu.tienda.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.interfaces.IGestionCliente;
import co.uptc.edu.tienda.modelo.Cliente;

public class LocalCliente implements IGestionCliente {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String RUTA = "clientes.json";

    public LocalCliente() {

        super();
    }

    @Override
    public void guardar(Cliente cliente) {
        List<Cliente> lista = leerClientes();
        lista.add(cliente);
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Cliente cliente) {
        List<Cliente> lista = leerClientes();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdCliente() == cliente.getIdCliente()) {
                lista.set(i, cliente);
                break;
            }
        }
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idCliente) {
        List<Cliente> lista = leerClientes();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdCliente() == idCliente) {
                lista.get(i).setEstado(EstadoEnum.INACTIVO);
                break;
            }
        }
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente buscar(int idCliente) {

        List<Cliente> lista = leerClientes();

        if (lista != null) {

            for (Cliente c : lista) {

                if (c.getIdCliente() == idCliente) {

                    return c;
                }
            }
        }

        return null;
    }

    @Override
    public List<Cliente> leerClientes() {

        File archivo = new File(RUTA);

        if (!archivo.exists()) {

            return new ArrayList<Cliente>();
        }

        try {

            FileReader reader = new FileReader(archivo);

            Type tipoLista =
                    new TypeToken<List<Cliente>>() {
                    }.getType();

            List<Cliente> lista =
                    gson.fromJson(reader, tipoLista);

            reader.close();

            if (lista == null) {

                return new ArrayList<Cliente>();
            }

            return lista;

        } catch (IOException e) {

            System.out.println(
                    "Error al leer: " +
                    e.getMessage()
            );

            return new ArrayList<Cliente>();
            
            
        }
    }
    
    @Override
    public void cambiarEstado(int codigoCliente, EstadoEnum nuevoEstado) {
        List<Cliente> lista = leerClientes();
        for (Cliente c : lista) {
            if (c.getIdCliente() == codigoCliente) {
                c.setEstado(nuevoEstado);
                break;
            }
        }
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.out.println("Error al cambiar estado cliente: " + e.getMessage());
        }
    }

}