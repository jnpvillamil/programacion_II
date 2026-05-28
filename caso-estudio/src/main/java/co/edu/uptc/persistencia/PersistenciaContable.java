package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uptc.dto.ResumenDiarioJSONDTO;
import co.edu.uptc.interfaces.IRepositorioContable;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.enums.TipoMovimiento;
import co.edu.uptc.utilidades.ConexionBD;

public class PersistenciaContable implements IRepositorioContable {

    @Override
    public void guardar(MovimientoContable mov) {
        String sql = "INSERT INTO movimientos_contables (codigo_transaccion, fecha, tipo_movimiento, cuenta_contable, valor, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, mov.getCodigoTransaccion());
            ps.setTimestamp(2, Timestamp.valueOf(mov.getFechaMovimiento())); 
            ps.setString(3, mov.getTipoMovimiento().name()); 
            ps.setString(4, mov.getCuentaContable());
            ps.setDouble(5, mov.getValorMovimiento());
            ps.setString(6, mov.getDescripcion());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al registrar movimiento contable: " + e.getMessage());
        }
    }

 
    @Override
    public List<MovimientoContable> consultarPorTransaccion(String codigoTransaccion) {
        List<MovimientoContable> movimientos = new ArrayList<>();
        String sql = "SELECT codigo_transaccion, fecha, tipo_movimiento, cuenta_contable, valor, descripcion FROM movimientos_contables WHERE codigo_transaccion = ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, codigoTransaccion);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String codTrans = rs.getString("codigo_transaccion");
                    Timestamp timestamp = rs.getTimestamp("fecha");
                    String tipoStr = rs.getString("tipo_movimiento");
                    String cuenta = rs.getString("cuenta_contable");
                    double valor = rs.getDouble("valor");
                    String desc = rs.getString("descripcion");
                    
                    TipoMovimiento tipoEnum = TipoMovimiento.valueOf(tipoStr);
                    
                    MovimientoContable mov = new MovimientoContable(
                        codTrans,
                        timestamp.toLocalDateTime(),
                        tipoEnum,
                        cuenta,
                        valor,
                        desc
                    );
                    
                    movimientos.add(mov);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar movimientos contables en BD: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error de formato al convertir el Enum de TipoMovimiento: " + e.getMessage());
        }
        
        return movimientos;
    }


    @Override
    public Map<String, Double> resumenContablePorPeriodo(java.time.LocalDateTime inicio, java.time.LocalDateTime fin) {
        Map<String, Double> resumen = new HashMap<>();
        
        String sql = "SELECT cuenta_contable, tipo_movimiento, SUM(valor) AS total_cuenta FROM movimientos_contables WHERE fecha BETWEEN ? AND ? GROUP BY cuenta_contable, tipo_movimiento";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setTimestamp(1, Timestamp.valueOf(inicio));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String cuenta = rs.getString("cuenta_contable");
                    String tipo = rs.getString("tipo_movimiento");
                    double total = rs.getDouble("total_cuenta");
                    
                    double saldoActual = resumen.getOrDefault(cuenta, 0.0);
                    
                    if ("EGRESO".equalsIgnoreCase(tipo)) {
                        resumen.put(cuenta, saldoActual - total);
                    } else {
                        resumen.put(cuenta, saldoActual + total);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al generar el resumen contable en BD: " + e.getMessage());
        }
        
        return resumen;
    }

    @Override
    public ResumenDiarioJSONDTO.ResumenContable resumenFinancieroPorPeriodo(
            java.time.LocalDateTime inicio, java.time.LocalDateTime fin) {
        ResumenDiarioJSONDTO.ResumenContable resumen = new ResumenDiarioJSONDTO.ResumenContable();
        String sql = """
                SELECT cuenta_contable, tipo_movimiento, COALESCE(SUM(valor), 0) AS total
                FROM movimientos_contables
                WHERE fecha BETWEEN ? AND ?
                GROUP BY cuenta_contable, tipo_movimiento
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(inicio));
            ps.setTimestamp(2, Timestamp.valueOf(fin));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    acumularResumenFinanciero(
                            resumen,
                            rs.getString("cuenta_contable"),
                            rs.getString("tipo_movimiento"),
                            rs.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al generar resumen financiero contable: " + e.getMessage());
        }

        return resumen;
    }

    private void acumularResumenFinanciero(ResumenDiarioJSONDTO.ResumenContable resumen,
                                           String cuenta, String tipo, double total) {
        if (cuenta == null || tipo == null) {
            return;
        }

        switch (cuenta) {
            case "Ingresos por ventas" -> {
                if ("EGRESO".equalsIgnoreCase(tipo)) {
                    resumen.setIngresos(resumen.getIngresos() + total);
                } else {
                    resumen.setIngresos(resumen.getIngresos() - total);
                }
            }
            case "Proveedores" -> {
                if ("EGRESO".equalsIgnoreCase(tipo)) {
                    resumen.setEgresos(resumen.getEgresos() + total);
                } else {
                    resumen.setEgresos(resumen.getEgresos() - total);
                }
            }
            case "IVA generado" -> {
                if ("EGRESO".equalsIgnoreCase(tipo)) {
                    resumen.setIvaGenerado(resumen.getIvaGenerado() + total);
                } else {
                    resumen.setIvaGenerado(resumen.getIvaGenerado() - total);
                }
            }
            case "IVA descontable" -> {
                if ("INGRESO".equalsIgnoreCase(tipo)) {
                    resumen.setIvaDescontable(resumen.getIvaDescontable() + total);
                } else {
                    resumen.setIvaDescontable(resumen.getIvaDescontable() - total);
                }
            }
            default -> {
            }
        }
    }

    @Override public void eliminar(String id) {}
    @Override public void actualizar(MovimientoContable objeto) {}
    @Override public MovimientoContable buscarPorId(String id) { return null; }
    @Override public List<MovimientoContable> listar() { return null; }
}