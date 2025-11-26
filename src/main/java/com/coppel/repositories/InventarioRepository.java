package com.coppel.repositories;

   import com.coppel.entities.Inventario;
   import org.springframework.data.jpa.repository.JpaRepository;
   import org.springframework.stereotype.Repository;

   @Repository
   public interface InventarioRepository extends JpaRepository<Inventario, String> {
      
   }