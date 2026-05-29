package com.utnmdp.primerparcial.common.handler;

import com.utnmdp.primerparcial.common.dto.ErrorResponseDTO;
import com.utnmdp.primerparcial.common.exceptions.RecursoNoEncontradoException;
import com.utnmdp.primerparcial.features.anunciantes.excepciones.AnuncianteInactivoException;
import com.utnmdp.primerparcial.features.anunciantes.excepciones.AnunciosActivosException;
import com.utnmdp.primerparcial.features.anunciantes.excepciones.CuitDuplicadoException;
import com.utnmdp.primerparcial.features.anuncios.excepciones.AnuncioCanceladoException;
import com.utnmdp.primerparcial.features.anuncios.excepciones.CampanaFinalizadaException;
import com.utnmdp.primerparcial.features.anuncios.excepciones.DistribucionGastosNoDisponibleException;
import com.utnmdp.primerparcial.features.anuncios.excepciones.LimiteOverbudgetSuperadoException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //capturo los errores que pueden lanzar los diferentes RequestDTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handlerValidaciones(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " +e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponseDTO response = construirError(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ERROR 404
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handlerRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        ErrorResponseDTO response = construirError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // ERROR 409
    @ExceptionHandler({
            CuitDuplicadoException.class,
            AnunciosActivosException.class
    })
    public ResponseEntity<ErrorResponseDTO> handlerConflictos(RuntimeException ex) {
        ErrorResponseDTO response = construirError(HttpStatus.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // ERROR 400
    @ExceptionHandler({
            AnuncianteInactivoException.class,
            AnuncioCanceladoException.class,
            CampanaFinalizadaException.class,
            DistribucionGastosNoDisponibleException.class,
            LimiteOverbudgetSuperadoException.class
    })
    public ResponseEntity<ErrorResponseDTO> handlerReglasDeNegocio(RuntimeException ex) {
        ErrorResponseDTO respuesta = construirError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    private ErrorResponseDTO construirError(HttpStatus status, String message) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(message)
                .build();
    }
}
