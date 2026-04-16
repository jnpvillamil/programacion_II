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

        // 1. Calcular el total
        c.calcularTotal();

        // 2. Afectar Inventario (Suma el stock de cada producto comprado)
        for(DetalleTransaccion dt : c.getDetalles()) {
            dt.getProducto().actualizarStock(dt.getCantidad()); 
        }

        // 3. Generar el movimiento contable (Polimorfismo)
        c.generarMovimientoContable();

        // 4. Guardar en la "base de datos" (Persistencia)
        persistenciaCompra.guardar(c);
    }
}