package co.edu.uptc.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import co.edu.uptc.dto.CredencialDto;
import co.edu.uptc.gui.ClienteGUI;
import co.edu.uptc.gui.LoginGUI;
import co.edu.uptc.gui.MenuPrincipalGUI;
import co.edu.uptc.gui.ProductoGUI;  
import co.edu.uptc.gui.modelo.Cliente;
import co.edu.uptc.gui.modelo.Usuario;
import co.edu.uptc.gui.negocio.GestionCliente;
import co.edu.uptc.gui.negocio.GestionSeguridad;

public class Evento implements ActionListener {

    private LoginGUI loginGUI;
    private MenuPrincipalGUI menuPrincipalGUI;
    private ClienteGUI clienteGUI;
    private ProductoGUI productoGUI; 

    private GestionSeguridad gestionSeguridad;
    private GestionCliente gestionCliente;

    private Usuario usuarioSesion;

    public Evento() {
        gestionSeguridad = new GestionSeguridad();
        gestionCliente = new GestionCliente();
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
        menuPrincipalGUI.getBtnCerrarSesion().addActionListener(this);
        menuPrincipalGUI.getBtnRegistrarProducto().addActionListener(this);  
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

            // Aquí agregamos el caso para abrir la ventana de productos
            case "REGISTRAR_PRODUCTO":
                abrirRegistrarProducto();
                break;

            default:
                JOptionPane.showMessageDialog(null, "Acción no implementada: " + comando);
                break;
        }
    }

    private void iniciarSesion() {
        String usuario = loginGUI.getTxtUsuario().getText().trim();
        String contrasena = new String(loginGUI.getTxtContrasena().getPassword()); //porque nuevo string

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

    private void buscarCliente() {
        String codigo = clienteGUI.obtenerCodigoCliente();
        Cliente cliente = gestionCliente.buscarCliente(codigo);

        if (cliente != null) {
            clienteGUI.cargarClienteEnFormulario(cliente);
        } else {
            clienteGUI.mostrarMensaje("Cliente no encontrado");
        }
    }

    // Método para abrir la ventana de "Registrar Producto"
    private void abrirRegistrarProducto() {
        // Crear y mostrar la ventana de ProductoGUI
        productoGUI = new ProductoGUI();
        productoGUI.setVisible(true);
    }

}