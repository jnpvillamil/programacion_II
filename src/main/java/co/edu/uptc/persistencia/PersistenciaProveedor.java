package co.edu.uptc.persistencia;

import co.edu.uptc.dto.ProveedorResumenDTO;
import co.edu.uptc.modelo.Proveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaProveedor {

    private static final String SQL_INSERT = """
            INSERT INTO proveedor (
                codigo_proveedor, nit, razon_social, representante_legal,
                correo_electronico, telefono, direccion, estado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_UPDATE = """
            UPDATE proveedor
            SET nit = ?, razon_social = ?, representante_legal = ?,
                correo_electronico = ?, telefono = ?, direccion = ?, estado = ?
            WHERE codigo_proveedor = ?
            """;

    private static final String SQL_DELETE = """
            DELETE FROM proveedor
            WHERE codigo_proveedor = ?
            """;

    private static final String SQL_SELECT_ALL = """
            SELECT codigo_proveedor, nit, razon_social, representante_legal,
                   correo_electronico, telefono, direccion, estado
            FROM proveedor
            ORDER BY razon_social
            """;

    private static final String SQL_SELECT_BY_CODIGO = """
            SELECT codigo_proveedor, nit, razon_social, representante_legal,
                   correo_electronico, telefono, direccion, estado
            FROM proveedor
            WHERE codigo_proveedor = ?
            """;

    private static final String SQL_SELECT_BY_NIT = """
            SELECT codigo_proveedor, nit, razon_social, representante_legal,
                   correo_electronico, telefono, direccion, estado
            FROM proveedor
            WHERE nit = ?
            """;

    private static final String SQL_EXISTE_NIT = """
            SELECT COUNT(1) AS total
            FROM proveedor
            WHERE nit = ?
            """;

    private static final String SQL_SELECT_RESUMEN = """
            SELECT nit, razon_social, correo_electronico, telefono, estado
            FROM proveedor
            ORDER BY razon_social
            """;

    private static final String SQL_ACTIVAR_POR_NIT = """
            UPDATE proveedor SET estado = 'Activo' WHERE nit = ?
            """;

    private static final String SQL_INACTIVAR_POR_NIT = """
            UPDATE proveedor SET estado = 'Inactivo' WHERE nit = ?
            """;

    public void guardar(Proveedor proveedor) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERT)) {

            asignarParametroInsercion(sentencia, proveedor);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public void actualizar(Proveedor proveedor) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_UPDATE)) {

            sentencia.setString(1, proveedor.getNit());
            sentencia.setString(2, proveedor.getRazonSocial());
            sentencia.setString(3, proveedor.getNombre());
            sentencia.setString(4, proveedor.getCorreoElectronico());
            sentencia.setString(5, proveedor.getTelefono());
            sentencia.setString(6, proveedor.getDireccion());
            sentencia.setString(7, proveedor.isActivo() ? "Activo" : "Inactivo");
            sentencia.setString(8, proveedor.getCodigoProveedor());
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public void eliminar(String codigoProveedor) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_DELETE)) {

            sentencia.setString(1, codigoProveedor);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public List<Proveedor> listar() {
        List<Proveedor> listaProveedor = new ArrayList<>();

        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                listaProveedor.add(mapearProveedor(resultado));
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return listaProveedor;
    }

    public Proveedor buscarPorId(String codigoProveedor) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_BY_CODIGO)) {

            sentencia.setString(1, codigoProveedor);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearProveedor(resultado);
                }
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return null;
    }

    public Proveedor buscarPorNit(String nit) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_BY_NIT)) {

            sentencia.setString(1, nit);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapearProveedor(resultado);
                }
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return null;
    }

    public boolean existeNit(String nit) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_EXISTE_NIT)) {

            sentencia.setString(1, nit);
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

    public List<ProveedorResumenDTO> listarResumen() {
        List<ProveedorResumenDTO> listaResumen = new ArrayList<>();

        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_SELECT_RESUMEN);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                listaResumen.add(new ProveedorResumenDTO(
                        resultado.getString("nit"),
                        resultado.getString("razon_social"),
                        resultado.getString("correo_electronico"),
                        valorSeguro(resultado.getString("telefono")),
                        resultado.getString("estado")
                ));
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return listaResumen;
    }

    public void activarPorNit(String nit) {
        ejecutarActualizacionEstado(SQL_ACTIVAR_POR_NIT, nit);
    }

    public void inactivarPorNit(String nit) {
        ejecutarActualizacionEstado(SQL_INACTIVAR_POR_NIT, nit);
    }

    private void ejecutarActualizacionEstado(String sql, String nit) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, nit);
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    private void asignarParametroInsercion(PreparedStatement sentencia, Proveedor proveedor) throws SQLException {
        sentencia.setString(1, proveedor.getCodigoProveedor());
        sentencia.setString(2, proveedor.getNit());
        sentencia.setString(3, proveedor.getRazonSocial());
        sentencia.setString(4, proveedor.getNombre());
        sentencia.setString(5, proveedor.getCorreoElectronico());
        sentencia.setString(6, proveedor.getTelefono());
        sentencia.setString(7, proveedor.getDireccion());
        sentencia.setString(8, proveedor.isActivo() ? "Activo" : "Inactivo");
    }

    private Proveedor mapearProveedor(ResultSet resultado) throws SQLException {
        Proveedor proveedor = new Proveedor(
                resultado.getString("representante_legal"),
                "",
                resultado.getString("nit"),
                valorSeguro(resultado.getString("direccion")),
                valorSeguro(resultado.getString("telefono")),
                resultado.getString("codigo_proveedor"),
                resultado.getString("razon_social"),
                resultado.getString("nit"),
                resultado.getString("correo_electronico")
        );
        proveedor.setActivo("Activo".equalsIgnoreCase(resultado.getString("estado")));
        return proveedor;
    }

    private String valorSeguro(String valor) {
        return valor != null ? valor : "";
    }
}
