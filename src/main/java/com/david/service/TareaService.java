package com.david.service;

import com.david.dto.TareaDTO;
import com.david.entity.Tarea;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import java.util.List;

@ApplicationScoped
public class TareaService {

    private static final Logger LOG = Logger.getLogger(TareaService.class);

    public List<Tarea> listAll() {
        LOG.debug("Listando todas las tareas");
        return Tarea.listAll();
    }

    @Transactional
    public Tarea create(TareaDTO dto) {
        LOG.infof("Creando tarea: %s", dto.titulo);
        Tarea tarea = new Tarea();
        tarea.titulo = dto.titulo;
        tarea.descripcion = dto.descripcion;
        tarea.terminada = dto.terminada != null ? dto.terminada : false;
        tarea.persist();
        LOG.infof("Tarea creada con ID: %d", tarea.id);
        return tarea;
    }

    public Tarea findById(Long id) {
        if (id == null) {
            LOG.warn("findById llamado con id null");
            return null;
        }
        LOG.debugf("Buscando tarea por ID: %d", id);
        return Tarea.findById(id);
    }
}
