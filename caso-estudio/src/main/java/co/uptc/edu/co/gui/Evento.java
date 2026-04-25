package co.uptc.edu.co.gui;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import co.uptc.edu.co.config.TiendaConfig;
import co.uptc.edu.co.gui.dialog.DialogActualizarPrecio;
import co.uptc.edu.co.gui.dialog.DialogCliente;
import co.uptc.edu.co.gui.dialog.DialogHistorialCliente;
import co.uptc.edu.co.gui.dialog.DialogMovimientoInventario;
import co.uptc.edu.co.gui.dialog.DialogProducto;
import co.uptc.edu.co.gui.dialog.DialogProveedor;
import co.uptc.edu.co.gui.dialog.DialogRegistrarCompra;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.Producto;
import co.uptc.edu.co.negocio.GestionCliente;
import co.uptc.edu.co.negocio.GestionProducto;

public class Evento implements ActionListener {

    public static final String PRODUCTOS = "Productos";
    public static final String CLIENTES = "Clientes";
    public static final String PROVEEDORES = "Proveedores";
    public static final String VENTAS = "Ventas";
    public static final String COMPRAS = "Compras";
    public static final String CONTABILIDAD = "Contabilidad";
    public static final String REPORTES = "Reportes";
    public static final String CONSULTAS = "Consultas";

    public static final String CMD_NUEVO_PRODUCTO = "NuevoProducto";
    public static final String CMD_EDITAR_PRODUCTO = "EditarProducto";
    public static final String CMD_ESTADO_PRODUCTO = "CambiarEstadoProducto";
    public static final String CMD_ACTUALIZAR_PRECIO_PRODUCTO = "ActualizarPrecioProducto";
    public static final String CMD_MOVIMIENTO_INVENTARIO = "MovimientoInventarioProducto";
    public static final String CMD_CONFIRMAR_PRODUCTO = "ConfirmarProducto";
    public static final String CMD_CONFIRMAR_EDICION_PRODUCTO = "ConfirmarEdicionProducto";
    public static final String CMD_CONFIRMAR_ACTUALIZACION_PRECIO_PRODUCTO = "ConfirmarActualizacionPrecioProducto";
    public static final String CMD_CONFIRMAR_MOVIMIENTO_INVENTARIO = "ConfirmarMovimientoInventario";

    public static final String CMD_NUEVO_CLIENTE = "NuevoCliente";
    public static final String CMD_EDITAR_CLIENTE = "EditarCliente";
    public static final String CMD_ESTADO_CLIENTE = "EstadoCliente";
    public static final String CMD_HISTORIAL_CLIENTE = "HistorialCliente";
    public static final String CMD_CONFIRMAR_CLIENTE = "ConfirmarCliente";
    public static final String CMD_CONFIRMAR_EDICION_CLIENTE = "ConfirmarEdicionCliente";

    public static final String CMD_NUEVO_PROVEEDOR = "NuevoProveedor";
    public static final String CMD_EDITAR_PROVEEDOR = "EditarProveedor";
    public static final String CMD_ESTADO_PROVEEDOR = "EstadoProveedor";
    public static final String CMD_REGISTRAR_COMPRA_PROVEEDOR = "RegistrarCompraProveedor";
    public static final String CMD_CONFIRMAR_PROVEEDOR = "ConfirmarProveedor";
    public static final String CMD_CONFIRMAR_EDICION_PROVEEDOR = "ConfirmarEdicionProveedor";
    public static final String CMD_CONFIRMAR_REGISTRO_COMPRA = "ConfirmarRegistroCompra";

    private VentanaPrincipal ventana;
    private TiendaConfig config;
    private GestionProducto gestionProducto;
    private GestionCliente gestionCliente;

    public Evento(VentanaPrincipal ventana, TiendaConfig config) {
        this.ventana = ventana;
        this.config = config;
        this.gestionProducto = config.getGestionProducto();
        this.gestionCliente = config.getGestionCliente();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (manejarNavegacion(comando)) {
            return;
        }

        if (manejarEventosProducto(comando, e)) {
            return;
        }

        if (manejarEventosCliente(comando, e)) {
            return;
        }

        if (manejarEventosProveedor(comando)) {
            return;
        }
    }

