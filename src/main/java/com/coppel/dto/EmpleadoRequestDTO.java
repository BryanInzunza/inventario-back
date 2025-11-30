package com.coppel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpleadoRequestDTO {

    @NotBlank(message = "Nombre no puede estar vacío")
    @Size(min = 1, max = 100, message = "Nombre debe tener entre 1 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "Apellido no puede estar vacío")
    @Size(min = 1, max = 100, message = "Apellido debe tener entre 1 y 100 caracteres")
    private String apellido;

    @NotBlank(message = "Puesto no puede estar vacío")
    @Size(min = 1, max = 50, message = "Puesto debe tener entre 1 y 50 caracteres")
    private String puesto;
}