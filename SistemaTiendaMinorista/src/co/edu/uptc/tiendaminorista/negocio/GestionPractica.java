package co.edu.uptc.tiendaminorista.negocio;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.edu.uptc.tiendaminorista.modelo.PersonaPractica;
import co.edu.uptc.tiendaminorista.persistencia.LocalPractica;
import co.edu.uptc.tiendaminorista.persistencia.DatabaseConnection;

public class GestionPractica {

    private List<PersonaPractica> listaPersona;
    private LocalPractica localPractica; 
    
    public GestionPractica() {
        listaPersona = new ArrayList<>();
        this.localPractica = new LocalPractica();
    }
    
    public GestionPractica(LocalPractica localPractica) {
        listaPersona = new ArrayList<>();
        this.localPractica = localPractica; 
    }
     
    public void agregarpersona(String texto1, String texto2) {
        PersonaPractica nuevaPersona = new PersonaPractica();
        nuevaPersona.setTexto1(texto1);
        nuevaPersona.setTexto2(texto2);
        listaPersona.add(nuevaPersona); 
    }
    
    public boolean actualizarpersona(String text1Buscado, String nuevoTexto2) {
        for (PersonaPractica persona : listaPersona) {
            if (persona.getTexto1().equals(text1Buscado)) {
                persona.setTexto2(nuevoTexto2);
                return localPractica.actualizar(text1Buscado, nuevoTexto2);
            }
        }
        return false;
    }
    
    public boolean eliminarpersona(String texto1Buscando) {
        for (PersonaPractica persona : listaPersona ) {
            if (persona.getTexto1().equals(texto1Buscando)) {
                listaPersona.remove(persona);    
                return localPractica.eliminar(texto1Buscando);
            }
        }
        return false;
    }

    public void guardar(PersonaPractica persona) {
        listaPersona.add(persona);          
        localPractica.guardar(persona);     
    }

    public List<PersonaPractica> obtenerTodasLasPersonas() {
        List<PersonaPractica> lista = new ArrayList<>();
        String sql = "SELECT texto1, texto2 FROM practica"; 

   
        try (Connection con = DatabaseConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PersonaPractica persona = new PersonaPractica();
                persona.setTexto1(rs.getString("texto1"));
                persona.setTexto2(rs.getString("texto2"));
                
                lista.add(persona);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los datos de práctica: " + e.getMessage());
        }
        
        return lista; 
    }
}