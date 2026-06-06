package co.uptc.edu.tienda.gui;

import javax.swing.*;

import co.uptc.edu.co.tienda.configs.MascotaConfig;
import co.uptc.edu.co.tienda.configs.TiendaConfig;
import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.enums.EstadoVentaEnum;
import co.uptc.edu.tienda.modelo.Cliente;
import co.uptc.edu.tienda.modelo.Compra;
import co.uptc.edu.tienda.modelo.DetalleCompra;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Proveedor;
import co.uptc.edu.tienda.modelo.ResumenFinanciero;
import co.uptc.edu.tienda.modelo.Usuario;
import co.uptc.edu.tienda.modelo.Venta;
import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.negocio.GestionSeguridad;
import co.uptc.edu.tienda.negocio.dto.CredencialDto;
import co.uptc.edu.tienda.persistencia.LogsTxt;
import co.uptc.edu.tienda.persistencia.TxtFactura;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private PanelLogin pLogin;

    private PanelPadreProveedor pProveedor;
    private PanelPadreProducto pProducto;
    private PanelPadreCliente pCliente;
    private PanelVenta pVenta;
    private PanelHistorialVentas pHistorial;
    private PanelHistorialCompra pHistorialCompra;
    private PanelCompra pCompra;
    private PanelInventario pInventario;
    private PanelHistorialCliente pHistorialCliente;
    private PanelMovimientosContables pMovimientosContables;
    private PanelReportes pReportes;
    private List<DetalleVenta> listaDetalle = new ArrayList<>();
    private List<DetalleCompra> listaDetalleCompra = new ArrayList<>();

    private JPanel contenedor;

    private DialogoProveedor nuevoProveedor;
    private DialogoProducto nuevoProducto;
    private DialogoCliente nuevoCliente;
    private DialogoAnularVenta dialogoAnular;
    private DialogoMascota nuevoMascota;

    private Evento evento;

    private TiendaConfig tiendaConfig;
    private MascotaConfig mascotaConfig;
    private Usuario usuarioActual;

    public VentanaPrincipal() {

        setSize(400, 300);
        setTitle("Tienda Minorista");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        evento = new Evento(this);

        pLogin = new PanelLogin(evento);

        pProveedor = new PanelPadreProveedor(evento);
        pProducto = new PanelPadreProducto(evento);
        pCliente = new PanelPadreCliente(evento);
        pVenta = new PanelVenta(evento);
        pHistorial = new PanelHistorialVentas(evento);
        pCompra = new PanelCompra(evento);
        pHistorialCliente = new PanelHistorialCliente(evento);
        pHistorialCompra = new PanelHistorialCompra(evento);
        pInventario = new PanelInventario(evento);
        pMovimientosContables = new PanelMovimientosContables();
        pReportes = new PanelReportes(evento);


        tiendaConfig = new TiendaConfig();
        mascotaConfig = new MascotaConfig();

        
        add(pLogin, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        VentanaPrincipal login = new VentanaPrincipal();
        login.setVisible(true);
    }

    public void loguear() {
        try {
            CredencialDto validar = pLogin.getCredencialesUsuario();

            if (validar != null) {
                GestionSeguridad moduloSeguridad = tiendaConfig.getGestSeguridad();
                usuarioActual = moduloSeguridad.validarLogueo(validar);
                new LogsTxt().registrarLogin(usuarioActual);
                switch (usuarioActual.getRol()) {

                case ADMIN:
                    remove(pLogin);

                    // Panel norte con cerrar sesión
                    JPanel panelNorteAdmin = new JPanel(new BorderLayout());
                    JButton btnCerrarSesionAdmin = new JButton("Cerrar Sesión");
                    btnCerrarSesionAdmin.setBackground(new Color(192, 57, 43));
                    btnCerrarSesionAdmin.setForeground(Color.WHITE);
                    btnCerrarSesionAdmin.setFocusPainted(false);
                    JPanel panelCerrarAdmin = new JPanel();
                    panelCerrarAdmin.add(btnCerrarSesionAdmin);
                    panelNorteAdmin.add(panelCerrarAdmin, BorderLayout.EAST);
                    add(panelNorteAdmin, BorderLayout.NORTH);
                    btnCerrarSesionAdmin.addActionListener(e -> cerrarSesion());

                    // Panel lateral izquierdo
                    JPanel panelLateralAdmin = new JPanel(new GridLayout(3, 1, 5, 5));
                    panelLateralAdmin.setPreferredSize(new Dimension(150, 0));
                    panelLateralAdmin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    panelLateralAdmin.setBackground(new Color(52, 73, 94));

                    JButton btnProveedorAdmin = new JButton("Proveedores");
                    JButton btnClienteAdmin = new JButton("Clientes");
                    JButton btnVentasAdmin = new JButton("Ventas");

                    for (JButton btn : new JButton[]{btnProveedorAdmin, btnClienteAdmin, btnVentasAdmin}) {
                        btn.setBackground(new Color(52, 73, 94));
                        btn.setForeground(Color.WHITE);
                        btn.setFocusPainted(false);
                        btn.setBorderPainted(false);
                        btn.setFont(new Font("Arial", Font.BOLD, 13));
                    }

                    panelLateralAdmin.add(btnProveedorAdmin);
                    panelLateralAdmin.add(btnClienteAdmin);
                    panelLateralAdmin.add(btnVentasAdmin);
                    add(panelLateralAdmin, BorderLayout.WEST);

                    contenedor = new JPanel(new BorderLayout());
                    add(contenedor, BorderLayout.CENTER);

                    // Poblar datos iniciales
                    pProveedor.poblarTabla(tiendaConfig.getGestProveedor().leerProveedores());
                    pCliente.poblarTabla(tiendaConfig.getGestCliente().leerClientes());
                    contenedor.add(pProveedor);

                    this.setSize(1100, 650);
                    this.setLocationRelativeTo(null);
                    repaint();
                    revalidate();

                    btnProveedorAdmin.addActionListener(e -> {
                        contenedor.removeAll();
                        pProveedor.poblarTabla(tiendaConfig.getGestProveedor().leerProveedores());
                        contenedor.add(pProveedor);
                        contenedor.repaint();
                        contenedor.revalidate();
                    });

                    btnClienteAdmin.addActionListener(e -> {
                        contenedor.removeAll();
                        pCliente.poblarTabla(tiendaConfig.getGestCliente().leerClientes());
                        contenedor.add(pCliente);
                        contenedor.repaint();
                        contenedor.revalidate();
                    });

                    btnVentasAdmin.addActionListener(e -> {
                        contenedor.removeAll();
                        pHistorial.refrescar(tiendaConfig.getGestVenta().listarVentas());
                        contenedor.add(pHistorial);
                        contenedor.repaint();
                        contenedor.revalidate();
                    });

                    break;
                    
                case CAJERO:
                    remove(pLogin);

                    JPanel panelNorteCajero = new JPanel(new BorderLayout());
                    JButton btnCerrarSesionCajero = new JButton("Cerrar Sesión");
                    btnCerrarSesionCajero.setBackground(new Color(192, 57, 43));
                    btnCerrarSesionCajero.setForeground(Color.WHITE);
                    btnCerrarSesionCajero.setFocusPainted(false);
                    JPanel panelCerrarCajero = new JPanel();
                    panelCerrarCajero.add(btnCerrarSesionCajero);
                    panelNorteCajero.add(panelCerrarCajero, BorderLayout.EAST);
                    add(panelNorteCajero, BorderLayout.NORTH);
                    btnCerrarSesionCajero.addActionListener(e -> cerrarSesion());

                    JPanel panelLateral = new JPanel(new GridLayout(4, 1, 5, 5));
                    panelLateral.setPreferredSize(new Dimension(165, 0));
                    panelLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    panelLateral.setBackground(new Color(52, 73, 94));

                    JButton btnNuevaVenta = new JButton("Nueva Venta");
                    JButton btnHistorial = new JButton("Historial Ventas");
                    JButton btnClientesCajero = new JButton("Clientes");
                    JButton btnHistorialClientes = new JButton("Historial Clientes");

                    for (JButton btn : new JButton[]{btnNuevaVenta, btnHistorial, btnClientesCajero, btnHistorialClientes}) {
                        btn.setBackground(new Color(52, 73, 94));
                        btn.setForeground(Color.WHITE);
                        btn.setFocusPainted(false);
                        btn.setBorderPainted(false);
                        btn.setFont(new Font("Arial", Font.BOLD, 13));
                    }

                    panelLateral.add(btnNuevaVenta);
                    panelLateral.add(btnHistorial);
                    panelLateral.add(btnClientesCajero);
                    panelLateral.add(btnHistorialClientes);

                    add(panelLateral, BorderLayout.WEST);

                    contenedor = new JPanel(new BorderLayout());
                    add(contenedor, BorderLayout.CENTER);

                    pVenta.poblarClientes(tiendaConfig.getGestCliente().leerClientes());
                    pVenta.poblarProductos(tiendaConfig.getGestProducto().listar());

                    contenedor.add(pVenta);

                    this.setSize(1100, 650);
                    this.setLocationRelativeTo(null);
                    repaint();
                    revalidate();

                    btnNuevaVenta.addActionListener(e -> {
                        contenedor.removeAll();
                        pVenta.poblarProductos(tiendaConfig.getGestProducto().listar());
                        pVenta.poblarClientes(tiendaConfig.getGestCliente().leerClientes());
                        contenedor.add(pVenta);
                        contenedor.repaint();
                        contenedor.revalidate();
                    });

                    btnHistorial.addActionListener(e -> {
                        contenedor.removeAll();
                        pHistorial.refrescar(tiendaConfig.getGestVenta().listarVentas());
                        contenedor.add(pHistorial);
                        contenedor.repaint();
                        contenedor.revalidate();
                    });

                    btnClientesCajero.addActionListener(e -> {
                        contenedor.removeAll();
                        pCliente.poblarTabla(tiendaConfig.getGestCliente().leerClientes());
                        contenedor.add(pCliente);
                        contenedor.repaint();
                        contenedor.revalidate();
                    });

                    btnHistorialClientes.addActionListener(e -> {
                        contenedor.removeAll();
                        pHistorialCliente.refrescar(tiendaConfig.getGestVenta().listarVentas());
                        contenedor.add(pHistorialCliente);
                        contenedor.repaint();
                        contenedor.revalidate();
                    });

                    break;

                    case ALMACENISTA:
                        remove(pLogin);

                        JPanel panelNorteAlmacenista = new JPanel(new BorderLayout());
                        JButton btnCerrarSesionAlmacenista = new JButton("Cerrar Sesión");
                        btnCerrarSesionAlmacenista.setBackground(new Color(192, 57, 43));
                        btnCerrarSesionAlmacenista.setForeground(Color.WHITE);
                        btnCerrarSesionAlmacenista.setFocusPainted(false);
                        JPanel panelCerrarAlmacenista = new JPanel();
                        panelCerrarAlmacenista.add(btnCerrarSesionAlmacenista);
                        panelNorteAlmacenista.add(panelCerrarAlmacenista, BorderLayout.EAST);
                        add(panelNorteAlmacenista, BorderLayout.NORTH);
                        btnCerrarSesionAlmacenista.addActionListener(e -> cerrarSesion());

                        JPanel panelLateralAlmacen = new JPanel(new GridLayout(5, 1, 5, 5));
                        panelLateralAlmacen.setPreferredSize(new Dimension(150, 0));
                        panelLateralAlmacen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        panelLateralAlmacen.setBackground(new Color(52, 73, 94));

                        JButton btnNuevaCompra = new JButton("Nueva Compra");
                        JButton btnHistorialCompra = new JButton("Historial");
                        JButton btnProveedores = new JButton("Proveedores");
                        JButton btnProductos = new JButton("Productos");
                        JButton btnInventario = new JButton("Inventario");

                        for (JButton btn : new JButton[]{btnNuevaCompra, btnHistorialCompra, btnProveedores, btnProductos, btnInventario}) {
                            btn.setBackground(new Color(52, 73, 94));
                            btn.setForeground(Color.WHITE);
                            btn.setFocusPainted(false);
                            btn.setBorderPainted(false);
                            btn.setFont(new Font("Arial", Font.BOLD, 13));
                        }

                        panelLateralAlmacen.add(btnNuevaCompra);
                        panelLateralAlmacen.add(btnHistorialCompra);
                        panelLateralAlmacen.add(btnProveedores);
                        panelLateralAlmacen.add(btnProductos);
                        panelLateralAlmacen.add(btnInventario);

                        add(panelLateralAlmacen, BorderLayout.WEST);

                        contenedor = new JPanel(new BorderLayout());
                        add(contenedor, BorderLayout.CENTER);

                        pCompra.poblarProveedores(tiendaConfig.getGestProveedor().leerProveedores());
                        pCompra.poblarProductos(tiendaConfig.getGestProducto().listar());
                        contenedor.add(pCompra);

                        this.setSize(1100, 650);
                        this.setLocationRelativeTo(null);
                        repaint();
                        revalidate();

                        btnNuevaCompra.addActionListener(e -> {
                            contenedor.removeAll();
                            pCompra.poblarProveedores(tiendaConfig.getGestProveedor().leerProveedores());
                            pCompra.poblarProductos(tiendaConfig.getGestProducto().listar());
                            contenedor.add(pCompra);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        btnHistorialCompra.addActionListener(e -> {
                            contenedor.removeAll();
                            pHistorialCompra.refrescar(tiendaConfig.getGestCompra().listarCompras());
                            contenedor.add(pHistorialCompra);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        btnProveedores.addActionListener(e -> {
                            contenedor.removeAll();
                            pProveedor.poblarTabla(tiendaConfig.getGestProveedor().leerProveedores());
                            contenedor.add(pProveedor);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        btnProductos.addActionListener(e -> {
                            contenedor.removeAll();
                            contenedor.add(pProducto);
                            pProducto.poblarTabla(tiendaConfig.getGestProducto().listar());
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        btnInventario.addActionListener(e -> {
                            contenedor.removeAll();
                            contenedor.add(pInventario);
                            pInventario.refrescar(tiendaConfig.getGestInventario().listarMovimientos());
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        break;

                    case CONTADOR:
                        remove(pLogin);

                        JPanel panelNorteContador = new JPanel(new BorderLayout());
                        JButton btnCerrarSesionContador = new JButton("Cerrar Sesión");
                        btnCerrarSesionContador.setBackground(new Color(192, 57, 43));
                        btnCerrarSesionContador.setForeground(Color.WHITE);
                        btnCerrarSesionContador.setFocusPainted(false);
                        JPanel panelCerrarContador = new JPanel();
                        panelCerrarContador.add(btnCerrarSesionContador);
                        panelNorteContador.add(panelCerrarContador, BorderLayout.EAST);
                        add(panelNorteContador, BorderLayout.NORTH);
                        btnCerrarSesionContador.addActionListener(e -> cerrarSesion());

                        JPanel panelLateralContador = new JPanel(new GridLayout(2, 1, 5, 5));
                        panelLateralContador.setPreferredSize(new Dimension(165, 0));
                        panelLateralContador.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        panelLateralContador.setBackground(new Color(52, 73, 94));

                        JButton btnMovimientosContables = new JButton("Movimientos Contables");
                        JButton btnReportes = new JButton("Reportes");

                        for (JButton btn : new JButton[]{btnMovimientosContables, btnReportes}) {
                            btn.setBackground(new Color(52, 73, 94));
                            btn.setForeground(Color.WHITE);
                            btn.setFocusPainted(false);
                            btn.setBorderPainted(false);
                            btn.setFont(new Font("Arial", Font.BOLD, 13));
                        }

                        panelLateralContador.add(btnMovimientosContables);
                        panelLateralContador.add(btnReportes);
                        add(panelLateralContador, BorderLayout.WEST);

                        contenedor = new JPanel(new BorderLayout());
                        add(contenedor, BorderLayout.CENTER);
                        contenedor.add(pMovimientosContables);

                        this.setSize(1100, 650);
                        this.setLocationRelativeTo(null);
                        repaint();
                        revalidate();

                        btnMovimientosContables.addActionListener(e -> {
                            contenedor.removeAll();
                            pMovimientosContables.refrescar(tiendaConfig.getGestContable().listarMovimientos());
                            contenedor.add(pMovimientosContables);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        btnReportes.addActionListener(e -> {
                            contenedor.removeAll();
                            contenedor.add(pReportes);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error de Autenticación",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== PROVEEDOR =====================

    public void lanzarDialogoProveedor() {
        nuevoProveedor = new DialogoProveedor(evento, "Crear Proveedor", true);
        nuevoProveedor.setSize(400, 400);
        nuevoProveedor.setLocationRelativeTo(null);
        nuevoProveedor.setVisible(true);
    }

    public void cerrarDialogoProveedor() {
        if (nuevoProveedor != null) {
            nuevoProveedor.setVisible(false);
            nuevoProveedor = null;
        }
    }

    public void crearProveedor() {
        try {
        	tiendaConfig.getGestProveedor().agregarProveedor(nuevoProveedor.capturarDatos());
            cerrarDialogoProveedor();
            pProveedor.poblarTabla(tiendaConfig.getGestProveedor().leerProveedores());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        JOptionPane.showMessageDialog(this, "Proveedor agregado exitosamente.");
    }

    public void lanzarDialogoModificarProveedor() {
        int codigo = pProveedor.getItemSeleccionado();
        if (codigo == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un proveedor de la tabla.");
            return;
        }
        Proveedor p = tiendaConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);
        nuevoProveedor = new DialogoProveedor(evento, "Modificar Proveedor", false);
        nuevoProveedor.cargarDatos(p);
        nuevoProveedor.setSize(400, 400);
        nuevoProveedor.setLocationRelativeTo(null);
        nuevoProveedor.setVisible(true);
    }

    public void modificarProveedor() {
        int codigo = pProveedor.getItemSeleccionado();
        if (codigo == -1) return;
        try {
            Proveedor p = nuevoProveedor.capturarDatos();
            tiendaConfig.getGestProveedor().modificarProveedor(p);
            pProveedor.poblarTabla(tiendaConfig.getGestProveedor().leerProveedores());
            JOptionPane.showMessageDialog(this, "Proveedor modificado exitosamente.");
            cerrarDialogoProveedor();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void eliminarProveedor() {
        int codigo = pProveedor.getItemSeleccionado();
        if (codigo == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un proveedor de la tabla.");
            return;
        }
        try {
            Proveedor p = tiendaConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);
            if (p != null && p.getEstado() == EstadoEnum.INACTIVO) {
                JOptionPane.showMessageDialog(this, "El proveedor ya se encuentra inactivo.");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de inactivar el proveedor " + codigo + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
            	tiendaConfig.getGestProveedor().eliminarProveedor(codigo);
                pProveedor.poblarTabla(tiendaConfig.getGestProveedor().leerProveedores());
                JOptionPane.showMessageDialog(this, "Proveedor inactivado exitosamente.");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void verProveedor() {
        int codigo = pProveedor.getItemSeleccionado();
        if (codigo == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un proveedor de la tabla.");
            return;
        }
        Proveedor p = tiendaConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);
        if (p != null) {
            JOptionPane.showMessageDialog(this,
                    "Código: " + p.getCodigoProveedor() +
                    "\nRazón social: " + p.getRazonSocial() +
                    "\nNIT: " + p.getNit() +
                    "\nDirección: " + p.getDireccionP() +
                    "\nTeléfono: " + p.getTelefonoP() +
                    "\nCorreo electrónico: " + p.getCorreoP());
        }
    }

    public void buscarProveedor() {
        try {
            String input = JOptionPane.showInputDialog(this, "Ingrese código del proveedor:");
            if (input == null || input.isEmpty()) return;
            int codigo = Integer.parseInt(input);
            Proveedor p = tiendaConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);
            if (p != null) {
                pProveedor.poblarTabla(java.util.List.of(p));
            } else {
                JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public void limpiarProveedor() {
        pProveedor.poblarTabla(tiendaConfig.getGestProveedor().leerProveedores());
    }

    public void activarProveedor() {
        int codigo = pProveedor.getItemSeleccionado();
        if (codigo == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un proveedor de la tabla");
            return;
        }
        try {
            Proveedor p = tiendaConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);
            if (p != null && p.getEstado() == EstadoEnum.ACTIVO) {
                JOptionPane.showMessageDialog(this, "El proveedor ya se encuentra activo.");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de activar el proveedor " + codigo + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
            	tiendaConfig.getGestProveedor().activarProveedor(codigo);
                pProveedor.poblarTabla(tiendaConfig.getGestProveedor().leerProveedores());
                JOptionPane.showMessageDialog(this, "Proveedor activado exitosamente.");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================== CLIENTE =====================

    public void lanzarDialogoCliente() {
        nuevoCliente = new DialogoCliente(evento, "Crear Cliente", true);
        nuevoCliente.setSize(400, 400);
        nuevoCliente.setLocationRelativeTo(null);
        nuevoCliente.setVisible(true);
    }

    public void cerrarDialogoCliente() {
        if (nuevoCliente != null) {
            nuevoCliente.setVisible(false);
            nuevoCliente = null;
        }
    }

    public void crearCliente() {
        try {
        	tiendaConfig.getGestCliente().agregarCliente(nuevoCliente.capturarDatos());
            cerrarDialogoCliente();
            pCliente.poblarTabla(tiendaConfig.getGestCliente().leerClientes());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void lanzarDialogoModificarCliente() {
        int codigo = pCliente.getItemSeleccionado();
        if (codigo == -1) return;
        Cliente c = tiendaConfig.getGestCliente().buscarClientePorCodigo(codigo);
        nuevoCliente = new DialogoCliente(evento, "Modificar Cliente", false);
        nuevoCliente.cargarDatos(c);
        nuevoCliente.setSize(400, 400);
        nuevoCliente.setLocationRelativeTo(null);
        nuevoCliente.setVisible(true);
    }

    public void modificarCliente() {
        try {
            Cliente c = nuevoCliente.capturarDatos();
            tiendaConfig.getGestCliente().modificarCliente(c);
            cerrarDialogoCliente();
            pCliente.poblarTabla(tiendaConfig.getGestCliente().leerClientes());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void eliminarCliente() {
        int codigo = pCliente.getItemSeleccionado();
        if (codigo == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente de la tabla");
            return;
        }
        try {
            Cliente c = tiendaConfig.getGestCliente().buscarClientePorCodigo(codigo);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado");
                return;
            }
            if (c.getEstado() == EstadoEnum.INACTIVO) {
                JOptionPane.showMessageDialog(this, "El cliente ya está inactivo");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de inactivar el cliente " + codigo + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                c.setEstado(EstadoEnum.INACTIVO);
                tiendaConfig.getGestCliente().modificarCliente(c);
                pCliente.poblarTabla(tiendaConfig.getGestCliente().leerClientes());
                JOptionPane.showMessageDialog(this, "Cliente inactivado exitosamente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void buscarCliente() {
        try {
            String input = JOptionPane.showInputDialog(this, "Ingrese código del cliente:");
            if (input == null || input.isEmpty()) return;
            int codigo = Integer.parseInt(input);
            Cliente c = tiendaConfig.getGestCliente().buscarClientePorCodigo(codigo);
            if (c != null) {
                pCliente.poblarTabla(java.util.List.of(c));
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public void verCliente() {
        int codigo = pCliente.getItemSeleccionado();
        if (codigo == -1) return;
        Cliente c = tiendaConfig.getGestCliente().buscarClientePorCodigo(codigo);
        if (c != null) {
            JOptionPane.showMessageDialog(this,
                    "Código: " + c.getIdCliente() +
                    "\nNombre: " + c.getNombreCompleto() +
                    "\nDirección: " + c.getDireccionC() +
                    "\nTeléfono: " + c.getTelefonoC());
        }
    }

    public void limpiarCliente() {
        pCliente.poblarTabla(tiendaConfig.getGestCliente().leerClientes());
    }

    public void activarCliente() {
        int codigo = pCliente.getItemSeleccionado();
        if (codigo == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente de la tabla");
            return;
        }
        try {
            Cliente c = tiendaConfig.getGestCliente().buscarClientePorCodigo(codigo);
            if (c != null && c.getEstado() == EstadoEnum.ACTIVO) {
                JOptionPane.showMessageDialog(this, "El cliente ya se encuentra activo.");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de activar el cliente " + codigo + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
            	tiendaConfig.getGestCliente().activarCliente(codigo);
                pCliente.poblarTabla(tiendaConfig.getGestCliente().leerClientes());
            }
            JOptionPane.showMessageDialog(this, "Cliente activado exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================== PRODUCTO =====================

    public void lanzarDialogoProducto() {
        nuevoProducto = new DialogoProducto(evento, "Crear Producto", true);
        nuevoProducto.setSize(400, 400);
        nuevoProducto.setLocationRelativeTo(null);
        nuevoProducto.setVisible(true);
    }

    public void cerrarDialogoProducto() {
        if (nuevoProducto != null) {
            nuevoProducto.setVisible(false);
            nuevoProducto = null;
        }
    }

    public void crearProducto() {
    	tiendaConfig.getGestProducto().guardar(nuevoProducto.capturarDatos());
        cerrarDialogoProducto();
        pProducto.poblarTabla(tiendaConfig.getGestProducto().listar());
    }

    public void lanzarDialogoModificarProducto() {
        int codigo = pProducto.getItemSeleccionado();
        if (codigo == -1) return;
        Producto p = tiendaConfig.getGestProducto().buscar(codigo);
        nuevoProducto = new DialogoProducto(evento, "Modificar Producto", false);
        nuevoProducto.cargarDatos(p);
        nuevoProducto.setSize(400, 400);
        nuevoProducto.setLocationRelativeTo(null);
        nuevoProducto.setVisible(true);
    }

    public void modificarProducto() {
        try {
            Producto p = nuevoProducto.capturarDatos();
            tiendaConfig.getGestProducto().actualizar(p);
            cerrarDialogoProducto();
            pProducto.poblarTabla(tiendaConfig.getGestProducto().listar());
            JOptionPane.showMessageDialog(this, "Producto modificado exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public void inactivarProducto() {
        int codigo = pProducto.getItemSeleccionado();
        if (codigo == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto de la tabla.");
            return;
        }
        try {
            Producto p = tiendaConfig.getGestProducto().buscar(codigo);
            if (p != null && !p.isActivo()) {
                JOptionPane.showMessageDialog(this, "El producto ya se encuentra inactivo.");
                return;
            }
            Object[] opciones = {"Sí", "No"};
            int respuesta = JOptionPane.showOptionDialog(
                    this,
                    "¿Está seguro de inactivar el producto " + codigo + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[1]);
            if (respuesta == 0) {
                tiendaConfig.getGestProducto().inactivar(codigo);
                pProducto.poblarTabla(tiendaConfig.getGestProducto().listar());
                JOptionPane.showMessageDialog(this, "Producto inactivado exitosamente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public void activarProducto() {
        int codigo = pProducto.getItemSeleccionado();
        if (codigo == -1) return;
        try {
            tiendaConfig.getGestProducto().activar(codigo);
            pProducto.poblarTabla(tiendaConfig.getGestProducto().listar());
            JOptionPane.showMessageDialog(this, "Producto activado exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }


    public void buscarProducto() {
        try {
            String input = JOptionPane.showInputDialog(this, "Ingrese código del producto:");
            if (input == null || input.isEmpty()) return;
            int codigo = Integer.parseInt(input);
            Producto p = tiendaConfig.getGestProducto().buscar(codigo);
            if (p != null) {
                pProducto.poblarTabla(java.util.List.of(p));
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public void limpiarProducto() {
        pProducto.poblarTabla(tiendaConfig.getGestProducto().listar());
    }

    public void verProducto() {
        int codigo = pProducto.getItemSeleccionado();
        if (codigo == -1) return;
        Producto p = tiendaConfig.getGestProducto().buscar(codigo);
        if (p != null) {
            JOptionPane.showMessageDialog(this,
                    "Código: " + p.getCodigoProducto() +
                    "\nNombre: " + p.getNombreProducto() +
                    "\nCategoría: " + p.getCategoria() +
                    "\nPrecio Compra: " + p.getPrecioCompra() +
                    "\nPrecio Venta: " + p.getPrecioVenta() +
                    "\nStock: " + p.getStockActual());
        }
    }

    // ===================== VENTA =====================

    public void agregarProductoVenta() {
        if (pVenta.getFilaProductoSeleccionada() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }
        String txtCantidad = pVenta.getTxtCantidad();
        if (txtCantidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número");
            return;
        }
        int stock = pVenta.getStockProductoSeleccionado();
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
            return;
        }
        if (cantidad > stock) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente");
            return;
        }
        int codigo = pVenta.getCodigoProductoSeleccionado();
        String nombre = pVenta.getNombreProductoSeleccionado();
        double precio = pVenta.getPrecioProductoSeleccionado();
        double iva = pVenta.getIvaProductoSeleccionado();
        double impuestos = cantidad * precio * iva;
        double subtotal = (precio * cantidad) + impuestos;
        int nuevoStock = stock - cantidad;

        pVenta.agregarFilaDetalle(nombre, cantidad, precio, impuestos, subtotal);
        pVenta.actualizarStockTabla(pVenta.getFilaProductoSeleccionada(), nuevoStock);

        Producto producto = new Producto();
        producto.setCodigoProducto(codigo);
        producto.setNombreProducto(nombre);
        producto.setPrecioVenta(precio);
        producto.setPorcentajeIva(iva);
        producto.setStockActual(nuevoStock);

        listaDetalle.add(new DetalleVenta(producto, cantidad));

        double total = 0;
        for (int i = 0; i < pVenta.getFilasDetalle(); i++) {
            total += pVenta.getSubtotalDetalle(i);
        }
        pVenta.actualizarTotal(total);
    }

    public void quitarProductoVenta() {
        int fila = pVenta.getFilaDetalleSeleccionada();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto del detalle para quitar");
            return;
        }
        DetalleVenta detalle = listaDetalle.get(fila);
        pVenta.devolverStockTabla(detalle.getProducto().getCodigoProducto(), detalle.getCantidad());
        pVenta.quitarFilaDetalle(fila);
        listaDetalle.remove(fila);
        double total = 0;
        for (int i = 0; i < pVenta.getFilasDetalle(); i++) {
            total += pVenta.getSubtotalDetalle(i);
        }
        pVenta.actualizarTotal(total);
    }

    public void finalizarVenta() {
        try {
            Venta venta = new Venta();
            venta.setCliente(pVenta.getClienteSeleccionado());
            venta.setFormaPago(pVenta.getFormaPagoSeleccionada());
            venta.setDetalles(listaDetalle);

            List<Producto> productos = tiendaConfig.getGestProducto().listar();

            // 1. Guardar venta
            tiendaConfig.getGestVenta().guardarVenta(venta);

            // 2. Inventario registra salida y actualiza stock
            tiendaConfig.getGestInventario().registrarSalidaPorVenta(venta, productos);

            // 3. Persistir stock
            tiendaConfig.getGestProducto().guardarTodos(productos);

            // 4. ✅ Registrar movimientos contables
            System.out.println(">>> Registrando movimiento contable venta: " + venta.getNumeroFactura());
            tiendaConfig.getGestContable().registrarVenta(venta);
            System.out.println(">>> Movimiento contable venta OK");

            new TxtFactura().generarFactura(venta);

            JOptionPane.showMessageDialog(this,
                    "Venta registrada.\nFactura: " + venta.getNumeroFactura());
            listaDetalle = new ArrayList<>();
            pVenta.limpiar();

        } catch (Exception e) {
            e.printStackTrace(); // ✅ muestra error completo en consola
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getClass().getName() + "\n" + e.getMessage());
        }
    }

    public void lanzarDialogoAnularVenta() {
        String facturaSeleccionada = pHistorial.getFacturaSeleccionada();
        if (facturaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta de la tabla");
            return;
        }
        Venta venta = tiendaConfig.getGestVenta().buscarPorFactura(facturaSeleccionada);
        if (venta != null && venta.getEstado() == EstadoVentaEnum.ANULADA) {
            JOptionPane.showMessageDialog(this, "La venta " + facturaSeleccionada + " ya está anulada.");
            return;
        }
        dialogoAnular = new DialogoAnularVenta(evento);
        dialogoAnular.setFactura(facturaSeleccionada);
        dialogoAnular.setVisible(true);
    }

    public void cerrarDialogoAnularVenta() {
        if (dialogoAnular != null) {
            dialogoAnular.setVisible(false);
            dialogoAnular = null;
        }
    }

    public void anularVenta() {
        try {
            String factura = pHistorial.getFacturaSeleccionada();
            String motivo = dialogoAnular.getMotivo();
            if (factura == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una venta de la tabla");
                return;
            }
            if (motivo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el motivo de anulación");
                return;
            }
            Venta venta = tiendaConfig.getGestVenta().buscarPorFactura(factura);
            tiendaConfig.getGestVenta().anularVenta(factura, motivo);
            List<Producto> productos = tiendaConfig.getGestProducto().listar();
            tiendaConfig.getGestInventario().registrarEntradaPorAnulacion(venta, productos, motivo);
            tiendaConfig.getGestProducto().guardarTodos(productos);
            pHistorial.refrescar(tiendaConfig.getGestVenta().listarVentas());
            cerrarDialogoAnularVenta();
            JOptionPane.showMessageDialog(this, "Venta anulada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getClass().getName() + "\n" + e.getMessage());
        }
    }

    // ===================== COMPRA =====================

    public void agregarProductoCompra() {
        if (pCompra.getFilaProductoSeleccionada() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }
        String txtCantidad = pCompra.getTxtCantidad();
        if (txtCantidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número");
            return;
        }
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
            return;
        }
        int codigo = pCompra.getCodigoProductoSeleccionado();
        String nombre = pCompra.getNombreProductoSeleccionado();
        double precio = pCompra.getPrecioCompraProductoSeleccionado();
        int stock = pCompra.getStockProductoSeleccionado();
        double subtotal = precio * cantidad;
        int nuevoStock = stock + cantidad;

        pCompra.agregarFilaDetalle(nombre, cantidad, precio, subtotal);
        pCompra.actualizarStockTabla(pCompra.getFilaProductoSeleccionada(), nuevoStock);

        Producto producto = new Producto();
        producto.setCodigoProducto(codigo);
        producto.setNombreProducto(nombre);
        producto.setPrecioCompra(precio);
        producto.setStockActual(nuevoStock);

        listaDetalleCompra.add(new DetalleCompra(producto, cantidad));

        double total = 0;
        for (int i = 0; i < pCompra.getFilasDetalle(); i++) {
            total += pCompra.getSubtotalDetalle(i);
        }
        pCompra.actualizarTotal(total);
    }

    public void finalizarCompra() {
        try {
            if (listaDetalleCompra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe agregar productos");
                return;
            }
            Proveedor proveedor = pCompra.getProveedorSeleccionado();
            if (proveedor == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor");
                return;
            }

            Compra compra = new Compra();
            compra.setProveedor(proveedor);
            compra.setDetalles(listaDetalleCompra);

            List<Producto> productos = tiendaConfig.getGestProducto().listar();

            // 1. Guardar compra
            tiendaConfig.getGestCompra().guardarCompra(compra);

            // 2. Inventario registra entrada por cada producto
            for (DetalleCompra detalle : listaDetalleCompra) {
            	tiendaConfig.getGestInventario()
                        .registrarEntradaPorCompra(
                                compra.getNumeroFactura(),
                                detalle.getProducto(),
                                detalle.getCantidad(),
                                productos);
            }

            // 3. Persistir stock
            tiendaConfig.getGestProducto().guardarTodos(productos);

            // 4. ✅ Registrar movimientos contables
            System.out.println(">>> Registrando movimiento contable compra: " + compra.getNumeroFactura());
            tiendaConfig.getGestContable().registrarCompra(compra);
            System.out.println(">>> Movimiento contable compra OK");

            JOptionPane.showMessageDialog(this,
                    "Compra registrada.\nFactura: " + compra.getNumeroFactura());
            listaDetalleCompra = new ArrayList<>();
            pCompra.limpiar();

        } catch (Exception e) {
            e.printStackTrace(); // ✅ muestra error completo en consola
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getClass().getName() + "\n" + e.getMessage());
        }
    }

    public void alertaStockMinimo() {
        pProducto.filtrarStockMinimo();
    }

    public void cerrarSesion() {
        Object[] opciones = {"Sí", "No"};
        int respuesta = JOptionPane.showOptionDialog(
                this,
                "¿Está seguro de que desea cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1]);
        if (respuesta == 0) {
        	if (usuarioActual != null) {
        	    new LogsTxt().registrarLogout(usuarioActual);
        	    usuarioActual = null;
        	}
            getContentPane().removeAll();
            listaDetalle = new ArrayList<>();
            listaDetalleCompra = new ArrayList<>();
            setSize(400, 300);
            setLocationRelativeTo(null);
            add(pLogin, BorderLayout.CENTER);
            repaint();
            revalidate();
        }
    }
        public void lanzarDialogoMascota() {
            nuevoMascota = new DialogoMascota(evento, "Crear Mascota", true);
            nuevoMascota.setSize(400, 300);
            nuevoMascota.setLocationRelativeTo(null);
            nuevoMascota.setVisible(true);
        }

        public void cerrarDialogoMascota() {
            if (nuevoMascota != null) {
                nuevoMascota.setVisible(false);
                nuevoMascota = null;
            }
        }

        public void crearMascota() {
            try {
                mascotaConfig.getGestMascota().agregarMascota(nuevoMascota.capturarDatos());
                cerrarDialogoMascota();
                JOptionPane.showMessageDialog(this, "Mascota agregada exitosamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
     
    }

    // ===================== CONTADOR =====================

    public void generarReporte() {
        String desde = pReportes.getDesde();
        String hasta = pReportes.getHasta();
        if (desde.isEmpty() || hasta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el rango de fechas");
            return;
        }
        ResumenFinanciero resumen = tiendaConfig.getGestReporte()
                .generarReporte(desde, hasta, tiendaConfig.getGestProducto().listar());
        pReportes.mostrarResumen(resumen);
        JOptionPane.showMessageDialog(this, "Reporte generado y guardado en JSON.");
    }
    
}