package com.coppel.services.impl;

import com.coppel.dto.PolizaRequestDTO;
import com.coppel.dto.PolizaUpdateRequestDTO;
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

    @Override
    public Poliza actualizarPoliza(Integer idPoliza, PolizaUpdateRequestDTO polizaRequest) {

        // Encontrar la póliza existente
        Poliza polizaExistente = polizaRepository.findById(idPoliza)
                .orElseThrow(() -> new RuntimeException("Poliza no encontrada con el ID: " + idPoliza));

        // Guardar los datos originales para la lógica de inventario.
        Inventario inventarioDeArticulo = polizaExistente.getInventario(); // Guardar inventario actual
        int cantidadActualPoliza = polizaExistente.getCantidad(); // Guardar cantidad actual de poliza

        // Validar los nuevos datos que llegan en la
        // petición-------------------------------------------

        // Validar empleado de peticion
        Empleado nuevoEmpleado = empleadoRepository.findById(polizaRequest.getIdEmpleado())
                .orElseThrow(
                        () -> new RuntimeException("Empleado no encontrado con ID: " + polizaRequest.getIdEmpleado()));

        // Validar articulo de peticion
        Inventario nuevoInventarioArticulo = inventarioRepository.findById(polizaRequest.getSku())
                .orElseThrow(() -> new RuntimeException(
                        "Artículo de inventario no encontrado con SKU: " + polizaRequest.getSku()));

        // Validar cantidad de peticion
        int nuevaCantidad = polizaRequest.getCantidad();
        if (nuevaCantidad <= 0) {
            throw new RuntimeException("La cantidad en la póliza debe ser mayor a cero");
        }

        // Lógica de ajuste de
        // inventario---------------------------------------------------------------------------

        boolean skuHaCambiado = !inventarioDeArticulo.getSku().equals(nuevoInventarioArticulo.getSku());

        if (skuHaCambiado) {
            // Si el artículo cambia regresamos la cantidad actual de la poliza a inventario
            // del articulo
            inventarioDeArticulo.setCantidad(inventarioDeArticulo.getCantidad() + cantidadActualPoliza);

            if (nuevoInventarioArticulo.getCantidad() < nuevaCantidad) {
                throw new RuntimeException(
                        "Inventario insuficiente para el nuevo artículo: " + nuevoInventarioArticulo.getNombre());
            }
            nuevoInventarioArticulo.setCantidad(nuevoInventarioArticulo.getCantidad() - nuevaCantidad);

        } else { // Si el SKU es el mismo, solo cambia la cantidad o el empleado.
            int diferencia = nuevaCantidad - cantidadActualPoliza;

            // Si la diferencia es positiva, significa que estamos tomando MÁS artículos,
            // así que debemos verificar si hay stock suficiente para esa diferencia.
            if (diferencia > 0 && inventarioDeArticulo.getCantidad() < diferencia) {
                throw new RuntimeException("Inventario insuficiente para ajustar la cantidad. Stock actual: "
                        + inventarioDeArticulo.getCantidad() + ", se necesitan " + diferencia + " adicionales.");
            }

            // Ajustamos el inventario. Si la diferencia es 2, restamos 2 del stock.
            // Si la diferencia es -2 (porque la póliza disminuyó), restamos -2 (lo que
            // equivale a sumar 2).
            inventarioDeArticulo.setCantidad(inventarioDeArticulo.getCantidad() - diferencia);
        }

        // Actualizamos los datos de la póliza existente.
        polizaExistente.setEmpleado(nuevoEmpleado);
        polizaExistente.setInventario(nuevoInventarioArticulo);
        polizaExistente.setCantidad(nuevaCantidad);

        // Guradamos cambios
        return polizaRepository.save(polizaExistente);
    }

}