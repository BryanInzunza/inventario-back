package com.coppel.services;

import com.coppel.entities.Inventario;

import java.util.List;

public interface InventarioService {
    List<Inventario> findAll();

    Inventario findBySku(String sku);

    Inventario save(Inventario inventario);

    Inventario update(String sku, Inventario inventarioDetails);

    Inventario deleteBySku(String sku);
}