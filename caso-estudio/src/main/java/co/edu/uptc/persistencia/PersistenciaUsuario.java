package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.IPersistenciaUsuario;
import co.edu.uptc.modelo.Administrador;
import co.edu.uptc.modelo.Cajero;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuario implements IPersistenciaUsuario {

    @Override
    public Usuario validarUsuario(String nombreUsuario, String claveIngresada) {
        String sql = "SELECT usuario, contrasena FROM usuarios WHERE usuario = ? AND contrasena = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, claveIngresada);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearUsuarioDesdeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al validar usuario en SQL: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT usuario, contrasena FROM usuarios";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(crearUsuarioDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios desde SQL: " + e.getMessage());
        }
        return usuarios;
    }

    private Usuario crearUsuarioDesdeResultSet(ResultSet rs) throws SQLException {
        String usuario = rs.getString("usuario");
        String contrasena = rs.getString("contrasena");
        if ("cajero".equalsIgnoreCase(usuario)) {
            return new Cajero(usuario, "", "", "", usuario, contrasena);
        }
        return new Administrador(usuario, "", "", "", usuario, contrasena);
    }
}
