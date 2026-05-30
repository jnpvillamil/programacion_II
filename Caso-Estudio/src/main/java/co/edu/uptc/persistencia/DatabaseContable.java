package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.IGestionContable;
import co.edu.uptc.modelo.*;
import co.edu.uptc.Dtos.ReporteContableDTO;
import co.edu.uptc.Util.ConexionBD;
import co.edu.uptc.enums.CuentaContable;
import co.edu.uptc.enums.TipoMovimientoContable;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseContable implements IGestionContable {
    
    private ConexionBD conexionBD = new ConexionBD();
    private int contadorAsientos = 1;
    
    @Override
    public void registrarAsientoVenta(Venta venta) {
        String codigoAsiento = "VENTA-" + venta.getNumeroFactura();
        double valorVenta = venta.getTotal();
        double iva = venta.getIva();
        double baseGravable = venta.getSubtotal();
        
        Connection conn = null;
        try {
            conn = conexionBD.getConexion();
            conn.setAutoCommit(false);
            
       
            String sqlAsiento = "INSERT INTO asientos_contables (codigo_asiento, fecha, descripcion, referencia, usuario_registro, balanceado) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlAsiento)) {
                ps.setString(1, codigoAsiento);
                ps.setTimestamp(2, Timestamp.valueOf(venta.getFechaHora()));
                ps.setString(3, "Registro de venta factura " + venta.getNumeroFactura());
                ps.setString(4, venta.getNumeroFactura());
                ps.setString(5, "admin");
                ps.setBoolean(6, true);
                ps.executeUpdate();
            }
            
      
            String sqlMovimiento = "INSERT INTO movimientos_contables (codigo_transaccion, codigo_asiento, tipo_movimiento, cuenta, valor, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
            
        
            String cuentaEfectivo = venta.getFormaPago().equals("Efectivo") ? "CAJA" : "BANCOS";
            try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                ps.setString(1, codigoAsiento + "-1");
                ps.setString(2, codigoAsiento);
                ps.setString(3, "INGRESO");
                ps.setString(4, cuentaEfectivo);
                ps.setDouble(5, valorVenta);
                ps.setString(6, "Ingreso por venta");
                ps.executeUpdate();
            }
            
          
            try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                ps.setString(1, codigoAsiento + "-2");
                ps.setString(2, codigoAsiento);
                ps.setString(3, "EGRESO");
                ps.setString(4, "INGRESOS_VENTAS");
                ps.setDouble(5, baseGravable);
                ps.setString(6, "Ingreso por venta neto");
                ps.executeUpdate();
            }
            
          
            try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                ps.setString(1, codigoAsiento + "-3");
                ps.setString(2, codigoAsiento);
                ps.setString(3, "EGRESO");
                ps.setString(4, "IVA_GENERADO");
                ps.setDouble(5, iva);
                ps.setString(6, "IVA generado por venta");
                ps.executeUpdate();
            }
            
            
            int contador = 4;
            for (DetalleVenta detalle : venta.getDetalles()) {
                double costoVenta = detalle.getCantidad() * detalle.getProducto().getPrecioCompra();
                
                
                try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                    ps.setString(1, codigoAsiento + "-" + contador++);
                    ps.setString(2, codigoAsiento);
                    ps.setString(3, "INGRESO");
                    ps.setString(4, "COSTO_VENTAS");
                    ps.setDouble(5, costoVenta);
                    ps.setString(6, "Costo de " + detalle.getProducto().getNombre());
                    ps.executeUpdate();
                }
                
         
                try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                    ps.setString(1, codigoAsiento + "-" + contador++);
                    ps.setString(2, codigoAsiento);
                    ps.setString(3, "EGRESO");
                    ps.setString(4, "INVENTARIO");
                    ps.setDouble(5, costoVenta);
                    ps.setString(6, "Salida de inventario " + detalle.getProducto().getNombre());
                    ps.executeUpdate();
                }
            }
            
            conn.commit();
            System.out.println(" Asiento contable registrado para venta: " + venta.getNumeroFactura());
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
                System.err.println(" Error al registrar asiento venta: " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println(" Error en rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(" Error restaurando autoCommit: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void registrarAsientoCompra(Compra compra) {
        String codigoAsiento = "COMPRA-" + compra.getNumeroFacturaProveedor();
        double valorCompra = compra.getTotal();
        double iva = compra.getIva();
        double baseGravable = compra.getSubtotal();
        
        Connection conn = null;
        try {
            conn = conexionBD.getConexion();
            conn.setAutoCommit(false);
            
            
            String sqlAsiento = "INSERT INTO asientos_contables (codigo_asiento, fecha, descripcion, referencia, usuario_registro, balanceado) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlAsiento)) {
                ps.setString(1, codigoAsiento);
                ps.setTimestamp(2, Timestamp.valueOf(compra.getFechaHora()));
                ps.setString(3, "Registro de compra factura " + compra.getNumeroFacturaProveedor());
                ps.setString(4, compra.getNumeroFacturaProveedor());
                ps.setString(5, "admin");
                ps.setBoolean(6, true);
                ps.executeUpdate();
            }
            
        
            String sqlMovimiento = "INSERT INTO movimientos_contables (codigo_transaccion, codigo_asiento, tipo_movimiento, cuenta, valor, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
            
           
            try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                ps.setString(1, codigoAsiento + "-1");
                ps.setString(2, codigoAsiento);
                ps.setString(3, "INGRESO");
                ps.setString(4, "INVENTARIO");
                ps.setDouble(5, baseGravable);
                ps.setString(6, "Entrada de inventario por compra");
                ps.executeUpdate();
            }
            
         
            try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                ps.setString(1, codigoAsiento + "-2");
                ps.setString(2, codigoAsiento);
                ps.setString(3, "INGRESO");
                ps.setString(4, "IVA_DESCONTABLE");
                ps.setDouble(5, iva);
                ps.setString(6, "IVA descontable por compra");
                ps.executeUpdate();
            }
            
           
            try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                ps.setString(1, codigoAsiento + "-3");
                ps.setString(2, codigoAsiento);
                ps.setString(3, "EGRESO");
                ps.setString(4, "PROVEEDORES");
                ps.setDouble(5, valorCompra);
                ps.setString(6, "Cuenta por pagar a " + compra.getProveedor().getRazonSocial());
                ps.executeUpdate();
            }
            
            conn.commit();
            System.out.println(" Asiento contable registrado para compra: " + compra.getNumeroFacturaProveedor());
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
                System.err.println(" Error al registrar asiento compra: " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println(" Error en rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(" Error restaurando autoCommit: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void registrarAsientoAjuste(String descripcion, Map<CuentaContable, Double> debitos, 
                                        Map<CuentaContable, Double> creditos, String referencia) {
        String codigoAsiento = "AJUSTE-" + System.currentTimeMillis();
        
        Connection conn = null;
        try {
            conn = conexionBD.getConexion();
            conn.setAutoCommit(false);
            
            String sqlAsiento = "INSERT INTO asientos_contables (codigo_asiento, fecha, descripcion, referencia, usuario_registro, balanceado) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sqlAsiento)) {
                ps.setString(1, codigoAsiento);
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                ps.setString(3, descripcion);
                ps.setString(4, referencia);
                ps.setString(5, "admin");
                ps.setBoolean(6, true);
                ps.executeUpdate();
            }
            
            String sqlMovimiento = "INSERT INTO movimientos_contables (codigo_transaccion, codigo_asiento, tipo_movimiento, cuenta, valor, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
            int contador = 1;
            
            for (Map.Entry<CuentaContable, Double> entry : debitos.entrySet()) {
                try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                    ps.setString(1, codigoAsiento + "-" + contador++);
                    ps.setString(2, codigoAsiento);
                    ps.setString(3, "INGRESO");
                    ps.setString(4, entry.getKey().name());
                    ps.setDouble(5, entry.getValue());
                    ps.setString(6, "Débito por ajuste");
                    ps.executeUpdate();
                }
            }
            
            for (Map.Entry<CuentaContable, Double> entry : creditos.entrySet()) {
                try (PreparedStatement ps = conn.prepareStatement(sqlMovimiento)) {
                    ps.setString(1, codigoAsiento + "-" + contador++);
                    ps.setString(2, codigoAsiento);
                    ps.setString(3, "EGRESO");
                    ps.setString(4, entry.getKey().name());
                    ps.setDouble(5, entry.getValue());
                    ps.setString(6, "Crédito por ajuste");
                    ps.executeUpdate();
                }
            }
            
            conn.commit();
            System.out.println(" Asiento de ajuste registrado: " + codigoAsiento);
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
                System.err.println(" Error al registrar asiento ajuste: " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println(" Error en rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println(" Error restaurando autoCommit: " + e.getMessage());
            }
        }
    }
    
    @Override
    public List<AsientoContable> listarAsientos() {
        List<AsientoContable> asientos = new ArrayList<>();
        String sql = "SELECT * FROM asientos_contables ORDER BY fecha DESC";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                AsientoContable asiento = new AsientoContable(
                    rs.getString("codigo_asiento"),
                    rs.getString("descripcion"),
                    rs.getString("referencia"),
                    rs.getString("usuario_registro")
                );
                
                
                cargarMovimientos(asiento);
                asientos.add(asiento);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar asientos: " + e.getMessage());
        }
        return asientos;
    }
    
    private void cargarMovimientos(AsientoContable asiento) {
        String sql = "SELECT * FROM movimientos_contables WHERE codigo_asiento = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, asiento.getCodigoAsiento());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TipoMovimientoContable tipo = rs.getString("tipo_movimiento").equals("INGRESO") ? 
                    TipoMovimientoContable.INGRESO : TipoMovimientoContable.EGRESO;
                CuentaContable cuenta = CuentaContable.valueOf(rs.getString("cuenta"));
                
                asiento.agregarMovimiento(
                    tipo,
                    cuenta,
                    rs.getDouble("valor"),
                    rs.getString("descripcion")
                );
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al cargar movimientos: " + e.getMessage());
        }
    }
    
    @Override
    public List<AsientoContable> listarAsientosPorFecha(LocalDate fecha) {
        List<AsientoContable> asientos = new ArrayList<>();
        String sql = "SELECT * FROM asientos_contables WHERE DATE(fecha) = ? ORDER BY fecha DESC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, fecha.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                AsientoContable asiento = new AsientoContable(
                    rs.getString("codigo_asiento"),
                    rs.getString("descripcion"),
                    rs.getString("referencia"),
                    rs.getString("usuario_registro")
                );
                cargarMovimientos(asiento);
                asientos.add(asiento);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar asientos por fecha: " + e.getMessage());
        }
        return asientos;
    }
    
    @Override
    public List<AsientoContable> listarAsientosPorPeriodo(LocalDate inicio, LocalDate fin) {
        List<AsientoContable> asientos = new ArrayList<>();
        String sql = "SELECT * FROM asientos_contables WHERE DATE(fecha) BETWEEN ? AND ? ORDER BY fecha DESC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                AsientoContable asiento = new AsientoContable(
                    rs.getString("codigo_asiento"),
                    rs.getString("descripcion"),
                    rs.getString("referencia"),
                    rs.getString("usuario_registro")
                );
                cargarMovimientos(asiento);
                asientos.add(asiento);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar asientos por período: " + e.getMessage());
        }
        return asientos;
    }
    
    @Override
    public List<MovimientoContable> listarMovimientosPorCuenta(CuentaContable cuenta) {
        List<MovimientoContable> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_contables WHERE cuenta = ? ORDER BY codigo_asiento";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cuenta.name());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TipoMovimientoContable tipo = rs.getString("tipo_movimiento").equals("INGRESO") ? 
                    TipoMovimientoContable.INGRESO : TipoMovimientoContable.EGRESO;
                
                MovimientoContable movimiento = new MovimientoContable(
                    rs.getString("codigo_transaccion"),
                    tipo,
                    cuenta,
                    rs.getDouble("valor"),
                    rs.getString("descripcion"),
                    rs.getString("codigo_asiento"),
                    null
                );
                movimientos.add(movimiento);
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al listar movimientos por cuenta: " + e.getMessage());
        }
        return movimientos;
    }
    
    @Override
    public double calcularSaldoCuenta(CuentaContable cuenta, LocalDate fechaCorte) {
        double saldo = 0;
        String sql = "SELECT SUM(CASE WHEN tipo_movimiento = 'INGRESO' THEN valor ELSE -valor END) as saldo " +
                     "FROM movimientos_contables mc " +
                     "JOIN asientos_contables ac ON mc.codigo_asiento = ac.codigo_asiento " +
                     "WHERE mc.cuenta = ? AND DATE(ac.fecha) <= ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cuenta.name());
            ps.setString(2, fechaCorte.toString());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al calcular saldo de cuenta: " + e.getMessage());
        }
        return saldo;
    }
    
    @Override
    public Map<CuentaContable, Double> generarBalanceGeneral(LocalDate fechaCorte) {
        Map<CuentaContable, Double> balance = new HashMap<>();
        
        for (CuentaContable cuenta : CuentaContable.values()) {
            double saldo = calcularSaldoCuenta(cuenta, fechaCorte);
            if (Math.abs(saldo) > 0.01) {
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
        
        String sql = "SELECT mc.cuenta, SUM(mc.valor) as total " +
                     "FROM movimientos_contables mc " +
                     "JOIN asientos_contables ac ON mc.codigo_asiento = ac.codigo_asiento " +
                     "WHERE DATE(ac.fecha) BETWEEN ? AND ? " +
                     "GROUP BY mc.cuenta";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, inicio.toString());
            ps.setString(2, fin.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String cuenta = rs.getString("cuenta");
                double total = rs.getDouble("total");
                
                if ("INGRESOS_VENTAS".equals(cuenta)) {
                    ingresos = total;
                } else if ("COSTO_VENTAS".equals(cuenta)) {
                    costos = total;
                }
            }
            
        } catch (SQLException e) {
            System.err.println(" Error al generar estado resultados: " + e.getMessage());
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
        
        var resultados = generarEstadoResultados(inicio, fin);
        
        reporte.setIngresos(resultados.get("Ingresos Totales"));
        reporte.setEgresos(resultados.get("Costo de Ventas"));
        reporte.setUtilidadBruta(resultados.get("Utilidad Bruta"));
        reporte.setBalanceCuentas(new HashMap<>());
        
        return reporte;
    }
    
    @Override
    public Map<String, Double> obtenerResumenContable(LocalDate inicio, LocalDate fin) {
        Map<String, Double> resumen = new HashMap<>();
        var resultados = generarEstadoResultados(inicio, fin);
        
        resumen.put("Ingresos", resultados.get("Ingresos Totales"));
        resumen.put("Egresos", resultados.get("Costo de Ventas"));
        resumen.put("Utilidad", resultados.get("Utilidad Bruta"));
        
        return resumen;
    }
}