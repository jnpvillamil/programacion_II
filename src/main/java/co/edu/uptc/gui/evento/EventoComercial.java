package co.edu.uptc.gui.evento;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.interfaces.ManejadorEventoComercial;
import co.edu.uptc.modelo.Cliente;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.Producto;
import co.edu.uptc.modelo.Venta;
import co.edu.uptc.negocio.GestionCompra;
import co.edu.uptc.negocio.GestionVenta;
import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;

import co.edu.uptc.persistencia.ExcepcionAccesoDatos;
import co.edu.uptc.utilidades.UtilidadMensajeAccesoDatos;

import java.util.List;

/**
 * Intermediario unificado para operaciones comerciales de venta y compra.
 */
public class EventoComercial implements ManejadorEventoComercial {

    private final GestionVenta gestionVenta;
    private final GestionCompra gestionCompra;

    public EventoComercial(GestionVenta gestionVenta, GestionCompra gestionCompra) {
        this.gestionVenta = gestionVenta;
        this.gestionCompra = gestionCompra;
    }

    @Override
    public String realizarVenta(Venta venta) {
        try {
            return gestionVenta.realizarVenta(venta);
        } catch (IllegalStateException | IllegalArgumentException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion);
        }
    }

    @Override
    public String anularVenta(String numeroFactura) {
        try {
            return gestionVenta.anularVenta(numeroFactura);
        } catch (IllegalStateException | IllegalArgumentException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion);
        }
    }

    @Override
    public void calcularTotales(Venta venta) {
        gestionVenta.calcularTotales(venta);
    }

    @Override
    public String registrarCompra(Compra compra) {
        try {
            return gestionCompra.registrarCompra(compra);
        } catch (IllegalStateException | IllegalArgumentException excepcion) {
            return "Error: " + excepcion.getMessage();
        } catch (ExcepcionAccesoDatos excepcion) {
            return "Error: " + UtilidadMensajeAccesoDatos.mensajeGeneral(excepcion);
        }
    }

    @Override
    public Producto buscarProducto(String codigoInterno) {
        return gestionVenta.buscarProducto(codigoInterno);
    }

    @Override
    public Cliente buscarCliente(String identificacion) {
        return gestionVenta.buscarClientePorIdentificacion(identificacion);
    }

    @Override
    public List<VentaDTO> obtenerListadoVenta() {
        try {
            return gestionVenta.listarVenta();
        } catch (ExcepcionAccesoDatos excepcion) {
            throw excepcion;
        }
    }

    @Override
    public List<CompraDTO> obtenerListadoCompra() {
        try {
            return gestionCompra.listarCompra();
        } catch (ExcepcionAccesoDatos excepcion) {
            throw excepcion;
        }
    }
}
