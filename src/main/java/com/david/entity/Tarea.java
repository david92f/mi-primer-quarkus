package com.david.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Tarea extends PanacheEntity {

    public String titulo;
    public String descripcion;
    public Boolean terminada;

    public Tarea() {
    }

    public Tarea(String titulo, String descripcion, Boolean terminada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.terminada = terminada;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", terminada=" + terminada +
                '}';
    }
}
