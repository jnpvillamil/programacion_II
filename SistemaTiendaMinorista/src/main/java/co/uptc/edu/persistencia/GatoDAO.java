package co.uptc.edu.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.interfaces.IGatoDAO;
import co.uptc.edu.modelo.Gato;

public class GatoDAO implements IGatoDAO {

    @Override
    public boolean guardarGato(Gato gato) {

        String sql =
                "INSERT INTO gatos(nombre, edad) VALUES (?, ?)";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, gato.getNombre());
            ps.setInt(2, gato.getEdad());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Gato> obtenerGatos() {

        List<Gato> gatos = new ArrayList<>();

        String sql = "SELECT * FROM gatos";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                gatos.add(
                        new Gato(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getInt("edad")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return gatos;
    }
}