package com.david;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
class ProductoResourceTest {

    @Test
    void testListarTodos() {
        given()
            .when().get("/productos")
            .then()
            .statusCode(200)
            .body("$", is(notNullValue()));
    }

    @Test
    void testCrearProducto() {
        String json = """
            {
                "nombre": "Nuevo producto",
                "precio": 99.99,
                "stock": 10
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().post("/productos")
            .then()
            .statusCode(201)
            .body("nombre", is("Nuevo producto"))
            .body("precio", is(99.99f))
            .body("stock", is(10));
    }

    @Test
    void testCrearProductoValidation() {
        String json = """
            {
                "nombre": ""
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().post("/productos")
            .then()
            .statusCode(400);
    }

    @Test
    void testCrearProductoSinStock() {
        String json = """
            {
                "nombre": "Producto sin stock",
                "precio": 50.0
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().post("/productos")
            .then()
            .statusCode(201)
            .body("stock", is(nullValue()));
    }
}
