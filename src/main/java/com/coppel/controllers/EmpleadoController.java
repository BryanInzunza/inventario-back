package com.coppel.controllers;

import com.coppel.dto.ApiResponseDTO;
import com.coppel.dto.EmpleadoRequestDTO;
import com.coppel.dto.EmpleadoResponseDTO;
import com.coppel.entities.Empleado;
import com.coppel.services.EmpleadoService;
import com.coppel.util.Meta;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empleados") // URL base de empleados
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final ModelMapper modelMapper;

    public EmpleadoController(EmpleadoService empleadoService, ModelMapper modelMapper) {
        this.empleadoService = empleadoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAllEmpleados() {
        List<Empleado> empleadoList = empleadoService.findAll();
        List<EmpleadoResponseDTO> empleadoDTOs = empleadoList.stream()
                .map(empleado -> modelMapper.map(empleado, EmpleadoResponseDTO.class))
                .collect(Collectors.toList());

        Meta meta = new Meta("OK", HttpStatus.OK.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, empleadoDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getEmpleadoById(@PathVariable Integer id) {
        Empleado empleado = empleadoService.findById(id);

        EmpleadoResponseDTO empleadoDTO = modelMapper.map(empleado, EmpleadoResponseDTO.class);
        Meta meta = new Meta("OK", HttpStatus.OK.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, empleadoDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> createEmpleado(@RequestBody EmpleadoRequestDTO requestDTO) {
        Empleado empleado = modelMapper.map(requestDTO, Empleado.class);
        Empleado nuevoEmpleado = empleadoService.save(empleado);
        EmpleadoResponseDTO empleadoDTO = modelMapper.map(nuevoEmpleado, EmpleadoResponseDTO.class);

        Meta meta = new Meta("OK", HttpStatus.CREATED.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, empleadoDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> updateEmpleado(@PathVariable Integer id,
            @RequestBody EmpleadoRequestDTO requestDTO) {
        Empleado empleadoDetails = modelMapper.map(requestDTO, Empleado.class);
        Empleado empleadoActualizado = empleadoService.update(id, empleadoDetails);
        EmpleadoResponseDTO empleadoDTO = modelMapper.map(empleadoActualizado, EmpleadoResponseDTO.class);

        Meta meta = new Meta("OK", HttpStatus.OK.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, empleadoDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deleteEmpleado(@PathVariable Integer id) {
        Empleado empleadoAEliminar = empleadoService.deleteById(id);

        EmpleadoResponseDTO empleadoDTO = modelMapper.map(empleadoAEliminar, EmpleadoResponseDTO.class);
        Meta meta = new Meta("OK", HttpStatus.OK.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, empleadoDTO);
        return ResponseEntity.ok(response);
    }

}