package co.uptc.edu.co.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import co.uptc.edu.co.modelo.Cliente;
import co.uptc.edu.co.modelo.enums.EstadoEnum;
import co.uptc.edu.co.modelo.enums.TipoClienteEnum;

public class PanelCliente extends PanelCentral {

    private static final String TITULO_PANEL = "Gestión de Clientes";
    private static final String TEXTO_TOTAL_INICIAL = "Total de clientes: 0";
    private static final String TEXTO_TOTAL = "Total de clientes: ";

    private static final String TEXTO_BOTON_CAMBIAR_ESTADO = "Cambiar Estado";
    private static final String TEXTO_BOTON_INACTIVAR = "Inactivar";
    private static final String TEXTO_BOTON_ACTIVAR = "Activar";

    private static final String OPCION_TODOS = "Todos";

    private static final Object[] COLUMNAS = {
            "Código",
            "Nombre/Razón Social",
            "Tipo ID",
            "N° Identificación",
            "Dirección",
            "Teléfono",
            "Tipo Cliente",
            "Estado"
    };

    private static final int COLUMNA_CODIGO = 0;
    private static final int COLUMNA_ESTADO = 7;

    private JButton botonNuevo;
    private JButton botonEditar;
    private JButton botonCambiarEstado;
    private JButton botonHistorial;

    private JTextField campoBuscar;
    private JComboBox<String> comboTipo;

    private List<Cliente> clientesCargados;

    public PanelCliente() {
        super();
        clientesCargados = new ArrayList<>();
        inicializarComponentesCliente();
        configurarPanelCliente();
        agregarComponentesCliente();
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

    private void inicializarComponentesCliente() {
        botonNuevo = new JButton("Nuevo");
        botonEditar = new JButton("Editar");
        botonCambiarEstado = new JButton(TEXTO_BOTON_CAMBIAR_ESTADO);
        botonHistorial = new JButton("Ver Historial");

        campoBuscar = new JTextField(20);

        comboTipo = new JComboBox<>();
        comboTipo.addItem(OPCION_TODOS);
        comboTipo.addItem(TipoClienteEnum.MAYORISTA.toString());
        comboTipo.addItem(TipoClienteEnum.MINORISTA.toString());
    }

    private void configurarPanelCliente() {
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarTextoBotonSegunSeleccion();
            }
        });

        configurarBotonBase(botonNuevo);
        configurarBotonBase(botonEditar);
        configurarBotonBase(botonCambiarEstado);
        configurarBotonBase(botonHistorial);
    }

    private void agregarComponentesCliente() {
        panelBotones.add(botonNuevo);
        panelBotones.add(botonEditar);
        panelBotones.add(botonCambiarEstado);
        panelBotones.add(botonHistorial);

        agregarFiltro("Buscar:", campoBuscar);
        agregarFiltro("Tipo:", comboTipo);
    }
    
    private void inicializarFiltros() {
        asignarFiltroTexto(campoBuscar, this::aplicarFiltros);
        asignarFiltroCombo(comboTipo, this::aplicarFiltros);
    }

    public void inicializarEventos(Evento evento) {
        botonNuevo.setActionCommand(Evento.CMD_NUEVO_CLIENTE);
        botonNuevo.addActionListener(evento);

        botonEditar.setActionCommand(Evento.CMD_EDITAR_CLIENTE);
        botonEditar.addActionListener(evento);

        botonCambiarEstado.setActionCommand(Evento.CMD_ESTADO_CLIENTE);
        botonCambiarEstado.addActionListener(evento);

        botonHistorial.setActionCommand(Evento.CMD_HISTORIAL_CLIENTE);
        botonHistorial.addActionListener(evento);
    }

    public void cargarClientes(List<Cliente> clientes) {
        clientesCargados = new ArrayList<>(clientes);
        aplicarFiltros();
        restablecerTextoBotonEstado();
    }

    private void aplicarFiltros() {
        limpiarTabla();

        String textoBusqueda = campoBuscar.getText().trim().toLowerCase();
        String tipoSeleccionado = comboTipo.getSelectedItem().toString();

        int totalFiltrados = 0;

        for (Cliente cliente : clientesCargados) {
            boolean coincideBusqueda =
                    cliente.getCodigo().toLowerCase().contains(textoBusqueda) ||
                    cliente.getNombre().toLowerCase().contains(textoBusqueda) ||
                    cliente.getNumeroIdentificacion().toLowerCase().contains(textoBusqueda);

            boolean coincideTipo =
                    tipoSeleccionado.equals(OPCION_TODOS) ||
                    cliente.getTipoCliente().name().equalsIgnoreCase(tipoSeleccionado);

            if (coincideBusqueda && coincideTipo) {
                Object[] fila = {
                        cliente.getCodigo(),
                        cliente.getNombre(),
                        cliente.getTipoIdentificacion().name(),
                        cliente.getNumeroIdentificacion(),
                        cliente.getDireccion(),
                        cliente.getTelefono(),
                        cliente.getTipoCliente().name(),
                        cliente.getEstado().name()
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