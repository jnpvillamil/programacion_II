package co.edu.uptc.gui;

import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.enums.TipoCliente;
import co.edu.uptc.enums.TipoIdentificacion;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.negocio.GestionClientes;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EventoClientes implements ActionListener, ListSelectionListener {

    public static final String CMD_REGISTRAR = "CMD_REGISTRAR_CLIENTE";
    public static final String CMD_EDITAR = "CMD_EDITAR_CLIENTE";
    public static final String CMD_BUSCAR = "CMD_BUSCAR_CLIENTE";
    public static final String CMD_INACTIVAR = "CMD_INACTIVAR_CLIENTE";

    private final VentanaPrincipal ventanaPrincipal;
    private final PanelClientes panel;
    private final GestionClientes gestion;

    public EventoClientes(VentanaPrincipal ventanaPrincipal,
                          PanelClientes panel,
                          GestionClientes gestion) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panel = panel;
        this.gestion = gestion;

        suscribir(panel.getBtnRegistrar(), CMD_REGISTRAR);
        suscribir(panel.getBtnEditar(), CMD_EDITAR);
        suscribir(panel.getBtnBuscar(), CMD_BUSCAR);
        suscribir(panel.getBtnInactivar(), CMD_INACTIVAR);

        panel.getTablaClientes().getSelectionModel().addListSelectionListener(this);
    }

    public void refrescarVista() {
        actualizarTabla();
    }

    private void suscribir(javax.swing.JButton boton, String comando) {
        boton.setActionCommand(comando);
        boton.addActionListener(this);
    }

    private void registrar() {
        ResultadoOperacion resultado = gestion.registrarCliente(crearDesdeFormulario());
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void editar() {
        ResultadoOperacion resultado = gestion.actualizarCliente(crearDesdeFormulario());
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void buscar() {
        ResultadoOperacion resultado = gestion.buscarClienteValidado(panel.getTxtCodigo().getText());
        if (resultado.isExito()) {
            cargarEnFormulario(resultado.getDato());
        } else {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje());
        }
    }

    private void inactivar() {
        ResultadoOperacion resultado = gestion.inactivarCliente(panel.getTxtCodigo().getText());
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private Cliente crearDesdeFormulario() {
        return new Cliente(
                panel.getTxtNombre().getText().trim(),
                panel.getTxtIdentificacion().getText().trim(),
                panel.getTxtDireccion().getText().trim(),
                panel.getTxtTelefono().getText().trim(),
                panel.getTxtCodigo().getText().trim(),
                (TipoIdentificacion) panel.getCbTipoIdentificacion().getSelectedItem(),
                (TipoCliente) panel.getCbTipoCliente().getSelectedItem(),
                true
        );
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = panel.getModeloTabla();
        modelo.setRowCount(0);
        List<Cliente> lista = gestion.obtenerTodosLosClientes();
        for (Cliente cliente : lista) {
            if (cliente.isActivo()) {
                modelo.addRow(new Object[]{
                        cliente.getCodigoCliente(),
                        cliente.getNombre(),
                        cliente.getTelefono()
                });
            }
        }
    }

    private void cargarDesdeTabla() {
        int fila = panel.getTablaClientes().getSelectedRow();
        if (fila == -1) {
            return;
        }
        String codigo = panel.getModeloTabla().getValueAt(fila, 0).toString();
        Cliente cliente = gestion.buscarCliente(codigo);
        if (cliente != null) {
            cargarEnFormulario(cliente);
        }
    }

    private void cargarEnFormulario(Cliente cliente) {
        panel.getTxtCodigo().setText(cliente.getCodigoCliente());
        panel.getTxtNombre().setText(cliente.getNombre());
        panel.getTxtIdentificacion().setText(cliente.getIdentificacion());
        panel.getTxtDireccion().setText(cliente.getDireccion());
        panel.getTxtTelefono().setText(cliente.getTelefono());
        panel.getCbTipoIdentificacion().setSelectedItem(cliente.getTipoIdentificacion());
        panel.getCbTipoCliente().setSelectedItem(cliente.getTipoCliente());
    }

    private void limpiarFormulario() {
        panel.getTxtCodigo().setText("");
        panel.getTxtNombre().setText("");
        panel.getTxtIdentificacion().setText("");
        panel.getTxtDireccion().setText("");
        panel.getTxtTelefono().setText("");
    }

    private void mostrarResultado(ResultadoOperacion resultado) {
        JOptionPane.showMessageDialog(panel, resultado.getMensaje(),
                resultado.isExito() ? "Información" : "Validación",
                resultado.isExito() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && panel.getTablaClientes().getSelectedRow() != -1) {
            cargarDesdeTabla();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (CMD_REGISTRAR.equals(comando)) {
            registrar();
        } else if (CMD_EDITAR.equals(comando)) {
            editar();
        } else if (CMD_BUSCAR.equals(comando)) {
            buscar();
        } else if (CMD_INACTIVAR.equals(comando)) {
            inactivar();
        }
    }
}
