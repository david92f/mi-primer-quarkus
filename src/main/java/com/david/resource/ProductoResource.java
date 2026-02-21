package com.david.resource;

import com.david.dto.ProductoDTO;
import com.david.entity.Producto;
import com.david.service.ProductoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource {

    @Inject
    ProductoService productoService;

    @GET
    public List<Producto> listarTodos() {
        return productoService.listAll();
    }

    @POST
    public Response crear(@Valid ProductoDTO productoDTO) {
        Producto producto = productoService.create(productoDTO);
        return Response.status(Response.Status.CREATED).entity(producto).build();
    }
}
