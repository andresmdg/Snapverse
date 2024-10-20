package org.codecollad.snapverse.controllers;

import java.util.Map;

import org.codecollad.snapverse.models.dto.LoginDTO;
import org.codecollad.snapverse.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    private ResponseEntity<?> login (@RequestBody LoginDTO loginRequest) {
        Map<String , String> response = authService.login(loginRequest);
        if (response.containsKey("jwt")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    
}
