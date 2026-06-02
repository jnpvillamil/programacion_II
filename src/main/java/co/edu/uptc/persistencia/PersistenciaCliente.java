package co.edu.uptc.persistencia;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCliente {

    private static final String SQL_INSERT = """
            INSERT INTO cliente (
                codigo_cliente, tipo_id, identificacion, nombres, apellidos,
                telefono, direccion, tipo_cliente, estado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE cliente
            SET tipo_id = ?, identificacion = ?, nombres = ?, apellidos = ?,
                telefono = ?, direccion = ?, tipo_cliente = ?, estado = ?
            WHERE codigo_cliente = ?
            """;

    private static final String SQL_DELETE = """
            DELETE FROM cliente
            WHERE codigo_cliente = ?
            """;

    private static final String SQL_SELECT_ALL = """
            SELECT codigo_cliente, tipo_id, identificacion, nombres, apellidos,
                   telefono, direccion, tipo_cliente, estado
            FROM cliente
            ORDER BY nombres, apellidos
            """;

    private static final String SQL_SELECT_BY_CODIGO = """
            SELECT codigo_cliente, tipo_id, identificacion, nombres, apellidos,
                   telefono, direccion, tipo_cliente, estado
            FROM cliente
            WHERE codigo_cliente = ?
            """;

    private static final String SQL_SELECT_BY_IDENTIFICACION = """
            SELECT codigo_cliente, tipo_id, identificacion, nombres, apellidos,
                   telefono, direccion, tipo_cliente, estado
            FROM cliente
            WHERE identificacion = ?
            """;

    private static final String SQL_EXISTE_IDENTIFICACION = """
            SELECT COUNT(1) AS total
            FROM cliente
            WHERE identificacion = ?
            """;

    private static final String SQL_SELECT_RESUMEN = """
            SELECT codigo_cliente,
                   CONCAT(nombres, ' ', apellidos) AS nombre_completo,
                   telefono,
                   estado
            FROM cliente
            ORDER BY nombres, apellidos
            """;

    private static final String SQL_ACTIVAR_POR_IDENTIFICACION = """
            UPDATE cliente SET estado = 'Activo' WHERE identificacion = ?
            """;

    private static final String SQL_INACTIVAR_POR_IDENTIFICACION = """
            UPDATE cliente SET estado = 'Inactivo' WHERE identificacion = ?
            """;

    public void guardar(Cliente cliente) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERT)) {

            asignarParametroInsercion(sentencia, cliente);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public void actualizar(Cliente cliente) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_UPDATE)) {

            sentencia.setString(1, cliente.getTipoIdentificacion().name());
            sentencia.setString(2, cliente.getIdentificacion());
            sentencia.setString(3, cliente.getNombre());
            sentencia.setString(4, cliente.getApellido());
            sentencia.setString(5, cliente.getTelefono());
            sentencia.setString(6, cliente.getDireccion());
            sentencia.setString(7, cliente.getTipoCliente().name());
            sentencia.setString(8, cliente.isActivo() ? "Activo" : "Inactivo");
            sentencia.setString(9, cliente.getCodigoCliente());
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public void eliminar(String codigoCliente) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_DELETE)) {

            sentencia.setString(1, codigoCliente);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public List<Cliente> listar() {
        List<Cliente> listaCliente = new ArrayList<>();

        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                listaCliente.add(mapearCliente(resultado));
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return listaCliente;
    }

    public Cliente buscarPorId(String codigoCliente) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_BY_CODIGO)) {

            sentencia.setString(1, codigoCliente);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearCliente(resultado);
                }
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return null;
    }

    public Cliente buscarPorIdentificacion(String identificacion) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_BY_IDENTIFICACION)) {

            sentencia.setString(1, identificacion);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearCliente(resultado);
                }
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return null;
    }

    public boolean existeIdentificacion(String identificacion) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_EXISTE_IDENTIFICACION)) {

            sentencia.setString(1, identificacion);
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

    public List<ClienteResumenDTO> listarResumen() {
        List<ClienteResumenDTO> listaResumen = new ArrayList<>();

        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_RESUMEN);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                listaResumen.add(new ClienteResumenDTO(
                        resultado.getString("codigo_cliente"),
                        resultado.getString("nombre_completo"),
                        valorSeguro(resultado.getString("telefono")),
                        resultado.getString("estado")
                ));
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return listaResumen;
    }

    public void activarPorIdentificacion(String identificacion) {
        ejecutarActualizacionEstado(SQL_ACTIVAR_POR_IDENTIFICACION, identificacion);
    }

    public void inactivarPorIdentificacion(String identificacion) {
        ejecutarActualizacionEstado(SQL_INACTIVAR_POR_IDENTIFICACION, identificacion);
    }

    private void ejecutarActualizacionEstado(String sql, String identificacion) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, identificacion);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    private void asignarParametroInsercion(PreparedStatement sentencia, Cliente cliente) throws SQLException {
        sentencia.setString(1, cliente.getCodigoCliente());
        sentencia.setString(2, cliente.getTipoIdentificacion().name());
        sentencia.setString(3, cliente.getIdentificacion());
        sentencia.setString(4, cliente.getNombre());
        sentencia.setString(5, cliente.getApellido());
        sentencia.setString(6, cliente.getTelefono());
        sentencia.setString(7, cliente.getDireccion());
        sentencia.setString(8, cliente.getTipoCliente().name());
        sentencia.setString(9, cliente.isActivo() ? "Activo" : "Inactivo");
    }

    private Cliente mapearCliente(ResultSet resultado) throws SQLException {
        TipoIdentificacion tipoIdentificacion = TipoIdentificacion.valueOf(resultado.getString("tipo_id"));
        TipoCliente tipoCliente = TipoCliente.valueOf(resultado.getString("tipo_cliente"));

        Cliente cliente = new Cliente(
                resultado.getString("nombres"),
                resultado.getString("apellidos"),
                resultado.getString("identificacion"),
                valorSeguro(resultado.getString("direccion")),
                valorSeguro(resultado.getString("telefono")),
                resultado.getString("codigo_cliente"),
                tipoIdentificacion,
                tipoCliente
        );
        cliente.setActivo("Activo".equalsIgnoreCase(resultado.getString("estado")));
        return cliente;
    }

    private String valorSeguro(String valor) {
        return valor != null ? valor : "";
    }
}
