package co.edu.uptc.tiendaminorista.persistencia;

import co.edu.uptc.tiendaminorista.interfaces.IGestionCliente;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalCliente implements IGestionCliente {

    //CRUD PRINCIPAL 
    @Override
    public void guardar(Cliente cliente) {
        String sql = "INSERT INTO clientes (codigo, nombre, tipoIdentificacion, numeroIdentificacion, direccion, telefono, tipoCliente, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            if (cliente.getCodigo() == null || cliente.getCodigo().isEmpty()) {
                cliente.setCodigo(generarCodigo());
            }
            cliente.setActivo(true);

            pstmt.setString(1, cliente.getCodigo());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getNumeroIdentificacion());
         
            pstmt.setString(5, cliente.getDireccion());
            pstmt.setString(6, cliente.getTelefono());
            pstmt.setString(7, cliente.getTipoCliente());
            pstmt.setBoolean(8, cliente.isActivo());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, tipoIdentificacion=?, numeroIdentificacion=?, direccion=?, telefono=?, tipoCliente=?, activo=? WHERE codigo=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getNumeroIdentificacion());
            pstmt.setString(3, cliente.getNumeroIdentificacion());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setString(5, cliente.getTelefono());
            pstmt.setString(6, cliente.getTipoCliente());
            pstmt.setBoolean(7, cliente.isActivo());
            pstmt.setString(8, cliente.getCodigo());

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
        String sql = "UPDATE clientes SET activo = ? WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, activo);
            pstmt.setString(2, codigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> buscar(String texto) {
        List<Cliente> resultado = new ArrayList<>();
    
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ? OR numeroIdentificacion LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String like = "%" + (texto == null ? "" : texto) + "%";
            pstmt.setString(1, like);
            pstmt.setString(2, like);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // MÉTODOS AUXILIARES 
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setCodigo(rs.getString("codigo"));
        c.setNombre(rs.getString("nombre"));
        c.setNumeroIdentificacion(rs.getString("numeroIdentificacion"));
        c.setDireccion(rs.getString("direccion"));
        c.setTelefono(rs.getString("telefono"));
        c.setTipoCliente(rs.getString("tipoCliente"));
        c.setActivo(rs.getBoolean("activo"));
        return c;
    }

  
    private String generarCodigo() {

        String ultimo = "";
        String sql = "SELECT codigo FROM clientes ORDER BY codigo DESC LIMIT 1";
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
        if (ultimo != null && ultimo.startsWith("CLI")) {
            try {
                num = Integer.parseInt(ultimo.substring(3)) + 1;
            } catch (NumberFormatException e) {
                num = 1;
            }
        } else {
            // si no hay registros, empezamos desde el 1
            num = 1;
        }
        return "CLI" + num;
    }
}