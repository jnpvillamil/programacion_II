package co.edu.uptc.persistencia.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.interfaces.IGestionProveedor;
import co.edu.uptc.negocio.dto.proveedorDto;

public class DatabaseProveedor implements IGestionProveedor {

    private Conexion conexObj = new Conexion();

    @Override
    public void guardar(proveedorDto p) {
        String sql = "INSERT INTO proveedor (codigo, razon_social, nit, direccion, telefono, correo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, p.getCodigoProveedor());
            ps.setString(2, p.getRazonSocial());
            ps.setString(3, p.getNit());
            ps.setString(4, p.getDireccion());
            ps.setLong(5, p.getTelefono());
            ps.setString(6, p.getCorreo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al guardar proveedor: " + e.getMessage());
        }
    }

    @Override
    public List<proveedorDto> listar() {
        List<proveedorDto> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";
        try (Connection conex = conexObj.getConnection();
                PreparedStatement ps = conex.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public proveedorDto buscar(int codigo) {
        String sql = "SELECT * FROM proveedor WHERE codigo = ?";
        try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    /** Búsqueda por nombre parcial — usado en PanelCompra para autocompletado */
    public int buscarCodigoPorNombre(String razonSocial) {
        String sql = "SELECT codigo FROM proveedor WHERE razon_social LIKE ?";
        try (Connection conex = conexObj.getConnection(); PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setString(1, "%" + razonSocial + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar código por nombre: " + e.getMessage());
        }
        return -1;
    }

    private proveedorDto mapRow(ResultSet rs) throws SQLException {
        proveedorDto p = new proveedorDto();
        p.setCodigoProveedor(rs.getInt("codigo"));
        p.setRazonSocial(rs.getString("razon_social"));
        p.setNit(rs.getString("nit"));
        p.setDireccion(rs.getString("direccion"));
        p.setTelefono(rs.getLong("telefono"));
        p.setCorreo(rs.getString("correo"));
        return p;
    }

    @Override
    public void actualizar(proveedorDto p) {
        // Pendiente de implementar
    }

    @Override
    public void eliminar(int codigo) {
        // Pendiente de implementar
    }
}