package co.edu.uptc.gui;

import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.modelo.Proveedor;
import co.edu.uptc.negocio.GestionProveedor;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EventoProveedor implements ActionListener, ListSelectionListener {

    public static final String CMD_REGISTRAR = "CMD_REGISTRAR_PROVEEDOR";
    public static final String CMD_EDITAR = "CMD_EDITAR_PROVEEDOR";
    public static final String CMD_BUSCAR = "CMD_BUSCAR_PROVEEDOR";
    public static final String CMD_INACTIVAR = "CMD_INACTIVAR_PROVEEDOR";

    private final VentanaPrincipal ventanaPrincipal;
    private final PanelProveedor panel;
    private final GestionProveedor gestion;

    public EventoProveedor(VentanaPrincipal ventanaPrincipal,
                           PanelProveedor panel,
                           GestionProveedor gestion) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panel = panel;
        this.gestion = gestion;

        suscribir(panel.getBtnRegistrar(), CMD_REGISTRAR);
        suscribir(panel.getBtnEditar(), CMD_EDITAR);
        suscribir(panel.getBtnBuscar(), CMD_BUSCAR);
        suscribir(panel.getBtnInactivar(), CMD_INACTIVAR);

        panel.getTablaProveedores().getSelectionModel().addListSelectionListener(this);
    }

    public void refrescarVista() {
        actualizarTabla();
    }

    private void suscribir(javax.swing.JButton boton, String comando) {
        boton.setActionCommand(comando);
        boton.addActionListener(this);
    }

    private void registrar() {
        ResultadoOperacion resultado = gestion.registrar(crearDesdeFormulario());
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void editar() {
        ResultadoOperacion resultado = gestion.actualizar(crearDesdeFormulario());
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void buscar() {
        ResultadoOperacion resultado = gestion.buscarValidado(panel.getTxtCodigo().getText());
        if (resultado.isExito()) {
            cargarEnFormulario(resultado.getDato());
        } else {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje());
        }
    }

    private void inactivar() {
        ResultadoOperacion resultado = gestion.inactivar(panel.getTxtCodigo().getText());
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private Proveedor crearDesdeFormulario() {
        return new Proveedor(
                panel.getTxtRazonSocial().getText().trim(),
                panel.getTxtNit().getText().trim(),
                panel.getTxtDireccion().getText().trim(),
                panel.getTxtTelefono().getText().trim(),
                panel.getTxtCodigo().getText().trim(),
                panel.getTxtCorreo().getText().trim(),
                true
        );
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = panel.getModeloTabla();
        modelo.setRowCount(0);
        List<Proveedor> lista = gestion.listar();
        for (Proveedor proveedor : lista) {
            if (proveedor.isActivo()) {
                modelo.addRow(new Object[]{
                        proveedor.getCodigoProveedor(),
                        proveedor.getNombre(),
                        proveedor.getIdentificacion(),
                        proveedor.getTelefono(),
                        proveedor.getCorreoElectronico(),
                        "Activo"
                });
            }
        }
    }

    private void cargarDesdeTabla() {
        int fila = panel.getTablaProveedores().getSelectedRow();
        if (fila == -1) {
            return;
        }
        String codigo = panel.getModeloTabla().getValueAt(fila, 0).toString();
        Proveedor proveedor = gestion.buscar(codigo);
        if (proveedor != null) {
            cargarEnFormulario(proveedor);
        }
    }

    private void cargarEnFormulario(Proveedor proveedor) {
        panel.getTxtCodigo().setText(proveedor.getCodigoProveedor());
        panel.getTxtRazonSocial().setText(proveedor.getNombre());
        panel.getTxtNit().setText(proveedor.getIdentificacion());
        panel.getTxtDireccion().setText(proveedor.getDireccion());
        panel.getTxtTelefono().setText(proveedor.getTelefono());
        panel.getTxtCorreo().setText(proveedor.getCorreoElectronico());
    }

    private void limpiarFormulario() {
        panel.getTxtCodigo().setText("");
        panel.getTxtRazonSocial().setText("");
        panel.getTxtNit().setText("");
        panel.getTxtDireccion().setText("");
        panel.getTxtTelefono().setText("");
        panel.getTxtCorreo().setText("");
    }

    private void mostrarResultado(ResultadoOperacion resultado) {
        JOptionPane.showMessageDialog(panel, resultado.getMensaje(),
                resultado.isExito() ? "Información" : "Validación",
                resultado.isExito() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && panel.getTablaProveedores().getSelectedRow() != -1) {
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
