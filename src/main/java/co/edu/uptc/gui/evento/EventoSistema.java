package co.edu.uptc.gui.evento;

import co.edu.uptc.dto.LoginDTO;
import co.edu.uptc.dto.MovimientoResumenDTO;
import co.edu.uptc.dto.ReporteFinancieroDTO;
import co.edu.uptc.dto.ReporteUtilidadDTO;
import co.edu.uptc.dto.UsuarioDTO;
import co.edu.uptc.interfaces.ManejadorEventoSistema;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.negocio.ExcepcionAutenticacion;
import co.edu.uptc.negocio.GestionContable;
import co.edu.uptc.negocio.ServicioAutenticacion;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;

import java.awt.CardLayout;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;

/**
 * Intermediario unificado para autenticación, navegación y consolidados contables.
 */
public class EventoSistema implements ManejadorEventoSistema {

    private final ServicioAutenticacion servicioAutenticacion;
    private final GestionContable gestionContable;
    private JPanel panelContenedor;
    private CardLayout cardLayout;

    public EventoSistema(ServicioAutenticacion servicioAutenticacion, GestionContable gestionContable) {
        this.servicioAutenticacion = servicioAutenticacion;
        this.gestionContable = gestionContable;
    }

    @Override
    public UsuarioDTO validarIngreso(LoginDTO loginDto) {
        try {
            servicioAutenticacion.iniciarSesion(loginDto.usuario(), loginDto.clave());
            return mapearDto(servicioAutenticacion.getUsuarioSesion());
        } catch (ExcepcionAutenticacion excepcion) {
            throw excepcion;
        }
    }

    @Override
    public UsuarioDTO obtenerUsuarioSesion() {
        Usuario usuarioSesion = servicioAutenticacion.getUsuarioSesion();
        return usuarioSesion != null ? mapearDto(usuarioSesion) : null;
    }

    @Override
    public void cerrarSesion() {
        servicioAutenticacion.cerrarSesion();
    }

    @Override
    public void configurarNavegacion(JPanel panelContenedor, CardLayout cardLayout) {
        this.panelContenedor = panelContenedor;
        this.cardLayout = cardLayout;
    }

    @Override
    public void mostrarPanel(String nombrePanel) {
        if (cardLayout != null && panelContenedor != null) {
            cardLayout.show(panelContenedor, nombrePanel);
        }
    }

    @Override
    public List<MovimientoResumenDTO> listarResumenMovimiento() {
        try {
            return gestionContable.listarResumenMovimiento();
        } catch (ExcepcionAccesoDatos excepcion) {
            throw new IllegalStateException(UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion), excepcion);
        }
    }

    @Override
    public List<ReporteUtilidadDTO> listarReporteUtilidad() {
        try {
            return gestionContable.listarReporteUtilidad();
        } catch (ExcepcionAccesoDatos excepcion) {
            throw new IllegalStateException(UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion), excepcion);
        }
    }

    @Override
    public ReporteFinancieroDTO generarConsolidadoJson() {
        try {
            return gestionContable.generarReporteFinanciero();
        } catch (ExcepcionAccesoDatos | IllegalStateException excepcion) {
            throw new IllegalStateException(
                    excepcion instanceof ExcepcionAccesoDatos
                            ? UtilidadMensajeAccesoDatos.mensajeGeneral((ExcepcionAccesoDatos) excepcion)
                            : excepcion.getMessage(),
                    excepcion);
        }
    }

    @Override
    public ReporteFinancieroDTO leerReporteFinanciero() {
        try {
            return gestionContable.leerReporteFinanciero();
        } catch (IllegalStateException excepcion) {
            return new ReporteFinancieroDTO(
                    "",
                    new ReporteFinancieroDTO.ResumenPeriodoVentaDTO(0, 0, 0),
                    0,
                    Collections.emptyList(),
                    Collections.emptyList(),
                    new ReporteFinancieroDTO.BalanceResumenDTO(0, 0, 0));
        }
    }

    private UsuarioDTO mapearDto(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getNombre() + " " + usuario.getApellido(),
                usuario.getUsuario(),
                usuario.obtenerRol());
    }
}
