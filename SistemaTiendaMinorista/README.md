# Sistema Tienda Minorista — Grupo 9

## Estructura del proyecto

```
SistemaTiendaMinorista/
├── pom.xml
├── clientes.json
├── productos.json
├── proveedores.json
├── movimientos.json         (se crea automáticamente)
└── src/co/edu/uptc/tiendaminorista/
    ├── enums/               Enumeraciones (TipoDocumentoEnum, CategoriaProducto)
    ├── dto/                 Objetos de transferencia de datos (CredencialDto)
    ├── modelo/              Clases de dominio (Persona, Cliente, Proveedor, Producto, MovimientoContable)
    ├── interfaces/          Contratos de persistencia (IGestionCliente, IGestionProveedor, ...)
    ├── persistencia/        Implementaciones JSON con Gson (LocalCliente, LocalProveedor, ...)
    ├── negocio/             Lógica de negocio (GestionCliente, TiendaConfig, SistemaSeguridad, ...)
    └── gui/
        ├── PanelPrincipal.java   (main — punto de entrada)
        ├── PanelLogin.java
        ├── Evento.java
        └── administrador/        Paneles del módulo administrador
```

## Dependencias

- Java 21
- Gson 2.10.1 (persistencia JSON)
