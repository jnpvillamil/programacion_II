package co.uptc.edu.tienda.persistencia;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import co.uptc.edu.tienda.conexion.Conexion;
import co.uptc.edu.tienda.interfaces.IGestionMascota;
import co.uptc.edu.tienda.modelo.Mascota;

public class SqlMascota implements IGestionMascota {

    @Override
    public void guardar(Mascota m) {
        Conexion conex = new Conexion();
        try {
            String sql = "INSERT INTO mascotas (nombre, raza, edad) "
                       + "VALUES (?, ?, ?)";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getRaza());
            ps.setInt(3, m.getEdad());
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al insertar mascota: " + e.getMessage());
        }
    }
}