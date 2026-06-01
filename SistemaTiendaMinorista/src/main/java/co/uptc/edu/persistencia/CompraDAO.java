package co.uptc.edu.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import co.uptc.edu.modelo.Compra;
import co.uptc.edu.modelo.DetalleCompra;

public class CompraDAO {

	public boolean guardarCompra(Compra compra){

	    String sql =
	            "INSERT INTO compras(" +
	            "numero_factura," +
	            "fecha," +
	            "proveedor_codigo," +
	            "subtotal," +
	            "iva," +
	            "total" +
	            ") VALUES(?,?,?,?,?,?)";

	    try(

	        Connection con = ConexionBD.conectar();
	        PreparedStatement ps = con.prepareStatement(sql)

	    ){

	        String codigoProveedor =
	                compra.getProveedor().split(" - ")[0];

	        ps.setString(1, compra.getNumeroFactura());

	        // IMPORTANTE
	        ps.setDate(
	                2,
	                java.sql.Date.valueOf(compra.getFecha())
	        );

	        ps.setString(3, codigoProveedor);
	        ps.setDouble(4, compra.getSubtotal());
	        ps.setDouble(5, compra.getIva());
	        ps.setDouble(6, compra.getTotal());

	        ps.executeUpdate();

	        // GUARDAR DETALLES
	        for(DetalleCompra detalle : compra.getDetalles()){

	            guardarDetalleCompra(
	                    compra.getNumeroFactura(),
	                    detalle
	            );
	        }

	        return true;

	    }catch(Exception e){

	        JOptionPane.showMessageDialog(
	                null,
	                e.getMessage()
	        );

	        e.printStackTrace();
	        return false;
	    }
	}

    public boolean guardarDetalleCompra(
            String factura,
            DetalleCompra detalle){

        String sql =
                "INSERT INTO detalle_compras(" +
                "numero_factura," +
                "codigo_producto," +
                "nombre_producto," +
                "cantidad," +
                "costo_unitario," +
                "subtotal" +
                ") VALUES(?,?,?,?,?,?)";

        try(

            Connection con = ConexionBD.conectar();
            PreparedStatement ps = con.prepareStatement(sql)

        ){

            ps.setString(1, factura);
            ps.setString(2, detalle.getCodigoProducto());
            ps.setString(3, detalle.getNombreProducto());
            ps.setInt(4, detalle.getCantidad());
            ps.setDouble(5, detalle.getCostoUnitario());
            ps.setDouble(6, detalle.getSubtotal());

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            e.printStackTrace();
            return false;
        }
    }
    public ResultSet comprasPorProveedor(
            String codigoProveedor,
            String fechaInicio,
            String fechaFin){

        String sql =
                "SELECT * FROM compras " +
                "WHERE proveedor_codigo = ? " +
                "AND fecha BETWEEN ? AND ?";

        try{

            Connection con =
                    ConexionBD.conectar();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(
                    1,
                    codigoProveedor
            );

            ps.setDate(
                    2,
                    java.sql.Date.valueOf(
                            fechaInicio
                    )
            );

            ps.setDate(
                    3,
                    java.sql.Date.valueOf(
                            fechaFin
                    )
            );

            return ps.executeQuery();

        }catch(Exception e){

            e.printStackTrace();
            return null;
        }
    }
}