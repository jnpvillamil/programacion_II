package co.edu.uptc.gui;

import java.awt.event.*;
import javax.swing.*;
import co.edu.uptc.negocio.*;

public class Eventos implements ActionListener {

    // Login 
    public final static String lCANCELAR        = "Cancelar";
    public final static String lINICIAR_SESION  = "Iniciar Sesion";

    // Panel Clientes
    public final static String CLIENTES         = "CLIENTES";
    public final static String cREGISTRAR       = "Registrar";
    public final static String cMODIFICAR       = "Modificar";
    public final static String cINACTIVAR       = "Inactivar";
    public final static String cBUSCAR          = "Buscar";

    // Panel Productos
    public final static String PRODUCTOS        = "PRODUCTOS";
    public final static String pREGISTRAR       = "pRegistrar";
    public final static String pMODIFICAR       = "pModificar";
    public final static String pINACTIVAR       = "pInactivar";
    public final static String pBUSCAR          = "pBuscar";

    // Panel Proveedor
    public final static String PROVEEDORES      = "PROVEEDORES";
    public final static String oREGISTRAR       = "oRegistrar";
    public final static String oMODIFICAR       = "oModificar";
    public final static String oINACTIVAR       = "oInactivar";
    public final static String oBUSCAR          = "oBuscar";

    // Panel Ventas
    public final static String VENTAS           = "VENTAS";
    public final static String vREGISTRAR       = "vRegistrar";
    public final static String vANULAR          = "vAnular";
    public final static String vBUSCAR          = "vBuscar";

    // Panel Compras (NUEVO)
    public final static String COMPRAS          = "COMPRAS";
    public final static String cREGISTRAR_COMPRA = "cRegistrarCompra";
    public final static String cBUSCAR_COMPRA    = "cBuscarCompra";

    // Ventana Principal
    public final static String REPORTES         = "REPORTES";
    public final static String SALIR            = "SALIR";
    public final static String ANULAR           = "Anular";

    // Volver
    public final static String VOLVER           = "Volver";

    private ventanaPrincipal vPrincipal;

    // Negocio
    private gestionSeguridad  seguridad;
    private gestionClientes   gClientes;
    private gestionProductos  gProductos;
    private gestionProveedor  gProveedor;
    private gestionVentas     gVentas;
    private gestionCompras    gCompras;    

    // Paneles activos
    private panelLogin     loginActivo;
    private panelClientes  clientesActivo;
    private panelProductos productosActivo;
    private panelProveedor proveedorActivo;
    private panelVentas    ventasActivo;
    private panelCompras   comprasActivo;  

    public Eventos(ventanaPrincipal vPrincipal) {
        this.vPrincipal = vPrincipal;
        this.seguridad  = new gestionSeguridad();
        this.gClientes  = new gestionClientes();
        this.gProductos = new gestionProductos();
        this.gProveedor = new gestionProveedor();
        this.gVentas    = new gestionVentas();
        this.gCompras   = new gestionCompras();   
    }

