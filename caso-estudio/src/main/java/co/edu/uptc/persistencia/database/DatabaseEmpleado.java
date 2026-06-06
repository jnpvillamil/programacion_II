package co.edu.uptc.persistencia.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.interfaces.IGestionEmpleado;
import co.edu.uptc.negocio.dto.empleadoDto;

public class DatabaseEmpleado implements IGestionEmpleado {

    @Override
    public void guardar(empleadoDto empleado) {
        String sql = "INSERT INTO empleado (nombre) VALUES (?)";
        Conexion conexObj = new Conexion();
        try (Connection conex = conexObj.getConnection();
             PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setString(1, empleado.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar empleado: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(String nombreAntiguo, empleadoDto empleado) {
        String sql = "UPDATE empleado SET nombre = ? WHERE nombre = ?";
        Conexion conexObj = new Conexion();
        try (Connection conex = conexObj.getConnection();
             PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setString(1, empleado.getNombre());
            ps.setString(2, nombreAntiguo);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String nombre) {
        String sql = "DELETE FROM empleado WHERE nombre = ?";
        Conexion conexObj = new Conexion();
        try (Connection conex = conexObj.getConnection();
             PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
        }
    }

    @Override
    public empleadoDto buscar(String nombre) {
        String sql = "SELECT * FROM empleado WHERE nombre = ?";
        Conexion conexObj = new Conexion();
        try (Connection conex = conexObj.getConnection();
             PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<empleadoDto> listar() {
        List<empleadoDto> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleado";
        Conexion conexObj = new Conexion();
        try (Connection conex = conexObj.getConnection();
             PreparedStatement ps = conex.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }

    private empleadoDto mapRow(ResultSet rs) throws SQLException {
        empleadoDto e = new empleadoDto();
        e.setNombre(rs.getString("nombre"));
        return e;
    }
}