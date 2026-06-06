package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.RegistradorAuditoria;
import co.edu.uptc.persistencia.PersistenciaAdministracion;
import co.edu.uptc.persistencia.PersistenciaBodeguero;
import co.edu.uptc.persistencia.PersistenciaCliente;
import co.edu.uptc.persistencia.PersistenciaComercial;
import co.edu.uptc.persistencia.PersistenciaCompra;
import co.edu.uptc.persistencia.PersistenciaContable;
import co.edu.uptc.persistencia.PersistenciaProducto;
import co.edu.uptc.persistencia.PersistenciaProveedor;
import co.edu.uptc.persistencia.PersistenciaUsuario;
import co.edu.uptc.persistencia.PersistenciaVenta;
import co.edu.uptc.utilidades.RegistradorAuditoriaArchivo;

public class AppConfig {

    private static AppConfig instancia;

    private final PersistenciaCliente persistenciaCliente;
    private final PersistenciaProveedor persistenciaProveedor;
    private final PersistenciaProducto persistenciaProducto;
    private final PersistenciaUsuario persistenciaUsuario;
    private final PersistenciaVenta persistenciaVenta;
    private final PersistenciaCompra persistenciaCompra;
    private final PersistenciaContable persistenciaContable;
    private final PersistenciaBodeguero persistenciaBodeguero;
    private final PersistenciaAdministracion persistenciaAdministracion;
    private final PersistenciaComercial persistenciaComercial;

    private final GestionCliente gestionCliente;
    private final GestionProveedor gestionProveedor;
    private final GestionProducto gestionProducto;
    private final GestionUsuario gestionUsuario;
    private final GestionContable gestionContable;
    private final ServicioAuditoria servicioAuditoria;
    private final ServicioAutenticacion servicioAutenticacion;
    private final GestionVenta gestionVenta;
    private final GestionCompra gestionCompra;
    private final GestionBodeguero gestionBodeguero;

    private AppConfig() {
        persistenciaCliente = new PersistenciaCliente();
        persistenciaProveedor = new PersistenciaProveedor();
        persistenciaProducto = new PersistenciaProducto();
        persistenciaUsuario = new PersistenciaUsuario();
        persistenciaVenta = new PersistenciaVenta();
        persistenciaCompra = new PersistenciaCompra();
        persistenciaContable = new PersistenciaContable();
        persistenciaBodeguero = new PersistenciaBodeguero();

        persistenciaAdministracion = new PersistenciaAdministracion(
                persistenciaCliente,
                persistenciaProveedor,
                persistenciaProducto,
                persistenciaUsuario);
        persistenciaComercial = new PersistenciaComercial(
                persistenciaVenta,
                persistenciaCompra,
                persistenciaContable);

        gestionCliente = new GestionCliente(persistenciaAdministracion);
        gestionProveedor = new GestionProveedor(persistenciaAdministracion);
        gestionProducto = new GestionProducto(persistenciaAdministracion);
        gestionUsuario = new GestionUsuario(persistenciaAdministracion);
        gestionContable = new GestionContable(persistenciaContable);

        RegistradorAuditoria registradorAuditoria = new RegistradorAuditoriaArchivo();
        servicioAuditoria = new ServicioAuditoria(registradorAuditoria);
        servicioAutenticacion = new ServicioAutenticacion(gestionUsuario, servicioAuditoria);

        gestionVenta = new GestionVenta(
                persistenciaComercial,
                gestionProducto,
                gestionContable,
                gestionCliente,
                servicioAuditoria,
                servicioAutenticacion);
        gestionCompra = new GestionCompra(
                persistenciaComercial,
                gestionProducto,
                gestionContable,
                gestionProveedor,
                servicioAuditoria,
                servicioAutenticacion);
        gestionBodeguero = new GestionBodeguero(persistenciaBodeguero);
        
    }

    public static synchronized AppConfig getInstancia() {
        if (instancia == null) {
            instancia = new AppConfig();
        }
        return instancia;
    }

    public GestionCliente getGestionCliente() {
        return gestionCliente;
    }

    public GestionProveedor getGestionProveedor() {
        return gestionProveedor;
    }

    public GestionProducto getGestionProducto() {
        return gestionProducto;
    }

    public GestionUsuario getGestionUsuario() {
        return gestionUsuario;
    }

    public GestionContable getGestionContable() {
        return gestionContable;
    }

    public ServicioAuditoria getServicioAuditoria() {
        return servicioAuditoria;
    }

    public ServicioAutenticacion getServicioAutenticacion() {
        return servicioAutenticacion;
    }

    public GestionVenta getGestionVenta() {
        return gestionVenta;
    }

    public GestionCompra getGestionCompra() {
        return gestionCompra;
    }
    public GestionBodeguero getGestionBodeguero() {
        return gestionBodeguero;
    }
}
