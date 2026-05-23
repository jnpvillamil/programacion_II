package co.edu.uptc.gui;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import co.edu.uptc.negocio.*;
import co.edu.uptc.negocio.dto.*;

public class Eventos implements ActionListener {

    // Panel Clientes
    public final static String CLIENTES   = "CLIENTES";
    public final static String cREGISTRAR = "Registrar";
    public final static String cMODIFICAR = "Modificar";
    public final static String cINACTIVAR = "Inactivar";
    public final static String cBUSCAR    = "Buscar";
    public final static String cLIMPIAR   = "cLimpiar";

    // Panel Productos
    public final static String PRODUCTOS  = "PRODUCTOS";
    public final static String pREGISTRAR = "pRegistrar";
    public final static String pMODIFICAR = "pModificar";
    public final static String pINACTIVAR = "pInactivar";
    public final static String pBUSCAR    = "pBuscar";
    public final static String pLIMPIAR   = "pLimpiar";

    // Panel Proveedor
    public final static String PROVEEDORES = "PROVEEDORES";
    public final static String oREGISTRAR  = "oRegistrar";
    public final static String oMODIFICAR  = "oModificar";
    public final static String oINACTIVAR  = "oInactivar";
    public final static String oBUSCAR     = "oBuscar";
    public final static String oLIMPIAR    = "oLimpiar";

    // Panel Ventas
    public final static String VENTAS     = "VENTAS";
    public final static String vREGISTRAR = "vRegistrar";
    public final static String vANULAR    = "vAnular";
    public final static String vBUSCAR    = "vBuscar";

    // Panel Compras
    public final static String COMPRAS      = "COMPRAS";
    public final static String cmREGISTRAR  = "cmpRegistrar";
    public final static String cmANULAR     = "cmpAnular";
    public final static String cmBUSCAR     = "cmpBuscar";
    public final static String cmLIMPIAR    = "cmpLimpiar";

    // General
    public final static String SALIR  = "SALIR";
    public final static String VOLVER = "Volver";

    private ventanaPrincipal vPrincipal;

    private gestionClientes  gClientes;
    private gestionProductos gProductos;
    private gestionProveedor gProveedor;
    private gestionVentas    gVentas;
    private gestionCompras   gCompras;

    private panelClientes  clientesActivo;
    private panelProductos productosActivo;
    private panelProveedor proveedorActivo;
    private panelVentas    ventasActivo;
    private panelCompras   comprasActivo;

