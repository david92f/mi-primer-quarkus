package com.david.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductoDTO {

    public Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    public String nombre;

    @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
    public Double precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    public Integer stock;

    public ProductoDTO() {
    }

    public ProductoDTO(String nombre, Double precio, Integer stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
}
