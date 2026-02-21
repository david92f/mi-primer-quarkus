package com.david.service;

import com.david.dto.ProductoDTO;
import com.david.entity.Producto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProductoService {

    public List<Producto> listAll() {
        return Producto.listAll();
    }

    @Transactional
    public Producto create(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.nombre = dto.nombre;
        producto.precio = dto.precio;
        producto.stock = dto.stock;
        producto.persist();
        return producto;
    }

    public Producto findById(Long id) {
        return Producto.findById(id);
    }

    @Transactional
    public boolean delete(Long id) {
        return Producto.deleteById(id);
    }

    @Transactional
    public Producto update(Long id, ProductoDTO dto) {
        Producto producto = Producto.findById(id);
        if (producto == null) {
            return null;
        }
        producto.nombre = dto.nombre;
        producto.precio = dto.precio;
        producto.stock = dto.stock;
        return producto;
    }
}
