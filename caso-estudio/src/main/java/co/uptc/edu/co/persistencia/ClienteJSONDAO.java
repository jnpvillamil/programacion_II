package co.uptc.edu.co.persistencia;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.uptc.edu.co.interfaces.ClienteDAO;
import co.uptc.edu.co.modelo.Cliente;

public class ClienteJSONDAO implements ClienteDAO {

    private static final String RUTA = "clientes.json";

    private final Gson gson;

    public ClienteJSONDAO() {

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void guardarCliente(Cliente cliente)
            throws Exception {

        List<Cliente> clientes = listarClientes();

        clientes.add(cliente);

        guardarLista(clientes);
    }

    @Override
    public void actualizarCliente(Cliente cliente)
            throws Exception {

        List<Cliente> clientes = listarClientes();

        for (int i = 0; i < clientes.size(); i++) {

            if (clientes.get(i)
                    .getCodigo()
                    .equalsIgnoreCase(cliente.getCodigo())) {

                clientes.set(i, cliente);

                guardarLista(clientes);

                return;
            }
        }

        throw new Exception(
            "No se encontró el cliente a actualizar."
        );
    }

    @Override
    public Cliente buscarPorCodigo(String codigo)
            throws Exception {

        List<Cliente> clientes = listarClientes();

        for (Cliente cliente : clientes) {

            if (cliente.getCodigo()
                    .equalsIgnoreCase(codigo)) {

                return cliente;
            }
        }

        return null;
    }

    @Override
    public List<Cliente> listarClientes()
            throws Exception {

        try (FileReader reader = new FileReader(RUTA)) {

            Type tipoLista =
                    new TypeToken<List<Cliente>>() {}.getType();

            List<Cliente> clientes =
                    gson.fromJson(reader, tipoLista);

            return clientes != null
                    ? clientes
                    : new ArrayList<>();

        } catch (java.io.FileNotFoundException e) {

            return new ArrayList<>();

        } catch (Exception e) {

            throw new Exception(
                "Error al leer clientes JSON: "
                + e.getMessage()
            );
        }
    }

    private void guardarLista(List<Cliente> clientes)
            throws Exception {

        try (FileWriter writer = new FileWriter(RUTA)) {

            gson.toJson(clientes, writer);
        }
    }
}