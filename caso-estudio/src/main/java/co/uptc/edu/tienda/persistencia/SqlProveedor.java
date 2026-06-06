package co.uptc.edu.tienda.persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.conexion.Conexion;
import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.interfaces.IGestionProveedor;
import co.uptc.edu.tienda.modelo.Proveedor;

public class SqlProveedor implements IGestionProveedor {

    @Override
    public void guardar(Proveedor p) {
        Conexion conex = new Conexion();
        try {
            String sql = "INSERT INTO proveedores (razon_social, nit, direccion, telefono, correo, estado) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, p.getRazonSocial());
            ps.setString(2, p.getNit());
            ps.setString(3, p.getDireccionP());
            ps.setLong(4, p.getTelefonoP());
            ps.setString(5, p.getCorreoP());
            ps.setString(6, p.getEstado().name());
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al insertar proveedor: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Proveedor p) {
        Conexion conex = new Conexion();
        try {
            String sql = "UPDATE proveedores SET razon_social=?, nit=?, direccion=?, "
                       + "telefono=?, correo=?, estado=? WHERE codigo_proveedor=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, p.getRazonSocial());
            ps.setString(2, p.getNit());
            ps.setString(3, p.getDireccionP());
            ps.setLong(4, p.getTelefonoP());
            ps.setString(5, p.getCorreoP());
            ps.setString(6, p.getEstado().name());
            ps.setInt(7, p.getCodigoProveedor());
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al actualizar proveedor: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int codigoProveedor) {
        cambiarEstado(codigoProveedor, EstadoEnum.INACTIVO);
    }

    @Override
    public Proveedor buscar(int codigoProveedor) {
        Conexion conex = new Conexion();
        try {
            String sql = "SELECT * FROM proveedores WHERE codigo_proveedor=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setInt(1, codigoProveedor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al buscar proveedor: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Proveedor> leerProveedores() {
        Conexion conex = new Conexion();
        List<Proveedor> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM proveedores";
            Statement st = conex.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) lista.add(mapear(rs));
            st.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al leer proveedores: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void cambiarEstado(int codigoProveedor, EstadoEnum nuevoEstado) {
        Conexion conex = new Conexion();
        try {
            String sql = "UPDATE proveedores SET estado=? WHERE codigo_proveedor=?";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, nuevoEstado.name());
            ps.setInt(2, codigoProveedor);
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado: " + e.getMessage());
        }
    }

    private Proveedor mapear(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor(rs.getInt("codigo_proveedor"));
        p.setRazonSocial(rs.getString("razon_social"));
        p.setNit(rs.getString("nit"));
        p.setDireccionP(rs.getString("direccion"));
        p.setTelefonoP(rs.getLong("telefono"));
        p.setCorreoP(rs.getString("correo"));
        p.setEstado(EstadoEnum.valueOf(rs.getString("estado")));
        return p;
    }
}