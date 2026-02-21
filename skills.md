# Java 21 & Quarkus 3 Development Skills

## Stack Tecnológico
- **Java 21**: Usar Virtual Threads (Project Loom) cuando sea posible.
- **Quarkus 3.x**: Framework principal.
- **Hibernate con Panache (Active Record)**: Las entidades extienden de `PanacheEntity`.
- **PostgreSQL**: Base de datos gestionada por Dev Services en Docker.
- **RESTEasy Reactive**: Para los endpoints REST.

## Reglas de Codificación
1. **Entidades**: Siempre usar campos `public` para Panache (Active Record Pattern).
2. **Endpoints**: Retornar `RestResponse` o la entidad directamente si es simple.
3. **Persistencia**: Usar la anotación `@Transactional` en métodos que escriban en BD.
4. **Validación**: Usar Jakarta Bean Validation (@NotBlank, @Min, etc.).

## Base de Datos (Docker)
- El esquema se genera automáticamente (`drop-and-create` en dev).
- Datos iniciales en `src/main/resources/import.sql`.
