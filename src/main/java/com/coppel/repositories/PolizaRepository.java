 package com.coppel.repositories;

   import com.coppel.entities.Poliza;
   import org.springframework.data.jpa.repository.JpaRepository;
   import org.springframework.stereotype.Repository;

   @Repository
   public interface PolizaRepository extends JpaRepository<Poliza, Integer> {
       
   }