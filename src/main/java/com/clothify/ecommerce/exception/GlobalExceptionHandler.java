package com.clothify.ecommerce.exception;

import com.clothify.ecommerce.exception.jwt.InvalidJwtTokenException;
import com.clothify.ecommerce.exception.order.StockLimitExceededException;
import com.clothify.ecommerce.exception.user.RoleNotFoundException;
import com.clothify.ecommerce.exception.user.UserEmailAlreadyRegisteredException;
import com.clothify.ecommerce.exception.user.UserNotFoundException;
import com.clothify.ecommerce.exception.user.UserPasswordAlreadyUsedException;
import com.clothify.ecommerce.exception.product.CategoryNotFoundException;
import com.clothify.ecommerce.exception.product.ImageNotFoundException;
import com.clothify.ecommerce.exception.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<Object> handleImageNotFoundException(ImageNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserEmailAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleCustomerEmailAlreadyRegisteredException(UserEmailAlreadyRegisteredException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserPasswordAlreadyUsedException.class)
    public ResponseEntity<Object> handleCustomerPasswordAlreadyUsedException(UserPasswordAlreadyUsedException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(StockLimitExceededException.class)
    public ResponseEntity<Object> handleStockLimitExceededException(StockLimitExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<Object> handleInvalidJwtTokenException(StockLimitExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

}
