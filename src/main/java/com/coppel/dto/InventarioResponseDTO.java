 package com.coppel.dto;

   import lombok.Data;

   @Data
   public class InventarioResponseDTO {
       private String sku;
       private String nombre;
       private Integer cantidad;
   }