package co.uptc.edu.tienda.persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import co.uptc.edu.tienda.conexion.Conexion;
import co.uptc.edu.tienda.enums.TipoMovimientoContable;
import co.uptc.edu.tienda.interfaces.IGestionContable;
import co.uptc.edu.tienda.modelo.MovimientoContable;

public class SqlContable implements IGestionContable {

    @Override
    public void guardar(MovimientoContable m) {
        Conexion conex = new Conexion();
        try {
            String sql = "INSERT INTO movimientos_contables "
                    + "(fecha, tipo_movimiento, cuenta_contable, valor, descripcion, referencia_factura) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conex.getConnection().prepareStatement(sql);
            ps.setString(1, m.getFecha());
            ps.setString(2, m.getTipoMovimiento().name());
            ps.setString(3, m.getCuentaContable());
            ps.setDouble(4, m.getValor());
            ps.setString(5, m.getDescripcion());
            ps.setString(6, m.getReferenciaFactura());
            ps.executeUpdate();
            ps.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al guardar movimiento contable: " + e.getMessage());
        }
    }

    @Override
    public List<MovimientoContable> cargar() {
        Conexion conex = new Conexion();
        List<MovimientoContable> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM movimientos_contables";
            Statement st = conex.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            st.close();
            conex.desconectar();
        } catch (SQLException e) {
            System.out.println("Error al cargar movimientos contables: " + e.getMessage());
        }
        return lista;
    }

    private MovimientoContable mapear(ResultSet rs) throws SQLException {
        MovimientoContable m = new MovimientoContable();
        m.setCodigoTransaccion(rs.getInt("codigo_transaccion"));
        m.setFecha(rs.getString("fecha"));
        m.setTipoMovimiento(TipoMovimientoContable.valueOf(rs.getString("tipo_movimiento")));
        m.setCuentaContable(rs.getString("cuenta_contable"));
        m.setValor(rs.getDouble("valor"));
        m.setDescripcion(rs.getString("descripcion"));
        m.setReferenciaFactura(rs.getString("referencia_factura"));
        return m;
    }
}