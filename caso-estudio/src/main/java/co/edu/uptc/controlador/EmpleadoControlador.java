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

    private panelEmpleados   vista;
    private gestionEmpleados negocio;

    public EmpleadoControlador(panelEmpleados vista, gestionEmpleados negocio) {
        this.vista   = vista;
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
            if (emp == null) return;
            negocio.registrar(emp);
            cargarTabla();
            JOptionPane.showMessageDialog(vista, "Empleado registrado: " + emp.getNombre());
            vista.limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage());
        }
    }

    private void modificar() {
        try {
            String nombreAntiguo = vista.getNombreSeleccionado();
            if (nombreAntiguo == null) return;
            String nombreNuevo = vista.getNombreNuevo();
            if (nombreNuevo.isBlank()) {
                JOptionPane.showMessageDialog(vista, "Ingrese el nombre nuevo");
                return;
            }
            empleadoDto emp = new empleadoDto();
            emp.setNombre(nombreNuevo);
            negocio.modificar(nombreAntiguo, emp);
            cargarTabla();
            JOptionPane.showMessageDialog(vista, "Empleado modificado correctamente");
            vista.limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, ex.getMessage());
        }
    }

    private void inactivar() {
        try {
            String nombre = vista.getNombreSeleccionado();
            if (nombre == null) return;
            if (JOptionPane.showConfirmDialog(vista,
                    "¿Eliminar empleado '" + nombre + "'?", "Confirmar",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                negocio.inactivar(nombre);
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
            String nombre = vista.getNombreSeleccionado();
            if (nombre == null) return;
            empleadoDto emp = negocio.buscar(nombre);
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
        if (ventana != null) ventana.dispose();
    }
}