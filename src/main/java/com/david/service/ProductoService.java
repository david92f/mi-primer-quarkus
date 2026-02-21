package com.david.service;

import com.david.dto.ProductoDTO;
import com.david.entity.Producto;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import java.util.List;

@ApplicationScoped
public class ProductoService {

    private static final Logger LOG = Logger.getLogger(ProductoService.class);

    public List<Producto> listAll() {
        LOG.debug("Listando todos los productos");
        return Producto.listAll();
    }

    public List<Producto> findAll(int page, int size, String sortBy, boolean descending, String search) {
        LOG.debugf("Listando productos - page: %d, size: %d, sort: %s, search: %s", page, size, sortBy, search);
        
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        String searchFilter = search != null && !search.isBlank() 
            ? "nombre like ?1" 
            : null;
        
        List<Producto> productos = search != null && !search.isBlank()
            ? Producto.find(searchFilter, sort, "%" + search + "%").page(Page.of(page, size)).list()
            : Producto.findAll(sort).page(Page.of(page, size)).list();
        
        LOG.debugf("Encontrados %d productos", productos.size());
        return productos;
    }

    public long count(String search) {
        String searchFilter = search != null && !search.isBlank() 
            ? "nombre like ?1" 
            : null;
        
        return search != null && !search.isBlank()
            ? Producto.count(searchFilter, "%" + search + "%")
            : Producto.count();
    }

    @Transactional
    public Producto create(ProductoDTO dto) {
        LOG.infof("Creando producto: %s", dto.nombre);
        Producto producto = new Producto();
        producto.nombre = dto.nombre;
        producto.precio = dto.precio;
        producto.stock = dto.stock;
        producto.persist();
        LOG.infof("Producto creado con ID: %d", producto.id);
        return producto;
    }

    public Producto findById(Long id) {
        if (id == null) {
            LOG.warn("findById llamado con id null");
            return null;
        }
        LOG.debugf("Buscando producto por ID: %d", id);
        return Producto.findById(id);
    }
}
