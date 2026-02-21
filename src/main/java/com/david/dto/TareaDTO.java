package com.david.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TareaDTO {

    public Long id;

    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 100, message = "El titulo no puede exceder 100 caracteres")
    public String titulo;

    @Size(max = 500, message = "La descripcion no puede exceder 500 caracteres")
    public String descripcion;

    public Boolean terminada = false;

    public TareaDTO() {
    }

    public TareaDTO(String titulo, String descripcion, Boolean terminada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.terminada = terminada;
    }
}
