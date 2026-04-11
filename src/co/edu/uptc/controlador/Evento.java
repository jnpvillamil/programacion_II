package co.edu.uptc.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JOptionPane;

import co.edu.uptc.dto.CredencialDto;
import co.edu.uptc.gui.ClienteGUI;
import co.edu.uptc.gui.CompraGUI;
import co.edu.uptc.gui.ConsultaGUI;
import co.edu.uptc.gui.ContabilidadGUI;
import co.edu.uptc.gui.EmpleadoGUI;
import co.edu.uptc.gui.LoginGUI;
import co.edu.uptc.gui.MenuPrincipalGUI;
import co.edu.uptc.gui.NominaGUI;
import co.edu.uptc.gui.ProductoGUI;
import co.edu.uptc.gui.ProveedorGUI;
import co.edu.uptc.gui.ReporteGUI;
import co.edu.uptc.gui.VentaGUI;
import co.edu.uptc.gui.modelo.Cliente;
import co.edu.uptc.gui.modelo.Compra;
import co.edu.uptc.gui.modelo.Empleado;
import co.edu.uptc.gui.modelo.MovimientoContable;
import co.edu.uptc.gui.modelo.Nomina;
import co.edu.uptc.gui.modelo.Producto;
import co.edu.uptc.gui.modelo.Proveedor;
import co.edu.uptc.gui.modelo.Reporte;
import co.edu.uptc.gui.modelo.Usuario;
import co.edu.uptc.gui.modelo.Venta;
import co.edu.uptc.gui.negocio.GestionCliente;
import co.edu.uptc.gui.negocio.GestionCompra;
import co.edu.uptc.gui.negocio.GestionConsulta;
import co.edu.uptc.gui.negocio.GestionContabilidad;
import co.edu.uptc.gui.negocio.GestionEmpleado;
import co.edu.uptc.gui.negocio.GestionNomina;
import co.edu.uptc.gui.negocio.GestionProducto;
import co.edu.uptc.gui.negocio.GestionProveedor;
import co.edu.uptc.gui.negocio.GestionReporte;
import co.edu.uptc.gui.negocio.GestionSeguridad;
import co.edu.uptc.gui.negocio.GestionVenta;

public class Evento implements ActionListener {

    private LoginGUI loginGUI;
    private MenuPrincipalGUI menuPrincipalGUI;
    private ClienteGUI clienteGUI;
    private ProductoGUI productoGUI;
    private ProveedorGUI proveedorGUI;
    private CompraGUI compraGUI;
    private VentaGUI ventaGUI;
    private ConsultaGUI consultaGUI;
    private ContabilidadGUI contabilidadGUI;
    private ReporteGUI reporteGUI;
    private EmpleadoGUI empleadoGUI;
    private NominaGUI nominaGUI;

    private GestionSeguridad gestionSeguridad;
    private GestionCliente gestionCliente;
    private GestionProducto gestionProducto;
    private GestionProveedor gestionProveedor;
    private GestionCompra gestionCompra;
    private GestionVenta gestionVenta;
    private GestionConsulta gestionConsulta;
    private GestionContabilidad gestionContabilidad;
    private GestionReporte gestionReporte;
    private GestionEmpleado gestionEmpleado;
    private GestionNomina gestionNomina;

    private Usuario usuarioSesion;

    public Evento() {
        gestionSeguridad = new GestionSeguridad();
        gestionCliente = new GestionCliente();
        gestionProducto = new GestionProducto();
        gestionProveedor = new GestionProveedor();
        gestionCompra = new GestionCompra();
        gestionVenta = new GestionVenta();
        gestionConsulta = new GestionConsulta();
        gestionContabilidad = new GestionContabilidad();
        gestionReporte = new GestionReporte();
        gestionEmpleado = new GestionEmpleado();
        gestionNomina = new GestionNomina();
    }

    public void iniciar() {
        loginGUI = new LoginGUI();
        asignarEventosLogin();
        loginGUI.setVisible(true);
    }

    private void asignarEventosLogin() {
        loginGUI.getBtnIngresar().addActionListener(this);
        loginGUI.getBtnSalir().addActionListener(this);
    }

