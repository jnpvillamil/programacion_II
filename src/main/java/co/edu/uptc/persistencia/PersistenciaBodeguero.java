package co.edu.uptc.persistencia;

import co.edu.uptc.dto.BodegueroDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistenciaBodeguero {

    private static final String SQL_INSERT = """
            INSERT INTO usuario (
                usuario_login, clave, rol, zona_bodega, nombres, apellidos, activo
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SQL_EXISTE_LOGIN = """
            SELECT COUNT(1) AS total
            FROM usuario
            WHERE usuario_login = ?
            """;

    public void guardar(BodegueroDTO dto, String clave) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INSERT)) {

            String login = dto.login().trim();
            String nombreDerivado = capitalizar(login);

            sentencia.setString(1, login);              
            sentencia.setString(2, clave.trim());        
            sentencia.setString(3, dto.rol());          
            sentencia.setString(4, dto.zonaBodega());   
            sentencia.setString(5, nombreDerivado);     
            sentencia.setString(6, "Bodeguero");        
            sentencia.setBoolean(7, true);              
            sentencia.executeUpdate();
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
    }

    public boolean existeLogin(String login) {
        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_EXISTE_LOGIN)) {
            sentencia.setString(1, login.trim());
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("total") > 0;
                }
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
        return false;
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isBlank()) {
            return "";
        }
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
}
