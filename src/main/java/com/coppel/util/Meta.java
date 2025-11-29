package com.coppel.util;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Meta - Metadata de respuestas API
 * 
 * El timestamp se genera automáticamente en cada constructor.
 * Status codes: OK (200), CREATED (201), BAD_REQUEST (400), NOT_FOUND (404),
 * CONFLICT (409), ERROR (500)
 */
@Data
public class Meta {

    private String status;
    private int statusCode;
    private String timestamp;

    @JsonInclude(value = Include.NON_NULL)
    private String devMessage;

    @JsonInclude(value = Include.NON_NULL)
    private String message;

    /**
     * Constructor para respuestas exitosas (GET, POST, PUT, DELETE)
     * 
     * @param status     Status de la respuesta (ej: "OK", "CREATED")
     * @param statusCode Código HTTP (ej: 200, 201)
     */
    public Meta(String status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now().toString();
    }

    /**
     * Constructor para respuestas de error con mensaje técnico
     * 
     * @param status     Status del error (ej: "NOT_FOUND", "CONFLICT")
     * @param statusCode Código HTTP (ej: 404, 409, 500)
     * @param message    Mensaje para el usuario final
     * @param devMessage Mensaje técnico para desarrollo
     */
    public Meta(String status, int statusCode, String message, String devMessage) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.devMessage = devMessage;
        this.timestamp = LocalDateTime.now().toString();
    }

}
