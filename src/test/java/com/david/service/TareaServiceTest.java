package com.david.service;

import com.david.dto.TareaDTO;
import com.david.entity.Tarea;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TareaServiceTest {

    @Inject
    TareaService tareaService;

    @BeforeEach
    @Transactional
    void setup() {
        Tarea.deleteAll();
    }

    @Test
    @Transactional
    void testListAll() {
        TareaDTO dto1 = new TareaDTO("Tarea 1", null, false);
        tareaService.create(dto1);
        
        TareaDTO dto2 = new TareaDTO("Tarea 2", null, false);
        tareaService.create(dto2);
        
        List<Tarea> tareas = tareaService.listAll();
        assertEquals(2, tareas.size());
    }

    @Test
    @Transactional
    void testFindById() {
        TareaDTO dto = new TareaDTO("Tarea test", "Descripcion test", false);
        Tarea created = tareaService.create(dto);
        
        Tarea found = tareaService.findById(created.id);
        assertNotNull(found);
        assertEquals("Tarea test", found.titulo);
    }

    @Test
    void testFindByIdNotFound() {
        Tarea found = tareaService.findById(99999L);
        assertNull(found);
    }

    @Test
    void testFindByIdNull() {
        Tarea found = tareaService.findById(null);
        assertNull(found);
    }

    @Test
    @Transactional
    void testCreate() {
        TareaDTO dto = new TareaDTO("Nueva tarea", "Descripcion", false);
        Tarea tarea = tareaService.create(dto);
        assertNotNull(tarea.id);
        assertEquals("Nueva tarea", tarea.titulo);
        assertEquals("Descripcion", tarea.descripcion);
        assertFalse(tarea.terminada);
    }

    @Test
    @Transactional
    void testCreateWithDefaults() {
        TareaDTO dto = new TareaDTO("Tarea simple", null, null);
        Tarea tarea = tareaService.create(dto);
        assertNotNull(tarea.id);
        assertEquals("Tarea simple", tarea.titulo);
        assertNull(tarea.descripcion);
        assertFalse(tarea.terminada);
    }

    @Test
    @Transactional
    void testUpdate() {
        TareaDTO createDto = new TareaDTO("Original", "Original desc", false);
        Tarea tarea = tareaService.create(createDto);
        
        TareaDTO updateDto = new TareaDTO("Actualizado", "Nueva desc", true);
        Tarea updated = tareaService.update(tarea.id, updateDto);
        assertNotNull(updated);
        assertEquals("Actualizado", updated.titulo);
        assertEquals("Nueva desc", updated.descripcion);
        assertTrue(updated.terminada);
    }

    @Test
    void testUpdateNotFound() {
        TareaDTO dto = new TareaDTO("Test", "Test", true);
        Tarea updated = tareaService.update(99999L, dto);
        assertNull(updated);
    }

    @Test
    @Transactional
    void testPatch() {
        TareaDTO createDto = new TareaDTO("Original", null, false);
        Tarea tarea = tareaService.create(createDto);
        
        TareaDTO patchDto = new TareaDTO(null, null, true);
        Tarea patched = tareaService.patch(tarea.id, patchDto);
        assertNotNull(patched);
        assertEquals("Original", patched.titulo);
        assertTrue(patched.terminada);
    }

    @Test
    void testPatchNotFound() {
        TareaDTO dto = new TareaDTO("Test", null, null);
        Tarea patched = tareaService.patch(99999L, dto);
        assertNull(patched);
    }

    @Test
    @Transactional
    void testDelete() {
        TareaDTO createDto = new TareaDTO("Para eliminar", null, false);
        Tarea tarea = tareaService.create(createDto);
        Long id = tarea.id;
        
        boolean deleted = tareaService.delete(id);
        assertTrue(deleted);
        
        Tarea found = tareaService.findById(id);
        assertNull(found);
    }

    @Test
    void testDeleteNotFound() {
        boolean deleted = tareaService.delete(99999L);
        assertFalse(deleted);
    }
}