    private boolean manejarNavegacion(String comando) {
        switch (comando) {
            case PRODUCTOS:
                ventana.irProductos();
                refrescarTablaProductos();
                return true;

            case CLIENTES:
                ventana.irClientes();
                refrescarTablaClientes();
                return true;

            case PROVEEDORES:
                ventana.irProveedores();
                return true;

            case VENTAS:
                ventana.irVentas();
                return true;

            case COMPRAS:
                ventana.irCompras();
                return true;

            case CONTABILIDAD:
                ventana.irContabilidad();
                return true;

            case REPORTES:
                ventana.irReportes();
                return true;

            case CONSULTAS:
                ventana.irConsultas();
                return true;

            default:
                return false;
        }
    }

    private boolean manejarEventosProducto(String comando, ActionEvent e) {
        switch (comando) {
            case CMD_NUEVO_PRODUCTO:
                abrirDialogoNuevoProducto();
                return true;

            case CMD_CONFIRMAR_PRODUCTO:
                registrarProducto(e);
                return true;

            case CMD_EDITAR_PRODUCTO:
                abrirFormularioEditarProducto();
                return true;

            case CMD_CONFIRMAR_EDICION_PRODUCTO:
                editarProducto(e);
                return true;

            case CMD_ESTADO_PRODUCTO:
                cambiarEstadoProductoSeleccionado();
                return true;

            case CMD_ACTUALIZAR_PRECIO_PRODUCTO:
                abrirDialogoActualizarPrecio();
                return true;

            case CMD_CONFIRMAR_ACTUALIZACION_PRECIO_PRODUCTO:
                actualizarPrecioProducto(e);
                return true;

            case CMD_MOVIMIENTO_INVENTARIO:
                abrirDialogoMovimientoInventario();
                return true;

            case CMD_CONFIRMAR_MOVIMIENTO_INVENTARIO:
                registrarMovimientoInventario(e);
                return true;

            default:
                return false;
        }
    }

    private boolean manejarEventosCliente(String comando, ActionEvent e) {
        switch (comando) {
            case CMD_NUEVO_CLIENTE:
                abrirDialogoNuevoCliente();
                return true;

            case CMD_CONFIRMAR_CLIENTE:
                registrarCliente(e);
                return true;

            case CMD_EDITAR_CLIENTE:
                abrirFormularioEditarCliente();
                return true;

            case CMD_CONFIRMAR_EDICION_CLIENTE:
                editarCliente(e);
                return true;

            case CMD_ESTADO_CLIENTE:
                cambiarEstadoClienteSeleccionado();
                return true;

            case CMD_HISTORIAL_CLIENTE:
                abrirHistorialCliente();
                return true;

            default:
                return false;
        }
    }

