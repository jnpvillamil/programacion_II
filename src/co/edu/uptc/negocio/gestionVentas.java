package co.edu.uptc.negocio;

public class gestionVentas {

    public void registrar(ventaDto venta) throws Exception {
        if (venta == null) throw new Exception("No se tiene informacion de la venta");
        // TODO: implementar logica de registro
        System.out.println("Registrando venta: " + venta);
    }

    public void anular(String numeroFactura) throws Exception {
        if (numeroFactura == null || numeroFactura.isBlank()) throw new Exception("El numero de factura es requerido");
        // TODO: implementar logica de anulacion
        System.out.println("Anulando venta con factura: " + numeroFactura);
    }

    public ventaDto buscar(String numeroFactura) throws Exception {
        if (numeroFactura == null || numeroFactura.isBlank()) throw new Exception("El numero de factura es requerido");
        // TODO: implementar logica de busqueda
        System.out.println("Buscando venta con factura: " + numeroFactura);
        return null;
    }
}