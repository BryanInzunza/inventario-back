package com.coppel.services;

   import com.coppel.entities.Inventario;

   import java.util.List;
   import java.util.Optional;

   public interface InventarioService {
       List<Inventario> findAll();
       Optional<Inventario> findBySku(String sku);
       Inventario save(Inventario inventario);
       Inventario update(String sku, Inventario inventarioDetails);
       void deleteBySku(String sku);
   }