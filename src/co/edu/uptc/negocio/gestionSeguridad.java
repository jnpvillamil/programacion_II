package co.edu.uptc.negocio;

public class gestionSeguridad {

		public boolean validarLoguear(credencialDto credencial) throws Exception {
			if (credencial != null) {
				//TODO relaizar logica de negocio validacion 
				System.err.println(credencial);
				return true;
			}else {
				throw new Exception("No se tiene informacion de las credenciales del usuario");
			}
		}
}
