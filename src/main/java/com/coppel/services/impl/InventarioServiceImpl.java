package com.coppel.services.impl;

import com.coppel.entities.Inventario;
import com.coppel.repositories.InventarioRepository;
import com.coppel.services.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.coppel.execeptions.ResourceNotFoundException;
import com.coppel.execeptions.ResourceAlreadyExistsException;

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
    public Inventario findBySku(String sku) {
        return inventarioRepository.findById(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con SKU: " + sku));
    }

    @Override
    public Inventario save(Inventario inventario) {
        // Validar que el artículo no exista previamente
        if (inventario.getSku() != null && inventarioRepository.existsById(inventario.getSku())) {
            throw new ResourceAlreadyExistsException(
                    "El artículo con SKU " + inventario.getSku() + " ya existe. Use PUT para actualizar.");
        }
        return inventarioRepository.save(inventario);
    }

    @Override
    @Transactional
    public Inventario update(String sku, Inventario inventarioDetails) {
        // 1. Verificar si el inventario existe
        Inventario inventarioExistente = inventarioRepository.findById(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado con SKU: " + sku));

        // 2. Actualizar los campos necesarios
        inventarioExistente.setNombre(inventarioDetails.getNombre());
        inventarioExistente.setCantidad(inventarioDetails.getCantidad());

        // 3. Guardar el inventario actualizado
        return inventarioRepository.save(inventarioExistente);
    }

    @Override
    @Transactional
    public Inventario deleteBySku(String sku) {
        // 1. Verificar si el inventario existe antes de eliminar
        Inventario inventarioExistente = inventarioRepository.findById(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado con SKU: " + sku));
        inventarioRepository.delete(inventarioExistente);
        return inventarioExistente;
    }
}