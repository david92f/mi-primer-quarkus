package com.david;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
class TareaResourceTest {

    @Test
    void testGetAllTareas() {
        given()
            .when().get("/tareas")
            .then()
            .statusCode(200)
            .body("$", is(notNullValue()));
    }

    @Test
    void testCreateTarea() {
        String json = """
            {
                "titulo": "Nueva tarea de prueba",
                "descripcion": "Esta es una tarea de prueba",
                "terminada": false
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().post("/tareas")
            .then()
            .statusCode(201)
            .body("titulo", is("Nueva tarea de prueba"));
    }

    @Test
    void testCreateTareaValidation() {
        String json = """
            {
                "titulo": ""
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().post("/tareas")
            .then()
            .statusCode(400);
    }
}
