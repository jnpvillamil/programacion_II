package co.edu.uptc.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.gui.PanelProveedor;
import co.edu.uptc.negocio.GestionProveedor;
import co.edu.uptc.negocio.dto.proveedorDto;

public class ProveedorControlador implements ActionListener {

	private PanelProveedor vista;
	private GestionProveedor negocio;

	public ProveedorControlador(PanelProveedor vista, GestionProveedor negocio) {
		this.vista = vista;
		this.negocio = negocio;

		// Conectamos la vista con este controlador
		this.vista.setControlador(this);

		// Carga inicial de los datos en la tabla
		actualizarTabla();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		switch (comando) {
		case "REGISTRAR_PROVEEDOR":
			registrar();
			break;
		case "MODIFICAR_PROVEEDOR":
			modificar();
			break;
		case "INACTIVAR_PROVEEDOR":
			inactivar();
			break;
		case "BUSCAR_PROVEEDOR":
			buscar();
			break;
		case "LIMPIAR_PROVEEDOR":
			vista.limpiarCampos();
			actualizarTabla();
			break;
		case "VOLVER_PROVEEDORES":
			javax.swing.SwingUtilities.getWindowAncestor(vista).dispose();
			break;
		}
	}

	private void registrar() {
		try {
			proveedorDto p = vista.getDatosProveedor();
			if (p == null)
				return;
			negocio.registrar(p);
			actualizarTabla();
			vista.limpiarCampos();
			JOptionPane.showMessageDialog(vista, "Proveedor registrado correctamente.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void modificar() {
		try {
			proveedorDto p = vista.getDatosProveedorModificar();
			if (p == null)
				return;
			negocio.modificar(p);
			actualizarTabla();
			vista.limpiarCampos();
			JOptionPane.showMessageDialog(vista, "Proveedor modificado correctamente.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al modificar: " + ex.getMessage());
		}
	}

	private void inactivar() {
		try {
			int cod = vista.getCodigoProveedor();
			if (cod == -1)
				return;

			// --- Ventana de confirmación ---
			int confirmacion = JOptionPane.showConfirmDialog(vista,
					"¿Está seguro que desea eliminar/inactivar el proveedor con código " + cod + "?",
					"Confirmar acción", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			// Si el usuario presiona "Sí" (YES_OPTION), procedemos a borrar
			if (confirmacion == JOptionPane.YES_OPTION) {
				negocio.inactivar(cod);
				actualizarTabla();
				vista.limpiarCampos();
				JOptionPane.showMessageDialog(vista, "Proveedor eliminado correctamente.");
			}
			// Si presiona "No" o cierra la ventana, no hace nada y los datos se conservan.

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al inactivar: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// Ahora es idéntico a ClienteControlador (Búsqueda Multicriterio con ventana
	// flotante)
	private void buscar() {
		String input = JOptionPane.showInputDialog(vista, "Ingrese la razón social o NIT del proveedor a buscar:",
				"Búsqueda Multicriterio", JOptionPane.QUESTION_MESSAGE);

		if (input == null || input.trim().isEmpty()) {
			return;
		}

		try {
			String textoABuscar = input.trim();
			List<proveedorDto> encontrados = negocio.buscarMulticriterio(textoABuscar);

			if (encontrados != null && !encontrados.isEmpty()) {
				vista.poblarTabla(encontrados);
				JOptionPane.showMessageDialog(vista, "Se encontraron " + encontrados.size() + " coincidencia(s).");
			} else {
				JOptionPane.showMessageDialog(vista,
						"No se encontró ningún proveedor para la búsqueda: " + textoABuscar, "Sin resultados",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al ejecutar la búsqueda: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTabla() {
		try {
			vista.poblarTabla(negocio.listar());
		} catch (Exception e) {
			System.err.println("Error al listar proveedores: " + e.getMessage());
		}
	}
}