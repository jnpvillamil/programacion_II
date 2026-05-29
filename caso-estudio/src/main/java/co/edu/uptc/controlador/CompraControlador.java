package co.edu.uptc.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.gui.PanelCompra;
import co.edu.uptc.negocio.GestionCompra;
import co.edu.uptc.negocio.dto.compraDto;

public class CompraControlador implements ActionListener {

	private PanelCompra vista;
	private GestionCompra negocio;

	public CompraControlador(PanelCompra vista, GestionCompra negocio) {
		this.vista = vista;
		this.negocio = negocio;

		// Conectamos la vista con este controlador
		this.vista.setControlador(this);

		// Carga inicial
		actualizarTabla();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();

		switch (comando) {
		case "REGISTRAR_COMPRA":
			registrar();
			break;
		case "ANULAR_COMPRA":
			anular();
			break;
		case "BUSCAR_COMPRA":
			buscar();
			break;
		case "LIMPIAR_COMPRA":
			vista.limpiarCampos();
			actualizarTabla();
			break;
		case "VOLVER_COMPRAS":
			javax.swing.SwingUtilities.getWindowAncestor(vista).dispose();
			break;
		}
	}

	private void registrar() {
		try {
			compraDto c = vista.getDatosCompra();
			if (c == null)
				return;

			// Llamamos a la lógica de negocio
			negocio.registrar(c);

			// Actualizamos la interfaz
			actualizarTabla();
			vista.limpiarCampos();
			JOptionPane.showMessageDialog(vista, "Compra registrada con éxito.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al registrar la compra: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void anular() {
		try {
			int numFactura = vista.getNumeroFacturaSeleccionado();
			if (numFactura == -1)
				return;

			int confirmacion = JOptionPane.showConfirmDialog(vista,
					"¿Está seguro de anular la compra N° " + numFactura + "?", "Confirmar Anulación",
					JOptionPane.YES_NO_OPTION);

			if (confirmacion == JOptionPane.YES_OPTION) {
				negocio.anular(numFactura);
				actualizarTabla();
				JOptionPane.showMessageDialog(vista, "Compra anulada correctamente.");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al anular: " + ex.getMessage());
		}
	}

	private void buscar() {
		try {
			String input = JOptionPane.showInputDialog(vista, "Ingrese el número de factura de compra a buscar:");
			if (input == null || input.isBlank())
				return;

			int numFactura = Integer.parseInt(input.trim());
			compraDto c = negocio.buscar(numFactura);

			if (c != null) {
				List<compraDto> lista = new ArrayList<>();
				lista.add(c);
				vista.poblarTabla(lista);
				JOptionPane.showMessageDialog(vista, "Compra encontrada con éxito.");
			} else {
				JOptionPane.showMessageDialog(vista, "Factura de compra no encontrada.");
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(vista, "Por favor ingrese un número válido.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, "Error al buscar: " + ex.getMessage());
		}
	}

	private void actualizarTabla() {
		try {
			// Este método llama a la base de datos y refresca la tabla en PanelCompra
			vista.poblarTabla(negocio.listar());
		} catch (Exception e) {
			System.err.println("Error al listar compras en el controlador: " + e.getMessage());
		}
	}

}