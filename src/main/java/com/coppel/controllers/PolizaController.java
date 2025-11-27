package com.coppel.controllers;

import com.coppel.dto.ApiResponseDTO;
import com.coppel.dto.PolizaRequestDTO;
import com.coppel.dto.PolizaResponseDTO;
import com.coppel.entities.Poliza;
import com.coppel.services.PolizaService;
import com.coppel.util.Meta;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/polizas")
public class PolizaController {

    private final PolizaService polizaService;
    private final ModelMapper modelMapper;

    public PolizaController(PolizaService polizaService, ModelMapper modelMapper) {
        this.polizaService = polizaService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> generarPoliza(@RequestBody PolizaRequestDTO requestDTO) {

        // Poliza nuevaPoliza = modelMapper.map(requestDTO, Poliza.class);
        Poliza polizaAGuardar = polizaService.generarPoliza(requestDTO);

        PolizaResponseDTO polizaDTO = modelMapper.map(polizaAGuardar, PolizaResponseDTO.class);

        Meta meta = new Meta();
        meta.setStatus("OK");
        meta.setStatusCode(HttpStatus.CREATED.value()); // 201 CREATED
        ApiResponseDTO respuesta = new ApiResponseDTO(meta, polizaDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAllPolizas() {
        List<Poliza> polizasList = polizaService.findAll();

        List<PolizaResponseDTO> polizaDTOs = polizasList.stream()
                .map(poliza -> modelMapper.map(poliza, PolizaResponseDTO.class))
                .collect(Collectors.toList());

        Meta meta = new Meta();
        meta.setStatus("OK");
        meta.setStatusCode(HttpStatus.OK.value());
        ApiResponseDTO respuesta = new ApiResponseDTO(meta, polizaDTOs);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getPolizaById(@PathVariable Integer id) {
        Poliza polizaPorId = polizaService.getById(id);

        PolizaResponseDTO polizaDTO = modelMapper.map(polizaPorId, PolizaResponseDTO.class);
        Meta meta = new Meta();
        meta.setStatus("OK");
        meta.setStatusCode(HttpStatus.OK.value());
        ApiResponseDTO respuesta = new ApiResponseDTO(meta, polizaDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deletePoliza(@PathVariable Integer id) {
        Poliza polizaAEliminar = polizaService.eliminarPoliza(id);

        PolizaResponseDTO polizaDTO = modelMapper.map(polizaAEliminar, PolizaResponseDTO.class);

        Meta meta = new Meta();
        meta.setStatus("OK");
        meta.setStatusCode(HttpStatus.OK.value());
        ApiResponseDTO respuesta = new ApiResponseDTO(meta, polizaDTO);

        return ResponseEntity.ok(respuesta);
    }
}