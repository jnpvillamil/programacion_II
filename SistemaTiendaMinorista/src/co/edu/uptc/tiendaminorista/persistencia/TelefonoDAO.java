package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import co.edu.uptc.tiendaminorista.interfaces.ITelefono;
import co.edu.uptc.tiendaminorista.modelo.Telefono;

public class TelefonoDAO implements ITelefono {

   
    public TelefonoDAO() {
    }

    public void guardar(Telefono telefono) {
        String sql = "INSERT INTO telefonos (marca, modelo, precio) VALUES (?, ?, ?)";

        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, telefono.getMarca());
            stmt.setString(2, telefono.getModelo());
            stmt.setDouble(3, telefono.getPrecio());

            int filas = stmt.executeUpdate();
            if (filas == 0) {
                System.err.println("No se pudo insertar el registro del teléfono: " + telefono.getMarca());
            }

        } catch (SQLException e) {
            System.err.println("Error guardando teléfono en BD: " + e.getMessage());
        }
    }

    public List<Telefono> obtenerTelefonos() {
        List<Telefono> lista = new ArrayList<>();
        String sql = "SELECT marca, modelo, precio FROM telefonos";

       
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Telefono t = new Telefono();
                t.setMarca(rs.getString("marca"));
                t.setModelo(rs.getString("modelo"));
                t.setPrecio(rs.getDouble("precio"));
                lista.add(t);
            }

        } catch (SQLException e) {
            System.err.println("Error listando teléfonos desde la BD: " + e.getMessage());
        }

        return lista;
    }
}
