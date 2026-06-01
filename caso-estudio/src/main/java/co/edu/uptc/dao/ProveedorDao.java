package co.edu.uptc.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.modelo.Proveedor;

public class ProveedorDao {

    public void registrarProveedor(Proveedor prov) throws Exception {
        Conexion conex = new Conexion();

        String sql = "INSERT INTO proveedor (nit, razon_social, telefono, direccion, correo, producto_suministrado) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setString(1, prov.getNit());
            pst.setString(2, prov.getRazonSocial());
            pst.setString(3, prov.getTelefono());
            pst.setString(4, prov.getDireccion());
            pst.setString(5, prov.getCorreo());
            pst.setString(6, prov.getProductoSuministrado());
            pst.executeUpdate();
        } finally {
            conex.desconectar();
        }
    }

    public void actualizarProveedor(Proveedor prov) throws Exception {
        Conexion conex = new Conexion();

        String sql = "UPDATE proveedor SET razon_social=?, telefono=?, direccion=?, correo=?, producto_suministrado=? WHERE nit=?";
        
        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setString(1, prov.getRazonSocial());
            pst.setString(2, prov.getTelefono());
            pst.setString(3, prov.getDireccion());
            pst.setString(4, prov.getCorreo());
            pst.setString(5, prov.getProductoSuministrado());
            pst.setString(6, prov.getNit());
            pst.executeUpdate();
        } finally {
            conex.desconectar();
        }
    }

    public void eliminarProveedor(String nit) throws Exception {
        Conexion conex = new Conexion();
        String sql = "DELETE FROM proveedor WHERE nit = ?";
        
        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setString(1, nit);
            pst.executeUpdate();
        } finally {
            conex.desconectar();
        }
    }

    public void inactivarProveedor(String nit) throws Exception {
        Conexion conex = new Conexion();
        String sql = "UPDATE proveedor SET direccion = 'INACTIVO' WHERE nit = ?";
        
        try (Connection c = conex.getConnection();
             PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setString(1, nit);
            pst.executeUpdate();
        } finally {
            conex.desconectar();
        }
    }

    public List<Proveedor> listarProveedores() throws Exception {
        List<Proveedor> lista = new ArrayList<>();
        Conexion conex = new Conexion();
        String sql = "SELECT nit, razon_social, telefono, direccion, correo, producto_suministrado FROM proveedor";
        
        try (Connection c = conex.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setNit(rs.getString("nit"));
                p.setRazonSocial(rs.getString("razon_social"));
                p.setTelefono(rs.getString("telefono"));
                p.setDireccion(rs.getString("direccion"));
                p.setCorreo(rs.getString("correo"));
                p.setProductoSuministrado(rs.getString("producto_suministrado"));
                lista.add(p);
            }
        } finally {
            conex.desconectar();
        }
        return lista;
    }
}