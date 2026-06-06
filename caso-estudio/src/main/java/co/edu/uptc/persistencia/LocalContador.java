package co.edu.uptc.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.gui.interfaces.IGestionContador;
import co.edu.uptc.gui.modelo.Contador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class LocalContador implements IGestionContador{

	@SuppressWarnings("unused")
	private Connection obtenerConexion() throws SQLException {
        return new Conexion().getConnection();
	
	
}

	@Override
	public void guardar(Contador contador) {
		String sql = "INSERT INTO contador (id, nombre, tarjeta_profesional, telefono) VALUES (?, ?, ?, ?)";
        try (Connection con = obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, contador.getId()); 
            pst.setString(2, contador.getNombre());
            pst.setString(3, contador.getTarjetaProfesional());
            pst.setString(4, contador.getTelefono());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar contador: " + e.getMessage());
        }
	}
        @Override
        public void actualizar(Contador contador) {
            String sql = "UPDATE contador SET nombre = ?, tarjeta_profesional = ?, telefono = ? WHERE id = ?";
            try (Connection con = obtenerConexion();
                 PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, contador.getNombre());
                pst.setString(2, contador.getTarjetaProfesional());
                pst.setString(3, contador.getTelefono());
                pst.setLong(4, contador.getId());
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error al actualizar contador: " + e.getMessage());
            }
        }

        @Override
        public void eliminar(Long idContador) {
            String sql = "DELETE FROM contador WHERE id = ?";
            try (Connection con = obtenerConexion();
                 PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, idContador);
                pst.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error al eliminar contador: " + e.getMessage());
            }
        }

        @Override
        public Contador buscar(Long idContador) {
            String sql = "SELECT id, nombre, tarjeta_profesional, telefono FROM contador WHERE id = ?";
            try (Connection con = obtenerConexion();
                 PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, idContador);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return new Contador(rs.getLong("id"), rs.getString("nombre"), rs.getString("tarjeta_profesional"), rs.getString("telefono"));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al buscar contador: " + e.getMessage());
            }
            return null;
        }

        @Override
        public List<Contador> listar() {
            List<Contador> lista = new ArrayList<>();
            String sql = "SELECT id, nombre, tarjeta_profesional, telefono FROM contador";
            try (Connection con = obtenerConexion();
                 Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    lista.add(new Contador(rs.getLong("id"), rs.getString("nombre"), rs.getString("tarjeta_profesional"), rs.getString("telefono")));
                }
            } catch (SQLException e) {
                System.out.println("Error al listar contadores: " + e.getMessage());
            }
            return lista;
        }
    }
