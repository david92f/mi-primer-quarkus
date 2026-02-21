package com.david.resource;

import com.david.dto.ProductoDTO;
import com.david.entity.Producto;
import com.david.service.ProductoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import java.util.List;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource {

    private static final Logger LOG = Logger.getLogger(ProductoResource.class);

    @Inject
    ProductoService productoService;

    @GET
    public List<Producto> getAllProductos() {
        LOG.info("GET /productos - Request received");
        List<Producto> productos = productoService.listAll();
        LOG.infof("GET /productos - Returning %d productos", productos.size());
        return productos;
    }

    @GET
    @Path("/{id}")
    public Producto getProductoById(@PathParam("id") Long id) {
        LOG.infof("GET /productos/%d - Request received", id);
        Producto producto = productoService.findById(id);
        if (producto == null) {
            LOG.warnf("Producto no encontrado con ID: %d", id);
            throw new NotFoundException("Producto no encontrado");
        }
        return producto;
    }

    @POST
    public Response createProducto(@Valid ProductoDTO productoDTO) {
        LOG.info("POST /productos - Request received");
        Producto producto = productoService.create(productoDTO);
        LOG.infof("POST /productos - Created producto with ID: %d", producto.id);
        return Response.status(Response.Status.CREATED).entity(producto).build();
    }
}
