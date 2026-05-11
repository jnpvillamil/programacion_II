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
            JOptionPane.showMessageDialog(vista, "Proveedor registrado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error: El NIT o Código ya existe.");
        }
    }

    private void buscar() {
        String criterio = vista.getTxtNit().getText();
        Proveedor p = negocio.buscar(criterio);
        if (p != null) {
            cargarEnForm(p);
        } else {
            JOptionPane.showMessageDialog(vista, "Proveedor no encontrado.");
        }
    }

    private void editar() {
        if (!validar()) return;
        Proveedor p = crearDesdeForm();
        // ERROR: Usaba registrar en lugar de actualizar, duplicando la línea en el archivo
        negocio.registrar(p); 
        actualizarTabla();
        JOptionPane.showMessageDialog(vista, "Datos del proveedor actualizados.");
    }

    private void inactivar() {
        String cod = vista.getTxtCodigo().getText();
        Proveedor p = negocio.buscar(cod);
        if (p != null) {
            p.setActivo(false);
            negocio.registrar(p); 
            actualizarTabla();
            limpiar();
            JOptionPane.showMessageDialog(vista, "Proveedor inactivado correctamente.");
        }
    }

    private void actualizarTabla() {
        vista.getModeloTabla().setRowCount(0);
        List<Proveedor> lista = negocio.listar();
        for (Proveedor p : lista) {
            // ERROR: No filtraba por estado activo, mostrando todo el historial del archivo
            vista.getModeloTabla().addRow(new Object[]{
                p.getCodigoProveedor(), p.getNombre(), p.getIdentificacion(),
                p.getTelefono(), p.getCorreoElectronico(), p.isActivo() ? "Activo" : "Inactivo"
            });
        }
    }

    private void cargarDesdeTabla() {
        int fila = vista.getTablaProveedores().getSelectedRow();
        String cod = vista.getModeloTabla().getValueAt(fila, 0).toString();
        cargarEnForm(negocio.buscar(cod));
    }

    private boolean validar() {
        if (ValidadorEntradas.esVacio(vista.getTxtRazonSocial().getText()) || 
            ValidadorEntradas.esVacio(vista.getTxtNit().getText())) {
            JOptionPane.showMessageDialog(vista, "Razón Social y NIT son obligatorios.");
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
    }
}