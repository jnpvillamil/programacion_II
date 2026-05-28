package co.edu.uptc.tiendaminorista.gui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import co.edu.uptc.tiendaminorista.gui.administrador.PanelInicial;
import co.edu.uptc.tiendaminorista.gui.administrador.PanelRegistrosEmpleados;
import co.edu.uptc.tiendaminorista.enums.TipoDocumentoEnum;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.Empleado;
import co.edu.uptc.tiendaminorista.modelo.Proveedor;
import co.edu.uptc.tiendaminorista.negocio.GestionCliente;
import co.edu.uptc.tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.tiendaminorista.negocio.GestionProveedor;
import co.edu.uptc.tiendaminorista.negocio.SistemaSeguridad;
import co.edu.uptc.tiendaminorista.persistencia.LocalCliente;
import co.edu.uptc.tiendaminorista.persistencia.LocalProducto;
import co.edu.uptc.tiendaminorista.persistencia.LocalProveedor;
import co.edu.uptc.tiendaminorista.negocio.GestionEmpleado;
import co.edu.uptc.tiendaminorista.persistencia.LocalEmpleado;

public class PanelPrincipal extends JFrame {

    private Evento evento;

    private PanelLogin panelLogin;
    private PanelInicial panelInicial;
    private GestionEmpleado gestionEmpleado;
    private GestionCliente gestionCliente;
    private GestionProveedor gestionProveedor;
    private GestionProducto gestionProducto;
    private PanelRegistrosEmpleados empleados; 

    private SistemaSeguridad seguridad;

    public PanelPrincipal() {

        seguridad = new SistemaSeguridad();
        gestionEmpleado = new GestionEmpleado(new LocalEmpleado());
        gestionCliente = new GestionCliente(new LocalCliente());
        gestionProveedor = new GestionProveedor(new LocalProveedor());
        gestionProducto = new GestionProducto(new LocalProducto());
        
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
        try {
            if (seguridad.validarInicio(panelLogin.getCredencialusuario())) {
                cambiarPanel(panelInicial);
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
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

    public static void main(String[] args) {
        PanelPrincipal ventana = new PanelPrincipal();
        ventana.setVisible(true);
    }

    public void mostrarRegistroCliente() {
        panelInicial.mostrarRegistroCliente();
    }

    public void mostrarPanelCliente() {
        panelInicial.mostrarClienteLista();
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
        panelInicial.mostrarProveedorLista();
    }

    public void mostrarActualizarProveedor() {
        panelInicial.mostrarActualizarProveedor();
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
            JOptionPane.showMessageDialog(this, "Tipo de documento inválido");
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
            JOptionPane.showMessageDialog(this, "Tipo de documento inválido");
        }
    }

    public void desactivarCliente() {
        String codigo = panelInicial.getPanelActualizarCliente().getClienteCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para desactivar");
            return;
        }
        gestionCliente.desactivarCliente(codigo);
        JOptionPane.showMessageDialog(this, "Cliente desactivado correctamente");
        panelInicial.cargarClientes(gestionCliente.listarClientes());
        panelInicial.getPanelActualizarCliente().refreshClienteSeleccionado();
    }

    public void activarCliente() {
        String codigo = panelInicial.getPanelActualizarCliente().getClienteCodigoSeleccionado();
        if (codigo == null || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para activar");
            return;
        }
        gestionCliente.activarCliente(codigo);
        JOptionPane.showMessageDialog(this, "Cliente activado correctamente");
        panelInicial.cargarClientes(gestionCliente.listarClientes());
        panelInicial.getPanelActualizarCliente().refreshClienteSeleccionado();
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

        if (correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.");
            return;
        }

        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setCorreo(correo);
        nuevoEmpleado.setPassword(password);

        gestionEmpleado.guardar(nuevoEmpleado);
        JOptionPane.showMessageDialog(this, "Empleado registrado de forma correcta.");
        
        panelEmp.cargarEmpleados(gestionEmpleado.listarEmpleados());
        panelEmp.limpiarCampos();
    }

    public void actualizarEmpleado() {
        PanelRegistrosEmpleados panelEmp = panelInicial.getPanelRegistrosEmpleados();
        String correo = panelEmp.getCorreo();
        String password = panelEmp.getContraseña();

        if (correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos para poder actualizar.");
            return;
        }

        Empleado empleadoModificado = new Empleado();
        empleadoModificado.setCorreo(correo);
        empleadoModificado.setPassword(password);

        gestionEmpleado.actualizar(empleadoModificado);
        JOptionPane.showMessageDialog(this, "Empleado modificado con éxito.");
        
        panelEmp.cargarEmpleados(gestionEmpleado.listarEmpleados());
        panelEmp.limpiarCampos();
    }

    // 📍 LÓGICA SIMPLIFICADA Y FUNCIONAL PARA ELIMINAR EMPLEADO
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
}