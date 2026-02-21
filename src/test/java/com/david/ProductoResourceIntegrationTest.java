package com.david;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
class ProductoResourceIntegrationTest {

    @Test
    void testHappyPath() {
        String json = """
            {
                "nombre": "Producto Integracion",
                "precio": 149.99,
                "stock": 25
            }
            """;

        Integer productoId = given()
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .post("/productos")
            .then()
            .statusCode(201)
            .body("nombre", is("Producto Integracion"))
            .body("precio", is(149.99f))
            .body("stock", is(25))
            .body("id", notNullValue())
            .extract()
            .path("id");

        given()
            .when()
            .get("/productos")
            .then()
            .statusCode(200)
            .body("content.size()", greaterThan(0))
            .body("content.find { it.id == " + productoId + " }.nombre", is("Producto Integracion"));

        given()
            .when()
            .get("/productos/" + productoId)
            .then()
            .statusCode(200)
            .body("id", is(productoId.intValue()))
            .body("nombre", is("Producto Integracion"));
    }
}
