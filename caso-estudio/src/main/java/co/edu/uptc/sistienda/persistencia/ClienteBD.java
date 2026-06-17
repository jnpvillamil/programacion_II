package co.edu.uptc.sistienda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.sistienda.interfaces.IGestionCliente;
import co.edu.uptc.sistienda.modelo.Cliente;
import co.edu.uptc.sistienda.modelo.enums.TipoClienteEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoIdentificacionEnum;
import co.edu.uptc.sistienda.modelo.enums.TipoPersonaEnum;

//Esta clase guarda, actualiza, busca y lista clientes en la base de datos MySQL
public class ClienteBD implements IGestionCliente {

    // GUARDAR un cliente nuevo en la tabla clientes

    @Override
    public void guardarCliente(Cliente cliente) {

        String sql = "INSERT INTO clientes "
                   + "(codigo_cliente, nombre_razon_social, tipo_identificacion, "
                   + " numero_identificacion, direccion, ciudad, telefono, "
                   + " correo_electronico, tipo_cliente, tipo_persona, "
                   + " responsabilidad_fiscal, responsabilidad_tributaria, activo) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

        try (Connection conexion       = ConexionBD.conectar();
             PreparedStatement insertarCliente = conexion.prepareStatement(sql)) {

            insertarCliente.setString(1,  cliente.getCodigoCliente());
            insertarCliente.setString(2,  cliente.getNombreCompletoORazonSocial());
            insertarCliente.setString(3,  cliente.getTipoIdentificacion().name());
            insertarCliente.setString(4,  cliente.getNumeroIdentificacion());
            insertarCliente.setString(5,  cliente.getDireccion());
            insertarCliente.setString(6,  cliente.getCiudad());
            insertarCliente.setString(7,  cliente.getTelefono());
            insertarCliente.setString(8,  cliente.getCorreoElectronico());
            insertarCliente.setString(9,  cliente.getTipoCliente().name());
            insertarCliente.setString(10, cliente.getTipoPersona().name());
            insertarCliente.setString(11, cliente.getResponsabilidadFiscal());
            insertarCliente.setString(12, cliente.getResponsabilidadTributaria());

            insertarCliente.executeUpdate();

            JOptionPane.showMessageDialog(null,
                "Cliente guardado exitosamente", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo guardar el cliente", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // ACTUALIZAR los datos de un cliente que ya existe
    @Override
    public void actualizarCliente(Cliente cliente) {

        // El último ? del WHERE identifica qué cliente se va a actualizar
        String sql = "UPDATE clientes SET "
                   + "nombre_razon_social = ?, tipo_identificacion = ?, "
                   + "numero_identificacion = ?, direccion = ?, ciudad = ?, "
                   + "telefono = ?, correo_electronico = ?, tipo_cliente = ?, "
                   + "tipo_persona = ?, responsabilidad_fiscal = ?, "
                   + "responsabilidad_tributaria = ? "
                   + "WHERE codigo_cliente = ?";

        try (Connection conexion              = ConexionBD.conectar();
             PreparedStatement actualizarCliente = conexion.prepareStatement(sql)) {

            actualizarCliente.setString(1,  cliente.getNombreCompletoORazonSocial());
            actualizarCliente.setString(2,  cliente.getTipoIdentificacion().name());
            actualizarCliente.setString(3,  cliente.getNumeroIdentificacion());
            actualizarCliente.setString(4,  cliente.getDireccion());
            actualizarCliente.setString(5,  cliente.getCiudad());
            actualizarCliente.setString(6,  cliente.getTelefono());
            actualizarCliente.setString(7,  cliente.getCorreoElectronico());
            actualizarCliente.setString(8,  cliente.getTipoCliente().name());
            actualizarCliente.setString(9,  cliente.getTipoPersona().name());
            actualizarCliente.setString(10, cliente.getResponsabilidadFiscal());
            actualizarCliente.setString(11, cliente.getResponsabilidadTributaria());
            actualizarCliente.setString(12, cliente.getCodigoCliente()); // ← condición WHERE

            actualizarCliente.executeUpdate();

            JOptionPane.showMessageDialog(null,
                "Cliente actualizado correctamente", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo actualizar el cliente", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // INACTIVAR un cliente (no se elimina, solo se marca como inactivo)
    
    @Override
    public void inactivarCliente(String codigoCliente) {

        String sql = "UPDATE clientes SET activo = 0 WHERE codigo_cliente = ?";

        try (Connection conexion               = ConexionBD.conectar();
             PreparedStatement inactivarCliente = conexion.prepareStatement(sql)) {

            inactivarCliente.setString(1, codigoCliente);
            inactivarCliente.executeUpdate();

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo inactivar el cliente", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // ACTIVAR un cliente que estaba inactivo
    
    @Override
    public void activarCliente(String codigoCliente) {

        String sql = "UPDATE clientes SET activo = 1 WHERE codigo_cliente = ?";

        try (Connection conexion             = ConexionBD.conectar();
             PreparedStatement activarCliente = conexion.prepareStatement(sql)) {

            activarCliente.setString(1, codigoCliente);
            activarCliente.executeUpdate();

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo activar el cliente", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // BUSCAR un cliente por su código

    @Override
    public Cliente buscarClientePorCodigo(String codigoCliente) {

        String sql = "SELECT * FROM clientes WHERE codigo_cliente = ?";

        try (Connection conexion             = ConexionBD.conectar();
             PreparedStatement buscarCliente = conexion.prepareStatement(sql)) {

            buscarCliente.setString(1, codigoCliente);
            ResultSet clienteEncontrado = buscarCliente.executeQuery();

            if (clienteEncontrado.next()) {
                return convertirFilaEnCliente(clienteEncontrado);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo buscar el cliente", "Error",
                JOptionPane.ERROR_MESSAGE);
        }

        return null; // No se encontró ningún cliente con ese código
    }

    // LISTAR todos los clientes de la base de datos
    @Override
    public List<Cliente> obtenerListaClientes() {

        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nombre_razon_social";

        try (Connection conexion              = ConexionBD.conectar();
             PreparedStatement traerClientes  = conexion.prepareStatement(sql);
             ResultSet todosLosClientes       = traerClientes.executeQuery()) {

            while (todosLosClientes.next()) {
                listaClientes.add(convertirFilaEnCliente(todosLosClientes));
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo obtener la lista de clientes", "Error",
                JOptionPane.ERROR_MESSAGE);
        }

        return listaClientes;
    }

    // Método que convierte una fila de la tabla en un objeto Cliente
    private Cliente convertirFilaEnCliente(ResultSet filaDelCliente) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setCodigoCliente             (filaDelCliente.getString("codigo_cliente"));
        cliente.setNombreCompletoORazonSocial(filaDelCliente.getString("nombre_razon_social"));
        cliente.setTipoIdentificacion        (TipoIdentificacionEnum.valueOf(filaDelCliente.getString("tipo_identificacion")));
        cliente.setNumeroIdentificacion      (filaDelCliente.getString("numero_identificacion"));
        cliente.setDireccion                 (filaDelCliente.getString("direccion"));
        cliente.setCiudad                    (filaDelCliente.getString("ciudad"));
        cliente.setTelefono                  (filaDelCliente.getString("telefono"));
        cliente.setCorreoElectronico         (filaDelCliente.getString("correo_electronico"));
        cliente.setTipoCliente               (TipoClienteEnum.valueOf(filaDelCliente.getString("tipo_cliente")));
        cliente.setTipoPersona               (TipoPersonaEnum.valueOf(filaDelCliente.getString("tipo_persona")));
        cliente.setResponsabilidadFiscal     (filaDelCliente.getString("responsabilidad_fiscal"));
        cliente.setResponsabilidadTributaria (filaDelCliente.getString("responsabilidad_tributaria"));
        cliente.setActivo                    (filaDelCliente.getInt("activo") == 1);
        return cliente;
    }
}

