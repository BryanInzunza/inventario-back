 package com.coppel.services;

   import com.coppel.entities.Empleado;

   import java.util.List;
   import java.util.Optional;

   public interface EmpleadoService {
       List<Empleado> findAll();
       Optional<Empleado> findById(Integer idEmpleado);
       Empleado save(Empleado empleado);
       Empleado update(Integer idEmpleado, Empleado empleadoDetails);
       void deleteById(Integer idEmpleado);
   }