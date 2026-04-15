package co.edu.uptc.negocio;

public class gestionCompras {

    public void registrar(compraDto compra) throws Exception {
        if (compra == null) throw new Exception("No se tiene informacion de la compra");
        // TODO: __implementar__ __logica__ __de__ __registro__
        System.out.println("Registrando compra: " + compra);
    }

    public compraDto buscar(String numeroFactura) throws Exception {
        if (numeroFactura == null || numeroFactura.isBlank())
            throw new Exception("El numero de factura del proveedor es requerido");
        // TODO: __implementar__ __logica__ __de__ __busqueda__
        System.out.println("Buscando compra con factura: " + numeroFactura);
        return null;
    }
}