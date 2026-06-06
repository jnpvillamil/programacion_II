package co.uptc.edu.tienda.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.conexion.Conexion;
import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.enums.TipoClienteEnum;
import co.uptc.edu.tienda.enums.TipoDocEnum;
import co.uptc.edu.tienda.interfaces.IGestionCliente;
import co.uptc.edu.tienda.modelo.Cliente;

public class SqlCliente implements IGestionCliente {

    @Override
    public void guardar(Cliente c) {
        Conexion conex = new Conexion();
        try {
            String sql = "INSERT INTO clientes (nombre_completo, tipo_documento, numero_documento, "
                       + "direccion, tipo_cliente, telefono, estado) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, c.getNombreCompleto());
            ps.setString(2, c.getTipoDocumento().name());
            ps.setLong(3, c.getNumeroDocumento());
            ps.setString(4, c.getDireccionC());
            ps.setString(5, c.getTipoCliente().name());
            ps.setLong(6, c.getTelefonoC());
            ps.setString(7, c.getEstado().name());
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Cliente c) {
        Conexion conex = new Conexion();
        try {
            String sql = "UPDATE clientes SET nombre_completo=?, tipo_documento=?, numero_documento=?, "
                       + "direccion=?, tipo_cliente=?, telefono=?, estado=? WHERE id_cliente=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, c.getNombreCompleto());
            ps.setString(2, c.getTipoDocumento().name());
            ps.setLong(3, c.getNumeroDocumento());
            ps.setString(4, c.getDireccionC());
            ps.setString(5, c.getTipoCliente().name());
            ps.setLong(6, c.getTelefonoC());
            ps.setString(7, c.getEstado().name());
            ps.setInt(8, c.getIdCliente());
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idCliente) {
        cambiarEstado(idCliente, EstadoEnum.INACTIVO);
    }

    @Override
    public Cliente buscar(int idCliente) {
        Conexion conex = new Conexion();
        try {
            String sql = "SELECT * FROM clientes WHERE id_cliente=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Cliente> leerClientes() {
        Conexion conex = new Conexion();
        List<Cliente> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM clientes";
            Statement st = conex.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) lista.add(mapear(rs));
            st.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al leer clientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void cambiarEstado(int idCliente, EstadoEnum nuevoEstado) {
        Conexion conex = new Conexion();
        try {
            String sql = "UPDATE clientes SET estado=? WHERE id_cliente=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, idCliente);
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado cliente: " + e.getMessage());
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente(rs.getInt("id_cliente"));
        c.setNombreCompleto(rs.getString("nombre_completo"));
        c.setTipoDocumento(TipoDocEnum.valueOf(rs.getString("tipo_documento")));
        c.setNumeroDocumento(rs.getLong("numero_documento"));
        c.setDireccionC(rs.getString("direccion"));
        c.setTipoCliente(TipoClienteEnum.valueOf(rs.getString("tipo_cliente")));
        c.setTelefonoC(rs.getLong("telefono"));
        c.setEstado(EstadoEnum.valueOf(rs.getString("estado")));
        return c;
    }
}