    public Eventos() {
        this.vPrincipal = null;
        this.seguridad  = new gestionSeguridad();
        this.gClientes  = new gestionClientes();
        this.gProductos = new gestionProductos();
        this.gProveedor = new gestionProveedor();
        this.gVentas    = new gestionVentas();
        this.gCompras   = new gestionCompras();   
    }

    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {

            // inicio de sesion
            case lCANCELAR:          cancelar();              break;
            case lINICIAR_SESION:    iniciarSesion();         break;

            // Panel Clientes
            case CLIENTES:           abrirClientes();         break;
            case cREGISTRAR:         registrarCliente();      break;
            case cMODIFICAR:         modificarCliente();      break;
            case cINACTIVAR:         inactivarCliente();      break;
            case cBUSCAR:            buscarCliente();         break;

            // Panel Productos
            case PRODUCTOS:          abrirProductos();        break;
            case pREGISTRAR:         registrarProducto();     break;
            case pMODIFICAR:         modificarProducto();     break;
            case pINACTIVAR:         inactivarProducto();     break;
            case pBUSCAR:            buscarProducto();        break;

            // Panel Proveedor
            case PROVEEDORES:        abrirProveedores();      break;
            case oREGISTRAR:         registrarProveedor();    break;
            case oMODIFICAR:         modificarProveedor();    break;
            case oINACTIVAR:         inactivarProveedor();    break;
            case oBUSCAR:            buscarProveedor();       break;

            // Panel Ventas
            case VENTAS:             abrirVentas();           break;
            case vREGISTRAR:         registrarVenta();        break;
            case vANULAR:            anularVenta();           break;
            case vBUSCAR:            buscarVenta();           break;

            // Panel Compras (NUEVO)
            case COMPRAS:            abrirCompras();          break;
            case cREGISTRAR_COMPRA:  registrarCompra();       break;
            case cBUSCAR_COMPRA:     buscarCompra();          break;

            // Ventana Principal
            case REPORTES:           abrirReportes();         break;
            case SALIR:              abrirSalir();            break;
            case ANULAR:             anularVenta();           break;

            // Volver
            case VOLVER:             volver(e);               break;

            default:
                System.out.println("Comando no reconocido: " + comando);
                break;
        }
    }

    // Volver
    private void volver(ActionEvent e) {
        java.awt.Component origen = (java.awt.Component) e.getSource();
        JFrame ventanaActual = (JFrame) SwingUtilities.getWindowAncestor(origen);
        if (ventanaActual != null) ventanaActual.dispose();
        if (vPrincipal != null) vPrincipal.setVisible(Boolean.TRUE);
    }

    // inicio sesion
    private void cancelar() {
        JOptionPane.showMessageDialog(vPrincipal, "Boton CANCELAR presionado");
    }

    private void iniciarSesion() {
        try {
            credencialDto credencial = loginActivo.getCredencialesUsuario();
            if (credencial == null) return;
            if (seguridad.validarLoguear(credencial)) {
                JOptionPane.showMessageDialog(vPrincipal, "Sesion iniciada correctamente");
            } else {
                JOptionPane.showMessageDialog(vPrincipal, "Usuario o contrasena no valido");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    // metodos clientes
    private void registrarCliente() {
        try {
            clienteDto cliente = clientesActivo.getDatosCliente();
            if (cliente == null) return;
            gClientes.registrar(cliente);
            JOptionPane.showMessageDialog(vPrincipal, "Cliente registrado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void modificarCliente() {
        try {
            clienteDto cliente = clientesActivo.getDatosCliente();
            if (cliente == null) return;
            gClientes.modificar(cliente);
            JOptionPane.showMessageDialog(vPrincipal, "Cliente modificado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void inactivarCliente() {
        try {
            String codigo = clientesActivo.getCodigoCliente();
            if (codigo == null) return;
            gClientes.inactivar(codigo);
            JOptionPane.showMessageDialog(vPrincipal, "Cliente inactivado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void buscarCliente() {
        try {
            String codigo = clientesActivo.getCodigoCliente();
            if (codigo == null) return;
            clienteDto cliente = gClientes.buscar(codigo);
            if (cliente != null) {
                JOptionPane.showMessageDialog(vPrincipal, "Cliente encontrado: " + cliente);
            } else {
                JOptionPane.showMessageDialog(vPrincipal, "Cliente no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    // metodos Productos
    private void registrarProducto() {
        try {
            productoDto producto = productosActivo.getDatosProducto();
            if (producto == null) return;
            gProductos.registrar(producto);
            JOptionPane.showMessageDialog(vPrincipal, "Producto registrado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void modificarProducto() {
        try {
            productoDto producto = productosActivo.getDatosProducto();
            if (producto == null) return;
            gProductos.modificar(producto);
            JOptionPane.showMessageDialog(vPrincipal, "Producto modificado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void inactivarProducto() {
        try {
            String codigo = productosActivo.getCodigoProducto();
            if (codigo == null) return;
            gProductos.inactivar(codigo);
            JOptionPane.showMessageDialog(vPrincipal, "Producto inactivado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void buscarProducto() {
        try {
            String codigo = productosActivo.getCodigoProducto();
            if (codigo == null) return;
            productoDto producto = gProductos.buscar(codigo);
            if (producto != null) {
                JOptionPane.showMessageDialog(vPrincipal, "Producto encontrado: " + producto);
            } else {
                JOptionPane.showMessageDialog(vPrincipal, "Producto no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    // metodos Proveedores
    private void registrarProveedor() {
        try {
            proveedorDto proveedor = proveedorActivo.getDatosProveedor();
            if (proveedor == null) return;
            gProveedor.registrar(proveedor);
            JOptionPane.showMessageDialog(vPrincipal, "Proveedor registrado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void modificarProveedor() {
        try {
            proveedorDto proveedor = proveedorActivo.getDatosProveedor();
            if (proveedor == null) return;
            gProveedor.modificar(proveedor);
            JOptionPane.showMessageDialog(vPrincipal, "Proveedor modificado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void inactivarProveedor() {
        try {
            String codigo = proveedorActivo.getCodigoProveedor();
            if (codigo == null) return;
            gProveedor.inactivar(codigo);
            JOptionPane.showMessageDialog(vPrincipal, "Proveedor inactivado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void buscarProveedor() {
        try {
            String codigo = proveedorActivo.getCodigoProveedor();
            if (codigo == null) return;
            proveedorDto proveedor = gProveedor.buscar(codigo);
            if (proveedor != null) {
                JOptionPane.showMessageDialog(vPrincipal, "Proveedor encontrado: " + proveedor);
            } else {
                JOptionPane.showMessageDialog(vPrincipal, "Proveedor no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    // metodos Ventas
    private void registrarVenta() {
        try {
            ventaDto venta = ventasActivo.getDatosVenta();
            if (venta == null) return;
            gVentas.registrar(venta);
            JOptionPane.showMessageDialog(vPrincipal, "Venta registrada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void anularVenta() {
        try {
            String numeroFactura = ventasActivo.getNumeroFactura();
            if (numeroFactura == null) return;
            gVentas.anular(numeroFactura);
            JOptionPane.showMessageDialog(vPrincipal, "Venta anulada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void buscarVenta() {
        try {
            String numeroFactura = ventasActivo.getNumeroFactura();
            if (numeroFactura == null) return;
            ventaDto venta = gVentas.buscar(numeroFactura);
            if (venta != null) {
                JOptionPane.showMessageDialog(vPrincipal, "Venta encontrada: " + venta);
            } else {
                JOptionPane.showMessageDialog(vPrincipal, "Venta no encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    // metodos Compras 
    private void registrarCompra() {
        try {
            compraDto compra = comprasActivo.getDatosCompra();
            if (compra == null) return;
            gCompras.registrar(compra);
            JOptionPane.showMessageDialog(vPrincipal, "Compra registrada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    private void buscarCompra() {
        try {
            String numeroFactura = comprasActivo.getNumeroFacturaProveedor();
            if (numeroFactura == null) return;
            compraDto compra = gCompras.buscar(numeroFactura);
            if (compra != null) {
                JOptionPane.showMessageDialog(vPrincipal, "Compra encontrada: " + compra);
            } else {
                JOptionPane.showMessageDialog(vPrincipal, "Compra no encontrada");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vPrincipal, e.getMessage());
        }
    }

    // Abrir paneles
    public void abrirClientes() {
        if (vPrincipal != null) vPrincipal.setVisible(Boolean.FALSE);
        clientesActivo = new panelClientes(this);
        JFrame ventana = new JFrame("Clientes");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.add(clientesActivo);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(Boolean.TRUE);
    }

    public void abrirProductos() {
        if (vPrincipal != null) vPrincipal.setVisible(Boolean.FALSE);
        productosActivo = new panelProductos(this);
        JFrame ventana = new JFrame("Productos");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.add(productosActivo);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(Boolean.TRUE);
    }

    public void abrirProveedores() {
        if (vPrincipal != null) vPrincipal.setVisible(Boolean.FALSE);
        proveedorActivo = new panelProveedor(this);
        JFrame ventana = new JFrame("Proveedores");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.add(proveedorActivo);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(Boolean.TRUE);
    }

    public void abrirVentas() {
        if (vPrincipal != null) vPrincipal.setVisible(Boolean.FALSE);
        ventasActivo = new panelVentas(this);
        JFrame ventana = new JFrame("Ventas");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.add(ventasActivo);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(Boolean.TRUE);
    }

    public void abrirCompras() {
        if (vPrincipal != null) vPrincipal.setVisible(Boolean.FALSE);
        comprasActivo = new panelCompras(this);
        JFrame ventana = new JFrame("Compras");
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.add(comprasActivo);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(Boolean.TRUE);
    }

    public void abrirReportes() {
        // TODO: __implementar__ __panel__ __de__ __reportes__
        JOptionPane.showMessageDialog(vPrincipal, "Modulo de Reportes - en desarrollo");
    }

    public void abrirSalir() {
        int respuesta = JOptionPane.showConfirmDialog(
            vPrincipal, "Quieres salir del sistema?", "Salir", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}