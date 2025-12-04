package com.coppel.services.impl;

import com.coppel.entities.Inventario;
import com.coppel.repositories.InventarioRepository;
import com.coppel.repositories.PolizaRepository;
import com.coppel.services.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.coppel.execeptions.ResourceNotFoundException;
import com.coppel.execeptions.ResourceAlreadyExistsException;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;
    private final PolizaRepository polizaRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository, PolizaRepository polizaRepository) {
        this.inventarioRepository = inventarioRepository;
        this.polizaRepository = polizaRepository;
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

        // 2. VALIDACIÓN CRÍTICA: Calcular total asignado en pólizas activas
        Integer cantidadAsignadaEnPolizas = polizaRepository.sumCantidadBySku(sku);

        // 3. Validar que la nueva cantidad sea suficiente para cubrir pólizas activas
        if (inventarioDetails.getCantidad() < cantidadAsignadaEnPolizas) {
            throw new IllegalStateException(
                    String.format(
                            "No se puede reducir el inventario a %d unidades. " +
                                    "Existen %d unidades asignadas en pólizas activas. " +
                                    "Primero debe liberar o reducir las pólizas correspondientes.",
                            inventarioDetails.getCantidad(),
                            cantidadAsignadaEnPolizas));
        }

        // 4. Actualizar los campos necesarios
        inventarioExistente.setNombre(inventarioDetails.getNombre());
        inventarioExistente.setCantidad(inventarioDetails.getCantidad());

        // 5. Guardar el inventario actualizado
        return inventarioRepository.save(inventarioExistente);
    }

    @Override
    @Transactional
    public Inventario deleteBySku(String sku) {
        // 1. Verificar si el inventario existe antes de eliminar
        Inventario inventarioExistente = inventarioRepository.findById(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario no encontrado con SKU: " + sku));

        // 2. VALIDACIÓN: No se puede eliminar si hay pólizas activas
        Integer cantidadAsignadaEnPolizas = polizaRepository.sumCantidadBySku(sku);

        if (cantidadAsignadaEnPolizas > 0) {
            throw new IllegalStateException(
                    String.format(
                            "No se puede eliminar el artículo '%s'. " +
                                    "Existen %d unidades asignadas en pólizas activas. " +
                                    "Primero debe eliminar o reasignar todas las pólizas.",
                            inventarioExistente.getNombre(),
                            cantidadAsignadaEnPolizas));
        }

        inventarioRepository.delete(inventarioExistente);
        return inventarioExistente;
    }
}