package co.edu.uptc.negocio;

public class gestionProveedor {

    public void registrar(proveedorDto proveedor) throws Exception {
        if (proveedor == null) throw new Exception("No se tiene informacion del proveedor");
        // TODO: implementar logica de registro
        System.out.println("Registrando proveedor: " + proveedor);
    }

    public void modificar(proveedorDto proveedor) throws Exception {
        if (proveedor == null) throw new Exception("No se tiene informacion del proveedor");
        // TODO: implementar logica de modificacion
        System.out.println("Modificando proveedor: " + proveedor);
    }

    public void inactivar(String codigo) throws Exception {
        if (codigo == null || codigo.isBlank()) throw new Exception("El codigo del proveedor es requerido");
        // TODO: implementar logica de inactivacion
        System.out.println("Inactivando proveedor con codigo: " + codigo);
    }

    public proveedorDto buscar(String codigo) throws Exception {
        if (codigo == null || codigo.isBlank()) throw new Exception("El codigo del proveedor es requerido");
        // TODO: implementar logica de busqueda
        System.out.println("Buscando proveedor con codigo: " + codigo);
        return null;
    }
}