    private void abrirMenuPrincipal() {
        menuPrincipalGUI = new MenuPrincipalGUI(usuarioSesion);
        menuPrincipalGUI.getBtnClientes().addActionListener(this);
        menuPrincipalGUI.getBtnProductos().addActionListener(this);
        menuPrincipalGUI.getBtnProveedores().addActionListener(this);
        menuPrincipalGUI.getBtnCompras().addActionListener(this);
        menuPrincipalGUI.getBtnVentas().addActionListener(this);
        menuPrincipalGUI.getBtnConsultas().addActionListener(this);
        menuPrincipalGUI.getBtnContabilidad().addActionListener(this);
        menuPrincipalGUI.getBtnReportes().addActionListener(this);
        menuPrincipalGUI.getBtnEmpleados().addActionListener(this);
        menuPrincipalGUI.getBtnNomina().addActionListener(this);
        menuPrincipalGUI.getBtnCerrarSesion().addActionListener(this);
        menuPrincipalGUI.setVisible(true);
    }

    private void abrirModuloClientes() {
        clienteGUI = new ClienteGUI();
        clienteGUI.getBtnRegistrar().addActionListener(this);
        clienteGUI.getBtnModificar().addActionListener(this);
        clienteGUI.getBtnEliminar().addActionListener(this);
        clienteGUI.getBtnBuscar().addActionListener(this);
        clienteGUI.getBtnLimpiar().addActionListener(this);
        clienteGUI.cargarTabla(gestionCliente.listarClientes());
        clienteGUI.setVisible(true);
    }

    private void abrirModuloProductos() {
        productoGUI = new ProductoGUI();
        productoGUI.getBtnRegistrar().addActionListener(this);
        productoGUI.getBtnModificar().addActionListener(this);
        productoGUI.getBtnEliminar().addActionListener(this);
        productoGUI.getBtnBuscar().addActionListener(this);
        productoGUI.getBtnLimpiar().addActionListener(this);
        productoGUI.cargarTabla(gestionProducto.listarProductos()); //explicaion porfa
        productoGUI.setVisible(true);
    }

    private void abrirModuloProveedores() {
        proveedorGUI = new ProveedorGUI();
        proveedorGUI.getBtnRegistrar().addActionListener(this);
        proveedorGUI.getBtnModificar().addActionListener(this);
        proveedorGUI.getBtnEliminar().addActionListener(this);
        proveedorGUI.getBtnBuscar().addActionListener(this);
        proveedorGUI.getBtnLimpiar().addActionListener(this);
        proveedorGUI.cargarTabla(gestionProveedor.listarProveedores());
        proveedorGUI.setVisible(true);
    }

    private void abrirModuloCompras() {
        compraGUI = new CompraGUI();
        compraGUI.getBtnRegistrar().addActionListener(this);
        compraGUI.getBtnBuscar().addActionListener(this);
        compraGUI.getBtnLimpiar().addActionListener(this);
        compraGUI.setVisible(true);
    }

    private void abrirModuloVentas() {
        ventaGUI = new VentaGUI();
        ventaGUI.getBtnRegistrar().addActionListener(this);
        ventaGUI.getBtnBuscar().addActionListener(this);
        ventaGUI.getBtnAnular().addActionListener(this);
        ventaGUI.getBtnLimpiar().addActionListener(this);
        ventaGUI.setVisible(true);
    }

    private void abrirModuloConsultas() {
        consultaGUI = new ConsultaGUI();
        consultaGUI.getBtnConsultar().addActionListener(this);
        consultaGUI.getBtnLimpiar().addActionListener(this);
        consultaGUI.setVisible(true);
    }

    private void abrirModuloContabilidad() {
        contabilidadGUI = new ContabilidadGUI();
        contabilidadGUI.getBtnRegistrar().addActionListener(this);
        contabilidadGUI.getBtnListar().addActionListener(this);
        contabilidadGUI.setVisible(true);
    }

    private void abrirModuloReportes() {
        reporteGUI = new ReporteGUI();
        reporteGUI.getBtnGenerar().addActionListener(this);
        reporteGUI.setVisible(true);
    }

