package com.coppel.repositories;

import com.coppel.entities.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Integer> {

    @Query("SELECT COALESCE(SUM(p.cantidad), 0) FROM Poliza p WHERE p.inventario.sku = :sku")
    Integer sumCantidadBySku(@Param("sku") String sku);
}