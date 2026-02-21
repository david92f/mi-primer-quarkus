# Mi Primer Quarkus

Proyecto de ejemplo construido con **Quarkus** - El framework Java supersonico y subatomico.

## Descripcion

Este proyecto es una API RESTful construida con Quarkus que permite gestionar productos y tareas. Utiliza:
- **Quarkus 3.31.4**
- **Java 21**
- **PostgreSQL** como base de datos
- **Hibernate ORM with Panache** para la persistencia

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/david/
│   │   ├── entity/          # Entidades de base de datos
│   │   │   ├── Producto.java
│   │   │   └── Tarea.java
│   │   ├── resource/       # Endpoints REST
│   │   │   ├── ProductoResource.java
│   │   │   └── TareaResource.java
│   │   ├── service/       # Logica de negocio
│   │   │   └── TareaService.java
│   │   ├── dto/           # Objetos de transferencia de datos
│   │   │   ├── ProductoDTO.java
│   │   │   └── TareaDTO.java
│   │   └── exception/     # Manejo de errores
│   │       └── GlobalExceptionMapper.java
│   └── resources/
│       ├── application.properties
│       └── import.sql
└── test/
    └── java/com/david/
        ├── ProductoResourceTest.java
        └── TareaResourceTest.java
```

## Entidades

### Producto
| Campo | Tipo | Descripcion |
|-------|------|-------------|
| id | Long | Identificador unico |
| nombre | String | Nombre del producto |
| precio | Double | Precio del producto |
| stock | Integer | Cantidad en stock |

### Tarea
| Campo | Tipo | Descripcion |
|-------|------|-------------|
| id | Long | Identificador unico |
| titulo | String | Titulo de la tarea |
| descripcion | String | Descripcion detallada |
| terminada | Boolean | Estado de la tarea |

## Endpoints REST

### Productos
| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | /productos | Listar todos los productos |
| POST | /productos | Crear un nuevo producto |

### Tareas
| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| GET | /tareas | Listar todas las tareas |
| POST | /tareas | Crear una nueva tarea |

## Configuracion

La configuracion se encuentra en `src/main/resources/application.properties`:

```properties
# Database
quarkus.datasource.db-kind=postgresql
quarkus.hibernate-orm.database.generation=update
quarkus.datasource.devservices.port=5432

# OpenAPI/Swagger
quarkus.smallrye-openapi.path=/swagger-ui
quarkus.swagger-ui.always-include=true
```

## Como Ejecutar

### Desarrollo
```bash
./mvnw quarkus:dev
```
Accede a la Dev UI en: http://localhost:8080/q/dev/

### Produccion
```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

### Docker Compose
```bash
docker-compose up -d
```

## Pruebas

Ejecutar todos los tests:
```bash
./mvnw test
```

Ejecutar un test especifico:
```bash
./mvnw test -Dtest=ProductoResourceTest
```

## Documentacion API

Swagger UI esta disponible en: http://localhost:8080/q/swagger-ui/

## Licencia

MIT
