package com.coppel.services;

import com.coppel.entities.Empleado;

import java.util.List;

public interface EmpleadoService {

    List<Empleado> findAll();

    Empleado findById(Integer idEmpleado);

    Empleado save(Empleado empleado);

    Empleado update(Integer idEmpleado, Empleado empleadoDetails);

    Empleado deleteById(Integer idEmpleado);
}