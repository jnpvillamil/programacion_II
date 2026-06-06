package co.edu.uptc.negocio;

import co.edu.uptc.dto.BodegueroDTO;
import co.edu.uptc.enums.RolUsuario;
import co.edu.uptc.utilidades.ValidadorEntradas;
import co.edu.uptc.persistencia.PersistenciaBodeguero;

	 public class GestionBodeguero {

		 private final PersistenciaBodeguero persistenciaBodeguero =
		            new PersistenciaBodeguero();

		 public void registrarBodeguero(BodegueroDTO dto, String clave) {
		        validarDatosRegistro(dto, clave);

		 if (persistenciaBodeguero.existeLogin(dto.login())) {
		      throw new IllegalStateException("Ya existe un usuario con el mismo login.");
		        }

		     persistenciaBodeguero.guardar(dto, clave);
		    }

private void validarDatosRegistro(BodegueroDTO dto, String clave) {
    if (dto == null) {
        throw new IllegalArgumentException("Los datos del bodeguero no pueden ser nulos.");
    }
    if (ValidadorEntradas.esNuloOVacio(dto.login())) {
        throw new IllegalArgumentException("Debe indicar el login del bodeguero.");
    }
    if (ValidadorEntradas.esNuloOVacio(clave)) {
        throw new IllegalArgumentException("Debe indicar la contraseña del bodeguero.");
    }
    if (clave.trim().length() <= 4) {
        throw new IllegalArgumentException("La contraseña debe tener más de 4 caracteres.");
    }
    if (ValidadorEntradas.esNuloOVacio(dto.zonaBodega())) {
        throw new IllegalArgumentException("La zona de bodega no puede estar vacía.");
    }
    if (!RolUsuario.BODEGUERO.name().equals(dto.rol())) {
        throw new IllegalArgumentException("El rol del usuario debe ser BODEGUERO.");
    }
  }
}