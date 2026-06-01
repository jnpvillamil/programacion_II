package co.edu.uptc.tiendaminorista.persistencia;

import co.edu.uptc.tiendaminorista.interfaces.IGestionEmpleado;
import co.edu.uptc.tiendaminorista.modelo.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalEmpleado implements IGestionEmpleado {

    @Override
    public void guardar(Empleado empleado) {
        String sql = "INSERT INTO empleados (correo, password, tipo_empleado) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empleado.getCorreo());
            pstmt.setString(2, empleado.getPassword());
            pstmt.setString(3, empleado.getTipoEmpleado());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT correo, password, tipo_empleado FROM empleados";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearEmpleado(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET password = ?, tipo_empleado = ? WHERE correo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empleado.getPassword());
            pstmt.setString(2, empleado.getTipoEmpleado());
            pstmt.setString(3, empleado.getCorreo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Empleado empleado) {
        String sql = "DELETE FROM empleados WHERE correo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empleado.getCorreo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setCorreo(rs.getString("correo"));
        e.setPassword(rs.getString("password"));
        e.setTipoEmpleado(rs.getString("tipo_empleado"));
        return e;
    }
}
