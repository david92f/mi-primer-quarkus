package com.david.service;

import com.david.dto.TareaDTO;
import com.david.entity.Tarea;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TareaService {

    public List<Tarea> listAll() {
        return Tarea.listAll();
    }

    @Transactional
    public Tarea create(TareaDTO dto) {
        Tarea tarea = new Tarea();
        tarea.titulo = dto.titulo;
        tarea.descripcion = dto.descripcion;
        tarea.terminada = dto.terminada != null ? dto.terminada : false;
        tarea.persist();
        return tarea;
    }

    public Tarea findById(Long id) {
        if (id == null) {
            return null;
        }
        return Tarea.findById(id);
    }

    @Transactional
    public boolean delete(Long id) {
        return Tarea.deleteById(id);
    }

    @Transactional
    public Tarea update(Long id, TareaDTO dto) {
        Tarea tarea = Tarea.findById(id);
        if (tarea == null) {
            return null;
        }
        tarea.titulo = dto.titulo;
        tarea.descripcion = dto.descripcion;
        if (dto.terminada != null) {
            tarea.terminada = dto.terminada;
        }
        return tarea;
    }
}
