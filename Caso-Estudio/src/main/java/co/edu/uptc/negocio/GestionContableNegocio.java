package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.IGestionContable;
import co.edu.uptc.modelo.AsientoContable;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.enums.CuentaContable;
import co.edu.uptc.enums.TipoMovimientoContable;
import co.edu.uptc.Dtos.ReporteContableDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class GestionContableNegocio implements IGestionContable {

    private final IGestionContable persistencia;

    public GestionContableNegocio(IGestionContable persistencia) {
        this.persistencia = persistencia;
    }

    // ========== IMPLEMENTACIÓN DE MÉTODOS DE LA INTERFAZ ==========

    @Override
    public void registrarAsientoVenta(Venta venta) {
        validarVentaNula(venta);
        validarVentaParaAsiento(venta);
        
        if ("Anulada".equals(venta.getEstado())) {
            throw new IllegalStateException("No se puede registrar un asiento para una venta anulada.");
        }
        
        persistencia.registrarAsientoVenta(venta);
        System.out.println(" Asiento contable registrado para venta: " + venta.getNumeroFactura());
    }

    @Override
    public void registrarAsientoCompra(Compra compra) {
        validarCompraNula(compra);
        validarCompraParaAsiento(compra);
        
        if ("Anulada".equals(compra.getEstado())) {
            throw new IllegalStateException("No se puede registrar un asiento para una compra anulada.");
        }
        
        persistencia.registrarAsientoCompra(compra);
        System.out.println(" Asiento contable registrado para compra: " + compra.getNumeroFacturaProveedor());
    }

    @Override
    public void registrarAsientoAjuste(String descripcion, Map<CuentaContable, Double> debitos,
                                        Map<CuentaContable, Double> creditos, String referencia) {
        validarDescripcion(descripcion);
        validarMovimientosContables(debitos, creditos);
        validarPartidaDoble(debitos, creditos);
        
        persistencia.registrarAsientoAjuste(descripcion, debitos, creditos, referencia);
        System.out.println(" Asiento de ajuste registrado correctamente.");
    }

    @Override
    public List<AsientoContable> listarAsientos() {
        return persistencia.listarAsientos();
    }

    @Override
    public List<AsientoContable> listarAsientosPorFecha(LocalDate fecha) {
        validarFecha(fecha);
        return persistencia.listarAsientosPorFecha(fecha);
    }

    @Override
    public List<AsientoContable> listarAsientosPorPeriodo(LocalDate inicio, LocalDate fin) {
        validarPeriodo(inicio, fin);
        return persistencia.listarAsientosPorPeriodo(inicio, fin);
    }

    @Override
    public List<MovimientoContable> listarMovimientosPorCuenta(CuentaContable cuenta) {
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta contable no puede ser nula.");
        }
        return persistencia.listarMovimientosPorCuenta(cuenta);
    }

    @Override
    public double calcularSaldoCuenta(CuentaContable cuenta, LocalDate fechaCorte) {
        if (cuenta == null) {
            throw new IllegalArgumentException("La cuenta contable no puede ser nula.");
        }
        validarFecha(fechaCorte);
        return persistencia.calcularSaldoCuenta(cuenta, fechaCorte);
    }

    @Override
    public Map<CuentaContable, Double> generarBalanceGeneral(LocalDate fechaCorte) {
        validarFecha(fechaCorte);
        Map<CuentaContable, Double> balance = persistencia.generarBalanceGeneral(fechaCorte);
        
        double activos = 0;
        double pasivos = 0;
        double patrimonio = 0;
        
        System.out.println("\n=== SALDOS POR CUENTA ===");
        
        for (Map.Entry<CuentaContable, Double> entry : balance.entrySet()) {
            String nombre = entry.getKey().getNombre();
            String tipo = entry.getKey().getTipo();
            double saldo = entry.getValue();
            
            System.out.printf("%s (%s): $%,.2f\n", nombre, tipo, saldo);
            
            if ("Activo".equals(tipo)) {
                activos += saldo;
            } else if ("Pasivo".equals(tipo)) {
                pasivos += saldo;
            } else if ("Patrimonio".equals(tipo)) {
                patrimonio += saldo;
            }
        }
        
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL ACTIVOS: $%,.2f\n", activos);
        System.out.printf("TOTAL PASIVOS: $%,.2f\n", pasivos);
        System.out.printf("TOTAL PATRIMONIO: $%,.2f\n", patrimonio);
        System.out.printf("DIFERENCIA: $%,.2f\n", activos - (pasivos + patrimonio));
        System.out.println("========================================\n");
        
        if (Math.abs(activos - (pasivos + patrimonio)) > 0.01) {
            System.err.println("⚠️ Advertencia: El balance general NO está cuadrado.");
            System.err.println("   Diferencia: $" + (activos - (pasivos + patrimonio)));
        }
        
        return balance;
    }

    @Override
    public Map<String, Double> generarEstadoResultados(LocalDate inicio, LocalDate fin) {
        validarPeriodo(inicio, fin);
        return persistencia.generarEstadoResultados(inicio, fin);
    }

    @Override
    public ReporteContableDTO generarReporteContablePeriodo(LocalDate inicio, LocalDate fin) {
        validarPeriodo(inicio, fin);
        return persistencia.generarReporteContablePeriodo(inicio, fin);
    }

    @Override
    public Map<String, Double> obtenerResumenContable(LocalDate inicio, LocalDate fin) {
        validarPeriodo(inicio, fin);
        return persistencia.obtenerResumenContable(inicio, fin);
    }

    // ========== MÉTODOS ADICIONALES (para comodidad desde la interfaz) ==========

    public void registrarAsientoVentaConValidacion(Venta venta) {
        registrarAsientoVenta(venta);
    }

    public void registrarAsientoCompraConValidacion(Compra compra) {
        registrarAsientoCompra(compra);
    }

    // ========== MÉTODOS PRIVADOS DE VALIDACIÓN ==========

    private void validarVentaNula(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser nula.");
        }
    }

    private void validarCompraNula(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La compra no puede ser nula.");
        }
    }

    private void validarVentaParaAsiento(Venta venta) {
        if (venta.getNumeroFactura() == null || venta.getNumeroFactura().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de factura es obligatorio.");
        }
        if (venta.getTotal() <= 0) {
            throw new IllegalArgumentException("El total de la venta debe ser mayor a 0.");
        }
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("La venta debe tener un cliente asociado.");
        }
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un producto.");
        }
    }

    private void validarCompraParaAsiento(Compra compra) {
        if (compra.getNumeroFacturaProveedor() == null || compra.getNumeroFacturaProveedor().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de factura del proveedor es obligatorio.");
        }
        if (compra.getTotal() <= 0) {
            throw new IllegalArgumentException("El total de la compra debe ser mayor a 0.");
        }
        if (compra.getProveedor() == null) {
            throw new IllegalArgumentException("La compra debe tener un proveedor asociado.");
        }
        if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un producto.");
        }
    }

    private void validarDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del asiento es obligatoria.");
        }
        if (descripcion.length() < 5) {
            throw new IllegalArgumentException("La descripción debe tener al menos 5 caracteres.");
        }
    }

    private void validarMovimientosContables(Map<CuentaContable, Double> debitos,
                                              Map<CuentaContable, Double> creditos) {
        if (debitos == null || debitos.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un débito en el asiento.");
        }
        if (creditos == null || creditos.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un crédito en el asiento.");
        }
        
        for (Double valor : debitos.values()) {
            if (valor <= 0) {
                throw new IllegalArgumentException("Los valores de débito deben ser mayores a 0.");
            }
        }
        for (Double valor : creditos.values()) {
            if (valor <= 0) {
                throw new IllegalArgumentException("Los valores de crédito deben ser mayores a 0.");
            }
        }
    }

    private void validarPartidaDoble(Map<CuentaContable, Double> debitos,
                                      Map<CuentaContable, Double> creditos) {
        double sumaDebitos = debitos.values().stream().mapToDouble(Double::doubleValue).sum();
        double sumaCreditos = creditos.values().stream().mapToDouble(Double::doubleValue).sum();
        
        if (Math.abs(sumaDebitos - sumaCreditos) > 0.01) {
            throw new IllegalStateException(
                String.format("La partida no está balanceada. Débitos: %.2f, Créditos: %.2f", 
                sumaDebitos, sumaCreditos));
        }
    }

    private void validarFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fin) {
        validarFecha(inicio);
        validarFecha(fin);
        
        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser mayor a la fecha de fin.");
        }
        
        if (inicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
    }
}