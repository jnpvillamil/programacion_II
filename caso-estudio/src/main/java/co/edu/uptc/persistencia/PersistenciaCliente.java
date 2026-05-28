package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.utilidades.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCliente implements Repositorio<Cliente> {

    @Override
    public void guardar(Cliente cliente) {
        String sql = """
            INSERT INTO clientes (codigo_cliente, nombre_completo, tipo_identificacion,
                numero_identificacion, direccion, telefono, tipo_cliente, activo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            llenarStatement(ps, cliente);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar cliente en BD: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String id) {
        String sql = "UPDATE clientes SET activo = 0 WHERE codigo_cliente = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al inactivar cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente buscarPorId(String id) {
        String sql = """
            SELECT codigo_cliente, nombre_completo, tipo_identificacion, numero_identificacion,
                   direccion, telefono, tipo_cliente, activo
            FROM clientes
            WHERE codigo_cliente = ? OR numero_identificacion = ?
            """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = """
            SELECT codigo_cliente, nombre_completo, tipo_identificacion, numero_identificacion,
                   direccion, telefono, tipo_cliente, activo
            FROM clientes
            """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void actualizar(Cliente cliente) {
        String sql = """
            UPDATE clientes SET nombre_completo = ?, tipo_identificacion = ?,
                numero_identificacion = ?, direccion = ?, telefono = ?,
                tipo_cliente = ?, activo = ?
            WHERE codigo_cliente = ?
            """;
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTipoIdentificacion() != null
                    ? cliente.getTipoIdentificacion().name() : TipoIdentificacion.CC.name());
            ps.setString(3, cliente.getIdentificacion());
            ps.setString(4, cliente.getDireccion());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getTipoCliente() != null
                    ? cliente.getTipoCliente().name() : TipoCliente.MINORISTA.name());
            ps.setBoolean(7, cliente.isActivo());
            ps.setString(8, cliente.getCodigoCliente());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    private void llenarStatement(PreparedStatement ps, Cliente c) throws SQLException {
        ps.setString(1, c.getCodigoCliente());
        ps.setString(2, c.getNombre());
        ps.setString(3, c.getTipoIdentificacion() != null ? c.getTipoIdentificacion().name() : TipoIdentificacion.CC.name());
        ps.setString(4, c.getIdentificacion());
        ps.setString(5, c.getDireccion());
        ps.setString(6, c.getTelefono());
        ps.setString(7, c.getTipoCliente() != null ? c.getTipoCliente().name() : TipoCliente.MINORISTA.name());
        ps.setBoolean(8, c.isActivo());
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setCodigoCliente(rs.getString("codigo_cliente"));
        c.setNombre(rs.getString("nombre_completo"));
        c.setIdentificacion(rs.getString("numero_identificacion"));
        c.setDireccion(rs.getString("direccion"));
        c.setTelefono(rs.getString("telefono"));
        c.setTipoIdentificacion(TipoIdentificacion.desdeTexto(rs.getString("tipo_identificacion")));
        c.setTipoCliente(TipoCliente.desdeTexto(rs.getString("tipo_cliente")));
        c.setActivo(rs.getBoolean("activo"));
        return c;
    }
}
