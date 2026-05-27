package co.edu.uptc.Tiendaminorista.Gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import co.edu.uptc.Tiendaminorista.Administrador.gui.PanelInicial;
import co.edu.uptc.Tiendaminorista.Enum.TipoDcoumenEnum;
import co.edu.uptc.Tiendaminorista.modelo.Cliente;
import co.edu.uptc.Tiendaminorista.modelo.Proveedor;
import co.edu.uptc.Tiendaminorista.negocio.GestionCliente;
import co.edu.uptc.Tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.Tiendaminorista.negocio.GestionProveedor;
import co.edu.uptc.Tiendaminorista.negocio.SistemaSeguridad;
import co.edu.uptc.Tiendaminorista.persistencia.LocalCliente;
import co.edu.uptc.Tiendaminorista.persistencia.LocalProducto;
import co.edu.uptc.Tiendaminorista.persistencia.LocalProveedor;

public class PanelPrincipal extends JFrame {

    private Evento evento;

    private PanelLogin panelLogin;
    private PanelInicial panelInicial;

    private GestionCliente gestionCliente;
    private GestionProveedor gestionProveedor;
    private GestionProducto gestionProducto;

    private SistemaSeguridad seguridad;

    public PanelPrincipal() {

        seguridad = new SistemaSeguridad();

        gestionCliente = new GestionCliente(new LocalCliente());
        gestionProveedor = new GestionProveedor(new LocalProveedor());
        gestionProducto = new GestionProducto(new LocalProducto());

        setTitle("Sistema Tienda Minorista");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        evento = new Evento(this);

        iniciarPaneles();

        add(panelLogin, BorderLayout.CENTER);
    }

    private void iniciarPaneles() {

        panelLogin = new PanelLogin(evento);

        panelInicial = new PanelInicial(evento, gestionProducto, gestionCliente, gestionProveedor);
    }

    public void cambiarPanel(JPanel panel) {

        getContentPane().removeAll();

        add(panel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    public void loguear() {

        try {

            if (seguridad.validadinicio(panelLogin.getCredencialusuario())) {

                cambiarPanel(panelInicial);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Credenciales incorrectas"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
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
            cliente.setTipodoc(TipoDcoumenEnum.valueOf(panelInicial.getPanelRegistroCliente().getTipoDoc()));
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
            cliente.setTipodoc(TipoDcoumenEnum.valueOf(panelInicial.getPanelActualizarCliente().getTipoDoc()));
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
}
