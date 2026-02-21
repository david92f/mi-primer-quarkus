package com.david.resource;

import com.david.dto.TareaDTO;
import com.david.entity.Tarea;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;
import java.util.List;

@Path("/tareas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TareaResource {

    @GET
    public List<Tarea> getAllTareas() {
        return Tarea.listAll();
    }

    @POST
    @Transactional
    public Response createTarea(@Valid TareaDTO tareaDTO) {
        Tarea tarea = new Tarea();
        tarea.titulo = tareaDTO.titulo;
        tarea.descripcion = tareaDTO.descripcion;
        tarea.terminada = tareaDTO.terminada != null ? tareaDTO.terminada : false;
        
        tarea.persist();
        
        return Response.status(Response.Status.CREATED).entity(tarea).build();
    }
}
