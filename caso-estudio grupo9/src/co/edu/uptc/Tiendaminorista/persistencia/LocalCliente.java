package co.edu.uptc.Tiendaminorista.persistencia;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson; // Librería para convertir objetos a texto JSON y viceversa [cite: 22]
import com.google.gson.reflect.TypeToken; // Ayuda a manejar listas genéricas en JSON [cite: 106]
import co.edu.uptc.Tiendaminorista.interfaces.IGestionCliente;
import co.edu.uptc.Tiendaminorista.modelo.Cliente;

public class LocalCliente implements IGestionCliente {
    private final String RUTA = "clientes.json"; 
    private final Gson gson = new Gson();

    
    private List<Cliente> leer() {
        
        try (FileReader reader = new FileReader(RUTA)) {
          
            Type tipoLista = new TypeToken<List<Cliente>>(){}.getType();
            List<Cliente> lista = gson.fromJson(reader, tipoLista);
            return lista != null ? lista : new ArrayList<>(); 
        } catch (IOException e) {
            return new ArrayList<>(); 
        }
    }

    private void escribir(List<Cliente> lista) {
        try (FileWriter writer = new FileWriter(RUTA)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public void guardar(Cliente cliente) {
        List<Cliente> lista = leer();
        if (cliente.getCodigo() == null || cliente.getCodigo().isEmpty()) {
            cliente.setCodigo("CLI" + (lista.size() + 1));
        }
        cliente.setActivo(true);
        lista.add(cliente);
        escribir(lista); 
    }

    @Override
    public List<Cliente> listar() {
        return leer(); 
    }

    @Override
    public void actualizar(Cliente cliente) {
        List<Cliente> lista = leer();
        for (int i = 0; i < lista.size(); i++) {
            
            if (lista.get(i).getCodigo().equals(cliente.getCodigo())) {
                lista.set(i, cliente); 
                break;
            }
        }
        escribir(lista); 
    }

    @Override
    public void desactivar(String codigo) {
        cambiarEstado(codigo, false); 
    }

    @Override
    public void activar(String codigo) {
        cambiarEstado(codigo, true); 
    }

   
    private void cambiarEstado(String codigo, boolean estado) {
        List<Cliente> lista = leer();
        for (Cliente c : lista) {
            if (c.getCodigo().equals(codigo)) {
                c.setActivo(estado); 
                break;
            }
        }
        escribir(lista);
    }
}