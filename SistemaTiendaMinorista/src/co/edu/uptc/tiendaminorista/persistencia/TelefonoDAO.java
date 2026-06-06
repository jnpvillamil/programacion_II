package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.tiendaminorista.modelo.Telefono;

public class TelefonoDAO {
	
	public boolean insertar(Telefono t) {
	
		String sql = "INSERT INTO telefonos (marca, modelo, precio) VALUES (?,?,?)";
		
	
		try (Connection con = Conexion.getConexion();
				PreparedStatement ps = con.prepareStatement(sql)) {
			/* AÑADIR LOS DATOS DEL OBJETO (Por ejemplo: Juan Pablo hace la inyección para el precio
			 * como get y set Precio
			*/ 
			    ps.setString(1, t.getMarca()); // Huertas
			    ps.setString(2, t.getModelo()); // Mateo
				ps.setDouble(3, t.getPrecio()); // Juan Pablo C:
				
				return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.out.println("Error al insertar en base de datos: " + e.getMessage());
			return false;
		}
	}
	public List<Telefono> listar() {
		List<Telefono> lista = new ArrayList<>();
		String sql = "SELECT * FROM telefonos";
		

		try (Connection con = Conexion.getConexion();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			    
			    	while (rs.next()) {
			    		Telefono t = new Telefono();
			    		// aqui se añaden los t.setMarca o t.setModelo
			    		
			    		t.setPrecio(rs.getDouble("Precio"));
			    		
			    		lista.add(t);
			    	}
			    } catch (SQLException e) { 
					
			    	System.out.println("Error al listar desde la base de datos: " + e.getMessage()); 
			    }
			    return lista;
		}
}












