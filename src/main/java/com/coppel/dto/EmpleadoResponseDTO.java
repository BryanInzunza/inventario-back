package com.coppel.dto;

   import lombok.Data;

   @Data
   public class EmpleadoResponseDTO {
       private Integer idEmpleado;
       private String nombre;
       private String apellido;
       private String puesto;
   }