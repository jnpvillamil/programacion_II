package co.edu.uptc.controlador;

import co.edu.uptc.gui.PanelClientes;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.negocio.GestionClientes;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorCliente{

    private PanelClientes vistaClientes;
    private GestionClientes gestionClientes;

    public ControladorCliente(PanelClientes vistaClientes, GestionClientes gestionClientes) {
        this.vistaClientes = vistaClientes;
        this.gestionClientes = gestionClientes;
        inicializarEventos();
        actualizarTabla();
    }

    private void inicializarEventos() {
        vistaClientes.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarCliente();
            }
        });

        vistaClientes.getBtnInactivar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inactivarCliente();
            }
        });
    }

    private void registrarCliente() {
        String codigo = vistaClientes.getTxtCodigo().getText();
        String nombre = vistaClientes.getTxtNombre().getText();
        String identificacion = vistaClientes.getTxtIdentificacion().getText();
        String direccion = vistaClientes.getTxtDireccion().getText();
        String telefono = vistaClientes.getTxtTelefono().getText();
        String tipoId = vistaClientes.getCbTipoIdentificacion().getSelectedItem().toString();
        String tipoCliente = vistaClientes.getCbTipoCliente().getSelectedItem().toString();

        if (ValidadorEntradas.esVacio(codigo) || ValidadorEntradas.esVacio(nombre) || ValidadorEntradas.esVacio(identificacion)) {
            JOptionPane.showMessageDialog(vistaClientes, "El código, nombre e identificación son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Cliente cliente = new Cliente(nombre, identificacion, direccion, telefono, codigo, tipoId, tipoCliente, true);
            
            if (gestionClientes.buscarCliente(codigo) == null) {
                boolean registrado = gestionClientes.registrarCliente(cliente);
                if (registrado) {
                    JOptionPane.showMessageDialog(vistaClientes, "Cliente registrado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(vistaClientes, "La identificación ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                gestionClientes.actualizarCliente(cliente);
                JOptionPane.showMessageDialog(vistaClientes, "Cliente actualizado con éxito.");
            }
            
            limpiarFormulario();
            actualizarTabla();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaClientes, "Ocurrió un error: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inactivarCliente() {
        int filaSeleccionada = vistaClientes.getTablaClientes().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaClientes, "Seleccione un cliente de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = vistaClientes.getModeloTabla().getValueAt(filaSeleccionada, 0).toString();
        int confirmacion = JOptionPane.showConfirmDialog(vistaClientes, "¿Está seguro de inactivar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                boolean inactivado = gestionClientes.inactivarCliente(codigo);
                if (inactivado) {
                    actualizarTabla();
                    JOptionPane.showMessageDialog(vistaClientes, "Cliente inactivado con éxito.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaClientes, "Ocurrió un error: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = vistaClientes.getModeloTabla();
        modelo.setRowCount(0);
        List<Cliente> clientes = gestionClientes.obtenerTodosLosClientes();
        
        for (Cliente c : clientes) {
            Object[] fila = {
                    c.getCodigoCliente(),
                    c.getNombre(),
                    c.getTipoIdentificacion(),
                    c.getIdentificacion(),
                    c.getTelefono(),
                    c.getTipoCliente(),
                    c.isActivo() ? "SI" : "NO"
            };
            modelo.addRow(fila);
        }
    }

    private void limpiarFormulario() {
        vistaClientes.getTxtCodigo().setText("");
        vistaClientes.getTxtNombre().setText("");
        vistaClientes.getTxtIdentificacion().setText("");
        vistaClientes.getTxtDireccion().setText("");
        vistaClientes.getTxtTelefono().setText("");
        vistaClientes.getCbTipoIdentificacion().setSelectedIndex(0);
        vistaClientes.getCbTipoCliente().setSelectedIndex(0);
    }
}