package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import co.edu.uptc.interfaces.IRepositorioFinanciero;
import co.edu.uptc.utilidades.ConexionBD;

public class PersistenciaFinanciera implements IRepositorioFinanciero {

    @Override
    public double calcularTotalIngresosVentas(LocalDateTime inicio, LocalDateTime fin) {
        double totalIngresos = 0.0;
        String sql = "SELECT SUM(total) AS total FROM ventas WHERE fecha BETWEEN ? AND ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setTimestamp(1, Timestamp.valueOf(inicio));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalIngresos = rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular ingresos de ventas: " + e.getMessage());
        }
        
        return totalIngresos;
    }

    @Override
    public double calcularTotalCostosCompras(LocalDateTime inicio, LocalDateTime fin) {
        double totalCostos = 0.0;
        String sql = "SELECT SUM(total_compra) AS total FROM compras WHERE fecha BETWEEN ? AND ?";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setTimestamp(1, Timestamp.valueOf(inicio));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalCostos = rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular costos de compras: " + e.getMessage());
        }
        
        return totalCostos;
    }
}