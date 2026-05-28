package co.edu.uptc.persistencia;



import co.edu.uptc.interfaces.IRepositorioCompra;

import co.edu.uptc.modelo.Compra;

import co.edu.uptc.modelo.DetalleVenta;

import co.edu.uptc.utilidades.ConexionBD;



import java.sql.Connection;

import java.sql.Date;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;



public class PersistenciaCompra implements IRepositorioCompra {



    @Override

    public void guardarCompra(Compra compra) {

        String sqlCabecera = """

            INSERT INTO compras (factura_proveedor, fecha, codigo_proveedor, total_compra, iva)

            VALUES (?, ?, ?, ?, ?)

            """;

        String sqlDetalle = """

            INSERT INTO detalles_compras (factura_proveedor, codigo_producto, cantidad, costo_unitario)

            VALUES (?, ?, ?, ?)

            """;



        Connection con = ConexionBD.getConexion();

        if (con == null) {

            return;

        }



        try {

            con.setAutoCommit(false);

            Date fecha = Date.valueOf(compra.getFecha().toLocalDate());



            try (PreparedStatement ps = con.prepareStatement(sqlCabecera)) {

                ps.setString(1, compra.getFacturaProveedor());

                ps.setDate(2, fecha);

                ps.setString(3, compra.getProveedor().getCodigoProveedor());

                ps.setDouble(4, compra.getTotalCompra());

                ps.setDouble(5, compra.getIva());

                ps.executeUpdate();

            }



            if (compra.getProductosComprados() != null) {

                try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {

                    for (DetalleVenta dv : compra.getProductosComprados()) {

                        ps.setString(1, compra.getFacturaProveedor());

                        ps.setString(2, dv.getProducto().getCodigoProducto());

                        ps.setInt(3, dv.getCantidad());

                        ps.setDouble(4, dv.getPrecioUnitario());

                        ps.addBatch();

                    }

                    ps.executeBatch();

                }

            }



            con.commit();

        } catch (SQLException e) {

            try {

                con.rollback();

            } catch (SQLException ex) {

                System.err.println("Error en rollback compra: " + ex.getMessage());

            }

            System.err.println("Error al guardar compra en BD: " + e.getMessage());

        } finally {

            try {

                con.setAutoCommit(true);

            } catch (SQLException e) {

                System.err.println("Error al restaurar autocommit: " + e.getMessage());

            }

        }

    }



    @Override

    public List<Compra> consultarPorProveedor(String codigoProveedor) {

        List<Compra> lista = new ArrayList<>();

        String sql = "SELECT * FROM compras WHERE codigo_proveedor = ?";

        try (Connection con = ConexionBD.getConexion();

             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoProveedor);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Compra c = new Compra();

                    c.setFacturaProveedor(rs.getString("factura_proveedor"));

                    c.setFecha(rs.getDate("fecha").toLocalDate().atStartOfDay());

                    c.setTotalCompra(rs.getDouble("total_compra"));

                    c.setIva(rs.getDouble("iva"));

                    lista.add(c);

                }

            }

        } catch (SQLException e) {

            System.err.println("Error al consultar compras por proveedor: " + e.getMessage());

        }

        return lista;

    }



    @Override

    public void guardar(Compra objeto) {

        guardarCompra(objeto);

    }



    @Override

    public void eliminar(String id) {

    }

    @Override

    public Compra buscarPorId(String id) {

        return null;

    }

    @Override

    public List<Compra> listar() {

        return new ArrayList<>();

    }

    @Override

    public void actualizar(Compra objeto) {

    }

}


