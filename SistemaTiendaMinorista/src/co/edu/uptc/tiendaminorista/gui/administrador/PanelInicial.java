package co.edu.uptc.tiendaminorista.gui.administrador;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.List;
import javax.swing.*;

import co.edu.uptc.tiendaminorista.gui.Evento;
import co.edu.uptc.tiendaminorista.modelo.Cliente;
import co.edu.uptc.tiendaminorista.modelo.Proveedor;
import co.edu.uptc.tiendaminorista.negocio.GestionCliente;
import co.edu.uptc.tiendaminorista.negocio.GestionProducto;
import co.edu.uptc.tiendaminorista.negocio.GestionProveedor;

public class PanelInicial extends JPanel {

    private JTabbedPane pestanas;
    private JPanel clienteCards;
    private CardLayout clienteLayout;
    private JPanel proveedorCards;
    private CardLayout proveedorLayout;
    private PanelHistorialCliente panelHistorialCliente;
    private PanelCompraCliente compracliente;
    private PanelCliente panelCliente;
    private PanelRegistrosEmpleados Empleados; 
    private PanelResgistroCli panelRegistroCliente;
    private PanelActualizarCliente panelActualizarCliente;
    private PanelProveedores panelProveedores;
    private PanelRegistrarProveedor panelRegistrarProveedor;
    private PanelActualizarProveedor panelActualizarProveedor;
    private PanelComprasPro panelComprasPro;
    private GestionCliente gestionCliente;
    private GestionProveedor gestionProveedor;
    private GestionProducto gestionProducto; 
    private PanelGestionContable panelGestionContable;
    private PanelReportes panelReportes;

    public PanelInicial(Evento e, GestionProducto gestionProducto, GestionCliente gestionCliente, GestionProveedor gestionProveedor, PanelRegistrosEmpleados Empleados) {

        setLayout(new BorderLayout());
        this.gestionCliente = gestionCliente;
        this.gestionProveedor = gestionProveedor;
        this.gestionProducto = gestionProducto; 
        this.Empleados = Empleados; 
        this.panelHistorialCliente = new PanelHistorialCliente(e);

        panelesCliente(e);
        panelesProveedores(e);

        pestanas = new JTabbedPane();
        pestanas.addTab("Cliente", clienteCards);
        pestanas.addTab("Producto", new PanelProductos(e));
        pestanas.addTab("Proveedores", proveedorCards);
        pestanas.addTab("Empleados", this.Empleados); 

        panelGestionContable = new PanelGestionContable();
        pestanas.addTab("Contabilidad", panelGestionContable);

        panelReportes = new PanelReportes();
        pestanas.addTab("Reportes", panelReportes);

        add(pestanas, BorderLayout.CENTER);
    }

    private void panelesCliente(Evento e) {
        clienteLayout = new CardLayout();
        clienteCards = new JPanel(clienteLayout);

        panelCliente = new PanelCliente(e);
        panelRegistroCliente = new PanelResgistroCli(e);
        panelActualizarCliente = new PanelActualizarCliente(e);
        this.compracliente = new PanelCompraCliente(e);
        clienteCards.add(panelCliente, "CLIENTE_LIST");
        clienteCards.add(panelRegistroCliente, "CLIENTE_REGISTRAR");
        clienteCards.add(panelActualizarCliente, "CLIENTE_ACTUALIZAR");
        clienteCards.add(this.compracliente, "CLIENTE_COMPRA");
        clienteLayout.show(clienteCards, "CLIENTE_LIST");
        clienteCards.add(this.panelHistorialCliente, "CLIENTE_HISTORIAL");
    }

    private void panelesProveedores(Evento e) {
        proveedorLayout = new CardLayout();
        proveedorCards = new JPanel(proveedorLayout);

        panelProveedores = new PanelProveedores(e);
        panelRegistrarProveedor = new PanelRegistrarProveedor(e);
        panelActualizarProveedor = new PanelActualizarProveedor(e);
        
        panelComprasPro = new PanelComprasPro(e, gestionProveedor, gestionProducto);

        proveedorCards.add(panelProveedores, "PROVEEDOR_LIST");
        proveedorCards.add(panelRegistrarProveedor, "PROVEEDOR_REGISTRAR");
        proveedorCards.add(panelActualizarProveedor, "PROVEEDOR_ACTUALIZAR");
        proveedorCards.add(panelComprasPro, "PROVEEDOR_COMPRAS"); 

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
    
    public void mostrarComprasProveedor() {
        pestanas.setSelectedIndex(2);
        proveedorLayout.show(proveedorCards, "PROVEEDOR_COMPRAS");
    }


    public PanelRegistrosEmpleados getPanelRegistrosEmpleados() {
        return Empleados;
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
    
    public PanelComprasPro getPanelComprasPro() {
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
    public PanelHistorialCliente getPanelHistorialCliente() {
        return panelHistorialCliente;
    }

    public void mostrarPantallaHistorial() {
        pestanas.setSelectedIndex(0);
        clienteLayout.show(clienteCards, "CLIENTE_HISTORIAL");
        if (panelHistorialCliente != null) {
            panelHistorialCliente.cargarClientesEnCombo(gestionCliente.listarClientes());
            panelHistorialCliente.actualizarTabla(null); 
        }
    }
    
    public void mostrarClienteLista1() {
        pestanas.setSelectedIndex(0);
        clienteLayout.show(clienteCards, "CLIENTE_LIST");
    }

    public void mostrarCompraCliente() {
        pestanas.setSelectedIndex(0); 
        
        clienteLayout.show(clienteCards, "CLIENTE_COMPRA"); 
        
        if (this.compracliente != null) {
            this.compracliente.cargarClientesEnCombo(this.gestionCliente.listarClientes());
            this.compracliente.cargarProductosEnCombo(this.gestionProducto.listarProductos());
            this.compracliente.actualizarTablaCompras(this.gestionCliente.listarTodasLasCompras());
        }
    }

    public PanelCompraCliente getPanelCompraCliente() {
        return this.compracliente;
    }
}