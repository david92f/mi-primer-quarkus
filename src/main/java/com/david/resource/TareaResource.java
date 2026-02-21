package com.david.resource;

import com.david.dto.TareaDTO;
import com.david.entity.Tarea;
import com.david.service.TareaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener tarea por ID", description = "Retorna una tarea específica por su ID")
    @APIResponse(responseCode = "200", description = "Tarea encontrada", content = @Content(mediaType = "application/json"))
    @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    public Tarea getTareaById(@Parameter(description = "ID de la tarea", required = true) @PathParam("id") Long id) {
        LOG.infof("GET /tareas/%d - Request received", id);
        Tarea tarea = tareaService.findById(id);
        if (tarea == null) {
            LOG.warnf("Tarea no encontrada con ID: %d", id);
            throw new NotFoundException("Tarea no encontrada");
        }
        return tarea;
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

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar tarea", description = "Actualiza completamente una tarea existente")
    @APIResponse(responseCode = "200", description = "Tarea actualizada")
    @APIResponse(responseCode = "400", description = "Datos de tarea inválidos")
    @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    public Response updateTarea(
            @Parameter(description = "ID de la tarea", required = true) @PathParam("id") Long id,
            @Valid TareaDTO tareaDTO) {
        LOG.infof("PUT /tareas/%d - Request received", id);
        Tarea tarea = tareaService.update(id, tareaDTO);
        if (tarea == null) {
            LOG.warnf("Tarea no encontrada con ID: %d", id);
            throw new NotFoundException("Tarea no encontrada");
        }
        LOG.infof("PUT /tareas/%d - Updated successfully", id);
        return Response.ok(tarea).build();
    }

    @PATCH
    @Path("/{id}")
    @Operation(summary = "Actualizar tarea parcialmente", description = "Actualiza parcialmente una tarea existente")
    @APIResponse(responseCode = "200", description = "Tarea actualizada")
    @APIResponse(responseCode = "400", description = "Datos de tarea inválidos")
    @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    public Response patchTarea(
            @Parameter(description = "ID de la tarea", required = true) @PathParam("id") Long id,
            TareaDTO tareaDTO) {
        LOG.infof("PATCH /tareas/%d - Request received", id);
        Tarea tarea = tareaService.patch(id, tareaDTO);
        if (tarea == null) {
            LOG.warnf("Tarea no encontrada con ID: %d", id);
            throw new NotFoundException("Tarea no encontrada");
        }
        LOG.infof("PATCH /tareas/%d - Patched successfully", id);
        return Response.ok(tarea).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea por su ID")
    @APIResponse(responseCode = "204", description = "Tarea eliminada exitosamente")
    @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    public Response deleteTarea(@Parameter(description = "ID de la tarea", required = true) @PathParam("id") Long id) {
        LOG.infof("DELETE /tareas/%d - Request received", id);
        boolean deleted = tareaService.delete(id);
        if (!deleted) {
            LOG.warnf("Tarea no encontrada con ID: %d", id);
            throw new NotFoundException("Tarea no encontrada");
        }
        LOG.infof("DELETE /tareas/%d - Deleted successfully", id);
        return Response.noContent().build();
    }
}
