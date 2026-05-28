package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.modelo.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaClienteTXT implements Repositorio<Cliente> {

    private final String RUTA_ARCHIVO = "clientes.txt";
    private final String SEPARADOR = ";";

    public PersistenciaClienteTXT() {
        File archivo = new File(RUTA_ARCHIVO);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear archivo de clientes: " + e.getMessage());
            }
        }
    }

    @Override
    public void guardar(Cliente cliente) {
        List<Cliente> clientes = listar();
        clientes.add(cliente);
        sobrescribirArchivo(clientes);
    }

    @Override
    public void eliminar(String id) {
        List<Cliente> clientes = listar();
        clientes.removeIf(c -> c.getCodigoCliente().equals(id));
        sobrescribirArchivo(clientes);
    }

    @Override
    public Cliente buscarPorId(String id) {
        for (Cliente c : listar()) {
            if (c.getCodigoCliente().equals(id) || c.getIdentificacion().equals(id)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(SEPARADOR);
                    clientes.add(new Cliente(
                            datos[0], datos[1], datos[2], datos[3],
                            datos[4],
                            TipoIdentificacion.desdeTexto(datos[5]),
                            TipoCliente.desdeTexto(datos[6]),
                            Boolean.parseBoolean(datos[7])));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer clientes TXT: " + e.getMessage());
        }
        return clientes;
    }

    @Override
    public void actualizar(Cliente clienteActualizado) {
        List<Cliente> clientes = listar();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCodigoCliente().equals(clienteActualizado.getCodigoCliente())) {
                clientes.set(i, clienteActualizado);
                break;
            }
        }
        sobrescribirArchivo(clientes);
    }

    private void sobrescribirArchivo(List<Cliente> clientes) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, false))) {
            for (Cliente c : clientes) {
                escritor.println(
                        c.getNombre() + SEPARADOR +
                        c.getIdentificacion() + SEPARADOR +
                        c.getDireccion() + SEPARADOR +
                        c.getTelefono() + SEPARADOR +
                        c.getCodigoCliente() + SEPARADOR +
                        (c.getTipoIdentificacion() != null ? c.getTipoIdentificacion().name() : TipoIdentificacion.CC.name()) + SEPARADOR +
                        (c.getTipoCliente() != null ? c.getTipoCliente().name() : TipoCliente.MINORISTA.name()) + SEPARADOR +
                        c.isActivo());
            }
        } catch (IOException e) {
            System.err.println("Error al escribir clientes TXT: " + e.getMessage());
        }
    }
}
