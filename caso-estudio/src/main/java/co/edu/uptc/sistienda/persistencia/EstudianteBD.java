package co.edu.uptc.sistienda.persistencia;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.JOptionPane;
 
import co.edu.uptc.sistienda.interfaces.IGestionEstudiante;
import co.edu.uptc.sistienda.modelo.Estudiante;
 
public class EstudianteBD implements IGestionEstudiante {
 
    // GUARDAR
    @Override
    public void guardarEstudiante(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (cedula, nombre, telefono) VALUES (?, ?, ?)";
 
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setString(1, estudiante.getCedula());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getTelefono());
            ps.executeUpdate();
 
            JOptionPane.showMessageDialog(null,
                "Estudiante guardado exitosamente.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo guardar el estudiante: " + e.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // ACTUALIZAR
    @Override
    public void actualizarEstudiante(Estudiante estudiante) {
        String sql = "UPDATE estudiantes SET nombre = ?, telefono = ? WHERE cedula = ?";
 
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getTelefono());
            ps.setString(3, estudiante.getCedula());
            ps.executeUpdate();
 
            JOptionPane.showMessageDialog(null,
                "Estudiante actualizado exitosamente.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo actualizar el estudiante: " + e.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // ELIMINAR
    @Override
    public void eliminarEstudiante(String cedula) {
        String sql = "DELETE FROM estudiantes WHERE cedula = ?";
 
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setString(1, cedula);
            ps.executeUpdate();
 
            JOptionPane.showMessageDialog(null,
                "Estudiante eliminado.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo eliminar el estudiante: " + e.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // BUSCAR POR CÉDULA
    @Override
    public Estudiante buscarEstudiantePorCedula(String cedula) {
        String sql = "SELECT cedula, nombre, telefono FROM estudiantes WHERE cedula = ?";
 
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
 
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearFila(rs);
                }
            }
 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
 
    // LISTAR TODOS
    @Override
    public List<Estudiante> obtenerListaEstudiantes() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT cedula, nombre, telefono FROM estudiantes";
 
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }
 
    // AUXILIAR: convierte una fila del ResultSet en un objeto Estudiante
    private Estudiante mapearFila(ResultSet rs) throws SQLException {
        Estudiante e = new Estudiante();
        e.setCedula(rs.getString("cedula"));
        e.setNombre(rs.getString("nombre"));
        e.setTelefono(rs.getString("telefono"));
        return e;
    }
}
