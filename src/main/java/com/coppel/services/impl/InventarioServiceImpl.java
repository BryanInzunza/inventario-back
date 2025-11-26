package com.coppel.services.impl;

   import com.coppel.entities.Inventario;
   import com.coppel.repositories.InventarioRepository;
   import com.coppel.services.InventarioService;
   import org.springframework.stereotype.Service;

   import java.util.List;
   import java.util.Optional;

   @Service
   public class InventarioServiceImpl implements InventarioService {

       private final InventarioRepository inventarioRepository;

       public InventarioServiceImpl(InventarioRepository inventarioRepository) {
           this.inventarioRepository = inventarioRepository;
       }

       @Override
       public List<Inventario> findAll() {
           return inventarioRepository.findAll();
       }

       @Override
       public Optional<Inventario> findBySku(String sku) {
           return inventarioRepository.findById(sku);
       }

       @Override
       public Inventario save(Inventario inventario) {
           return inventarioRepository.save(inventario);
       }

       @Override
       public Inventario update(String sku, Inventario inventarioDetails) {
           // 1. Verificar si el inventario existe
           Inventario inventarioExistente = inventarioRepository.findById(sku)
                   .orElseThrow(() -> new RuntimeException("Inventario no encontrado con SKU: " + sku));

           // 2. Actualizar los campos necesarios
           inventarioExistente.setNombre(inventarioDetails.getNombre());
           inventarioExistente.setCantidad(inventarioDetails.getCantidad());

           // 3. Guardar el inventario actualizado
           return inventarioRepository.save(inventarioExistente);
       }

       @Override
       public void deleteBySku(String sku) {
           // 1. Verificar si el inventario existe antes de eliminar
           Inventario inventarioExistente = inventarioRepository.findById(sku)
                   .orElseThrow(() -> new RuntimeException("Inventario no encontrado con SKU: " + sku));
           inventarioRepository.delete(inventarioExistente);
       }
   }