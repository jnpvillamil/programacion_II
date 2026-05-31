package co.uptc.edu.tienda.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.enums.TipoMovimientoContable;
import co.uptc.edu.tienda.interfaces.IGestionContable;
import co.uptc.edu.tienda.modelo.Compra;
import co.uptc.edu.tienda.modelo.MovimientoContable;
import co.uptc.edu.tienda.modelo.Venta;

public class GestionContable {

    private IGestionContable iContable;
    

    public GestionContable(IGestionContable iContable) {
        this.iContable = iContable;
        
    }

    public void registrarVenta(Venta venta) {
        System.out.println("=== registrarVenta ===");
        System.out.println("  Factura:   " + venta.getNumeroFactura());
        System.out.println("  Total:     " + venta.getTotal());
        System.out.println("  Impuestos: " + venta.getImpuestos());
        System.out.println("  Base:      " + (venta.getTotal() - venta.getImpuestos()));

        String fecha;
        try {
            fecha = venta.getFechaHora().substring(0, 10);
        } catch (Exception e) {
            fecha = java.time.LocalDate.now().toString();
        }

        String factura = venta.getNumeroFactura();
        double base = venta.getTotal() - venta.getImpuestos();
        double iva = venta.getImpuestos();

        guardar(new MovimientoContable(
                fecha,
                TipoMovimientoContable.INGRESO,
                "Caja",
                venta.getTotal(),
                "Venta factura " + factura,
                factura));

        guardar(new MovimientoContable(
                fecha,
                TipoMovimientoContable.INGRESO,
                "Ingresos por ventas",
                base,
                "Venta factura " + factura,
                factura));

        if (iva > 0) {
            guardar(new MovimientoContable(
                    fecha,
                    TipoMovimientoContable.INGRESO,
                    "IVA generado",
                    iva,
                    "IVA venta factura " + factura,
                    factura));
        }

        System.out.println("=== registrarVenta FIN ===");
    }

    public void registrarCompra(Compra compra) {
        System.out.println("=== registrarCompra ===");
        System.out.println("  Factura: " + compra.getNumeroFactura());
        System.out.println("  Total:   " + compra.getTotal());

        String fecha;
        try {
            fecha = compra.getFechaCompra().substring(0, 10);
        } catch (Exception e) {
            fecha = java.time.LocalDate.now().toString();
        }

        String factura = compra.getNumeroFactura();

        guardar(new MovimientoContable(
                fecha,
                TipoMovimientoContable.EGRESO,
                "Inventario",
                compra.getTotal(),
                "Compra factura " + factura,
                factura));

        guardar(new MovimientoContable(
                fecha,
                TipoMovimientoContable.EGRESO,
                "Proveedores",
                compra.getTotal(),
                "Compra factura " + factura,
                factura));

        System.out.println("=== registrarCompra FIN ===");
    }

    public List<MovimientoContable> consultarPorCuentaYPeriodo(
            String cuenta, String desde, String hasta) {
        List<MovimientoContable> resultado = new ArrayList<>();
        for (MovimientoContable m : iContable.cargar()) { // ← lee de BD
            boolean cumpleCuenta = cuenta == null || cuenta.isEmpty()
                    || m.getCuentaContable().equalsIgnoreCase(cuenta);
            boolean cumpleDesde = desde == null || desde.isEmpty()
                    || m.getFecha().compareTo(desde) >= 0;
            boolean cumpleHasta = hasta == null || hasta.isEmpty()
                    || m.getFecha().compareTo(hasta) <= 0;
            if (cumpleCuenta && cumpleDesde && cumpleHasta) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public List<MovimientoContable> listarMovimientos() {
        return iContable.cargar();
    }

    private void guardar(MovimientoContable m) {
        try {
            iContable.guardar(m);
        } catch (Exception e) {
            System.out.println("ERROR al guardar movimiento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}