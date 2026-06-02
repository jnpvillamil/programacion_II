package co.edu.uptc.persistencia;

import java.sql.SQLException;

public class ExcepcionAccesoDatos extends RuntimeException {

    public ExcepcionAccesoDatos(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public static ExcepcionAccesoDatos desde(SQLException e) {
        return new ExcepcionAccesoDatos(e.getMessage(), e);
    }
}
