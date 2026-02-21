package com.david.resource;

import com.david.dto.PagedResponse;
import com.david.dto.ProductoDTO;
import com.david.entity.Producto;
import com.david.service.ProductoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import java.util.List;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductoResource {

    private static final Logger LOG = Logger.getLogger(ProductoResource.class);

    @Inject
    ProductoService productoService;

    @GET
    @Operation(summary = "Listar productos con paginación", description = "Retorna una lista paginada de productos con filtros opcionales")
    @APIResponse(responseCode = "200", description = "Lista de productos")
    public PagedResponse<Producto> getAllProductos(
            @Parameter(description = "Número de página (0-based)") @DefaultValue("0") @QueryParam("page") int page,
            @Parameter(description = "Tamaño de página") @DefaultValue("10") @QueryParam("size") int size,
            @Parameter(description = "Campo por el cual ordenar") @DefaultValue("id") @QueryParam("sortBy") String sortBy,
            @Parameter(description = "Orden descendente") @DefaultValue("false") @QueryParam("descending") boolean descending,
            @Parameter(description = "Buscar por nombre") @QueryParam("search") String search
    ) {
        LOG.infof("GET /productos - Request received - page: %d, size: %d, sortBy: %s, search: %s", page, size, sortBy, search);
        
        List<Producto> productos = productoService.findAll(page, size, sortBy, descending, search);
        long total = productoService.count(search);
        
        LOG.infof("GET /productos - Returning %d productos de %d total", productos.size(), total);
        return PagedResponse.of(productos, page, size, total);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico por su ID")
    @APIResponse(responseCode = "200", description = "Producto encontrado", content = @Content(mediaType = "application/json"))
    @APIResponse(responseCode = "404", description = "Producto no encontrado")
    public Producto getProductoById(@Parameter(description = "ID del producto", required = true) @PathParam("id") Long id) {
        LOG.infof("GET /productos/%d - Request received", id);
        Producto producto = productoService.findById(id);
        if (producto == null) {
            LOG.warnf("Producto no encontrado con ID: %d", id);
            throw new NotFoundException("Producto no encontrado");
        }
        return producto;
    }

    @POST
    @Operation(summary = "Crear producto", description = "Crea un nuevo producto")
    @APIResponse(responseCode = "201", description = "Producto creado exitosamente")
    @APIResponse(responseCode = "400", description = "Datos de producto inválidos")
    public Response createProducto(@Valid ProductoDTO productoDTO) {
        LOG.info("POST /productos - Request received");
        Producto producto = productoService.create(productoDTO);
        LOG.infof("POST /productos - Created producto with ID: %d", producto.id);
        return Response.status(Response.Status.CREATED).entity(producto).build();
    }
}
