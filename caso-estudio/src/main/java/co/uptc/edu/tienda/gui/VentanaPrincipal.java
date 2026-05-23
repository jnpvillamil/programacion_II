package co.uptc.edu.tienda.gui;

import javax.swing.*;

import co.uptc.edu.co.tienda.configs.ClienteConfig;
import co.uptc.edu.co.tienda.configs.InventarioConfig;
import co.uptc.edu.co.tienda.configs.ProductoConfig;
import co.uptc.edu.co.tienda.configs.ProveedorConfig;
import co.uptc.edu.co.tienda.configs.SeguridadConfig;
import co.uptc.edu.co.tienda.configs.VentaConfig;
import co.uptc.edu.tienda.enums.EstadoEnum;
import co.uptc.edu.tienda.modelo.Cliente;
import co.uptc.edu.tienda.modelo.Producto;
import co.uptc.edu.tienda.modelo.Proveedor;
import co.uptc.edu.tienda.modelo.Venta;
import co.uptc.edu.tienda.modelo.DetalleVenta;
import co.uptc.edu.tienda.negocio.GestionSeguridad;
import co.uptc.edu.tienda.negocio.dto.CredencialDto;

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
    private List<DetalleVenta> listaDetalle = new ArrayList<>();

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

        seguridadConfig = new SeguridadConfig();

        proveedorConfig = new ProveedorConfig();
        productoConfig = new ProductoConfig();
        clienteConfig = new ClienteConfig();
        ventaConfig = new VentaConfig();
        inventarioConfig = new InventarioConfig();

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

                        JPanel panelBotones = new JPanel();
                        btnProveedor = new JButton("Proveedor");
                        btnProducto = new JButton("Producto");
                        btnCliente = new JButton("Cliente");

                        panelBotones.add(btnProveedor);
                        panelBotones.add(btnProducto);
                        panelBotones.add(btnCliente);

                        add(panelBotones, BorderLayout.NORTH);

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

                        btnProducto.addActionListener(e -> {
                            contenedor.removeAll();
                            contenedor.add(pProducto);
                            pProducto.poblarTabla(productoConfig.getGestProducto().listar());
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

                        this.setSize(900, 500);
                        repaint();
                        revalidate();
                        break;

                    case CAJERO:
                        remove(pLogin);

                        // Panel lateral izquierdo
                        JPanel panelLateral = new JPanel(new GridLayout(3, 1, 5, 5));
                        panelLateral.setPreferredSize(new Dimension(150, 0));
                        panelLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                        panelLateral.setBackground(new Color(52, 73, 94)); // azul oscuro

                        JButton btnNuevaVenta = new JButton("Nueva Venta");
                        JButton btnHistorial = new JButton("Historial Ventas");
                        JButton btnClientesCajero = new JButton("Clientes");

                        // Estilo botones laterales
                        for (JButton btn : new JButton[]{btnNuevaVenta, btnHistorial, btnClientesCajero}) {
                            btn.setBackground(new Color(52, 73, 94));
                            btn.setForeground(Color.WHITE);
                            btn.setFocusPainted(false);
                            btn.setBorderPainted(false);
                            btn.setFont(new Font("Arial", Font.BOLD, 13));
                        }

                        panelLateral.add(btnNuevaVenta);
                        panelLateral.add(btnHistorial);
                        panelLateral.add(btnClientesCajero);

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
                            // El cajero solo VE clientes, no los modifica
                            pCliente.poblarTabla(clienteConfig.getGestCliente().leerClientes());
                            contenedor.add(pCliente);
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

        try {
            if (pVenta.getTxtCantidad().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una cantidad");
                return;
            }

            int cantidad = pVenta.getCantidad();
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
            double subtotal = precio * cantidad;
            int nuevoStock = stock - cantidad;

            // Actualizar tabla UI
            pVenta.agregarFilaDetalle(nombre, cantidad, precio, subtotal);
            pVenta.actualizarStockTabla(pVenta.getFilaProductoSeleccionada(), nuevoStock);

            // Construir detalle para la lista
            Producto producto = new Producto();
            producto.setCodigoProducto(codigo);
            producto.setNombreProducto(nombre);
            producto.setPrecioVenta(precio);
            producto.setStockActual(nuevoStock);

            listaDetalle.add(new DetalleVenta(producto, cantidad));

            // Recalcular total en pantalla
            double total = 0;
            for (int i = 0; i < pVenta.getFilasDetalle(); i++) {
                total += pVenta.getSubtotalDetalle(i);
            }
            pVenta.actualizarTotal(total);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
        }
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

            JOptionPane.showMessageDialog(this,
                    "Venta registrada.\nFactura: " + venta.getNumeroFactura());
            listaDetalle = new ArrayList<>();
            pVenta.limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    public void lanzarDialogoAnularVenta() {
        dialogoAnular = new DialogoAnularVenta(evento);
        // Si hay una factura seleccionada en el historial, la precarga
        String facturaSeleccionada = pHistorial.getFacturaSeleccionada();
        if (facturaSeleccionada != null) {
            dialogoAnular.setFactura(facturaSeleccionada);
        }
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
                    .registrarEntradaPorAnulacion(venta, productos);

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
}