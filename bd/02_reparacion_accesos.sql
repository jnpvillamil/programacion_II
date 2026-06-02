-- =============================================================================
-- Tienda Minorista G4 | Reparación segura de accesos (DML)
-- Motor: MySQL 8.4 | Destino: Aiven Cloud | Base de datos: defaultdb
--
-- SEGURIDAD EN PRODUCCIÓN:
--   Este script es 100% SEGURO de ejecutar en la base de datos activa.
--   Solo restablece o crea los usuarios obligatorios admin y cajero.
--
-- Uso: ejecutar en MySQL Workbench si el login falla con admin/123 o cajero/123
-- =============================================================================

USE defaultdb;

INSERT INTO usuario (usuario_login, clave, rol, nombres, apellidos, identificacion, telefono, activo)
VALUES
('admin',  '123', 'ADMINISTRADOR', 'Administrador', 'Sistema',   '900000001', '3001000001', 1),
('cajero', '123', 'CAJERO',        'Cajero',        'Principal', '900000002', '3001000002', 1)
AS nuevo
ON DUPLICATE KEY UPDATE
    clave = '123',
    rol = CASE usuario_login
              WHEN 'admin'  THEN 'ADMINISTRADOR'
              WHEN 'cajero' THEN 'CAJERO'
              ELSE rol
          END,
    nombres = nuevo.nombres,
    apellidos = nuevo.apellidos,
    activo = 1;
