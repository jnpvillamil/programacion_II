package co.uptc.edu.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.ResultSet;

import co.uptc.edu.interfaces.IProductoDAO;
import co.uptc.edu.modelo.Producto;

public class ProductoDAO implements IProductoDAO{
	private JTable tablaStock;

	private DefaultTableModel modeloStock;
	
	@Override
    public boolean guardarProducto(Producto producto) {

    	String sql =
    	        "INSERT INTO productos(" +
    	        "codigo," +
    	        "nombre," +
    	        "categoria," +
    	        "precio_compra," +
    	        "precio_venta," +
    	        "stock_actual," +
    	        "stock_minimo," +
    	        "estado" +
    	        ") VALUES(?,?,?,?,?,?,?,?)";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getCategoria());
            ps.setDouble(4, producto.getPrecioCompra());
            ps.setDouble(5, producto.getPrecioVenta());
            ps.setInt(6, producto.getStockActual());
            ps.setInt(7, producto.getStockMinimo());
            ps.setString(8, producto.getEstado());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
	@Override
    public List<Producto> obtenerProductos() {

        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT * FROM productos";

        try (
                Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while(rs.next()) {

                Producto p = new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getDouble("precio_compra"),
                        rs.getDouble("precio_venta"),
                        rs.getInt("stock_actual"),
                        rs.getInt("stock_minimo")
                );

                p.setEstado(
                        rs.getString("estado")
                );

                productos.add(p);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return productos;
    }
	@Override
    public boolean modificarProducto(Producto producto){

        String sql =
                "UPDATE productos SET " +
                "nombre=?," +
                "categoria=?," +
                "precio_compra=?," +
                "precio_venta=?," +
                "stock_actual=?," +
                "stock_minimo=?," +
                "estado=? " +
                "WHERE codigo=?";

        try(
            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)
        ){

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getCategoria());
            ps.setDouble(3, producto.getPrecioCompra());
            ps.setDouble(4, producto.getPrecioVenta());
            ps.setInt(5, producto.getStockActual());
            ps.setInt(6, producto.getStockMinimo());
            ps.setString(7, producto.getEstado());

            ps.setString(8, producto.getCodigo());

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }
	@Override
    public boolean aumentarStock(String codigo, int cantidad){

        String sql =
                "UPDATE productos " +
                "SET stock_actual = stock_actual + ? " +
                "WHERE codigo = ?";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)

        ){

            ps.setInt(1, cantidad);
            ps.setString(2, codigo);

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }
	@Override
    public boolean descontarStock(String codigo, int cantidad){

        String sql =
                "UPDATE productos " +
                "SET stock_actual = stock_actual - ? " +
                "WHERE codigo = ?";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)

        ){

            ps.setInt(1, cantidad);
            ps.setString(2, codigo);

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }
	@Override
    public ResultSet obtenerStockBajoMinimo(){

        String sql =
                "SELECT * " +
                "FROM productos " +
                "WHERE stock_actual <= stock_minimo";

        try{

            Connection con = ConexionBD.conectar();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            return ps.executeQuery();

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }
}