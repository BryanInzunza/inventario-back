package com.coppel.controllers;

import com.coppel.dto.ApiResponseDTO;
import com.coppel.dto.InventarioRequestDTO;
import com.coppel.dto.InventarioResponseDTO;
import com.coppel.entities.Inventario;
import com.coppel.services.InventarioService;
import com.coppel.util.Meta;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;
    private final ModelMapper modelMapper;

    public InventarioController(InventarioService inventarioService, ModelMapper modelMapper) {
        this.inventarioService = inventarioService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAllInventario() {
        List<Inventario> inventarioList = inventarioService.findAll();
        List<InventarioResponseDTO> inventarioDTOs = inventarioList.stream()
                .map(inventario -> modelMapper.map(inventario, InventarioResponseDTO.class))
                .collect(Collectors.toList());

        Meta meta = new Meta();
        meta.setStatus("OK");
        meta.setStatusCode(HttpStatus.OK.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, inventarioDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ApiResponseDTO> getInventarioBySku(@PathVariable String sku) {
        Inventario inventarioPorSku = inventarioService.findBySku(sku);

        InventarioResponseDTO inventarioDTO = modelMapper.map(inventarioPorSku, InventarioResponseDTO.class);
        Meta meta = new Meta();
        meta.setStatus("OK");
        meta.setStatusCode(HttpStatus.OK.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, inventarioDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> createInventario(@RequestBody InventarioRequestDTO requestDTO) {
        // Convertimos lo que llega por body a una entidad Inventario
        Inventario inventarioACrear = modelMapper.map(requestDTO, Inventario.class);
        Inventario nuevoInventario = inventarioService.save(inventarioACrear);

        // Mapeamos lo que guardamos en BD para devolver al usuario
        InventarioResponseDTO inventarioDTO = modelMapper.map(nuevoInventario, InventarioResponseDTO.class);
        Meta meta = new Meta();
        meta.setStatus("OK");
        meta.setStatusCode(HttpStatus.CREATED.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, inventarioDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<ApiResponseDTO> updateInventario(@PathVariable String sku,
            @RequestBody InventarioRequestDTO requestDTO) {
        Inventario inventarioParaActualizar = modelMapper.map(requestDTO, Inventario.class);
        Inventario inventarioConActualizacion = inventarioService.update(sku, inventarioParaActualizar);

        InventarioResponseDTO inventarioDTO = modelMapper.map(inventarioConActualizacion, InventarioResponseDTO.class);
        Meta meta = new Meta();
        meta.setStatus("OK");
        meta.setStatusCode(HttpStatus.OK.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, inventarioDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<ApiResponseDTO> deleteInventario(@PathVariable String sku) {
        Inventario inventarioAEliminar = inventarioService.deleteBySku(sku);

        InventarioResponseDTO inventarioDTO = modelMapper.map(inventarioAEliminar, InventarioResponseDTO.class);

        Meta meta = new Meta();
        meta.setStatus("OK");
        meta.setStatusCode(HttpStatus.OK.value());
        ApiResponseDTO response = new ApiResponseDTO(meta, inventarioDTO);

        return ResponseEntity.ok(response);
    }
}