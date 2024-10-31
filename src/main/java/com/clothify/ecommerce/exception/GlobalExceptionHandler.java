package com.clothify.ecommerce.exception;

import com.clothify.ecommerce.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse failed = ErrorResponse.builder().msg(ex.getMessage()).code(404).build();
        return ResponseEntity.ok().body(failed);
    }

    @ExceptionHandler(RuntimePersistException.class)
    public ResponseEntity<ErrorResponse> handleRuntimePersistException(RuntimePersistException ex) {
        ErrorResponse failed = ErrorResponse.builder().msg(ex.getMessage()).code(404).build();
        return ResponseEntity.ok().body(failed);
    }

}
