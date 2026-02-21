package com.david.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Producto extends PanacheEntity {
    
    @NotNull
    @Column(nullable = false)
    public String nombre;
    
    @NotNull
    @Positive
    @Column(nullable = false)
    public Double precio;
    
    @Min(0)
    public Integer stock;
    
}
