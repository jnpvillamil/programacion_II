package co.edu.uptc.sistienda.negocio;


import java.util.List;

import co.edu.uptc.sistienda.interfaces.IGestionEstudiante;
import co.edu.uptc.sistienda.modelo.Estudiante;
 

public class GestionEstudiante {
 
    private IGestionEstudiante repositorioEstudiante;
 
  
    public GestionEstudiante(IGestionEstudiante repositorioEstudiante) {
        this.repositorioEstudiante = repositorioEstudiante;
    }
 
    public void registrarNuevoEstudiante(Estudiante estudiante) throws Exception {
        if (estudiante.getCedula() == null || estudiante.getCedula().trim().isEmpty()) {
            throw new Exception("La cédula del estudiante no puede estar vacía.");
        }
        if (estudiante.getNombre() == null || estudiante.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del estudiante no puede estar vacío.");
        }
        if (estudiante.getTelefono() == null || estudiante.getTelefono().trim().isEmpty()) {
            throw new Exception("El teléfono del estudiante no puede estar vacío.");
        }
        if (repositorioEstudiante.buscarEstudiantePorCedula(estudiante.getCedula()) != null) {
            throw new Exception("Ya existe un estudiante con la cédula: " + estudiante.getCedula());
        }
        repositorioEstudiante.guardarEstudiante(estudiante);
    }
 
    public void modificarEstudiante(Estudiante estudiante) throws Exception {
        if (repositorioEstudiante.buscarEstudiantePorCedula(estudiante.getCedula()) == null) {
            throw new Exception("No se encontró el estudiante con cédula: " + estudiante.getCedula());
        }
        repositorioEstudiante.actualizarEstudiante(estudiante);
    }
 
    public void eliminarEstudiante(String cedula) throws Exception {
        if (repositorioEstudiante.buscarEstudiantePorCedula(cedula) == null) {
            throw new Exception("No se encontró el estudiante con cédula: " + cedula);
        }
        repositorioEstudiante.eliminarEstudiante(cedula);
    }
 
    public Estudiante consultarEstudiantePorCedula(String cedula) {
        return repositorioEstudiante.buscarEstudiantePorCedula(cedula);
    }
 
    public List<Estudiante> obtenerListaEstudiantes() {
        return repositorioEstudiante.obtenerListaEstudiantes();
    }
}
