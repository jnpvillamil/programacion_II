package co.edu.uptc.negocio;

import co.edu.uptc.persistencia.PersistenciaCompra;

public class GestorCompra {
    private PersistenciaCompra persistenciaCompra;

    public GestorCompra(PersistenciaCompra persistenciaCompra) {
        this.persistenciaCompra = persistenciaCompra;
    }

    public void registrarCompra(Compra c) throws Exception {
        if (c.getDetalles().isEmpty()) {
            throw new Exception("La compra no tiene productos.");
        }

        //Calcular el total
        c.calcularTotal();

        //Suma el stock de cada producto comprado
        for(DetalleTransaccion dt : c.getDetalles()) {
            dt.getProducto().actualizarStock(dt.getCantidad()); 
        }

        //Generar el movimiento contable
        c.generarMovimientoContable();

        //Guardar en la "base de datos" (Persistencia)
        persistenciaCompra.guardar(c);
    }
}