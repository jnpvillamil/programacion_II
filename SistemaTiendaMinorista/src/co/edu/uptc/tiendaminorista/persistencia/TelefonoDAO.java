package co.edu.uptc.tiendaminorista.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import co.edu.uptc.tiendaminorista.interfaces.ITelefono;
=======
>>>>>>> 649fcfcd8e48f315c0286147eda4788fec31a280
import co.edu.uptc.tiendaminorista.modelo.Telefono;


public class TelefonoDAO implements ITelefono {
	
<<<<<<< HEAD

	private static final String URL = "jdbc:postgresql://localhost:5432/tienda_minorista";
	
	
	@Override
	public void guardar(Telefono telefono) {
=======
	public boolean insertar(Telefono t) {
	
>>>>>>> 649fcfcd8e48f315c0286147eda4788fec31a280
		String sql = "INSERT INTO telefonos (marca, modelo, precio) VALUES (?,?,?)";
		
	
		try (Connection con = Conexion.getConexion();
				PreparedStatement ps = con.prepareStatement(sql)) {
<<<<<<< HEAD
			
			ps.setString(1, telefono.getMarca());
			ps.setString(2, telefono.getModelo());
			ps.setDouble(3, telefono.getPrecio());
				
			ps.executeUpdate();
			System.out.println("Teléfono guardado exitosamente en PostgreSQL.");
			
=======
			/* AÑADIR LOS DATOS DEL OBJETO (Por ejemplo: Juan Pablo hace la inyección para el precio
			 * como get y set Precio
			*/ 
			    ps.setString(1, t.getMarca()); // Huertas
			    ps.setString(2, t.getModelo()); // Mateo
				ps.setDouble(3, t.getPrecio()); // Juan Pablo C:
				
				return ps.executeUpdate() > 0;
>>>>>>> 649fcfcd8e48f315c0286147eda4788fec31a280
		} catch (SQLException e) {
			System.err.println("Error al insertar en base de datos: " + e.getMessage());
		}
	}
<<<<<<< HEAD
	
	
	@Override
	public List<Telefono> obtenerTelefonos() {
=======
	public List<Telefono> listar() {
>>>>>>> 649fcfcd8e48f315c0286147eda4788fec31a280
		List<Telefono> lista = new ArrayList<>();
		String sql = "SELECT * FROM telefonos";
		

		try (Connection con = Conexion.getConexion();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			    
			    	while (rs.next()) {
			    		Telefono t = new Telefono();
			    		// aqui se añaden los t.setMarca o t.setModelo
			    		
<<<<<<< HEAD
				lista.add(t);
			}
		} catch (SQLException e) { 
			System.err.println("Error al listar desde la base de datos: " + e.getMessage()); 
		}
		return lista;
	}
}
=======
			    		t.setPrecio(rs.getDouble("Precio"));
			    		
			    		lista.add(t);
			    	}
			    } catch (SQLException e) { 
					
			    	System.out.println("Error al listar desde la base de datos: " + e.getMessage()); 
			    }
			    return lista;
		}
}












>>>>>>> 649fcfcd8e48f315c0286147eda4788fec31a280
