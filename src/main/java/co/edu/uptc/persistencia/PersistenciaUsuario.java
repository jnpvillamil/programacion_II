package co.edu.uptc.persistencia;

import co.edu.uptc.dto.UsuarioResumenDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.modelo.Administrador;
import co.edu.uptc.modelo.Cajero;
import co.edu.uptc.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuario {

    private static final String SQL_INSERT = """
            INSERT INTO usuario (
                usuario_login, clave, rol, nombres, apellidos, identificacion, telefono, activo
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_SELECT_BY_LOGIN = """
            SELECT usuario_login, clave, rol, nombres, apellidos, identificacion, telefono, activo
            FROM usuario
            WHERE usuario_login = ?
            """;

    private static final String SQL_SELECT_ALL = """
            SELECT usuario_login, clave, rol, nombres, apellidos, identificacion, telefono, activo
            FROM usuario
            ORDER BY nombres, apellidos
            """;

    private static final String SQL_DELETE = """
            DELETE FROM usuario WHERE usuario_login = ?
            """;

    private static final String SQL_EXISTE_LOGIN = """
            SELECT COUNT(1) AS total
            FROM usuario
            WHERE usuario_login = ?
            """;

    private static final String SQL_ACTIVAR_POR_LOGIN = """
            UPDATE usuario SET activo = 1 WHERE usuario_login = ?
            """;

    private static final String SQL_INACTIVAR_POR_LOGIN = """
            UPDATE usuario SET activo = 0 WHERE usuario_login = ?
            """;

    private static final String SQL_SELECT_RESUMEN = """
            SELECT usuario_login,
                   CONCAT(nombres, ' ', apellidos) AS nombre_completo,
                   rol,
                   CASE WHEN activo = 1 THEN 'Activo' ELSE 'Inactivo' END AS estado
            FROM usuario
            ORDER BY nombres, apellidos
            """;

    public void guardar(Usuario usuario) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERT)) {

            sentencia.setString(1, usuario.getUsuario());
            sentencia.setString(2, usuario.getClave());
            sentencia.setString(3, usuario.obtenerRol());
            sentencia.setString(4, usuario.getNombre());
            sentencia.setString(5, usuario.getApellido());
            sentencia.setString(6, usuario.getIdentificacion());
            sentencia.setString(7, usuario.getTelefono());
            sentencia.setBoolean(8, usuario.isActivo());
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public void eliminar(String id) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_DELETE)) {
            sentencia.setString(1, id);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                lista.add(mapearUsuario(resultado));
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return lista;
    }

    public Usuario buscarPorId(String id) {
        return buscarPorUsuarioLogin(id);
    }

    public Usuario buscarPorUsuarioLogin(String usuarioLogin) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_BY_LOGIN)) {
            sentencia.setString(1, usuarioLogin);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearUsuario(resultado);
                }
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return null;
    }

    public boolean existeUsuarioLogin(String usuarioLogin) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_EXISTE_LOGIN)) {
            sentencia.setString(1, usuarioLogin);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("total") > 0;
                }
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return false;
    }

    public void activarPorUsuarioLogin(String usuarioLogin) {
        ejecutarActualizacionEstado(SQL_ACTIVAR_POR_LOGIN, usuarioLogin);
    }

    public void inactivarPorUsuarioLogin(String usuarioLogin) {
        ejecutarActualizacionEstado(SQL_INACTIVAR_POR_LOGIN, usuarioLogin);
    }

    public List<UsuarioResumenDTO> listarResumen() {
        List<UsuarioResumenDTO> listaResumen = new ArrayList<>();
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_RESUMEN);
             ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                listaResumen.add(new UsuarioResumenDTO(
                        resultado.getString("usuario_login"),
                        resultado.getString("nombre_completo"),
                        resultado.getString("rol"),
                        resultado.getString("estado")));
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return listaResumen;
    }

    private void ejecutarActualizacionEstado(String sql, String usuarioLogin) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, usuarioLogin);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    private Usuario mapearUsuario(ResultSet resultado) throws SQLException {
        String rol = resultado.getString("rol");
        String nombre = resultado.getString("nombres");
        String apellido = resultado.getString("apellidos");
        String identificacion = resultado.getString("identificacion");
        String telefono = resultado.getString("telefono");
        String login = resultado.getString("usuario_login");
        String clave = resultado.getString("clave");
        boolean activo = resultado.getBoolean("activo");

        Usuario usuario = RolUsuario.ADMINISTRADOR.name().equals(rol)
                ? new Administrador(nombre, apellido, identificacion, "", telefono, login, clave)
                : new Cajero(nombre, apellido, identificacion, "", telefono, login, clave);
        usuario.setActivo(activo);
        return usuario;
    }
}
