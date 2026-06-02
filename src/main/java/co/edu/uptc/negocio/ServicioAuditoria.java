package co.edu.uptc.negocio;

import co.edu.uptc.interfaces.RegistradorAuditoria;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.Usuario;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.utilidades.ManejadorFechas;

import java.time.LocalDateTime;

public class ServicioAuditoria {

    private static final String RUTA_LOG_VENTA = "logs/auditoria_venta.txt";
    private static final String RUTA_LOG_COMPRA = "logs/auditoria_compra.txt";
    private static final String RUTA_LOG_ACCESO = "logs/auditoria_acceso.txt";
    private static final String DETALLE_INGRESO_FALLIDO = "Contraseña incorrecta o usuario no existe";

    private final RegistradorAuditoria registradorAuditoria;

    public ServicioAuditoria(RegistradorAuditoria registradorAuditoria) {
        this.registradorAuditoria = registradorAuditoria;
    }

    public void registrarVenta(Venta venta, String loginOperador) {
        String marcaTiempo = ManejadorFechas.formatearFecha(LocalDateTime.now());
        String linea = String.format(
                "[%s] | ACCIÓN: REGISTRO_VENTA | FACTURA: %s | CLIENTE: %s | TOTAL: $%.2f | USUARIO: %s",
                marcaTiempo,
                venta.getNumeroFactura(),
                venta.getCliente().getIdentificacion(),
                venta.getTotal(),
                loginOperador);
        registradorAuditoria.registrar(linea, RUTA_LOG_VENTA);
    }

    public void anularVenta(Venta venta, String loginOperador) {
        String marcaTiempo = ManejadorFechas.formatearFecha(LocalDateTime.now());
        String linea = String.format(
                "[%s] | ACCIÓN: ANULACIÓN_VENTA | FACTURA: %s | MOTIVO: Anulación del sistema | USUARIO: %s",
                marcaTiempo,
                venta.getNumeroFactura(),
                loginOperador);
        registradorAuditoria.registrar(linea, RUTA_LOG_VENTA);
    }

    public void registrarCompra(Compra compra, String loginOperador) {
        String marcaTiempo = ManejadorFechas.formatearFecha(LocalDateTime.now());
        String linea = String.format(
                "[%s] | ACCIÓN: REGISTRO_COMPRA | FACTURA_PROV: %s | PROVEEDOR: %s | TOTAL: $%.2f | USUARIO: %s",
                marcaTiempo,
                compra.getNumeroFacturaProveedor(),
                compra.getProveedor().getNit(),
                compra.getTotal(),
                loginOperador);
        registradorAuditoria.registrar(linea, RUTA_LOG_COMPRA);
    }

    public void registrarIngresoExitoso(Usuario usuario) {
        String marcaTiempo = ManejadorFechas.formatearFecha(LocalDateTime.now());
        String linea = String.format(
                "[%s] | ACCIÓN: INGRESO_EXITOSO | USUARIO: %s | ROL: %s",
                marcaTiempo,
                usuario.getUsuario(),
                usuario.obtenerRol());
        registradorAuditoria.registrar(linea, RUTA_LOG_ACCESO);
    }

    public void registrarIngresoFallido(String loginIntentado) {
        String marcaTiempo = ManejadorFechas.formatearFecha(LocalDateTime.now());
        String login = loginIntentado != null && !loginIntentado.isBlank() ? loginIntentado : "SIN_USUARIO";
        String linea = String.format(
                "[%s] | ACCIÓN: INGRESO_FALLIDO | DETALLE: %s | USUARIO: %s",
                marcaTiempo,
                DETALLE_INGRESO_FALLIDO,
                login);
        registradorAuditoria.registrar(linea, RUTA_LOG_ACCESO);
    }
}
