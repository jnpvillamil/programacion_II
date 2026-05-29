package co.edu.uptc.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import co.edu.uptc.gui.PanelProducto;
import co.edu.uptc.negocio.GestionProducto;
import co.edu.uptc.negocio.dto.productoDto;

public class ProductoControlador implements ActionListener {

	private PanelProducto vista;
	private GestionProducto negocio;

	public ProductoControlador(PanelProducto vista, GestionProducto negocio) {
		this.vista = vista;
		this.negocio = negocio;
		this.vista.setControlador(this);
		actualizarTabla();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		switch (comando) {
		case "REGISTRAR_PRODUCTO":
			registrar();
			break;
		case "MODIFICAR_PRODUCTO":
			modificar();
			break;
		case "INACTIVAR_PRODUCTO":
			inactivar();
			break;
		case "BUSCAR_PRODUCTO":
			buscar();
			break;
		case "LIMPIAR_PRODUCTO":
			vista.limpiarCampos();
			actualizarTabla();
			break;
		case "VOLVER_PRODUCTOS":
			javax.swing.SwingUtilities.getWindowAncestor(vista).dispose();
			break;
		}
	}

	// --- MÉTODO BUSCAR (Multicriterio) ---
	private void buscar() {
		String input = JOptionPane.showInputDialog(vista, "Ingrese el nombre o código del producto a buscar:");

		if (input == null || input.trim().isEmpty())
			return;

		try {
			// Nota: Debes asegurarte de tener el método buscarMulticriterio en
			// GestionProducto
			java.util.List<productoDto> resultados = negocio.buscarMulticriterio(input.trim());

			if (resultados != null && !resultados.isEmpty()) {
				vista.poblarTabla(resultados);
			} else {
				JOptionPane.showMessageDialog(vista, "No se encontró ningún producto que coincida.");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al buscar: " + ex.getMessage());
		}
	}

	// --- MÉTODO INACTIVAR (Con confirmación segura) ---
	private void inactivar() {
		// 1. Obtenemos la fila seleccionada en la tabla visual
		int row = vista.getTabla().getSelectedRow();

		// 2. Validamos que el usuario realmente haya hecho clic en un producto
		if (row == -1) {
			JOptionPane.showMessageDialog(vista,
					"Para inactivar un producto, primero búscalo y selecciónalo en la tabla.");
			return;
		}

		try {
			// 3. Obtenemos el Código (ID) y el Nombre directamente de la fila seleccionada
			// OJO: Asumimos que el Código está en la columna 0 y el Nombre en la columna 1.
			int id = Integer.parseInt(vista.getTabla().getValueAt(row, 0).toString());
			String nombre = vista.getTabla().getValueAt(row, 1).toString();

			// 4. Pedimos confirmación mostrando el nombre real del producto
			int confirm = JOptionPane.showConfirmDialog(vista,
					"¿Está seguro de que desea eliminar permanentemente el producto: " + nombre + "?",
					"Confirmar Inactivación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			// 5. Si dice que sí, ejecutamos el borrado
			if (confirm == JOptionPane.YES_OPTION) {
				negocio.inactivar(id); // Va al DAO y hace el DELETE en MySQL
				actualizarTabla(); // Vuelve a consultar MySQL y recarga la tabla limpia
				vista.limpiarCampos();
				JOptionPane.showMessageDialog(vista, "Producto eliminado correctamente.");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al intentar inactivar: " + ex.getMessage());
		}
	}

	// --- RESTO DE MÉTODOS ---
	private void registrar() {
		try {
			productoDto p = vista.getDatosProducto();
			if (p == null)
				return;
			negocio.registrar(p);
			actualizarTabla();
			vista.limpiarCampos();
			JOptionPane.showMessageDialog(vista, "Producto registrado correctamente.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al registrar: " + ex.getMessage());
		}
	}

	private void modificar() {
		try {
			productoDto p = vista.getDatosProductoModificar();
			if (p == null)
				return;
			negocio.modificar(p);
			actualizarTabla();
			vista.limpiarCampos();
			JOptionPane.showMessageDialog(vista, "Producto modificado.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al modificar: " + ex.getMessage());
		}
	}

	private void actualizarTabla() {
		try {
			vista.poblarTabla(negocio.listar());
		} catch (Exception e) {
			System.err.println("Error al listar productos: " + e.getMessage());
		}
	}
}