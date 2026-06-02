package co.edu.uptc.gui;

import co.edu.uptc.dto.ResultadoOperacion;
import co.edu.uptc.enums.CategoriaProducto;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.negocio.GestionInventario;
import co.edu.uptc.utilidades.ValidadorEntradas;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EventoProducto implements ActionListener, ListSelectionListener {

    public static final String CMD_GUARDAR = "CMD_GUARDAR_PRODUCTO";
    public static final String CMD_EDITAR = "CMD_EDITAR_PRODUCTO";
    public static final String CMD_BUSCAR = "CMD_BUSCAR_PRODUCTO";
    public static final String CMD_INACTIVAR = "CMD_INACTIVAR_PRODUCTO";
    public static final String CMD_REGISTRAR_MOVIMIENTO = "CMD_REGISTRAR_MOVIMIENTO";

    private final VentanaPrincipal ventanaPrincipal;
    private final PanelProducto panel;
    private final GestionInventario gestion;

    public EventoProducto(VentanaPrincipal ventanaPrincipal,
                          PanelProducto panel,
                          GestionInventario gestion) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.panel = panel;
        this.gestion = gestion;

        suscribir(panel.getBtnGuardar(), CMD_GUARDAR);
        suscribir(panel.getBtnEditar(), CMD_EDITAR);
        suscribir(panel.getBtnBuscar(), CMD_BUSCAR);
        suscribir(panel.getBtnInactivar(), CMD_INACTIVAR);
        suscribir(panel.getBtnRegistrarMovimiento(), CMD_REGISTRAR_MOVIMIENTO);

        panel.getTablaProductos().getSelectionModel().addListSelectionListener(this);
    }

    public void refrescarVista() {
        actualizarTabla();
    }

    private void suscribir(javax.swing.JButton boton, String comando) {
        boton.setActionCommand(comando);
        boton.addActionListener(this);
    }

    private void guardar() {
        ResultadoOperacion resultado = gestion.registrarProducto(crearDesdeFormulario());
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void editar() {
        ResultadoOperacion resultado = gestion.actualizarProducto(crearDesdeFormulario());
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void buscar() {
        ResultadoOperacion resultado = gestion.buscarProductoValidado(panel.getTxtCodigo().getText());
        if (resultado.isExito()) {
            cargarEnFormulario(resultado.getDato());
        } else {
            JOptionPane.showMessageDialog(panel, resultado.getMensaje());
        }
    }

    private void inactivar() {
        ResultadoOperacion resultado = gestion.inactivarProducto(panel.getTxtCodigo().getText());
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            limpiarFormulario();
        }
    }

    private void registrarMovimiento() {
        String tipo = (String) panel.getCbTipoMovimiento().getSelectedItem();
        ResultadoOperacion resultado = gestion.registrarMovimientoInventario(
                panel.getTxtCodMovimiento().getText(),
                panel.getTxtCantMovimiento().getText(),
                tipo);
        mostrarResultado(resultado);
        if (resultado.isExito()) {
            actualizarTabla();
            panel.getTxtCodMovimiento().setText("");
            panel.getTxtCantMovimiento().setText("");
        }
    }

    private Producto crearDesdeFormulario() {
        return new Producto(
                panel.getTxtCodigo().getText().trim(),
                panel.getTxtNombre().getText().trim(),
                (CategoriaProducto) panel.getCbCategoria().getSelectedItem(),
                parsearDouble(panel.getTxtPrecioCompra().getText()),
                parsearDouble(panel.getTxtPrecioVenta().getText()),
                parsearEntero(panel.getTxtStockActual().getText()),
                parsearEntero(panel.getTxtStockMinimo().getText()),
                parsearEntero(panel.getTxtStockMaximo().getText()),
                true
        );
    }

    private double parsearDouble(String texto) {
        if (ValidadorEntradas.esVacio(texto)) {
            return 0.0;
        }
        return Double.parseDouble(texto.trim());
    }

    private int parsearEntero(String texto) {
        if (ValidadorEntradas.esVacio(texto)) {
            return 0;
        }
        return (int) Double.parseDouble(texto.trim());
    }

    private void actualizarTabla() {
        DefaultTableModel modelo = panel.getModeloTabla();
        modelo.setRowCount(0);
        List<Producto> lista = gestion.obtenerTodosLosProductos();
        for (Producto p : lista) {
            if (p.isActivo()) {
                modelo.addRow(new Object[]{
                        p.getCodigoProducto(),
                        p.getNombreProducto(),
                        p.getCategoria() != null ? p.getCategoria().name() : "",
                        p.getPrecioVenta(),
                        p.getStockActual()
                });
            }
        }
    }

    private void cargarDesdeTabla() {
        int fila = panel.getTablaProductos().getSelectedRow();
        if (fila == -1) {
            return;
        }
        String codigo = panel.getModeloTabla().getValueAt(fila, 0).toString();
        Producto producto = gestion.buscarProducto(codigo);
        if (producto != null) {
            cargarEnFormulario(producto);
        }
    }

    private void cargarEnFormulario(Producto producto) {
        panel.getTxtCodigo().setText(producto.getCodigoProducto());
        panel.getTxtNombre().setText(producto.getNombreProducto());
        panel.getCbCategoria().setSelectedItem(producto.getCategoria());
        panel.getTxtPrecioCompra().setText(String.valueOf(producto.getPrecioCompra()));
        panel.getTxtPrecioVenta().setText(String.valueOf(producto.getPrecioVenta()));
        panel.getTxtStockActual().setText(String.valueOf(producto.getStockActual()));
        panel.getTxtStockMinimo().setText(String.valueOf(producto.getStockMinimo()));
        panel.getTxtStockMaximo().setText(String.valueOf(producto.getStockMaximo()));
    }

    private void limpiarFormulario() {
        panel.getTxtCodigo().setText("");
        panel.getTxtNombre().setText("");
        panel.getTxtPrecioCompra().setText("");
        panel.getTxtPrecioVenta().setText("");
        panel.getTxtStockActual().setText("");
        panel.getTxtStockMinimo().setText("");
        panel.getTxtStockMaximo().setText("");
    }

    private void mostrarResultado(ResultadoOperacion resultado) {
        JOptionPane.showMessageDialog(panel, resultado.getMensaje(),
                resultado.isExito() ? "Información" : "Validación",
                resultado.isExito() ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && panel.getTablaProductos().getSelectedRow() != -1) {
            cargarDesdeTabla();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (CMD_GUARDAR.equals(comando)) {
            guardar();
        } else if (CMD_EDITAR.equals(comando)) {
            editar();
        } else if (CMD_BUSCAR.equals(comando)) {
            buscar();
        } else if (CMD_INACTIVAR.equals(comando)) {
            inactivar();
        } else if (CMD_REGISTRAR_MOVIMIENTO.equals(comando)) {
            registrarMovimiento();
        }
    }
}
