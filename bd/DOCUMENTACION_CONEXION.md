# Documentación de conexión — MySQL Aiven Cloud (defaultdb)

## Objetivo

Centralizar los parámetros obligatorios para que el equipo de desarrollo conecte **MySQL Workbench** y la **aplicación Java** al servicio MySQL en Aiven Cloud, en modo producción con inserción manual de datos desde la GUI.

---

## Parámetros obligatorios de conexión

| Parámetro | Valor / regla |
|-----------|----------------|
| Motor | MySQL 8.4 (Aiven Cloud) |
| Base de datos | `defaultdb` |
| Host | Host SSL proporcionado por Aiven (ej. `mysql-....e.aivencloud.com`) |
| Puerto | Puerto TLS asignado por Aiven (ej. `19414`) |
| Usuario | Credencial de servicio Aiven (ej. `avnadmin`) |
| SSL | **Obligatorio** — `sslMode=REQUIRED` |
| Zona horaria JDBC | `serverTimezone=UTC` |

### Cadena JDBC (aplicación Java)

Archivo local: `configuracion/bd.properties` (copiar desde `configuracion/bd.ejemplo.properties`).

```properties
urlJdbc=jdbc:mysql://HOST:PUERTO/defaultdb?sslMode=REQUIRED&serverTimezone=UTC
usuario=su_usuario
contrasena=su_contrasena
```

La aplicación carga este archivo desde la raíz del proyecto (`user.dir`). En Eclipse, configure el **Working directory** apuntando a la raíz de `TiendaMinoristaG4`.

---

## Conexión desde MySQL Workbench

1. Abra **MySQL Workbench**.
2. Cree o edite una conexión hacia el host y puerto de Aiven.
3. **Default Schema:** `defaultdb`.
4. Pestaña **SSL:** active **Use SSL** y cargue el certificado CA de Aiven si la instancia lo exige.
5. Use **Test Connection** antes de operar.

> Si el Navigator muestra un esquema distinto o tablas inesperadas, verifique que no esté conectado a un MySQL local (`localhost`).

---

## Esquema oficial en producción (9 tablas)

La aplicación Java opera exclusivamente sobre tablas en **singular** y **snake_case**:

`cliente`, `compra`, `detalle_compra`, `detalle_venta`, `movimiento_contable`, `producto`, `proveedor`, `usuario`, `venta`

Los datos comerciales se ingresan **manualmente** desde la interfaz Swing. No se distribuyen scripts DML de simulación en el repositorio.

---

## Scripts SQL disponibles en `bd/`

| Archivo | Propósito | ¿Ejecutar en producción activa? |
|---------|-----------|----------------------------------|
| `01_estructura_maestra.sql` | DDL de respaldo: 9 tablas, vista `cliente_activo`, trigger `trg_venta_cliente_activo` | **NO** — contiene `DROP TABLE IF EXISTS` |
| `02_reparacion_accesos.sql` | Restablece `admin`/`123` y `cajero`/`123` con `INSERT ... ON DUPLICATE KEY UPDATE` | **SÍ** — no borra datos comerciales |

### Consulta rápida del catálogo (solo lectura)

```sql
USE defaultdb;

SELECT TABLE_NAME AS tabla_oficial,
       ENGINE AS motor,
       TABLE_ROWS AS filas_estimadas
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'defaultdb'
  AND TABLE_TYPE = 'BASE TABLE'
ORDER BY TABLE_NAME;
```

---

## Credenciales de acceso a la aplicación

| Usuario | Clave | Rol |
|---------|-------|-----|
| `admin` | `123` | ADMINISTRADOR |
| `cajero` | `123` | CAJERO |

Si el login falla, ejecute `bd/02_reparacion_accesos.sql` en Workbench.

---

## Refresco del Navigator en Workbench

1. Clic derecho sobre **`defaultdb`** → **Refresh All**.
2. Si persiste información desactualizada: **Database → Disconnect**, reconectar o reiniciar Workbench.
