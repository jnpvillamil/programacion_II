package co.uptc.edu.tienda.negocio;

import co.uptc.edu.tienda.negocio.dto.CredencialDto;

public class GestionSeguridad {
	
	public boolean validarLogueo(CredencialDto credencial) throws Exception{
		if(credencial!=null) {
			//TODO realizar lógica de negocio de validación
			System.err.println(credencial);
			return true;
		}else {
			throw new Exception("No se tiene información de las credenciales de usuario"); 
		}
		
	}
}
