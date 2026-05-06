package co.edu.uptc.caso.estudio;

import co.edu.uptc.caso.estudio.dao.ClienteDAOImpl;
import co.edu.uptc.negocio.dto.clienteDto;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        
        String ruta = "clientes.json";
        ClienteDAOImpl dao = new ClienteDAOImpl();

        // Crear lista de clientes
        List<clienteDto> clientes = new ArrayList<>();
        
        clienteDto c1 = new clienteDto();
        c1.setNombre("Juan Perez");
        c1.setTelefono(3101234567L);
        c1.setDireccion("Calle 10 #5-20");
        
        clienteDto c2 = new clienteDto();
        c2.setNombre("Maria Lopez");
        c2.setTelefono(3209876543L);
        c2.setDireccion("Carrera 7 #15-30");

        clientes.add(c1);
        clientes.add(c2);

        // Guardar en JSON
        dao.guardarClientes(clientes, ruta);

        // Leer del JSON
        List<clienteDto> leidos = dao.leerClientes(ruta);
        System.out.println("\nClientes leídos del JSON:");
        leidos.forEach(System.out::println);
    }
}