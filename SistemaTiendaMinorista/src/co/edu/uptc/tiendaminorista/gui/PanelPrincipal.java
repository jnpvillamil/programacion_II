package co.edu.uptc.tiendaminorista.gui;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import co.edu.uptc.tiendaminorista.gui.administrador.PanelCompraCliente;
import co.edu.uptc.tiendaminorista.gui.administrador.PanelHistorialCliente;
import co.edu.uptc.tiendaminorista.gui.administrador.PanelInicial;
import co.edu.uptc.tiendaminorista.gui.administrador.PanelRegistrosEmpleados;
import co.edu.uptc.tiendaminorista.dto.CredencialDto;
import co.edu.uptc.tiendaminorista.enums.TipoDocumentoEnum;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.CompasCliente;
import co.edu.uptc.tiendaminorista.modelo.CompraPro;
import co.edu.uptc.tiendaminorista.modelo.Empleado;
import co.edu.uptc.tiendaminorista.modelo.Proveedor;
import co.edu.uptc.tiendaminorista.modelo.Producto; 
import co.edu.uptc.tiendaminorista.negocio.GestionCliente;
import co.edu.uptc.tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.tiendaminorista.negocio.GestionProveedor;
import co.edu.uptc.tiendaminorista.negocio.SistemaSeguridad;
import co.edu.uptc.tiendaminorista.negocio.GestionEmpleado;
import co.edu.uptc.tiendaminorista.negocio.GestionCompasCliente; 
import co.edu.uptc.tiendaminorista.negocio.TiendaConfig;
import co.edu.uptc.tiendaminorista.modelo.MovimientoContable;

public class PanelPrincipal extends JFrame {

    private Evento evento;

    private PanelLogin panelLogin;
    private PanelInicial panelInicial;
    private GestionEmpleado gestionEmpleado;
    private GestionCliente gestionCliente;
    private GestionProveedor gestionProveedor;
    private GestionProducto gestionProducto;
    private GestionCompasCliente gestionCompasCliente; 
    private PanelRegistrosEmpleados empleados; 

    private SistemaSeguridad seguridad;
    private TiendaConfig tiendaConfig;

    private PanelCompraCliente compracliente;

    public PanelPrincipal() {
        seguridad = new SistemaSeguridad();
        this.tiendaConfig = new TiendaConfig();
        
        this.gestionEmpleado = tiendaConfig.getGestionEmpleado();
        this.gestionCliente = tiendaConfig.getGestionCliente();
        this.gestionProveedor = tiendaConfig.getGestionProveedor();
        this.gestionProducto = tiendaConfig.getGestionProducto();
        this.gestionCompasCliente = new GestionCompasCliente(); 
        
        evento = new Evento(this); 
        empleados = new PanelRegistrosEmpleados(evento);
        
        setTitle("Sistema Tienda Minorista");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        iniciarPaneles();

        add(panelLogin, BorderLayout.CENTER);

        if (panelInicial.getPanelRegistrosEmpleados() != null) {
            panelInicial.getPanelRegistrosEmpleados().cargarEmpleados(gestionEmpleado.listarEmpleados());
        }
    }

    private void iniciarPaneles() {
        panelLogin = new PanelLogin(evento);
        panelInicial = new PanelInicial(evento, gestionProducto, gestionCliente, gestionProveedor, empleados);
    }

