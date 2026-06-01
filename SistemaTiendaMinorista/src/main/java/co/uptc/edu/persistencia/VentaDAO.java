package co.uptc.edu.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import co.uptc.edu.modelo.Venta;

public class VentaDAO {

    public boolean guardarVenta(
            String fecha,
            String clienteCodigo,
            Venta venta){

        String sql =
                "INSERT INTO ventas(" +
                "fecha," +
                "cliente_codigo," +
                "codigo_producto," +
                "cantidad," +
                "precio," +
                "subtotal" +
                ") VALUES(?,?,?,?,?,?)";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps =
                    con.prepareStatement(sql)

        ){

            ps.setDate(
                    1,
                    java.sql.Date.valueOf(fecha)
            );

            ps.setString(2, clienteCodigo);
            ps.setString(3, venta.getCodigoProducto());
            ps.setInt(4, venta.getCantidadVendida());
            ps.setDouble(5, venta.getPrecio());

            ps.setDouble(
                    6,
                    venta.getCantidadVendida()
                    * venta.getPrecio()
            );

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }
    public ResultSet historialCliente(String codigoCliente){

        String sql =
                "SELECT * " +
                "FROM ventas " +
                "WHERE cliente_codigo = ?";

        try{

            Connection con = ConexionBD.conectar();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, codigoCliente);

            return ps.executeQuery();

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }

}