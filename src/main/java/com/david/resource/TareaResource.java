package com.david.resource;

import com.david.dto.TareaDTO;
import com.david.entity.Tarea;
import com.david.service.TareaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import java.util.List;

@Path("/tareas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tareas", description = "Gestión de tareas")
public class TareaResource {

    private static final Logger LOG = Logger.getLogger(TareaResource.class);

    @Inject
    TareaService tareaService;

    @GET
    @Operation(summary = "Listar todas las tareas", description = "Retorna la lista completa de tareas")
    @APIResponse(responseCode = "200", description = "Lista de tareas")
    public List<Tarea> getAllTareas() {
        LOG.info("GET /tareas - Request received");
        List<Tarea> tareas = tareaService.listAll();
        LOG.infof("GET /tareas - Returning %d tareas", tareas.size());
        return tareas;
    }

    @POST
    @Operation(summary = "Crear tarea", description = "Crea una nueva tarea")
    @APIResponse(responseCode = "201", description = "Tarea creada exitosamente")
    @APIResponse(responseCode = "400", description = "Datos de tarea inválidos")
    public Response createTarea(@Valid TareaDTO tareaDTO) {
        LOG.info("POST /tareas - Request received");
        Tarea tarea = tareaService.create(tareaDTO);
        LOG.infof("POST /tareas - Created tarea with ID: %d", tarea.id);
        return Response.status(Response.Status.CREATED).entity(tarea).build();
    }
}
