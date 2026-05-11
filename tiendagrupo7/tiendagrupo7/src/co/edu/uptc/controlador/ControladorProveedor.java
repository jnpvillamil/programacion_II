package co.edu.uptc.controlador;

import co.edu.uptc.gui.PanelProveedor;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.negocio.GestionProveedor;
import co.edu.uptc.utilidades.ValidadorEntradas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorProveedor {

    private PanelProveedor vista;
    private GestionProveedor negocio;

    public ControladorProveedor(PanelProveedor vista, GestionProveedor negocio) {
        this.vista = vista;
        this.negocio = negocio;
        this.inicializarEventos();
        this.actualizarTabla();
    }

    private void inicializarEventos() {
        vista.getBtnRegistrar().addActionListener(e -> registrar());
        vista.getBtnBuscar().addActionListener(e -> buscar());
        vista.getBtnEditar().addActionListener(e -> editar());
        vista.getBtnInactivar().addActionListener(e -> inactivar());

        vista.getTablaProveedores().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && vista.getTablaProveedores().getSelectedRow() != -1) {
                cargarDesdeTabla();
            }
        });
    }

    private void registrar() {
        if (!validar()) return;
        Proveedor p = crearDesdeForm();
        if (negocio.registrar(p)) {
            actualizarTabla();
            limpiar();
            JOptionPane.showMessageDialog(vista, "Proveedor registrado con éxito.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error: El código de proveedor ya existe.");
        }
    }

    private void buscar() {
        String cod = vista.getTxtCodigo().getText();
        Proveedor p = negocio.buscar(cod);
        if (p != null) {
            cargarEnForm(p);
        } else {
            JOptionPane.showMessageDialog(vista, "Proveedor no encontrado.");
        }
    }

    private void editar() {
        if (!validar()) return;
        Proveedor p = crearDesdeForm();
        // Se usa actualizar para sobreescribir los datos y evitar duplicados
        negocio.actualizar(p); 
        actualizarTabla();
        limpiar();
        JOptionPane.showMessageDialog(vista, "Proveedor actualizado correctamente.");
    }

    private void inactivar() {
        String cod = vista.getTxtCodigo().getText();
        Proveedor p = negocio.buscar(cod);
        if (p != null) {
            p.setActivo(false); // Inactivación lógica
            negocio.actualizar(p);
            actualizarTabla();
            limpiar();
            JOptionPane.showMessageDialog(vista, "Proveedor inactivado.");
        }
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);
        List<Proveedor> lista = negocio.listar();
        for (Proveedor p : lista) {
            // Solo se agregan a la tabla los proveedores que estén activos
            if (p.isActivo()) { 
                modelo.addRow(new Object[]{
                    p.getCodigoProveedor(), p.getNombre(), p.getIdentificacion(),
                    p.getTelefono(), p.getCorreoElectronico(), "Activo"
                });
            }
        }
    }

    private void cargarDesdeTabla() {
        int fila = vista.getTablaProveedores().getSelectedRow();
        if (fila != -1) {
            String cod = vista.getModeloTabla().getValueAt(fila, 0).toString();
            Proveedor p = negocio.buscar(cod);
            if (p != null) cargarEnForm(p);
        }
    }

    private boolean validar() {
        if (ValidadorEntradas.esVacio(vista.getTxtCodigo().getText()) || 
            ValidadorEntradas.esVacio(vista.getTxtRazonSocial().getText())) {
            JOptionPane.showMessageDialog(vista, "Código y Razón Social son obligatorios.");
            return false;
        }
        return true;
    }

    private void cargarEnForm(Proveedor p) {
        vista.getTxtCodigo().setText(p.getCodigoProveedor());
        vista.getTxtRazonSocial().setText(p.getNombre());
        vista.getTxtNit().setText(p.getIdentificacion());
        vista.getTxtDireccion().setText(p.getDireccion());
        vista.getTxtTelefono().setText(p.getTelefono());
        vista.getTxtCorreo().setText(p.getCorreoElectronico());
    }

    private Proveedor crearDesdeForm() {
        return new Proveedor(
            vista.getTxtRazonSocial().getText(), 
            vista.getTxtNit().getText(),
            vista.getTxtDireccion().getText(), 
            vista.getTxtTelefono().getText(),
            vista.getTxtCodigo().getText(), 
            vista.getTxtCorreo().getText(), 
            true
        );
    }

    private void limpiar() {
        vista.getTxtCodigo().setText(""); 
        vista.getTxtRazonSocial().setText("");
        vista.getTxtNit().setText(""); 
        vista.getTxtDireccion().setText("");
        vista.getTxtTelefono().setText(""); 
        vista.getTxtCorreo().setText("");
    }
}