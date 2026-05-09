package co.edu.uptc.negocio;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.Datos.Clientedt;

public class Cliente_negocio {
    
    private static Cliente_negocio instance;
    private List<Clientedt> listaClientes;


    private Cliente_negocio() {
        listaClientes = new ArrayList<>();
    }

    
    public static Cliente_negocio getInstance() {
        if (instance == null) {
            instance = new Cliente_negocio();
        }
        return instance;
    }

   
    public void agregarCliente(Clientedt cliente) {
        listaClientes.add(cliente);
    }

   
    public List<Clientedt> getListaClientes() {
        return listaClientes;
    }
    public void ActualizarCliente(Clientedt cliente) {

        for (int i = 0; i < listaClientes.size(); i++) {

            if (listaClientes.get(i).getNumerodocli().equals(cliente.getNumerodocli())) {

                listaClientes.set(i, cliente); 
                return;
            }
        }
    }public void desactivarCliente(String numerodoc) {

        for (Clientedt c : listaClientes) {
            if (c.getNumerodocli().equals(numerodoc)) {
                c.setActivo(false);
                return;
            }
        }
    }public void activarCliente(String numerodoc) {

        for (Clientedt c : listaClientes) {
            if (c.getNumerodocli().equals(numerodoc)) {
                c.setActivo(true);
                return;
            }
        }
    }
}