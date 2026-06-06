package co.uptc.edu.co.negocio;

import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.co.interfaces.IGestionProveedor;
import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class GestionProveedor {

	private List<Proveedor> proveedores;

	private final IGestionProveedor gestionProveedor;

	public GestionProveedor(IGestionProveedor gestionProveedor) {

		if (gestionProveedor == null) {
			throw new IllegalArgumentException("La gestionProveedor no puede ser nula.");
		}

		this.gestionProveedor = gestionProveedor;

		try {
			proveedores = gestionProveedor.listar();
		} catch (Exception e) {
			proveedores = new ArrayList<>();
			throw new IllegalStateException("Error al cargar proveedores.", e);
		}
	}

	public Proveedor buscarProveedorPorCodigo(String codigo) {
		try {
			return gestionProveedor.buscar(codigo);
		} catch (Exception e) {
			throw new IllegalStateException("Error al buscar proveedor por codigo: " + codigo, e);
		}
	}

	public List<Proveedor> obtenerProveedores() {
		return new ArrayList<>(proveedores);
	}

	public void registrarProveedor(Proveedor proveedor) throws Exception {
		validarProveedor(proveedor);

		if (buscarProveedorPorCodigo(proveedor.getCodigoProveedor()) != null) {
			throw new Exception("Ya existe un proveedor con ese código.");
		}

		proveedor.setEstado(EstadoEnum.ACTIVO);
		gestionProveedor.guardar(proveedor);
		proveedores.add(proveedor);
	}

	public void actualizarProveedor(Proveedor proveedorActualizado) throws Exception {
		validarProveedor(proveedorActualizado);

		Proveedor proveedorExistente = buscarProveedorPorCodigo(proveedorActualizado.getCodigoProveedor());

		if (proveedorExistente == null) {
			throw new Exception("No se encontró el proveedor a actualizar.");
		}

		gestionProveedor.actualizar(proveedorActualizado);

		proveedorExistente.setRazonSocial(proveedorActualizado.getRazonSocial());
		proveedorExistente.setNit(proveedorActualizado.getNit());
		proveedorExistente.setDireccion(proveedorActualizado.getDireccion());
		proveedorExistente.setTelefono(proveedorActualizado.getTelefono());
		proveedorExistente.setCorreoElectronico(proveedorActualizado.getCorreoElectronico());
	}

	public void cambiarEstadoProveedor(String codigo) throws Exception {
		Proveedor proveedor = buscarProveedorPorCodigo(codigo);

		if (proveedor == null) {
			throw new Exception("No se encontró el proveedor.");
		}

		gestionProveedor.cambiarEstado(codigo);

		if (proveedor.getEstado() == EstadoEnum.ACTIVO) {
			proveedor.setEstado(EstadoEnum.INACTIVO);
		} else {
			proveedor.setEstado(EstadoEnum.ACTIVO);
		}
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

			throw new Exception("El código del proveedor es obligatorio.");
		}

		validarRazonSocial(proveedor.getRazonSocial());
		validarNit(proveedor.getNit());
		validarDireccion(proveedor.getDireccion());
		validarTelefono(proveedor.getTelefono());
		validarCorreoElectronico(proveedor.getCorreoElectronico());
	}

	private void validarRazonSocial(String razonSocial) throws Exception {

		if (razonSocial == null || razonSocial.trim().isEmpty()) {
			throw new Exception("La razón social es obligatoria.");
		}

		if (razonSocial.trim().length() < 3) {
			throw new Exception("La razón social debe tener al menos 3 caracteres.");
		}

		if (!razonSocial.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ0-9 .,&-]+")) {
			throw new Exception("La razón social contiene caracteres no válidos.");
		}
	}

	private void validarNit(String nit) throws Exception {

		if (nit == null || nit.trim().isEmpty()) {
			throw new Exception("El NIT es obligatorio.");
		}

		if (!nit.matches("\\d{8,15}(-\\d)?")) {
			throw new Exception("El NIT debe contener entre 8 y 15 dígitos.");
		}
	}

	private void validarDireccion(String direccion) throws Exception {

		if (direccion == null || direccion.trim().isEmpty()) {
			throw new Exception("La dirección es obligatoria.");
		}

		if (direccion.trim().length() < 5) {
			throw new Exception("La dirección debe tener al menos 5 caracteres.");
		}
	}

	private void validarTelefono(String telefono) throws Exception {

		if (telefono == null || telefono.trim().isEmpty()) {
			throw new Exception("El teléfono es obligatorio.");
		}

		if (!telefono.matches("^(3\\d{9}|6\\d{9})$")) {
			throw new Exception("El teléfono debe iniciar por 3 y tener 10 dígitos.");
		}
	}

	private void validarCorreoElectronico(String correo) throws Exception {

		if (correo == null || correo.trim().isEmpty()) {
			throw new Exception("El correo electrónico es obligatorio.");
		}

		if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			throw new Exception("El correo electrónico no tiene un formato válido.");
		}
	}
}