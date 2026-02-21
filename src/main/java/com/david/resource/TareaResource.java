package com.david.resource;

import com.david.dto.TareaDTO;
import com.david.entity.Tarea;
import com.david.service.TareaService;
import jakarta.inject.Inject;
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

    @Inject
    TareaService tareaService;

    @GET
    public List<Tarea> getAllTareas() {
        return tareaService.listAll();
    }

    @POST
    public Response createTarea(@Valid TareaDTO tareaDTO) {
        Tarea tarea = tareaService.create(tareaDTO);
        return Response.status(Response.Status.CREATED).entity(tarea).build();
    }
}
