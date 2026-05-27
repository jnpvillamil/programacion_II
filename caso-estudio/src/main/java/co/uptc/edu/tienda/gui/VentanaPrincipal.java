package co.uptc.edu.tienda.gui;

import javax.swing.*;

import co.uptc.edu.co.tienda.configs.ClienteConfig;
import co.uptc.edu.co.tienda.configs.CompraConfig;
import co.uptc.edu.co.tienda.configs.InventarioConfig;
import co.uptc.edu.co.tienda.configs.ProductoConfig;
import co.uptc.edu.co.tienda.configs.ProveedorConfig;
import co.uptc.edu.co.tienda.configs.SeguridadConfig;
import co.uptc.edu.co.tienda.configs.VentaConfig;
import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.enums.EstadoVentaEnum;
import co.uptc.edu.tienda.modelo.Cliente;
import co.uptc.edu.tienda.modelo.Compra;
import co.uptc.edu.tienda.modelo.DetalleCompra;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Proveedor;
import co.uptc.edu.tienda.modelo.Venta;
import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.negocio.GestionSeguridad;
import co.uptc.edu.tienda.negocio.dto.CredencialDto;
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
    private List<DetalleVenta> listaDetalle = new ArrayList<>();
    private List<DetalleCompra> listaDetalleCompra = new ArrayList<>();

    private JPanel contenedor;

    private JButton btnProveedor;
    private JButton btnProducto;
    private JButton btnCliente;

    private SeguridadConfig seguridadConfig;

    private DialogoProveedor nuevoProveedor;
    private DialogoProducto nuevoProducto;
    private DialogoCliente nuevoCliente;
    private DialogoAnularVenta dialogoAnular;

    private Evento evento;
    
    //CONFIGS
    private ProveedorConfig proveedorConfig;
    private ProductoConfig productoConfig;
    private ClienteConfig clienteConfig;
    private VentaConfig ventaConfig;
    private InventarioConfig inventarioConfig;
    private CompraConfig compraConfig;

    public VentanaPrincipal() {

        setSize(400,300);
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

        seguridadConfig = new SeguridadConfig();

        proveedorConfig = new ProveedorConfig();
        productoConfig = new ProductoConfig();
        clienteConfig = new ClienteConfig();
        ventaConfig = new VentaConfig();
        inventarioConfig = new InventarioConfig();
        compraConfig = new CompraConfig();

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
                // 1. Obtenemos el servicio de negocio
                GestionSeguridad moduloSeguridad = seguridadConfig.getGestSeguridad();
                
                // 2. Validamos credenciales y obtenemos el usuario completo
                co.uptc.edu.tienda.modelo.Usuario usuarioLogueado = moduloSeguridad.validarLogueo(validar);
                
                // 3. Evaluamos el Rol del usuario para definir su acceso
                switch (usuarioLogueado.getRol()) {
                    
                    case ADMIN:
                        // El flujo que ya tienes para el Administrador
                        remove(pLogin);
                        
                        JPanel panelNorteAdmin = new JPanel(new BorderLayout());
                        		
                        JPanel panelBotones = new JPanel();
                        btnProveedor = new JButton("Proveedor");
                        btnProducto = new JButton("Producto");
                        btnCliente = new JButton("Cliente");

                        panelBotones.add(btnProveedor);                       
                        panelBotones.add(btnCliente);
                        JButton btnCerrarSesionAdmin = new JButton("Cerrar Sesión");
                        btnCerrarSesionAdmin.setBackground(new Color(192, 57, 43));
                        btnCerrarSesionAdmin.setForeground(Color.WHITE);
                        btnCerrarSesionAdmin.setFocusPainted(false);
                        JPanel panelCerrar = new JPanel();
                        panelCerrar.add(btnCerrarSesionAdmin);
                        panelNorteAdmin.add(panelBotones, BorderLayout.CENTER);
                        panelNorteAdmin.add(panelCerrar, BorderLayout.EAST);

                        add(panelNorteAdmin, BorderLayout.NORTH);

                        contenedor = new JPanel(new BorderLayout());
                        add(contenedor, BorderLayout.CENTER);
                        contenedor.add(pProveedor);

                        // Poblar datos iniciales del Admin
                        pProveedor.poblarTabla(proveedorConfig.getGestProveedor().leerProveedores());
                        pProducto.poblarTabla(productoConfig.getGestProducto().listar());
                        pCliente.poblarTabla(clienteConfig.getGestCliente().leerClientes());

                        // Listeners de los botones del Admin
                        btnProveedor.addActionListener(e -> {
                            contenedor.removeAll();
                            contenedor.add(pProveedor);
                            pProveedor.poblarTabla(proveedorConfig.getGestProveedor().leerProveedores());
                            contenedor.repaint();
                            contenedor.revalidate();
                        });


                        btnCliente.addActionListener(e -> {
                            contenedor.removeAll();
                            contenedor.add(pCliente);
                            pCliente.poblarTabla(clienteConfig.getGestCliente().leerClientes());
                            contenedor.repaint();
                            contenedor.revalidate();
                        });
                        btnCerrarSesionAdmin.addActionListener(e -> cerrarSesion());
                        this.setSize(900, 500);
                        this.setLocationRelativeTo(null);
                        repaint();
                        revalidate();
                        break;

                    case CAJERO:
                        remove(pLogin);
                        // boton para cerrar sesion
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
                        
                        // Panel lateral izquierdo
                        
                        JPanel panelLateral = new JPanel(new GridLayout(4, 1, 5, 5));
                        panelLateral.setPreferredSize(new Dimension(165, 0));
                        panelLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        panelLateral.setBackground(new Color(52, 73, 94)); // azul oscuro

                        JButton btnNuevaVenta = new JButton("Nueva Venta");
                        JButton btnHistorial = new JButton("Historial Ventas");
                        JButton btnClientesCajero = new JButton("Clientes");
                        JButton btnHistorialClientes = new JButton("Historial Clientes");

                        // Estilo botones laterales
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

                        // Panel de contenido derecho
                        contenedor = new JPanel(new BorderLayout());
                        add(contenedor, BorderLayout.CENTER);

                        // Cargar datos en pVenta
                        pVenta.poblarClientes(clienteConfig.getGestCliente().leerClientes());
                        pVenta.poblarProductos(productoConfig.getGestProducto().listar());

                        // Mostrar nueva venta por defecto
                        contenedor.add(pVenta);

                        this.setSize(1100, 650);
                        this.setLocationRelativeTo(null);
                        repaint();
                        revalidate();

                        // Eventos botones laterales
                        btnNuevaVenta.addActionListener(e -> {
                            contenedor.removeAll();
                            pVenta.poblarProductos(productoConfig.getGestProducto().listar());
                            pVenta.poblarClientes(clienteConfig.getGestCliente().leerClientes());
                            contenedor.add(pVenta);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        btnHistorial.addActionListener(e -> {
                            contenedor.removeAll();
                            pHistorial.refrescar(ventaConfig.getGestVenta().listarVentas());
                            contenedor.add(pHistorial);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        btnClientesCajero.addActionListener(e -> {
                            contenedor.removeAll();
                            pCliente.poblarTabla(clienteConfig.getGestCliente().leerClientes());
                            contenedor.add(pCliente);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });
                        
                        btnHistorialClientes.addActionListener(e -> {
                            contenedor.removeAll();
                            pHistorialCliente.refrescar(ventaConfig.getGestVenta().listarVentas());
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
                        JButton btnHistorialCompra = new JButton("Historial");      // ← futuro
                        JButton btnProveedores = new JButton("Proveedores");
                        JButton btnProductos = new JButton("Productos");
                        JButton btnInventario = new JButton("Inventario");

                        

                        for (JButton btn : new JButton[]{btnNuevaCompra, btnHistorialCompra, btnProveedores, btnProductos, 
                        		btnInventario}) {
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

                        pCompra.poblarProveedores(proveedorConfig.getGestProveedor().leerProveedores());
                        pCompra.poblarProductos(productoConfig.getGestProducto().listar());
                        contenedor.add(pCompra);

                        this.setSize(1100, 650);
                        this.setLocationRelativeTo(null);
                        repaint();
                        revalidate();
                        

                        btnNuevaCompra.addActionListener(e -> {
                            contenedor.removeAll();
                            pCompra.poblarProveedores(proveedorConfig.getGestProveedor().leerProveedores());
                            pCompra.poblarProductos(productoConfig.getGestProducto().listar());
                            contenedor.add(pCompra);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        btnHistorialCompra.addActionListener(e -> {
                            
                        	contenedor.removeAll();
                            pHistorialCompra.refrescar(compraConfig.getGestion().listarCompras());
                            contenedor.add(pHistorialCompra);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        btnProveedores.addActionListener(e -> {
                        	contenedor.removeAll();
                            pProveedor.poblarTabla(proveedorConfig.getGestProveedor().leerProveedores());
                            contenedor.add(pProveedor);
                            contenedor.repaint();
                            contenedor.revalidate();
                        });
                        
                        btnProductos.addActionListener(e -> {
                            contenedor.removeAll();
                            contenedor.add(pProducto);
                            pProducto.poblarTabla(productoConfig.getGestProducto().listar());
                            contenedor.repaint();
                            contenedor.revalidate();
                        });
                        
                        btnInventario.addActionListener(e -> {
                        	contenedor.removeAll();
                            contenedor.add(pInventario);
                            pInventario.refrescar(inventarioConfig.getGestInventario().listarMovimientos());
                            contenedor.repaint();
                            contenedor.revalidate();
                        });

                        break;

                    case CONTADOR:
                        // Preparado para el futuro: Por ahora solo notificamos
                        JOptionPane.showMessageDialog(
                            this,
                            "¡Bienvenido, Contador(a)! Tu módulo de Reportes y Balances está en desarrollo.",
                            "Módulo en Construcción",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                        break;

                    default:
                        JOptionPane.showMessageDialog(
                            this,
                            "Rol no reconocido en el sistema.",
                            "Error de Permisos",
                            JOptionPane.ERROR_MESSAGE
                        );
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

        nuevoProveedor = new DialogoProveedor(
            evento,
            "Crear Proveedor",
            true
        );

        nuevoProveedor.setSize(400,400);
        nuevoProveedor.setLocationRelativeTo(null);
        nuevoProveedor.setVisible(true);
    }

    public void cerrarDialogoProveedor() {

        if(nuevoProveedor != null){

            nuevoProveedor.setVisible(false);
            nuevoProveedor = null;
        }
    }

    public void crearProveedor() {

        try {

            proveedorConfig.getGestProveedor().agregarProveedor(
                nuevoProveedor.capturarDatos()
            );

            cerrarDialogoProveedor();

            pProveedor.poblarTabla(
                proveedorConfig.getGestProveedor().leerProveedores()
            );

        } catch(Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
        JOptionPane.showMessageDialog(this, "Proveedor agregado exitosamente.");
    }

    public void lanzarDialogoModificarProveedor() {

        int codigo = pProveedor.getItemSeleccionado();

        if(codigo == -1) {
        	JOptionPane.showMessageDialog(this, "Por favor, seleccione un proveedor de la tabla.");
        	return;
        }

        Proveedor p = proveedorConfig
                .getGestProveedor()
                .buscarProveedorPorCodigo(codigo);

        nuevoProveedor = new DialogoProveedor(
            evento,
            "Modificar Proveedor",
            false
        );

        nuevoProveedor.cargarDatos(p);

        nuevoProveedor.setSize(400,400);
        nuevoProveedor.setLocationRelativeTo(null);
        nuevoProveedor.setVisible(true);
    }

    public void modificarProveedor() {

        int codigo = pProveedor.getItemSeleccionado();

        if(codigo == -1) return;
        
        try {

            Proveedor p = nuevoProveedor.capturarDatos();

            proveedorConfig.getGestProveedor().modificarProveedor(p);


            pProveedor.poblarTabla(
                proveedorConfig.getGestProveedor().leerProveedores()
            );
            JOptionPane.showMessageDialog(this, "Proveedor modificado exitosamente.");
            cerrarDialogoProveedor();

        } catch(Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
        
    }

    public void eliminarProveedor() {

        int codigo = pProveedor.getItemSeleccionado();

        if(codigo == -1) {
        	JOptionPane.showMessageDialog(this,"Por favor, seleccione un proveedor de la tabla.");
        	return;
        }
 try {
            
            Proveedor p = proveedorConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);
            
            if (p != null && p.getEstado() == EstadoEnum.INACTIVO) {
                JOptionPane.showMessageDialog(this, "El proveedor ya se encuentra inactivo.");
                return; // 
            }

            
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de inactivar el proveedor " + codigo + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
                );

                if (respuesta == JOptionPane.YES_OPTION) {

                    proveedorConfig.getGestProveedor().eliminarProveedor(codigo);

                    pProveedor.poblarTabla(
                        proveedorConfig.getGestProveedor().leerProveedores()
                    );
                }
                JOptionPane.showMessageDialog(this, "Proveedor inactivado exitosamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

    public void verProveedor() {

        int codigo = pProveedor.getItemSeleccionado();

        if (codigo == -1) {
        	JOptionPane.showMessageDialog(this,"Por favor, seleccione un proveedor de la tabla.");
        	return;
        }

        Proveedor p = proveedorConfig
                .getGestProveedor()
                .buscarProveedorPorCodigo(codigo);

        if (p != null) {

            JOptionPane.showMessageDialog(
                this,
                "Código: " + p.getCodigoProveedor() +
                "\nRazón social: " + p.getRazonSocial() +
                "\nNIT: " + p.getNit() +
                "\nDirección: " + p.getDireccionP() +
                "\nTeléfono: " + p.getTelefonoP() +
                "\nCorreo electrónico: " + p.getCorreoP()
            );
        }
    }

    public void buscarProveedor() {

        try {

            String input = JOptionPane.showInputDialog(
                this,
                "Ingrese código del proveedor:"
            );

            if (input == null || input.isEmpty()) return;

            int codigo = Integer.parseInt(input);

            Proveedor p = proveedorConfig
                    .getGestProveedor()
                    .buscarProveedorPorCodigo(codigo);

            if (p != null) {

                pProveedor.poblarTabla(java.util.List.of(p));

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Proveedor no encontrado"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Error: " + e.getMessage()
            );
        }
    }
    
    public void limpiarProveedor() {

        pProveedor.poblarTabla(
            proveedorConfig.getGestProveedor().leerProveedores()
        );
    }
    
    public void activarProveedor() {
    	int codigo = pProveedor.getItemSeleccionado();

        if (codigo == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un proveedor de la tabla");
            return;
        }

        try {
            
            Proveedor p = proveedorConfig.getGestProveedor().buscarProveedorPorCodigo(codigo);
            
            if (p != null && p.getEstado() == EstadoEnum.ACTIVO) {
                JOptionPane.showMessageDialog(this, "El proveedor ya se encuentra activo.");
                return; // 
            }
            
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de activar el proveedor " + codigo + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
                );

                if (respuesta == JOptionPane.YES_OPTION) {

                    proveedorConfig.getGestProveedor().activarProveedor(codigo);

                    pProveedor.poblarTabla(
                        proveedorConfig.getGestProveedor().leerProveedores()
                    );
                }
                JOptionPane.showMessageDialog(this, "Proveedor activado exitosamente.");

            
           

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ===================== CLIENTE =====================

    public void lanzarDialogoCliente() {

        nuevoCliente = new DialogoCliente(
            evento,
            "Crear Cliente",
            true
        );

        nuevoCliente.setSize(400,400);
        nuevoCliente.setLocationRelativeTo(null);
        nuevoCliente.setVisible(true);
    }

    public void cerrarDialogoCliente() {

        if(nuevoCliente != null){

            nuevoCliente.setVisible(false);
            nuevoCliente = null;
        }
    }

    public void crearCliente() {

        try {

            clienteConfig.getGestCliente().agregarCliente(
                nuevoCliente.capturarDatos()
            );

            cerrarDialogoCliente();

            pCliente.poblarTabla(
                clienteConfig.getGestCliente().leerClientes()
            );

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
    }

    public void lanzarDialogoModificarCliente() {

        int codigo = pCliente.getItemSeleccionado();

        if(codigo == -1) return;

        Cliente c = clienteConfig
                .getGestCliente()
                .buscarClientePorCodigo(codigo);

        nuevoCliente = new DialogoCliente(
            evento,
            "Modificar Cliente",
            false
        );

        nuevoCliente.cargarDatos(c);

        nuevoCliente.setSize(400,400);
        nuevoCliente.setLocationRelativeTo(null);
        nuevoCliente.setVisible(true);
    }

    public void modificarCliente() {

        try {

            Cliente c = nuevoCliente.capturarDatos();

            clienteConfig.getGestCliente().modificarCliente(c);

            cerrarDialogoCliente();

            pCliente.poblarTabla(
                clienteConfig.getGestCliente().leerClientes()
            );

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
    }

    public void eliminarCliente() {

        int codigo = pCliente.getItemSeleccionado();

        if (codigo == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un cliente de la tabla");
            return;
        }

        try {

            Cliente c = clienteConfig.getGestCliente().buscarClientePorCodigo(codigo);

            if (c == null) {
                JOptionPane.showMessageDialog(this,
                    "Cliente no encontrado");
                return;
            }

            if (c.getEstado() == EstadoEnum.INACTIVO) {
                JOptionPane.showMessageDialog(this,
                    "El cliente ya está inactivo");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de inactivar el cliente " + codigo + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (respuesta == JOptionPane.YES_OPTION) {

                // 🔴 NO BORRAMOS, SOLO INACTIVAMOS (como proveedor)
                c.setEstado(EstadoEnum.INACTIVO);

                clienteConfig.getGestCliente().modificarCliente(c);

                pCliente.poblarTabla(
                    clienteConfig.getGestCliente().leerClientes()
                );

                JOptionPane.showMessageDialog(this,
                    "Cliente inactivado exitosamente.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void buscarCliente() {

        try {

            String input = JOptionPane.showInputDialog(
                this,
                "Ingrese código del cliente:"
            );

            if (input == null || input.isEmpty()) return;

            int codigo = Integer.parseInt(input);

            Cliente c = clienteConfig
                    .getGestCliente()
                    .buscarClientePorCodigo(codigo);

            if (c != null) {

                pCliente.poblarTabla(java.util.List.of(c));

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Cliente no encontrado"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Error: " + e.getMessage()
            );
        }
    }

    public void verCliente() {

        int codigo = pCliente.getItemSeleccionado();

        if (codigo == -1) return;

        Cliente c = clienteConfig
                .getGestCliente()
                .buscarClientePorCodigo(codigo);

        if (c != null) {

            JOptionPane.showMessageDialog(
                this,
                "Código: " + c.getIdCliente() +
                "\nNombre: " + c.getNombreCompleto() +
                "\nDirección: " + c.getDireccionC() +
                "\nTeléfono: " + c.getTelefonoC()
            );
        }
    }
        public void limpiarCliente() {

            pCliente.poblarTabla(
                clienteConfig.getGestCliente().leerClientes()
            );
        }
        public void activarCliente() {

            int codigo = pCliente.getItemSeleccionado();

            if (codigo == -1) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un cliente de la tabla");
                return;
            }

            try {

                Cliente c = clienteConfig.getGestCliente().buscarClientePorCodigo(codigo);

                if (c != null && c.getEstado() == EstadoEnum.ACTIVO) {
                    JOptionPane.showMessageDialog(this,
                        "El cliente ya se encuentra activo.");
                    return;
                }

                int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de activar el cliente " + codigo + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
                );

                if (respuesta == JOptionPane.YES_OPTION) {

                    clienteConfig.getGestCliente().activarCliente(codigo);

                    pCliente.poblarTabla(
                        clienteConfig.getGestCliente().leerClientes()
                    );
                }

                JOptionPane.showMessageDialog(this,
                    "Cliente activado exitosamente.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
      

    // ===================== PRODUCTO =====================

    public void lanzarDialogoProducto() {

        nuevoProducto = new DialogoProducto(
            evento,
            "Crear Producto",
            true
        );

        nuevoProducto.setSize(400,400);
        nuevoProducto.setLocationRelativeTo(null);
        nuevoProducto.setVisible(true);
    }

    public void cerrarDialogoProducto() {

        if(nuevoProducto != null){

            nuevoProducto.setVisible(false);
            nuevoProducto = null;
        }
    }

    public void crearProducto() {

        productoConfig.getGestProducto().guardar(
            nuevoProducto.capturarDatos()
        );

        cerrarDialogoProducto();

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void lanzarDialogoModificarProducto() {

        int codigo = pProducto.getItemSeleccionado();

        if(codigo == -1) return;

        Producto p = productoConfig
                .getGestProducto()
                .buscar(codigo);

        nuevoProducto = new DialogoProducto(
            evento,
            "Modificar Producto",
            false
        );

        nuevoProducto.cargarDatos(p);

        nuevoProducto.setSize(400,400);
        nuevoProducto.setLocationRelativeTo(null);
        nuevoProducto.setVisible(true);
    }

    public void modificarProducto() {

        Producto p = nuevoProducto.capturarDatos();

        productoConfig.getGestProducto().actualizar(p);

        cerrarDialogoProducto();

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void inactivarProducto() {

        int codigo = pProducto.getItemSeleccionado();

        if(codigo == -1) return;

        productoConfig.getGestProducto().inactivar(codigo);

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void activarProducto() {

        int codigo = pProducto.getItemSeleccionado();

        if(codigo == -1) return;

        productoConfig.getGestProducto().activar(codigo);

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void buscarProducto() {

        try {

            String input = JOptionPane.showInputDialog(
                this,
                "Ingrese código del producto:"
            );

            if (input == null || input.isEmpty()) return;

            int codigo = Integer.parseInt(input);

            Producto p = productoConfig
                    .getGestProducto()
                    .buscar(codigo);

            if (p != null) {

                pProducto.poblarTabla(java.util.List.of(p));

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "Producto no encontrado"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Error: " + e.getMessage()
            );
        }
    }

    public void limpiarProducto() {

        pProducto.poblarTabla(
            productoConfig.getGestProducto().listar()
        );
    }

    public void verProducto() {

        int codigo = pProducto.getItemSeleccionado();

        if (codigo == -1) return;

        Producto p = productoConfig
                .getGestProducto()
                .buscar(codigo);

        if (p != null) {

            JOptionPane.showMessageDialog(
                this,
                "Código: " + p.getCodigoProducto() +
                "\nNombre: " + p.getNombreProducto() +
                "\nCategoría: " + p.getCategoria() +
                "\nPrecio Compra: " + p.getPrecioCompra() +
                "\nPrecio Venta: " + p.getPrecioVenta() +
                "\nStock: " + p.getStockActual()
            );
        }
    }
    
    //VENTA
    
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
        double iva = pVenta.getIvaProductoSeleccionado(); // ← agregar
        double impuestos = cantidad*precio*pVenta.getIvaProductoSeleccionado();
        double subtotal = (precio * cantidad) + impuestos;

        int nuevoStock = stock - cantidad;

        pVenta.agregarFilaDetalle(nombre, cantidad, precio,impuestos, subtotal);
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

        // Devolver stock en la tabla de productos
        pVenta.devolverStockTabla(
            detalle.getProducto().getCodigoProducto(),
            detalle.getCantidad()
        );

        // Quitar de la tabla visual y de la lista
        pVenta.quitarFilaDetalle(fila);
        listaDetalle.remove(fila);

        // Recalcular total
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

            List<Producto> productos = productoConfig.getGestProducto().listar();

            // 1. Guardar venta (negocio asigna número y fecha)
            ventaConfig.getGestVenta().guardarVenta(venta);

            // 2. Inventario registra la salida y actualiza stock
            inventarioConfig.getGestInventario()
                    .registrarSalidaPorVenta(venta, productos);

            // 3. Persistir productos con stock actualizado
            productoConfig.getGestProducto().guardarTodos(productos);
            
            new TxtFactura().generarFactura(venta);
            
            JOptionPane.showMessageDialog(this,
                    "Venta registrada.\nFactura: " + venta.getNumeroFactura());
            listaDetalle = new ArrayList<>();
            pVenta.limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    public void lanzarDialogoAnularVenta() {

        String facturaSeleccionada = pHistorial.getFacturaSeleccionada();

        if (facturaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta de la tabla");
            return;
        }

        // Verificar si ya está anulada antes de abrir el diálogo
        Venta venta = ventaConfig.getGestVenta().buscarPorFactura(facturaSeleccionada);
        if (venta != null && venta.getEstado() == EstadoVentaEnum.ANULADA) {
            JOptionPane.showMessageDialog(this, 
                "La venta " + facturaSeleccionada + " ya está anulada.");
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
            // Factura viene de la fila seleccionada en el historial
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

            // 1. Buscar la venta
            Venta venta = ventaConfig.getGestVenta().buscarPorFactura(factura);

            // 2. Anular
            ventaConfig.getGestVenta().anularVenta(factura, motivo);

            // 3. Devolver stock via inventario
            List<Producto> productos = productoConfig.getGestProducto().listar();
            inventarioConfig.getGestInventario()
                    .registrarEntradaPorAnulacion(venta, productos, motivo);

            // 4. Persistir stock
            productoConfig.getGestProducto().guardarTodos(productos);

            // 5. Refrescar historial
            pHistorial.refrescar(ventaConfig.getGestVenta().listarVentas());
            cerrarDialogoAnularVenta();

            JOptionPane.showMessageDialog(this, "Venta anulada correctamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
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
        int nuevoStock = stock + cantidad; // ← compra SUMA stock

        pCompra.agregarFilaDetalle(nombre, cantidad, precio, subtotal);
        pCompra.actualizarStockTabla(pCompra.getFilaProductoSeleccionada(), nuevoStock);

        Producto producto = new Producto();
        producto.setCodigoProducto(codigo);
        producto.setNombreProducto(nombre);
        producto.setPrecioCompra(precio);
        producto.setStockActual(nuevoStock);

        DetalleCompra detalle = new DetalleCompra(producto, cantidad);
        listaDetalleCompra.add(detalle);

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

            // La GUI construye el objeto — negocio asigna número y fecha
            Compra compra = new Compra();
            compra.setProveedor(proveedor);
            compra.setDetalles(listaDetalleCompra);

            List<Producto> productos = productoConfig.getGestProducto().listar();

            // 1. Guardar compra
            compraConfig.getGestion().guardarCompra(compra);

            // 2. Inventario registra entrada por cada producto
            for (DetalleCompra detalle : listaDetalleCompra) {
                inventarioConfig.getGestInventario()
                        .registrarEntradaPorCompra(
                                compra.getNumeroFactura(),
                                detalle.getProducto(),
                                detalle.getCantidad(),
                                productos);
            }

            // 3. Persistir stock actualizado
            productoConfig.getGestProducto().guardarTodos(productos);

            JOptionPane.showMessageDialog(this,
                    "Compra registrada.\nFactura: " + compra.getNumeroFactura());

            listaDetalleCompra = new ArrayList<>();
            pCompra.limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            e.printStackTrace();
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
            opciones[1] // botón por defecto: "No"
        );

        if (respuesta == 0) { // 0 = "Sí"
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
    
    
    
}