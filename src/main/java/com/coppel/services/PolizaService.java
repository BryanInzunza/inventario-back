package com.coppel.services;

   import com.coppel.dto.PolizaRequestDTO;
   import com.coppel.entities.Poliza;

   import java.util.List;
   import java.util.Optional;

   public interface PolizaService {
       Poliza generarPoliza(PolizaRequestDTO polizaRequest);
       void eliminarPoliza(Integer idPoliza);
       Optional<Poliza> findById(Integer idPoliza);
       List<Poliza> findAll();
   }