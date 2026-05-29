package co.edu.uptc.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.gui.PanelCliente;
import co.edu.uptc.negocio.GestionCliente;
import co.edu.uptc.negocio.dto.clienteDto;

public class ClienteControlador implements ActionListener {

	private PanelCliente vista;
	private GestionCliente negocio; // Capa de negocio

	public ClienteControlador(PanelCliente vista, GestionCliente negocio) {
		this.vista = vista;
		this.negocio = negocio;

		// ¡Magia MVC! Le decimos a la vista que nosotros somos su controlador
		this.vista.setControlador(this);

		// Al iniciar, le pedimos al negocio la lista y se la mandamos a la vista
		actualizarTabla();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println("¡Botón presionado! Comando: " + e.getActionCommand());
		String comando = e.getActionCommand();

		switch (comando) {
		case "REGISTRAR":
			registrar();
			break;
		case "MODIFICAR":
			modificar();
			break;
		case "INACTIVAR":
			inactivar();
			break;
		case "BUSCAR":
			buscar();
			break;
		case "LIMPIAR":
			vista.limpiarCampos();
			actualizarTabla();
			break;
		case "VOLVER":
			javax.swing.SwingUtilities.getWindowAncestor(vista).dispose();

			break;
		}
	}

	// --- MÉTODOS TRAÍDOS DESDE EVENTOS.JAVA ---
	private void registrar() {
		try {
			clienteDto c = vista.getDatosCliente();
			if (c == null)
				return;
			negocio.registrar(c); // Llamamos clase negocio (que luego llamará al DAO)
			actualizarTabla();
			vista.limpiarCampos();
			JOptionPane.showMessageDialog(vista, "Cliente registrado. Código: " + c.getCodigoCliente());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage());
		}
	}

	private void modificar() {
		try {
			clienteDto c = vista.getDatosClienteModificar();
			if (c == null)
				return;
			negocio.modificar(c);
			actualizarTabla();
			vista.limpiarCampos();
			JOptionPane.showMessageDialog(vista, "Cliente modificado correctamente");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage());
		}
	}

	private void inactivar() {
		try {
			int cod = vista.getCodigoCliente();
			if (cod == -1)
				return;
			if (JOptionPane.showConfirmDialog(vista, "¿Eliminar cliente " + cod + "?", "Confirmar",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				negocio.inactivar(cod);
				actualizarTabla();
				vista.limpiarCampos();
				JOptionPane.showMessageDialog(vista, "Cliente eliminado");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage());
		}
	}

	private void buscar() {
		// 1. Pedir el dato mediante ventana flotante (ahora es más flexible)
		String input = JOptionPane.showInputDialog(vista, "Ingrese el nombre o código del cliente a buscar:",
				"Búsqueda Multicriterio", JOptionPane.QUESTION_MESSAGE);

		// 2. Si el usuario cancela o cierra la ventana, detenemos la acción
		if (input == null || input.trim().isEmpty()) {
			return;
		}

		try {
			// 3. Limpiamos los espacios en blanco de los extremos
			String textoABuscar = input.trim();

			// 4. El negocio recibe un String y devuelve una lista de posibles coincidencias
			List<clienteDto> encontrados = negocio.buscarMulticriterio(textoABuscar);

			// 5. Evaluamos el resultado
			if (encontrados != null && !encontrados.isEmpty()) {
				// Actualizamos la tabla con todos los resultados (pueden ser varios si buscó
				// por nombre)
				vista.poblarTabla(encontrados);
				JOptionPane.showMessageDialog(vista, "Se encontraron " + encontrados.size() + " coincidencia(s).");
			} else {
				// Si la lista está vacía, avisamos
				JOptionPane.showMessageDialog(vista, "No se encontró ningún cliente para la búsqueda: " + textoABuscar,
						"Sin resultados", JOptionPane.WARNING_MESSAGE);
			}

		} catch (Exception ex) {
			// Capturamos cualquier error técnico
			JOptionPane.showMessageDialog(vista, "Error al ejecutar la búsqueda: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTabla() {
		try {
			vista.poblarTabla(negocio.listar());
		} catch (Exception e) {
			System.out.println("Error al cargar tabla: " + e.getMessage());
		}
	}
}