package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.GestionContable;
import co.edu.uptc.modelo.AsientoContable;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.DetalleVenta;
import co.edu.uptc.negocio.ReporteContableDTO;
import co.edu.uptc.enums.TipoMovimientoContable;
import co.edu.uptc.enums.CuentaContable;
import co.edu.uptc.config.TiendaConfig;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LocalContable implements GestionContable {
    
    private Map<String, AsientoContable> asientos;
    private int contadorAsientos;
    
    public LocalContable() {
        asientos = new HashMap<>();
        contadorAsientos = 1;
    }
    
    @Override
    public void registrarAsientoVenta(Venta venta) {
        String codigoAsiento = "VENTA-" + venta.getNumeroFactura();
        AsientoContable asiento = new AsientoContable(
            codigoAsiento,
            "Registro de venta factura " + venta.getNumeroFactura(),
            venta.getNumeroFactura(),
            "admin"
        );
        
        double valorVenta = venta.getTotal();
        double iva = venta.getIva();
        double baseGravable = venta.getSubtotal();
        
        // PARTIDA DOBLE PARA VENTA:
        // Débito: Caja o Bancos (según forma de pago)
        CuentaContable cuentaEfectivo = venta.getFormaPago().equals("Efectivo") ? 
            CuentaContable.CAJA : CuentaContable.BANCOS;
        asiento.agregarMovimiento(TipoMovimientoContable.INGRESO, cuentaEfectivo, 
                                    valorVenta, "Ingreso por venta");
        
        // Crédito: Ingresos por Ventas
        asiento.agregarMovimiento(TipoMovimientoContable.EGRESO, CuentaContable.INGRESOS_VENTAS,
                                    baseGravable, "Ingreso por venta neto");
        
        // Crédito: IVA Generado
        asiento.agregarMovimiento(TipoMovimientoContable.EGRESO, CuentaContable.IVA_GENERADO,
                                    iva, "IVA generado por venta");
        
        // Registrar costo de ventas (disminución de inventario)
        for(DetalleVenta detalle : venta.getDetalles()) {
            double costoVenta = detalle.getCantidad() * detalle.getProducto().getPrecioCompra();
            
            // Débito: Costo de Ventas
            asiento.agregarMovimiento(TipoMovimientoContable.INGRESO, CuentaContable.COSTO_VENTAS,
                                        costoVenta, "Costo de " + detalle.getProducto().getNombre());
            
            // Crédito: Inventario (disminuye)
            asiento.agregarMovimiento(TipoMovimientoContable.EGRESO, CuentaContable.INVENTARIO,
                                        costoVenta, "Salida de inventario " + detalle.getProducto().getNombre());
        }
        
        asientos.put(codigoAsiento, asiento);
    }
    
    @Override
    public void registrarAsientoCompra(Compra compra) {
        String codigoAsiento = "COMPRA-" + compra.getNumeroFacturaProveedor();
        AsientoContable asiento = new AsientoContable(
            codigoAsiento,
            "Registro de compra factura " + compra.getNumeroFacturaProveedor(),
            compra.getNumeroFacturaProveedor(),
            "admin"
        );
        
        double valorCompra = compra.getTotal();
        double iva = compra.getIva();
        double baseGravable = compra.getSubtotal();
        
        // PARTIDA DOBLE PARA COMPRA:
        // Débito: Inventario (aumenta)
        asiento.agregarMovimiento(TipoMovimientoContable.INGRESO, CuentaContable.INVENTARIO,
                                    baseGravable, "Entrada de inventario por compra");
        
        // Débito: IVA Descontable
        asiento.agregarMovimiento(TipoMovimientoContable.INGRESO, CuentaContable.IVA_DESCONTABLE,
                                    iva, "IVA descontable por compra");
        
        // Crédito: Proveedores ( Caja/Bancos según forma de pago)
        asiento.agregarMovimiento(TipoMovimientoContable.EGRESO, CuentaContable.PROVEEDORES,
                                    valorCompra, "Cuenta por pagar a " + compra.getProveedor().getRazonSocial());
        
        asientos.put(codigoAsiento, asiento);
    }
    
    @Override
    public void registrarAsientoAjuste(String descripcion, Map<CuentaContable, Double> debitos,
                                        Map<CuentaContable, Double> creditos, String referencia) {
        String codigoAsiento = "AJUSTE-" + System.currentTimeMillis();
        AsientoContable asiento = new AsientoContable(codigoAsiento, descripcion, referencia, "admin");
        
        for(Map.Entry<CuentaContable, Double> entry : debitos.entrySet()) {
            asiento.agregarMovimiento(TipoMovimientoContable.INGRESO, entry.getKey(),
                                        entry.getValue(), "Débito por ajuste");
        }
        
        for(Map.Entry<CuentaContable, Double> entry : creditos.entrySet()) {
            asiento.agregarMovimiento(TipoMovimientoContable.EGRESO, entry.getKey(),
                                        entry.getValue(), "Crédito por ajuste");
        }
        
        asientos.put(codigoAsiento, asiento);
    }
    
    @Override
    public List<AsientoContable> listarAsientos() {
        return new ArrayList<>(asientos.values());
    }
    
    @Override
    public List<AsientoContable> listarAsientosPorFecha(LocalDate fecha) {
        return asientos.values().stream()
            .filter(a -> a.getFecha().toLocalDate().equals(fecha))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<AsientoContable> listarAsientosPorPeriodo(LocalDate inicio, LocalDate fin) {
        return asientos.values().stream()
            .filter(a -> {
                LocalDate fechaAsiento = a.getFecha().toLocalDate();
                return (fechaAsiento.isEqual(inicio) || fechaAsiento.isAfter(inicio)) &&
                       (fechaAsiento.isEqual(fin) || fechaAsiento.isBefore(fin));
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public List<MovimientoContable> listarMovimientosPorCuenta(CuentaContable cuenta) {
        List<MovimientoContable> movimientos = new ArrayList<>();
        for(AsientoContable asiento : asientos.values()) {
            for(MovimientoContable m : asiento.getMovimientos()) {
                if(m.getCuenta() == cuenta) {
                    movimientos.add(m);
                }
            }
        }
        return movimientos;
    }
    
    @Override
    public double calcularSaldoCuenta(CuentaContable cuenta, LocalDate fechaCorte) {
        double saldo = 0;
        for(AsientoContable asiento : asientos.values()) {
            if(asiento.getFecha().toLocalDate().isAfter(fechaCorte)) continue;
            
            for(MovimientoContable m : asiento.getMovimientos()) {
                if(m.getCuenta() == cuenta) {
                    if(m.getTipoMovimiento() == TipoMovimientoContable.INGRESO) {
                        saldo += m.getValor();
                    } else {
                        saldo -= m.getValor();
                    }
                }
            }
        }
        return saldo;
    }
    
    @Override
    public Map<CuentaContable, Double> generarBalanceGeneral(LocalDate fechaCorte) {
        Map<CuentaContable, Double> balance = new HashMap<>();
        
        for(CuentaContable cuenta : CuentaContable.values()) {
            double saldo = calcularSaldoCuenta(cuenta, fechaCorte);
            if(Math.abs(saldo) > 0.01) {
                balance.put(cuenta, saldo);
            }
        }
        
        return balance;
    }
    
    @Override
    public Map<String, Double> generarEstadoResultados(LocalDate inicio, LocalDate fin) {
        Map<String, Double> resultados = new HashMap<>();
        
        double ingresos = 0;
        double costos = 0;
        double gastos = 0;
        
        for(AsientoContable asiento : listarAsientosPorPeriodo(inicio, fin)) {
            for(MovimientoContable m : asiento.getMovimientos()) {
                if(m.getCuenta() == CuentaContable.INGRESOS_VENTAS) {
                    ingresos += m.getValor();
                } else if(m.getCuenta() == CuentaContable.COSTO_VENTAS) {
                    costos += m.getValor();
                }
            }
        }
        
        double utilidadBruta = ingresos - costos;
        double utilidadNeta = utilidadBruta - gastos;
        
        resultados.put("Ingresos Totales", ingresos);
        resultados.put("Costo de Ventas", costos);
        resultados.put("Utilidad Bruta", utilidadBruta);
        resultados.put("Gastos", gastos);
        resultados.put("Utilidad Neta", utilidadNeta);
        
        return resultados;
    }
    
    @Override
    public ReporteContableDTO generarReporteContablePeriodo(LocalDate inicio, LocalDate fin) {
        ReporteContableDTO reporte = new ReporteContableDTO();
        reporte.setFechaInicio(inicio);
        reporte.setFechaFin(fin);
        
        double ingresos = 0;
        double egresos = 0;
        double ivaGenerado = 0;
        double ivaDescontable = 0;
        
        for(AsientoContable asiento : listarAsientosPorPeriodo(inicio, fin)) {
            for(MovimientoContable m : asiento.getMovimientos()) {
                if(m.getCuenta() == CuentaContable.INGRESOS_VENTAS) {
                    ingresos += m.getValor();
                } else if(m.getCuenta() == CuentaContable.COSTO_VENTAS) {
                    egresos += m.getValor();
                } else if(m.getCuenta() == CuentaContable.IVA_GENERADO) {
                    ivaGenerado += m.getValor();
                } else if(m.getCuenta() == CuentaContable.IVA_DESCONTABLE) {
                    ivaDescontable += m.getValor();
                }
            }
        }
        
        reporte.setIngresos(ingresos);
        reporte.setEgresos(egresos);
        reporte.setUtilidadBruta(ingresos - egresos);
        reporte.setIvaGenerado(ivaGenerado);
        reporte.setIvaDescontable(ivaDescontable);
        reporte.setIvaPorPagar(ivaGenerado - ivaDescontable);
        Map<CuentaContable, Double> balanceOriginal = generarBalanceGeneral(fin);
        Map<String, Double> balanceConvertido = new HashMap<>();
        for(Map.Entry<CuentaContable, Double> entry : balanceOriginal.entrySet()) {
            balanceConvertido.put(entry.getKey().getNombre(), entry.getValue());
        }
        reporte.setBalanceCuentas(balanceConvertido);
        
        return reporte;
    }

    @Override
    public Map<String, Double> obtenerResumenContable(LocalDate inicio, LocalDate fin) {
        Map<String, Double> resumen = new HashMap<>();
        
        double ingresos = 0;
        double egresos = 0;
        
        for(AsientoContable asiento : listarAsientosPorPeriodo(inicio, fin)) {
            for(MovimientoContable m : asiento.getMovimientos()) {
                if(m.getCuenta() == CuentaContable.INGRESOS_VENTAS) {
                    ingresos += m.getValor();
                } else if(m.getCuenta() == CuentaContable.COSTO_VENTAS) {
                    egresos += m.getValor();
                }
            }
        }
        
        resumen.put("Ingresos", ingresos);
        resumen.put("Egresos", egresos);
        resumen.put("Utilidad", ingresos - egresos);
        
        return resumen;
    }
}