package co.edu.uptc.negocio;

public class gestionProductos {

    public void registrar(productoDto producto) throws Exception {
        if (producto == null) throw new Exception("No se tiene informacion del producto");
        // TODO: implementar logica de registro
        System.out.println("Registrando producto: " + producto);
    }

    public void modificar(productoDto producto) throws Exception {
        if (producto == null) throw new Exception("No se tiene informacion del producto");
        // TODO: implementar logica de modificacion
        System.out.println("Modificando producto: " + producto);
    }

    public void inactivar(String codigo) throws Exception {
        if (codigo == null || codigo.isBlank()) throw new Exception("El codigo del producto es requerido");
        // TODO: implementar logica de inactivacion
        System.out.println("Inactivando producto con codigo: " + codigo);
    }

    public productoDto buscar(String codigo) throws Exception {
        if (codigo == null || codigo.isBlank()) throw new Exception("El codigo del producto es requerido");
        // TODO: implementar logica de busqueda
        System.out.println("Buscando producto con codigo: " + codigo);
        return null;
    }
}