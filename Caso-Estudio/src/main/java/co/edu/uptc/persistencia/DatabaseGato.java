package co.edu.uptc.persistencia;

import co.edu.uptc.interfaces.IGestionGato;
import co.edu.uptc.modelo.Gato;
import co.edu.uptc.Util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseGato implements IGestionGato {

    private ConexionBD conexionBD = new ConexionBD();
    
    @Override
	public void crear(Gato gato) {
		// TODO Auto-generated method stub
		
	}
    
    

    @Override
    public List<Gato> listarTodos() {
        List<Gato> lista = new ArrayList<>();
        String sql = "SELECT * FROM gatos";
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Gato g = new Gato(
                        rs.getString("color"),
                        rs.getString("raza"),
                        rs.getString("ojos")
                );
                lista.add(g);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar gatos: " + e.getMessage());
        }
        return lista;
    }


	
}