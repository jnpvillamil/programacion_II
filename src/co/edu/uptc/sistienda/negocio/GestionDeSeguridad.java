package co.edu.uptc.sistienda.negocio;

import co.edu.uptc.sistienda.negocio.dto.CredencialDto;

public class GestionDeSeguridad {

	private static final String USUARIO_ADMIN = "admin";
	private static final String PASSWORD_ADMIN = "admin123";
	private static final String ROL_ADMINISTRADOR = "Administrador";

	private static final String USUARIO_CAJERO = "cajero";
	private static final String PASSWORD_CAJERO = "cajero123";
	private static final String ROL_CAJERO = "Cajero";
	
	private static final String USUARIO_ENCARGADO_COMPRAS = "encargado de compras";
	private static final String PASSWORD_ENCARGADO_COMPRAS = "compras123";
	private static final String ROL_ENCARGADO_COMPRAS = "Encargado de compras";
	
	private static final String USUARIO_CONTADOR = "contador";
	private static final String PASSWORD_CONTADOR = "contador123";
	private static final String ROL_CONTADOR = "Contador";

	public boolean validarLogueo(CredencialDto credencial) throws Exception {
		if (credencial == null) {
			throw new Exception("No se tiene información de las credenciales del usuario");
		}
		String usuario = credencial.getUsuario();
		String password = new String(credencial.getPassword());
		String rol = credencial.getRol();

		if (usuario == null || usuario.trim().isEmpty()) {
			throw new Exception("El campo usuario no puede estar vacío");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new Exception("El campo contraseña no puede estar vacío");
		}
		if (rol == null || rol.trim().isEmpty()) {
			throw new Exception("Debe seleccionar un rol o perfil");
		}

		if (usuario.equals(USUARIO_ADMIN) && password.equals(PASSWORD_ADMIN)
				&& rol.equals(ROL_ADMINISTRADOR)) {
			return true;
		}
		if (usuario.equals(USUARIO_CAJERO) && password.equals(PASSWORD_CAJERO)
				&& rol.equals(ROL_CAJERO)) {
			return true;
		}
		if (usuario.equals(USUARIO_ENCARGADO_COMPRAS) && password.equals(PASSWORD_ENCARGADO_COMPRAS)
				&& rol.equals(ROL_ENCARGADO_COMPRAS)) {
			return true;
		}
		if (usuario.equals(USUARIO_CONTADOR) && password.equals(PASSWORD_CONTADOR)
				&& rol.equals(ROL_CONTADOR)) {
			return true;
		}
		return false;
	}

}
