package co.uptc.edu.co.persistencia.archivo;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import co.uptc.edu.co.interfaces.dao.FacturaDAO;
import co.uptc.edu.co.util.LogUtil;

public class FacturaTxtDAO implements FacturaDAO {

	private static final String CARPETA_FACTURAS = "facturas";

	@Override
	public String guardarFactura(String numeroFactura, String contenido) throws Exception {
        LogUtil.info("Entrando a guardarFactura. numeroFactura=" + numeroFactura);
		File carpeta = new File(CARPETA_FACTURAS);

		if (!carpeta.exists() && !carpeta.mkdirs()) {
			throw new Exception("No se pudo crear la carpeta de facturas.");
		}

		File archivo = new File(carpeta, numeroFactura + ".txt");

		try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
			writer.print(contenido);
		}

		LogUtil.info("Factura guardada en: " + archivo.getPath());

		return archivo.getPath();
	}

}
