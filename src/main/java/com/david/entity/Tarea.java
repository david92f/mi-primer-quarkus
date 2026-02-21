package com.david.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Tarea extends PanacheEntity {

    @NotBlank
    @Column(nullable = false)
    public String titulo;
    
    public String descripcion;
    
    public Boolean terminada;
}
