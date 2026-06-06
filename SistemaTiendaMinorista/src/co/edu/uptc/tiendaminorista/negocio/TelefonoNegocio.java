package co.edu.uptc.tiendaminorista.negocio;

import java.util.List;
import co.edu.uptc.tiendaminorista.modelo.Telefono;
import co.edu.uptc.tiendaminorista.persistencia.TelefonoDAO;

public class TelefonoNegocio {
    
    private TelefonoDAO dao = new TelefonoDAO();
    
    public String registrarTelefono(Telefono t) {

        if (t.getMarca() == null || t.getMarca().trim().isEmpty()) {
            return "Error: La marca no puede estar vacía.";
        }
        if (t.getModelo() == null || t.getModelo().trim().isEmpty()) {
            return "Error: El modelo no puede estar vacío.";
        }
        
        if (t.getPrecio() <= 0) {
            return "Error: El precio debe ser mayor a 0.";
        }
        
        boolean exito = dao.insertar(t);
        
        if (exito) {
            return "¡Teléfono registrado con éxito!";
        } else {
            return "Error crítico: No se pudo guardar en la base de datos.";
        }
    }
    
    public List<Telefono> obtenerTodosLosTelefonos() {
        return dao.listar();
    }
}
