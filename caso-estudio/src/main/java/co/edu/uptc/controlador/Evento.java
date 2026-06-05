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
import co.edu.uptc.gui.interfaces.IGestionDeCliente;
import co.edu.uptc.gui.interfaces.IGestionDeSeguridad;
import co.edu.uptc.config.Config; // Central de configuración compartida

public class Evento implements ActionListener {

    private LoginGUI loginGUI;
    private MenuPrincipalGUI menuPrincipalGUI;
    private ClienteGUI clienteGUI;
    private ProductoGUI productoGUI; 

    // Atributos definidos usando sus respectivas INTERFACES
    private IGestionDeSeguridad gestionSeguridad;
    private IGestionDeCliente gestionCliente;

    private Usuario usuarioSesion;
    
    // Atributo para recordar la configuración y pasarla a las ventanas hijas
    private Config config;

    // Guarda la referencia de config para reutilizarla
    public Evento() {
        this.config = config; //  Guardamos la configuración global
        this.gestionSeguridad = config.getGestionDeSeguridad();
        this.gestionCliente = config.getGestionCliente();
    }

    public Evento(Object object) {
		// TODO Auto-generated constructor stub
	}

	public void iniciar() {
        loginGUI = new LoginGUI();
        asignarEventosLogin();
        loginGUI.setVisible(true);
    }

    private void asignarEventosLogin() {
        loginGUI.getBtnIngresar().addActionListener(this);
        loginGUI.getBtnIngresar().addActionListener(this);
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
        
        clienteGUI.cargarTabla(gestionCliente.obtenerListaClientes());
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
                System.exit(0); 
                break;
            case "CERRAR_SESION":
                menuPrincipalGUI.dispose();
                usuarioSesion = null; 
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
        String contrasena = new String(loginGUI.getTxtContrasena().getPassword()); 

        CredencialDto credencialDto = new CredencialDto(usuario, contrasena);
        boolean accesoValido = gestionSeguridad.validarAcceso(credencialDto);

        if (accesoValido) {
            usuarioSesion = new Usuario(); 
            usuarioSesion.setNombreUsuario(usuario);
            
            loginGUI.mostrarMensaje("Bienvenido " + usuarioSesion.getNombreUsuario());
            loginGUI.dispose();
            abrirMenuPrincipal();
        } else {
            loginGUI.mostrarMensaje("Usuario o contraseña incorrectos");
        }
    }

    private void registrarCliente() {
        Cliente cliente = clienteGUI.obtenerClienteFormulario();
        try {
            gestionCliente.guardarCliente(cliente);
            clienteGUI.mostrarMensaje("Cliente registrado correctamente");
            clienteGUI.cargarTabla(gestionCliente.obtenerListaClientes());
            clienteGUI.limpiarFormulario();
        } catch (IllegalArgumentException ex) {
            clienteGUI.mostrarMensaje(ex.getMessage());
        }
    }

    private void modificarCliente() {
        Cliente cliente = clienteGUI.obtenerClienteFormulario();
        try {
            gestionCliente.actualizarCliente(cliente);
            clienteGUI.mostrarMensaje("Cliente modificado correctamente");
            clienteGUI.cargarTabla(gestionCliente.obtenerListaClientes());
            clienteGUI.limpiarFormulario();
        } catch (IllegalArgumentException ex) {
            clienteGUI.mostrarMensaje(ex.getMessage());
        }
    }

    private void eliminarCliente() {
        String codigo = clienteGUI.obtenerCodigoCliente();
        try {
            gestionCliente.inactivarCliente(codigo);
            clienteGUI.mostrarMensaje("Cliente inactivado correctamente");
            clienteGUI.cargarTabla(gestionCliente.obtenerListaClientes());
            clienteGUI.limpiarFormulario();
        } catch (IllegalArgumentException ex) {
            clienteGUI.mostrarMensaje(ex.getMessage());
        }
    }

    private void buscarCliente() {
        String codigo = clienteGUI.obtenerCodigoCliente();
        Cliente cliente = gestionCliente.buscarClientePorCodigo(codigo);

        if (cliente != null) {
            clienteGUI.cargarClienteEnFormulario(cliente);
        } else {
            clienteGUI.mostrarMensaje("Cliente no encontrado");
        }
    }

    private void abrirRegistrarProducto() {
        // CORREGIDO: Se pasa 'this.config' en vez de 'null' para que ProductoGUI tenga inyección
        productoGUI = new ProductoGUI();
        productoGUI.setVisible(true);
    }
}