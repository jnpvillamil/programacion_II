package co.edu.uptc.persistencia;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class CargadorConfiguracionBd {

    private static final String ARCHIVO_PREDETERMINADO = "configuracion/bd.properties";
    private static final String PROPIEDAD_RUTA = "co.edu.uptc.bd.configuracion";
    private static final String VARIABLE_RUTA = "TIENDA_BD_CONFIGURACION";

    private static final String CLAVE_URL = "urlJdbc";
    private static final String CLAVE_USUARIO = "usuario";
    private static final String CLAVE_CONTRASENA = "contrasena";

    private static final Properties PROPIEDAD = cargarPropiedad();

    private CargadorConfiguracionBd() {
    }

    public static String obtenerUrlJdbc() {
        return obtenerObligatorio(CLAVE_URL);
    }

    public static String obtenerUsuario() {
        return obtenerObligatorio(CLAVE_USUARIO);
    }

    public static String obtenerContrasena() {
        return obtenerObligatorio(CLAVE_CONTRASENA);
    }

    private static String obtenerObligatorio(String clave) {
        String valor = PROPIEDAD.getProperty(clave);
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException(
                    "Falta la propiedad obligatoria '" + clave + "' en la configuración de base de datos.");
        }
        return valor.trim();
    }

    private static Properties cargarPropiedad() {
        Properties propiedad = new Properties();
        Path ruta = resolverRutaArchivo();

        if (Files.isRegularFile(ruta)) {
            try (InputStream flujo = Files.newInputStream(ruta)) {
                propiedad.load(flujo);
                return propiedad;
            } catch (IOException excepcion) {
                throw new IllegalStateException(
                        "No fue posible leer el archivo de configuración: " + ruta.toAbsolutePath(), excepcion);
            }
        }

        throw new IllegalStateException(mensajeArchivoNoEncontrado(ruta));
    }

    private static Path resolverRutaArchivo() {
        String rutaPersonalizada = System.getProperty(PROPIEDAD_RUTA);
        if (rutaPersonalizada == null || rutaPersonalizada.isBlank()) {
            rutaPersonalizada = System.getenv(VARIABLE_RUTA);
        }
        if (rutaPersonalizada != null && !rutaPersonalizada.isBlank()) {
            return Paths.get(rutaPersonalizada.trim()).toAbsolutePath().normalize();
        }
        return Paths.get(System.getProperty("user.dir"), ARCHIVO_PREDETERMINADO)
                .toAbsolutePath()
                .normalize();
    }

    private static String mensajeArchivoNoEncontrado(Path ruta) {
        return "No se encontró el archivo de configuración de base de datos en: "
                + ruta.toAbsolutePath()
                + ". Copie configuracion/bd.ejemplo.properties a configuracion/bd.properties "
                + "o defina la ruta con -D" + PROPIEDAD_RUTA + "=<ruta> o la variable " + VARIABLE_RUTA + ".";
    }
}