    public void cambiarPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void loguear() {
        CredencialDto credencial = panelLogin.getCredencialusuario();
        
        if (credencial == null) {
            return;
        }
        
        if (seguridad.validarInicio(credencial)) {
            cambiarPanel(panelInicial);
            
            if (panelInicial.getPanelCliente() != null) {
                panelInicial.getPanelCliente().cargarClientes(gestionCliente.listarClientes());
            }
            panelInicial.cargarProveedores(gestionProveedor.listarProveedores());
            
            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    }

    public void cambiarVistaConsulta(String identificadorTarjeta) {
        if (panelInicial != null && panelInicial.getPanelConsultas() != null) {
            panelInicial.getPanelConsultas().conmutarVista(identificadorTarjeta);
        }
    }

    public PanelInicial getPanelInicial() {
        return panelInicial;
    }

    public GestionCliente getGestionCliente() {
        return gestionCliente;
    }

    public GestionProveedor getGestionProveedor() {
        return gestionProveedor;
    }

    public GestionProducto getGestionProducto() {
        return gestionProducto;
    }

    public void mostrarRegistroCliente() {
        panelInicial.mostrarRegistroCliente();
    }

    public void mostrarPanelCliente() {
        if (panelInicial.getPanelCliente() != null) {
            panelInicial.getPanelCliente().cargarClientes(gestionCliente.listarClientes());
        }
        panelInicial.mostrarClienteLista();
        revalidate();
        repaint();
    }

    public void regresarAlInicial() {
        panelInicial.mostrarListaActual();
    }

    public void mostrarActualizarCliente() {
        panelInicial.mostrarActualizarCliente();
    }

    public void mostrarRegistrarProveedor() {
        panelInicial.mostrarRegistrarProveedor();
    }

    public void mostrarProveedores() {
        panelInicial.cargarProveedores(gestionProveedor.listarProveedores());
        panelInicial.mostrarProveedorLista();
        revalidate();
        repaint();
    }

    public void mostrarActualizarProveedor() {
        panelInicial.mostrarActualizarProveedor();
    }
    
    public void mostrarCompraCliente() {
        panelInicial.mostrarCompraCliente();
        
        if (panelInicial.getPanelCompraCliente() != null) {
            panelInicial.getPanelCompraCliente().cargarClientesEnCombo(gestionCliente.listarClientes());
            panelInicial.getPanelCompraCliente().cargarProductosEnCombo(gestionProducto.listarProductos());
            panelInicial.getPanelCompraCliente().actualizarTablaCompras(gestionCompasCliente.listarTodasLasCompras());
        }
    }

    public void ejecutarCompraCliente() {
        PanelCompraCliente panelCompra = panelInicial.getPanelCompraCliente();
        if (panelCompra == null) return;

        try {
            Cliente clienteSel = panelCompra.getClienteSeleccionado();
            Producto productoSel = panelCompra.getProductoSeleccionado();
            int cantidad = panelCompra.getCantidad();

            gestionCompasCliente.registrarCompra(clienteSel, productoSel, cantidad);

            // Descontar stock del producto vendido
            gestionProducto.registrarMovimientoInventario(productoSel.getCodigo(), -cantidad);

            double totalVenta = productoSel.getPrecioVenta() * cantidad;
            registrarVenta(productoSel.getNombre(), totalVenta, clienteSel.getCodigo());

            JOptionPane.showMessageDialog(this, "Compra registrada con éxito.\nTotal: $" + totalVenta, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            panelCompra.actualizarTablaCompras(gestionCompasCliente.listarTodasLasCompras());
            panelCompra.cargarProductosEnCombo(gestionProducto.listarProductos());
            panelCompra.limpiarCampos();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número entero válido en la cantidad.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void registrarCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(panelInicial.getPanelRegistroCliente().getNombre());
            cliente.setDireccion(panelInicial.getPanelRegistroCliente().getDireccion());
            cliente.setTelefono(panelInicial.getPanelRegistroCliente().getTelefono());
            cliente.setTipodoc(TipoDocumentoEnum.valueOf(panelInicial.getPanelRegistroCliente().getTipoDoc()));
            cliente.setNumeroIdentificacion(panelInicial.getPanelRegistroCliente().getNumeroDoc());
            cliente.setTipoCliente(panelInicial.getPanelRegistroCliente().getTipoCliente());
            
            gestionCliente.agregarCliente(cliente);
            
            JOptionPane.showMessageDialog(this, "Cliente registrado correctamente");
            panelInicial.cargarClientes(gestionCliente.listarClientes());
            mostrarPanelCliente();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void registrarProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(panelInicial.getPanelRegistrarProveedor().getRazon());
        proveedor.setNit(panelInicial.getPanelRegistrarProveedor().getNit());
        proveedor.setDireccion(panelInicial.getPanelRegistrarProveedor().getDireccion());
        proveedor.setTelefono(panelInicial.getPanelRegistrarProveedor().getTelefono());
        proveedor.setCorreo(panelInicial.getPanelRegistrarProveedor().getCorreo());
        gestionProveedor.agregarProveedor(proveedor);
        JOptionPane.showMessageDialog(this, "Proveedor registrado correctamente");
        panelInicial.cargarProveedores(gestionProveedor.listarProveedores());
        mostrarProveedores();
    }

    public void actualizarCliente() {
        String codigo = panelInicial.getPanelActualizarCliente().getClienteCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para actualizar");
            return;
        }
        try {
            Cliente cliente = new Cliente();
            cliente.setCodigo(codigo);
            cliente.setNombre(panelInicial.getPanelActualizarCliente().getNombre());
            cliente.setDireccion(panelInicial.getPanelActualizarCliente().getDireccion());
            cliente.setTelefono(panelInicial.getPanelActualizarCliente().getTelefono());
            cliente.setTipodoc(TipoDocumentoEnum.valueOf(panelInicial.getPanelActualizarCliente().getTipoDoc()));
            cliente.setNumeroIdentificacion(panelInicial.getPanelActualizarCliente().getNumeroDoc());
            cliente.setTipoCliente(panelInicial.getPanelActualizarCliente().getTipoCliente());
            
            gestionCliente.actualizarCliente(cliente);
            
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente");
            panelInicial.cargarClientes(gestionCliente.listarClientes());
            mostrarPanelCliente();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void desactivarCliente() {
        String codigo = panelInicial.getPanelActualizarCliente().getClienteCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para desactivar");
            return;
        }
        try {
            gestionCliente.desactivarCliente(codigo);
            JOptionPane.showMessageDialog(this, "Cliente desactivado correctamente");
            panelInicial.cargarClientes(gestionCliente.listarClientes());
            panelInicial.getPanelActualizarCliente().refreshClienteSeleccionado();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void activarCliente() {
        String codigo = panelInicial.getPanelActualizarCliente().getClienteCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para activar");
            return;
        }
        try {
            gestionCliente.activarCliente(codigo);
            JOptionPane.showMessageDialog(this, "Cliente activado correctamente");
            panelInicial.cargarClientes(gestionCliente.listarClientes());
            panelInicial.getPanelActualizarCliente().refreshClienteSeleccionado();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarProveedor() {
        String codigo = panelInicial.getPanelActualizarProveedor().getProveedorCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para actualizar");
            return;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(panelInicial.getPanelActualizarProveedor().getRazon());
        proveedor.setNit(panelInicial.getPanelActualizarProveedor().getNit());
        proveedor.setDireccion(panelInicial.getPanelActualizarProveedor().getDireccion());
        proveedor.setTelefono(panelInicial.getPanelActualizarProveedor().getTelefono());
        proveedor.setCorreo(panelInicial.getPanelActualizarProveedor().getCorreo());
        gestionProveedor.actualizarProveedor(proveedor);
        JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente");
        panelInicial.cargarProveedores(gestionProveedor.listarProveedores());
        mostrarProveedores();
    }

    public void desactivarProveedor() {
        String codigo = panelInicial.getPanelActualizarProveedor().getProveedorCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para desactivar");
            return;
        }
        gestionProveedor.desactivarProveedor(codigo);
        JOptionPane.showMessageDialog(this, "Proveedor desactivado correctamente");
        panelInicial.cargarProveedores(gestionProveedor.listarProveedores());
        panelInicial.getPanelActualizarProveedor().refreshProveedorSeleccionado();
    }

    public void activarProveedor() {
        String codigo = panelInicial.getPanelActualizarProveedor().getProveedorCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para activar");
            return;
        }
        gestionProveedor.activarProveedor(codigo);
        JOptionPane.showMessageDialog(this, "Proveedor activado correctamente");
        panelInicial.cargarProveedores(gestionProveedor.listarProveedores());
        panelInicial.getPanelActualizarProveedor().refreshProveedorSeleccionado();
    }
    
    public void registrarCompra(String producto, double valor, String nitProveedor) {
        if (panelInicial.getPanelGestionContable() != null) {
            panelInicial.getPanelGestionContable().getGestionContable().registrarEgreso(producto, valor, nitProveedor);
            panelInicial.getPanelGestionContable().actualizarDatos();
            JOptionPane.showMessageDialog(this, "Compra registrada: " + producto + " por $" + valor);
        }
    }

    public void mostrarComprasPro() {
        panelInicial.mostrarComprasProveedor();
    }

    public void registrarEmpleado() {
        PanelRegistrosEmpleados panelEmp = panelInicial.getPanelRegistrosEmpleados();
        String correo = panelEmp.getCorreo();
        String password = panelEmp.getContraseña();
        String rol = panelEmp.getTipoEmpleado(); 

        if (correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.");
            return;
        }

        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setCorreo(correo);
        nuevoEmpleado.setPassword(password);
        nuevoEmpleado.setTipoEmpleado(rol);

        gestionEmpleado.guardar(nuevoEmpleado);
        JOptionPane.showMessageDialog(this, "Empleado registrado de forma correcta.");
        
        panelEmp.cargarEmpleados(gestionEmpleado.listarEmpleados());
        panelEmp.limpiarCampos();
    }

    public void actualizarEmpleado() {
        PanelRegistrosEmpleados panelEmp = panelInicial.getPanelRegistrosEmpleados();
        String correo = panelEmp.getCorreo();
        String password = panelEmp.getContraseña();
        String rol = panelEmp.getTipoEmpleado(); 

        if (correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos para poder actualizar.");
            return;
        }

        Empleado empleadoModificado = new Empleado();
        empleadoModificado.setCorreo(correo);
        empleadoModificado.setPassword(password);
        empleadoModificado.setTipoEmpleado(rol); 
        
        gestionEmpleado.actualizar(empleadoModificado);
        JOptionPane.showMessageDialog(this, "Empleado modificado con éxito.");
        
        panelEmp.cargarEmpleados(gestionEmpleado.listarEmpleados());
        panelEmp.limpiarCampos();
    }

    public void eliminarEmpleado() {
        PanelRegistrosEmpleados panelEmp = panelInicial.getPanelRegistrosEmpleados();
        String correo = panelEmp.getCorreo();

        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla para eliminar.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this, 
            "¿Desea eliminar al empleado: " + correo + "?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            Empleado emp = new Empleado();
            emp.setCorreo(correo);

            gestionEmpleado.eliminar(emp);
            JOptionPane.showMessageDialog(this, "Empleado eliminado.");

            panelEmp.cargarEmpleados(gestionEmpleado.listarEmpleados());
            panelEmp.limpiarCampos();
        }
    }

    public void filtrarClientes(String texto) {
        List<Cliente> filtrados = gestionCliente.consultarClientes(texto);
        if (panelInicial.getPanelCliente() != null) {
            panelInicial.getPanelCliente().cargarClientes(filtrados);
        }
    }

    public void mostrarPantallaHistorial() {
        panelInicial.mostrarPantallaHistorial();
    }

    public void buscarHistorialCliente() {
        PanelHistorialCliente panelHistorial = panelInicial.getPanelHistorialCliente();
        if (panelHistorial == null) return;

        Cliente clienteSel = panelHistorial.getClienteSeleccionado();
        
        if (clienteSel == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoCliente = clienteSel.getCodigo();
        String nombreCliente = clienteSel.getNombre();
        java.util.List<CompasCliente> comprasDelCliente = gestionCompasCliente.listarComprasPorCliente(codigoCliente, nombreCliente);

        if (comprasDelCliente == null || comprasDelCliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Este cliente no tiene compras registradas.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }

        panelHistorial.actualizarTabla(comprasDelCliente);
        
        revalidate();
        repaint();
    }

    public void registrarVenta(String producto, double valor, String idCliente) {
        if (panelInicial.getPanelGestionContable() != null) {
            panelInicial.getPanelGestionContable().getGestionContable().registrarIngreso(producto, valor, idCliente);
            panelInicial.getPanelGestionContable().actualizarDatos();
        }
    } 

    public void ejecutarConsultaCompraProveedor() {
        if (panelInicial.getPanelConsultas() == null) return;
        try {
            String nit = panelInicial.getPanelConsultas().getTxtNitProveedor().getText().trim();
            String fD = panelInicial.getPanelConsultas().getTxtFechaDesde().getText().trim();
            String fH = panelInicial.getPanelConsultas().getTxtFechaHasta().getText().trim();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            List<CompraPro> lista = gestionProveedor.listarComprasProveedorPorFecha(
                                        nit, LocalDate.parse(fD, formatter), LocalDate.parse(fH, formatter));

            panelInicial.getPanelConsultas().actualizarTablaComprasPro(lista);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Verifique el formato de fecha (dd/MM/yyyy) y los datos.");
        }
    }

    public void ejecutarConsultaVentasPorFecha() {
        if (panelInicial.getPanelConsultas() == null) return;
        try {
            String fechaTexto = panelInicial.getPanelConsultas().getTxtFechaVentas().getText().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(fechaTexto, formatter);
            Date fechaBusqueda = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.util.List<CompasCliente> ventas = gestionCompasCliente.obtenerVentasPorFecha(fechaBusqueda);
            panelInicial.getPanelConsultas().actualizarTablaVentasFecha(ventas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Verifique el formato de fecha (dd/MM/yyyy).", "Consulta", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void mostrarPanelStockMinimo() {
        if (panelInicial.getPanelConsultas() == null) return;
        java.util.List<Producto> lista = gestionProducto.listarProductosBajoStock();
        panelInicial.getPanelConsultas().actualizarTablaStockMinimo(lista);
        panelInicial.getPanelConsultas().conmutarVista("STOCK_MIN");
    }

    public void ejecutarConsultaStockMinimo() {
        if (panelInicial.getPanelConsultas() == null) return;
        java.util.List<Producto> lista = gestionProducto.listarProductosBajoStock();
        panelInicial.getPanelConsultas().actualizarTablaStockMinimo(lista);
    }

    public void mostrarHistorialClienteConsulta() {
        if (panelInicial.getPanelConsultas() == null) return;
        panelInicial.getPanelConsultas().getTxtDocumentoCliente().setText("");
        panelInicial.getPanelConsultas().actualizarTablaHistorialCliente(null);
        panelInicial.getPanelConsultas().conmutarVista("HISTORIAL_CLIENTE");
    }

    public void ejecutarConsultaHistorialClienteConsulta() {
        if (panelInicial.getPanelConsultas() == null) return;
        try {
            String documento = panelInicial.getPanelConsultas().getTxtDocumentoCliente().getText().trim();
            if (documento == null || documento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el documento del cliente para buscar su historial.", "Consulta", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            java.util.List<CompasCliente> compras = gestionCompasCliente.listarComprasPorCliente(documento);
            if (compras == null || compras.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron compras para ese cliente.", "Consulta", JOptionPane.INFORMATION_MESSAGE);
            }
            panelInicial.getPanelConsultas().actualizarTablaHistorialCliente(compras);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al ejecutar la consulta. Verifique los datos.", "Consulta", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void ejecutarConsultaMovimientosContables() {
        if (panelInicial.getPanelConsultas() == null) return;
        try {
            String cuenta = panelInicial.getPanelConsultas().getTxtCuentaContable().getText().trim();
            String fD = panelInicial.getPanelConsultas().getTxtFechaDesdeMov().getText().trim();
            String fH = panelInicial.getPanelConsultas().getTxtFechaHastaMov().getText().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate desde = LocalDate.parse(fD, formatter);
            LocalDate hasta = LocalDate.parse(fH, formatter);
            java.util.List<MovimientoContable> movimientos = panelInicial.getPanelGestionContable() != null
                    ? panelInicial.getPanelGestionContable().getGestionContable().obtenerMovimientosPorCuentaYPeriodo(cuenta.isEmpty() ? "TODAS" : cuenta, desde, hasta)
                    : new java.util.ArrayList<>();
            panelInicial.getPanelConsultas().actualizarTablaMovimientosContables(movimientos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Verifique el formato de fecha (dd/MM/yyyy) y los datos.", "Consulta", JOptionPane.WARNING_MESSAGE);
        }
    }
}