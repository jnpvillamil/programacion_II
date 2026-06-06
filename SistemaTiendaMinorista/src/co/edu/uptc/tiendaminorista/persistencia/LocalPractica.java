package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.tiendaminorista.interfaces.IGestionPractica;
import co.edu.uptc.tiendaminorista.modelo.PersonaPractica;

public class LocalPractica implements IGestionPractica {

    public LocalPractica() {
    }

    @Override
    public void guardar(PersonaPractica persona) {
        String sql = "INSERT INTO practica (texto1, texto2) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	stmt.setString(1, persona.getTexto1());
            stmt.setString(2, persona.getTexto2());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.err.println("No se pudo insertar el registro: " + persona.getTexto1());
            }

        } catch (SQLException e) {
            System.err.println("Error guardando en BD: " + e.getMessage());
        }
    }

    @Override
    public List<PersonaPractica> obtenerTodasLasPersonas() {
        List<PersonaPractica> lista = new ArrayList<>();
        String sql = "SELECT texto1, texto2 FROM practica";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

         
            while (rs.next()) {
                PersonaPractica p = new PersonaPractica();
                p.setTexto1(rs.getString("texto1"));
                p.setTexto2(rs.getString("texto2"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error listando desde la BD: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public boolean actualizar(String texto1Buscado, String nuevoTexto2) {
        String sql = "UPDATE practica SET texto2 = ? WHERE texto1 = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoTexto2);
            stmt.setString(2, texto1Buscado);

            int filasAfectadas = stmt.executeUpdate();
            
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error actualizando en BD: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean eliminar(String texto1Buscado) {

        String sql = "DELETE FROM practica WHERE texto1 = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, texto1Buscado);

            int filasAfectadas = stmt.executeUpdate();
            
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error eliminando en BD: " + e.getMessage());
            return false;
        }
    }
}