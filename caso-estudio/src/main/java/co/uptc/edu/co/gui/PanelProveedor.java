package co.uptc.edu.co.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import co.uptc.edu.co.modelo.Proveedor;
import co.uptc.edu.co.modelo.enums.EstadoEnum;

public class PanelProveedor extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Proveedores";
    private static final String TEXTO_TOTAL_INICIAL = "Total de proveedores: 0";
    private static final String TEXTO_TOTAL = "Total de proveedores: ";

    private static final String TEXTO_BOTON_CAMBIAR_ESTADO = "Cambiar Estado";
    private static final String TEXTO_BOTON_INACTIVAR = "Inactivar";
    private static final String TEXTO_BOTON_ACTIVAR = "Activar";

    private static final String[] COLUMNAS = {
            "Código",
            "Razón Social",
            "NIT",
            "Dirección",
            "Teléfono",
            "Correo Electrónico",
            "Estado"
    };

    private static final int COLUMNA_CODIGO = 0;
    private static final int COLUMNA_ESTADO = 6;

    private JButton botonNuevo;
    private JButton botonEditar;
    private JButton botonCambiarEstado;

    private JTextField campoBuscar;

    private List<Proveedor> proveedoresCargados;

    public PanelProveedor() {
        super();
        proveedoresCargados = new ArrayList<>();
        inicializarComponentesProveedor();
        configurarPanelProveedor();
        agregarComponentesProveedor();
        inicializarFiltros();
    }

    @Override
    protected String obtenerTituloPanel() {
        return TITULO_PANEL;
    }

    @Override
    protected String obtenerTextoTotalInicial() {
        return TEXTO_TOTAL_INICIAL;
    }

    @Override
    protected Object[] obtenerColumnas() {
        return COLUMNAS;
    }

    private void inicializarComponentesProveedor() {
        botonNuevo = new JButton("Nuevo");
        botonEditar = new JButton("Editar");
        botonCambiarEstado = new JButton(TEXTO_BOTON_CAMBIAR_ESTADO);

        campoBuscar = new JTextField(20);
    }

    private void configurarPanelProveedor() {
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarTextoBotonSegunSeleccion();
            }
        });

        configurarBotonBase(botonNuevo);
        configurarBotonBase(botonEditar);
        configurarBotonBase(botonCambiarEstado);
    }

    private void agregarComponentesProveedor() {
        panelBotones.add(botonNuevo);
        panelBotones.add(botonEditar);
        panelBotones.add(botonCambiarEstado);

        agregarFiltro("Buscar:", campoBuscar);
    }

    private void inicializarFiltros() {
        asignarFiltroTexto(campoBuscar, this::aplicarFiltros);
    }

    public void inicializarEventos(Evento evento) {
        botonNuevo.setActionCommand(Evento.CMD_NUEVO_PROVEEDOR);
        botonNuevo.addActionListener(evento);

        botonEditar.setActionCommand(Evento.CMD_EDITAR_PROVEEDOR);
        botonEditar.addActionListener(evento);

        botonCambiarEstado.setActionCommand(Evento.CMD_ESTADO_PROVEEDOR);
        botonCambiarEstado.addActionListener(evento);
    }

    public void cargarProveedores(List<Proveedor> proveedores) {
        proveedoresCargados = new ArrayList<>(proveedores);
        aplicarFiltros();
        restablecerTextoBotonEstado();
    }

    private void aplicarFiltros() {
        limpiarTabla();

        String textoBusqueda = campoBuscar.getText().trim().toLowerCase();

        int totalFiltrados = 0;

        for (Proveedor proveedor : proveedoresCargados) {
            boolean coincideBusqueda =
                    proveedor.getCodigoProveedor().toLowerCase().contains(textoBusqueda) ||
                    proveedor.getRazonSocial().toLowerCase().contains(textoBusqueda) ||
                    proveedor.getNit().toLowerCase().contains(textoBusqueda) ||
                    proveedor.getCorreoElectronico().toLowerCase().contains(textoBusqueda);

            if (coincideBusqueda) {
                Object[] fila = {
                        proveedor.getCodigoProveedor(),
                        proveedor.getRazonSocial(),
                        proveedor.getNit(),
                        proveedor.getDireccion(),
                        proveedor.getTelefono(),
                        proveedor.getCorreoElectronico(),
                        proveedor.getEstado().name()
                };

                modeloTabla.addRow(fila);
                totalFiltrados++;
            }
        }

        actualizarTextoTotal(TEXTO_TOTAL, totalFiltrados);
    }

    private void actualizarTextoBotonSegunSeleccion() {
        String estado = obtenerEstadoSeleccionado();
        actualizarTextoBotonEstado(estado);
    }

    public String obtenerEstadoSeleccionado() {
        return obtenerTextoSeleccionado(COLUMNA_ESTADO);
    }

    public void actualizarTextoBotonEstado(String estado) {
        if (estado == null) {
            botonCambiarEstado.setText(TEXTO_BOTON_CAMBIAR_ESTADO);
            return;
        }

        if (estado.equalsIgnoreCase(EstadoEnum.ACTIVO.name())) {
            botonCambiarEstado.setText(TEXTO_BOTON_INACTIVAR);
        } else {
            botonCambiarEstado.setText(TEXTO_BOTON_ACTIVAR);
        }
    }

    public void restablecerTextoBotonEstado() {
        botonCambiarEstado.setText(TEXTO_BOTON_CAMBIAR_ESTADO);
    }

    public String obtenerCodigoSeleccionado() {
        return obtenerTextoSeleccionado(COLUMNA_CODIGO);
    }
}