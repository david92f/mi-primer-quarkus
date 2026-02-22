package com.david.service;

import com.david.dto.ProductoDTO;
import com.david.entity.Producto;
import com.david.testresources.PostgreSQLTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(PostgreSQLTestResource.class)
class ProductoServiceTest {

    @Inject
    ProductoService productoService;

    @BeforeEach
    @Transactional
    void setup() {
        Producto.deleteAll();
    }

    @Test
    void testCreateProducto() {
        ProductoDTO dto = new ProductoDTO("Test Producto", 99.99, 10);
        
        Producto result = productoService.create(dto);
        
        assertNotNull(result);
        assertNotNull(result.id);
        assertEquals("Test Producto", result.nombre);
        assertEquals(99.99, result.precio);
        assertEquals(10, result.stock);
    }

    @Test
    void testCreateProductoConStockNulo() {
        ProductoDTO dto = new ProductoDTO("Sin stock", 50.0, null);
        
        Producto result = productoService.create(dto);
        
        assertNotNull(result);
        assertNull(result.stock);
    }

    @Test
    void testFindByIdExistente() {
        ProductoDTO dto = new ProductoDTO("Buscar test", 25.0, 5);
        Producto created = productoService.create(dto);
        
        Producto found = productoService.findById(created.id);
        
        assertNotNull(found);
        assertEquals(created.id, found.id);
        assertEquals("Buscar test", found.nombre);
    }

    @Test
    void testFindByIdNoExistente() {
        Producto result = productoService.findById(99999L);
        
        assertNull(result);
    }

    @Test
    void testFindByIdNull() {
        Producto result = productoService.findById(null);
        
        assertNull(result);
    }

    @Test
    void testListAll() {
        productoService.create(new ProductoDTO("Producto 1", 10.0, 1));
        productoService.create(new ProductoDTO("Producto 2", 20.0, 2));
        productoService.create(new ProductoDTO("Producto 3", 30.0, 3));
        
        List<Producto> resultados = productoService.listAll();
        
        assertEquals(3, resultados.size());
    }

    @Test
    void testListAllVacio() {
        List<Producto> resultados = productoService.listAll();
        
        assertTrue(resultados.isEmpty());
    }

    @Test
    void testFindAllConPaginacion() {
        for (int i = 1; i <= 15; i++) {
            productoService.create(new ProductoDTO("Producto " + i, i * 10.0, i));
        }
        
        List<Producto> page0 = productoService.findAll(0, 5, "nombre", false, null);
        List<Producto> page2 = productoService.findAll(2, 5, "nombre", false, null);
        
        assertEquals(5, page0.size());
        assertEquals(5, page2.size());
    }

    @Test
    void testFindAllConBusqueda() {
        productoService.create(new ProductoDTO("Camara Sony", 500.0, 10));
        productoService.create(new ProductoDTO("Camara Canon", 450.0, 5));
        productoService.create(new ProductoDTO("Laptop Dell", 1200.0, 3));
        
        List<Producto> resultados = productoService.findAll(0, 10, "id", false, "Camara");
        
        assertEquals(2, resultados.size());
    }

    @Test
    void testCount() {
        assertEquals(0, productoService.count(null));
        
        productoService.create(new ProductoDTO("Producto 1", 10.0, 1));
        productoService.create(new ProductoDTO("Producto 2", 20.0, 2));
        
        assertEquals(2, productoService.count(null));
    }

    @Test
    void testCountConBusqueda() {
        productoService.create(new ProductoDTO("Camara Sony", 500.0, 10));
        productoService.create(new ProductoDTO("Camara Canon", 450.0, 5));
        productoService.create(new ProductoDTO("Laptop Dell", 1200.0, 3));
        
        assertEquals(2, productoService.count("Camara"));
        assertEquals(1, productoService.count("Laptop"));
        assertEquals(0, productoService.count("Inexistente"));
    }
}
