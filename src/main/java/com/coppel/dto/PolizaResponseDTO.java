 package com.coppel.dto;

   import lombok.Data;
   import java.time.LocalDateTime;

   @Data
   public class PolizaResponseDTO {
       private Integer idPoliza;
       private EmpleadoResponseDTO empleado;
       private InventarioResponseDTO inventario; 
       private Integer cantidad;
       private LocalDateTime fecha;
   }