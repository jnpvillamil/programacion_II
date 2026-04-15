package negocio;

import java.util.ArrayList;
import modelo.Cliente;

public class NegocioCliente {

    private ArrayList<Cliente> lista;

    public NegocioCliente() {
        lista = new ArrayList<>();
    }

    public void agregarCliente(Cliente c) {
        lista.add(c);
    }

    public Cliente buscarCliente(String codigo) {

        for (Cliente c : lista) {
            if (c.getCodigo().equals(codigo)) {
                return c;
            }
        }
        return null;
    }

    public void inactivarCliente(String codigo) {

        for (Cliente c : lista) {
            if (c.getCodigo().equals(codigo)) {
                c.setActivo(false);
                break;
            }
        }
    }
    public boolean existeCliente(String codigo) {

        for (Cliente c : lista) {
            if (c.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Cliente> getLista() {
        return lista;
    }
}
