package co.edu.uptc.dao;

import co.edu.uptc.conexion.Conexion;
import co.edu.uptc.vo.ProductoVo;
import java.sql.*;
import javax.swing.JOptionPane;

public class ProductoDao {
    public void registrarProducto(ProductoVo miProducto) {
        Conexion conexion = new Conexion();
        try {
            Statement estatuto = conexion.getConnection().createStatement();
            String consulta = "INSERT INTO productos (codigo_producto, nombre_producto, categoria, precio_compra, precio_venta, stock, stock_minimo) VALUES ("
                    + "'" + miProducto.getCodigoProducto() + "', '" + miProducto.getNombreProducto() + "', '" + miProducto.getCategoria() + "', "
                    + miProducto.getPrecioCompra() + ", " + miProducto.getPrecioVenta() + ", " + miProducto.getStock() + ", " + miProducto.getStockMinimo() + ")";
            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Producto Guardado", "Información", JOptionPane.INFORMATION_MESSAGE);
            estatuto.close(); conexion.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarProducto(ProductoVo miProducto) {
        Conexion conexion = new Conexion();
        try {
            Statement estatuto = conexion.getConnection().createStatement();
            String consulta = "UPDATE productos SET "
                    + "nombre_producto = '" + miProducto.getNombreProducto() + "', categoria = '" + miProducto.getCategoria() + "', "
                    + "precio_compra = " + miProducto.getPrecioCompra() + ", precio_venta = " + miProducto.getPrecioVenta() + ", "
                    + "stock = " + miProducto.getStock() + ", stock_minimo = " + miProducto.getStockMinimo() + " "
                    + "WHERE codigo_producto = '" + miProducto.getCodigoProducto() + "'";
            estatuto.executeUpdate(consulta);
            JOptionPane.showMessageDialog(null, "Producto Actualizado con Éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
            estatuto.close(); conexion.desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Object[][] consultarTodosLosProductos() {
        Conexion conexion = new Conexion();
        Object[][] datos = new Object[0][7];
        try {
            Statement estatuto = conexion.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "SELECT * FROM productos";
            ResultSet rs = estatuto.executeQuery(consulta);
            rs.last(); int filas = rs.getRow(); rs.beforeFirst();
            datos = new Object[filas][7];
            int i = 0;
            while(rs.next()) {
                datos[i][0] = rs.getString("codigo_producto");
                datos[i][1] = rs.getString("nombre_producto");
                datos[i][2] = rs.getString("categoria");
                datos[i][3] = rs.getDouble("precio_compra");
                datos[i][4] = rs.getDouble("precio_venta");
                datos[i][5] = rs.getInt("stock");
                datos[i][6] = rs.getInt("stock_minimo");
                i++;
            }
            rs.close(); estatuto.close(); conexion.desconectar();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return datos;
    }
}