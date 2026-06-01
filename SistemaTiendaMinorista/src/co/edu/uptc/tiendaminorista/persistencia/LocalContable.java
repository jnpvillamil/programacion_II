package co.edu.uptc.tiendaminorista.persistencia;

import co.edu.uptc.tiendaminorista.interfaces.IGestionContable;
import co.edu.uptc.tiendaminorista.modelo.MovimientoContable;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocalContable implements IGestionContable {

    @Override
    public void guardar(MovimientoContable movimiento) {
        if (movimiento.getCodigo() == null || movimiento.getCodigo().isEmpty()) {
            movimiento.setCodigo(generarCodigo());
        }
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(LocalDate.now());
        }

        String sql = "INSERT INTO movimientos_contables (codigo, fecha, tipo, cuenta_contable, debito, credito, descripcion, documento_relacionado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movimiento.getCodigo());
            pstmt.setDate(2, Date.valueOf(movimiento.getFecha()));
            pstmt.setString(3, movimiento.getTipo());
            pstmt.setString(4, movimiento.getCuentaContable());
            pstmt.setDouble(5, movimiento.getDebito());
            pstmt.setDouble(6, movimiento.getCredito());
            pstmt.setString(7, movimiento.getDescripcion());
            pstmt.setString(8, movimiento.getDocumentoRelacionado());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MovimientoContable> listar() {
        List<MovimientoContable> lista = new ArrayList<>();
        String sql = "SELECT codigo, fecha, tipo, cuenta_contable, debito, credito, descripcion, documento_relacionado FROM movimientos_contables ORDER BY fecha DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearMovimiento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<MovimientoContable> filtrarPorTipo(String tipo) {
        if (tipo == null || tipo.equals("Todos")) return listar();
        return listar().stream()
                .filter(m -> m.getTipo() != null && m.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoContable> filtrarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return listar().stream()
                .filter(m -> m.getFecha() != null
                        && !m.getFecha().isBefore(desde)
                        && !m.getFecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovimientoContable> buscarPorCuenta(String cuenta) {
        if (cuenta == null || cuenta.trim().isEmpty()) return listar();
        return listar().stream()
                .filter(m -> m.getCuentaContable() != null
                        && m.getCuentaContable().toLowerCase().contains(cuenta.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalIngresos() {
        String sql = "SELECT COALESCE(SUM(credito), 0) FROM movimientos_contables WHERE tipo = 'Ingreso'";
        return ejecutarSumaSQL(sql);
    }

    @Override
    public double getTotalEgresos() {
        String sql = "SELECT COALESCE(SUM(debito), 0) FROM movimientos_contables WHERE tipo = 'Egreso'";
        return ejecutarSumaSQL(sql);
    }

    @Override
    public double getSaldoActual() {
        return getTotalIngresos() - getTotalEgresos();
    }

    // ── Métodos auxiliares ───────────────────────────────────────────────────

    private MovimientoContable mapearMovimiento(ResultSet rs) throws SQLException {
        MovimientoContable m = new MovimientoContable();
        m.setCodigo(rs.getString("codigo"));
        Date fecha = rs.getDate("fecha");
        m.setFecha(fecha != null ? fecha.toLocalDate() : LocalDate.now());
        m.setTipo(rs.getString("tipo"));
        m.setCuentaContable(rs.getString("cuenta_contable"));
        m.setDebito(rs.getDouble("debito"));
        m.setCredito(rs.getDouble("credito"));
        m.setDescripcion(rs.getString("descripcion"));
        m.setDocumentoRelacionado(rs.getString("documento_relacionado"));
        return m;
    }

    private double ejecutarSumaSQL(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private String generarCodigo() {
        String sql = "SELECT codigo FROM movimientos_contables ORDER BY codigo DESC LIMIT 1";
        int num = 1;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String ultimo = rs.getString("codigo");
                if (ultimo != null && ultimo.startsWith("MOV")) {
                    num = Integer.parseInt(ultimo.substring(3)) + 1;
                }
            }
        } catch (SQLException | NumberFormatException e) {
            // Si falla, usa 1
        }
        return "MOV" + String.format("%05d", num);
    }
}
