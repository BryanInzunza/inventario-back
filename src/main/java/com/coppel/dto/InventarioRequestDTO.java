package com.coppel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InventarioRequestDTO {

    @NotBlank(message = "SKU no puede estar vacío")
    @Size(min = 1, max = 50, message = "SKU debe tener entre 1 y 50 caracteres")
    private String sku;

    @NotBlank(message = "Nombre no puede estar vacío")
    @Size(min = 1, max = 100, message = "Nombre debe tener entre 1 y 100 caracteres")
    private String nombre;

    @NotNull(message = "Cantidad no puede ser nula")
    @Min(value = 0, message = "Cantidad no puede ser negativa")
    private Integer cantidad;
}