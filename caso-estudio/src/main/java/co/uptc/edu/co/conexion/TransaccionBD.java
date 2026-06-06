package co.uptc.edu.co.conexion;

import java.sql.Connection;

public class TransaccionBD {

	@FunctionalInterface
	public interface OperacionTransaccional {
		void ejecutar(Connection conexion) throws Exception;
	}

	public static void ejecutar(OperacionTransaccional operacion) throws Exception {
		try (Connection conexion = ConexionBD.getConexion()) {
			try {
				conexion.setAutoCommit(false);

				operacion.ejecutar(conexion);

				conexion.commit();
			} catch (Exception e) {
				conexion.rollback();
				throw e;
			} finally {
				conexion.setAutoCommit(true);
			}
		}
	}
}