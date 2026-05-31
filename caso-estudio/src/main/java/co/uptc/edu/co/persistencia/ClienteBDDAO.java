package co.uptc.edu.co.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.conexion.ConexionBD;
import co.uptc.edu.co.interfaces.ClienteDAO;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.enums.EstadoEnum;
import co.uptc.edu.co.modelo.enums.TipoClienteEnum;
import co.uptc.edu.co.modelo.enums.TipoDocEnum;

public class ClienteBDDAO implements ClienteDAO {

    private static final String TABLA = "clientes";

    private static final String SQL_INSERTAR =
            "INSERT INTO " + TABLA + " "
            + "(codigoCliente, nombre, tipoIdentificacion, "
            + "numeroIdentificacion, direccion, telefono, "
            + "tipoCliente, estado) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_LISTAR =
            "SELECT * FROM " + TABLA;

    private static final String SQL_BUSCAR =
            "SELECT * FROM " + TABLA + " "
            + "WHERE codigoCliente = ?";

    private static final String SQL_ACTUALIZAR =
            "UPDATE " + TABLA + " SET "
            + "nombre = ?, "
            + "tipoIdentificacion = ?, "
            + "numeroIdentificacion = ?, "
            + "direccion = ?, "
            + "telefono = ?, "
            + "tipoCliente = ?, "
            + "estado = ? "
            + "WHERE codigoCliente = ?";

    @Override
    public void guardarCliente(Cliente cliente)
            throws Exception {

        try (Connection connection =
                     ConexionBD.getConexion();

             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             SQL_INSERTAR)) {

            prepararInsert(preparedStatement, cliente);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            throw new Exception(
                    "Error al guardar cliente: "
                            + e.getMessage(), e
            );
        }
    }

    @Override
    public void actualizarCliente(Cliente cliente)
            throws Exception {

        try (Connection connection =
                     ConexionBD.getConexion();

             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             SQL_ACTUALIZAR)) {

            prepararActualizar(
                    preparedStatement,
                    cliente
            );

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            throw new Exception(
                    "Error al actualizar cliente: "
                            + e.getMessage(), e
            );
        }
    }

    @Override
    public Cliente buscarPorCodigo(String codigo)
            throws Exception {

        try (Connection connection =
                     ConexionBD.getConexion();

             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             SQL_BUSCAR)) {

            preparedStatement.setString(1, codigo);

            try (ResultSet resultSet =
                         preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    return construirCliente(resultSet);
                }
            }

        } catch (SQLException e) {

            throw new Exception(
                    "Error al buscar cliente: "
                            + e.getMessage(), e
            );
        }

        return null;
    }

    @Override
    public List<Cliente> listarClientes()
            throws Exception {

        List<Cliente> lista = new ArrayList<>();

        try (Connection connection =
                     ConexionBD.getConexion();

             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             SQL_LISTAR);

             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                lista.add(
                        construirCliente(resultSet)
                );
            }

        } catch (SQLException e) {

            throw new Exception(
                    "Error al listar clientes: "
                            + e.getMessage(), e
            );
        }

        return lista;
    }

    private Cliente construirCliente(
            ResultSet resultSet)
            throws SQLException {

        Cliente cliente = new Cliente();

        cliente.setCodigo(
                resultSet.getString(
                        "codigoCliente"));

        cliente.setNombre(
                resultSet.getString(
                        "nombre"));

        cliente.setTipoIdentificacion(
                TipoDocEnum.valueOf(
                        resultSet.getString(
                                "tipoIdentificacion")));

        cliente.setNumeroIdentificacion(
                resultSet.getString(
                        "numeroIdentificacion"));

        cliente.setDireccion(
                resultSet.getString(
                        "direccion"));

        cliente.setTelefono(
                resultSet.getString(
                        "telefono"));

        cliente.setTipoCliente(
                TipoClienteEnum.valueOf(
                        resultSet.getString(
                                "tipoCliente")));

        cliente.setEstado(
                EstadoEnum.valueOf(
                        resultSet.getString(
                                "estado")));

        return cliente;
    }

    private void prepararInsert(
            PreparedStatement preparedStatement,
            Cliente cliente)
            throws SQLException {

        preparedStatement.setString(
                1,
                cliente.getCodigo());

        preparedStatement.setString(
                2,
                cliente.getNombre());

        preparedStatement.setString(
                3,
                cliente.getTipoIdentificacion().name());

        preparedStatement.setString(
                4,
                cliente.getNumeroIdentificacion());

        preparedStatement.setString(
                5,
                cliente.getDireccion());

        preparedStatement.setString(
                6,
                cliente.getTelefono());

        preparedStatement.setString(
                7,
                cliente.getTipoCliente().name());

        preparedStatement.setString(
                8,
                cliente.getEstado().name());
    }

    private void prepararActualizar(
            PreparedStatement preparedStatement,
            Cliente cliente)
            throws SQLException {

        preparedStatement.setString(
                1,
                cliente.getNombre());

        preparedStatement.setString(
                2,
                cliente.getTipoIdentificacion().name());

        preparedStatement.setString(
                3,
                cliente.getNumeroIdentificacion());

        preparedStatement.setString(
                4,
                cliente.getDireccion());

        preparedStatement.setString(
                5,
                cliente.getTelefono());

        preparedStatement.setString(
                6,
                cliente.getTipoCliente().name());

        preparedStatement.setString(
                7,
                cliente.getEstado().name());

        preparedStatement.setString(
                8,
                cliente.getCodigo());
    }
}
