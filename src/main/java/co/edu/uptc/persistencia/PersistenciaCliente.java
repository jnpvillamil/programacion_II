package co.edu.uptc.persistencia;

import co.edu.uptc.dto.ClienteResumenDTO;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.interfaces.RepositorioCliente;
import co.edu.uptc.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCliente implements RepositorioCliente {

    private static final String SQL_INSERT = """
            INSERT INTO clientes (
                codigo_cliente, tipo_id, identificacion, nombres, apellidos,
                telefono, direccion, tipo_cliente, estado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_DELETE = """
            DELETE FROM clientes
            WHERE codigo_cliente = ? OR identificacion = ?
            """;

    private static final String SQL_SELECT_ALL = """
            SELECT codigo_cliente, tipo_id, identificacion, nombres, apellidos,
                   telefono, direccion, tipo_cliente, estado
            FROM clientes
            """;

    private static final String SQL_SELECT_BY_ID = """
            SELECT codigo_cliente, tipo_id, identificacion, nombres, apellidos,
                   telefono, direccion, tipo_cliente, estado
            FROM clientes
            WHERE identificacion = ?
            """;

    private static final String SQL_SELECT_RESUMEN = """
            SELECT codigo_cliente,
                   CONCAT(nombres, ' ', apellidos) AS nombre_completo,
                   telefono,
                   estado
            FROM clientes
            """;

    @Override
    public void guardar(Cliente cliente) {
        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT)) {

            pstmt.setString(1, cliente.getCodigoCliente());
            pstmt.setString(2, cliente.getTipoIdentificacion() != null
                    ? cliente.getTipoIdentificacion().name() : TipoIdentificacion.CC.name());
            pstmt.setString(3, cliente.getIdentificacion());
            pstmt.setString(4, cliente.getNombre());
            pstmt.setString(5, cliente.getApellido());
            pstmt.setString(6, cliente.getTelefono());
            pstmt.setString(7, cliente.getDireccion());
            pstmt.setString(8, cliente.getTipoCliente() != null
                    ? cliente.getTipoCliente().name() : TipoCliente.MINORISTA.name());
            pstmt.setString(9, cliente.isActivo() ? "Activo" : "Inactivo");

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
    }

    @Override
    public void eliminar(String id) {
        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {

            pstmt.setString(1, id);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> listaCompleta = new ArrayList<>();

        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaCompleta.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
        return listaCompleta;
    }

    @Override
    public Cliente buscarPorId(String identificacion) {
        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            pstmt.setString(1, identificacion);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
        return null;
    }

    @Override
    public List<ClienteResumenDTO> listarResumen() {
        List<ClienteResumenDTO> listaResumen = new ArrayList<>();

        try (Connection conn = ConexionSql.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_RESUMEN);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaResumen.add(new ClienteResumenDTO(
                        rs.getString("codigo_cliente"),
                        rs.getString("nombre_completo"),
                        rs.getString("telefono"),
                        rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            throw ExcepcionAccesoDatos.desde(e);
        }
        return listaResumen;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        TipoIdentificacion tipoId = TipoIdentificacion.valueOf(rs.getString("tipo_id"));
        TipoCliente tipoCli = TipoCliente.valueOf(rs.getString("tipo_cliente"));

        Cliente cliente = new Cliente(
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("identificacion"),
                rs.getString("direccion"),
                rs.getString("telefono"),
                rs.getString("codigo_cliente"),
                tipoId,
                tipoCli
        );
        cliente.setActivo("Activo".equalsIgnoreCase(rs.getString("estado")));
        return cliente;
    }
}
