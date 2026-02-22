package com.david;

import com.david.testresources.PostgreSQLTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@QuarkusTestResource(PostgreSQLTestResource.class)
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

    // ======== TESTS PUT ========
    
    @Test
    void testUpdateProducto() {
        String createJson = """
            {
                "nombre": "Producto a actualizar",
                "precio": 50.0,
                "stock": 10
            }
            """;
        
        Integer productoId = given()
            .contentType("application/json")
            .body(createJson)
            .when().post("/productos")
            .then()
            .statusCode(201)
            .extract().path("id");
        
        String updateJson = """
            {
                "nombre": "Producto actualizado",
                "precio": 150.0,
                "stock": 25
            }
            """;
        
        given()
            .contentType("application/json")
            .body(updateJson)
            .when().put("/productos/" + productoId)
            .then()
            .statusCode(200)
            .body("nombre", is("Producto actualizado"))
            .body("precio", is(150.0f))
            .body("stock", is(25));
    }

    @Test
    void testUpdateProductoNoEncontrado() {
        String json = """
            {
                "nombre": "Producto no existe",
                "precio": 100.0,
                "stock": 5
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().put("/productos/99999")
            .then()
            .statusCode(404);
    }

    @Test
    void testUpdateProductoInvalid() {
        String json = """
            {
                "nombre": "",
                "precio": -10.0,
                "stock": 5
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().put("/productos/1")
            .then()
            .statusCode(400);
    }

    // ======== TESTS PATCH ========
    
    @Test
    void testPatchProducto() {
        String createJson = """
            {
                "nombre": "Producto patch",
                "precio": 50.0,
                "stock": 10
            }
            """;
        
        Integer productoId = given()
            .contentType("application/json")
            .body(createJson)
            .when().post("/productos")
            .then()
            .statusCode(201)
            .extract().path("id");
        
        String patchJson = """
            {
                "nombre": "Producto parchado"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(patchJson)
            .when().patch("/productos/" + productoId)
            .then()
            .statusCode(200)
            .body("nombre", is("Producto parchado"))
            .body("precio", is(50.0f));
    }

    @Test
    void testPatchProductoParcial() {
        String createJson = """
            {
                "nombre": "Producto parcial",
                "precio": 100.0,
                "stock": 20
            }
            """;
        
        Integer productoId = given()
            .contentType("application/json")
            .body(createJson)
            .when().post("/productos")
            .then()
            .statusCode(201)
            .extract().path("id");
        
        String patchJson = """
            {
                "stock": 50
            }
            """;
        
        given()
            .contentType("application/json")
            .body(patchJson)
            .when().patch("/productos/" + productoId)
            .then()
            .statusCode(200)
            .body("stock", is(50))
            .body("precio", is(100.0f));
    }

    @Test
    void testPatchProductoNoEncontrado() {
        String json = """
            {
                "nombre": "No existe"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(json)
            .when().patch("/productos/99999")
            .then()
            .statusCode(404);
    }

    // ======== TESTS DELETE ========
    
    @Test
    void testDeleteProducto() {
        String createJson = """
            {
                "nombre": "Producto a eliminar",
                "precio": 50.0,
                "stock": 10
            }
            """;
        
        Integer productoId = given()
            .contentType("application/json")
            .body(createJson)
            .when().post("/productos")
            .then()
            .statusCode(201)
            .extract().path("id");
        
        given()
            .when().delete("/productos/" + productoId)
            .then()
            .statusCode(204);
        
        given()
            .when().get("/productos/" + productoId)
            .then()
            .statusCode(404);
    }

    @Test
    void testDeleteProductoNoEncontrado() {
        given()
            .when().delete("/productos/99999")
            .then()
            .statusCode(404);
    }
}
