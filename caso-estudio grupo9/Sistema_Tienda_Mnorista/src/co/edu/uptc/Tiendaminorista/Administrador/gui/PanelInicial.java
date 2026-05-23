package co.edu.uptc.Tiendaminorista.Administrador.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.List;
import javax.swing.*;

import co.edu.uptc.Tiendaminorista.Gui.Evento;
import co.edu.uptc.Tiendaminorista.modelo.Cliente;
import co.edu.uptc.Tiendaminorista.modelo.Proveedor;
import co.edu.uptc.Tiendaminorista.negocio.GestionCliente;
import co.edu.uptc.Tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.Tiendaminorista.negocio.GestionProveedor;

public class PanelInicial extends JPanel {

    private JTabbedPane pestanas;
    private JPanel clienteCards;
    private CardLayout clienteLayout;
    private JPanel proveedorCards;
    private CardLayout proveedorLayout;

    private PanelCliente panelCliente;
    private PanelResgistroCli panelRegistroCliente;
    private PanelActualizarCliente panelActualizarCliente;
    private PanelProveedores panelProveedores;
    private PanelRegistrarProveedor panelRegistrarProveedor;
    private PanelActualizarProveedor panelActualizarProveedor;
    private PanelComprasPro panelComprasPro; // NUEVO
    private GestionCliente gestionCliente;
    private GestionProveedor gestionProveedor;
    private GestionProducto gestionProducto; // NUEVO: guardar referencia
    private PanelGestionContable panelGestionContable;

    public PanelInicial(Evento e, GestionProducto gestionProducto, GestionCliente gestionCliente, GestionProveedor gestionProveedor) {

        setLayout(new BorderLayout());
        this.gestionCliente = gestionCliente;
        this.gestionProveedor = gestionProveedor;
        this.gestionProducto = gestionProducto; // NUEVO: guardar referencia

        panelesCliente(e);
        panelesProveedores(e);

        pestanas = new JTabbedPane();
        pestanas.addTab("Cliente", clienteCards);
        pestanas.addTab("Producto", new PanelProductos(e));
        pestanas.addTab("Proveedores", proveedorCards);

        add(pestanas, BorderLayout.CENTER);
        cargarClientes(gestionCliente.listarClientes());
        cargarProveedores(gestionProveedor.listarProveedores());
        
        panelGestionContable = new PanelGestionContable();
        pestanas.addTab("Contabilidad", panelGestionContable);
    }

    private void panelesCliente(Evento e) {
        clienteLayout = new CardLayout();
        clienteCards = new JPanel(clienteLayout);

        panelCliente = new PanelCliente(e);
        panelRegistroCliente = new PanelResgistroCli(e);
        panelActualizarCliente = new PanelActualizarCliente(e);

        clienteCards.add(panelCliente, "CLIENTE_LIST");
        clienteCards.add(panelRegistroCliente, "CLIENTE_REGISTRAR");
        clienteCards.add(panelActualizarCliente, "CLIENTE_ACTUALIZAR");

        clienteLayout.show(clienteCards, "CLIENTE_LIST");
    }

    private void panelesProveedores(Evento e) {
        proveedorLayout = new CardLayout();
        proveedorCards = new JPanel(proveedorLayout);

        panelProveedores = new PanelProveedores(e);
        panelRegistrarProveedor = new PanelRegistrarProveedor(e);
        panelActualizarProveedor = new PanelActualizarProveedor(e);
        
        // NUEVO: crear PanelComprasPro con los gestores
        panelComprasPro = new PanelComprasPro(e, gestionProveedor, gestionProducto);

        proveedorCards.add(panelProveedores, "PROVEEDOR_LIST");
        proveedorCards.add(panelRegistrarProveedor, "PROVEEDOR_REGISTRAR");
        proveedorCards.add(panelActualizarProveedor, "PROVEEDOR_ACTUALIZAR");
        proveedorCards.add(panelComprasPro, "PROVEEDOR_COMPRAS"); // NUEVO

        proveedorLayout.show(proveedorCards, "PROVEEDOR_LIST");
    }

    public void mostrarClienteLista() {
        pestanas.setSelectedIndex(0);
        cargarClientes(gestionCliente.listarClientes());
        clienteLayout.show(clienteCards, "CLIENTE_LIST");
    }

    public void mostrarRegistroCliente() {
        pestanas.setSelectedIndex(0);
        clienteLayout.show(clienteCards, "CLIENTE_REGISTRAR");
    }

    public void mostrarActualizarCliente() {
        pestanas.setSelectedIndex(0);
        cargarClientes(gestionCliente.listarClientes());
        clienteLayout.show(clienteCards, "CLIENTE_ACTUALIZAR");
    }

    public void mostrarProveedorLista() {
        pestanas.setSelectedIndex(2);
        cargarProveedores(gestionProveedor.listarProveedores());
        proveedorLayout.show(proveedorCards, "PROVEEDOR_LIST");
    }

    public void mostrarRegistrarProveedor() {
        pestanas.setSelectedIndex(2);
        proveedorLayout.show(proveedorCards, "PROVEEDOR_REGISTRAR");
    }

    public void mostrarActualizarProveedor() {
        pestanas.setSelectedIndex(2);
        cargarProveedores(gestionProveedor.listarProveedores());
        proveedorLayout.show(proveedorCards, "PROVEEDOR_ACTUALIZAR");
    }
    
    // NUEVO: método para mostrar compras
    public void mostrarComprasProveedor() {
        pestanas.setSelectedIndex(2);
        proveedorLayout.show(proveedorCards, "PROVEEDOR_COMPRAS");
    }

    public void mostrarListaActual() {
        int indice = pestanas.getSelectedIndex();
        if (indice == 0) {
            mostrarClienteLista();
        } else if (indice == 2) {
            mostrarProveedorLista();
        }
    }

    public PanelCliente getPanelCliente() {
        return panelCliente;
    }

    public PanelResgistroCli getPanelRegistroCliente() {
        return panelRegistroCliente;
    }

    public PanelActualizarCliente getPanelActualizarCliente() {
        return panelActualizarCliente;
    }

    public PanelProveedores getPanelProveedores() {
        return panelProveedores;
    }

    public PanelRegistrarProveedor getPanelRegistrarProveedor() {
        return panelRegistrarProveedor;
    }

    public PanelActualizarProveedor getPanelActualizarProveedor() {
        return panelActualizarProveedor;
    }
    
    public PanelComprasPro getPanelComprasPro() { // NUEVO
        return panelComprasPro;
    }

    public PanelGestionContable getPanelGestionContable() {
        return panelGestionContable;
    }
    
    public void cargarClientes(List<Cliente> clientes) {
        panelCliente.cargarClientes(clientes);
        panelActualizarCliente.setClientes(clientes);
    }

    public void cargarProveedores(List<Proveedor> proveedores) {
        panelProveedores.cargarProveedores(proveedores);
        panelActualizarProveedor.setProveedores(proveedores);
    }
}