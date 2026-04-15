package co.edu.uptc.sistienda.persistencia;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.sistienda.interfaces.IGestionProveedor;
import co.edu.uptc.sistienda.modelo.Proveedor;

public class LocalProveedor implements IGestionProveedor {

	private List<Proveedor> listaProveedores;

	public LocalProveedor() {
		listaProveedores = new ArrayList<>();
		cargarDatosIniciales();
	}

	private void cargarDatosIniciales() {
		Proveedor prov1 = new Proveedor();
		prov1.setCodigoProveedor("PR001");
		prov1.setRazonSocial("Alimentos del Valle S.A.S");
		prov1.setNit("800234567-2");
		prov1.setDireccion("Zona Industrial Calle 80 # 30-10");
		prov1.setTelefono("6014567890");
		prov1.setCorreoElectronico("ventas@alimentosdelvalle.com");

		Proveedor prov2 = new Proveedor();
		prov2.setCodigoProveedor("PR002");
		prov2.setRazonSocial("Papelería Central Ltda");
		prov2.setNit("900876543-1");
		prov2.setDireccion("Cra 15 # 22-40, Bogotá");
		prov2.setTelefono("3012345678");
		prov2.setCorreoElectronico("info@papeleriacentral.com");

		listaProveedores.add(prov1);
		listaProveedores.add(prov2);
	}

	@Override
	public void guardarProveedor(Proveedor proveedor) {
		listaProveedores.add(proveedor);
	}

	@Override
	public void actualizarProveedor(Proveedor proveedorActualizado) {
		Proveedor existente = buscarProveedorPorCodigo(proveedorActualizado.getCodigoProveedor());
		if (existente != null) {
			existente.setRazonSocial(proveedorActualizado.getRazonSocial());
			existente.setNit(proveedorActualizado.getNit());
			existente.setDireccion(proveedorActualizado.getDireccion());
			existente.setTelefono(proveedorActualizado.getTelefono());
			existente.setCorreoElectronico(proveedorActualizado.getCorreoElectronico());
		}
	}

	@Override
	public void inactivarProveedor(String codigoProveedor) {
		Proveedor existente = buscarProveedorPorCodigo(codigoProveedor);
		if (existente != null) {
			existente.setActivo(false);
		}
	}
	
	@Override
	public void activarProveedor(String codigoProveedor) {
		Proveedor existente = buscarProveedorPorCodigo(codigoProveedor);
		if (existente != null) {
			existente.setActivo(true);
		}
	}

	@Override
	public Proveedor buscarProveedorPorCodigo(String codigoProveedor) {
		for (Proveedor proveedor : listaProveedores) {
			if (proveedor.getCodigoProveedor().equalsIgnoreCase(codigoProveedor)) {
				return proveedor;
			}
		}
		return null;
	}

	@Override
	public List<Proveedor> obtenerListaProveedores() {
		return listaProveedores;
	}
}
