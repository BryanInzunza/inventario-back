package com.coppel.services.impl;

import com.coppel.entities.Empleado;
import com.coppel.repositories.EmpleadoRepository;
import com.coppel.services.EmpleadoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.coppel.execeptions.ResourceNotFoundException;
import com.coppel.execeptions.ResourceAlreadyExistsException;

@Service // Marca esta clase como un Servicio
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado findById(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));
    }

    @Override
    public Empleado save(Empleado empleado) {
        // Validar que el empleado no exista previamente
        if (empleado.getIdEmpleado() != null && empleadoRepository.existsById(empleado.getIdEmpleado())) {
            throw new ResourceAlreadyExistsException(
                    "El empleado con ID " + empleado.getIdEmpleado() + " ya existe. Use PUT para actualizar.");
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    @Transactional
    public Empleado update(Integer idEmpleado, Empleado empleadoDetails) {
        // 1. Verificar si el empleado existe
        Empleado empleadoExistente = this.findById(idEmpleado);

        // 2. Actualizar los campos necesarios
        empleadoExistente.setNombre(empleadoDetails.getNombre());
        empleadoExistente.setApellido(empleadoDetails.getApellido());
        empleadoExistente.setPuesto(empleadoDetails.getPuesto());

        // 3. Guardar el empleado actualizado
        return empleadoRepository.save(empleadoExistente);
    }

    @Override
    @Transactional
    public Empleado deleteById(Integer idEmpleado) {
        // 1. Verificar si el empleado existe antes de eliminar
        Empleado empleadoExistente = this.findById(idEmpleado);
        empleadoRepository.delete(empleadoExistente);

        return empleadoExistente;
    }
}