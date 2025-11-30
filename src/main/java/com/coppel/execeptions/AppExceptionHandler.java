package com.coppel.execeptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coppel.dto.ApiResponseDTO;
import com.coppel.util.Meta;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        log.warn("Excepción de cliente capturada ({}): {}", ex.getStatusCode(), ex.getReason());

        Meta meta = new Meta(
                "CLIENT_ERROR",
                ex.getStatusCode().value(),
                ex.getReason(),
                null);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ApiResponseDTO apiResponse = new ApiResponseDTO(meta, null);
        return new ResponseEntity<>(apiResponse, httpHeaders, ex.getStatusCode());
    }

    @ExceptionHandler(value = { Exception.class, RuntimeException.class })
    protected ResponseEntity<Object> handleGenericException(RuntimeException runtimeException, WebRequest webRequest) {
        log.error("Excepción interna no controlada: ", runtimeException);

        String errorMessage = runtimeException.getMessage() == null
                ? runtimeException.getClass().toString()
                : runtimeException.getMessage();

        Meta meta = new Meta(
                "SERVER_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                errorMessage);
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
        log.warn("Recurso no encontrado: {}", ex.getMessage());

        Meta meta = new Meta(
                "NOT_FOUND",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ApiResponseDTO apiResponse = new ApiResponseDTO(meta, null);

        return new ResponseEntity<>(apiResponse, httpHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        log.warn("Recurso duplicado: {}", ex.getMessage());

        Meta meta = new Meta(
                "CONFLICT",
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                ex.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ApiResponseDTO apiResponse = new ApiResponseDTO(meta, null);

        return new ResponseEntity<>(apiResponse, httpHeaders, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            org.springframework.http.HttpStatusCode statusCode,
            WebRequest request) {
        log.warn("Error de validación en request: {}", ex.getBindingResult().getFieldError());

        StringBuilder errorMessage = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }

        Meta meta = new Meta(
                "BAD_REQUEST",
                statusCode.value(),
                "Validación de datos fallida",
                errorMessage.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        ApiResponseDTO apiResponse = new ApiResponseDTO(meta, null);

        return new ResponseEntity<>(apiResponse, httpHeaders, HttpStatus.BAD_REQUEST);
    }

}