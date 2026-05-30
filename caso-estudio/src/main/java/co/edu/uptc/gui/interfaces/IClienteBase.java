package co.edu.uptc.gui.interfaces;
import co.edu.uptc.gui.modelo.Cliente;
import java.util.List;

public interface IClienteBase {
    public void ejecutarOperacionCliente(Cliente cliente);
    public List<Cliente> listarClientes();
}