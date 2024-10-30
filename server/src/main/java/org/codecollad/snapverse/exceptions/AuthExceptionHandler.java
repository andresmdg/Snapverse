package org.codecollad.snapverse.exceptions;

import org.codecollad.snapverse.exceptions.custom.user.InvalidCredentialsException;
import org.codecollad.snapverse.exceptions.custom.user.TokenGenerationException;
import org.codecollad.snapverse.exceptions.custom.user.UserAlreadyExistsException;
import org.codecollad.snapverse.exceptions.custom.user.UserNotFoundException;
import org.codecollad.snapverse.models.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .message("Error: The user is not registered in the system. Details: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .statusCode(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT)
                .message("Error: A user with this username already exists. Details: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED)
                .message("Error: Invalid credentials provided. Details: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<ApiResponse<String>> handleTokenGenerationException(TokenGenerationException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(false)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Error: Unable to generate authentication token. Details: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
