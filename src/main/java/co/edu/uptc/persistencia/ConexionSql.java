package co.edu.uptc.persistencia;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConexionSql {

    private static final HikariDataSource FUENTE_DATO;

    static {
        HikariConfig configuracion = new HikariConfig();
        configuracion.setJdbcUrl(CargadorConfiguracionBd.obtenerUrlJdbc());
        configuracion.setUsername(CargadorConfiguracionBd.obtenerUsuario());
        configuracion.setPassword(CargadorConfiguracionBd.obtenerContrasena());
        configuracion.setDriverClassName("com.mysql.cj.jdbc.Driver");
        configuracion.setMaximumPoolSize(10);
        configuracion.setMinimumIdle(2);
        configuracion.setConnectionTimeout(30_000);
        configuracion.setIdleTimeout(600_000);
        configuracion.setMaxLifetime(1_800_000);
        configuracion.setPoolName("PoolTiendaMinorista");
        FUENTE_DATO = new HikariDataSource(configuracion);
    }

    private ConexionSql() {
    }

    public static Connection getConexion() throws SQLException {
        return FUENTE_DATO.getConnection();
    }

    public static void cerrarPool() {
        if (FUENTE_DATO != null && !FUENTE_DATO.isClosed()) {
            FUENTE_DATO.close();
        }
    }
}
