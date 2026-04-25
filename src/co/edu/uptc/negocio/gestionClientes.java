package co.edu.uptc.negocio;

public class gestionClientes {

    public void registrar(clienteDto cliente) throws Exception {
        if (cliente == null) throw new Exception("No se tiene informacion del cliente");
        // TODO: implementar logica de registro
        System.out.println("Registrando cliente: " + cliente);
    }

    public void modificar(clienteDto cliente) throws Exception {
        if (cliente == null) throw new Exception("No se tiene informacion del cliente");
        // TODO: implementar logica de modificacion
        System.out.println("Modificando cliente: " + cliente);
    }

    public void inactivar(String codigo) throws Exception {
        if (codigo == null || codigo.isBlank()) throw new Exception("El codigo del cliente es requerido");
        // TODO: implementar logica de inactivacion
        System.out.println("Inactivando cliente con codigo: " + codigo);
    }

    public clienteDto buscar(String codigo) throws Exception {
        if (codigo == null || codigo.isBlank()) throw new Exception("El codigo del cliente es requerido");
        // TODO: implementar logica de busqueda
        System.out.println("Buscando cliente con codigo: " + codigo);
        return null;
    }
}