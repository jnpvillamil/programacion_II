package co.edu.uptc.sistienda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.sistienda.interfaces.IGestionProveedor;
import co.edu.uptc.sistienda.modelo.Proveedor;


// Esta clase guarda, actualiza, busca y lista proveedores en la base de datos MySQL
 
public class ProveedorBD implements IGestionProveedor {

    // GUARDAR un proveedor nuevo en la tabla proveedores
    @Override
    public void guardarProveedor(Proveedor proveedor) {

        String sql = "INSERT INTO proveedores "
                   + "(codigo_proveedor, razon_social, nit, direccion, ciudad, "
                   + " telefono, correo_electronico, responsabilidad_fiscal, "
                   + " responsabilidad_tributaria, actividad_economica, activo) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

        try (Connection conexion                  = ConexionBD.conectar();
             PreparedStatement insertarProveedor  = conexion.prepareStatement(sql)) {

            insertarProveedor.setString(1,  proveedor.getCodigoProveedor());
            insertarProveedor.setString(2,  proveedor.getRazonSocial());
            insertarProveedor.setString(3,  proveedor.getNit());
            insertarProveedor.setString(4,  proveedor.getDireccion());
            insertarProveedor.setString(5,  proveedor.getCiudad());
            insertarProveedor.setString(6,  proveedor.getTelefono());
            insertarProveedor.setString(7,  proveedor.getCorreoElectronico());
            insertarProveedor.setString(8,  proveedor.getResponsabilidadFiscal());
            insertarProveedor.setString(9,  proveedor.getResponsabilidadTributaria());
            insertarProveedor.setString(10, proveedor.getActividadEconomica());

            insertarProveedor.executeUpdate();

            JOptionPane.showMessageDialog(null,
                "Proveedor guardado exitosamente", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo guardar el proveedor", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // ACTUALIZAR los datos de un proveedor que ya existe
    @Override
    public void actualizarProveedor(Proveedor proveedor) {

        String sql = "UPDATE proveedores SET "
                   + "razon_social = ?, nit = ?, direccion = ?, ciudad = ?, "
                   + "telefono = ?, correo_electronico = ?, "
                   + "responsabilidad_fiscal = ?, responsabilidad_tributaria = ?, "
                   + "actividad_economica = ? "
                   + "WHERE codigo_proveedor = ?";

        try (Connection conexion                    = ConexionBD.conectar();
             PreparedStatement actualizarProveedor  = conexion.prepareStatement(sql)) {

            actualizarProveedor.setString(1,  proveedor.getRazonSocial());
            actualizarProveedor.setString(2,  proveedor.getNit());
            actualizarProveedor.setString(3,  proveedor.getDireccion());
            actualizarProveedor.setString(4,  proveedor.getCiudad());
            actualizarProveedor.setString(5,  proveedor.getTelefono());
            actualizarProveedor.setString(6,  proveedor.getCorreoElectronico());
            actualizarProveedor.setString(7,  proveedor.getResponsabilidadFiscal());
            actualizarProveedor.setString(8,  proveedor.getResponsabilidadTributaria());
            actualizarProveedor.setString(9,  proveedor.getActividadEconomica());
            actualizarProveedor.setString(10, proveedor.getCodigoProveedor()); // ← condición WHERE

            actualizarProveedor.executeUpdate();

            JOptionPane.showMessageDialog(null,
                "Proveedor actualizado correctamente", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo actualizar el proveedor", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // INACTIVAR un proveedor
    @Override
    public void inactivarProveedor(String codigoProveedor) {

        String sql = "UPDATE proveedores SET activo = 0 WHERE codigo_proveedor = ?";

        try (Connection conexion                   = ConexionBD.conectar();
             PreparedStatement inactivarProveedor  = conexion.prepareStatement(sql)) {

            inactivarProveedor.setString(1, codigoProveedor);
            inactivarProveedor.executeUpdate();

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo inactivar el proveedor", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // ACTIVAR un proveedor que estaba inactivo
    @Override
    public void activarProveedor(String codigoProveedor) {

        String sql = "UPDATE proveedores SET activo = 1 WHERE codigo_proveedor = ?";

        try (Connection conexion                 = ConexionBD.conectar();
             PreparedStatement activarProveedor  = conexion.prepareStatement(sql)) {

            activarProveedor.setString(1, codigoProveedor);
            activarProveedor.executeUpdate();

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo activar el proveedor", "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // BUSCAR un proveedor por su código
    @Override
    public Proveedor buscarProveedorPorCodigo(String codigoProveedor) {

        String sql = "SELECT * FROM proveedores WHERE codigo_proveedor = ?";

        try (Connection conexion               = ConexionBD.conectar();
             PreparedStatement buscarProveedor = conexion.prepareStatement(sql)) {

            buscarProveedor.setString(1, codigoProveedor);
            ResultSet proveedorEncontrado = buscarProveedor.executeQuery();

            if (proveedorEncontrado.next()) {
                return convertirFilaEnProveedor(proveedorEncontrado);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo buscar el proveedor", "Error",
                JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    // LISTAR todos los proveedores de la base de datos
    @Override
    public List<Proveedor> obtenerListaProveedores() {

        List<Proveedor> listaProveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores ORDER BY razon_social";

        try (Connection conexion                = ConexionBD.conectar();
             PreparedStatement traerProveedores = conexion.prepareStatement(sql);
             ResultSet todosLosProveedores      = traerProveedores.executeQuery()) {

            while (todosLosProveedores.next()) {
                listaProveedores.add(convertirFilaEnProveedor(todosLosProveedores));
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null,
                "No se pudo obtener la lista de proveedores", "Error",
                JOptionPane.ERROR_MESSAGE);
        }

        return listaProveedores;
    }

    // Método que convierte una fila de la tabla en un objeto Proveedor
    private Proveedor convertirFilaEnProveedor(ResultSet filaDelProveedor) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigoProveedor          (filaDelProveedor.getString("codigo_proveedor"));
        proveedor.setRazonSocial              (filaDelProveedor.getString("razon_social"));
        proveedor.setNit                      (filaDelProveedor.getString("nit"));
        proveedor.setDireccion                (filaDelProveedor.getString("direccion"));
        proveedor.setCiudad                   (filaDelProveedor.getString("ciudad"));
        proveedor.setTelefono                 (filaDelProveedor.getString("telefono"));
        proveedor.setCorreoElectronico        (filaDelProveedor.getString("correo_electronico"));
        proveedor.setResponsabilidadFiscal    (filaDelProveedor.getString("responsabilidad_fiscal"));
        proveedor.setResponsabilidadTributaria(filaDelProveedor.getString("responsabilidad_tributaria"));
        proveedor.setActividadEcocomica       (filaDelProveedor.getString("actividad_economica"));
        proveedor.setActivo                   (filaDelProveedor.getInt("activo") == 1);
        return proveedor;
    }
}

