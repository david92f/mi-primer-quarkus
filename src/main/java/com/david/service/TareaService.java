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

    @Transactional
    public Tarea update(Long id, TareaDTO dto) {
        LOG.infof("Actualizando tarea ID: %d", id);
        Tarea tarea = Tarea.findById(id);
        if (tarea == null) {
            LOG.warnf("Tarea no encontrada con ID: %d", id);
            return null;
        }
        tarea.titulo = dto.titulo;
        tarea.descripcion = dto.descripcion;
        tarea.terminada = dto.terminada != null ? dto.terminada : false;
        tarea.persist();
        LOG.infof("Tarea actualizada: %s", tarea.titulo);
        return tarea;
    }

    @Transactional
    public Tarea patch(Long id, TareaDTO dto) {
        LOG.infof("Parcheando tarea ID: %d", id);
        Tarea tarea = Tarea.findById(id);
        if (tarea == null) {
            LOG.warnf("Tarea no encontrada con ID: %d", id);
            return null;
        }
        if (dto.titulo != null) {
            tarea.titulo = dto.titulo;
        }
        if (dto.descripcion != null) {
            tarea.descripcion = dto.descripcion;
        }
        if (dto.terminada != null) {
            tarea.terminada = dto.terminada;
        }
        tarea.persist();
        LOG.infof("Tarea parchada: %s", tarea.titulo);
        return tarea;
    }

    @Transactional
    public boolean delete(Long id) {
        LOG.infof("Eliminando tarea ID: %d", id);
        Tarea tarea = Tarea.findById(id);
        if (tarea == null) {
            LOG.warnf("Tarea no encontrada con ID: %d", id);
            return false;
        }
        tarea.delete();
        LOG.infof("Tarea eliminada: %d", id);
        return true;
    }
}
