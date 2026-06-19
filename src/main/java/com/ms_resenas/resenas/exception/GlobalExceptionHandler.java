package com.ms_resenas.resenas.exception;


import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Recurso no encontrado → 404
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 2. Errores de validación de DTOs (@Valid) → 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errores);
    }

    // 3. Errores de conversión de parámetros (ej. ?minPrecio=abc) → 400
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> manejarErrorDeTipo(MethodArgumentTypeMismatchException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Valor inválido para el parámetro '" + ex.getName() + "'. Se esperaba un valor de tipo " +
                        (ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido"),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    // 4. Errores al llamar a otros microservicios con Feign → código del servicio remoto
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> manejarErrorFeign(FeignException ex) {
        HttpStatus status = HttpStatus.resolve(ex.status());
        if (status == null) status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse error = new ErrorResponse(
                ex.status(),
                "Error al comunicarse con un servicio externo: " + ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

    // 5. Errores de regla de negocio (ej. usuario no compró el producto) → 409 Conflict
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> manejarErrorDeNegocio(IllegalStateException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // 6. Error de Storage → 502 Bad Gateway
    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorResponse> manejarErrorStorage(StorageException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_GATEWAY.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }

    // 7. Cualquier otra excepción no controlada → 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErrorGeneral(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor: " + ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
