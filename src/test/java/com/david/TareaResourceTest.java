package com.david;

import com.david.testresources.PostgreSQLTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@QuarkusTestResource(PostgreSQLTestResource.class)
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
    void testGetTareaById() {
        String json = """
            {
                "titulo": "Tarea para buscar",
                "descripcion": "Buscar esta tarea",
                "terminada": false
            }
            """;
        
        Integer tareaId = given()
            .contentType("application/json")
            .body(json)
            .when().post("/tareas")
            .then()
            .statusCode(201)
            .extract().path("id");
        
        given()
            .when().get("/tareas/" + tareaId)
            .then()
            .statusCode(200)
            .body("titulo", is("Tarea para buscar"));
    }

    @Test
    void testGetTareaNoEncontrada() {
        given()
            .when().get("/tareas/99999")
            .then()
            .statusCode(404);
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

    // ======== TESTS PUT ========

    @Test
    void testUpdateTarea() {
        String createJson = """
            {
                "titulo": "Tarea a actualizar",
                "descripcion": "Descripcion original",
                "terminada": false
            }
            """;
        
        Integer tareaId = given()
            .contentType("application/json")
            .body(createJson)
            .when().post("/tareas")
            .then()
            .statusCode(201)
            .extract().path("id");
        
        String updateJson = """
            {
                "titulo": "Tarea actualizada",
                "descripcion": "Nueva descripcion",
                "terminada": true
            }
            """;
        
        given()
            .contentType("application/json")
            .body(updateJson)
            .when().put("/tareas/" + tareaId)
            .then()
            .statusCode(200)
            .body("titulo", is("Tarea actualizada"))
            .body("terminada", is(true));
    }

    @Test
    void testUpdateTareaNoEncontrada() {
        String json = """
            {
                "titulo": "Tarea no existe",
                "descripcion": "No existe",
                "terminada": false
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().put("/tareas/99999")
            .then()
            .statusCode(404);
    }

    // ======== TESTS PATCH ========

    @Test
    void testPatchTarea() {
        String createJson = """
            {
                "titulo": "Tarea a parchear",
                "descripcion": "Descripcion original",
                "terminada": false
            }
            """;
        
        Integer tareaId = given()
            .contentType("application/json")
            .body(createJson)
            .when().post("/tareas")
            .then()
            .statusCode(201)
            .extract().path("id");
        
        String patchJson = """
            {
                "terminada": true
            }
            """;
        
        given()
            .contentType("application/json")
            .body(patchJson)
            .when().patch("/tareas/" + tareaId)
            .then()
            .statusCode(200)
            .body("terminada", is(true))
            .body("titulo", is("Tarea a parchear"));
    }

    @Test
    void testPatchTareaNoEncontrada() {
        String json = """
            {
                "titulo": "No existe"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().patch("/tareas/99999")
            .then()
            .statusCode(404);
    }

    // ======== TESTS DELETE ========

    @Test
    void testDeleteTarea() {
        String createJson = """
            {
                "titulo": "Tarea a eliminar",
                "descripcion": "Eliminar esta tarea",
                "terminada": false
            }
            """;
        
        Integer tareaId = given()
            .contentType("application/json")
            .body(createJson)
            .when().post("/tareas")
            .then()
            .statusCode(201)
            .extract().path("id");
        
        given()
            .when().delete("/tareas/" + tareaId)
            .then()
            .statusCode(204);
        
        given()
            .when().get("/tareas/" + tareaId)
            .then()
            .statusCode(404);
    }

    @Test
    void testDeleteTareaNoEncontrada() {
        given()
            .when().delete("/tareas/99999")
            .then()
            .statusCode(404);
    }
}
