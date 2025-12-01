package com.coppel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolizaUpdateRequestDTO {

    private Integer idEmpleado;

    @NotBlank(message = "SKU no puede estar vacío")
    @Size(min = 1, max = 50, message = "SKU debe tener entre 1 y 50 caracteres")
    private String sku;

    @NotNull(message = "Cantidad no puede ser nula")
    @Min(value = 1, message = "Cantidad debe ser mayor a 0")
    private Integer cantidad;
}