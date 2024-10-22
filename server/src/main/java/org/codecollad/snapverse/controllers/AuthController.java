package org.codecollad.snapverse.controllers;

import org.codecollad.snapverse.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.codecollad.snapverse.models.dto.LoginDTO;
import org.codecollad.snapverse.services.AuthService;
import org.codecollad.snapverse.models.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    private ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDTO loginRequest) {
        ApiResponse<?> apiResponse = authService.login(loginRequest);
        return new ResponseEntity<>(apiResponse, apiResponse.getSuccess() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    private ResponseEntity<ApiResponse<?>> register(@RequestBody User user) {
        ApiResponse<?> response = authService.register(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
