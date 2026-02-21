package com.david.service;

import com.david.dto.ProductoDTO;
import com.david.entity.Producto;
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
