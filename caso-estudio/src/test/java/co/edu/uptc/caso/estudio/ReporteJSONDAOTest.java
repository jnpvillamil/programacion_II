package co.edu.uptc.caso.estudio;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import co.uptc.edu.co.modelo.dto.ResumenContableDTO;
import co.uptc.edu.co.modelo.dto.ResumenFinancieroDiarioDTO;
import co.uptc.edu.co.modelo.dto.ResumenFormaPagoDTO;
import co.uptc.edu.co.modelo.dto.ResumenProductoDTO;
import co.uptc.edu.co.modelo.enums.FormaPago;
import co.uptc.edu.co.persistencia.archivo.ReporteJSONDAO;
import junit.framework.TestCase;

public class ReporteJSONDAOTest extends TestCase {

	public void testGuardarReporteResumenFinancieroDiario() throws Exception {
		ReporteJSONDAO dao = new ReporteJSONDAO();
		ResumenFinancieroDiarioDTO resumen = new ResumenFinancieroDiarioDTO(LocalDate.of(2025, 2, 25), 12500000,
				7200000, 5300000,
				List.of(new ResumenFormaPagoDTO(FormaPago.EFECTIVO, 2, 5000000),
						new ResumenFormaPagoDTO(FormaPago.TARJETA, 1, 4500000)),
				List.of(new ResumenProductoDTO("P001", "Arroz 1kg", 120, 360000),
						new ResumenProductoDTO("P015", "Aceite 1L", 85, 425000)),
				new ResumenContableDTO(12500000, 7200000, 5300000), 2375000, 1368000);

		String ruta = dao.guardarReporteResumenFinancieroDiario(resumen);
		File archivo = new File(ruta);

		assertTrue(archivo.exists());

		String contenido = Files.readString(archivo.toPath(), StandardCharsets.UTF_8);
		assertTrue(contenido.contains("\"fecha\": \"2025-02-25\""));
		assertTrue(contenido.contains("\"total_ventas\""));
		assertTrue(contenido.contains("\"ventas_por_forma_pago\""));
		assertTrue(contenido.contains("\"productos_mas_vendidos\""));
		assertTrue(contenido.contains("\"resumen_contable\""));
		assertTrue(contenido.contains("\"iva_generado\""));
		assertTrue(archivo.delete());
	}
}