    public Eventos() {
        this.gClientes  = new gestionClientes();
        this.gProductos = new gestionProductos();
        this.gProveedor = new gestionProveedor();
        this.gVentas    = new gestionVentas();
        this.gCompras   = new gestionCompras();
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case CLIENTES:    abrirClientes();      break;
            case cREGISTRAR:  registrarCliente();   break;
            case cMODIFICAR:  modificarCliente();   break;
            case cINACTIVAR:  inactivarCliente();   break;
            case cBUSCAR:     buscarCliente();       break;
            case cLIMPIAR:    limpiarCliente();      break;
            case PRODUCTOS:   abrirProductos();      break;
            case pREGISTRAR:  registrarProducto();   break;
            case pMODIFICAR:  modificarProducto();   break;
            case pINACTIVAR:  inactivarProducto();   break;
            case pBUSCAR:     buscarProducto();      break;
            case pLIMPIAR:    limpiarProducto();     break;
            case PROVEEDORES: abrirProveedores();    break;
            case oREGISTRAR:  registrarProveedor();  break;
            case oMODIFICAR:  modificarProveedor();  break;
            case oINACTIVAR:  inactivarProveedor();  break;
            case oBUSCAR:     buscarProveedor();     break;
            case oLIMPIAR:    limpiarProveedor();    break;
            case VENTAS:      abrirVentas();         break;
            case vREGISTRAR:  registrarVenta();      break;
            case vANULAR:     anularVenta();         break;
            case vBUSCAR:     buscarVenta();         break;
            case COMPRAS:     abrirCompras();        break;
            case cmREGISTRAR: registrarCompra();     break;
            case cmANULAR:    anularCompra();        break;
            case cmBUSCAR:    buscarCompra();        break;
            case cmLIMPIAR:   limpiarCompra();       break;
            case SALIR:       abrirSalir();          break;
            case VOLVER:      volver(e);             break;
            default: System.out.println("Comando no reconocido: " + cmd);
        }
    }

    private void volver(ActionEvent e) {
        JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor((java.awt.Component) e.getSource());
        if (ventanaActual != null) ventanaActual.dispose();
        if (vPrincipal != null) vPrincipal.setVisible(true);
    }

    //  Clientes 
    private void registrarCliente() {
        try {
            clienteDto c = clientesActivo.getDatosCliente();
            if (c == null) return;
            gClientes.registrar(c);
            clientesActivo.poblarTabla(gClientes.listar());
            clientesActivo.limpiarCampos();
            JOptionPane.showMessageDialog(vPrincipal, "Cliente registrado. Codigo: " + c.getCodigoCliente());
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void modificarCliente() {
        try {
            clienteDto c = clientesActivo.getDatosClienteModificar();
            if (c == null) return;
            gClientes.modificar(c);
            clientesActivo.poblarTabla(gClientes.listar());
            clientesActivo.limpiarCampos();
            JOptionPane.showMessageDialog(vPrincipal, "Cliente modificado correctamente");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void inactivarCliente() {
        try {
            int cod = clientesActivo.getCodigoCliente();
            if (cod == -1) return;
            if (JOptionPane.showConfirmDialog(vPrincipal, "¿Eliminar cliente " + cod + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                gClientes.inactivar(cod);
                clientesActivo.poblarTabla(gClientes.listar());
                clientesActivo.limpiarCampos();
                JOptionPane.showMessageDialog(vPrincipal, "Cliente eliminado");
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void buscarCliente() {
        try {
            int cod = clientesActivo.getCodigoCliente();
            if (cod == -1) return;
            clienteDto c = gClientes.buscar(cod);
            if (c != null) { List<clienteDto> r = new ArrayList<>(); r.add(c); clientesActivo.poblarTabla(r); }
            else JOptionPane.showMessageDialog(vPrincipal, "Cliente no encontrado");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void limpiarCliente() {
        clientesActivo.limpiarCampos();
        clientesActivo.poblarTabla(gClientes.listar());
    }

    //  Productos 
    private void registrarProducto() {
        try {
            productoDto p = productosActivo.getDatosProducto();
            if (p == null) return;
            gProductos.registrar(p);
            productosActivo.poblarTabla(gProductos.listar());
            productosActivo.limpiarCampos();
            JOptionPane.showMessageDialog(vPrincipal, "Producto registrado. Codigo: " + p.getCodigoProducto());
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void modificarProducto() {
        try {
            productoDto p = productosActivo.getDatosProductoModificar();
            if (p == null) return;
            gProductos.modificar(p);
            productosActivo.poblarTabla(gProductos.listar());
            productosActivo.limpiarCampos();
            JOptionPane.showMessageDialog(vPrincipal, "Producto modificado correctamente");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void inactivarProducto() {
        try {
            int cod = productosActivo.getCodigoProducto();
            if (cod == -1) return;
            if (JOptionPane.showConfirmDialog(vPrincipal, "¿Eliminar producto " + cod + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                gProductos.inactivar(cod);
                productosActivo.poblarTabla(gProductos.listar());
                productosActivo.limpiarCampos();
                JOptionPane.showMessageDialog(vPrincipal, "Producto eliminado");
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void buscarProducto() {
        try {
            int cod = productosActivo.getCodigoProducto();
            if (cod == -1) return;
            productoDto p = gProductos.buscar(cod);
            if (p != null) { List<productoDto> r = new ArrayList<>(); r.add(p); productosActivo.poblarTabla(r); }
            else JOptionPane.showMessageDialog(vPrincipal, "Producto no encontrado");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void limpiarProducto() {
        productosActivo.limpiarCampos();
        productosActivo.poblarTabla(gProductos.listar());
    }

    //  Proveedores 
    private void registrarProveedor() {
        try {
            proveedorDto p = proveedorActivo.getDatosProveedor();
            if (p == null) return;
            gProveedor.registrar(p);
            proveedorActivo.poblarTabla(gProveedor.listar());
            proveedorActivo.limpiarCampos();
            JOptionPane.showMessageDialog(vPrincipal, "Proveedor registrado. Codigo: " + p.getCodigoProveedor());
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void modificarProveedor() {
        try {
            proveedorDto p = proveedorActivo.getDatosProveedorModificar();
            if (p == null) return;
            gProveedor.modificar(p);
            proveedorActivo.poblarTabla(gProveedor.listar());
            proveedorActivo.limpiarCampos();
            JOptionPane.showMessageDialog(vPrincipal, "Proveedor modificado correctamente");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void inactivarProveedor() {
        try {
            int cod = proveedorActivo.getCodigoProveedor();
            if (cod == -1) return;
            if (JOptionPane.showConfirmDialog(vPrincipal, "¿Eliminar proveedor " + cod + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                gProveedor.inactivar(cod);
                proveedorActivo.poblarTabla(gProveedor.listar());
                proveedorActivo.limpiarCampos();
                JOptionPane.showMessageDialog(vPrincipal, "Proveedor eliminado");
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void buscarProveedor() {
        try {
            int cod = proveedorActivo.getCodigoProveedor();
            if (cod == -1) return;
            proveedorDto p = gProveedor.buscar(cod);
            if (p != null) { List<proveedorDto> r = new ArrayList<>(); r.add(p); proveedorActivo.poblarTabla(r); }
            else JOptionPane.showMessageDialog(vPrincipal, "Proveedor no encontrado");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void limpiarProveedor() {
        proveedorActivo.limpiarCampos();
        proveedorActivo.poblarTabla(gProveedor.listar());
    }

    //  Ventas 
    private void registrarVenta() {
        try {
            ventaDto v = ventasActivo.getDatosVenta();
            if (v == null) return;
            gVentas.registrar(v);
            ventasActivo.poblarTabla(gVentas.listar());
            ventasActivo.limpiarCampos();
            JOptionPane.showMessageDialog(vPrincipal, "Venta registrada. Factura N°: " + v.getNumeroFactura());
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void anularVenta() {
        try {
            String input = JOptionPane.showInputDialog(vPrincipal, "Ingrese el numero de factura a anular:");
            if (input == null || input.isBlank()) return;
            gVentas.anular(Integer.parseInt(input));
            ventasActivo.poblarTabla(gVentas.listar());
            JOptionPane.showMessageDialog(vPrincipal, "Venta anulada correctamente");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void buscarVenta() {
        try {
            String input = JOptionPane.showInputDialog(vPrincipal, "Ingrese el numero de factura:");
            if (input == null || input.isBlank()) return;
            ventaDto v = gVentas.buscar(Integer.parseInt(input));
            JOptionPane.showMessageDialog(vPrincipal, v != null ? "Venta encontrada: " + v : "Venta no encontrada");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    //  Compras 
    private void registrarCompra() {
        try {
            compraDto c = comprasActivo.getDatosCompra();
            if (c == null) return;
            gCompras.registrar(c);
            comprasActivo.poblarTabla(gCompras.listar());
            comprasActivo.limpiarCampos();
            JOptionPane.showMessageDialog(vPrincipal,
                "Compra registrada. Factura N°: " + c.getNumeroFacturaProveedor()
                + "\nInventario actualizado automaticamente."
                + "\nEgreso contable registrado.");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void anularCompra() {
        try {
            int num = comprasActivo.getNumeroFacturaSeleccionado();
            if (num == -1) return;
            if (JOptionPane.showConfirmDialog(vPrincipal,
                    "¿Anular la compra N° " + num + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                gCompras.anular(num);
                comprasActivo.poblarTabla(gCompras.listar());
                JOptionPane.showMessageDialog(vPrincipal, "Compra anulada correctamente");
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void buscarCompra() {
        try {
            String input = JOptionPane.showInputDialog(vPrincipal, "Ingrese el numero de factura de compra:");
            if (input == null || input.isBlank()) return;
            compraDto c = gCompras.buscar(Integer.parseInt(input));
            JOptionPane.showMessageDialog(vPrincipal,
                    c != null ? "Compra encontrada:\n" + c : "Compra no encontrada");
        } catch (Exception e) { JOptionPane.showMessageDialog(vPrincipal, e.getMessage()); }
    }

    private void limpiarCompra() {
        comprasActivo.limpiarCampos();
        comprasActivo.poblarTabla(gCompras.listar());
    }

    //  Abrir paneles 
    public void abrirClientes() {
        if (vPrincipal != null) vPrincipal.setVisible(false);
        clientesActivo = new panelClientes(this);
        clientesActivo.poblarTabla(gClientes.listar());
        abrirVentana("Clientes", clientesActivo);
    }

    public void abrirProductos() {
        if (vPrincipal != null) vPrincipal.setVisible(false);
        productosActivo = new panelProductos(this);
        productosActivo.poblarTabla(gProductos.listar());
        abrirVentana("Productos", productosActivo);
    }

    public void abrirProveedores() {
        if (vPrincipal != null) vPrincipal.setVisible(false);
        proveedorActivo = new panelProveedor(this);
        proveedorActivo.poblarTabla(gProveedor.listar());
        abrirVentana("Proveedores", proveedorActivo);
    }

    public void abrirVentas() {
        if (vPrincipal != null) vPrincipal.setVisible(false);
        ventasActivo = new panelVentas(this);
        ventasActivo.poblarTabla(gVentas.listar());
        abrirVentana("Ventas", ventasActivo);
    }

    public void abrirCompras() {
        if (vPrincipal != null) vPrincipal.setVisible(false);
        comprasActivo = new panelCompras(this);
        comprasActivo.poblarTabla(gCompras.listar());
        abrirVentana("Compras a Proveedor", comprasActivo);
    }

    private void abrirVentana(String titulo, JPanel panel) {
        JFrame ventana = new JFrame(titulo);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.add(panel);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }

    public void abrirSalir() {
        if (JOptionPane.showConfirmDialog(vPrincipal, "¿Quieres salir del sistema?",
                "Salir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    public void setVentanaPrincipal(ventanaPrincipal vp) { this.vPrincipal = vp; }
}