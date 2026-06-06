package co.edu.uptc.persistencia;

<<<<<<< HEAD
import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.enums.CategoriaProducto;
=======
import co.edu.uptc.dto.BodegueroDTO;
>>>>>>> 7d4245951cf20ce153a582cbbc5a372a9ca9ae72

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

public class PersistenciaBodeguero {

    private static final String SQL_INVENTARIO_CRITICO = """
            SELECT codigo_interno,
                   nombre_producto,
                   categoria,
                   precio_venta,
                   stock_actual,
                   stock_minimo,
                   activo
            FROM producto
            WHERE stock_actual <= stock_minimo
              AND activo = 1
            ORDER BY stock_actual ASC, nombre_producto ASC
            """;

    public List<ProductoResumenDTO> listarInventarioCritico() {
        List<ProductoResumenDTO> listaCritica = new ArrayList<>();

        try (Connection conexion = ConexionSql.getConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_INVENTARIO_CRITICO);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                listaCritica.add(mapearProductoCritico(resultado));
=======


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
>>>>>>> 7d4245951cf20ce153a582cbbc5a372a9ca9ae72
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }
<<<<<<< HEAD

        return listaCritica;
    }

    private ProductoResumenDTO mapearProductoCritico(ResultSet resultado) throws SQLException {
        int stockActual = resultado.getInt("stock_actual");
        int stockMinimo = resultado.getInt("stock_minimo");

        return new ProductoResumenDTO(
                resultado.getString("codigo_interno"),
                resultado.getString("nombre_producto"),
                CategoriaProducto.valueOf(resultado.getString("categoria")).name(),
                resultado.getDouble("precio_venta"),
                stockActual,
                stockActual <= stockMinimo ? "¡BAJO STOCK!" : "Normal",
                resultado.getBoolean("activo") ? "Activo" : "Inactivo");
    }
}
=======
        return false;
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isBlank()) {
            return "";
        }
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
}
>>>>>>> 7d4245951cf20ce153a582cbbc5a372a9ca9ae72
