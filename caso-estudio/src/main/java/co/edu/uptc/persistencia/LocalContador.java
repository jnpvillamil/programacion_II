package co.edu.uptc.persistencia;

import co.edu.uptc.gui.interfaces.IGestionContador;
import co.edu.uptc.gui.modelo.Contador;
import co.edu.uptc.conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalContador implements IGestionContador {

    @Override
    public void guardar(Contador contador) {
        String sql = "INSERT INTO contador (id, nombre, tarjeta_profesional, telefono) VALUES (?, ?, ?, ?)";

        Conexion conObj = new Conexion();
        Connection con = conObj.getConnection();
        
        if (con != null) {
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                
                pst.setLong(1, contador.getId());
                pst.setString(2, contador.getNombre());
                pst.setString(3, contador.getTarjetaProfesional());
                pst.setString(4, contador.getTelefono());
                
                pst.executeUpdate();
                System.out.println(">>> Contador guardado con éxito en MySQL.");
                
            } catch (SQLException e) {
                System.out.println("ERROR al guardar en BDContador: " + e.getMessage());
            } finally {

                conObj.desconectar();
            }
        }
    }

    @Override
    public List<Contador> listar() {
        List<Contador> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, tarjeta_profesional, telefono FROM contador";
        Conexion conObj = new Conexion();
        Connection con = conObj.getConnection();
        
        if (con != null) {
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                
                while (rs.next()) {

                    long id = rs.getLong("id");
                    String nombre = rs.getString("nombre");
                    String tarjeta = rs.getString("tarjeta_profesional");
                    String telefono = rs.getString("telefono");

                    Contador c = new Contador(id, nombre, tarjeta, telefono);

                    lista.add(c);
                }
                
            } catch (SQLException e) {
                System.out.println("ERROR al listar en LocalContador: " + e.getMessage());
            } finally {
                conObj.desconectar();
            }
        }
        return lista;
    }

    @Override 
    public void actualizar(Contador contador) {
        String sql = "UPDATE contador SET nombre = ?, tarjeta_profesional = ?, telefono = ? WHERE id = ?";
        Conexion conObj = new Conexion();
        Connection con = conObj.getConnection();
        if (con != null) {
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, contador.getNombre());
                pst.setString(2, contador.getTarjetaProfesional());
                pst.setString(3, contador.getTelefono());
                pst.setLong(4, contador.getId());
                pst.executeUpdate();
            } catch (SQLException e) { System.out.println(e.getMessage()); }
            finally { conObj.desconectar(); }
        }
    }

    @Override 
    public void eliminar(Long idContador) {
        String sql = "DELETE FROM contador WHERE id = ?";
        Conexion conObj = new Conexion();
        Connection con = conObj.getConnection();
        if (con != null) {
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setLong(1, idContador);
                pst.executeUpdate();
            } catch (SQLException e) { System.out.println(e.getMessage()); }
            finally { conObj.desconectar(); }
        }
    }

    @Override 
    public Contador buscar(Long idContador) { return null; }
}
