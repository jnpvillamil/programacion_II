package co.edu.uptc.persistencia;

<<<<<<< HEAD
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.utilidades.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaSupervisor {
=======
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Supervisor;
import co.edu.uptc.utilidades.ConexionBD;
>>>>>>> d477bf940f1eea00c38484c317713f01db11137e

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaSupervisor implements Repositorio<Supervisor> {

    @Override
    public void guardar(Supervisor supervisor) {
        String sql = """
                INSERT INTO supervisores
                (nombre, identificacion, direccion, telefono, usuario, clave)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, supervisor.getNombre());
            ps.setString(2, supervisor.getIdentificacion());
            ps.setString(3, supervisor.getDireccion());
            ps.setString(4, supervisor.getTelefono());
            ps.setString(5, supervisor.getUsuario());
            ps.setString(6, supervisor.getClave());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al guardar supervisor: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        String sql = "DELETE FROM supervisores WHERE identificacion = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar supervisor: " + e.getMessage());
        }
    }

    @Override
    public Supervisor buscarPorId(String id) {
        String sql = "SELECT * FROM supervisores WHERE identificacion = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Supervisor supervisor = new Supervisor();

                supervisor.setNombre(rs.getString("nombre"));
                supervisor.setIdentificacion(rs.getString("identificacion"));
                supervisor.setDireccion(rs.getString("direccion"));
                supervisor.setTelefono(rs.getString("telefono"));
                supervisor.setUsuario(rs.getString("usuario"));
                supervisor.setClave(rs.getString("clave"));

                return supervisor;
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar supervisor: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Supervisor> listar() {

        List<Supervisor> lista = new ArrayList<>();

        String sql = "SELECT * FROM supervisores";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Supervisor supervisor = new Supervisor();

                supervisor.setNombre(rs.getString("nombre"));
                supervisor.setIdentificacion(rs.getString("identificacion"));
                supervisor.setDireccion(rs.getString("direccion"));
                supervisor.setTelefono(rs.getString("telefono"));
                supervisor.setUsuario(rs.getString("usuario"));
                supervisor.setClave(rs.getString("clave"));

                lista.add(supervisor);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar supervisores: " + e.getMessage());
        }

        return lista;
    }
}