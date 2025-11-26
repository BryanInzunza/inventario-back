package com.coppel.entities;

   import jakarta.persistence.Column;
   import jakarta.persistence.Entity;
   import jakarta.persistence.Id;
   import jakarta.persistence.Table;
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;

   @Entity
   @Table(name = "Inventario") // Mapea a la tabla 'Inventario' en la base de datos
   @Data // Genera getters, setters, toString, equals y hashCode de Lombok
   @NoArgsConstructor // Genera un constructor sin argumentos de Lombok
   @AllArgsConstructor // Genera un constructor con todos los argumentos de Lombok
   public class Inventario {

       @Id // Marca este campo como la clave primaria
       @Column(name = "SKU") // Mapea al nombre de la columna en la BD
       private String sku; // El SKU es VARCHAR en la BD

       @Column(name = "Nombre", nullable = false)
       private String nombre;

       @Column(name = "Cantidad", nullable = false)
       private Integer cantidad;

   }