package co.edu.uptc.persistencia;

import co.edu.uptc.dto.CompraDTO;
import co.edu.uptc.dto.VentaDTO;
import co.edu.uptc.interfaces.RepositorioComercial;
import co.edu.uptc.modelo.Compra;
import co.edu.uptc.modelo.MovimientoContable;
import co.edu.uptc.modelo.Venta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class PersistenciaComercial implements RepositorioComercial {

    private final PersistenciaVenta persistenciaVenta;
    private final PersistenciaCompra persistenciaCompra;
    private final PersistenciaContable persistenciaContable;

    public PersistenciaComercial(PersistenciaVenta persistenciaVenta,
                                 PersistenciaCompra persistenciaCompra,
                                 PersistenciaContable persistenciaContable) {
        this.persistenciaVenta = persistenciaVenta;
        this.persistenciaCompra = persistenciaCompra;
        this.persistenciaContable = persistenciaContable;
    }

    @Override
    public void guardarVenta(Venta venta, Function<Venta, List<MovimientoContable>> constructorAsiento) {
        Connection conexion = null;
        try {
            conexion = ConexionSql.getConexion();
            conexion.setAutoCommit(false);
            persistenciaVenta.guardarEnTransaccion(conexion, venta);
            persistirAsientoContable(conexion, constructorAsiento.apply(venta));
            conexion.commit();
        } catch (SQLException excepcion) {
            revertirTransaccion(conexion);
            throw ExcepcionAccesoDatos.desde(excepcion);
        } finally {
            restablecerConexion(conexion);
        }
    }

    @Override
    public void anularVenta(String numeroFactura, Function<Venta, List<MovimientoContable>> constructorAsiento) {
        Venta venta = persistenciaVenta.buscarPorNumeroFactura(numeroFactura);
        if (venta == null) {
            throw new ExcepcionAccesoDatos("Factura no encontrada: " + numeroFactura, null);
        }

        Connection conexion = null;
        try {
            conexion = ConexionSql.getConexion();
            conexion.setAutoCommit(false);
            persistenciaVenta.anularEnTransaccion(conexion, numeroFactura);
            venta.setEstado(co.edu.uptc.enums.EstadoVenta.ANULADA);
            persistirAsientoContable(conexion, constructorAsiento.apply(venta));
            conexion.commit();
        } catch (SQLException excepcion) {
            revertirTransaccion(conexion);
            throw ExcepcionAccesoDatos.desde(excepcion);
        } finally {
            restablecerConexion(conexion);
        }
    }

    @Override
    public Venta buscarVentaPorNumeroFactura(String numeroFactura) {
        return persistenciaVenta.buscarPorNumeroFactura(numeroFactura);
    }

    @Override
    public void guardarCompra(Compra compra, Function<Compra, List<MovimientoContable>> constructorAsiento) {
        Connection conexion = null;
        try {
            conexion = ConexionSql.getConexion();
            conexion.setAutoCommit(false);
            persistenciaCompra.guardarEnTransaccion(conexion, compra);
            persistirAsientoContable(conexion, constructorAsiento.apply(compra));
            conexion.commit();
        } catch (SQLException excepcion) {
            revertirTransaccion(conexion);
            throw ExcepcionAccesoDatos.desde(excepcion);
        } finally {
            restablecerConexion(conexion);
        }
    }

    @Override
    public List<VentaDTO> listarVenta() {
        return persistenciaVenta.listarResumen();
    }

    @Override
    public List<CompraDTO> listarCompra() {
        return persistenciaCompra.listarResumen();
    }

    private void persistirAsientoContable(Connection conexion, List<MovimientoContable> asientoContable)
            throws SQLException {
        for (MovimientoContable movimiento : asientoContable) {
            persistenciaContable.guardarEnTransaccion(conexion, movimiento);
        }
    }

    private void revertirTransaccion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.rollback();
            } catch (SQLException excepcion) {
                throw ExcepcionAccesoDatos.desde(excepcion);
            }
        }
    }

    private void restablecerConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException excepcion) {
                throw ExcepcionAccesoDatos.desde(excepcion);
            } finally {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    throw ExcepcionAccesoDatos.desde(excepcion);
                }
            }
        }
    }
}
