package co.edu.uptc.interfaces;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.modelo.Cliente;
import java.util.List;

public interface RepositorioCliente extends Repositorio<Cliente> {
    List<ClienteResumenDTO> listarResumen();
}
