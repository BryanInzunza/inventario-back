package com.coppel.services.impl;

   import com.coppel.entities.Empleado;
   import com.coppel.repositories.EmpleadoRepository;
   import com.coppel.services.EmpleadoService;
   import org.springframework.stereotype.Service;

   import java.util.List;
   import java.util.Optional;

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
       public Optional<Empleado> findById(Integer idEmpleado) {
           return empleadoRepository.findById(idEmpleado);
       }

       @Override
       public Empleado save(Empleado empleado) {
           return empleadoRepository.save(empleado); 
       }

       @Override
       public Empleado update(Integer idEmpleado, Empleado empleadoDetails) {
           // 1. Verificar si el empleado existe
           Empleado empleadoExistente = empleadoRepository.findById(idEmpleado)
                   .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + idEmpleado));

           // 2. Actualizar los campos necesarios
           empleadoExistente.setNombre(empleadoDetails.getNombre());
           empleadoExistente.setApellido(empleadoDetails.getApellido());
           empleadoExistente.setPuesto(empleadoDetails.getPuesto());

           // 3. Guardar el empleado actualizado
           return empleadoRepository.save(empleadoExistente);
       }

       @Override
       public void deleteById(Integer idEmpleado) {
           // 1. Verificar si el empleado existe antes de eliminar
           Empleado empleadoExistente = empleadoRepository.findById(idEmpleado)
                   .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + idEmpleado));
           empleadoRepository.delete(empleadoExistente); 
       }
   }