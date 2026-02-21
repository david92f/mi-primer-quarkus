package com.david.resource;

import com.david.entity.Producto;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource {
    
    @GET
    public List<Producto> listarTodos() {
        return Producto.listAll();
    }
    
    @POST
    @Transactional
    public Producto crear(Producto producto) {
        producto.persist();
        return producto;
    }
    
}
