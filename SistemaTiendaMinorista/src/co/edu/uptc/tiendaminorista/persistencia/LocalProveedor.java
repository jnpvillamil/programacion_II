package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.tiendaminorista.interfaces.IGestionProveedor;
import co.edu.uptc.tiendaminorista.modelo.Proveedor;

// Cambie el JSON a MySQL - copie el patron de LocalCliente para no reinventar la rueda
// El generarCodigo lo hice igual que en clientes pero con PRO
public class LocalProveedor implements IGestionProveedor {

    @Override
    public void guardar(Proveedor proveedor) {
        String sql = "INSERT INTO proveedores (codigo, nombre, nit, correo, direccion, telefono, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (proveedor.getCodigo() == null || proveedor.getCodigo().isEmpty()) {
                proveedor.setCodigo(generarCodigo());
            }
            proveedor.setActivo(true);
            pstmt.setString(1, proveedor.getCodigo());
            pstmt.setString(2, proveedor.getNombre());
            pstmt.setString(3, proveedor.getNit());
            pstmt.setString(4, proveedor.getCorreo());
            pstmt.setString(5, proveedor.getDireccion());
            pstmt.setString(6, proveedor.getTelefono());
            pstmt.setBoolean(7, proveedor.isActivo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearProveedor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void actualizar(Proveedor proveedor) {
        String sql = "UPDATE proveedores SET nombre=?, nit=?, correo=?, direccion=?, telefono=? WHERE codigo=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getNit());
            pstmt.setString(3, proveedor.getCorreo());
            pstmt.setString(4, proveedor.getDireccion());
            pstmt.setString(5, proveedor.getTelefono());
            pstmt.setString(6, proveedor.getCodigo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void desactivar(String codigo) {
        cambiarEstado(codigo, false);
    }

    @Override
    public void activar(String codigo) {
        cambiarEstado(codigo, true);
    }

    private void cambiarEstado(String codigo, boolean activo) {
        String sql = "UPDATE proveedores SET activo = ? WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, activo);
            pstmt.setString(2, codigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // MÉTODOS AUXILIARES
    private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor();
        p.setCodigo(rs.getString("codigo"));
        p.setNombre(rs.getString("nombre"));
        p.setNit(rs.getString("nit"));
        p.setCorreo(rs.getString("correo"));
        p.setDireccion(rs.getString("direccion"));
        p.setTelefono(rs.getString("telefono"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }

    private String generarCodigo() {
        String ultimo = "";
        String sql = "SELECT codigo FROM proveedores ORDER BY codigo DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                ultimo = rs.getString("codigo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int num = 1;
        if (ultimo != null && ultimo.startsWith("PRO")) {
            try {
                num = Integer.parseInt(ultimo.substring(3)) + 1;
            } catch (NumberFormatException e) {
                num = 1;
            }
        }
        return "PRO" + num;
    }
}
