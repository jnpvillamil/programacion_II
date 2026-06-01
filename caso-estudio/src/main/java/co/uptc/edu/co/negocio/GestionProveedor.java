package co.uptc.edu.co.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.interfaces.dao.ProveedorDAO;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class GestionProveedor implements IGestionProveedor {

	private List<Proveedor> proveedores;

	private final ProveedorDAO proveedorDAO;

	public GestionProveedor(ProveedorDAO proveedorDAO) {

		if (proveedorDAO == null) {
			throw new IllegalArgumentException("El ProveedorDAO no puede ser nulo.");
		}

		this.proveedorDAO = proveedorDAO;

		try {
			proveedores = proveedorDAO.listarProveedor();
		} catch (Exception e) {
			proveedores = new ArrayList<>();
			System.out.println("Error al cargar proveedor: " + e.getMessage());
		}

	}

	@Override
	public Proveedor buscarProveedorPorCodigo(String codigo) {
		for (Proveedor proveedor : proveedores) {
			if (proveedor.getCodigoProveedor().equalsIgnoreCase(codigo)) {
				return proveedor;
			}
		}
		return null;
	}

	@Override
	public List<Proveedor> obtenerProveedores() {
		return new ArrayList<>(proveedores);
	}

	@Override
	public void registrarProveedor(Proveedor proveedor) throws Exception {
		validarProveedor(proveedor);

		if (buscarProveedorPorCodigo(proveedor.getCodigoProveedor()) != null) {
			throw new Exception("Ya existe un proveedor con ese código.");
		}

		proveedor.setEstado(EstadoEnum.ACTIVO);
		proveedorDAO.guardarProveedor(proveedor);
		proveedores.add(proveedor);
	}

	@Override
	public void actualizarProveedor(Proveedor proveedorActualizado) throws Exception {
		validarProveedor(proveedorActualizado);

		Proveedor proveedorExistente = buscarProveedorPorCodigo(proveedorActualizado.getCodigoProveedor());

		if (proveedorExistente == null) {
			throw new Exception("No se encontró el proveedor a actualizar.");
		}

		proveedorExistente.setRazonSocial(proveedorActualizado.getRazonSocial());
		proveedorExistente.setNit(proveedorActualizado.getNit());
		proveedorExistente.setDireccion(proveedorActualizado.getDireccion());
		proveedorExistente.setTelefono(proveedorActualizado.getTelefono());
		proveedorExistente.setCorreoElectronico(proveedorActualizado.getCorreoElectronico());
		proveedorDAO.actualizarProveedor(proveedorExistente);
	}

	@Override
	public void cambiarEstadoProveedor(String codigo) throws Exception {
		Proveedor proveedor = buscarProveedorPorCodigo(codigo);

		if (proveedor == null) {
			throw new Exception("No se encontró el proveedor.");
		}

		if (proveedor.getEstado() == EstadoEnum.ACTIVO) {
			proveedor.setEstado(EstadoEnum.INACTIVO);
		} else {
			proveedor.setEstado(EstadoEnum.ACTIVO);
		}
		proveedorDAO.actualizarProveedor(proveedor);
	}

	public String generarCodigoProveedor() {

		int mayor = 0;

		for (Proveedor proveedor : proveedores) {
			String codigo = proveedor.getCodigoProveedor();

			if (codigo != null && codigo.matches("PRV\\d{4}")) {
				int numero = Integer.parseInt(codigo.substring(3));

				if (numero > mayor) {
					mayor = numero;
				}
			}
		}

		return String.format("PRV%04d", mayor + 1);

	}

	private void validarProveedor(Proveedor proveedor) throws Exception {

	    if (proveedor == null) {
	        throw new Exception("El proveedor no puede ser nulo.");
	    }

	    if (proveedor.getCodigoProveedor() == null
	            || proveedor.getCodigoProveedor().trim().isEmpty()) {

	        throw new Exception(
	            "El código del proveedor es obligatorio."
	        );
	    }

	    validarRazonSocial(
	        proveedor.getRazonSocial()
	    );

	    validarNit(
	        proveedor.getNit()
	    );

	    validarDireccion(
	        proveedor.getDireccion()
	    );

	    validarTelefono(
	        proveedor.getTelefono()
	    );

	    validarCorreoElectronico(
	        proveedor.getCorreoElectronico()
	    );
	}
	
	

	
	private void validarRazonSocial(String razonSocial)
	        throws Exception {

	    if (razonSocial == null
	            || razonSocial.trim().isEmpty()) {

	        throw new Exception(
	            "La razón social es obligatoria."
	        );
	    }

	    if (razonSocial.trim().length() < 3) {

	        throw new Exception(
	            "La razón social debe tener al menos 3 caracteres."
	        );
	    }

	    if (!razonSocial.matches(
	            "[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 .,&-]+")) {

	        throw new Exception(
	            "La razón social contiene caracteres no válidos."
	        );
	    }
	}
	
	
	private void validarNit(String nit)
	        throws Exception {

	    if (nit == null
	            || nit.trim().isEmpty()) {

	        throw new Exception(
	            "El NIT es obligatorio."
	        );
	    }

	    if (!nit.matches("\\d{8,15}(-\\d)?")) {

	        throw new Exception(
	            "El NIT debe contener entre 8 y 15 dígitos."
	        );
	    }
	}
	
	private void validarDireccion(String direccion)
	        throws Exception {

	    if (direccion == null
	            || direccion.trim().isEmpty()) {

	        throw new Exception(
	            "La dirección es obligatoria."
	        );
	    }

	    if (direccion.trim().length() < 5) {

	        throw new Exception(
	            "La dirección debe tener al menos 5 caracteres."
	        );
	    }
	}
	
	private void validarTelefono(String telefono)
	        throws Exception {

	    if (telefono == null
	            || telefono.trim().isEmpty()) {

	        throw new Exception(
	            "El teléfono es obligatorio."
	        );
	    }

	    if (!telefono.matches("^(3\\d{9}|6\\d{9})$")) {

	        throw new Exception(
	            "El teléfono debe iniciar por 3 y tener 10 dígitos."
	        );
	    }
	}
	
	
	private void validarCorreoElectronico(
	        String correo)
	        throws Exception {

	    if (correo == null
	            || correo.trim().isEmpty()) {

	        throw new Exception(
	            "El correo electrónico es obligatorio."
	        );
	    }

	    if (!correo.matches(
	            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

	        throw new Exception(
	            "El correo electrónico no tiene un formato válido."
	        );
	    }
	}
	
}