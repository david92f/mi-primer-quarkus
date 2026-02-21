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
            .body("content", is(notNullValue()));
    }

    @Test
    void testPaginacion() {
        given()
            .queryParam("page", 0)
            .queryParam("size", 2)
            .when().get("/productos")
            .then()
            .statusCode(200)
            .body("content", is(notNullValue()))
            .body("page", is(0))
            .body("size", is(2))
            .body("totalElements", is(notNullValue()));
    }

    @Test
    void testOrdenamiento() {
        given()
            .queryParam("sortBy", "nombre")
            .queryParam("descending", true)
            .when().get("/productos")
            .then()
            .statusCode(200)
            .body("content", is(notNullValue()));
    }

    @Test
    void testBusqueda() {
        given()
            .queryParam("search", "producto")
            .when().get("/productos")
            .then()
            .statusCode(200)
            .body("content", is(notNullValue()));
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

    @Test
    void testCrearProductoPrecioNegativo() {
        String json = """
            {
                "nombre": "Producto precio negativo",
                "precio": -10.0,
                "stock": 5
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
    void testCrearProductoStockNegativo() {
        String json = """
            {
                "nombre": "Producto stock negativo",
                "precio": 10.0,
                "stock": -5
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
    void testProductoNoEncontrado() {
        given()
            .when().get("/productos/99999")
            .then()
            .statusCode(404);
    }

    @Test
    void testCrearProductoSinNombre() {
        String json = """
            {
                "precio": 10.0
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
    void testCrearProductoSinPrecio() {
        String json = """
            {
                "nombre": "Producto sin precio"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().post("/productos")
            .then()
            .statusCode(400);
    }
}
