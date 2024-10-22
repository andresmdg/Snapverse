package org.codecollad.snapverse.exceptions;

import org.codecollad.snapverse.models.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .token(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .statusCode(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .token(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .token(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("An unexpected error occurred: " + ex.getMessage())
                .token(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
