package co.edu.uptc.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import co.edu.uptc.gui.panelEmpleados;
import co.edu.uptc.negocio.gestionEmpleados;
import co.edu.uptc.negocio.dto.empleadoDto;

public class EmpleadoControlador {

	private panelEmpleados vista;
	private gestionEmpleados negocio;

	public EmpleadoControlador(panelEmpleados vista, gestionEmpleados negocio) {
		this.vista = vista;
		this.negocio = negocio;
		iniciarEventos();
		cargarTabla();
	}

	private void iniciarEventos() {
		vista.bRegistrar.addActionListener(e -> registrar());
		vista.bModificar.addActionListener(e -> modificar());
		vista.bInactivar.addActionListener(e -> inactivar());
		vista.bBuscar.addActionListener(e -> buscar());
		vista.bVolver.addActionListener(e -> volver());
	}

	private void registrar() {
		try {
			empleadoDto emp = vista.getDatosEmpleado();
			if (emp == null)
				return;
			negocio.registrar(emp);
			cargarTabla();
			JOptionPane.showMessageDialog(vista, "Empleado registrado: " + emp.getCodigoEmpleado());
			vista.limpiarCampos();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage());
		}
	}

	private void modificar() {
		try {
			empleadoDto emp = vista.getDatosEmpleadoModificar();
			if (emp == null)
				return;
			negocio.modificar(emp);
			cargarTabla();
			JOptionPane.showMessageDialog(vista, "Empleado modificado correctamente");
			vista.limpiarCampos();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage());
		}
	}

	private void inactivar() {
		try {
			int cod = vista.getCodigoEmpleado();
			if (cod == -1)
				return;
			if (JOptionPane.showConfirmDialog(vista, "¿Eliminar empleado " + cod + "?", "Confirmar",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				negocio.inactivar(cod);
				cargarTabla();
				JOptionPane.showMessageDialog(vista, "Empleado eliminado");
				vista.limpiarCampos();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage());
		}
	}

	private void buscar() {
		try {
			int cod = vista.getCodigoEmpleado();
			if (cod == -1)
				return;
			empleadoDto emp = negocio.buscar(cod);
			if (emp != null) {
				List<empleadoDto> resultado = new ArrayList<>();
				resultado.add(emp);
				vista.poblarTabla(resultado);
			} else {
				JOptionPane.showMessageDialog(vista, "Empleado no encontrado");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vista, ex.getMessage());
		}
	}

	private void cargarTabla() {
		vista.poblarTabla(negocio.listar());
	}

	private void volver() {
		JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(vista);
		if (ventana != null)
			ventana.dispose();
	}
}