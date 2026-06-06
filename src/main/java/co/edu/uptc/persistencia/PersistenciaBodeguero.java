package co.edu.uptc.persistencia;

import co.edu.uptc.dto.ProductoResumenDTO;
import co.edu.uptc.enums.CategoriaProducto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            }
        } catch (SQLException excepcion) {
            throw ExcepcionAccesoDatos.desde(excepcion);
        }

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