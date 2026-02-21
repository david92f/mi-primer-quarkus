# Agent Guidelines - mi-primer-quarkus

This document provides essential information for autonomous agents working on this Quarkus project.

## Build and Test Commands

The project uses Maven with the Quarkus wrapper (`./mvnw`).

| Task | Command |
| :--- | :--- |
| **Build Project** | `./mvnw compile` |
| **Run Dev Mode** | `./mvnw quarkus:dev` |
| **Run All Tests** | `./mvnw test` |
| **Run Single Test** | `./mvnw test -Dtest=ClassName` |
| **Run Single Method** | `./mvnw test -Dtest=ClassName#methodName` |
| **Package Application** | `./mvnw package` |
| **Check Code Quality** | `./mvnw verify` |

## Code Style & Conventions

### 1. Language & Framework
- **Java 21**: Utilize modern Java features (records, pattern matching, etc.).
- **Quarkus 3.31.4**: Follow Quarkus-specific patterns.
- **Jakarta EE**: Use `jakarta.*` namespaces (not `javax.*`).

### 2. Naming Conventions
- **Classes**: `PascalCase`. Resources should end in `Resource` (e.g., `ProductoResource`). Entities should be concise (e.g., `Producto`).
- **Methods/Variables**: `camelCase`.
- **Packages**: `lowercase.dot.separated` (e.g., `com.david.entity`).
- **Database Tables**: By default, Panache matches class names. Use `@Table(name = "...")` only if necessary.

### 3. Imports & Formatting
- **No Wildcard Imports**: Explicitly import each class.
- **Indentation**: 4 spaces.
- **Line Length**: Aim for 120 characters maximum.
- **Braces**: Use Egyptian style (opening brace on the same line as the statement).

### 4. Persistence (Panache)
- Prefer `PanacheEntity` for simple CRUD.
- Fields in `PanacheEntity` should be `public` (Panache handles field access transformation).
- Use `@Transactional` on any method that modifies the database.
- Database initialization: Use `src/main/resources/import.sql` for development data.

### 5. REST Resources
- Use Jakarta REST annotations (`@Path`, `@GET`, `@POST`, etc.).
- Use `@Produces(MediaType.APPLICATION_JSON)` and `@Consumes(MediaType.APPLICATION_JSON)` at the class level.
- Return entities directly or `Response` objects for more control (e.g., `Response.created(uri).build()`).

### 6. Error Handling
- Use Jakarta REST ExceptionMappers for global error handling.
- Avoid swallowing exceptions; log them or wrap them in appropriate runtime exceptions.

### 7. Testing
- **JUnit 5**: Use `@QuarkusTest`.
- **REST-assured**: Use `given().when().get().then()` syntax for endpoint verification.
- Matchers: Use `org.hamcrest.CoreMatchers`.

## Project Structure
- `src/main/java/`: Source code.
  - `com.david.entity/`: Data models using Panache.
  - `com.david.resource/`: REST endpoints using Jakarta REST.
- `src/main/resources/`: Configuration and SQL scripts.
  - `application.properties`: Main configuration file.
  - `import.sql`: Default data for dev/test environments.
- `src/test/java/`: Unit and integration tests.

## Development Workflow

### 1. Dev Mode
Always prefer using `./mvnw quarkus:dev` during development. This provides:
- **Live Coding**: Changes to Java files, resources, and configuration are applied immediately.
- **Dev UI**: Accessible at `/q/dev/` for inspecting beans, endpoints, and configuration.
- **Continuous Testing**: Press `r` in the console to run tests automatically on change.

### 2. Database Management
- The project is configured for PostgreSQL, but uses Dev Services in dev mode (automatic container management if Docker is available).
- Hibernate ORM with Panache is the primary data access layer.
- `import.sql` is automatically executed if the database is initialized (standard in dev mode).

### 3. Dependency Management
- Add new extensions using `./mvnw quarkus:add-extension -Dextensions="name"`.
- Check for available extensions with `./mvnw quarkus:list-extensions`.

## Environment Configuration
- Use `${VAR_NAME:default_value}` syntax in `application.properties` to allow environment variable overrides.
- Profile-specific configuration:
  - `%dev.`: Properties for dev mode.
  - `%test.`: Properties for test mode.
  - `%prod.`: Properties for production.

## Security & Secrets
- **NEVER** commit plain-text passwords or API keys.
- Use the SmallRye Config secret encryption or environment variables for sensitive data.
- Check `application.properties` for any accidentally committed credentials before pushing.
