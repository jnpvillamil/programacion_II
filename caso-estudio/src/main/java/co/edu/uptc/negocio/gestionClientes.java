package co.edu.uptc.negocio;

import java.util.List;
import co.edu.uptc.interfaces.IGestionCliente;
import co.edu.uptc.negocio.dto.clienteDto;
import co.edu.uptc.persistencia.LocalCliente;

public class gestionClientes {

    private IGestionCliente iCliente;

    public gestionClientes() {
        this.iCliente = new LocalCliente();
    }

    public void registrar(clienteDto cliente) throws Exception {
        if (cliente == null) throw new Exception("No se tiene informacion del cliente");
        iCliente.guardar(cliente);
    }

    public void modificar(clienteDto cliente) throws Exception {
        if (cliente == null) throw new Exception("No se tiene informacion del cliente");
        iCliente.actualizar(cliente);
    }

    public void inactivar(int codigo) throws Exception {
        iCliente.eliminar(codigo);
    }

    public clienteDto buscar(int codigo) throws Exception {
        return iCliente.buscar(codigo);
    }

    public List<clienteDto> listar() {
        return iCliente.listar();
    }
}
