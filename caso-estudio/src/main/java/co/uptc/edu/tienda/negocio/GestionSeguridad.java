package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.negocio.dto.CredencialDto;
import co.uptc.edu.tienda.interfaces.IGestionUsuario;
import co.uptc.edu.tienda.modelo.Usuario;

public class GestionSeguridad {
	
	private IGestionUsuario persistenciaUsuario;
	
	// Constructor para la Inyección de Dependencias
	public GestionSeguridad(IGestionUsuario persistenciaUsuario) {
		this.persistenciaUsuario = persistenciaUsuario;
	}
	
	public Usuario validarLogueo(CredencialDto credencial) throws Exception {
		if (credencial == null) {
			throw new Exception("No se tiene información de las credenciales de usuario"); 
		}
		
		// Buscar al usuario usando la interfaz
		Usuario usuarioEncontrado = persistenciaUsuario.buscarPorCorreo(credencial.getUsuario());
		
		if (usuarioEncontrado == null) {
			throw new Exception("El usuario no se encuentra registrado");
		}
		
		// Convertir el byte[] del DTO a String para comparar
		String passwordIngresado = new String(credencial.getPassword());
		
		if (!usuarioEncontrado.getPassword().equals(passwordIngresado)) {
			throw new Exception("Contraseña incorrecta");
		}
		
		// Retorna el usuario con su RolEnum correspondiente hacia la vista
		return usuarioEncontrado;
	}
}