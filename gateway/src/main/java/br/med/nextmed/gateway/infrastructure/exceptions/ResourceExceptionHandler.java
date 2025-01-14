package br.med.nextmed.gateway.infrastructure.exceptions;

import br.med.nextmed.common.enums.ErrorCode;
import br.med.nextmed.common.exceptions.DefaultRuntimeException;
import br.med.nextmed.common.models.response.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(" | "));

        var errorResponse = ErrorResponse
            .builder()
            .code(ErrorCode.UNEXPECTED_ERROR)
            .message(errorMessage)
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
    }

    @ExceptionHandler(DefaultRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleDefaultRuntimeException(DefaultRuntimeException exception) {
        var errorResponse = ErrorResponse
            .builder()
            .code(exception.getErrorCode())
            .message(exception.getMessage())
            .timestamp(Instant.now())
            .status(exception.getStatusCode())
            .build();

        return ResponseEntity.status(HttpStatus.valueOf(exception.getStatusCode()))
            .body(errorResponse);
    }

}
