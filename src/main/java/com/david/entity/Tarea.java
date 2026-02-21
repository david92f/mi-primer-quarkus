package com.david.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Tarea extends PanacheEntity {

    public String titulo;
    public String descripcion;
    public Boolean terminada;
}
