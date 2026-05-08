package co.edu.uptc.sistienda.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.sistienda.modelo.Cliente;
import co.edu.uptc.sistienda.modelo.DetalleVenta;
import co.edu.uptc.sistienda.modelo.Producto;
import co.edu.uptc.sistienda.modelo.Venta;
import co.edu.uptc.sistienda.modelo.enums.FormaPagoEnum;
import co.edu.uptc.sistienda.ventas.gui.PanelRegistrarVenta;
import co.edu.uptc.sistienda.ventas.gui.PanelVentasRegistradas;

public class PanelCajero extends JPanel {

    public static final String TARJETA_REGISTRAR   = "CAJERO_REGISTRAR";
    public static final String TARJETA_REGISTRADAS = "CAJERO_REGISTRADAS";

    private PanelRegistrarVenta    panelRegistrarVenta;
    private PanelVentasRegistradas panelVentasRegistradas;
    private CardLayout             navegadorDeTarjetas;
    private JPanel                 contenedorDeTarjetas;

    public PanelCajero(Evento evento) {
        setLayout(new BorderLayout());
        add(construirMenuLateral(evento), BorderLayout.WEST);

        navegadorDeTarjetas  = new CardLayout();
        contenedorDeTarjetas = new JPanel(navegadorDeTarjetas);

        panelRegistrarVenta    = new PanelRegistrarVenta(evento);
        panelVentasRegistradas = new PanelVentasRegistradas(evento);

        contenedorDeTarjetas.add(panelRegistrarVenta,    TARJETA_REGISTRAR);
        contenedorDeTarjetas.add(panelVentasRegistradas, TARJETA_REGISTRADAS);
        
        add(contenedorDeTarjetas, BorderLayout.CENTER);
    }

    private JPanel construirMenuLateral(Evento evento) {
        JPanel menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setPreferredSize(new Dimension(160, 0));
        menuLateral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        menuLateral.add(crearTituloSeccion("VENTAS"));
        menuLateral.add(crearBotonMenu("Registrar Venta",   Evento.MENU_REGISTRAR_VENTA,      evento));
        menuLateral.add(crearBotonMenu("Ventas del día",    Evento.MENU_VENTAS_REGISTRADAS,    evento));

        menuLateral.add(crearTituloSeccion("CONSULTAS"));

        return menuLateral;
    }

    private JLabel crearTituloSeccion(String textoTitulo) {
        JLabel tituloSeccion = new JLabel(textoTitulo);
        tituloSeccion.setBorder(BorderFactory.createEmptyBorder(10, 8, 2, 8));
        tituloSeccion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return tituloSeccion;
    }

    private JButton crearBotonMenu(String textoBoton, String accionAsociada, Evento evento) {
        JButton botonMenu = new JButton(textoBoton);
        botonMenu.setActionCommand(accionAsociada);
        botonMenu.addActionListener(evento);
        botonMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        botonMenu.setHorizontalAlignment(JButton.LEFT);
        botonMenu.setBorderPainted(false);
        return botonMenu;
    }

    // Navegación entre tarjetas
    public void mostrarRegistrarVenta(String numeroVenta, String fechaVenta, List<Cliente> clientesDisponibles) {
        panelRegistrarVenta.iniciarNuevaVenta(numeroVenta, fechaVenta);
        panelRegistrarVenta.poblarComboCliente(clientesDisponibles);
        navegadorDeTarjetas.show(contenedorDeTarjetas, TARJETA_REGISTRAR);
    }

    public void mostrarVentasRegistradas(List<Venta> ventasDelDia) {
        panelVentasRegistradas.poblarTabla(ventasDelDia);
        navegadorDeTarjetas.show(contenedorDeTarjetas, TARJETA_REGISTRADAS);
    }

    
    
    //Delegados hacia VentanaPrincipal
    public String getNumeroFactura(){
    	return panelRegistrarVenta.getNumeroFactura();
    }
    
    public String getCodigoClienteSeleccionado(){
    	return panelRegistrarVenta.getCodigoClienteSeleccionado();
    }
    
    public FormaPagoEnum getFormaPagoSeleccionada(){
    	return panelRegistrarVenta.getFormaPagoSeleccionada();
    }
    
    public List<DetalleVenta> getProductosAgregados(){
    	return panelRegistrarVenta.getProductosAgregados();
    }
    
    public void agregarProductoALaVenta(DetalleVenta detalleNuevoProducto) {
    	panelRegistrarVenta.agregarProductoALaVenta(detalleNuevoProducto);
    }
    
    public String obtenerNumeroFacturaSeleccionada() {
    	return panelVentasRegistradas.obtenerNumeroFacturaSeleccionada();
    }
    
    public java.time.LocalDate obtenerFechaBusqueda(){
    	return panelVentasRegistradas.obtenerFechaDeBusqueda();
    }
    public void poblarTablaVentas(List<Venta> ventas) {
    	panelVentasRegistradas.poblarTabla(ventas);
    }
}
