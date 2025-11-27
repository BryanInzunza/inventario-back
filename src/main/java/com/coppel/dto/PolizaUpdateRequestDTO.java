package com.coppel.dto;

import lombok.Data;

@Data
public class PolizaUpdateRequestDTO {
    // Los campos que se pueden actualizar en una póliza
    private Integer idEmpleado;
    private String sku;
    private Integer cantidad;
}
