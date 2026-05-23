package co.edu.uptc.Tiendaminorista.negocio;

import co.edu.uptc.Tiendaminorista.negociodto.CredencialDto;

public class SistemaSeguridad {

	public boolean validadinicio(CredencialDto credencial) throws Exception {
	if (credencial!= null) {
		System.out.println(credencial);
		return true;
	}else {
		throw new Exception("las crdenciales no son validas");}
		
		
	}
	
}
