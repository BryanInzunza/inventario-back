package com.coppel.services.impl;

import com.coppel.dto.PolizaRequestDTO;
import com.coppel.entities.Empleado;
import com.coppel.entities.Inventario;
import com.coppel.entities.Poliza;
import com.coppel.repositories.EmpleadoRepository;
import com.coppel.repositories.InventarioRepository;
import com.coppel.repositories.PolizaRepository;
import com.coppel.services.PolizaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PolizaServiceImpl implements PolizaService {

    private final PolizaRepository polizaRepository;
    private final InventarioRepository inventarioRepository;
    private final EmpleadoRepository empleadoRepository;

    public PolizaServiceImpl(PolizaRepository polizaRepository, InventarioRepository inventarioRepository,
            EmpleadoRepository empleadoRepository) {
        this.polizaRepository = polizaRepository;
        this.inventarioRepository = inventarioRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    @Transactional
    public Poliza generarPoliza(PolizaRequestDTO polizaRequest) {
        Empleado empleado = empleadoRepository.findById(polizaRequest.getIdEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Inventario inventario = inventarioRepository.findById(polizaRequest.getSku())
                .orElseThrow(() -> new RuntimeException("Artículo de inventario no encontrado"));

        // Cantidad en poliza mayor a 0
        if (polizaRequest.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad en la póliza debe ser mayor a cero");
        }

        if (inventario.getCantidad() < polizaRequest.getCantidad()) {
            throw new RuntimeException("Inventario insuficiente para el artículo: " + inventario.getNombre());
        }

        // Quitamos cantidad de inventario
        inventario.setCantidad(inventario.getCantidad() - polizaRequest.getCantidad());
        inventarioRepository.save(inventario); // Guardamos el cambio

        Poliza nuevaPoliza = new Poliza();
        nuevaPoliza.setEmpleado(empleado);
        nuevaPoliza.setInventario(inventario);
        nuevaPoliza.setCantidad(polizaRequest.getCantidad());
        nuevaPoliza.setFecha(LocalDateTime.now());

        return polizaRepository.save(nuevaPoliza);
    }

    @Override
    @Transactional // Inicia Transaccion
    public Poliza eliminarPoliza(Integer idPoliza) {
        Poliza poliza = polizaRepository.findById(idPoliza)
                .orElseThrow(() -> new RuntimeException("Póliza no encontrada"));

        Inventario inventario = poliza.getInventario();

        // Regresar (sumar) la cantidad al inventario
        inventario.setCantidad(inventario.getCantidad() + poliza.getCantidad());
        inventarioRepository.save(inventario); // Guardamos el cambio en el inventario

        // Eliminar poliza
        polizaRepository.delete(poliza);

        return poliza;
    }

    @Override
    public Poliza getById(Integer idPoliza) {
        return polizaRepository.findById(idPoliza)
                .orElseThrow(() -> new RuntimeException("Póliza no econtrada con ID: " + idPoliza));
    }

    @Override
    public List<Poliza> findAll() {
        return polizaRepository.findAll();
    }
}