    private void abrirModuloEmpleados() {
        empleadoGUI = new EmpleadoGUI();
        empleadoGUI.getBtnRegistrar().addActionListener(this);
        empleadoGUI.getBtnModificar().addActionListener(this);
        empleadoGUI.getBtnEliminar().addActionListener(this);
        empleadoGUI.getBtnBuscar().addActionListener(this);
        empleadoGUI.getBtnLimpiar().addActionListener(this);
        empleadoGUI.cargarTabla(gestionEmpleado.listarEmpleados());
        empleadoGUI.setVisible(true);
    }

    private void abrirModuloNomina() {
        nominaGUI = new NominaGUI();
        nominaGUI.getBtnRegistrar().addActionListener(this);
        nominaGUI.getBtnBuscar().addActionListener(this);
        nominaGUI.getBtnLimpiar().addActionListener(this);
        nominaGUI.cargarTabla(gestionNomina.listarNominas());
        nominaGUI.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "INGRESAR":
                iniciarSesion();
                break;
            case "SALIR":
                System.exit(0); //explicar porfa
                break;
            case "CERRAR_SESION":
                menuPrincipalGUI.dispose(); //aca que acontece
                usuarioSesion = null; ///aca tambuen
                iniciar();
                break;

            case "ABRIR_CLIENTES":
                abrirModuloClientes();
                break;
            case "ABRIR_PRODUCTOS":
                abrirModuloProductos();
                break;
            case "ABRIR_PROVEEDORES":
                abrirModuloProveedores();
                break;
            case "ABRIR_COMPRAS":
                abrirModuloCompras();
                break;
            case "ABRIR_VENTAS":
                abrirModuloVentas();
                break;
            case "ABRIR_CONSULTAS":
                abrirModuloConsultas();
                break;
            case "ABRIR_CONTABILIDAD":
                abrirModuloContabilidad();
                break;
            case "ABRIR_REPORTES":
                abrirModuloReportes();
                break;
            case "ABRIR_EMPLEADOS":
                abrirModuloEmpleados();
                break;
            case "ABRIR_NOMINA":
                abrirModuloNomina();
                break;

            case "REGISTRAR_CLIENTE":
                registrarCliente();
                break;
            case "MODIFICAR_CLIENTE":
                modificarCliente();
                break;
            case "ELIMINAR_CLIENTE":
                eliminarCliente();
                break;
            case "BUSCAR_CLIENTE":
                buscarCliente();
                break;
            case "LIMPIAR_CLIENTE":
                clienteGUI.limpiarFormulario();
                break;

            case "REGISTRAR_PRODUCTO":
                registrarProducto();
                break;
            case "MODIFICAR_PRODUCTO":
                modificarProducto();
                break;
            case "ELIMINAR_PRODUCTO":
                eliminarProducto();
                break;
            case "BUSCAR_PRODUCTO":
                buscarProducto();
                break;
            case "LIMPIAR_PRODUCTO":
                productoGUI.limpiarFormulario();
                break;

            case "REGISTRAR_PROVEEDOR":
                registrarProveedor();
                break;
            case "MODIFICAR_PROVEEDOR":
                modificarProveedor();
                break;
            case "ELIMINAR_PROVEEDOR":
                eliminarProveedor();
                break;
            case "BUSCAR_PROVEEDOR":
                buscarProveedor();
                break;
            case "LIMPIAR_PROVEEDOR":
                proveedorGUI.limpiarFormulario();
                break;

            case "REGISTRAR_COMPRA":
                registrarCompra();
                break;
            case "BUSCAR_COMPRA":
                buscarCompra();
                break;
            case "LIMPIAR_COMPRA":
                compraGUI.limpiarFormulario();
                break;

            case "REGISTRAR_VENTA":
                registrarVenta();
                break;
            case "BUSCAR_VENTA":
                buscarVenta();
                break;
            case "ANULAR_VENTA":
                anularVenta();
                break;
            case "LIMPIAR_VENTA":
                ventaGUI.limpiarFormulario();
                break;

            case "CONSULTAR_COMPRAS_PROVEEDOR":
                consultarComprasProveedor();
                break;
            case "LIMPIAR_CONSULTA":
                consultaGUI.limpiar();
                break;

            case "REGISTRAR_MOVIMIENTO":
                registrarMovimiento();
                break;
            case "LISTAR_MOVIMIENTOS":
                listarMovimientos();
                break;

            case "GENERAR_REPORTE":
                generarReporte();
                break;

            case "REGISTRAR_EMPLEADO":
                registrarEmpleado();
                break;
            case "MODIFICAR_EMPLEADO":
                modificarEmpleado();
                break;
            case "ELIMINAR_EMPLEADO":
                eliminarEmpleado();
                break;
            case "BUSCAR_EMPLEADO":
                buscarEmpleado();
                break;
            case "LIMPIAR_EMPLEADO":
                empleadoGUI.limpiarFormulario();
                break;

            case "REGISTRAR_NOMINA":
                registrarNomina();
                break;
            case "BUSCAR_NOMINA":
                buscarNomina();
                break;
            case "LIMPIAR_NOMINA":
                nominaGUI.limpiarFormulario();
                break;

            default:
                JOptionPane.showMessageDialog(null, "Acción no implementada: " + comando);
                break;
        }
    }

    private void iniciarSesion() {
        String usuario = loginGUI.getTxtUsuario().getText().trim();
        String contrasena = new String(loginGUI.getTxtContrasena().getPassword());//porque nuevo string

        CredencialDto credencialDto = new CredencialDto(usuario, contrasena);
        Usuario usuarioValidado = gestionSeguridad.validarIngreso(credencialDto);

        if (usuarioValidado != null) {
            usuarioSesion = usuarioValidado;
            loginGUI.mostrarMensaje("Bienvenido " + usuarioValidado.getNombreUsuario());
            loginGUI.dispose();
            abrirMenuPrincipal();
        } else {
            loginGUI.mostrarMensaje("Usuario o contraseña incorrectos");
        }
    }

    private void registrarCliente() {
        Cliente cliente = clienteGUI.obtenerClienteFormulario();
        boolean registrado = gestionCliente.registrarCliente(cliente);

        if (registrado) {
            clienteGUI.mostrarMensaje("Cliente registrado correctamente");
            clienteGUI.cargarTabla(gestionCliente.listarClientes());
            clienteGUI.limpiarFormulario();
        } else {
            clienteGUI.mostrarMensaje("Ya existe un cliente con ese código");
        }
    }

    private void modificarCliente() {
        Cliente cliente = clienteGUI.obtenerClienteFormulario();
        boolean modificado = gestionCliente.modificarCliente(cliente);

        if (modificado) {
            clienteGUI.mostrarMensaje("Cliente modificado correctamente");
            clienteGUI.cargarTabla(gestionCliente.listarClientes());
            clienteGUI.limpiarFormulario();
        } else {
            clienteGUI.mostrarMensaje("No se encontró el cliente para modificar");
        }
    }

    private void eliminarCliente() {
        String codigo = clienteGUI.obtenerCodigoCliente();
        boolean eliminado = gestionCliente.eliminarCliente(codigo);

        if (eliminado) {
            clienteGUI.mostrarMensaje("Cliente eliminado correctamente");
            clienteGUI.cargarTabla(gestionCliente.listarClientes());
            clienteGUI.limpiarFormulario();
        } else {
            clienteGUI.mostrarMensaje("No se encontró el cliente");
        }
    }

    private void buscarCliente() { //si se buscan por las diferentes opciones codigo,nombre,numero documento telefono
        String codigo = clienteGUI.obtenerCodigoCliente();
        Cliente cliente = gestionCliente.buscarCliente(codigo);

        if (cliente != null) {
            clienteGUI.cargarClienteEnFormulario(cliente);
        } else {
            clienteGUI.mostrarMensaje("Cliente no encontrado");
        }
    }

    private void registrarProducto() {
        try {
            Producto producto = productoGUI.obtenerProductoFormulario();
            boolean registrado = gestionProducto.registrarProducto(producto);

            if (registrado) {
                productoGUI.mostrarMensaje("Producto registrado correctamente");
                productoGUI.cargarTabla(gestionProducto.listarProductos());
                productoGUI.limpiarFormulario();
            } else {
                productoGUI.mostrarMensaje("Ya existe un producto con ese código");
            }
        } catch (NumberFormatException e) {
            productoGUI.mostrarMensaje("Verifique los valores numéricos del producto");
        }
    }

    private void modificarProducto() {
        try {
            Producto producto = productoGUI.obtenerProductoFormulario();
            boolean modificado = gestionProducto.modificarProducto(producto);

            if (modificado) {
                productoGUI.mostrarMensaje("Producto modificado correctamente");
                productoGUI.cargarTabla(gestionProducto.listarProductos());
                productoGUI.limpiarFormulario();
            } else {
                productoGUI.mostrarMensaje("No se encontró el producto para modificar");
            }
        } catch (NumberFormatException e) {
            productoGUI.mostrarMensaje("Verifique los valores numéricos del producto");
        }
    }

    private void eliminarProducto() {
        String codigo = productoGUI.obtenerCodigoProducto();
        boolean eliminado = gestionProducto.eliminarProducto(codigo);

        if (eliminado) {
            productoGUI.mostrarMensaje("Producto eliminado correctamente");
            productoGUI.cargarTabla(gestionProducto.listarProductos());
            productoGUI.limpiarFormulario();
        } else {
            productoGUI.mostrarMensaje("No se encontró el producto");
        }
    }

    private void buscarProducto() {
        String codigo = productoGUI.obtenerCodigoProducto();
        Producto producto = gestionProducto.buscarProducto(codigo);

        if (producto != null) {
            productoGUI.cargarProductoEnFormulario(producto);
        } else {
            productoGUI.mostrarMensaje("Producto no encontrado");
        }
    }

    private void registrarProveedor() {
        Proveedor proveedor = proveedorGUI.obtenerProveedorFormulario();
        boolean registrado = gestionProveedor.registrarProveedor(proveedor);

        if (registrado) {
            proveedorGUI.mostrarMensaje("Proveedor registrado correctamente");
            proveedorGUI.cargarTabla(gestionProveedor.listarProveedores());
            proveedorGUI.limpiarFormulario();
        } else {
            proveedorGUI.mostrarMensaje("Ya existe un proveedor con ese código");
        }
    }

    private void modificarProveedor() {
        Proveedor proveedor = proveedorGUI.obtenerProveedorFormulario();
        boolean modificado = gestionProveedor.modificarProveedor(proveedor);

        if (modificado) {
            proveedorGUI.mostrarMensaje("Proveedor modificado correctamente");
            proveedorGUI.cargarTabla(gestionProveedor.listarProveedores());
            proveedorGUI.limpiarFormulario();
        } else {
            proveedorGUI.mostrarMensaje("No se encontró el proveedor para modificar");
        }
    }

    private void eliminarProveedor() {
        String codigo = proveedorGUI.obtenerCodigoProveedor();
        boolean eliminado = gestionProveedor.eliminarProveedor(codigo);

        if (eliminado) {
            proveedorGUI.mostrarMensaje("Proveedor eliminado correctamente");
            proveedorGUI.cargarTabla(gestionProveedor.listarProveedores());
            proveedorGUI.limpiarFormulario();
        } else {
            proveedorGUI.mostrarMensaje("No se encontró el proveedor");
        }
    }

    private void buscarProveedor() {
        String codigo = proveedorGUI.obtenerCodigoProveedor();
        Proveedor proveedor = gestionProveedor.buscarProveedor(codigo);

        if (proveedor != null) {
            proveedorGUI.cargarProveedorEnFormulario(proveedor);
        } else {
            proveedorGUI.mostrarMensaje("Proveedor no encontrado");
        }
    }

    private void registrarCompra() {
        try {
            Proveedor proveedor = gestionProveedor.buscarProveedor(compraGUI.getCodigoProveedor());
            if (proveedor == null) {
                compraGUI.mostrarMensaje("Proveedor no encontrado");
                return;
            }

            Compra compra = new Compra();
            compra.setNumeroFacturaProveedor(compraGUI.getFacturaProveedor());
            compra.setProveedor(proveedor);
            compra.setFechaHora(LocalDateTime.now());

            boolean registrada = gestionCompra.registrarCompra(compra);
            if (registrada) {
                compraGUI.setTotalCompra(compraGUI.getTotalCompra());
                compraGUI.mostrarMensaje("Compra registrada");
                compraGUI.limpiarFormulario();
            } else {
                compraGUI.mostrarMensaje("Ya existe una compra con esa factura");
            }
        } catch (NumberFormatException e) {
            compraGUI.mostrarMensaje("Verifique el total de la compra");
        }
    }

    private void buscarCompra() {
        Compra compra = gestionCompra.buscarCompra(compraGUI.getFacturaProveedor());
        if (compra != null) {
            compraGUI.setTotalCompra(compra.getTotal());
            compraGUI.mostrarMensaje("Compra encontrada");
        } else {
            compraGUI.mostrarMensaje("Compra no encontrada");
        }
    }

    private void registrarVenta() {
        try {
            Cliente cliente = gestionCliente.buscarCliente(ventaGUI.getCodigoCliente());
            if (cliente == null) {
                ventaGUI.mostrarMensaje("Cliente no encontrado");
                return;
            }

            Venta venta = new Venta();
            venta.setNumeroFactura(ventaGUI.getFactura());
            venta.setCliente(cliente);
            venta.setFormaPago(ventaGUI.getFormaPago());
            venta.setFechaHora(LocalDateTime.now());

            boolean registrada = gestionVenta.registrarVenta(venta);
            if (registrada) {
                ventaGUI.setTotalVenta(ventaGUI.getTotalVenta());
                ventaGUI.mostrarMensaje("Venta registrada");
                ventaGUI.limpiarFormulario();
            } else {
                ventaGUI.mostrarMensaje("Ya existe una venta con esa factura");
            }
        } catch (NumberFormatException e) {
            ventaGUI.mostrarMensaje("Verifique el total de la venta");
        }
    }

    private void buscarVenta() {
        Venta venta = gestionVenta.buscarVenta(ventaGUI.getFactura());
        if (venta != null) {
            ventaGUI.setTotalVenta(venta.getTotal());
            ventaGUI.mostrarMensaje("Venta encontrada. Estado: " + venta.getEstado());
        } else {
            ventaGUI.mostrarMensaje("Venta no encontrada");
        }
    }

    private void anularVenta() {
        boolean anulada = gestionVenta.anularVenta(ventaGUI.getFactura());
        if (anulada) {
            ventaGUI.mostrarMensaje("Venta anulada correctamente");
        } else {
            ventaGUI.mostrarMensaje("Venta no encontrada");
        }
    }

    private void consultarComprasProveedor() {
        List<Compra> compras = gestionConsulta.consultarComprasPorProveedor(consultaGUI.getCodigoProveedor());
        StringBuilder sb = new StringBuilder();

        if (compras.isEmpty()) {
            sb.append("No se encontraron compras para el proveedor.");
        } else {
            for (Compra compra : compras) {
                sb.append("Factura: ").append(compra.getNumeroFacturaProveedor())
                  .append(" | Fecha: ").append(compra.getFechaHora())
                  .append(" | Total: ").append(compra.getTotal())
                  .append("\n");
            }
        }
        consultaGUI.setResultados(sb.toString());
    }

    private void registrarMovimiento() {
        try {
            MovimientoContable movimiento = contabilidadGUI.obtenerMovimientoFormulario();
            gestionContabilidad.registrarMovimiento(movimiento);
            contabilidadGUI.mostrarMensaje("Movimiento registrado");
        } catch (NumberFormatException e) {
            contabilidadGUI.mostrarMensaje("Verifique el valor del movimiento");
        }
    }

    private void listarMovimientos() {
        contabilidadGUI.cargarTabla(gestionContabilidad.listarMovimientos());
    }

    private void generarReporte() {
        Reporte reporte = reporteGUI.obtenerReporteFormulario();
        String descripcion = gestionReporte.generarDescripcionReporte(reporte);
        reporteGUI.mostrarResultado(descripcion);
    }

    private void registrarEmpleado() {
        try {
            Empleado empleado = empleadoGUI.obtenerEmpleadoFormulario();
            boolean registrado = gestionEmpleado.registrarEmpleado(empleado);

            if (registrado) {
                empleadoGUI.mostrarMensaje("Empleado registrado correctamente");
                empleadoGUI.cargarTabla(gestionEmpleado.listarEmpleados());
                empleadoGUI.limpiarFormulario();
            } else {
                empleadoGUI.mostrarMensaje("Ya existe un empleado con ese código");
            }
        } catch (NumberFormatException e) {
            empleadoGUI.mostrarMensaje("Verifique el salario base");
        }
    }

    private void modificarEmpleado() {
        try {
            Empleado empleado = empleadoGUI.obtenerEmpleadoFormulario();
            boolean modificado = gestionEmpleado.modificarEmpleado(empleado);

            if (modificado) {
                empleadoGUI.mostrarMensaje("Empleado modificado correctamente");
                empleadoGUI.cargarTabla(gestionEmpleado.listarEmpleados());
                empleadoGUI.limpiarFormulario();
            } else {
                empleadoGUI.mostrarMensaje("No se encontró el empleado para modificar");
            }
        } catch (NumberFormatException e) {
            empleadoGUI.mostrarMensaje("Verifique el salario base");
        }
    }

    private void eliminarEmpleado() {
        String codigo = empleadoGUI.obtenerCodigoEmpleado();
        boolean eliminado = gestionEmpleado.eliminarEmpleado(codigo);

        if (eliminado) {
            empleadoGUI.mostrarMensaje("Empleado eliminado correctamente");
            empleadoGUI.cargarTabla(gestionEmpleado.listarEmpleados());
            empleadoGUI.limpiarFormulario();
        } else {
            empleadoGUI.mostrarMensaje("No se encontró el empleado");
        }
    }

    private void buscarEmpleado() {
        String codigo = empleadoGUI.obtenerCodigoEmpleado();
        Empleado empleado = gestionEmpleado.buscarEmpleado(codigo);

        if (empleado != null) {
            empleadoGUI.cargarEmpleadoEnFormulario(empleado);
        } else {
            empleadoGUI.mostrarMensaje("Empleado no encontrado");
        }
    }

    private void registrarNomina() {
        try {
            Empleado empleado = gestionEmpleado.buscarEmpleado(nominaGUI.getCodigoEmpleado());
            if (empleado == null) {
                nominaGUI.mostrarMensaje("Empleado no encontrado");
                return;
            }

            Nomina nomina = new Nomina();
            nomina.setCodigoNomina(nominaGUI.getCodigoNomina());
            nomina.setEmpleado(empleado);
            nomina.setDevengado(nominaGUI.getDevengado());
            nomina.setDescuentos(nominaGUI.getDescuentos());

            boolean registrada = gestionNomina.registrarNomina(nomina);
            if (registrada) {
                nominaGUI.mostrarMensaje("Nómina registrada correctamente");
                nominaGUI.cargarTabla(gestionNomina.listarNominas());
                nominaGUI.limpiarFormulario();
            } else {
                nominaGUI.mostrarMensaje("Ya existe una nómina con ese código");
            }
        } catch (NumberFormatException e) {
            nominaGUI.mostrarMensaje("Verifique los valores de nómina");
        }
    }

    private void buscarNomina() {
        Nomina nomina = null;
        for (Nomina n : gestionNomina.listarNominas()) {
            if (n.getCodigoNomina().equalsIgnoreCase(nominaGUI.getCodigoNomina())) {
                nomina = n;
                break;
            } 
        }

        if (nomina != null) {
            nominaGUI.mostrarMensaje("Nómina encontrada. Total a pagar: " + nomina.calcularTotal());
        } else {
            nominaGUI.mostrarMensaje("Nómina no encontrada");
        }
    }
}