package uptc.edu.co.controller;

import uptc.edu.co.dao.UsuarioDAO;
import uptc.edu.co.model.Usuario;
import uptc.edu.co.util.Validador;
import java.io.IOException;
import java.util.List;


public class UsuarioController {

    
    private final UsuarioDAO dao = new UsuarioDAO();

    //Aqui va el login del ususario 
    
    public Resultado login(String nombre, String password) {
        
        if (Validador.esCampoVacio(nombre) || Validador.esCampoVacio(password)) {
            return Resultado.error("Completa todos los campos");
        }

        try {
            
            Usuario u = dao.buscarPorNombre(nombre.trim());

            
            if (u != null && u.getPassword().equals(password)) {
                return Resultado.exito("Bienvenido, " + nombre + "!");
            } else {
                return Resultado.error("Usuario o contraseña incorrectos");
            }
        } catch (IOException e) {
            
            return Resultado.error("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Aqui se registra el ususario  
    
    
    public Resultado registrar(String nombre, String password) {
        
        if (!Validador.esUsuarioValido(nombre)) {
            return Resultado.error(Validador.mensajeErrorUsuario());
        }

        
        if (!Validador.esPasswordValida(password)) {
            return Resultado.error(Validador.mensajeErrorPassword());
        }

        try {
            
            boolean guardado = dao.guardar(new Usuario(nombre.trim(), password));

            if (guardado) {
                return Resultado.exito("Usuario '" + nombre + "' registrado correctamente");
            } else {
                return Resultado.error("El usuario '" + nombre + "' ya existe");
            }
        } catch (IOException e) {
            return Resultado.error("Error al guardar: " + e.getMessage());
        }
    }

    
    
    public List<Usuario> listarUsuarios() throws IOException {
        return dao.listarTodos();
    }

    
    public Resultado eliminar(String nombre) {
        if (Validador.esCampoVacio(nombre)) {
            return Resultado.error("Selecciona un usuario para eliminar");
        }
        try {
            boolean eliminado = dao.eliminar(nombre);
            if (eliminado) {
                return Resultado.exito("Usuario eliminado correctamente");
            } else {
                return Resultado.error("No se puede eliminar: usuario no encontrado o es administrador");
            }
        } catch (IOException e) {
            return Resultado.error("Error al eliminar: " + e.getMessage());
        }
    }

    
    public Resultado actualizarPassword(String nombre, String nuevaPass) {
        if (!Validador.esPasswordValida(nuevaPass)) {
            return Resultado.error(Validador.mensajeErrorPassword());
        }
        try {
            boolean actualizado = dao.actualizar(nombre, nuevaPass);
            if (actualizado) {
                return Resultado.exito("Contraseña actualizada correctamente");
            } else {
                return Resultado.error("Usuario no encontrado");
            }
        } catch (IOException e) {
            return Resultado.error("Error al actualizar: " + e.getMessage());
        }
    }

    
    public static class Resultado {
        public final boolean exito;   
        public final String  mensaje; 

        private Resultado(boolean exito, String mensaje) {
            this.exito   = exito;
            this.mensaje = mensaje;
        }

        
        public static Resultado exito(String mensaje) {
            return new Resultado(true, mensaje);
        }

        
        public static Resultado error(String mensaje) {
            return new Resultado(false, mensaje);
        }
    }
}
