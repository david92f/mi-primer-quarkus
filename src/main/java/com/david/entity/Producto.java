package com.david.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
public class Producto extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    @NotNull
    @Column(nullable = false)
    public String nombre;
    
    @NotNull
    @Positive
    @Column(nullable = false)
    public Double precio;
    
    @Min(0)
    public Integer stock;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    public Categoria categoria;
    
    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    public Proveedor proveedor;
    
    public LocalDateTime createdAt;
    
    public LocalDateTime updatedAt;
    
    public Boolean deleted = false;
    
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public static java.util.List<Producto> listActive() {
        return list("deleted", false);
    }
    
    public static java.util.List<Producto> findActiveByName(String search) {
        return find("deleted = false and nombre like ?1", "%" + search + "%").list();
    }
}
