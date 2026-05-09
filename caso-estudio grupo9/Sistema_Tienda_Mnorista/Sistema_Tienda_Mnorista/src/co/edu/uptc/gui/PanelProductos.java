package co.edu.uptc.gui;

import javax.swing.*;
import java.awt.GridLayout;

public class PanelProductos extends JPanel {

    JTextField textoDelCodigo;
    JTextField textoDelNombre;
    JTextField textoCategoria;
    JTextField textoPrecioCompra;
    JTextField textoPrecioVenta;
    JTextField textoStockActual;
    JTextField textoStockMinimo;
    JTextField textoMostrarInfo;
    JTextArea areaListaProductos;

    JButton btnBorrar;
    JButton btnRegistrar;
    JButton btnActualizarProducto;
    JButton btnActualizarPrecios;
    JButton btnControlarStock;

    public PanelProductos() {
        inicio();
        cargarDatos();
    }

    public void inicio() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panelSuperior = new JPanel();
        JPanel panelCampos = new JPanel(new GridLayout(0, 1));

        textoDelCodigo = new JTextField(15);
        textoDelNombre = new JTextField(15);
        textoCategoria = new JTextField(15);
        textoPrecioCompra = new JTextField(15);
        textoPrecioVenta = new JTextField(15);
        textoStockActual = new JTextField(15);
        textoStockMinimo = new JTextField(15);
        textoMostrarInfo = new JTextField(30);

        panelCampos.add(new JLabel("Nombre del producto"));
        panelCampos.add(textoDelNombre);
        panelCampos.add(new JLabel("Categoria"));
        panelCampos.add(textoCategoria);
        panelCampos.add(new JLabel("Precio de compra"));
        panelCampos.add(textoPrecioCompra);
        panelCampos.add(new JLabel("Precio de venta"));
        panelCampos.add(textoPrecioVenta);
        panelCampos.add(new JLabel("Stock actual"));
        panelCampos.add(textoStockActual);
        panelCampos.add(new JLabel("Stock minimo"));
        panelCampos.add(textoStockMinimo);

        btnBorrar = new JButton("BORRAR");
        btnRegistrar = new JButton("REGISTRAR");

        btnBorrar.setActionCommand("BorrarProducto");
        btnRegistrar.setActionCommand("RegistrarProducto");

        JPanel panelBotonesBR = new JPanel();
        panelBotonesBR.add(btnBorrar);
        panelBotonesBR.add(btnRegistrar);
        panelCampos.add(panelBotonesBR);

        JPanel panelBotonesAccion = new JPanel(new GridLayout(0, 1));
        btnActualizarProducto = new JButton("Actualizar producto");
        btnActualizarPrecios = new JButton("Actualizar precios de venta y costo.");
        btnControlarStock = new JButton("Controlar stock minimo y maximo.");

        btnActualizarProducto.setActionCommand("ActualizarProducto");
        btnActualizarPrecios.setActionCommand("ActualizarPrecios");
        btnControlarStock.setActionCommand("ControlarStock");

        panelBotonesAccion.add(btnActualizarProducto);
        panelBotonesAccion.add(btnActualizarPrecios);
        panelBotonesAccion.add(btnControlarStock);

        panelSuperior.add(panelCampos);
        panelSuperior.add(panelBotonesAccion);

        JPanel panelCabeceraTabla = new JPanel(new GridLayout(1, 6));
        panelCabeceraTabla.add(new JLabel("Nombre del producto"));
        panelCabeceraTabla.add(new JLabel("Categoria"));
        panelCabeceraTabla.add(new JLabel("Precio de compra"));
        panelCabeceraTabla.add(new JLabel("Precio de venta"));
        panelCabeceraTabla.add(new JLabel("Stock"));
        panelCabeceraTabla.add(new JLabel("ENTRADA/SALIDA"));

        // Crear el area de texto
        areaListaProductos = new JTextArea(4, 50);

        add(panelSuperior);
        add(panelCabeceraTabla);
        add(new JScrollPane(areaListaProductos));
        add(textoMostrarInfo);
    }

    public void cargarDatos() {
        String codigo1 = "001";
        String nombre1 = "Arroz Diana";
        String categoria1 = "Granos";
        int precioCompra1 = 1000;
        int precioVenta1 = 2000;
        int stock1 = 20;

        String codigo2 = "002";
        String nombre2 = "Leche";
        String categoria2 = "Lacteos";
        int precioCompra2 = 1500;
        int precioVenta2 = 2500;
        int stock2 = 15;

        String texto = "";
        texto = texto + nombre1 + "   " + categoria1 + "   " + precioCompra1 + "UN" + "   " + precioVenta1 + "UN" + "   " + stock1 + "   " + "ENTRADA" + "\n";
        texto = texto + nombre2 + "   " + categoria2 + "   " + precioCompra2 + "UN" + "   " + precioVenta2 + "UN" + "   " + stock2 + "   " + "ENTRADA" + "\n";

        areaListaProductos.setText(texto);

        String infoResumen = "";
        infoResumen = infoResumen + "Productos: " + codigo1 + " " + nombre1 + " | ";
        infoResumen = infoResumen + codigo2 + " " + nombre2;
        textoMostrarInfo.setText(infoResumen);
    }

    public void asignarEventos(Evento evento) {
        btnBorrar.addActionListener(evento);
        btnRegistrar.addActionListener(evento);
        btnActualizarProducto.addActionListener(evento);
        btnActualizarPrecios.addActionListener(evento);
        btnControlarStock.addActionListener(evento);
    }

    class Producto {
        String codigo;
        String nombre;
        String categoria;
        int precioCompra;
        int precioVenta;
        int stock;

        public Producto(String codigo, String nombre, String categoria, int precioCompra, int precioVenta, int stock) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.categoria = categoria;
            this.precioCompra = precioCompra;
            this.precioVenta = precioVenta;
            this.stock = stock;
        }
    }
}
