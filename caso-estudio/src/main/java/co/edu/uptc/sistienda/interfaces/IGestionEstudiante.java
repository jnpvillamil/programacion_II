package co.edu.uptc.sistienda.interfaces;
 
import java.util.List;
import co.edu.uptc.sistienda.modelo.Estudiante;
 
public interface IGestionEstudiante {
 
    void guardarEstudiante(Estudiante estudiante);
 
    void actualizarEstudiante(Estudiante estudiante);
 
    void eliminarEstudiante(String cedula);
 
    Estudiante buscarEstudiantePorCedula(String cedula);
 
    List<Estudiante> obtenerListaEstudiantes();
}