    private boolean manejarEventosProveedor(String comando) {
        switch (comando) {
            case CMD_NUEVO_PROVEEDOR:
                abrirDialogoNuevoProveedor();
                return true;

            case CMD_REGISTRAR_COMPRA_PROVEEDOR:
                abrirDialogoRegistrarCompra();
                return true;

            default:
                return false;
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
                ventana,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void mostrarInformacion(String mensaje) {
        JOptionPane.showMessageDialog(
                ventana,
                mensaje,
                "Información",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private Window obtenerVentanaPadre(ActionEvent e) {
        Component componente = (Component) e.getSource();
        return SwingUtilities.getWindowAncestor(componente);
    }

    private Producto obtenerProductoSeleccionado() throws Exception {
        PanelProducto panelProducto = ventana.getPanelProducto();

        if (!panelProducto.haySeleccion()) {
            throw new Exception("Debe seleccionar un producto.");
        }

        String codigo = panelProducto.obtenerCodigoSeleccionado();
        Producto producto = gestionProducto.buscarProductoPorCodigo(codigo);

        if (producto == null) {
            throw new Exception("No se encontró el producto seleccionado.");
        }

        return producto;
    }

    private Cliente obtenerClienteSeleccionado() throws Exception {
        PanelCliente panelCliente = ventana.getPanelCliente();

        if (!panelCliente.haySeleccion()) {
            throw new Exception("Debe seleccionar un cliente.");
        }

        String codigo = panelCliente.obtenerCodigoSeleccionado();
        Cliente cliente = gestionCliente.buscarClientePorCodigo(codigo);

        if (cliente == null) {
            throw new Exception("No se encontró el cliente seleccionado.");
        }

        return cliente;
    }

    private void abrirDialogoNuevoProducto() {
        DialogProducto dialog = new DialogProducto(ventana, this);
        dialog.setVisible(true);
    }

    private void registrarProducto(ActionEvent e) {
        try {
            Window ventanaPadre = obtenerVentanaPadre(e);

            if (!(ventanaPadre instanceof DialogProducto)) {
                throw new Exception("Error interno: no se pudo identificar el formulario de producto.");
            }

            DialogProducto dialog = (DialogProducto) ventanaPadre;
            Producto producto = dialog.obtenerProducto();

            gestionProducto.registrarProducto(producto);
            mostrarInformacion("Producto registrado exitosamente.");
            refrescarTablaProductos();
            dialog.dispose();

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void abrirFormularioEditarProducto() {
        try {
            Producto producto = obtenerProductoSeleccionado();

            DialogProducto dialog = new DialogProducto(ventana, this);
            dialog.configurarModoEdicion();
            dialog.cargarProducto(producto);
            dialog.setVisible(true);

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void editarProducto(ActionEvent e) {
        try {
            Window ventanaPadre = obtenerVentanaPadre(e);

            if (!(ventanaPadre instanceof DialogProducto)) {
                throw new Exception("Error interno: no se pudo identificar el formulario de edición.");
            }

            DialogProducto dialog = (DialogProducto) ventanaPadre;
            Producto productoEditado = dialog.obtenerProducto();

            gestionProducto.actualizarProducto(productoEditado);
            mostrarInformacion("Producto editado exitosamente.");
            refrescarTablaProductos();
            dialog.dispose();

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void cambiarEstadoProductoSeleccionado() {
        try {
            Producto producto = obtenerProductoSeleccionado();
            boolean estabaActivo = producto.estaActivo();

            String mensaje = estabaActivo
                    ? "¿Está seguro de inactivar este producto?"
                    : "¿Está seguro de activar este producto?";

            int confirmacion = JOptionPane.showConfirmDialog(
                    ventana,
                    mensaje,
                    "Confirmar cambio de estado",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            gestionProducto.cambiarEstadoProducto(producto.getCodigoProducto());

            mostrarInformacion(
                    estabaActivo
                            ? "Producto inactivado exitosamente."
                            : "Producto activado exitosamente."
            );

            refrescarTablaProductos();

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void abrirDialogoActualizarPrecio() {
        try {
            Producto producto = obtenerProductoSeleccionado();

            DialogActualizarPrecio dialog = new DialogActualizarPrecio(ventana, this);
            dialog.cargarProducto(producto);
            dialog.setVisible(true);

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void actualizarPrecioProducto(ActionEvent e) {
        try {
            Window ventanaPadre = obtenerVentanaPadre(e);

            if (!(ventanaPadre instanceof DialogActualizarPrecio)) {
                throw new Exception("Error interno: no se pudo identificar el formulario de actualización de precio.");
            }

            DialogActualizarPrecio dialog = (DialogActualizarPrecio) ventanaPadre;

            String codigo = dialog.obtenerCodigoProducto();
            double precioCompra = dialog.obtenerNuevoPrecioCompra();
            double precioVenta = dialog.obtenerNuevoPrecioVenta();

            gestionProducto.actualizarPrecioProducto(codigo, precioCompra, precioVenta);

            mostrarInformacion("Precio actualizado exitosamente.");
            refrescarTablaProductos();
            dialog.dispose();

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void abrirDialogoMovimientoInventario() {
        try {
            Producto producto = obtenerProductoSeleccionado();

            DialogMovimientoInventario dialog = new DialogMovimientoInventario(ventana, this);
            dialog.cargarProducto(producto.getCodigoProducto());
            dialog.setVisible(true);

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void registrarMovimientoInventario(ActionEvent e) {
        try {
            Window ventanaPadre = obtenerVentanaPadre(e);

            if (!(ventanaPadre instanceof DialogMovimientoInventario)) {
                throw new Exception("Error interno: no se pudo identificar el formulario de movimiento.");
            }

            DialogMovimientoInventario dialog = (DialogMovimientoInventario) ventanaPadre;

            String codigo = dialog.obtenerCodigoProducto();
            String tipoMovimiento = dialog.obtenerTipoMovimiento();
            int cantidad = dialog.obtenerCantidad();

            gestionProducto.registrarMovimientoInventario(codigo, tipoMovimiento, cantidad);

            mostrarInformacion("Movimiento de inventario registrado exitosamente.");
            refrescarTablaProductos();
            dialog.dispose();

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void abrirDialogoNuevoCliente() {
        DialogCliente dialog = new DialogCliente(ventana, this);
        dialog.setVisible(true);
    }

    private void registrarCliente(ActionEvent e) {
        try {
            Window ventanaPadre = obtenerVentanaPadre(e);

            if (!(ventanaPadre instanceof DialogCliente)) {
                throw new Exception("Error interno: no se pudo identificar el formulario de cliente.");
            }

            DialogCliente dialog = (DialogCliente) ventanaPadre;
            Cliente cliente = dialog.obtenerCliente();

            gestionCliente.registrarCliente(cliente);
            mostrarInformacion("Cliente registrado exitosamente.");
            refrescarTablaClientes();
            dialog.dispose();

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void abrirFormularioEditarCliente() {
        try {
            Cliente cliente = obtenerClienteSeleccionado();

            DialogCliente dialog = new DialogCliente(ventana, this);
            dialog.configurarModoEdicion();
            dialog.cargarCliente(cliente);
            dialog.setVisible(true);

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void editarCliente(ActionEvent e) {
        try {
            Window ventanaPadre = obtenerVentanaPadre(e);

            if (!(ventanaPadre instanceof DialogCliente)) {
                throw new Exception("Error interno: no se pudo identificar el formulario de edición de cliente.");
            }

            DialogCliente dialog = (DialogCliente) ventanaPadre;
            Cliente clienteEditado = dialog.obtenerCliente();

            gestionCliente.actualizarCliente(clienteEditado);
            mostrarInformacion("Cliente editado exitosamente.");
            refrescarTablaClientes();
            dialog.dispose();

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void cambiarEstadoClienteSeleccionado() {
        try {
            Cliente cliente = obtenerClienteSeleccionado();
            boolean estabaActivo = cliente.estaActivo();

            String mensaje = estabaActivo
                    ? "¿Está seguro de inactivar este cliente?"
                    : "¿Está seguro de activar este cliente?";

            int confirmacion = JOptionPane.showConfirmDialog(
                    ventana,
                    mensaje,
                    "Confirmar cambio de estado",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            gestionCliente.cambiarEstadoCliente(cliente.getCodigo());

            mostrarInformacion(
                    estabaActivo
                            ? "Cliente inactivado exitosamente."
                            : "Cliente activado exitosamente."
            );

            refrescarTablaClientes();

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void abrirHistorialCliente() {
        try {
            Cliente cliente = obtenerClienteSeleccionado();

            DialogHistorialCliente dialog = new DialogHistorialCliente(ventana);
            dialog.cargarCliente(cliente.getCodigo(), cliente.getNombre());
            dialog.setVisible(true);

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void abrirDialogoNuevoProveedor() {
        DialogProveedor dialog = new DialogProveedor(ventana, this);
        dialog.setVisible(true);
    }

    private void abrirDialogoRegistrarCompra() {
        DialogRegistrarCompra dialog = new DialogRegistrarCompra(ventana, this);
        dialog.setVisible(true);
    }

    private void refrescarTablaProductos() {
        PanelProducto panelProducto = ventana.getPanelProducto();
        panelProducto.cargarProductos(gestionProducto.obtenerProductos());
    }

    private void refrescarTablaClientes() {
        PanelCliente panelCliente = ventana.getPanelCliente();
        panelCliente.cargarClientes(gestionCliente.obtenerClientes());
    }
}