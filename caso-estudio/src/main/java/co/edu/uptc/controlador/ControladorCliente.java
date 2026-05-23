
package co.edu.uptc.controlador;

import co.edu.uptc.gui.PanelClientes;
import co.edu.uptc.interfaces.Repositorio;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.negocio.GestionClientes;
import co.edu.uptc.persistencia.PersistenciaCliente;
import co.edu.uptc.utilidades.ValidadorEntradas;
import javax.swing.*;
import java.util.List;

/**
 * Controlador para la gestión de clientes.
 * 
 * APLICACIÓN DE PRINCIPIOS SOLID:
 * - S (Single Responsibility): Solo coordina interacción entre GUI y negocio
 * - D (Dependency Inversion): Depende de Repositorio<Cliente> (interfaz), no de implementación
 * - O (Open/Closed): Abierto a nuevas implementaciones de persistencia sin modificar este código
 */
public class ControladorCliente {

    private PanelClientes vista;
    private GestionClientes negocio;

    /**
     * Constructor con inyección de dependencias (DIP).
     * 
     * @param vista El panel GUI de clientes
     * @param repositorioCliente Implementación de Repositorio<Cliente>
     */
    public ControladorCliente(PanelClientes vista, Repositorio<Cliente> repositorioCliente) {
        this.vista = vista;
        this.negocio = new GestionClientes(repositorioCliente);
        this.inicializarEventos();
        this.actualizarTabla();
    }

    /**
     * Constructor convenencia: usa PersistenciaCliente por defecto.
     * 
     * @param vista El panel GUI de clientes
     */
    public ControladorCliente(PanelClientes vista) {
        this(vista, new PersistenciaCliente());
    }

    /**
     * Constructor alternativo si aún se recibe GestionClientes instanciada.
     * Se mantiene para máxima compatibilidad.
     */
    public ControladorCliente(PanelClientes vista, GestionClientes negocio) {
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

      
        vista.getTablaClientes().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && vista.getTablaClientes().getSelectedRow() != -1) {
                cargarDesdeTabla();
            }
        });
    }

    private void registrar() {
        if (!validar()) return;
        Cliente c = crearDesdeForm();
        if (negocio.registrarCliente(c)) {
            actualizarTabla();
            limpiar();
            JOptionPane.showMessageDialog(vista, "Cliente registrado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error: La identificación ya existe.");
        }
    }//

    private void buscar() {
        String criterio = vista.getTxtIdentificacion().getText();
        Cliente c = negocio.buscarCliente(criterio);
        if (c != null) {
            cargarEnForm(c);
        } else {
            JOptionPane.showMessageDialog(vista, "Cliente no encontrado.");
        }
    }

    private void editar() {
        if (!validar()) return;
        Cliente c = crearDesdeForm();
        if (negocio.actualizarCliente(c)) {
            actualizarTabla();
            JOptionPane.showMessageDialog(vista, "Datos del cliente actualizados.");
        }
    }

    private void inactivar() {
        String cod = vista.getTxtCodigo().getText();
        if (negocio.inactivarCliente(cod)) {
            actualizarTabla();
            limpiar();
            JOptionPane.showMessageDialog(vista, "Cliente inactivado correctamente.");
        }
    }

    private void actualizarTabla() {
        vista.getModeloTabla().setRowCount(0);
        List<Cliente> lista = negocio.obtenerTodosLosClientes();
        for (Cliente c : lista) {
            vista.getModeloTabla().addRow(new Object[]{
                c.getCodigoCliente(), c.getNombre(), c.getIdentificacion(),
                c.getTelefono(), c.getTipoCliente(), c.isActivo() ? "Activo" : "Inactivo"
            });
        }
    }

    private void cargarDesdeTabla() {
        int fila = vista.getTablaClientes().getSelectedRow();
        String cod = vista.getModeloTabla().getValueAt(fila, 0).toString();
        cargarEnForm(negocio.buscarCliente(cod));
    }

    private boolean validar() {
        return !ValidadorEntradas.esVacio(vista.getTxtNombre().getText()) && 
               !ValidadorEntradas.esVacio(vista.getTxtIdentificacion().getText());
    }

    private void cargarEnForm(Cliente c) {
        vista.getTxtCodigo().setText(c.getCodigoCliente());
        vista.getTxtNombre().setText(c.getNombre());
        vista.getCbTipoIdentificacion().setSelectedItem(c.getTipoIdentificacion());
        vista.getTxtIdentificacion().setText(c.getIdentificacion());
        vista.getTxtDireccion().setText(c.getDireccion());
        vista.getTxtTelefono().setText(c.getTelefono());
        vista.getCbTipoCliente().setSelectedItem(c.getTipoCliente());
    }

    private Cliente crearDesdeForm() {
        return new Cliente(
            vista.getTxtNombre().getText(), vista.getTxtIdentificacion().getText(),
            vista.getTxtDireccion().getText(), vista.getTxtTelefono().getText(),
            vista.getTxtCodigo().getText(), vista.getCbTipoIdentificacion().getSelectedItem().toString(),
            vista.getCbTipoCliente().getSelectedItem().toString(), true
        );
    }

    private void limpiar() {
        vista.getTxtCodigo().setText(""); vista.getTxtNombre().setText("");
        vista.getTxtIdentificacion().setText(""); vista.getTxtDireccion().setText("");
        vista.getTxtTelefono().setText("");
    }
}