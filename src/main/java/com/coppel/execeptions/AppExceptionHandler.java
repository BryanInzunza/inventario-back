package com.coppel.execeptions;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coppel.dto.ApiResponseDTO;
import com.coppel.util.Meta;

/**
 * Clase para manejo de excepciones no controladas.
 */
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = { ResponseStatusException.class })
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        log.warn("Excepción de cliente capturada ({}): {}", ex.getStatusCode(), ex.getReason());

        Meta meta = new Meta();
        meta.setDevMessage(null);
        meta.setStatus("CLIENT_ERROR");
        meta.setStatusCode(ex.getStatusCode().value());
        meta.setMessage(ex.getReason());
        meta.setTimestamp(LocalDateTime.now().toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ApiResponseDTO apiResponse = new ApiResponseDTO(meta, null);
        return new ResponseEntity<>(apiResponse, httpHeaders, ex.getStatusCode());
    }

    /**
     * Cualquier excepción que no sea atendida será tratada en este método.
     * 
     * @param runtimeException
     * @param webRequest
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(value = { Exception.class, RuntimeException.class })
    protected ResponseEntity<Object> handleGenericException(RuntimeException runtimeException, WebRequest webRequest) {
        log.error("Excepción interna no controlada: ", runtimeException);

        Meta meta = new Meta();
        meta.setStatus("ERROR_SERVIDOR");
        meta.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        meta.setDevMessage(
                runtimeException.getMessage() == null ? runtimeException.getClass().toString()
                        : runtimeException.getMessage());
        meta.setTimestamp(LocalDateTime.now().toString());
        ApiResponseDTO apiResponse = new ApiResponseDTO(meta, null);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return handleExceptionInternal(
                runtimeException,
                apiResponse,
                httpHeaders,
                HttpStatus.INTERNAL_SERVER_ERROR,
                webRequest);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("Excepción de recurso no encontrado capturada: {}", ex.getMessage());

        Meta meta = new Meta();
        meta.setDevMessage(ex.getMessage());
        meta.setStatus("NOT_FOUND");
        meta.setStatusCode(HttpStatus.NOT_FOUND.value()); // Código 404 Not Found
        meta.setMessage(ex.getMessage());
        meta.setTimestamp(LocalDateTime.now().toString());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ApiResponseDTO apiResponse = new ApiResponseDTO(meta, null); // No hay datos en el cuerpo para un 404

        return new ResponseEntity<>(apiResponse, httpHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        log.warn("Excepción de recurso duplicado capturada: {}", ex.getMessage());

        Meta meta = new Meta();
        meta.setDevMessage(ex.getMessage());
        meta.setStatus("CONFLICT");
        meta.setStatusCode(HttpStatus.CONFLICT.value()); // Código 409 Conflict
        meta.setMessage(ex.getMessage());
        meta.setTimestamp(LocalDateTime.now().toString());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ApiResponseDTO apiResponse = new ApiResponseDTO(meta, null);

        return new ResponseEntity<>(apiResponse, httpHeaders, HttpStatus.CONFLICT);
    }
}