package com.david.resource;

import com.david.dto.ProductoDTO;
import com.david.entity.Producto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    public Producto crear(@Valid ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.nombre = productoDTO.nombre;
        producto.precio = productoDTO.precio;
        producto.stock = productoDTO.stock;
        producto.persist();
        return producto;
    }
    
}
