package com.coppel.services;

import com.coppel.dto.PolizaRequestDTO;
import com.coppel.dto.PolizaUpdateRequestDTO;
import com.coppel.entities.Poliza;

import java.util.List;

public interface PolizaService {
    Poliza generarPoliza(PolizaRequestDTO polizaRequest);

    Poliza eliminarPoliza(Integer idPoliza);

    Poliza getById(Integer idPoliza);

    List<Poliza> findAll();

    Poliza actualizarPoliza(Integer idPoliza, PolizaUpdateRequestDTO polizaRequest);
}